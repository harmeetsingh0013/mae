package com.mae.controllers

import javax.inject.Inject

import com.mae.models.Company
import com.mae.service.CompanyService
import com.mae.util.Utility.createJsonResponse
import play.api.Logger
import play.api.mvc.{AbstractController, ControllerComponents}

import scala.concurrent.{ExecutionContext, Future}

class CompanyController @Inject()(companyService: CompanyService, cc: ControllerComponents)
                                 (implicit ec: ExecutionContext) extends AbstractController(cc) {

  def addNewCompany = Action.async(parse.json) { implicit request =>
    Logger.info("addNewCompany action performed")

    request.body.validate[Company].asOpt match {
      case Some(company) => companyService.addNewCompany(company).map { value =>
        Ok(createJsonResponse(data = value))
      }.recover {
        case ex =>
          Logger.error("unable to insert new company", ex)
          InternalServerError(createJsonResponse(status = false, msg = "unable to insert new company", data = -1))
      }
      case None => Future.successful(BadRequest(createJsonResponse(status = false, msg = "Invalid json", data = -1)))
    }
  }

  def findCompanies(name: Option[String], code: Option[String]
                    , pincode: Option[String], page: Int) = Action.async { implicit request =>
    Logger.info("findCompanies action performed")

    companyService.findCompanyByNameCodeOrPincode(name, code, pincode, page).map { companies =>
      Ok(createJsonResponse(data = companies))
    }.recover {
      case ex =>
        Logger.error("unable to find companies", ex)
        InternalServerError(createJsonResponse(status = false, msg = "unable to find companies", data = -1))
    }
  }

  def findCompany(id: Int) = Action.async { implicit request =>
    Logger.info("findCompany action performed")

    companyService.findCompanyById(id).map {
      case Some(company) => Ok(createJsonResponse(data = company))
      case None => NotFound(createJsonResponse(status = false, msg = "company not found", data = -1))
    }
  }

  def updateCompany(id: Int) = Action.async(parse.json) { implicit request =>
    Logger.info("updateCompany action performed")

    request.body.validate[Company].asOpt.map { company =>
      companyService.updateCompanyDetail(id, company).map { value =>
        Ok(createJsonResponse(data = value))
      }.recover {
        case ex =>
          Logger.error("unable to update company", ex)
          InternalServerError(createJsonResponse(status = false, msg = "unable to update company", data = -1))
      }
    }.getOrElse(Future.successful(BadRequest(createJsonResponse(status = false, msg = "Invalid json", data = -1))))
  }

  def deleteCompany(id: Int) = Action.async {
    Logger.info("deleteCompany action performed")

    companyService.deleteCompany(id).map { count =>
      Ok(createJsonResponse(data = count))
    }.recover {
      case ex =>
        Logger.error("unable to remove company", ex)
        InternalServerError(createJsonResponse(status = false, msg = "unable to remove company", data = -1))
    }
  }
}
