import sbt._
 
class TwisampleProject(info: ProjectInfo) extends DefaultProject(info) {
  import BasicScalaProject._
    val repoJava = "Java.net Repository" at "http://download.java.net/maven/2/"
    val repoScala = "Snapshot Repository " at "http://www.scala-tools.org/repo-snapshots/"

    val httpclient = "org.apache.httpcomponents" % "httpclient" % "4.0.1" 
    val scalaTest = "org.scalatest" % "scalatest" % "1.2-for-scala-2.8.0.final-SNAPSHOT" % "test"

  override def javaCompileOptions = super.javaCompileOptions ++ 
                               Seq("-source", "1.5","-encoding", "utf8").map(x => JavaCompileOption(x))

}