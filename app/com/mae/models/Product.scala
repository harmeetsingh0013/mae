package com.mae.models

import java.sql.Timestamp
import java.text.SimpleDateFormat

import play.api.libs.json._

case class Product (
                   id: Option[Int] = None,
                   code: String,
                   name: String,
                   price: Double,
                   quantity: Int,
                   addDate: Option[Timestamp] = None,
                   updateDate: Option[Timestamp] = None,
                   companyId: Option[Int] = None
                   )

object Product {
  implicit object timestampFormat extends Format[Timestamp] {
    val format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS'Z'")
    def reads(json: JsValue) = {
      val str = json.as[String]
      JsSuccess(new Timestamp(format.parse(str).getTime))
    }
    def writes(ts: Timestamp) = JsString(format.format(ts))
  }

  implicit val formatter = Json.format[Product]
}
