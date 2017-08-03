package com.mae.service.impl

import javax.inject.{Inject, Singleton}

import com.mae.models.Product
import com.mae.repo.ProductRepo
import com.mae.repo.models.Tables.ProductsRow
import com.mae.service.ProductService
import com.mae.util.Utility
import com.typesafe.config.ConfigFactory
import org.slf4j.{Logger, LoggerFactory}

import scala.concurrent.{ExecutionContext, Future}
import scala.util.Try

@Singleton
class ProductServiceImpl @Inject()(productRepo: ProductRepo)
                                  (implicit ec: ExecutionContext) extends ProductService {
  val logger: Logger = LoggerFactory.getLogger(this.getClass())
  private val LIMIT = Try(ConfigFactory.load().getInt("page.limit")).toOption.getOrElse(10);

  override def addNewProduct(product: Product): Future[Int] = {
    logger.info("In addNewProduct service method")

    productRepo.addNewProduct(mapperProductToProductsRow(product))
  }

  override def findProductsByNameOrCode(name: Option[String], code: Option[String], page: Int): Future[Vector[Product]] = {
    logger.info("In findProductsByNameOrCode service method")

    val offset = (page - 1) * LIMIT
    productRepo.findProductsByNameOrCode(name, code, offset, LIMIT).map { rows =>
      rows.map(mapperProductsRowToProduct(_)).toVector
    }
  }

  override def findProductById(id: Int): Future[Option[Product]] = {
    logger.info("In findProductById service method")

    productRepo.findProductById(id).map {
      case Some(row) => Some(mapperProductsRowToProduct(row))
      case None => Option.empty
    }
  }

  override def updateProduct(id: Int, product: Product): Future[Int] = {
    logger.info("In updateProduct service method")

    productRepo.updateProduct(id,
      mapperProductToProductsRow(product.copy(updateDate = Some(Utility.timestampGenerator))))
  }

  override def removeProductById(id: Int): Future[Int] = {
    logger.info("In removeProductById service method")

    productRepo.removeProductById(id)
  }

  private def mapperProductsRowToProduct(row: ProductsRow) = {
    Product(
      id = Some(row.id),
      name = row.name,
      code = row.code,
      addDate = Some(row.addDate),
      quantity = row.quantity,
      price = row.price.doubleValue()
    )
  }

  private def mapperProductToProductsRow(product: Product) = {
    ProductsRow (
      id = product.id.getOrElse(-1),
      name = product.name,
      code = product.code,
      addDate = product.addDate.getOrElse(Utility.timestampGenerator),
      updateDate = product.updateDate,
      quantity = product.quantity,
      price = BigDecimal(product.price),
      companyId = product.companyId.getOrElse(1)
    )
  }
}
