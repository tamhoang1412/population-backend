package controllers

/**
 * Created by tam_ht on 3/9/17.
 */
import models.User
import play.api.mvc._

/**
 * Provide security features
 */
trait Secured {

  /**
   * Retrieve the connected user's email
   */
  private def username(request: RequestHeader) = request.session.get("username")
  def userId(request: RequestHeader): Long = User.where('username -> request.session.get("username")).apply().head.id

  /**
   * Not authorized, forward to login
   */
  private def onUnauthorized(request: RequestHeader) = {
    Results.Redirect(routes.AuthController.login)
  }

  /**
   * Action for authenticated users.
   */
  def needAuthenticated(f: => String => Request[AnyContent] => Result): EssentialAction = {
    Security.Authenticated(username, onUnauthorized) { user =>
      Action(request => f(user)(request))
    }
  }
}