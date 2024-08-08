package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class LoggerUtil {
    private static final Logger logger = LogManager.getLogger(LoggerUtil.class);
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_RESET = "\u001B[0m";

    /**
     * Logs the given message in yellow color with a new line.
     *
     * @param message the message to log
     */
    public static void logMessage(String message) {
        logger.info(ANSI_YELLOW + message + ANSI_RESET);
    }

}