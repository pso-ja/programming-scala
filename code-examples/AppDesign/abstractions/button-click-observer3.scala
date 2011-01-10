// code-examples/AppDesign/abstractions/button-click-observer3.scala

package ui
import observer._

class ButtonClickObserver extends ButtonSubjectObserver.ButtonObserver {
  val clicks = new scala.collection.mutable.HashMap[String,Int]()
  
  def receiveUpdate(button: ButtonSubjectObserver.ObservableButton) = {
    val count = clicks.getOrElse(button.label, 0) + 1
    clicks.update(button.label, count)
  }
}