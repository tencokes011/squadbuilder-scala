package com.squadbuilder.app.actors

import akka.actor.{Actor, ActorLogging}
import akka.http.scaladsl.model.StatusCodes
import akka.util.Timeout
import com.roundeights.hasher.Implicits._
import com.squadbuilder.app.dal.UserDAL
import com.squadbuilder.app.model.{Name, Udid}
import io.igl.jwt._
import org.joda.time.{DateTime, DateTimeZone}

import scala.concurrent.{Await, Future}
import scala.concurrent.duration._
import scala.language.postfixOps
import scala.util.{Failure, Success}

/**
  * Created by Timothy Owens on 7/5/16.
  * Copyright © 2016 Data Meaning Services Group, Inc. All rights reserved.
  */

object UserActor {

  final val salt = "AddingSomeSaltToThis"

  case class Login(username: Option[String] = None, email: Option[String] = None, password: String) {
    def withSalt = password.salt(salt).sha256
  }

}

class UserActor extends Actor with ActorLogging {

  import UserActor._

  private final lazy val alg = Seq(Alg(Algorithm.HS256), Typ("JWT"))
  private final lazy val iss = "SquadBuilderAPI"
  implicit val timeout = Timeout(2 minutes)

  def receive = {
    case l: Login => sender ! loginUser(l.username, l.email, l.withSalt)
  }

  def loginUser(username: Option[String], email: Option[String], password: String) = try {
    Await.result(UserDAL.findUser(username, email, password), 2 minutes) match {
      case Some(user) => user
        val jwt = new DecodedJwt(alg, Seq(Exp(DateTime.now(DateTimeZone.UTC).plusDays(1).getMillis), Iss(iss), Name(user.username), Udid(user.udid)))
        Future.successful(jwt.encodedAndSigned(salt))
      case None => Future.failed(new Throwable(s"${StatusCodes.Unauthorized}"))
    }
  } catch {
    case e: Exception => Future.failed(new Throwable(s"${StatusCodes.BadRequest}", e))
  }
}