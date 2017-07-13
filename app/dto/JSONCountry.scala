package dto

import play.api.libs.json._
import models.Country

object JSONCountry {
  def convert(country: Option[Country]): JsValue = {
    country.getOrElse(None) match {
      case None => JsNull
      case _ =>
        val countrySeq = Seq(
          "id" -> JsNumber(country.get.id),
          "name" -> JsString(country.get.name),
          "code" -> JsString(country.get.code)
        )
        JsObject(countrySeq)
    }
  }

  def convertList(countries: List[Country]): JsArray = {
    JsArray(countries.map(country => convert(Option(country))))
  }
}
