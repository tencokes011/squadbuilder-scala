package com.squadbuilder.app.utilities

import com.squadbuilder.app.actors.UserActor.Login
import spray.json.DefaultJsonProtocol

/**
  * Created by Timothy Owens on 7/7/16.
  */
trait JSONProtocol extends DefaultJsonProtocol {

  implicit val loginFormat = jsonFormat3(Login)

}
