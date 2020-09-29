import java.io._
import java.util.Properties

import io.gatling.core.Predef._
import io.gatling.core.structure._
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

class GoogleApisTest extends Simulation {

  val httpConfig: HttpProtocolBuilder = http
    .baseUrl("https://www.googleapis.com/blogger/v3/")
    .acceptHeader(HttpHeaderValues.ApplicationJson)

  val props = new Properties()
  val inputStream = new FileInputStream(new File("src/gatling/resources/gradle.properties"))

  try props.load(inputStream)
  catch {
    case e: FileNotFoundException => println(s"The file read is missing: ${e.printStackTrace()}")
    case e: IOException => println(s"An unexpected error has occurred: ${e.printStackTrace()}")
  }

  val API_KEY: String = props.getProperty("API_KEY")

  val scn: ScenarioBuilder = scenario("HTTP GET Requests With Authorization")
    .exec(getBlogById(API_KEY, "2399953"))
    .pause(5)
    .exec(getBlogByUrl(API_KEY, "https://blogger-developers.googleblog.com"))
    .pause(5)
    .exec(getPageByBlogIdAndPageId(API_KEY, "2399953", "8251768148820290538"))

  def getBlogById(apiKey: String, blogId: String): ChainBuilder = {
    exec(http("Get Blog By Id")
      .get(s"blogs/$blogId")
      .queryParam("key", apiKey)
      .check(status.is(200)))
  }

  def getBlogByUrl(apiKey: String, blogUrl: String): ChainBuilder = {
    exec(http("Get Blog By Url")
      .get("blogs/byurl")
      .queryParam("key", apiKey)
      .queryParam("url", blogUrl)
      .check(status.is(200)))
  }

  def getPageByBlogIdAndPageId(apiKey: String, blogId: String, pageId: String): ChainBuilder = {
    exec(http("Get Page By Blog Id And Page Id")
      .get(s"blogs/$blogId/pages/$pageId")
      .queryParam("key", apiKey)
      .check(status.is(200)))
  }

  setUp(scn.inject(atOnceUsers(1))
    .protocols(httpConfig))
    .assertions(global.responseTime.max.lt(50), global.successfulRequests.percent.gt(95)
  )
}
