package com.mae.controllers

import javax.inject.Inject

import com.mae.service.OrderService
import com.mae.util.Utility.createJsonResponse
import play.api.Logger
import play.api.mvc.{AbstractController, ControllerComponents}

import scala.concurrent.ExecutionContext

class OrderController @Inject()(orderService: OrderService, cc: ControllerComponents)
                               (implicit ec: ExecutionContext) extends AbstractController(cc) {

  def invoiceGenerator = Action.async {
    Logger.info("invoiceGenerator action performed")

    orderService.generateNewInvoice.map {
      case Some(invoiceNo) => Ok(createJsonResponse(data = invoiceNo))
      case None =>
        InternalServerError(createJsonResponse(status = false, msg = "unable to generate InvoiceNo", data = -1))
    }.recover {
      case ex =>
        Logger.error("unable to insert new company", ex)
        InternalServerError(createJsonResponse(status = false, msg = "unable to generate InvoiceNo", data = -1))
    }
  }
}
