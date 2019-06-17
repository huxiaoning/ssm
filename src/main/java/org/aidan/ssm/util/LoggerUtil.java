/*
 * Copyright by Deppon and the original author or authors.
 *
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *
 *      http://www.deppon.com
 *
 */
package org.aidan.ssm.util;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 日志工具类
 *
 * @author aidan
 */
public class LoggerUtil {

    /**
     * fqcn
     */
    private static final String fqcn = LoggerUtil.class.getName();
    /**
     * NOT_AVAIL
     */
    private static final String NOT_AVAIL = "?";

    /**
     * @param stackTrace
     * @return
     */
    private static StackTraceElement getStackTraceElement(
            final StackTraceElement[] stackTrace) {
        boolean next = false;
        for (final StackTraceElement element : stackTrace) {
            final String className = element.getClassName();
            if (next && !fqcn.equals(className)) {
                return element;
            }
            if (fqcn.equals(className)) {
                next = true;
            } else if (NOT_AVAIL.equals(className)) {
                break;
            }
        }
        return null;
    }

    /**
     * @param message
     */
    public static void info(final String message) {
        StackTraceElement caller = getStackTraceElement(Thread.currentThread()
                .getStackTrace());
        if (null == caller) {
            return;
        }
        Logger log = LogManager
                .getLogger(caller.getClassName() + "." + caller.getMethodName()
                        + "() Line: " + caller.getLineNumber());
        log.info(message);
    }

    /**
     * @param message
     * @param t
     */
    public static void info(final String message, Throwable t) {
        StackTraceElement caller = getStackTraceElement(Thread.currentThread()
                .getStackTrace());
        if (null == caller) {
            return;
        }
        Logger log = LogManager
                .getLogger(caller.getClassName() + "." + caller.getMethodName()
                        + "() Line: " + caller.getLineNumber());
        log.info(message, t);
    }


    /**
     * @param message
     */
    public static void debug(final String message) {
        StackTraceElement caller = getStackTraceElement(Thread.currentThread()
                .getStackTrace());
        if (null == caller) {
            return;
        }
        Logger log = LogManager
                .getLogger(caller.getClassName() + "." + caller.getMethodName()
                        + "() Line: " + caller.getLineNumber());
        log.debug(message);
    }

    /**
     * @param message
     * @param t
     */
    public static void debug(final String message, Throwable t) {
        StackTraceElement caller = getStackTraceElement(Thread.currentThread()
                .getStackTrace());
        if (null == caller) {
            return;
        }
        Logger log = LogManager
                .getLogger(caller.getClassName() + "." + caller.getMethodName()
                        + "() Line: " + caller.getLineNumber());
        log.debug(message, t);
    }

    /**
     * @param message
     */
    public static void error(final String message) {
        StackTraceElement caller = getStackTraceElement(Thread.currentThread()
                .getStackTrace());
        if (null == caller) {
            return;
        }
        Logger log = LogManager
                .getLogger(caller.getClassName() + "." + caller.getMethodName()
                        + "() Line: " + caller.getLineNumber());
        log.error(message);
    }

    /**
     * @param message
     * @param t
     */
    public static void error(final String message, Throwable t) {
        StackTraceElement caller = getStackTraceElement(Thread.currentThread()
                .getStackTrace());
        if (null == caller) {
            return;
        }
        Logger log = LogManager
                .getLogger(caller.getClassName() + "." + caller.getMethodName()
                        + "() Line: " + caller.getLineNumber());
        log.error(message, t);
    }

    /**
     * @param message
     */
    public static void warn(final String message) {
        StackTraceElement caller = getStackTraceElement(Thread.currentThread()
                .getStackTrace());
        if (null == caller) {
            return;
        }
        Logger log = LogManager
                .getLogger(caller.getClassName() + "." + caller.getMethodName()
                        + "() Line: " + caller.getLineNumber());
        log.warn(message);
    }
}
