// code-examples/TypeLessDoMore/method-overloaded-return-script.scala
// Version 1 of "StringUtil" (with a compilation error).
// ERROR: Won't compile: needs a String return type on the second "joiner".

object StringUtil {
  def joiner(strings: List[String], separator: String): String = 
    strings.mkString(separator)

  def joiner(strings: List[String]) = joiner(strings, " ")   // ERROR
}
import StringUtil._  // Import the joiner methods.

println( joiner(List("Programming", "Scala")) )