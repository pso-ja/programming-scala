// code-examples/TypeSystem/typepaths/type-path-wont-compile.scala
// ERROR: Won't compile

trait Service {
  trait Logger {
    def log(message: String): Unit
  }
  val logger: Logger
  
  def run = {
    logger.log("Starting " + getClass.getSimpleName + ":")
    doRun
  }
  
  protected def doRun: Boolean
}

object MyService1 extends Service {
  class MyService1Logger extends Logger {
    def log(message: String) = println("1: "+message)
  }
  override val logger = new MyService1Logger
  def doRun = true  // do some real work...
}

object MyService2 extends Service {
  override val logger = MyService1.logger  // ERROR
  def doRun = true  // do some real work...
}
