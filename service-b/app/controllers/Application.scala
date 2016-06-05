package controllers

import com.google.inject.Inject
import com.knoldus.servicediscovery.ConsulDiscovery
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.mvc._
import services.PricingService

class Application @Inject()(pricingService: PricingService, consulDisco: ConsulDiscovery) extends Controller {

  def index = Action.async {
    consulDisco.registerService("myService2", 7500, 9001).map { data =>
      Ok(s"the registration was = $data")
    }
  }

  def processPrice = Action.async {
    request =>
      pricingService.getPrice.map {
        msg =>
          Ok(msg)
      }
  }

  def health = Action {
    request =>
      println(s"..................... ${request.body}")
      Ok
  }

}
