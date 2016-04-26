package game

import java.time.LocalDateTime

import scala.math.BigDecimal.RoundingMode
import scala.util.parsing.json.JSON
import scalaj.http._

case class GameInfo(_name: String, _price: String, _url: String) {
  val name: String  = _name
  val price: PriceEntry = priceStrToPriceEntry(_price)
  val url: String = if (_url.contains("steam")) _url else shortenUrl(_url)

  override def toString = { name + " [ " + price.price.value + " " + price.price.currency + " | " + url + " ]" }

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

  def priceStrToPriceEntry(_price: String, euroToPln: Double = 4.4) : PriceEntry = {
    val pattern = """(\d+,*\d*).*""".r
    var price = "0.00"
    pattern.findAllIn(_price).matchData foreach {
      m => {
        price = m.group(1).replace(",", ".")
        if (!_price.contains("zł")) price = (price.toDouble * euroToPln).toString;
      };
    }
    new PriceEntry(LocalDateTime.now(), new Money(BigDecimal(price).setScale(2, RoundingMode.HALF_UP), "PLN"))
  }
}

case class PriceEntry(date: LocalDateTime, price: Money)
case class Money(value: BigDecimal, currency: String)