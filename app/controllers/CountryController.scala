package controllers

import javax.inject._

import play.api.mvc._
import models._
import utils.Constants.ResponseCode
import dto._
import play.api.libs.json._

class CountryController @Inject() extends Controller {
  def getAll: EssentialAction = Action { implicit req =>
    val countries = Country.findAll()
    Ok(JSONResponse.convert(ResponseCode.SUCCESS, "", JSONCountry.convertList(countries)))
  }

  def index: EssentialAction = Action { implicit req =>
    Ok(JsNull)
  }
}
