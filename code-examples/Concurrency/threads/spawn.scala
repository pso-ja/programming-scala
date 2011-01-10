// code-examples/Concurrency/threads/spawn.scala

import scala.concurrent.ops._

object SpawnExample {
  def main(args: Array[String]) {
    println("this will run synchronously")

    spawn {
      println("this will run asychronously")
    }
  }
}

