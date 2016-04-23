package scraper

import game.GameInfo
import net.ruippeixotog.scalascraper.browser.JsoupBrowser

abstract class Scraper(url: String) {
  val browser = JsoupBrowser()
  def asSearchUrl: String => String = url + _
  def getInfo(game: String): List[GameInfo]
}

