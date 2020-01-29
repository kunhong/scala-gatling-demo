package com.uzooin.utils

import java.util.concurrent.locks.ReentrantReadWriteLock

object LockUtil {
  def withReadLock[B](rwLock: ReentrantReadWriteLock)(fn: => B) = {
    rwLock.readLock().lock()
    try {
      fn
    } finally {
      rwLock.readLock().unlock()
    }
  }

  def withWriteLock[B](rwLock: ReentrantReadWriteLock)(fn: => B) = {
    rwLock.writeLock().lock()
    try {
      fn
    } finally {
      rwLock.writeLock().unlock()
    }
  }
}
