package com.mae.controllers

import javax.inject.Inject

import com.mae.models.Product
import com.mae.service.ProductService
import com.mae.util.Utility.createJsonResponse
import play.api.Logger
import play.api.mvc.{AbstractController, ControllerComponents}

import scala.concurrent.{ExecutionContext, Future}

class ProductController @Inject()(productService: ProductService, cc: ControllerComponents)
                                 (implicit ec: ExecutionContext) extends AbstractController(cc) {

  def addNewProduct = Action.async(parse.json) { implicit request =>
    Logger.info("addNewProduct action performed")

    request.body.validate[Product].asOpt match {
      case Some(product) =>
        productService.addNewProduct(product).map { value =>
          Ok(createJsonResponse(data = value))
        }.recover {
          case ex =>
            Logger.error("unable to insert new product", ex)
            InternalServerError(createJsonResponse(status = false, msg = "unable to insert new product", data = -1))
        }
      case None => Future.successful(BadRequest(createJsonResponse(status = false, msg = "Invalid json", data = -1)))
    }
  }

  def findProducts(name: Option[String], code: Option[String], page: Int) = Action.async { implicit request =>
    Logger.info("findProducts action performed")

    productService.findProductsByNameOrCode(name, code, page).map { products =>
      Ok(createJsonResponse(data = products))
    }.recover {
      case ex =>
        Logger.error("unable to find products", ex)
        InternalServerError(createJsonResponse(status = false, msg = "unable to find products", data = -1))
    }
  }

  def findProduct(id: Int) = Action.async {
    Logger.info("findProduct action performed")

    productService.findProductById(id).map {
      case Some(product) => Ok(createJsonResponse(data = product))
      case None => NotFound(createJsonResponse(status = false, msg = "product not found", data = -1))
    }
  }

  def updateProduct(id: Int) = Action.async(parse.json) { implicit  request =>
    Logger.info("updateProduct action performed")

    request.body.validate[Product].asOpt match {
      case Some(product) =>
        productService.updateProduct(id, product).map { value =>
          Ok(createJsonResponse(data = value))
        }.recover {
          case ex =>
            Logger.error("unable to update product", ex)
            InternalServerError(createJsonResponse(status = false, msg = "unable to update product", data = -1))
        }
      case None => Future.successful(BadRequest(createJsonResponse(status = false, msg = "Invalid json", data = -1)))
    }
  }

  def deleteProduct(id: Int) = Action.async {
    Logger.info("deleteProduct action performed")

    productService.removeProductById(id).map { count =>
      Ok(createJsonResponse(data = count))
    }.recover {
      case ex =>
        Logger.error("unable to remove product", ex)
        InternalServerError(createJsonResponse(status = false, msg = "unable to remove product", data = -1))
    }
  }
}
