package com.mae.service

import com.mae.models.Product

import scala.concurrent.Future

trait ProductService {

  def addNewProduct(product: Product): Future[Int]
  def findProductsByNameOrCode(name: Option[String], code: Option[String]): Future[Vector[Product]]
  def findProductById(id: Int): Future[Option[Product]]
  def updateProduct(id: Int, product: Product): Future[Int]
}
