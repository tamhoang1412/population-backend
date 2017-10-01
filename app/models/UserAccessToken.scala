package models

import org.joda.time.DateTime
import scalikejdbc.WrappedResultSet
import skinny.orm.SkinnyCRUDMapper

case class UserAccessToken(
  id: Long,
  userId: Long,
  accessToken: String,
  createdTime: DateTime,
  isExpired: Boolean
)

object UserAccessToken extends SkinnyCRUDMapper[UserAccessToken] {
  override val tableName = "users_access_tokens"
  override val defaultAlias = createAlias("p")

  override def extract(rs: WrappedResultSet, n: scalikejdbc.ResultName[UserAccessToken]): UserAccessToken =
    UserAccessToken(
      id = rs.long(n.id),
      userId = rs.long(n.userId),
      accessToken = rs.string(n.accessToken),
      createdTime = rs.jodaDateTime(n.createdTime),
      isExpired = rs.boolean(n.isExpired)
    )

  def getAccessToken(token: String): Option[UserAccessToken] = {
    UserAccessToken.where('access_token -> token, 'is_expired -> 0).apply().headOption
  }

  def expireAccessToken(id: Long): Any = {
    UserAccessToken.updateById(id).withAttributes('is_expired -> 1)
  }
}