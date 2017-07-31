package com.mae.service.impl

import javax.inject.{Inject, Singleton}

import com.mae.models.Product
import com.mae.repo.ProductRepo
import scala.concurrent.ExecutionContext.Implicits.global
import com.mae.repo.models.Tables.ProductsRow
import com.mae.service.ProductService
import com.mae.util.Utility

import scala.concurrent.Future

@Singleton
class ProductServiceImpl @Inject()(productRepo: ProductRepo) extends ProductService {

  override def addNewProduct(product: Product): Future[Int] = {

    val row = ProductsRow(id = -1, name = product.name, code = product.code, date = Utility.timestampGenerator,
      quantity = product.quantity, price = BigDecimal(product.price), companyId = product.companyId.flatMap(_.id).getOrElse(1))

    productRepo.addNewProduct(row)
  }

  override def findProductsByNameOrCode(name: Option[String], code: Option[String]): Future[Vector[Product]] = {
    productRepo.findProductsByNameOrCode(name, code).map { rows =>
      rows.map { row =>
        Product(
          id = Some(row.id),
          name = row.name,
          code = row.code,
          date = Some(row.date),
          quantity = row.quantity,
          price = row.price.doubleValue()
        )
      }.toVector
    }
  }

  override def findProductById(id: Int): Unit = ???

  override def findAllProducts: Vector[Product] = ???

  override def updateProduct(id: Int, product: Product): Unit = ???
}
