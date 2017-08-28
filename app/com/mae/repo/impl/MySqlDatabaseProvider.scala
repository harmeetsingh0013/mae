package com.mae.repo.impl

import javax.inject.{Inject, Singleton}

import com.mae.repo.DatabaseProvider
import play.api.db.slick.DatabaseConfigProvider
import slick.basic.DatabaseConfig
import slick.jdbc.JdbcProfile

@Singleton
class MySqlDatabaseProvider @Inject()(dbConfigProvider: DatabaseConfigProvider)
  extends DatabaseProvider {

  override lazy val jdbcConfig: DatabaseConfig[JdbcProfile] = dbConfigProvider.get[JdbcProfile]
}
