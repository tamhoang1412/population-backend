package controllers

import models.LoginForm
import play.api.mvc._
import play.api.i18n.I18nSupport
import play.api.i18n.MessagesApi
import javax.inject._
/**
 * Created by tam_ht on 3/9/17.
 */

class AuthController @Inject() (val messagesApi: MessagesApi) extends Controller with I18nSupport {
  def login: EssentialAction = Action { implicit req =>
    Ok(views.html.auth.login(req.session.get("username"), LoginForm.form))
  }

  def logout: EssentialAction = Action {
    Redirect(routes.AuthController.login).withNewSession.flashing(
      "success" -> "You've been logged out!"
    )
  }

  def authenticate: EssentialAction = Action { implicit req =>
    LoginForm.form.bindFromRequest.fold(
      formWithErrors => {
        BadRequest(views.html.auth.login(req.session.get("username"), formWithErrors))
      },
      user => {
        Ok(views.html.auth.login(req.session.get("username"), LoginForm.form))
      }
    )
  }
}
