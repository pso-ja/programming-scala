// code-examples/AdvOOP/overrides/final-class-wont-compile.scala
// WON'T COMPILE.

final class Fixed {
  def doSomething = "Fixed did something!"
}

class Changeable1 extends Fixed     // ERROR
