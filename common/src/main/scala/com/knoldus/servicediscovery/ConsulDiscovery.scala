package com.knoldus.servicediscovery

import java.net.{Inet4Address, InetAddress}

import com.google.inject.Singleton
import consul.Consul
import consul.v1.agent.service.LocalService
import consul.v1.common.Types._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.control.NonFatal

@Singleton
class ConsulDiscovery {


  val inetAddr = InetAddress.getByName("127.0.0.1") match {
    case i4: Inet4Address => i4
    case _ => throw new Exception
  }

  def registerService(myserviceId: String = "myServiceId",consulPort: Int = 8500, myServicePort: Int = 9000) = {
    val myConsul = new Consul(inetAddr, consulPort)
    import myConsul.v1._
    val myServiceCheck = agent.service.httpCheck(s"http://localhost:$myServicePort/health","15s")
    val myService: LocalService = agent.service.LocalService(ServiceId(myserviceId), ServiceType("myTypeOfService"), Set(ServiceTag("MyTag")), Some(myServicePort) , Some(myServiceCheck))
    agent.service.register(myService).recover {
      case NonFatal(ex) =>
        ex.printStackTrace()
        false
    }
  }

  def getServicerAddress(consulPort: Int, myserviceId: String = "myServiceId"): Future[String] = {
    val myConsul = new Consul(inetAddr, consulPort)
    import myConsul.v1._
    catalog.service(ServiceType("myTypeOfService"), Some(ServiceTag("MyTag")), Some(DatacenterId("dc1"))).map {
      services =>
        services.filter(service => service.ServiceID.value == myserviceId && service.ServicePort != 0).map {
        service =>
          s"${service.Address}:${service.ServicePort}"
      }.headOption
    } map{
      addrOpt => addrOpt match {
        case Some(addr) => addr
        case None => throw new NoSuchElementException
      }
    }
  }


}
