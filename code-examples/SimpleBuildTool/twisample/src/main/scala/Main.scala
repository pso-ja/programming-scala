
package twisample
object Main {
  def main(args: Array[String]): Unit = {
    val search = new TwitterSearch
    val timer = Timer.start(args(1).toInt){ () => 
      search.fetch (Query(args(0)))      
      search.accept(if(args.length==3) args(2) else "twisample.PrintAuthors")
    }
    Thread.sleep(10000)
    timer ! 'stop
  }

  implicit def string2Handler(className:String):EntryHandler =  {
      Class.forName(className).newInstance.asInstanceOf[EntryHandler]
  }
}

