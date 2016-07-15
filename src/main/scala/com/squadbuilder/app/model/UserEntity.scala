package com.squadbuilder.app.model


/**
  * Created by Timothy Owens on 7/6/16.
  */

case class UserEntity(override val _id: String, name: Option[String], username: String, email: String, password: String, squads: Option[List[String]] = None, ownedUnits: Option[List[String]] = None) extends BaseEntity


