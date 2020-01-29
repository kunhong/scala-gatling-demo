package com.uzooin.utils

import com.uzooin.contexts.TestContext

object SessionUtils {
  def getSessionVars = Iterator.continually(
    Map(
      TestContext.KEY -> new TestContext
    )
  )
}
