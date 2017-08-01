package com.mae.repo

import com.mae.repo.models.Tables.ProductsRow

import scala.concurrent.Future

trait ProductRepo {

  def addNewProduct(row: ProductsRow): Future[Int]
  def findProductsByNameOrCode(name: Option[String], code: Option[String]): Future[Seq[ProductsRow]]
  def findProductById(id: Int): Future[Option[ProductsRow]]
  def updateProduct(id: Int, row: ProductsRow): Future[Int]
}
