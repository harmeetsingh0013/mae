package com.mae.service.impl

import javax.inject.{Inject, Singleton}

import com.mae.models.Company
import com.mae.repo.CompanyRepo
import com.mae.repo.models.Tables.CompaniesRow
import com.mae.service.CompanyService
import com.mae.util.Utility
import com.typesafe.config.ConfigFactory
import org.slf4j.{Logger, LoggerFactory}

import scala.concurrent.{ExecutionContext, Future}
import scala.util.Try

@Singleton
class CompanyServiceImpl @Inject()(companyRepo: CompanyRepo)
                                  (implicit ec: ExecutionContext) extends CompanyService {

  val logger: Logger = LoggerFactory.getLogger(this.getClass())
  private val LIMIT = Try(ConfigFactory.load().getInt("page.limit")).toOption.getOrElse(10);

  override def addNewCompany(company: Company): Future[Int] = {
    logger.info("In addNewCompany service method")

    companyRepo.addNewCompany(mapperCompanyToComapniesRow(company))
  }

  override def findCompanyByNameCodeOrPincode(name: Option[String], code: Option[String],
                                              pincode: Option[String], page: Int): Future[Vector[Company]] = {
    logger.info("In findCompanyByNameCodeOrPincode service method")

    val offset = (page - 1) * LIMIT
    companyRepo.findCompanyByNameCodeOrPincode(name, code, pincode, offset, LIMIT).map { rows =>
      rows.map(mapperComapniesRowToCompany(_)).toVector
    }
  }

  override def findCompanyById(id: Int): Future[Option[Company]] = {
    logger.info("In findCompanyById service method")

    companyRepo.findCompanyById(id).map(_.map(mapperComapniesRowToCompany(_)))
  }

  override def updateCompanyDetail(id: Int, company: Company): Future[Int] = {
    logger.info("In updateCompanyDetail service method")

    companyRepo.updateCompanyDetail(id,
      mapperCompanyToComapniesRow(company.copy(updateDate = Some(Utility.timestampGenerator))))
  }

  override def deleteCompany(id: Int): Future[Int] = {
    logger.info("In deleteCompany service method")

     companyRepo.deleteCompany(id)
  }

  private def mapperCompanyToComapniesRow(company: Company) = {
    CompaniesRow(
      id = company.id.getOrElse(-1),
      name = company.name,
      addDate = company.addDate.getOrElse(Utility.timestampGenerator),
      updateDate = company.updateDate,
      code = company.code,
      `type` = company.`type`.getOrElse("CUSTOMER"),
      gstNo = company.gstNo,
      address = company.address,
      state = company.state,
      city = company.city,
      pincode = company.pincode,
      others = company.others
    )
  }

  private def mapperComapniesRowToCompany(row: CompaniesRow) = {
    Company(
      id = Some(row.id),
      addDate = Some(row.addDate),
      updateDate = row.updateDate,
      code = row.code,
      name = row.name,
      `type` = Some(row.`type`),
      gstNo = row.gstNo,
      address = row.address,
      state = row.state,
      city = row.city,
      pincode = row.pincode,
      others = row.others
    )
  }
}
