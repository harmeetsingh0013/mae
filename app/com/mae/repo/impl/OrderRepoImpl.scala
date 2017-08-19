package com.mae.repo.impl

import java.sql.Date
import javax.inject.{Inject, Singleton}

import com.mae.repo.OrderRepo
import com.mae.repo.models.Tables
import com.mae.repo.models.Tables.{Orders, OrdersItems, OrdersItemsRow, OrdersRow, Products}
import org.slf4j.{Logger, LoggerFactory}
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class OrderRepoImpl @Inject() (dbConfigProvider: DatabaseConfigProvider)
                              (implicit ec: ExecutionContext) extends OrderRepo{

  val logger: Logger = LoggerFactory.getLogger(this.getClass())
  private val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._

  private val order = TableQuery[Orders]
  private val orderItems = TableQuery[OrdersItems]
  private val products = TableQuery[Products]

  override def generateNewInvoice: Future[Option[String]] = db.run {
    logger.info("In generateNewInvoice repository method")

    sql"""
      |SELECT CONCAT((SELECT UPPER(code) FROM companies WHERE id = 1),
      |LPAD(Auto_increment,8,'0')) FROM information_schema.tables
      |WHERE table_name='orders' AND table_schema='mae_app';
    """.stripMargin.as[String].headOption
  }

  override def placeNewOrder(order: OrdersRow, items: Seq[OrdersItemsRow]): Future[Int] = db.run {
    logger.info("In placeNewOrder repository method")

    (
      for {
        orderId <- (Tables.Orders returning  Tables.Orders.map(_.id)) += order
        _ <- ( orderItems ++= items.map(_.copy(orderId = orderId )))
      } yield (orderId)
    ).transactionally
  }

  override def findOrders(companyId: Option[Int], invoiceNo: Option[String], addDate: Option[Date]
                          , status: Option[String], limit: Int, offset: Int): Future[Seq[OrdersRow]] = db.run {
    logger.info("In findOrders repository method")

    order.filter { ordr =>
      companyId.map(ordr.companyId === _).getOrElse(ordr.id =!= -1) &&
        invoiceNo.map(ordr.invoiceNo === _).getOrElse(ordr.id =!= -1) &&
          addDate.map(ordr.addDate === _).getOrElse(ordr.id =!= -1) &&
            status.map(ordr.status === _).getOrElse(ordr.id =!= -1)
    }.drop(offset).take(limit).result
  }

  override def findOrderItems(orderId: Int): Future[Seq[(Int, Int, String, Int, BigDecimal)]] = db.run {
    logger.info("In findOrderItems repository method")

    {
      for {
        (items, products) <- orderItems.filter(_.orderId === orderId) join products on (_.productId === _.id)
      } yield (items.id, products.id, products.name, items.qty, items.price)
    }.result
  }

  override def updateStatus(orderId: Int, status: String): Future[Int] = db.run {
    logger.info("In findOrderItems repository method")

    {
      for {
        ordr <- order.filter(_.id === orderId)
      } yield (ordr.status)
    }.update(status)
  }
}
