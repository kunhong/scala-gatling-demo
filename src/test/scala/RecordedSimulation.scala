import java.util.concurrent.ThreadLocalRandom

import com.uzooin.requests.{Browse, Edit, Search}
import com.uzooin.scenarios.{ComputerDataBaseScenario, ConcurrentUserScenario, PostManUserScenario}
import io.gatling.core.Predef._
import io.gatling.core.session
import io.gatling.core.session.Expression
import io.gatling.core.structure.PopulationBuilder
import io.gatling.http.Predef._

import scala.concurrent.duration._

class RecordedSimulation extends Simulation {

  val httpProtocol = http
    .baseUrl("http://computer-database.gatling.io")
    .inferHtmlResources(BlackList(""".*\.css""", """.*\.js""", """.*\.ico"""), WhiteList())
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
    .acceptEncodingHeader("gzip, deflate")
    .acceptLanguageHeader("ko-KR,ko;q=0.8,en-US;q=0.5,en;q=0.3")
    .upgradeInsecureRequestsHeader("1")
    .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.14; rv:69.0) Gecko/20100101 Firefox/69.0")
    .warmUp("http://www.google.com")

//  val users = scenario("Users").exec(Search.random_search, Browse.browse)
//  val admins = scenario("Admins").exec(Search.random_search, Edit.tryMaxEdit)
//
////  setUp(
////      scn.inject(rampUsers(8) during (10 seconds)),
////      users.inject(rampUsers(10) during (10 seconds)),
////      admins.inject(rampUsers(2) during (10 seconds))
////    ).protocols(httpProtocol)
//
//    setUp(
//        scn.inject(rampUsers(1) during (15 seconds))
//      ).protocols(httpProtocol)
//
//
//
//  before {
//    println("Simulation is about to start!")
//  }
//
//  after {
//    println("Simulation is finished!")
//  }

  val httpConf = http.baseUrl("http://websocket.org")
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8") // Here are the common headers
    .doNotTrackHeader("1")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .acceptEncodingHeader("gzip, deflate")
    .userAgentHeader("Gatling")
    .wsBaseUrl("ws://echo.websocket.org")


val scn = scenario("WebSocketScenario")
  .exec(http("openhomepage").get("/"))
  .pause(1 second)
  .exec(
    ws("opensocket")
      .connect("/?encoding=text"))
  .pause(2 seconds)
  .exec(
    ws("send message")
      .sendText("hello")
      .await(20 seconds)(
        ws.checkTextMessage("hellocheck")
          .check(
            regex(".*hello.*")
            .saveAs("name")
          )
      )
  )
  .exec(session => {

    val mystring= session("name").as[String]
    println("hello"+ mystring)
    session
  })
  .pause(1 second)
  .exec(ws("close socket").close)

  //setUp(scn.inject(atOnceUsers(1))).protocols(httpConf)
  setUp(createBuilder()
//    PostManUserScenario.mainScenario.inject(
//    PostManUserScenario.asLongAsScenario.inject(
//      atOnceUsers(1)
//      rampUsers(10) during(3 second)
      // constantUsersPerSec(10) during (15 seconds) // 15초 동안 10명씩 증가
      // constantUsersPerSec(10) during (15 seconds) // 15초 동안 10명씩 증가
      //constantUsersPerSec(10) during (15 seconds) randomized
      //rampUsersPerSec(10) to 20 during (15 seconds)
//      rampConcurrentUsers(10) to (20) during (10 seconds)
//      incrementUsersPerSec(3) // Double
//        .times(2)
//        .eachLevelLasting(2 seconds)
//        .separatedByRampsLasting(4 seconds)
//        .startingFrom(10) // Double
//    PostManUserScenario.throttlingScenario.inject(constantUsersPerSec(10) during (1 minute)).throttle(
//    reachRps(4) in (10 seconds),
//    holdFor(20 seconds),
//    jumpToRps(5),
//    holdFor(1 minute)
//    )
  )
    .protocols(httpProtocol)
    // force your run to terminate based on a duration limit, even though some virtual users are still running.
    .maxDuration(1 minutes)


  private def createBuilder():List[PopulationBuilder] = {
    List(
      PostManUserScenario.mainScenario
              .inject(atOnceUsers(1))
//      ComputerDataBaseScenario.mainScenario
//              .inject(atOnceUsers(5))
//      ConcurrentUserScenario.mainScenario
//        .inject(atOnceUsers(5))
    )
  }
}
