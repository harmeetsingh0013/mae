package com.mae.service

import scala.concurrent.Future

trait OrderService {

  def generateNewInvoice: Future[Option[String]]
}
