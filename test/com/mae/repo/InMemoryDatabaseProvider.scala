package com.mae.repo

import slick.basic.DatabaseConfig
import slick.jdbc.JdbcProfile

class InMemoryDatabaseProvider extends DatabaseProvider {

  override lazy val jdbcConfig = DatabaseConfig.forConfig[JdbcProfile]("test")
}
