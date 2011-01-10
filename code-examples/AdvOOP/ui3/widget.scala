// code-examples/AdvOOP/ui3/widget.scala

package ui3

abstract class Widget {
  def draw(): Unit
  override def toString() = "(widget)"
}

