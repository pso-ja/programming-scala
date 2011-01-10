package twisample
class PrintAuthors extends EntryHandler {
  def handle(results:List[Entry]) = {
     println(results.map(_.author).mkString(","))
  }
}