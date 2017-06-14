package models

import models.Post.createAlias
import org.joda.time.DateTime
import scalikejdbc.WrappedResultSet
import skinny.orm.SkinnyCRUDMapper

/**
 * Created by tam_ht on 3/9/17.
 */
case class User(
  id: Long,
  username: String,
  password: String
)

object User extends SkinnyCRUDMapper[User] {
  override val tableName = "users"
  override val defaultAlias = createAlias("u")

  override def extract(rs: WrappedResultSet, n: scalikejdbc.ResultName[User]): User = User(
    id = rs.long(n.id),
    username = rs.string(n.username),
    password = rs.string(n.password)
  )
}
