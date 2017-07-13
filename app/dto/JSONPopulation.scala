package dto

import models.Population
import play.api.libs.json._

object JSONPopulation {
  def convert(population: Option[Population]): JsValue = {
    population.getOrElse(None) match {
      case None => JsNull
      case _ =>
        val populationSeq = Seq(
          "id" -> JsNumber(population.get.id),
          "country_id" -> JsNumber(population.get.country_id),
          "year" -> JsNumber(population.get.year),
          "population" -> JsNumber(population.get.population)
        )
        JsObject(populationSeq)
    }
  }

  def convertList(population: List[Population]): JsArray = {
    JsArray(population.map(p => convert(Option(p))))
  }
}
