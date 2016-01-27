package com.example

import akka.actor.{Actor, ActorLogging, Props}

class PingActor extends Actor with ActorLogging {
  import PingActor._
  
  var counter = 0
	val pongRouter = context.actorOf(PongRouter.props, "pongRouter")

  def receive = {
  	case Initialize => 
	    log.info("In PingActor - starting ping-pong")
	    pongRouter ! PingMessage("ping")
  	case PongActor.PongMessage(text) =>
  	  //log.info("In PingActor - received message: {}", text)
  	  counter += 1
			if (counter % 100 == 0) {
				pongRouter ! PongRouter.Create
			}
  	  if (counter == 300000) {
  	    context.system.shutdown()
			} else if (counter % 5 == 0) {
				pongRouter ! PingMessage(s"ping-${counter}-1")
				pongRouter ! PingMessage(s"ping-${counter}-2")
  	  } else {
				pongRouter ! PingMessage(s"ping-${counter}")
  	  }
  }	
}

object PingActor {
  val props = Props[PingActor]
  case object Initialize
  case class PingMessage(text: String)
}