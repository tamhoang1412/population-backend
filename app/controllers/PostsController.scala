package controllers

/**
 * Created by tam_ht on 3/2/17.
 */

import javax.inject._

import models._
import org.joda.time.DateTime
import play.api.mvc._
import play.api.i18n.I18nSupport
import play.api.i18n.MessagesApi

class PostsController @Inject() (val messagesApi: MessagesApi) extends Controller with I18nSupport with Secured {
  def index: EssentialAction = Action { implicit req =>
    val previewPosts: List[PreviewPost] = Post.findAll().map(PreviewPost.apply)
    val message: String = if (previewPosts.length == 0) "There are no posts to show right now." else ""
    Ok(views.html.posts.index(req.session.get("username"), message, previewPosts))
  }

  def viewPost(id: Long): EssentialAction = Action { implicit req =>
    Post.findById(id) match {
      case None       => Ok(views.html.shared.notFound(req.session.get("username")))
      case Some(post) => Ok(views.html.posts.post(req.session.get("username"), PostDetail(post, User.findById(post.authorId).get.username)))
    }
  }

  def create: EssentialAction = needAuthenticated { username: String => req =>
    Ok(views.html.posts.create(Some(username), PostForm.form))
  }

  def createNewPost: EssentialAction = needAuthenticated { username: String => implicit req =>
    PostForm.form.bindFromRequest.fold(
      formWithErrors => {
        BadRequest(views.html.posts.create(Some(username), formWithErrors))
      },
      form => {
        val post = Post.createWithAttributes('title -> form.title, 'content -> form.content, 'createdAt -> DateTime.now, 'authorId -> userId(req))
        Redirect(routes.PostsController.viewPost(post))
      }
    )
  }
}
