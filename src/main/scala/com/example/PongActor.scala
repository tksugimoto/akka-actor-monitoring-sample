package com.example

import akka.actor.{Actor, ActorLogging, Props}

class PongActor extends Actor with ActorLogging {
  import PongActor._

  override def preStart(): Unit = {
    log.info("Created PongActor!!" + self.path)
  }

  def receive = {
  	case PingActor.PingMessage(text) => 
  	  //log.info("In PongActor - received message: {}", text)
      Thread.sleep(100)
  	  sender() ! PongMessage("pong")
  }
}

object PongActor {
  val props = Props[PongActor]
  case class PongMessage(text: String)
}
