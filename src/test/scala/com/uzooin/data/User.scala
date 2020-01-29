package com.uzooin.data

import scala.util.Random._

abstract class User {
  val id = (nextInt(26) + 'A').toChar.toString +
    (0 until 2).map(_ => nextInt(10)).mkString

  def getType(): String
}
