package game

import scala.util.parsing.json.JSON
import scalaj.http._

class GameInfo(_name: String, _price: String, _url: String) {
  val name: String  = _name
  val price: String = _price
  val url: String = if (_url.contains("steam")) _url else shortenUrl(_url)

  override def toString = { name + " [ " + price + " | " + url + " ]" }

  def shortenUrl(_url: String = url) : String = {
    val apiKey = "R_e29aa9c6e2144e19a52df028f146c899"
    val login = "test508"
    val response: HttpResponse[String] = Http("https://api-ssl.bitly.com/v3/shorten")
              .params(Seq("apiKey" -> apiKey, "login" -> login, "longUrl" -> _url)).asString
    val json = JSON.parseFull(response.body)
    json match {
      case _map : Some[Map[String, Any]] => _map.get("data").asInstanceOf[Map[String,_]]("url").asInstanceOf[String]
      case None => _url
    }
  }
}