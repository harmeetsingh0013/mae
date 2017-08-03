package com.mae.models

import java.sql.Timestamp
import java.text.SimpleDateFormat

import play.api.libs.json._

case class Company (
                   id: Option[Int],
                   userId: Option[Int] = None,
                   addDate: Option[Timestamp] = None,
                   updateDate: Option[Timestamp] = None,
                   code: String,
                   name: String,
                   `type`: Option[String],
                   gstNo: String,
                   address: String,
                   state: Option[String] = None,
                   city: Option[String] = None,
                   pincode: String,
                   others: Option[String] = None
                   )

object Company {
  implicit object timestampFormat extends Format[Timestamp] {
    val format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS'Z'")
    def reads(json: JsValue) = {
      val str = json.as[String]
      JsSuccess(new Timestamp(format.parse(str).getTime))
    }
    def writes(ts: Timestamp) = JsString(format.format(ts))
  }

  implicit val formatter = Json.format[Company]
}