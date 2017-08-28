package com.mae.repo.impl

import javax.inject.Inject

import com.mae.repo.models.Tables
import com.mae.repo.models.Tables.{Companies, CompaniesRow}
import com.mae.repo.{CompanyRepo, DatabaseProvider}
import org.slf4j.{Logger, LoggerFactory}

import scala.concurrent.{ExecutionContext, Future}

class CompanyRepoImpl @Inject() (provider: DatabaseProvider)
                                (implicit ec: ExecutionContext) extends CompanyRepo {

  val logger: Logger = LoggerFactory.getLogger(this.getClass())
  private val dbConfig = provider.jdbcConfig

  import dbConfig._
  import profile.api._

  private val company = TableQuery[Companies]

  override def addNewCompany(row: CompaniesRow): Future[Int] = db.run{
    logger.info("In addNewCompany repository method")

    company.map(cmp => (cmp.code, cmp.name, cmp.`type`, cmp.gstNo, cmp.address, cmp.state, cmp.city,
      cmp.pincode, cmp.others, cmp.addDate, cmp.updateDate))
        .+=(row.code, row.name, row.`type`, row.gstNo, row.address, row.state, row.city, row.pincode,
          row.others, row.addDate, row.updateDate)
  }

  override def findCompanyByNameCodeOrPincode(name: Option[String], code: Option[String], pincode: Option[String]
                                              , offset: Int, limit: Int): Future[Seq[Tables.CompaniesRow]] = db.run {
    logger.info("In findCompanyByNameCodeOrPincode repository method")

    company.filter { cmp =>
      name.map(cmp.name like "%" + _ + "%").getOrElse(cmp.name like "%") &&
        code.map(cmp.code like "%" + _ + "%").getOrElse(cmp.code like "%") &&
          pincode.map(cmp.pincode like "%" + _  + "%").getOrElse(cmp.pincode like "%")
    }.drop(offset).take(limit).result
  }

  override def findCompanyById(id: Int): Future[Option[CompaniesRow]] = db.run {
    logger.info("In findCompanyById repository method")

    company.filter(_.id === id).result.headOption
  }

  override def updateCompanyDetail(id: Int, row: CompaniesRow): Future[Int] = db.run {
    company.filter(_.id === id).update(row)
  }

  override def deleteCompany(id: Int): Future[Int] = db.run {
    company.filter(_.id === id).delete
  }
}
