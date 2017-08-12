package com.mae.repo

import java.sql.Timestamp

import com.mae.repo.models.Tables.{OrdersItemsRow, OrdersRow}

import scala.concurrent.Future

trait OrderRepo {

  def generateNewInvoice: Future[Option[String]]
  def placeNewOrder(order: OrdersRow, ordersItems: Seq[OrdersItemsRow]): Future[Int]
  def findOrders(companyId: Option[Int], invoiceNo: Option[String], addDate: Option[Timestamp]
                 , status: Option[String], limit: Int, offset: Int): Future[Seq[OrdersRow]]
  def findOrderItems(orderId: Int): Future[Seq[(Int, Int, String, Int, BigDecimal)]]
}
