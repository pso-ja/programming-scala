// code-examples/BasicOOP/type-erasure-wont-compile.scala
// WON'T COMPILE

object Foo {
  def bar(list: List[String]) = list.toString
  def bar(list: List[Int]) = list.size.toString
}