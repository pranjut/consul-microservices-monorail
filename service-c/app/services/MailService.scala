package services

import com.google.inject.Singleton
import play.api.libs.json.JsString

import scala.concurrent.Future

@Singleton
class MailService{

  def send: Future[JsString] = {

    Future.successful(JsString("email sent"))
  }

}
