package models

/**
 * Created by tam_ht on 3/10/17.
 */
import org.mindrot.jbcrypt.BCrypt
import org.specs2.mutable._
import org.specs2.specification.BeforeAfterAll
import scalikejdbc.config.DBs
import skinny.test.FactoryGirl

class LoginFormSpec extends Specification with BeforeAfterAll {
  def beforeAll(): Unit = {
    DBs.setupAll()
    FactoryGirl(User).create('username -> "bbs", 'password -> BCrypt.hashpw("bbs", BCrypt.gensalt()))
  }

  def afterAll(): Unit = {
    DBs.setupAll()
    User.deleteAll()
  }

  "LoginForm" >> {
    "doUserExist()" >> {
      "return true if user exits" >> {
        LoginForm.doesUserExist("bbs", "bbs") mustEqual true
      }

      "return false if user does not exits" >> {
        LoginForm.doesUserExist("wrongUsername", "bbs") mustEqual false
        LoginForm.doesUserExist("bbs", "wrongPassword") mustEqual false
      }
    }
  }
}
