package com.uzooin.constants

import java.util.concurrent.locks.ReentrantReadWriteLock

import com.uzooin.utils.LockUtil

import scala.collection.concurrent
import scala.collection.mutable

// 참고 : https://agilewombat.com/2016/09/17/multithreading-with-shared-data-in-scala/


object Environment {
  var accountNameList:concurrent.TrieMap[Int, Boolean] = concurrent.TrieMap.empty[Int, Boolean]
  var accountNameFilter:concurrent.TrieMap[Boolean, mutable.Seq[Int]] = concurrent.TrieMap.empty[Boolean, mutable.Seq[Int]]
  accountNameFilter += (true -> mutable.Seq.empty)
  accountNameFilter += (false -> mutable.Seq.empty)
  var id = 0

  private[this] val rwLock = new ReentrantReadWriteLock()

  def addAccount() = {
    LockUtil.withWriteLock(rwLock) {
      id += 1
      accountNameList.putIfAbsent(id, false)
      accountNameFilter.update(id % 2 == 0, accountNameFilter(id % 2 == 0) :+ id)
    }
  }

  def printAccount(name: String) = {
    LockUtil.withReadLock(rwLock) {
      println("[[ " + name + " ]] accountList => " + accountNameList.toSeq.sortBy(_._1))
      println("[[ " + name + " ]] accountNameFilter => " + accountNameFilter)
    }
  }

  def isDone() = {
    LockUtil.withReadLock(rwLock) {
      println("isDone => " + accountNameList.size)
      accountNameList.size == 100
    }
  }
}
