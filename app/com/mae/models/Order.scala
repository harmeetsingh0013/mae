package com.mae.models

import java.sql.Timestamp
import java.text.SimpleDateFormat

import play.api.libs.json._

case class Order (
                 id: Option[Int] = None,
                 company: Company,
                 discount: Option[Double] = None,
                 sGst: Double,
                 cGst: Double,
                 date: Option[Timestamp] = None,
                 items: Vector[OrderItems]
                 )

case class OrderItems(
                     id: Option[Int],
                     item: Product,
                     quantity: Int,
                     price: Double,
                     )

object OrderItems {
  implicit val formatter = Json.format[OrderItems]
}

object Order {
  implicit object timestampFormat extends Format[Timestamp] {
    val format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS'Z'")
    def reads(json: JsValue) = {
      val str = json.as[String]
      JsSuccess(new Timestamp(format.parse(str).getTime))
    }
    def writes(ts: Timestamp) = JsString(format.format(ts))
  }

  implicit val formatter = Json.format[Order]
}