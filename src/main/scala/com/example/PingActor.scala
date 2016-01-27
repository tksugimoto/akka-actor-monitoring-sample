package com.example

import akka.actor.{Actor, ActorLogging, Props}

class PingActor extends Actor with ActorLogging {
  import PingActor._
  
  var counter = 0
	val pongRouter = context.actorOf(PongRouter.props, "pongRouter")
	

  def receive = {
  	case Initialize => 
	    log.info("In PingActor - starting ping-pong")
			import scala.concurrent.duration._
			
			context.system.scheduler.schedule(initialDelay = 0 seconds, interval = 20 milliseconds) {
				counter += 1
				pongRouter ! PingMessage("ping", counter)
			} (context.system.dispatcher)
			
			// 60秒後から5秒おきに10回スケーリング
			(1 to 10) foreach { i =>
				context.system.scheduler.scheduleOnce(delay = 55 + 5 * i seconds) {
					pongRouter ! PongRouter.Create
				} (context.system.dispatcher)
			}
		case PongActor.PongMessage(text, index) =>
			log.info(s"In PingActor - received from ${sender().path.toString.split("/").last}: ${text}-${index}")
			// 作られすぎる
			// if (counter - index > 50) pongRouter ! PongRouter.Create
  }	
}

object PingActor {
  val props = Props[PingActor]
	case object Initialize
	case object Help
  case class PingMessage(text: String, index: Int)
}