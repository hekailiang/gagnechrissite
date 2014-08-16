package com.gagnechris.backend

import akka.actor.{ActorSystem, Props}
import akka.actor.ActorDSL.{actor, Act}
import akka.event.Logging
import akka.io.IO
import akka.io.Tcp.{Bound, CommandFailed}

import spray.can.Http


object Main {
  implicit val system = ActorSystem("backend-actor-system")
  def main(args: Array[String]): Unit = {
    val app = new GagneChrisService()
    app.run(args)
  }
}

class GagneChrisService(implicit system: ActorSystem) {
  import com.gagnechris.backend.service.BackendServiceActor
  import BackendConfig._

  def run(args: Array[String]): Unit = {

    val log = Logging(system, getClass)

    val callbackActor = actor(new Act {
      become {
        case b @ Bound(connection) => log.info(b.toString)
        case cf @ CommandFailed(command) => log.error(cf.toString)
        case all => log.debug("Backend Service Received a message from Akka.IO: " + all.toString)
      }
    })

    /* Spray Service */
    val rootActor = system.actorOf(Props(classOf[BackendServiceActor]), "backend-actor-system")
    IO(Http).tell(Http.Bind(rootActor, HttpConfig.Interface, HttpConfig.Port), callbackActor)

    log.info("Backend Service Ready")
  }
}
