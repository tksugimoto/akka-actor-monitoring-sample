package com.example

import akka.actor.{ActorLogging, Terminated, Props, Actor}
import akka.routing.{RoundRobinRoutingLogic, Router, ActorRefRoutee}

/**
 * Created by Takashi Sugimoto on 2016/01/27.
 */
class PongRouter extends Actor with ActorLogging {
  import PongRouter._

  override def preStart(): Unit = {
    log.info("Created PongRouter!!" + self.path)
  }
  
  var router = {
    val routees = Vector.fill(1) {
      val r = context.actorOf(Props[PongActor])
      context watch r
      ActorRefRoutee(r)
    }
    Router(RoundRobinRoutingLogic(), routees)
  }

  def receive = {
    case w: PingActor.PingMessage =>
      router.route(w, sender())
    case Terminated(a) =>
      log.info("Terminated PongRouter: " + a.path)
      router = router.removeRoutee(a)
      self ! Create
    case Create =>
      val r = context.actorOf(Props[PongActor])
      context watch r
      router = router.addRoutee(r)
  }
}

object PongRouter {
  val props = Props[PongRouter]
  case object Create
}