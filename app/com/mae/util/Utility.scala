package com.mae.util

import java.sql.Date
import java.time.{LocalDate, ZoneId}

import play.api.libs.json.{Json, Writes}

object Utility {

  def timestampGenerator = {
    new Date(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toEpochSecond())
  }

  def createJsonResponse[T](status: Boolean = true, msg: String = "done", data: T)(implicit writes: Writes[T]) =
    Json.obj("status" -> status, "message" -> msg, "data" -> Json.toJson[T](data))
}
