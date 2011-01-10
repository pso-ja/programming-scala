// code-examples/ToolsLibs/sake.scala
import sake.Project._

// This chapter has examples that use the Spring Framework and AspectJ.
// The standard "sake" targets don't build or run the examples that require
// these libraries to be present. Instead, there are targets "spring" and
// "aspectj" that build and run these examples. They assume you have the
// environment variable $SPRING_HOME and/or $ASPECTJ_HOME defined in your
// shell environment. Furthermore, the AspectJ compiler, ajc, must be in 
// your path.

// Define some convenient variables.
val buildDir = "build/"
val libDir   = "../lib/"

// If true, don't actually run any commands.
environment.dryRun = false

// If true, show stack traces when a failure happens (doesn't affect some "errors").
showStackTracesOnFailures = false

// Logging level: Info, Notice, Warn, Error, Failure
log.threshold = Level.Info

environment.classpath :::= (files(libDir + "*.jar") -- files(libDir + "*src.jar"))
environment.classpath ::= buildDir

target('all -> List('clean, 'compile, 'spec, 'check, 'test, 'scripts))

target('spec) {
    specs(
        'classpath -> environment.classpath,
        'path -> "**/*.scala", 
        'pattern -> ".*Spec.*"
    )
}

target('check) {
    scala(
        'classpath -> environment.classpath,
        'opts -> "complex-check-script.scala")
}

target('test -> List('compile, 'dotest))

target('dotest) {
    scala(
        'classpath -> environment.classpath,
        'opts   -> "org.scalatest.tools.Runner -p build -o -s ComplexSuite")
    specs(
        'classpath -> environment.classpath,
        'path -> "**/*.scala", 
        'pattern -> ".*Spec.*"
    )
    sh("java -cp ../lib/scala/scala-library.jar:" + environment.classpath + " org.junit.runner.JUnitCore SMapTest SMapTestWithFunctions")
}

target('scripts) { 
  // Omits the following:
  //   object-bean-script:  Spring-specified; build the "spring" target instead.
  (files("**/*-script.scala") -- 
   files("**/object-bean-script.scala")).foreach { script =>
    scala(
        'classpath -> environment.classpath,
        'opts -> script 
    )
  }
}

target('compile -> List('clean, 'build_dir)) {
    sh("javac -Xlint:unchecked -d " + buildDir + " JStack.java")
    scalac(
        // Exclude this build script, demonstration scripts, some test scripts, and 
        // files that intentionally don't compile.
        'files     -> (files("**/*.scala") -- files("sake.scala") -- 
                       files("**/*-script.scala") -- files("**/*-wont-compile.scala")),
        'classpath -> environment.classpath,
        'd         -> buildDir,
        'opts      -> "-unchecked -deprecation"
    )
    sh("javac -Xlint:unchecked -d " + buildDir + " -cp ../lib/scala/scala-library.jar:" + environment.classpath + " SMapTest.java SMapTestWithFunctions.java")
}

target('spring -> 'compile) {
    val spring = environment.environmentVariables.get("SPRING_HOME")
    val cp = (spring + "/dist/spring.jar") :: (spring + "/lib/jakarta-commons/commons-logging.jar") :: "." :: environment.classpath
    scala(
        'classpath -> cp,
        'opts -> "spring/object-bean-script.scala")
}

target('aspectj -> 'compile) {
    val aspectj = environment.environmentVariables.get("ASPECTJ_HOME")
    val cp = (aspectj + "/lib/aspectjrt.jar") :: "../lib/scala-library.jar" :: "." :: environment.classpath
    sh("ajc -d build -classpath " + cp + " aspectj/LogComplex.aj")
    sh("java -classpath " + cp + " -javaagent:" + aspectj + "/lib/aspectjweaver.jar example.aspectj.ComplexMain")
}

target('clean) {
    deleteRecursively(buildDir)
    deleteRecursively("_ajdump")
}

target('build_dir) {
    mkdir(buildDir)
}
