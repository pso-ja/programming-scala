// code-examples/AppDesign/abstractions/vetoable-clicks2-script.scala

trait Clickable { 
  private var clicks = 0
  def count = clicks

  def click() = { clicks += 1 }
}

class Widget
class Button(val label: String) extends Widget with Clickable {
  override def click() = {
    super.click()
    println("click!")
  }
}

trait VetoableClicks extends Clickable {
  val maxAllowed = 1
  abstract override def click() = {
    if (count < maxAllowed)
      super.click()
  }
}

val button1 = new Button("click me!")
println("new Button(...)")
for (i <- 1 to 3 ) button1.click()

val button2 = new Button("click me!") with VetoableClicks
println("new Button(...) with VetoableClicks")
for (i <- 1 to 3 ) button2.click()
