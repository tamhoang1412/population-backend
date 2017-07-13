package dto

import play.api.libs.json._

object JSONResponse {
  def convert(code: Int, message: String, data: JsValue): JsValue = {
    val responseSeq = Seq(
      "code" -> JsNumber(code),
      "message" -> JsString(message),
      "data" -> data
    )
    JsObject(responseSeq)
  }
}
