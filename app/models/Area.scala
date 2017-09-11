package models

import scalikejdbc._
import skinny.orm.SkinnyCRUDMapper

case class Area(
  id: Long,
  country_id: Long,
  year: Int,
  area: Long
)

object Area extends SkinnyCRUDMapper[Area] {
  override val tableName = "area"
  override val defaultAlias = createAlias("p")

  override def extract(rs: WrappedResultSet, n: scalikejdbc.ResultName[Area]): Area = Area(
    id = rs.long(n.id),
    country_id = rs.long(n.country_id),
    year = rs.int(n.year),
    area = rs.long(n.area)
  )
}