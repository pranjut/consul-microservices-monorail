package services

import com.google.inject.Singleton
import play.api.libs.json.JsString

import scala.concurrent.Future

@Singleton
class PricingService {
  val topic = "demo_topic1"
  val groupId = "group-1"

  def getPrice: Future[JsString] ={
    Future.successful(JsString("price processing started"))
  }
}
