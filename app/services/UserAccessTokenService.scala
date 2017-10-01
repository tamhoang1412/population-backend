package services

import javax.inject.Inject

import models.{ User, UserAccessToken }
import org.joda.time.DateTime

trait UserAccessTokenService {
  def createAccessToken(user: User): String
}

class UserAccessTokenSkinnyService @Inject() (
    hashService: HashService
) extends UserAccessTokenService {
  override def createAccessToken(user: User): String = {
    val token = hashService.hash(user.email + DateTime.now.toString)
    UserAccessToken.createWithAttributes(
      'userId -> user.id,
      'accessToken -> token,
      'createdTime -> DateTime.now()
    )
    token
  }
}
