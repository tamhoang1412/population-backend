package controllers

import javax.inject._

import play.api.libs.json.JsNull
import play.api.mvc._

class HomeController @Inject() extends Controller {
  def index: EssentialAction = Action { implicit req =>
    Ok(JsNull)
  }
}
