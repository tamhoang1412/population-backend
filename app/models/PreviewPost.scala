package models

import org.joda.time.format.DateTimeFormat

/**
 * Created by tam_ht on 3/3/17.
 */

case class PreviewPost(private val post: Post) {
  def id: Long = post.id
  def title: String = post.title
  def content: String = {
    val minLength = 400
    def indexOfFirstSpaceFromMinLength = post.content.indexOf(' ', minLength)
    val splitPosition = if (indexOfFirstSpaceFromMinLength == -1) minLength else indexOfFirstSpaceFromMinLength
    post.content.take(splitPosition)
  }
  def createAt: String = DateTimeFormat.forPattern("dd-MM-yyyy HH:mm").print(post.createdAt)
  def author: String = User.findById(post.authorId).get.username
}
