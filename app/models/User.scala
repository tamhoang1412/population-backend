package models

import org.joda.time.DateTime
import play.api.libs.json.JsValue
import scalikejdbc._
import skinny.orm.SkinnyCRUDMapper

case class User(
  id: Long = 0L,
  name: String,
  email: String,
  profilePicture: String,
  locale: String,
  active: Boolean,
  isAdmin: Boolean,
  createdTime: DateTime,
  lastLoginTime: DateTime
)

object User extends SkinnyCRUDMapper[User] {
  override val tableName = "users"
  override val defaultAlias = createAlias("p")
  override def extract(rs: WrappedResultSet, n: scalikejdbc.ResultName[User]): User = User(
    id = rs.long(n.id),
    name = rs.string(n.name),
    email = rs.string(n.email),
    profilePicture = rs.string(n.profilePicture),
    locale = rs.string(n.locale),
    active = rs.boolean(n.active),
    isAdmin = rs.boolean(n.isAdmin),
    createdTime = rs.jodaDateTime(n.createdTime),
    lastLoginTime = rs.jodaDateTime(n.lastLoginTime)
  )

  def isExistEmail(email: String): Boolean = {
    val usersList = getUserByEmail(email)
    if (usersList.isDefined) true else false
  }

  def getUserByEmail(email: String): Option[User] = {
    User.where('email -> email).apply().headOption
  }

  def create(user: User): Any = {
    User.createWithAttributes(
      'name -> user.name,
      'email -> user.email,
      'profilePicture -> user.profilePicture,
      'locale -> user.locale,
      'active -> user.active,
      'isAdmin -> user.isAdmin,
      'createdTime -> user.createdTime,
      'lastLoginTime -> user.lastLoginTime
    )
  }

  def create(userProfileJson: JsValue): User = {
    val name = (userProfileJson \ "name").as[String]
    val email = (userProfileJson \ "email").as[String]
    val profilePicture = (userProfileJson \ "picture").as[String]
    val locale = (userProfileJson \ "locale").as[String]
    val active = (userProfileJson \ "email_verified").as[String] == "true"
    val isAdmin = false
    val createdTime = DateTime.now
    val lastLoginTime = DateTime.now
    val newUser = User(
      name = name,
      email = email,
      profilePicture = profilePicture,
      locale = locale,
      active = active,
      isAdmin = isAdmin,
      createdTime = createdTime,
      lastLoginTime = lastLoginTime
    )
    User.create(newUser)
    newUser
  }
}
