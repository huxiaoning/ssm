package org.aidan.ssm.lock;

import org.redisson.api.RLock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @author Aidan
 * @创建时间：2019/6/16 3:53 PM
 * @描述: 分布式锁
 */
public class DistributedLock implements Lock {

    private RLock rLock;


    public DistributedLock(RLock rLock) {
        this.rLock = rLock;
    }

    @Override
    public void lock() {
        rLock.lock();
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        rLock.lockInterruptibly();
    }

    @Override
    public boolean tryLock() {
        return rLock.tryLock();
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return rLock.tryLock(time, unit);
    }

    @Override
    public void unlock() {
        rLock.unlock();
    }

    @Override
    public Condition newCondition() {
        return rLock.newCondition();
    }
}
