package com.mae.models

import java.sql.Date
import java.text.SimpleDateFormat

import play.api.libs.json._

case class Company (
                     id: Option[Int],
                     userId: Option[Int] = None,
                     addDate: Option[Date] = None,
                     updateDate: Option[Date] = None,
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
  implicit object timestampFormat extends Format[Date] {
    val format = new SimpleDateFormat("dd-MM-yyyy")
    def reads(json: JsValue) = {
      val str = json.as[String]
      JsSuccess(new Date(format.parse(str).getTime))
    }
    def writes(ts: Date) = JsString(format.format(ts))
  }

  implicit val formatter = Json.format[Company]
}