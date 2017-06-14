package controllers

import play.api.test._
import play.api.test.Helpers._
import org.specs2.mutable._
import models.{ Post, User }
import org.specs2.specification.BeforeAfterAll
import scalikejdbc.config.DBs
import play.api.Application
import play.api.inject.guice.GuiceApplicationBuilder
import skinny.test._
import org.joda.time.DateTime
import org.mindrot.jbcrypt.BCrypt
import play.api.libs.json.Json

/**
 * Created by tam_ht on 3/3/17.
 */

class PostsControllerSpec extends Specification with BeforeAfterAll {
  def beforeAll(): Unit = {
    DBs.setupAll()
    FactoryGirl(User).create('username -> "bbs", 'password -> BCrypt.hashpw("bbs", BCrypt.gensalt()))
  }

  def afterAll(): Unit = {
    DBs.setupAll()
    Post.deleteAll()
    User.deleteAll()
  }

  "PostsController" >> {
    "index()" >> {
      "show all post " >> {
        val application: Application = GuiceApplicationBuilder().build()
        val request = FakeRequest(GET, routes.PostsController.index().url)
        val Some(response) = route(application, request)
        status(response) must be_==(OK)
        contentAsString(response) must contain("All posts")
      }

      "notify when there is no posts" >> {
        val application: Application = GuiceApplicationBuilder().build()
        val request = FakeRequest(GET, routes.PostsController.index().url)
        val Some(response) = route(application, request)
        contentAsString(response) must contain("There are no posts to show right now.")
      }
    }

    "viewPost(id)" >> {
      "show post whose id equals id" >> {
        val user = User.findAll().head
        FactoryGirl(Post).create('title -> "Sample title", 'content -> "Sample content", 'createdAt -> DateTime.now, 'authorId -> user.id)
        val post = Post.findAll().head
        val application: Application = GuiceApplicationBuilder().build()
        val request = FakeRequest(GET, routes.PostsController.viewPost(post.id).url)
        val Some(response) = route(application, request)
        contentAsString(response) must contain("Sample title")
      }

      """return "Page not found" page when id is not exist""" >> {
        val application: Application = GuiceApplicationBuilder().build()
        val request = FakeRequest(GET, routes.PostsController.viewPost(0).url)
        val Some(response) = route(application, request)
        contentAsString(response) must contain("Page not found.")
      }

    }

    "create()" >> {
      "show login page if user have not logged in" >> new WithApplication {
        val application: Application = GuiceApplicationBuilder().build()
        val request = FakeRequest(GET, routes.PostsController.create().url)
        val Some(response) = route(application, request)
        status(response) must be_==(SEE_OTHER)
        redirectLocation(response).toString must contain("login")
      }

      "show create page" >> new WithApplication {
        val application: Application = GuiceApplicationBuilder().build()
        val request = FakeRequest(GET, routes.PostsController.create().url).withSession("username" -> "bbs")
        val Some(response) = route(application, request)
        contentAsString(response) must contain("form")
        contentAsString(response) must contain("Create new post")
      }
    }

    "createNewPost()" >> {
      "show login page if user have not logged in" >> new WithApplication {
        val application: Application = GuiceApplicationBuilder().build()
        val request = FakeRequest(POST, routes.PostsController.createNewPost().url).withJsonBody(
          Json.obj("title" -> "Sample title", "content" -> "Sample content")
        )
        val Some(response) = route(application, request)
        status(response) must be_==(SEE_OTHER)
        redirectLocation(response).toString must contain("login")

      }

      "create a new post success" >> new WithApplication {
        val application: Application = GuiceApplicationBuilder().build()
        val request = FakeRequest(POST, routes.PostsController.createNewPost().url).withJsonBody(
          Json.obj("title" -> "Sample title", "content" -> "Sample content")
        ).withSession("username" -> "bbs")
        val Some(response) = route(application, request)
        status(response) must be_==(SEE_OTHER)
        redirectLocation(response).toString must contain("post")
      }

      "Title is empty, display error " >> new WithApplication {
        val application: Application = GuiceApplicationBuilder().build()
        val request = FakeRequest(POST, routes.PostsController.createNewPost().url).withJsonBody(
          Json.obj("title" -> "", "content" -> "Sample content")
        ).withSession("username" -> "bbs")
        val Some(response) = route(application, request)
        contentAsString(response) must contain("This field is required")
      }

      "Content is empty, display error " >> new WithApplication {
        val application: Application = GuiceApplicationBuilder().build()
        val request = FakeRequest(POST, routes.PostsController.createNewPost().url).withJsonBody(
          Json.obj("title" -> "Sample title", "content" -> "")
        ).withSession("username" -> "bbs")
        val Some(response) = route(application, request)
        contentAsString(response) must contain("This field is required")
      }
    }
  }
}
