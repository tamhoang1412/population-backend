package controllers

import models.User
import org.mindrot.jbcrypt.BCrypt
import org.specs2.mutable.Specification
import org.specs2.specification.BeforeAfterAll
import play.api.Application
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.libs.json.Json
import play.api.mvc.Results
import play.api.test.{ FakeRequest, WithApplication }
import play.api.test.Helpers._
import scalikejdbc.config.DBs
import skinny.test.FactoryGirl

/**
 * Created by tam_ht on 3/10/17.
 */
class AuthControllerSpec extends Specification with Results with BeforeAfterAll {
  def beforeAll(): Unit = {
    DBs.setupAll()
    FactoryGirl(User).create('username -> "bbs", 'password -> BCrypt.hashpw("bbs", BCrypt.gensalt()))
  }

  def afterAll(): Unit = {
    DBs.setupAll()
    User.deleteAll()
  }

  "AuthController" >> {
    "login() return to Login page" >> {
      val application: Application = GuiceApplicationBuilder().build()
      val request = FakeRequest(GET, routes.AuthController.login.url)
      val Some(response) = route(application, request)
      status(response) must be_==(OK)
      contentAsString(response) must contain("Login")
    }

    "logout() return to Login page" >> {
      val application: Application = GuiceApplicationBuilder().build()
      val request = FakeRequest(GET, routes.AuthController.logout.url)
      val Some(response) = route(application, request)
      status(response) must be_==(SEE_OTHER)
    }

    "authenticate()" >> {
      "success req should return Homepage" >> new WithApplication {
        val application: Application = GuiceApplicationBuilder().build()
        val request = FakeRequest(POST, routes.AuthController.login.url).withJsonBody(
          Json.obj("username" -> "bbs", "password" -> "bbs")
        )
        val Some(response) = route(application, request)
        status(response) must be_==(SEE_OTHER)
      }

      "fail req should return Login page and show fail cause" >> {
        "no username" >> {
          val application: Application = GuiceApplicationBuilder().build()
          val request = FakeRequest(POST, routes.AuthController.login.url).withJsonBody(
            Json.obj("username" -> "", "password" -> "bbs")
          )
          val Some(response) = route(application, request)
          contentAsString(response) must contain("Login")
          contentAsString(response) must contain("This field is required")
        }

        "no password" >> {
          val application: Application = GuiceApplicationBuilder().build()
          val request = FakeRequest(POST, routes.AuthController.login.url).withJsonBody(
            Json.obj("username" -> "bbs", "password" -> "")
          )
          val Some(response) = route(application, request)
          contentAsString(response) must contain("Login")
          contentAsString(response) must contain("This field is required")
        }

        "wrong username" >> {
          val application: Application = GuiceApplicationBuilder().build()
          val request = FakeRequest(POST, routes.AuthController.login.url).withJsonBody(
            Json.obj("username" -> "wrongUsername", "password" -> "bbs")
          )
          val Some(response) = route(application, request)
          contentAsString(response) must contain("Login")
          contentAsString(response) must contain("Invalid username or password")
        }

        "wrong password" >> {
          val application: Application = GuiceApplicationBuilder().build()
          val request = FakeRequest(POST, routes.AuthController.login.url).withJsonBody(
            Json.obj("username" -> "bbs", "password" -> "wrongPassword")
          )
          val Some(response) = route(application, request)
          contentAsString(response) must contain("Login")
          contentAsString(response) must contain("Invalid username or password")
        }
      }
    }
  }
}
