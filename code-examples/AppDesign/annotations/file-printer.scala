// code-examples/AppDesign/annotations/file-printer.scala

import java.io._

class FilePrinter(val file: File) {

  @throws(classOf[IOException])
  def print() = {
    var reader: LineNumberReader = null
    try {
      reader = new LineNumberReader(new FileReader(file))
      loop(reader)
    } finally {
      if (reader != null)
        reader.close
    }
  }
      
  private def loop(reader: LineNumberReader): Unit = {
    val line = reader.readLine()
    if (line != null) {
      format("%3d: %s\n", reader.getLineNumber, line)
      loop(reader)
    }
  }
}
