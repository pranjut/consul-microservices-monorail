package controllers

import com.google.inject.Inject
import com.knoldus.servicediscovery.ConsulDiscovery
import play.api._
import play.api.mvc._
import services.MailService
import scala.concurrent.ExecutionContext.Implicits.global

class Application @Inject()(mailService: MailService, consulDisco: ConsulDiscovery) extends Controller {

  def index = Action.async {
    consulDisco.registerService("myService3", 6500, 9002).map { data =>
      Ok(s"the registration was = $data")
    }
  }

  def sendMail = Action.async {
    request =>
      mailService.send.map { msg =>
        Ok(msg)
      }
  }

  def health = Action {
    request =>
      println(s"..................... ${request.body}")
      Ok
  }

}
