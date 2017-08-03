package com.mae.repo

import com.mae.repo.models.Tables.CompaniesRow

import scala.concurrent.Future

trait CompanyRepo {

  def addNewCompany(row: CompaniesRow): Future[Int]
  def findCompanyByNameCodeOrPincode(name: Option[String], code: Option[String], pincode: Option[String], offset: Int, limit: Int): Future[Vector[CompaniesRow]]
  def findCompanyById(id: Int): Future[Option[CompaniesRow]]
  def updateCompanyDetail(id: Int, row: CompaniesRow): Future[Int]
  def deleteCompany(id: Int): Future[Int]
}
