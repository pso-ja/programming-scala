// code-examples/Traits/ui2/vetoable-clicks.scala

package ui2
import observer._

trait VetoableClicks extends Clickable {
  val maxAllowed = 1  // default
  private var count = 0

  abstract override def click() = {
    if (count < maxAllowed) {
      count += 1
      super.click()
    }
  }
}
