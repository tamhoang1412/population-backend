package utils

object Constants {
  object ResponseCode {
    val SUCCESS = 200
    val BAD_REQUEST = 400
    val NOT_FOUND = 404
    val FORBIDDEN = 403
  }

  var OAUTH2_GOOGLE_API_URL = "https://www.googleapis.com/oauth2/v3/tokeninfo?id_token="
}
