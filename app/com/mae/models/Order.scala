package com.mae.models

import java.sql.Date
import java.text.SimpleDateFormat

import play.api.libs.json._

case class Order (
                   id: Option[Int] = None,
                   companyId: Int,
                   invoiceNo: String,
                   addDate: Option[Date] = None,
                   updateDate: Option[Date] = None,
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
  implicit object timestampFormat extends Format[Date] {
    val format = new SimpleDateFormat("dd-MM-yyyy")
    def reads(json: JsValue) = {
      val str = json.as[String]
      JsSuccess(new Date(format.parse(str).getTime))
    }
    def writes(ts: Date) = JsString(format.format(ts))
  }

  implicit val formatter = Json.format[Order]
}