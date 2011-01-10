// code-examples/TypeLessDoMore/string-util.scala

object StringUtil {
  def joiner(strings: List[String]) = strings.mkString(" ")
  def joiner(strings: String*):String = joiner(strings.toList)
  
  def toCollection(string: String) = string.split(' ')
}
