package com.uzooin.contexts

import com.uzooin.data.{PcUser, RoomUser, User}

import scala.util.Random._

class AccountContext(accountType: String = "Pc") {
  val name = (nextInt(26) + 'A').toChar.toString +
    (0 until 2).map(_ => nextInt(10)).mkString

  val user:User = accountType match {
    case "Room" => new RoomUser
    case _ => new PcUser
  }
}
