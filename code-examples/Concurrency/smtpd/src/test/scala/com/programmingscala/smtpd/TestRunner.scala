// code-examples/Concurrency/smtpd/src/test/scala/com/programmingscala/smtpd/TestRunner.scala
package com.programmingscala.smtpd

import org.specs.runner.SpecsFileRunner

object TestRunner extends SpecsFileRunner("src/test/scala/**/*.scala", ".*",
  System.getProperty("system", ".*"), System.getProperty("example", ".*"))
