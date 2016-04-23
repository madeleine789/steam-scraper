package scraper

import game.GameInfo
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.model.Element

class SteamScraper(url: String = "http://store.steampowered.com/search/?snr=1_4_4__12&term=") extends Scraper(url) {

  override def getInfo(game: String): List[GameInfo] = {
    val doc = browser.get(asSearchUrl(game))
    val searchItems: List[Element] = doc >> elementList(".search_result_row")

    val itemTitles: List[(String, String, String)] = searchItems.map(_ >> (text(".title"), text(".search_price"),
      attr("src")("img")))
    var info: List[GameInfo] = List()
    itemTitles.foreach{case (title, price, img) => info ++= List(new GameInfo(title, price, img))}
    info
  }

}