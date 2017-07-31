package com.mae.service

import com.mae.models.Product

import scala.concurrent.Future

trait ProductService {

  def addNewProduct(product: Product): Future[Int]
  def findProductsByNameOrCode(name: Option[String], code: Option[String]): Future[Vector[Product]]

  def findProductById(id: Int)
  def findAllProducts: Vector[Product]
  def updateProduct(id: Int, product: Product)
}
