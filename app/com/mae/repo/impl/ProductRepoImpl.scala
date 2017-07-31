package com.mae.repo.impl

import javax.inject.{Inject, Singleton}

import com.mae.repo.{ProductRepo, models}
import com.mae.repo.models.Tables
import com.mae.repo.models.Tables.{Products, ProductsRow}
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class ProductRepoImpl @Inject() (dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext)
  extends ProductRepo {

  private val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._

  private val product = TableQuery[Products]

  override def addNewProduct(row: ProductsRow): Future[Int] = db.run {
    product.map(p => (p.code, p.name, p.date, p.quantity, p.price, p.companyId))
      .+= (row.code, row.name, row.date, row.quantity, row.price, row.companyId)
  }.recover {
    case ex =>
      ex.printStackTrace()
      1
  }

  override def findProductsByNameOrCode(name: Option[String], code: Option[String])
                                                        : Future[Seq[ProductsRow]] = db.run {
    product.filter { prod =>
      name.map(prod.name like "%" + _).getOrElse(prod.name like "%") &&
        code.map(prod.code like "%" + _).getOrElse(prod.code like "%")
    }.result
  }

  override def findProductById(id: Int): Unit = ???

  override def findAllProducts: Vector[ProductsRow] = ???

  override def updateProduct(id: Int, product: ProductsRow): Unit = ???
}
