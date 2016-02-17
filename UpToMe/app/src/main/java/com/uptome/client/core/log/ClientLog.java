package com.uptome.client.core.log;

/**
 * The client log wrapper.
 *
 * @author Vladimir Rybkin
 */
public class ClientLog {

    private static boolean log_e, log_w, log_i, log_d, log_v;

    static {
        log_e = true;
        log_w = true;
        log_i = true;
        log_d = true;
        log_v = true;
    }

    /**
     * Verbose logging.
     *
     * @param tag
     * @param string
     */
    public static void v(String tag, String string) {
        if (log_v) {
            android.util.Log.v(tag, string);
        }
    }

    /**
     * Verbose logging.
     *
     * @param tag
     * @param format
     * @param args
     */
    public static void v(String tag, String format, Object... args) {
        if (log_v) {
            String message = String.format(format, args);

            android.util.Log.v(tag, message);
        }
    }

    /**
     * Debug logging.
     *
     * @param tag
     * @param string
     */
    public static void d(String tag, String string) {
        if (log_d) {
            android.util.Log.d(tag, string);
        }
    }

    /**
     * Debug logging.
     *
     * @param tag
     * @param format
     * @param args
     */
    public static void d(String tag, String format, Object... args) {
        if (log_d) {
            String message = String.format(format, args);
            android.util.Log.d(tag, message);
        }
    }

    /**
     * Info logging.
     *
     * @param tag
     * @param string
     */
    public static void i(String tag, String string) {
        if (log_i) {
            android.util.Log.i(tag, string);
        }
    }

    /**
     * Info logging.
     *
     * @param tag
     * @param format
     * @param args
     */
    public static void i(String tag, String format, Object... args) {
        if (log_i) {
            String message = String.format(format, args);
            android.util.Log.i(tag, message);
        }
    }

    /**
     * Warning logging.
     *
     * @param tag
     * @param string
     */
    public static void w(String tag, String string) {
        if (log_w) {
            android.util.Log.w(tag, string);
        }
    }

    /**
     * Warning logging.
     *
     * @param tag
     * @param format
     * @param args
     */
    public static void w(String tag, String format, Object... args) {
        if (log_w) {
            String message = String.format(format, args);
            android.util.Log.w(tag, message);
        }
    }

    /**
     * Warning logging.
     *
     * @param tag
     * @param e
     */
    public static void w(String tag, Throwable e) {
        if (log_w) {
            android.util.Log.w(tag, e != null ? e.getMessage() : "", e);
        }
    }

    /**
     * Warning logging.
     *
     * @param tag
     * @param message
     * @param e
     */
    public static void w(String tag, String message, Throwable e) {
        if (log_w) {
            android.util.Log.w(tag, message, e);
        }
    }

    /**
     * Error logging.
     *
     * @param tag
     * @param string
     */
    public static void e(String tag, String string) {
        if (log_e) {
            android.util.Log.e(tag, string);
        }
    }

    /**
     * Error logging.
     *
     * @param tag
     * @param format
     * @param args
     */
    public static void e(String tag, String format, Object... args) {
        if (log_e) {
            String message = String.format(format, args);
            android.util.Log.e(tag, message);
        }
    }

    /**
     * Error logging
     *
     * @param tag
     * @param e
     */
    public static void e(String tag, Throwable e) {
        if (log_e) {
            android.util.Log.e(tag, e != null ? e.getMessage() : "", e);
        }
    }

    /**
     * Error logging
     *
     * @param tag
     * @param message
     * @param e
     */
    public static void e(String tag, String message, Throwable e) {
        if (log_e) {
            android.util.Log.e(tag, message, e);
        }
    }
}
