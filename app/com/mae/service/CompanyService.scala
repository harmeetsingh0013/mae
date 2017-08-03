package com.mae.service

import com.mae.models.Company

import scala.concurrent.Future

trait CompanyService {

  def addNewCompany(company: Company): Future[Int]
  def findCompanyByNameCodeOrPincode(name: Option[String], code: Option[String], pincode: Option[String], page: Int): Future[Vector[Company]]
  def findCompanyById(id: Int): Future[Option[Company]]
  def updateCompanyDetail(id: Int, company: Company): Future[Int]
  def deleteCompany(id: Int): Future[Int]
}

