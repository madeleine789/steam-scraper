package scraper

import game.GameInfo
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.model.Element

class OriginScraper(url: String = "https://www.origin.com/pl-pl/store/browse?q=")  extends Scraper(url) {
  override def getInfo(game: String): List[GameInfo] = {
    val doc = browser.get(asSearchUrl(game))
    val searchItems: List[Element] = doc >> elementList(".background-gradient")
    val priceInfo: List[Element] = doc >> elementList(".price")
    val prices: List[String] = priceInfo.map(_ >> attr("data-defaultprice")("span"))
    val items: List[(String, String)] = searchItems.map(_ >> (text("h5 a"), attr("src")("a img")))
    for ( (item, price) <- items zip prices) yield new GameInfo(item._1, price, item._2)
  }

}
