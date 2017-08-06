package com.mae.repo

import scala.concurrent.Future

trait OrderRepo {

  def generateNewInvoice: Future[Option[String]]
}
