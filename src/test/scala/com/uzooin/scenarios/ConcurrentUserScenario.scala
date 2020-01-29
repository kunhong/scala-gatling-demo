package com.uzooin.scenarios

import com.uzooin.constants.{Environment, SessionKey}
import com.uzooin.contexts.TestContext
import com.uzooin.utils.SessionUtils
import io.gatling.core.Predef._
import io.gatling.http.Predef._

object ConcurrentUserScenario {
  var mainScenario = scenario("ConcurrentUser scenario")
    .feed(SessionUtils.getSessionVars)
    .repeat(10){
      exec(session=>{
        Environment.addAccount()
        session
      }).pause(1)
    }
    .exec(session=>{
      Environment.printAccount("[[ " + TestContext.from(session).accountContext.user.getType() + " ]] " + TestContext.from(session).accountContext.name)
      session
    })

  def randomString(length: Int) = {
    val r = new scala.util.Random
    val sb = new StringBuilder
    for (i <- 1 to length) {
      sb.append(r.nextPrintableChar)
    }
    sb.toString
  }
}
