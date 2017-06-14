package controllers

import javax.inject._
import play.api.mvc._

@Singleton
class HomeController @Inject() extends Controller {
  def index: EssentialAction = Action { implicit req =>
    Ok(views.html.index(req.session.get("username")))
  }
}
