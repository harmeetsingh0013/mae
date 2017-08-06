package com.mae.repo.impl

import javax.inject.Inject

import com.mae.repo.OrderRepo
import org.slf4j.{Logger, LoggerFactory}
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

class OrderRepoImpl @Inject() (dbConfigProvider: DatabaseConfigProvider)
                              (implicit ec: ExecutionContext) extends OrderRepo{

  val logger: Logger = LoggerFactory.getLogger(this.getClass())
  private val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._

  override def generateNewInvoice: Future[Option[String]] = db.run {
    logger.info("In generateNewInvoice repository method")

    sql"""
      |SELECT CONCAT((SELECT UPPER(code) FROM companies WHERE id = 1),
      |LPAD(Auto_increment,8,'0')) FROM information_schema.tables
      |WHERE table_name='orders' AND table_schema='mae_app';
    """.stripMargin.as[String].headOption
  }
}
