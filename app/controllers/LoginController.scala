package controllers

import javax.inject.Inject

import dto.{ JSONResponse, JSONUser }
import models.{ User, UserAccessToken }
import utils.Constants.ResponseCode
import play.api.libs.json._
import play.api.mvc._
import play.api.libs.ws._
import services.UserAccessTokenService
import utils.Constants.OAUTH2_GOOGLE_API_URL

import scala.concurrent.ExecutionContext.Implicits.global

class LoginController @Inject() (
    userAccessTokenService: UserAccessTokenService,
    ws: WSClient
) extends Controller {

  def loginWithGoogleToken: EssentialAction = Action.async { implicit request =>
    val token = (request.body.asJson.get \ "token").as[String]
    ws.url(OAUTH2_GOOGLE_API_URL + token).get.map {
      userProfile =>
        if (userProfile.status == 200) {
          val userProfileJson = Json.parse(userProfile.body)
          val email = (userProfileJson \ "email").as[String]
          val (user, accessToken) = User.getUserByEmail(email) match {
            case None => {
              User.create(userProfileJson)
              val user = User.getUserByEmail(email).get
              (user, userAccessTokenService.createAccessToken(user))
            }
            case Some(user) => (user, userAccessTokenService.createAccessToken(user))
          }
          Ok(JSONResponse.convert(ResponseCode.SUCCESS, "success", JSONUser.convert(user, accessToken)))
        } else {
          Ok(JSONResponse.convert(ResponseCode.FORBIDDEN, "error", JsNull))
        }
    }
  }

  def loginWithAccessToken(token: String): EssentialAction = Action { implicit request =>
    UserAccessToken.getAccessToken(token) match {
      case None => {
        Ok(JSONResponse.convert(ResponseCode.BAD_REQUEST, "Access token is not exist.", JsNull))
      }
      case Some(userAccessToken) => {
        val user = User.findById(userAccessToken.userId).get
        Ok(JSONResponse.convert(ResponseCode.SUCCESS, "success", JSONUser.convert(user, userAccessToken.accessToken)))
      }
    }
  }

  def logout(token: String): EssentialAction = Action {
    UserAccessToken.getAccessToken(token) match {
      case None => {
        Ok(JSONResponse.convert(ResponseCode.BAD_REQUEST, "Access token is not exist.", JsNull))
      }
      case Some(userAccessToken) => {
        UserAccessToken.expireAccessToken(userAccessToken.id)
        Ok(JSONResponse.convert(ResponseCode.SUCCESS, "Logout successfully", JsNull))
      }
    }
  }
}
