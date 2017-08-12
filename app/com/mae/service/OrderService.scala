package com.mae.service

import java.sql.Timestamp

import com.mae.models.{Order, OrderItems}

import scala.concurrent.Future

trait OrderService {

  def generateNewInvoice: Future[Option[String]]
  def placeNewOrder(order: Order): Future[Int]
  def findOrders(companyId: Option[Int], invoiceNo: Option[String], addDate: Option[Timestamp]
                 , status: Option[String], page: Int): Future[Vector[Order]]
  def findOrderItems(orderId: Int): Future[Vector[OrderItems]]
}
