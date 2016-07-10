package com.squadbuilder.app.services

import java.util.Properties

/**
  * Created by Timothy Owens on 7/5/16.
  * Copyright © 2016 Data Meaning Services Group, Inc. All rights reserved.
  */
object SquadBuilderProperties {

  private lazy val PROPERTIES = {
    val buildProperties = new Properties()
    buildProperties.load(getClass.getClassLoader.getResourceAsStream("build.properties"))

    val serverProperties = new Properties()

    if (buildProperties.getProperty("build.isDeploy") == "1")
      serverProperties.load(getClass.getClassLoader.getResourceAsStream("deploy.properties"))
    else
      serverProperties.load(getClass.getClassLoader.getResourceAsStream("local.properties"))

    serverProperties
  }

  def getProperty(key: String) = PROPERTIES.getProperty(key)
}
