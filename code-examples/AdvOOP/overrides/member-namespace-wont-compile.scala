// code-examples/AdvOOP/overrides/member-namespace-wont-compile.scala
// WON'T COMPILE

class IllegalMemberNameUse {
  def member(i: Int) = 2 * i
  val member = 2         // ERROR
  object member {        // ERROR 
    def apply() = 2
  }
}