package com.cjburkey.cjsreactors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.cjburkey.cjsreactors.ModInfo.*;

/**
 * Created by CJ Burkey on 2019/04/14
 */
public final class Log {

    private static final Logger LOG = LogManager.getLogger(MODID);

    public static void info(String msg) {
        LOG.info(msg);
    }

    public static void info(String msg, Object... data) {
        LOG.info(msg, data);
    }

    public static void warn(String msg) {
        LOG.warn(msg);
    }

    public static void warn(String msg, Object... data) {
        LOG.warn(msg, data);
    }

    public static void error(String msg) {
        LOG.error(msg);
    }

    public static void error(String msg, Object... data) {
        LOG.error(msg, data);
    }

}
