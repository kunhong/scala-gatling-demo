package com.uzooin.requests

import java.util.concurrent.ThreadLocalRandom // First we import ThreadLocalRandom, to generate random values.

import io.gatling.core.Predef._
import io.gatling.http.Predef._

object Edit {
  val edit = exec(http("Form")
    .get("/computers/new"))
    .pause(1)
    .exec(http("Post")
      .post("/computers")
      .check(status.is(session => 200 + ThreadLocalRandom.current.nextInt(2)))) // We do a check on a condition that’s been customized with a lambda. It will be evaluated every time a user executes the request and randomly return 200 or 201. As response status is 200, the check will fail randomly.

  val tryMaxEdit = tryMax(2) { // tries a given block up to n times. Here we try a maximum of two times.
    exec(edit)
  }.exitHereIfFailed // If all tries failed, the user exits the whole scenario due to exitHereIfFailed.
}
