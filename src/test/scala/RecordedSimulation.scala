import com.uzooin.requests.{Browse, Edit, Search}
import io.gatling.core.Predef._
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


  val foo = forever(
    pace(5 seconds)
      .exec(
        pause(1 second, 4 seconds) // Will be run every 5 seconds, irrespective of what pause time is used
      )
      .rendezVous(6).exec{session=>
        println("xxxxxxxxxxxx")
        session
      }
  )

  val scn = scenario("RecordedSimulation")
    .exec(Search.search)
    .exec {
      session => session.set("foo", "bar")
    }
    .exec { session =>
      println(">>>>>> " + session)
      session
    }
//    .exec(foo)



  val users = scenario("Users").exec(Search.random_search, Browse.browse)
  val admins = scenario("Admins").exec(Search.random_search, Edit.tryMaxEdit)

//  setUp(
//      scn.inject(rampUsers(8) during (10 seconds)),
//      users.inject(rampUsers(10) during (10 seconds)),
//      admins.inject(rampUsers(2) during (10 seconds))
//    ).protocols(httpProtocol)

    setUp(
        scn.inject(rampUsers(8) during (30 seconds))
      ).protocols(httpProtocol)



  before {
    println("Simulation is about to start!")
  }

  after {
    println("Simulation is finished!")
  }
}
