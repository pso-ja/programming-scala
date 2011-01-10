// code-examples/AdvOOP/objects/button.scala

package objects
import ui3.Clickable

class Button(val label: String) extends Widget with Clickable {
  
  def click() = {
    // Logic to give the appearance of clicking a button...
  }
  
  def draw() = {
    // Logic to draw the button on the display, web page, etc.
  }
  
  override def toString() = "(button: label="+label+", "+super.toString()+")"
}

object Button {
  def unapply(button: Button) = Some(button.label)
}