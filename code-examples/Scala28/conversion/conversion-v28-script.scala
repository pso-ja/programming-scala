// code-examples/Scala28/conversion/conversions-v28-script.scala
import scala.collection.JavaConversions._

var scalaMap = scala.collection.mutable.Map("Sandman" -> "In a Silent Way", 
                                            "Gyro" -> "Scan", 
                                            "Pocoloco" -> "Buckethead" )
var javaMap:java.util.Map[String, String] = scalaMap
