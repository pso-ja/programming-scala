// code-examples/AppDesign/abstractions/observer3-wont-compile.scala
// WON'T COMPILE

package observer

abstract class SubjectObserver {
  type S <: Subject
  type O <: Observer
  
  trait Subject {
    private var observers = List[O]()
    def addObserver(observer: O) = observers ::= observer
    def notifyObservers = observers foreach (_.receiveUpdate(this)) // ERROR
  }
  
  trait Observer {
    def receiveUpdate(subject: S)
  }
}
