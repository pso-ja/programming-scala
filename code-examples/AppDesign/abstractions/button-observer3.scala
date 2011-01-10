// code-examples/AppDesign/abstractions/button-observer3.scala

package ui
import observer._

object ButtonSubjectObserver extends SubjectObserver {
  type S = ObservableButton
  type O = ButtonObserver
  
  class ObservableButton(name: String) extends Button(name) with Subject {
    override def click() = {
      super.click()
      notifyObservers
    }
  }
  
  trait ButtonObserver extends Observer {
    def receiveUpdate(button: ObservableButton)
  }
}