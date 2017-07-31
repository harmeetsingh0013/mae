package com.mae.models

import java.sql.Timestamp
import java.text.SimpleDateFormat

import play.api.libs.json._

case class User (
                id: Option[Int] = None,
                firstName: String,
                lastName: Option[String] = None,
                mobile1: String,
                mobile2: Option[String],
                date: Option[Timestamp] = None
                )

object User {
  implicit object timestampFormat extends Format[Timestamp] {
    val format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS'Z'")
    def reads(json: JsValue) = {
      val str = json.as[String]
      JsSuccess(new Timestamp(format.parse(str).getTime))
    }
    def writes(ts: Timestamp) = JsString(format.format(ts))
  }

  implicit val formatter = Json.format[User]
}