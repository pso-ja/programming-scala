// code-examples/TypeLessDoMore/string-util-v3.scala
// Version 3 of "StringUtil" (for all versions of Scala).

object StringUtil {
  def joiner(strings: List[String], separator: String): String = 
    strings.mkString(separator)
      
  def joiner(strings: List[String]): String = strings.mkString(" ")
  
  def toCollection(string: String) = string.split(' ')
}
