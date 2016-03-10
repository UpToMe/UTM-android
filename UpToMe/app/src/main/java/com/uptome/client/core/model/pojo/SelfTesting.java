package com.uptome.client.core.model.pojo;

import android.content.Context;

import com.uptome.client.core.log.ClientLog;
import com.uptome.client.core.model.ISelfTesting;

/**
 * Self testing.
 *
 * @author Vladimir Rybkin
 */
public class SelfTesting implements ISelfTesting {

    /**
     * Tag for logging.
     */
    private static final String LOG_TAG = SelfTesting.class.getSimpleName();

    /**
     * Whether the self-testing is completed
     */
    private boolean mIsCompleted;

    /**
     * Initialize the instance
     */
    public static SelfTesting init(Context context) {
        ClientLog.d(LOG_TAG, "Start the self-testing proxy initializaation");
        SelfTesting instance = new SelfTesting();

        ClientLog.d(LOG_TAG, "The self-testing proxy initializaation completed successfully");
        return instance;
    }

    @Override
    public boolean isCompleted() {
        return mIsCompleted;
    }
}
