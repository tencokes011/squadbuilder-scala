package com.squadbuilder.app.core

import akka.actor.ActorSystem
import akka.http.scaladsl.model.{HttpResponse, StatusCodes}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.ExceptionHandler
import akka.http.scaladsl.server.directives.Credentials
import akka.stream.ActorMaterializer

/**
  * Created by Timothy Owens on 7/5/16.
  * */

object Main {

  implicit val system = ActorSystem("Squad-Builder-WS")
  implicit val materializer = ActorMaterializer()



  def main = {








  }


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
