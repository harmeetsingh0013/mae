package com.mae.util

import java.sql.Timestamp
import java.time.{LocalDate, ZoneId}

import play.api.libs.json.{Json, Writes}

object Utility {

  def timestampGenerator = {
    new Timestamp( LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toEpochSecond())
  }

  def createJsonResponse[T](status: Boolean = true, msg: String = "done", data: T)(implicit writes: Writes[T]) =
    Json.obj("status" -> status, "message" -> msg, "data" -> Json.toJson[T](data))

  def calculateTotalBill(tax: Double, amount: Double, discount: Double) = {

    val taxAmt = (tax/100) * amount
    val discountAmt = (discount / 100) * amount
    (amount + taxAmt) - discountAmt
  }
}
