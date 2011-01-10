// code-examples/Concurrency/smtpd/src/main/scala/com/programmingscala/smtpd/Codec.scala

package com.programmingscala.smtpd

import org.apache.mina.core.buffer.IoBuffer
import org.apache.mina.core.session.{IdleStatus, IoSession}
import org.apache.mina.filter.codec._
import net.lag.naggati._
import net.lag.naggati.Steps._

case class Request(command: String, data: String)
case class Response(data: IoBuffer)

object Codec {
  val encoder = new ProtocolEncoder {
    def encode(session: IoSession, message: AnyRef, out: ProtocolEncoderOutput) = {
      val buffer = message.asInstanceOf[Response].data
      out.write(buffer)
    }

    def dispose(session: IoSession): Unit = {
      // no-op, required by ProtocolEncoder trait
    }
  }

  val decoder = new Decoder(readLine(true, "ISO-8859-1") { line =>
    line.split(' ').first match {
      case "HELO" => state.out.write(Request("HELO", line.split(' ')(1))); End
      case "QUIT" => state.out.write(Request("QUIT", null)); End
      case _ => throw new ProtocolError("Malformed request line: " + line)
    }
  })
}