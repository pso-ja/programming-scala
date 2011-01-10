// code-examples/Traits/ui/button-count-observer.scala

package ui
import observer._

class ButtonCountObserver {
  var count = 0
  def receiveUpdate(subject: Any) = count += 1
}