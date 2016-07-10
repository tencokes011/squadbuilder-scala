package com.squadbuilder.app.core

import akka.actor.{ActorSystem, Props}
import akka.pattern.ask
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.model.{HttpResponse, StatusCodes}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.ExceptionHandler
import akka.http.scaladsl.server.directives.Credentials
import akka.stream.ActorMaterializer
import akka.util.Timeout
import com.squadbuilder.app.actors.UserActor
import com.squadbuilder.app.actors.UserActor.Login
import com.squadbuilder.app.services.SquadBuilderProperties
import com.squadbuilder.app.utilities.JSONProtocol

import scala.util.{Failure, Success}
import scala.concurrent.duration._
import scala.language.postfixOps

/**
  * Created by Timothy Owens on 7/5/16.
  **/

object Main extends JSONProtocol {

  import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._

  implicit val system = ActorSystem("Squad-Builder-WS")
  implicit val materializer = ActorMaterializer()
  implicit val timeout = Timeout(2 minutes)

  lazy val userActor = system.actorOf(Props[UserActor])

  def main = {
    path("signin") {
      entity(as[Login]) { login =>
        onComplete(userActor ? login) {
          case Success(s) => complete(s.toString)
          case Failure(ex) => complete(StatusCodes.BadRequest)
        }
      }
    }
  }

  Http().bindAndHandle(main, SquadBuilderProperties.getProperty("webservice.host"), SquadBuilderProperties.getProperty("webservice.port").toInt)

  implicit def myExceptionHandler: ExceptionHandler =
    ExceptionHandler {
      case _: ArithmeticException =>
        extractUri { uri =>
          println(s"Request to $uri could not be handled normally")
          complete(HttpResponse(StatusCodes.InternalServerError, entity = "Bad Request made to the server"))
        }
    }

  def stripApiKey(credentials: Credentials): Option[String] =
    credentials match {
      case p@Credentials.Provided(id) if p.verify("") => Some(id)
      case _ => None
    }
}
