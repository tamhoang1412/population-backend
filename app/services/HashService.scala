package services
import java.security.MessageDigest

trait HashService {
  def hash(string: String): String
}

class MD5HashService extends HashService {
  override def hash(string: String): String = {
    MessageDigest.getInstance("MD5").digest(string.getBytes()).map(0xFF & _).map { "%02x".format(_) }.foldLeft("") { _ + _ }
  }
}
