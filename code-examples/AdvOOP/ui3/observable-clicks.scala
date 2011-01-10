// code-examples/AdvOOP/ui3/observable-clicks.scala
package ui3

import observer._

trait ObservableClicks extends Clickable with Subject {
    abstract override def click() = { 
        super.click()
        notifyObservers
    }
}
