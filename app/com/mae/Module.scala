package com.mae

import com.google.inject.AbstractModule
import com.mae.repo.{CompanyRepo, ProductRepo}
import com.mae.repo.impl.{CompanyRepoImpl, ProductRepoImpl}
import com.mae.service.{CompanyService, ProductService}
import com.mae.service.impl.{CompanyServiceImpl, ProductServiceImpl}

class Module extends AbstractModule {
  override def configure(): Unit = {
    bind(classOf[ProductService]).to(classOf[ProductServiceImpl])
    bind(classOf[ProductRepo]).to(classOf[ProductRepoImpl])
    bind(classOf[CompanyService]).to(classOf[CompanyServiceImpl])
    bind(classOf[CompanyRepo]).to(classOf[CompanyRepoImpl])
  }
}
