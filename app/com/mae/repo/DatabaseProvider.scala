package com.mae.repo

import slick.basic.DatabaseConfig
import slick.jdbc.JdbcProfile

trait DatabaseProvider {

  def jdbcConfig: DatabaseConfig[JdbcProfile]
}
