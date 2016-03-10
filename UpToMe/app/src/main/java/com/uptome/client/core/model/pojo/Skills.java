package com.uptome.client.core.model.pojo;

import android.content.Context;

import com.uptome.client.core.log.ClientLog;
import com.uptome.client.core.model.ISkills;

/**
 * Skills class.
 *
 * @author Vladimir Rybkin
 */
public class Skills implements ISkills {

    /**
     * Tag for logging.
     */
    private static final String LOG_TAG = Registration.class.getSimpleName();

    /**
     * Initialize the instance
     */
    public static Skills init(Context context) {
        ClientLog.d(LOG_TAG, "Start the skills proxy initializaation");
        Skills skills = new Skills();

        ClientLog.d(LOG_TAG, "The skills proxy initializaation completed successfully");
        return skills;
    }
}
