// code-examples/Concurrency/smtpd/src/main/scala/com/programmingscala/smtpd/SmtpHandler.scala

package com.programmingscala.smtpd

import net.lag.naggati.{IoHandlerActorAdapter, MinaMessage, ProtocolError}
import org.apache.mina.core.buffer.IoBuffer
import org.apache.mina.core.session.{IdleStatus, IoSession}
import java.io.IOException
import scala.actors.Actor
import scala.actors.Actor._
import scala.collection.{immutable, mutable}

class SmtpHandler(val session: IoSession) extends Actor {
  start

  def act = {
    loop {
      react {
        case MinaMessage.MessageReceived(msg) => 
            handle(msg.asInstanceOf[smtpd.Request])
        case MinaMessage.SessionClosed => exit()
        case MinaMessage.SessionIdle(status) => session.close
        case MinaMessage.SessionOpened => reply("220 localhost Tapir SMTPd 0.1\n")

        case MinaMessage.ExceptionCaught(cause) => {
          cause.getCause match {
            case e: ProtocolError => reply("502 Error: " + e.getMessage + "\n")
            case i: IOException   => reply("502 Error: " + i.getMessage + "\n")
            case _                => reply("502 Error unknown\n")
          }
          session.close
        }
      }
    }
  }

  private def handle(request: smtpd.Request) = {
    request.command match {
      case "HELO" => reply("250 Hi there " + request.data + "\n")
      case "QUIT" => reply("221 Peace out girl scout\n"); session.close
    }
  }

  private def reply(s: String) = {
    session.write(new smtpd.Response(IoBuffer.wrap(s.getBytes)))
  }

}

