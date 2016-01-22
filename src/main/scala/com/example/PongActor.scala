package com.example

import akka.actor.{Actor, ActorLogging, Props}

class PongActor extends Actor with ActorLogging {
  import PongActor._

  def receive = {
  	case PingActor.PingMessage(text) => 
  	  log.info("In PongActor - received message: {}", text)
      Thread.sleep(1300)
  	  sender() ! PongMessage("pong")
  }	
}

object PongActor {
  val props = Props[PongActor]
  case class PongMessage(text: String)
}
