import org.scalatest.FunSuite
import twisample._
import java.net.{URI,URISyntaxException,URLDecoder,UnknownHostException}

class TwisampleSuite extends FunSuite {

 test("""Timerの実験""") {
    val timer = Timer.start(1000){() => 
       println("TEST:" + new java.util.Date)
    }
    Thread.sleep(5100)
    timer ! 'stop
  }

  test("""Twitter Search Timelineを1秒おきにpingしてみるテスト""") {
    val search = new TwitterSearch
    val timer = Timer.start(1000){() => 
      search.fetch (Query("scala"))
      println(search.results.map(_.author).mkString(","))
    }
    Thread.sleep(7000)
    timer ! 'stop
  }

  test("""Twitter Search Timelineに直接アクセスしてみるテスト""") {
    val search = new TwitterSearch
    search.fetch (Query("yojik")) 
    println(search.results)
  }
  test("""twitter seach timelineをオブジェクトに変換してみるテスト""") {
     val feed =
<feed xmlns:google="http://base.google.com/ns/1.0" xml:lang="en-US" xmlns:openSearch="http://a9.com/-/spec/opensearch/1.1/" xmlns="http://www.w3.org/2005/Atom" xmlns:twitter="http://api.twitter.com/">
  <entry>
    <id>tag:search.twitter.com,2005:00000000000</id>
    <published>2010-06-16T11:10:23Z</published>
    <link type="text/html" href="http://twitter.com/-example-/statuses/00000000000" rel="alternate"/>
    <title>-- example -- </title>
    <content type="html">--- example -- </content>
    <updated>2010-06-16T11:10:23Z</updated>
    <link type="image/png" href="http://a3.twimg.com/profile_images/296923847/--example--.jpg" rel="image"/>
    <twitter:geo>
    </twitter:geo>
    <twitter:metadata>
      <twitter:result_type>recent</twitter:result_type>
    </twitter:metadata>
    <twitter:source>&lt;a href=&quot;http://coderepos.org/share/browser/lang/ruby/misc/tig.rb&quot; rel=&quot;nofollow&quot;&gt;tig.rb&lt;/a&gt;</twitter:source>
    <twitter:lang>ja</twitter:lang>
    <author>
      <name>xxxx</name>
      <uri>http://twitter.com/-example-</uri>
    </author>
  </entry>

</feed>
      
      val entries = Entries(feed)
      assert (1 === entries.size)
      assert ("http://twitter.com/-example-/statuses/00000000000" === entries(0).link)
  
  }
}
