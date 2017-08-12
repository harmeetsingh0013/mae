package com.mae.controllers

import java.sql.Timestamp
import javax.inject.Inject

import com.mae.models.Order
import com.mae.service.OrderService
import com.mae.util.Utility.createJsonResponse
import play.api.Logger
import play.api.mvc.{AbstractController, ControllerComponents}

import scala.concurrent.{ExecutionContext, Future}

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

  def placeNewOrder = Action.async(parse.json) { implicit request =>
    Logger.info("placeNewOrder action performed")

    request.body.validate[Order].asOpt match {
      case Some(order) => order.products match {
        case p1 +: pn => orderService.placeNewOrder(order.copy(status = Some("APPROVE"))).map { orderNo =>
          Ok(createJsonResponse(data = orderNo))
        }.recover {
          case ex =>
            Logger.error("unable to add new order", ex)
          InternalServerError(createJsonResponse(status = false, msg = "unable to add new order", data = -1))
        }
        case _ =>
          Future.successful(BadRequest(createJsonResponse(status = false,
            msg = "At least one product for order", data = -1)))
      }
      case None => Future.successful(BadRequest(createJsonResponse(status = false,
        msg = "Invalid json", data = -1)))
    }
  }

  def findOrders(companyId: Option[Int], invoiceNo: Option[String], addDate: Option[Timestamp]
                 , status: Option[String], page: Int) = {
    Logger.info("findOrders action performed")

    orderService.findOrders(companyId, invoiceNo, addDate, status, page).map { orders =>
      Ok(createJsonResponse(data = orders))
    }.recover {
      case ex =>
        Logger.error("unable to find orders", ex)
        InternalServerError(createJsonResponse(status = false, msg = "unable to find orders", data = -1))
    }
  }

  def findOrderItems(orderId: Int) = Action.async {
    orderService.findOrderItems(orderId).map { items =>
      Ok(createJsonResponse(data = items))
    }.recover {
      case ex =>
        Logger.error("unable to find order items", ex)
        InternalServerError(createJsonResponse(status = false, msg = "unable to find order items", data = -1))
    }
  }
}
