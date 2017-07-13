package models

/**
 * Created by tam_ht on 3/8/17.
 */
import play.api.data._
import play.api.data.Forms._

case class CountryForm(
  name: String
)

object CountryForm {
  val form: Form[CountryForm] = Form(
    mapping(
      "name" -> nonEmptyText
    )(CountryForm.apply)(CountryForm.unapply)
  )
}
