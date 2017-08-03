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

}
