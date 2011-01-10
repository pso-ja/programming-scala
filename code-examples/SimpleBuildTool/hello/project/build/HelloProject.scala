import sbt._ 
class HelloProject(info: ProjectInfo) extends DefaultProject(info) {
  val repoScala = "Snapshot Repository " at "http://www.scala-tools.org/repo-snapshots/"
  val scalaTest = "org.scalatest" % "scalatest" % "1.2-for-scala-2.8.0.final-SNAPSHOT" % "test"
}