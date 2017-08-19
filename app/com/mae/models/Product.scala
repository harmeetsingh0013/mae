package com.mae.models

import java.sql.Date
import java.text.SimpleDateFormat

import play.api.libs.json._

case class Product (
                   id: Option[Int] = None,
                   code: String,
                   name: String,
                   price: Double,
                   quantity: Int,
                   addDate: Option[Date] = None,
                   updateDate: Option[Date] = None,
                   companyId: Option[Int] = None
                   )

object Product {
  implicit object timestampFormat extends Format[Date] {
    val format = new SimpleDateFormat("dd-MM-yyyy")
    def reads(json: JsValue) = {
      val str = json.as[String]
      JsSuccess(new Date(format.parse(str).getTime))
    }
    def writes(ts: Date) = JsString(format.format(ts))
  }

  implicit val formatter = Json.format[Product]
}
