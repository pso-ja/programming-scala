// code-examples/Concurrency/smtpd/src/test/scala/com/programmingscala/smtpd/SmtpDecoderSpec.scala

package com.programmingscala.smtpd

import java.nio.ByteOrder
import net.lag.naggati._
import org.apache.mina.core.buffer.IoBuffer
import org.apache.mina.core.filterchain.IoFilter
import org.apache.mina.core.session.{DummySession, IoSession}
import org.apache.mina.filter.codec._
import org.specs._
import scala.collection.{immutable, mutable}

object SmtpDecoderSpec extends Specification {
  private var fakeSession: IoSession = null
  private var fakeDecoderOutput: ProtocolDecoderOutput = null
  private var written = new mutable.ListBuffer[Request]

  def quickDecode(s: String): Unit = {
    Codec.decoder.decode(fakeSession, IoBuffer.wrap(s.getBytes), fakeDecoderOutput)
  }

  "SmtpRequestDecoder" should {
    doBefore {
      written.clear()
      fakeSession = new DummySession
      fakeDecoderOutput = new ProtocolDecoderOutput {
        override def flush(nextFilter: IoFilter.NextFilter, s: IoSession) = {}
        override def write(obj: AnyRef) = written += obj.asInstanceOf[Request]
      }
    }

    "parse HELO" in {
      quickDecode("HELO client.example.org\n")
      written.size mustEqual 1
      written(0).command mustEqual "HELO"
      written(0).data mustEqual "client.example.org"
    }

    "parse QUIT" in {
      quickDecode("QUIT\n")
      written.size mustEqual 1
      written(0).command mustEqual "QUIT"
      written(0).data mustEqual null
    }
  }
}