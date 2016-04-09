import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL.Parse._
import net.ruippeixotog.scalascraper.model.Element
import net.ruippeixotog.scalascraper.scraper._


object SteamScraper {
	val browser = JsoupBrowser()
	val asSteamUrl: String => String = "http://store.steampowered.com/search/?snr=1_4_4__12&term=" + _

	def getPrices(game: String): List[(String, String)] = {
		val doc = browser.get(asSteamUrl(game))
		val searchItems: List[Element] = doc >> elementList(".search_result_row")
		val itemTitles: List[(String, String)] = searchItems.map(_ >> 
			(text(".title"), text(".search_price")))

		itemTitles
	}

	def main(args: Array[String]): Unit = {
		val gameTitle = args(0)
		println("Starting to parse " + gameTitle)
		println(getPrices(gameTitle))
		
	}
}