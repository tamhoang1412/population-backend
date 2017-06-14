package models

import org.mindrot.jbcrypt.BCrypt
import play.api.data._
import play.api.data.Forms._

/**
 * Created by tam_ht on 3/9/17.
 */
case class LoginForm(
  username: String,
  password: String
)

object LoginForm {
  val form: Form[LoginForm] = Form(
    mapping(
      "username" -> nonEmptyText,
      "password" -> nonEmptyText
    )(LoginForm.apply)(LoginForm.unapply)
      verifying ("Invalid username or password", result => result match {
        case LoginForm(username, password) => doesUserExist(username, password)
      })
  )

  def doesUserExist(username: String, password: String): Boolean = {
    val userOption = User.where('username -> username).apply.headOption
    userOption match {
      case None       => false
      case Some(user) => BCrypt.checkpw(password, user.password)
    }
  }
}