package com.squadbuilder.app.model

import reactivemongo.bson._


/**
  * Created by Timothy Owens on 7/6/16.
  * Copyright © 2016 Data Meaning Services Group, Inc. All rights reserved.
  */

case class UserEntity(override val udid: String, name: String, username: String, email: String, password: String, squads: List[String] = List.empty[String], ownedUnits: List[String] = List.empty[String]) extends BaseEntity(udid) {
  override implicit val handler = Macros.handler[UserEntity]
}


