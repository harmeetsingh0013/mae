package com.mae.models

import java.sql.Timestamp
import java.text.SimpleDateFormat

import play.api.libs.json._

case class Order (
                 id: Option[Int] = None,
                 companyId: Int,
                 invoiceNo: String,
                 addDate: Option[Timestamp] = None,
                 updateDate: Option[Timestamp] = None,
                 discount: Option[Double] = None,
                 status: Option[String] = None,
                 sGst: Double,
                 cGst: Double,
                 total: Double,
                 products: Vector[OrderItems] = Vector.empty
                 )

case class OrderItems(
                     id: Option[Int],
                     productId: Int,
                     productName: Option[String] = None,
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