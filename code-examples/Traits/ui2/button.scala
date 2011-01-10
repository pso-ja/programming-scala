// code-examples/Traits/ui2/button.scala

package ui2

import ui.Widget

class Button(val label: String) extends Widget with Clickable {
  def click() = {
    // Logic to give the appearance of clicking a button...
  }
}