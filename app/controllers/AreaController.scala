package controllers

import javax.inject._

import play.api.mvc._
import models._
import utils.Constants.ResponseCode
import dto._

class AreaController @Inject() extends Controller {
  def get(country_id: Long): EssentialAction = Action { implicit request =>
    val area = Area.where('country_id -> country_id).apply()
    val country = Country.where('id -> country_id).apply().headOption
    var countryName = ""
    if (country.isDefined) {
      countryName = country.get.name
    }
    Ok(JSONResponse.convert(ResponseCode.SUCCESS, "", JSONArea.countryToJson(countryName, area)))
  }
}
