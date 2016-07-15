package com.squadbuilder.app.dal

import org.json4s.jackson.JsonMethods._
import com.squadbuilder.app.model.{BaseEntityCollection, UserEntity}

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.language.postfixOps

/**
  * Created by Timothy Owens on 7/5/16.
  * Copyright © 2016 Data Meaning Services Group, Inc. All rights reserved.
  */
object UserDAL extends BaseEntityCollection[UserEntity] {

  def findUser(username: Option[String], email: Option[String], password: String) = findOne(if (username.isDefined) Map("username" -> username.get, "password" -> password) else Map("email" -> email.get, "password" -> password)).flatMap {
    case Some(s) => Future.successful(parse(s.toJson()).extract[UserEntity])
    case None => Future.failed(new Throwable("Object was not found"))
  }

  override protected def collectionName: String = "users"

}