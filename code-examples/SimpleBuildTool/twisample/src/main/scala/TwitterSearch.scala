package twisample
import java.io.{File,IOException}
import java.net.URI
import org.apache.http.{HttpResponse,HttpStatus,HttpHost}
import org.apache.http.util.EntityUtils

import scala.xml.XML
import scala.xml.Elem
import java.io.{File,IOException}
import java.net.{URI,URISyntaxException,URLDecoder,UnknownHostException}
import org.apache.http.{HttpResponse,HttpStatus,HttpHost}
import org.apache.http.client.ResponseHandler
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.DefaultHttpClient
import org.apache.http.util.EntityUtils
import org.apache.http.conn.params.ConnRoutePNames
import scala.actors._
import scala.actors.Actor._

trait EntryHandler {
  def handle(results:List[Entry]);
}

object Timer { 
  def start(waitTime:Long)(f:()=>Any):Actor = {
    actor {
      loop { 
        reactWithin(waitTime) { 	
          case 'stop   => exit
          case TIMEOUT => {
            try { f() } catch {case e:Exception => println(e); exit } 
          }
        }
      }
    }
  }
}
class Entry(val id:String, val author:String , val title:String , val link:String)  {
  override def toString:String = {
    return "(" + id + ":" + author + ":" + title + ":" +  link + ")"
  }

}
object Entries {
  def apply(feed:Elem):List[Entry] = {
    val list = feed \ "entry"  map { (entry) =>
  	  new Entry ( (entry \ "id" ).text ,  (entry \ "author" \ "name").text ,
	             (entry \ "title").text , ( (entry \ "link")(0) \ "@href" ).text)
    }
    return list.toList
  }
}


class TwitterSearch extends ResponseHandler[List[Entry]] {
  var results:List[Entry] = Nil

  override def handleResponse(response: HttpResponse): List[Entry] = {
    response.getStatusLine.getStatusCode match {
       case HttpStatus.SC_OK =>  return Entries( XML.load(response.getEntity.getContent) )
       case otherStatus:Int => throw new RuntimeException("HTTP Error:" + otherStatus.toString)
    }
  }   
  def fetch(query:Query):Unit  = fetch(query.uri)
  
  def fetch(uri:URI):Unit = {
      //client.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, new HttpHost("proxy.example.com", 8080));
      val client = new DefaultHttpClient
      try {
        val get = new HttpGet(uri)
        this.results  = client.execute[List[Entry]](get, this)
      } catch {
	case e: UnknownHostException => e.printStackTrace; throw e
	case e: IOException          => e.printStackTrace; throw e
      } finally {
	client.getConnectionManager.shutdown
      }
  }

  def accept(entryHandler:EntryHandler) =  entryHandler.handle(results) 

}
class Query(var keywords:List[String]) {
  def uri = new URI("http://search.twitter.com/search.atom?q=" + keywords.mkString("+"))
}
object Query {
  def apply(keywords  : String*):Query = new Query(keywords.toList) 
}