package com.uzooin.data

import io.gatling.core.Predef._

trait SessionObject[T] {
  val KEY: String

  def from(session: Session): T = from(session, KEY)
  def from(session: Session, key: String): T = session.attributes(key).asInstanceOf[T]

  def keyExpression: String = "${" + KEY + "}"
}
