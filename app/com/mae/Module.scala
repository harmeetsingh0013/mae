package com.mae

import com.google.inject.AbstractModule
import com.mae.repo.ProductRepo
import com.mae.repo.impl.ProductRepoImpl
import com.mae.service.ProductService
import com.mae.service.impl.ProductServiceImpl

class Module extends AbstractModule {
  override def configure(): Unit = {
    bind(classOf[ProductService]).to(classOf[ProductServiceImpl])
    bind(classOf[ProductRepo]).to(classOf[ProductRepoImpl])
  }
}
