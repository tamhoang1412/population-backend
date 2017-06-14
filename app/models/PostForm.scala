package models

/**
 * Created by tam_ht on 3/8/17.
 */
import play.api.data._
import play.api.data.Forms._

case class PostForm(
  title: String,
  content: String
)

object PostForm {
  val form: Form[PostForm] = Form(
    mapping(
      "title" -> nonEmptyText,
      "content" -> nonEmptyText
    )(PostForm.apply)(PostForm.unapply)
  )
}
