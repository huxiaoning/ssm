package org.aidan.ssm.limit;

import com.google.common.math.LongMath;
import org.aidan.ssm.lock.DistributedLock;
import org.aidan.ssm.util.LoggerUtil;
import org.aidan.ssm.util.RedisUtils;

import java.util.concurrent.TimeUnit;

/**
 * 令牌桶限流器
 *
 * @author aidan
 */
public class RateLimiter {

    /**
     * redis 停牌桶的key
     */
    private String key;

    /**
     * 每秒存入的令牌数
     */
    private Double permitsPerSecond;
    /**
     * 最大存储maxBurstSeconds秒生成的令牌
     */
    private Integer maxBurstSeconds;
    /**
     * 分布式同步锁
     */
    private DistributedLock syncLock;

    public RateLimiter(String key, Double permitsPerSecond, Integer maxBurstSeconds, DistributedLock syncLock) {
        this.key = key;
        this.permitsPerSecond = permitsPerSecond;
        this.maxBurstSeconds = maxBurstSeconds;
        this.syncLock = syncLock;
    }

    /**
     * 生成并存储默认令牌桶
     *
     * @return
     */
    private RedisPermits putDefaultPermits() {
        this.lock();
        try {

            Object obj = RedisUtils.get(key);
            if (null == obj) {
                RedisPermits permits = new RedisPermits(permitsPerSecond, maxBurstSeconds);
                RedisUtils.set(key, permits, permits.expires(), TimeUnit.SECONDS);
                return permits;
            } else {
                return (RedisPermits) obj;
            }
        } finally {
            this.unlock();
        }

    }

    /**
     * 加锁
     */
    private void lock() {
        syncLock.lock();
    }

    /**
     * 解锁
     */
    private void unlock() {
        syncLock.unlock();
    }

    /**
     * 获取令牌桶
     *
     * @return
     */
    public RedisPermits getPermits() {
        Object obj = RedisUtils.get(key);
        if (null == obj) {
            return putDefaultPermits();
        }
        return (RedisPermits) obj;
    }

    /**
     * 更新令牌桶
     *
     * @param permits
     */
    public void setPermits(RedisPermits permits) {
        RedisUtils.set(key, permits, permits.expires(), TimeUnit.SECONDS);
    }

    /**
     * 等待直到获取指定数量的令牌
     *
     * @param tokens
     * @return
     * @throws InterruptedException
     */
    public Long acquire(Long tokens) throws InterruptedException {
        long milliToWait = this.reserve(tokens);
        LoggerUtil.info("acquire for " + milliToWait + "ms " + Thread.currentThread().getName());
        Thread.sleep(milliToWait);
        return milliToWait;
    }

    /**
     * 获取1一个令牌
     *
     * @return
     * @throws InterruptedException
     */
    private long acquire() throws InterruptedException {
        return acquire(1L);
    }

    /**
     * @param tokens  要获取的令牌数
     * @param timeout 获取令牌等待的时间，负数被视为0
     * @param unit
     * @return
     * @throws InterruptedException
     */
    private Boolean tryAcquire(Long tokens, Long timeout, TimeUnit unit) throws InterruptedException {
        long timeoutMicros = Math.max(unit.toMillis(timeout), 0);
        checkTokens(tokens);
        Long milliToWait;
        try {
            this.lock();
            if (!this.canAcquire(tokens, timeoutMicros)) {
                return false;
            } else {
                milliToWait = this.reserveAndGetWaitLength(tokens);
            }
        } finally {
            this.unlock();
        }
        Thread.sleep(milliToWait);
        return true;
    }

    /**
     * 获取一个令牌
     *
     * @param timeout
     * @param unit
     * @return
     * @throws InterruptedException
     */
    private Boolean tryAcquire(Long timeout, TimeUnit unit) throws InterruptedException {
        return tryAcquire(1L, timeout, unit);
    }

    private long redisNow() {
        Long time = RedisUtils.currentTime();
        return null == time ? System.currentTimeMillis() : time;
    }

    /**
     * 获取令牌n个需要等待的时间
     *
     * @param tokens
     * @return
     */
    private long reserve(Long tokens) {
        this.checkTokens(tokens);
        try {
            this.lock();
            return this.reserveAndGetWaitLength(tokens);
        } finally {
            this.unlock();
        }
    }

    /**
     * 校验token值
     *
     * @param tokens
     */
    private void checkTokens(Long tokens) {
        if (tokens < 0) {
            throw new IllegalArgumentException("Requested tokens " + tokens + " must be positive");
        }
    }

    /**
     * 在等待的时间内是否可以获取到令牌
     *
     * @param tokens
     * @param timeoutMillis
     * @return
     */
    private Boolean canAcquire(Long tokens, Long timeoutMillis) {
        return queryEarliestAvailable(tokens) - timeoutMillis <= 0;
    }

    /**
     * 返回获取{tokens}个令牌最早可用的时间
     *
     * @param tokens
     * @return
     */
    private Long queryEarliestAvailable(Long tokens) {
        long n = redisNow();
        RedisPermits permit = this.getPermits();
        permit.reSync(n);
        // 可以消耗的令牌数
        long storedPermitsToSpend = Math.min(tokens, permit.getStoredPermits());
        // 需要等待的令牌数
        long freshPermits = tokens - storedPermitsToSpend;
        // 需要等待的时间
        long waitMillis = freshPermits * permit.getIntervalMillis();
        return LongMath.saturatedAdd(permit.getNextFreeTicketMillis() - n, waitMillis);
    }

    /**
     * 预定@{tokens}个令牌并返回所需要等待的时间
     *
     * @param tokens
     * @return
     */
    private Long reserveAndGetWaitLength(Long tokens) {
        long n = redisNow();
        RedisPermits permit = this.getPermits();
        permit.reSync(n);
        // 可以消耗的令牌数
        long storedPermitsToSpend = Math.min(tokens, permit.getStoredPermits());
        // 需要等待的令牌数
        long freshPermits = tokens - storedPermitsToSpend;
        // 需要等待的时间
        long waitMillis = freshPermits * permit.getIntervalMillis();
        permit.setNextFreeTicketMillis(LongMath.saturatedAdd(permit.getNextFreeTicketMillis(), waitMillis));
        permit.setStoredPermits(permit.getStoredPermits() - storedPermitsToSpend);
        this.setPermits(permit);
        return permit.getNextFreeTicketMillis() - n;
    }
}
