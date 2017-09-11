package dto

import models.Area
import play.api.libs.json._

object JSONArea {
  def convert(area: Option[Area]): JsValue = {
    area.getOrElse(None) match {
      case None => JsNull
      case _ =>
        val populationSeq = Seq(
          "year" -> JsNumber(area.get.year),
          "area" -> JsNumber(area.get.area)
        )
        JsObject(populationSeq)
    }
  }

  def convertList(population: List[Area]): JsArray = {
    JsArray(population.map(p => convert(Option(p))))
  }

  def countryToJson(countryName: String, area: List[Area]): JsValue = {
    val areaSeq = Seq(
      "name" -> JsString(countryName),
      "area" -> convertList(area)
    )
    JsObject(areaSeq)
  }

}
