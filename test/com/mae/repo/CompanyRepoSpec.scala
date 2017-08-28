package com.mae.repo

import java.sql.Date

import com.mae.repo.impl.CompanyRepoImpl
import com.mae.repo.models.Tables.CompaniesRow
import org.scalatest.{AsyncWordSpec, BeforeAndAfterAll, Matchers}

class CompanyRepoSpec extends AsyncWordSpec with Matchers with BeforeAndAfterAll {

  private lazy val repo: CompanyRepo = new CompanyRepoImpl(new InMemoryDatabaseProvider)

  "CompanyRepo" should {
    "add new company successful" in {
      val date = new Date(System.currentTimeMillis())
      val row = CompaniesRow(
        id = 1,
        addDate = date,
        updateDate = Some(date),
        code = "MAE",
        name = "Malwa Agro Engg.",
        `type` = "OWNER",
        gstNo = "147852369",
        address = "Moga",
        pincode = "142001"
      )

      repo.addNewCompany(row).map { result =>
        result should ===(1)
      }
    }
  }

}
