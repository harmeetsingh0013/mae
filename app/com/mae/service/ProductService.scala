package com.mae.service

import com.mae.models.Product

import scala.concurrent.Future

trait ProductService {

  def addNewProduct(product: Product): Future[Int]
  def findProductsByNameOrCode(name: Option[String], code: Option[String], page: Int): Future[Vector[Product]]
  def findProductById(id: Int): Future[Option[Product]]
  def updateProduct(id: Int, product: Product): Future[Int]
  def removeProductById(id: Int): Future[Int]
}
