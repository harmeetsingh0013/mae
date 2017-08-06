package com.mae.repo.models

// AUTO-GENERATED Slick data model
/** Stand-alone Slick data model for immediate use */
object Tables extends {
  val profile = slick.jdbc.MySQLProfile
} with Tables

/** Slick data model trait for extension, choice of backend or usage in the cake pattern. (Make sure to initialize this late.) */
trait Tables {
  val profile: slick.jdbc.JdbcProfile
  import profile.api._
  import slick.model.ForeignKeyAction
  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
  import slick.jdbc.{GetResult => GR}

  /** DDL for all tables. Call .create to execute. */
  lazy val schema: profile.SchemaDescription = Companies.schema ++ Orders.schema ++ OrdersItems.schema ++ Products.schema ++ Users.schema
  @deprecated("Use .schema instead of .ddl", "3.0")
  def ddl = schema

  /** Entity class storing rows of table Companies
   *  @param id Database column id SqlType(INT), AutoInc, PrimaryKey
   *  @param addDate Database column add_date SqlType(TIMESTAMP)
   *  @param updateDate Database column update_date SqlType(TIMESTAMP), Default(None)
   *  @param code Database column code SqlType(VARCHAR), Length(45,true)
   *  @param name Database column name SqlType(VARCHAR), Length(255,true)
   *  @param `type` Database column type SqlType(ENUM), Length(8,false)
   *  @param gstNo Database column gst_no SqlType(VARCHAR), Length(45,true)
   *  @param address Database column address SqlType(VARCHAR), Length(255,true)
   *  @param state Database column state SqlType(VARCHAR), Length(45,true), Default(None)
   *  @param city Database column city SqlType(VARCHAR), Length(45,true), Default(None)
   *  @param pincode Database column pincode SqlType(VARCHAR), Length(15,true)
   *  @param others Database column others SqlType(VARCHAR), Length(255,true), Default(None) */
  final case class CompaniesRow(id: Int, addDate: java.sql.Timestamp, updateDate: Option[java.sql.Timestamp] = None, code: String, name: String, `type`: String, gstNo: String, address: String, state: Option[String] = None, city: Option[String] = None, pincode: String, others: Option[String] = None)
  /** GetResult implicit for fetching CompaniesRow objects using plain SQL queries */
  implicit def GetResultCompaniesRow(implicit e0: GR[Int], e1: GR[java.sql.Timestamp], e2: GR[Option[java.sql.Timestamp]], e3: GR[String], e4: GR[Option[String]]): GR[CompaniesRow] = GR{
    prs => import prs._
    CompaniesRow.tupled((<<[Int], <<[java.sql.Timestamp], <<?[java.sql.Timestamp], <<[String], <<[String], <<[String], <<[String], <<[String], <<?[String], <<?[String], <<[String], <<?[String]))
  }
  /** Table description of table companies. Objects of this class serve as prototypes for rows in queries.
   *  NOTE: The following names collided with Scala keywords and were escaped: type */
  class Companies(_tableTag: Tag) extends profile.api.Table[CompaniesRow](_tableTag, Some("mae_app"), "companies") {
    def * = (id, addDate, updateDate, code, name, `type`, gstNo, address, state, city, pincode, others) <> (CompaniesRow.tupled, CompaniesRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(addDate), updateDate, Rep.Some(code), Rep.Some(name), Rep.Some(`type`), Rep.Some(gstNo), Rep.Some(address), state, city, Rep.Some(pincode), others).shaped.<>({r=>import r._; _1.map(_=> CompaniesRow.tupled((_1.get, _2.get, _3, _4.get, _5.get, _6.get, _7.get, _8.get, _9, _10, _11.get, _12)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column add_date SqlType(TIMESTAMP) */
    val addDate: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("add_date")
    /** Database column update_date SqlType(TIMESTAMP), Default(None) */
    val updateDate: Rep[Option[java.sql.Timestamp]] = column[Option[java.sql.Timestamp]]("update_date", O.Default(None))
    /** Database column code SqlType(VARCHAR), Length(45,true) */
    val code: Rep[String] = column[String]("code", O.Length(45,varying=true))
    /** Database column name SqlType(VARCHAR), Length(255,true) */
    val name: Rep[String] = column[String]("name", O.Length(255,varying=true))
    /** Database column type SqlType(ENUM), Length(8,false)
     *  NOTE: The name was escaped because it collided with a Scala keyword. */
    val `type`: Rep[String] = column[String]("type", O.Length(8,varying=false))
    /** Database column gst_no SqlType(VARCHAR), Length(45,true) */
    val gstNo: Rep[String] = column[String]("gst_no", O.Length(45,varying=true))
    /** Database column address SqlType(VARCHAR), Length(255,true) */
    val address: Rep[String] = column[String]("address", O.Length(255,varying=true))
    /** Database column state SqlType(VARCHAR), Length(45,true), Default(None) */
    val state: Rep[Option[String]] = column[Option[String]]("state", O.Length(45,varying=true), O.Default(None))
    /** Database column city SqlType(VARCHAR), Length(45,true), Default(None) */
    val city: Rep[Option[String]] = column[Option[String]]("city", O.Length(45,varying=true), O.Default(None))
    /** Database column pincode SqlType(VARCHAR), Length(15,true) */
    val pincode: Rep[String] = column[String]("pincode", O.Length(15,varying=true))
    /** Database column others SqlType(VARCHAR), Length(255,true), Default(None) */
    val others: Rep[Option[String]] = column[Option[String]]("others", O.Length(255,varying=true), O.Default(None))

    /** Uniqueness Index over (code) (database name code_UNIQUE) */
    val index1 = index("code_UNIQUE", code, unique=true)
    /** Index over (pincode) (database name pincode) */
    val index2 = index("pincode", pincode)
  }
  /** Collection-like TableQuery object for table Companies */
  lazy val Companies = new TableQuery(tag => new Companies(tag))

  /** Entity class storing rows of table Orders
   *  @param id Database column id SqlType(INT), AutoInc, PrimaryKey
   *  @param companyId Database column company_id SqlType(INT)
   *  @param invoiceNo Database column invoice_no SqlType(VARCHAR), Length(45,true)
   *  @param addDate Database column add_date SqlType(TIMESTAMP)
   *  @param updateDate Database column update_date SqlType(VARCHAR), Length(45,true), Default(None)
   *  @param status Database column status SqlType(ENUM), Length(7,false), Default(approve)
   *  @param discount Database column discount SqlType(DECIMAL), Default(None)
   *  @param sGst Database column s_gst SqlType(DECIMAL)
   *  @param cGst Database column c_gst SqlType(DECIMAL) */
  final case class OrdersRow(id: Int, companyId: Int, invoiceNo: String, addDate: java.sql.Timestamp, updateDate: Option[String] = None, status: String = "approve", discount: Option[scala.math.BigDecimal] = None, sGst: scala.math.BigDecimal, cGst: scala.math.BigDecimal)
  /** GetResult implicit for fetching OrdersRow objects using plain SQL queries */
  implicit def GetResultOrdersRow(implicit e0: GR[Int], e1: GR[String], e2: GR[java.sql.Timestamp], e3: GR[Option[String]], e4: GR[Option[scala.math.BigDecimal]], e5: GR[scala.math.BigDecimal]): GR[OrdersRow] = GR{
    prs => import prs._
    OrdersRow.tupled((<<[Int], <<[Int], <<[String], <<[java.sql.Timestamp], <<?[String], <<[String], <<?[scala.math.BigDecimal], <<[scala.math.BigDecimal], <<[scala.math.BigDecimal]))
  }
  /** Table description of table orders. Objects of this class serve as prototypes for rows in queries. */
  class Orders(_tableTag: Tag) extends profile.api.Table[OrdersRow](_tableTag, Some("mae_app"), "orders") {
    def * = (id, companyId, invoiceNo, addDate, updateDate, status, discount, sGst, cGst) <> (OrdersRow.tupled, OrdersRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(companyId), Rep.Some(invoiceNo), Rep.Some(addDate), updateDate, Rep.Some(status), discount, Rep.Some(sGst), Rep.Some(cGst)).shaped.<>({r=>import r._; _1.map(_=> OrdersRow.tupled((_1.get, _2.get, _3.get, _4.get, _5, _6.get, _7, _8.get, _9.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column company_id SqlType(INT) */
    val companyId: Rep[Int] = column[Int]("company_id")
    /** Database column invoice_no SqlType(VARCHAR), Length(45,true) */
    val invoiceNo: Rep[String] = column[String]("invoice_no", O.Length(45,varying=true))
    /** Database column add_date SqlType(TIMESTAMP) */
    val addDate: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("add_date")
    /** Database column update_date SqlType(VARCHAR), Length(45,true), Default(None) */
    val updateDate: Rep[Option[String]] = column[Option[String]]("update_date", O.Length(45,varying=true), O.Default(None))
    /** Database column status SqlType(ENUM), Length(7,false), Default(approve) */
    val status: Rep[String] = column[String]("status", O.Length(7,varying=false), O.Default("approve"))
    /** Database column discount SqlType(DECIMAL), Default(None) */
    val discount: Rep[Option[scala.math.BigDecimal]] = column[Option[scala.math.BigDecimal]]("discount", O.Default(None))
    /** Database column s_gst SqlType(DECIMAL) */
    val sGst: Rep[scala.math.BigDecimal] = column[scala.math.BigDecimal]("s_gst")
    /** Database column c_gst SqlType(DECIMAL) */
    val cGst: Rep[scala.math.BigDecimal] = column[scala.math.BigDecimal]("c_gst")

    /** Foreign key referencing Companies (database name fk_orders_1) */
    lazy val companiesFk = foreignKey("fk_orders_1", companyId, Companies)(r => r.id, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)

    /** Uniqueness Index over (invoiceNo) (database name invoice_no) */
    val index1 = index("invoice_no", invoiceNo, unique=true)
  }
  /** Collection-like TableQuery object for table Orders */
  lazy val Orders = new TableQuery(tag => new Orders(tag))

  /** Entity class storing rows of table OrdersItems
   *  @param id Database column id SqlType(INT), AutoInc, PrimaryKey
   *  @param productId Database column product_id SqlType(INT)
   *  @param orderId Database column order_id SqlType(INT)
   *  @param qty Database column qty SqlType(INT)
   *  @param price Database column price SqlType(DECIMAL) */
  final case class OrdersItemsRow(id: Int, productId: Int, orderId: Int, qty: Int, price: scala.math.BigDecimal)
  /** GetResult implicit for fetching OrdersItemsRow objects using plain SQL queries */
  implicit def GetResultOrdersItemsRow(implicit e0: GR[Int], e1: GR[scala.math.BigDecimal]): GR[OrdersItemsRow] = GR{
    prs => import prs._
    OrdersItemsRow.tupled((<<[Int], <<[Int], <<[Int], <<[Int], <<[scala.math.BigDecimal]))
  }
  /** Table description of table orders_items. Objects of this class serve as prototypes for rows in queries. */
  class OrdersItems(_tableTag: Tag) extends profile.api.Table[OrdersItemsRow](_tableTag, Some("mae_app"), "orders_items") {
    def * = (id, productId, orderId, qty, price) <> (OrdersItemsRow.tupled, OrdersItemsRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(productId), Rep.Some(orderId), Rep.Some(qty), Rep.Some(price)).shaped.<>({r=>import r._; _1.map(_=> OrdersItemsRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column product_id SqlType(INT) */
    val productId: Rep[Int] = column[Int]("product_id")
    /** Database column order_id SqlType(INT) */
    val orderId: Rep[Int] = column[Int]("order_id")
    /** Database column qty SqlType(INT) */
    val qty: Rep[Int] = column[Int]("qty")
    /** Database column price SqlType(DECIMAL) */
    val price: Rep[scala.math.BigDecimal] = column[scala.math.BigDecimal]("price")

    /** Foreign key referencing Orders (database name fk_orders_items_2) */
    lazy val ordersFk = foreignKey("fk_orders_items_2", orderId, Orders)(r => r.id, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
    /** Foreign key referencing Products (database name fk_orders_items_1) */
    lazy val productsFk = foreignKey("fk_orders_items_1", productId, Products)(r => r.id, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
  }
  /** Collection-like TableQuery object for table OrdersItems */
  lazy val OrdersItems = new TableQuery(tag => new OrdersItems(tag))

  /** Entity class storing rows of table Products
   *  @param id Database column id SqlType(INT), AutoInc, PrimaryKey
   *  @param name Database column name SqlType(VARCHAR), Length(255,true)
   *  @param code Database column code SqlType(VARCHAR), Length(45,true)
   *  @param addDate Database column add_date SqlType(TIMESTAMP)
   *  @param updateDate Database column update_date SqlType(TIMESTAMP), Default(None)
   *  @param quantity Database column quantity SqlType(INT)
   *  @param price Database column price SqlType(DECIMAL)
   *  @param companyId Database column company_id SqlType(INT) */
  final case class ProductsRow(id: Int, name: String, code: String, addDate: java.sql.Timestamp, updateDate: Option[java.sql.Timestamp] = None, quantity: Int, price: scala.math.BigDecimal, companyId: Int)
  /** GetResult implicit for fetching ProductsRow objects using plain SQL queries */
  implicit def GetResultProductsRow(implicit e0: GR[Int], e1: GR[String], e2: GR[java.sql.Timestamp], e3: GR[Option[java.sql.Timestamp]], e4: GR[scala.math.BigDecimal]): GR[ProductsRow] = GR{
    prs => import prs._
    ProductsRow.tupled((<<[Int], <<[String], <<[String], <<[java.sql.Timestamp], <<?[java.sql.Timestamp], <<[Int], <<[scala.math.BigDecimal], <<[Int]))
  }
  /** Table description of table products. Objects of this class serve as prototypes for rows in queries. */
  class Products(_tableTag: Tag) extends profile.api.Table[ProductsRow](_tableTag, Some("mae_app"), "products") {
    def * = (id, name, code, addDate, updateDate, quantity, price, companyId) <> (ProductsRow.tupled, ProductsRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(name), Rep.Some(code), Rep.Some(addDate), updateDate, Rep.Some(quantity), Rep.Some(price), Rep.Some(companyId)).shaped.<>({r=>import r._; _1.map(_=> ProductsRow.tupled((_1.get, _2.get, _3.get, _4.get, _5, _6.get, _7.get, _8.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column name SqlType(VARCHAR), Length(255,true) */
    val name: Rep[String] = column[String]("name", O.Length(255,varying=true))
    /** Database column code SqlType(VARCHAR), Length(45,true) */
    val code: Rep[String] = column[String]("code", O.Length(45,varying=true))
    /** Database column add_date SqlType(TIMESTAMP) */
    val addDate: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("add_date")
    /** Database column update_date SqlType(TIMESTAMP), Default(None) */
    val updateDate: Rep[Option[java.sql.Timestamp]] = column[Option[java.sql.Timestamp]]("update_date", O.Default(None))
    /** Database column quantity SqlType(INT) */
    val quantity: Rep[Int] = column[Int]("quantity")
    /** Database column price SqlType(DECIMAL) */
    val price: Rep[scala.math.BigDecimal] = column[scala.math.BigDecimal]("price")
    /** Database column company_id SqlType(INT) */
    val companyId: Rep[Int] = column[Int]("company_id")

    /** Foreign key referencing Companies (database name fk_products_1) */
    lazy val companiesFk = foreignKey("fk_products_1", companyId, Companies)(r => r.id, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)

    /** Index over (code) (database name code) */
    val index1 = index("code", code)
    /** Uniqueness Index over (code) (database name code_UNIQUE) */
    val index2 = index("code_UNIQUE", code, unique=true)
  }
  /** Collection-like TableQuery object for table Products */
  lazy val Products = new TableQuery(tag => new Products(tag))

  /** Entity class storing rows of table Users
   *  @param id Database column id SqlType(INT), AutoInc, PrimaryKey
   *  @param companyId Database column company_id SqlType(INT)
   *  @param firstName Database column first_name SqlType(VARCHAR), Length(255,true)
   *  @param lastName Database column last_name SqlType(VARCHAR), Length(45,true), Default(None)
   *  @param addDate Database column add_date SqlType(TIMESTAMP)
   *  @param updateDate Database column update_date SqlType(TIMESTAMP), Default(None)
   *  @param `type` Database column type SqlType(ENUM), Length(8,false), Default(owner)
   *  @param mobile1 Database column mobile1 SqlType(VARCHAR), Length(15,true)
   *  @param mobile2 Database column mobile2 SqlType(VARCHAR), Length(15,true), Default(None) */
  final case class UsersRow(id: Int, companyId: Int, firstName: String, lastName: Option[String] = None, addDate: java.sql.Timestamp, updateDate: Option[java.sql.Timestamp] = None, `type`: String = "owner", mobile1: String, mobile2: Option[String] = None)
  /** GetResult implicit for fetching UsersRow objects using plain SQL queries */
  implicit def GetResultUsersRow(implicit e0: GR[Int], e1: GR[String], e2: GR[Option[String]], e3: GR[java.sql.Timestamp], e4: GR[Option[java.sql.Timestamp]]): GR[UsersRow] = GR{
    prs => import prs._
    UsersRow.tupled((<<[Int], <<[Int], <<[String], <<?[String], <<[java.sql.Timestamp], <<?[java.sql.Timestamp], <<[String], <<[String], <<?[String]))
  }
  /** Table description of table users. Objects of this class serve as prototypes for rows in queries.
   *  NOTE: The following names collided with Scala keywords and were escaped: type */
  class Users(_tableTag: Tag) extends profile.api.Table[UsersRow](_tableTag, Some("mae_app"), "users") {
    def * = (id, companyId, firstName, lastName, addDate, updateDate, `type`, mobile1, mobile2) <> (UsersRow.tupled, UsersRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(companyId), Rep.Some(firstName), lastName, Rep.Some(addDate), updateDate, Rep.Some(`type`), Rep.Some(mobile1), mobile2).shaped.<>({r=>import r._; _1.map(_=> UsersRow.tupled((_1.get, _2.get, _3.get, _4, _5.get, _6, _7.get, _8.get, _9)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column company_id SqlType(INT) */
    val companyId: Rep[Int] = column[Int]("company_id")
    /** Database column first_name SqlType(VARCHAR), Length(255,true) */
    val firstName: Rep[String] = column[String]("first_name", O.Length(255,varying=true))
    /** Database column last_name SqlType(VARCHAR), Length(45,true), Default(None) */
    val lastName: Rep[Option[String]] = column[Option[String]]("last_name", O.Length(45,varying=true), O.Default(None))
    /** Database column add_date SqlType(TIMESTAMP) */
    val addDate: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("add_date")
    /** Database column update_date SqlType(TIMESTAMP), Default(None) */
    val updateDate: Rep[Option[java.sql.Timestamp]] = column[Option[java.sql.Timestamp]]("update_date", O.Default(None))
    /** Database column type SqlType(ENUM), Length(8,false), Default(owner)
     *  NOTE: The name was escaped because it collided with a Scala keyword. */
    val `type`: Rep[String] = column[String]("type", O.Length(8,varying=false), O.Default("owner"))
    /** Database column mobile1 SqlType(VARCHAR), Length(15,true) */
    val mobile1: Rep[String] = column[String]("mobile1", O.Length(15,varying=true))
    /** Database column mobile2 SqlType(VARCHAR), Length(15,true), Default(None) */
    val mobile2: Rep[Option[String]] = column[Option[String]]("mobile2", O.Length(15,varying=true), O.Default(None))

    /** Foreign key referencing Companies (database name fk_users_1) */
    lazy val companiesFk = foreignKey("fk_users_1", companyId, Companies)(r => r.id, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
  }
  /** Collection-like TableQuery object for table Users */
  lazy val Users = new TableQuery(tag => new Users(tag))
}
