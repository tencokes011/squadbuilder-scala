package com.squadbuilder.app.dal

import com.squadbuilder.app.model.{BaseEntityCollection, UserEntity}

import scala.util.{Failure, Success}

/**
  * Created by Timothy Owens on 7/5/16.
  * Copyright © 2016 Data Meaning Services Group, Inc. All rights reserved.
  */
object UserDAL extends BaseEntityCollection[UserEntity] {

  def findUser(username: Option[String], email: Option[String], password: String) = findOne(if (username.isDefined) Map("username" -> username.get, "password" -> password) else Map("email" -> email.get, "password" -> password))

  override protected def collectionName: String = "users"

}