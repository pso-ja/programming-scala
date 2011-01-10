// code-examples/AdvOOP/ui3/vetoable-clicks.scala

package ui3
import observer._

trait VetoableClicks extends Clickable {
  var maxAllowed = 1       // default
  private var count = 0
  abstract override def click() = {
    count += 1
    if (count <= maxAllowed)
      super.click()
  }
}
