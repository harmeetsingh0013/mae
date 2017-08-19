package com.mae.service.impl

import java.sql.Date
import javax.inject.{Inject, Singleton}

import com.mae.models.{Order, OrderItems}
import com.mae.repo.OrderRepo
import com.mae.repo.models.Tables.{OrdersItemsRow, OrdersRow}
import com.mae.service.OrderService
import com.mae.util.Utility
import com.typesafe.config.ConfigFactory
import org.slf4j.{Logger, LoggerFactory}

import scala.concurrent.{ExecutionContext, Future}
import scala.util.Try

@Singleton
class OrderServiceImpl @Inject() (orderRepo: OrderRepo)
                                 (implicit ec: ExecutionContext) extends OrderService {

  val logger: Logger = LoggerFactory.getLogger(this.getClass())
  private val LIMIT = Try(ConfigFactory.load().getInt("page.limit")).toOption.getOrElse(10);

  override def generateNewInvoice: Future[Option[String]] = {
    logger.info("In generateNewInvoice service method")

    orderRepo.generateNewInvoice
  }

  override def placeNewOrder(order: Order): Future[Int] = {
    logger.info("In placeNewOrder service method")

    orderRepo.placeNewOrder(mapperOrderToOrdersRows(order), mapperOrderToOrdersItemsRow(order))
  }

  override def findOrders(companyId: Option[Int], invoiceNo: Option[String], addDate: Option[Date]
                          , status: Option[String], page: Int): Future[Vector[Order]] = {
    logger.info("In findOrders service method")

    val offset = (page - 1) * LIMIT
    orderRepo.findOrders(companyId, invoiceNo, addDate, status, LIMIT, offset).map { row =>
      row.map(mapperOrdersRowToOrder(_)).toVector
    }
  }

  override def findOrderItems(orderId: Int): Future[Vector[OrderItems]] = {
    logger.info("In findOrderItems service method")

    orderRepo.findOrderItems(orderId).map { rows =>
      rows.map {
        case (id, productId, productName, qty, price) =>
          OrderItems(
            id = Some(id),
            productId = productId,
            productName = Some(productName),
            quantity = qty,
            price = price.doubleValue()
          )
      }.toVector
    }
  }

  override def cancelOrder(orderId: Int): Future[Int] = {
    logger.info("In cancelOrder service method")

    orderRepo.updateStatus(orderId, "CANCEL")
  }

  private def mapperOrderToOrdersRows(order: Order) = {
    OrdersRow(
      id = order.id.getOrElse(-1),
      companyId = order.companyId,
      invoiceNo = order.invoiceNo,
      addDate = order.addDate.getOrElse(Utility.timestampGenerator),
      updateDate = order.updateDate,
      status = order.status.getOrElse("APPROVE"),
      discount = Some(BigDecimal(order.discount.getOrElse(0.0))),
      sGst = BigDecimal(order.sGst),
      cGst = BigDecimal(order.cGst),
      total = BigDecimal(order.total)
    )
  }

  private def mapperOrdersRowToOrder(row: OrdersRow): Order = {
    Order(
      id = Some(row.id),
      companyId = row.companyId,
      invoiceNo = row.invoiceNo,
      addDate = Some(row.addDate),
      updateDate = row.updateDate,
      discount = Some(row.discount.getOrElse(BigDecimal(0.0)).doubleValue()),
      status = Some(row.status),
      sGst = row.sGst.doubleValue(),
      cGst = row.cGst.doubleValue(),
      total = row.total.doubleValue()
    )
  }

  private def mapperOrderToOrdersItemsRow(order: Order) = {
    order.products.map { item =>
      OrdersItemsRow(id = -1, productId = item.productId, orderId = order.id.getOrElse(-1), qty = item.quantity,
        price = BigDecimal(item.price))
    }
  }


}
