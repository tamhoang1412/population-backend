package models

import scalikejdbc._
import skinny.orm.SkinnyCRUDMapper

case class Population(
  id: Long,
  country_id: Long,
  year: Int,
  population: Long
)

object Population extends SkinnyCRUDMapper[Population] {
  override val tableName = "population"
  override val defaultAlias = createAlias("p")

  override def extract(rs: WrappedResultSet, n: scalikejdbc.ResultName[Population]): Population = Population(
    id = rs.long(n.id),
    country_id = rs.long(n.country_id),
    year = rs.int(n.year),
    population = rs.long(n.population)
  )
}