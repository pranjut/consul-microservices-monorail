package services

import com.google.inject.{Inject, Singleton}
import com.knoldus.servicediscovery.ConsulDiscovery
import play.api.libs.ws.WS

import play.api.Play.current
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@Singleton
class PriceCordination @Inject()(consulDiscovery: ConsulDiscovery) {

  def compose: Future[String] = {
    consulDiscovery.getServicerAddress(8500, "myService2").flatMap { service2Addr =>
      WS.url(s"http://$service2Addr/price").get().flatMap {
        response =>
          consulDiscovery.getServicerAddress(8500, "myService3").flatMap {
            serviceAddr3 =>
              WS.url(s"http://$serviceAddr3/sendMail").post(response.body).map {
                response1 => response1.body
              }
          }
      }
    }
  }
}