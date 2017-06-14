package models

import org.joda.time.DateTime
import scalikejdbc._
import skinny.orm.SkinnyCRUDMapper

/**
 * Created by tam_ht on 3/1/17.
 */

case class Post(
  id: Long,
  title: String,
  content: String,
  createdAt: DateTime,
  authorId: Long
)

object Post extends SkinnyCRUDMapper[Post] {
  override val tableName = "posts"
  override val defaultAlias = createAlias("p")

  override def extract(rs: WrappedResultSet, n: scalikejdbc.ResultName[Post]): Post = Post(
    id = rs.long(n.id),
    title = rs.string(n.title),
    content = rs.string(n.content),
    createdAt = rs.jodaDateTime(n.createdAt),
    authorId = rs.long(n.authorId)
  )
}
