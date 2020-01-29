package com.uzooin.contexts

import com.uzooin.constants.SessionKey
import com.uzooin.data.SessionJsonObject

class TestContext {
  val roomContext:RoomContext = new RoomContext
  val accountContext:AccountContext = new AccountContext("Room")
}

object TestContext extends SessionJsonObject[TestContext] {
  override val KEY: String = SessionKey.TEST_CONTEXT
}