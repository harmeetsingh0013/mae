package com.mae.repo.impl

import javax.inject.{Inject, Singleton}

import com.mae.repo.ProductRepo
import com.mae.repo.models.Tables.{Products, ProductsRow}
import org.slf4j.{Logger, LoggerFactory}
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class ProductRepoImpl @Inject() (dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext)
  extends ProductRepo {

  val logger: Logger = LoggerFactory.getLogger(this.getClass())
  private val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._

  private val product = TableQuery[Products]

  override def addNewProduct(row: ProductsRow): Future[Int] = db.run {
    logger.info("In addNewProduct repository method")

    product.map(p => (p.code, p.name, p.addDate, p.updateDate, p.quantity, p.price, p.companyId))
      .+= (row.code, row.name, row.addDate, row.updateDate, row.quantity, row.price, row.companyId)
  }

  override def findProductsByNameOrCode(name: Option[String], code: Option[String], offset: Int, limit: Int)
                                                        : Future[Seq[ProductsRow]] = db.run {
    logger.info("In findProductsByNameOrCode repository method")

    product.filter { prod =>
      name.map(prod.name like "%" + _ + "%").getOrElse(prod.name like "%") &&
        code.map(prod.code like "%" + _ + "%").getOrElse(prod.code like "%")
    }.drop(offset).take(limit).result
  }

  override def findProductById(id: Int): Future[Option[ProductsRow]] = db.run {
    logger.info("In findProductById repository method")

    product.filter(_.id === id).result.headOption
  }

  override def updateProduct(id: Int, row: ProductsRow): Future[Int] = db.run {
    logger.info("In updateProduct repository method")

    product.filter(_.id === id).update(row)
  }

  override def removeProductById(id: Int): Future[Int] = db.run {
    logger.info("In removeProductById repository method")

    product.filter(_.id === id).delete
  }
}
