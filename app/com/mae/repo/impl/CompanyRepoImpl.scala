package com.mae.repo.impl

import javax.inject.Inject

import com.mae.repo.CompanyRepo
import com.mae.repo.models.Tables.{Companies, CompaniesRow}
import org.slf4j.{Logger, LoggerFactory}
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

class CompanyRepoImpl @Inject() (dbConfigProvider: DatabaseConfigProvider)
                                (implicit ec: ExecutionContext) extends CompanyRepo {

  val logger: Logger = LoggerFactory.getLogger(this.getClass())
  private val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._

  private val company = TableQuery[Companies]

  override def addNewCompany(row: CompaniesRow): Future[Int] = db.run{
    logger.info("In addNewCompany repository method")

    company.map(cmp => (cmp.userId, cmp.code, cmp.name, cmp.`type`, cmp.gstNo, cmp.address, cmp.state, cmp.city,
      cmp.pincode, cmp.others, cmp.addDate, cmp.updateDate))
        .+=(row.userId, row.code, row.name, row.`type`, row.gstNo, row.address, row.state, row.city, row.pincode,
          row.others, row.addDate, row.updateDate)
  }

  override def findCompanyByNameCodeOrPincode(name: Option[String], code: Option[String], pincode: Option[String], offset: Int, limit: Int): Future[Vector[CompaniesRow]] = ???

  override def findCompanyById(id: Int): Future[Option[CompaniesRow]] = ???

  override def updateCompanyDetail(id: Int, row: CompaniesRow): Future[Int] = ???

  override def deleteCompany(id: Int): Future[Int] = ???
}
