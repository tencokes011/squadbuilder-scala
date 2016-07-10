package com.squadbuilder.app.model

import io.igl.jwt.{ClaimField, ClaimValue}
import play.api.libs.json.{JsNumber, JsString, JsValue}

/**
  * Created by Timothy Owens on 7/7/16.
  */

case class Udid(value: String) extends ClaimValue {

  override val field: ClaimField = Udid

  override val jsValue: JsValue = JsString(value)
}

object Udid extends ClaimField {
  override def attemptApply(value: JsValue): Option[ClaimValue] = value.asOpt[String].map(apply)

  override val name: String ="udid"
}

case class Name(value: String) extends ClaimValue {
  override val field: ClaimField = Name

  override val jsValue: JsValue = JsString(value)
}

object Name extends ClaimField {
  override def attemptApply(value: JsValue): Option[ClaimValue] = value.asOpt[String].map(apply)

  override val name: String = "name"
}