package com.mae.util

import java.sql.Timestamp
import java.util.Date

import play.api.libs.json.{Json, Writes}

object Utility {

  def timestampGenerator = new Timestamp(new Date().getTime)

  def createJsonResponse[T](status: Boolean = true, msg: String = "done", data: T)(implicit writes: Writes[T]) =
    Json.obj("status" -> status, "message" -> msg, "data" -> Json.toJson[T](data))

}
