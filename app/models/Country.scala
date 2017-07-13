package models

import scalikejdbc._
import skinny.orm.SkinnyCRUDMapper

case class Country(
  id: Long,
  name: String,
  code: String
)

object Country extends SkinnyCRUDMapper[Country] {
  override val tableName = "countries"
  override val defaultAlias = createAlias("p")

  override def extract(rs: WrappedResultSet, n: scalikejdbc.ResultName[Country]): Country = Country(
    id = rs.long(n.id),
    name = rs.string(n.name),
    code = rs.string(n.code)
  )
}
