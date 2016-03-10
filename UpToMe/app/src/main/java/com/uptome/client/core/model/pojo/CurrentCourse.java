package com.uptome.client.core.model.pojo;

import android.content.Context;

import com.uptome.client.core.log.ClientLog;
import com.uptome.client.core.model.ICurrentCourse;

/**
 * The current course proxy
 */
public class CurrentCourse implements ICurrentCourse {

    /**
     * Tag for logging.
     */
    private static final String LOG_TAG = CurrentCourse.class.getSimpleName();

    /**
     * Initialize the instance
     */
    public static CurrentCourse init(Context context) {
        ClientLog.d(LOG_TAG, "Start the current course proxy initialization");

        ClientLog.d(LOG_TAG, "The skills proxy initializaation completed successfully");
        return null;
    }
}
