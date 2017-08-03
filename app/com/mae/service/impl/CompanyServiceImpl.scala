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

  override def findCompanyByNameCodeOrPincode(name: Option[String], code: Option[String], pincode: Option[String], page: Int): Future[Vector[Company]] = ???

  override def findCompanyById(id: Int): Future[Option[Company]] = ???

  override def updateCompanyDetail(id: Int, company: Company): Future[Int] = ???

  override def deleteCompany(id: Int): Future[Int] = ???

  private def mapperCompanyToComapniesRow(company: Company) = {
    CompaniesRow(
      id = company.id.getOrElse(-1),
      name = company.name,
      userId = company.userId.getOrElse(1),
      addDate = company.addDate.getOrElse(Utility.timestampGenerator),
      updateDate = company.updateDate,
      code = company.code,
      `type` = company.companyType.getOrElse("CUSTOMER"),
      gstNo = company.gstNo,
      address = company.address,
      state = company.state,
      city = company.city,
      pincode = company.pincode,
      others = company.others
    )
  }
}
