// code-examples/TypeLessDoMore/string-util-client.scala

import StringUtil._

object StringUtilClient {
  def main(args: Array[String]) = {
    args foreach { s => toCollection(s).foreach { x => println(x) } } 
  }
}