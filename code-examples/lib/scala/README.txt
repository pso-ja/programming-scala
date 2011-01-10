This directory and the scala-library.jar it contains is used only for a few special
builds, where we run "javac" with the scala library jar on the classpath. We don't 
want to put something like $SCALA_HOME/lib/... in the sake.scala file, if we can
avoid requiring the user to define $SCALA_HOME...

