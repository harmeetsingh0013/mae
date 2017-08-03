package com.mae.service.impl

import javax.inject.{Inject, Singleton}

import com.mae.models.Company
import com.mae.repo.CompanyRepo
import com.mae.repo.models.Tables.CompaniesRow
import com.mae.service.CompanyService
import com.mae.util.Utility

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class CompanyServiceImpl @Inject()(companyRepo: CompanyRepo)
                                  (implicit ec: ExecutionContext) extends CompanyService {

  override def addNewCompany(company: Company): Future[Int] = {
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
      date = company.date.getOrElse(Utility.timestampGenerator),
      code = company.code,
      companyType = company.companyType.getOrElse("CUSTOMER"),
      gstNo = company.gstNo,
      address = Some(company.address),
      state = company.state,
      city = company.city,
      pincode = company.pincode,
      other = company.other
    )
  }
}
