package com.mae.controllers

import javax.inject.Inject

import com.mae.models.Product
import com.mae.service.ProductService
import com.mae.util.Utility.createJsonResponse
import play.api.mvc.{AbstractController, ControllerComponents}

import scala.concurrent.{ExecutionContext, Future}

class ProductController @Inject()(productService: ProductService, cc: ControllerComponents)
                                 (implicit ec: ExecutionContext) extends AbstractController(cc) {

  def addNewProduct = Action.async(parse.json) { implicit request =>
    request.body.validate[Product].asOpt match {
      case Some(product) =>
        productService.addNewProduct(product).map { value =>
          Ok(createJsonResponse(data = value))
        }.recover {
          case ex => InternalServerError(createJsonResponse(status = false, msg = "unable to insert new product", data = -1))
        }
      case None => Future.successful(BadRequest(createJsonResponse(status = false, msg = "Invalid json", data = -1)))
    }
  }

  def findProducts(name: Option[String], code: Option[String]) = Action.async { implicit request =>
    productService.findProductsByNameOrCode(name, code).map { products =>
      Ok(createJsonResponse(data = products))
    }.recover {
      case ex => InternalServerError(createJsonResponse(status = false, msg = "unable to insert new product", data = -1))
    }
  }
}
