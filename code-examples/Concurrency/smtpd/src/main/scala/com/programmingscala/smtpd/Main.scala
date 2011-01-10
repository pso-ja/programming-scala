// code-examples/Concurrency/smtpd/src/main/scala/com/programmingscala/smtpd/Main.scala

package com.programmingscala.smtpd

import net.lag.naggati.IoHandlerActorAdapter
import org.apache.mina.filter.codec.ProtocolCodecFilter
import org.apache.mina.transport.socket.SocketAcceptor
import org.apache.mina.transport.socket.nio.{NioProcessor, NioSocketAcceptor}
import java.net.InetSocketAddress
import java.util.concurrent.{Executors, ExecutorService}
import scala.actors.Actor._

object Main {
  val listenAddress = "0.0.0.0"
  val listenPort = 2525

  def setMaxThreads = {
    val maxThreads = (Runtime.getRuntime.availableProcessors * 2)
    System.setProperty("actors.maxPoolSize", maxThreads.toString)
  }

  def initializeAcceptor = {
    var acceptorExecutor = Executors.newCachedThreadPool()
    var acceptor = 
      new NioSocketAcceptor(acceptorExecutor, new NioProcessor(acceptorExecutor))
    acceptor.setBacklog(1000)
    acceptor.setReuseAddress(true)
    acceptor.getSessionConfig.setTcpNoDelay(true)
    acceptor.getFilterChain.addLast("codec", 
            new ProtocolCodecFilter(smtpd.Codec.encoder, smtpd.Codec.decoder))
    acceptor.setHandler(
            new IoHandlerActorAdapter(session => new SmtpHandler(session)))
    acceptor.bind(new InetSocketAddress(listenAddress, listenPort))
  }

  def main(args: Array[String]) {
    setMaxThreads
    initializeAcceptor
    println("smtpd: up and listening on " + listenAddress + ":" + listenPort)
  }
}
