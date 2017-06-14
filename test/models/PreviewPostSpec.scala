package models

import org.specs2.mutable._
import org.joda.time.DateTime
/**
 * Created by tam_ht on 3/6/17.
 */
class PreviewPostSpec extends Specification {
  val post1 = Post(1, "Sample title 1",
    """Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam sit amet semper lorem, vel accumsan lorem. In
      |ullamcorper nisl sit amet ligula accumsan, consequat posuere orci blandit. Nam ut lectus aliquam, convallis est
      |et, aliquam leo. Donec porttitor purus ut dui faucibus, in tincidunt orci rhoncus. Nunc fringilla nunc sed
      |gravida cursus. Duis sodales semper tortor, at auctor enim euismod lacinia. Ut dapibus odio massa, ac consequat
      |eu consectetur nulla. Etiam finibus elementum tincidunt.""".stripMargin.replaceAll("\n", " "),
    new DateTime(2017, 1, 15, 0, 0, 0, 0), 1)
  val post2 = Post(2, "Sample title 2", "Sample content.", new DateTime(2017, 12, 14, 4, 5, 0, 0), 1)

  "Content of a preview post" >> {
    "have the same title with corresponding post" >> {
      val previewPost = PreviewPost(post2)
      previewPost.title mustEqual post2.title
    }

    "is subistring(0, index of first space from 400th character) of corresponding post" >> {
      val contentOfPreviewPost1 =
        """Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam sit amet semper lorem, vel accumsan lorem. In
          |ullamcorper nisl sit amet ligula accumsan, consequat posuere orci blandit. Nam ut lectus aliquam, convallis
          |est et, aliquam leo. Donec porttitor purus ut dui faucibus, in tincidunt orci rhoncus. Nunc fringilla nunc
          |sed gravida cursus. Duis sodales semper tortor, at auctor enim euismod lacinia.""".stripMargin.replaceAll("\n", " ")
      PreviewPost(post1).content mustEqual contentOfPreviewPost1
      PreviewPost(post2).content mustEqual post2.content
    }
  }

  "CreatedAt of a preview post" >> {
    "is formatted" >> {
      val previewPost = PreviewPost(post2)
      previewPost.createAt mustEqual "14-12-2017 04:05"
    }
  }
}
