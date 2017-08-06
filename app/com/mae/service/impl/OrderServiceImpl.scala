package com.mae.service.impl

import javax.inject.Singleton

import com.mae.repo.OrderRepo
import com.mae.service.OrderService
import com.typesafe.config.ConfigFactory
import org.slf4j.{Logger, LoggerFactory}

import scala.concurrent.Future
import scala.util.Try

@Singleton
class OrderServiceImpl(orderRepo: OrderRepo) extends OrderService {

  val logger: Logger = LoggerFactory.getLogger(this.getClass())
  private val LIMIT = Try(ConfigFactory.load().getInt("page.limit")).toOption.getOrElse(10);

  override def generateNewInvoice: Future[Option[String]] = {
    logger.info("In generateNewInvoice service method")

    orderRepo.generateNewInvoice
  }
}
