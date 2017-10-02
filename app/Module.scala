import com.google.inject.AbstractModule
import services.{ HashService, MD5HashService, UserAccessTokenService, UserAccessTokenSkinnyService }

class Module extends AbstractModule {

  override def configure() = {
    bind(classOf[HashService]).to(classOf[MD5HashService])
    bind(classOf[UserAccessTokenService]).to(classOf[UserAccessTokenSkinnyService])
  }

}
