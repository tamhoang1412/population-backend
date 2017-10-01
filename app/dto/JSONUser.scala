package dto

import models.User
import play.api.libs.json._

object JSONUser {
  def convert(user: User): JsValue = {
    val userSeq = Seq(
      "name" -> JsString(user.name),
      "email" -> JsString(user.email),
      "profile_picture" -> JsString(user.profilePicture),
      "locale" -> JsString(user.locale),
      "active" -> JsBoolean(user.active),
      "isAdmin" -> JsBoolean(user.isAdmin),
      "created_time" -> JsString(user.createdTime.toString),
      "last_login_time" -> JsString(user.lastLoginTime.toString)
    )
    JsObject(userSeq)
  }

  def convert(user: User, accessToken: String): JsValue = {
    val userSeq = Seq(
      "user" -> convert(user),
      "accessToken" -> JsString(accessToken)
    )
    JsObject(userSeq)
  }
}
