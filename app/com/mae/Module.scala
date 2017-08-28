package com.mae

import com.google.inject.AbstractModule
import com.mae.repo.{CompanyRepo, DatabaseProvider, OrderRepo, ProductRepo}
import com.mae.repo.impl.{CompanyRepoImpl, MySqlDatabaseProvider, OrderRepoImpl, ProductRepoImpl}
import com.mae.service.{CompanyService, OrderService, ProductService}
import com.mae.service.impl.{CompanyServiceImpl, OrderServiceImpl, ProductServiceImpl}

class Module extends AbstractModule {
  override def configure(): Unit = {
    bind(classOf[DatabaseProvider]).to(classOf[MySqlDatabaseProvider])

    bind(classOf[ProductRepo]).to(classOf[ProductRepoImpl])
    bind(classOf[ProductService]).to(classOf[ProductServiceImpl])

    bind(classOf[CompanyRepo]).to(classOf[CompanyRepoImpl])
    bind(classOf[CompanyService]).to(classOf[CompanyServiceImpl])

    bind(classOf[OrderRepo]).to(classOf[OrderRepoImpl])
    bind(classOf[OrderService]).to(classOf[OrderServiceImpl])
  }
}
