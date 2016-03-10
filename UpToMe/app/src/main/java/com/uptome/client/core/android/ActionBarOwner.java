package com.uptome.client.core.android;

import android.content.Context;

import com.uptome.client.core.log.ClientLog;

/**
 * Action bar owner interface.
 *
 * @author Vladimir Rybkin
 */
public class ActionBarOwner {

    private static final String LOG_TAG = ActionBarOwner.class.getSimpleName();

    private ActionBarOwnerCallback mCallbacks;

    /**
     * Callback interface
     */
    public interface ActionBarOwnerCallback {

        /**
         * Get the current context.
         * @return context
         */
        Context getContext();

        /**
         * Set the action bar visibility.
         *
         * @param visible True to show false to hide
         */
        void setActionBarVisibility(boolean visible);
    }

    /**
     * Attach a callback
     *
     * @param callbacks Callbacks attached
     */
    public void attachCallbacks(ActionBarOwnerCallback callbacks) {
        mCallbacks = callbacks;
    }

    /**
     * Detach callbacks
     */
    public void detachCallbacks() {
        mCallbacks = null;
    }

    /**
     * Destroy the engine
     */
    public void destroy() {
        ClientLog.d(LOG_TAG, "Destroy called");
        detachCallbacks();
    }

    /**
     * Set the action bar visibility.
     *
     * @param visible True to show false to hide
     */
    public void setActionBarVisibility(boolean visible) {
        ActionBarOwner.ActionBarOwnerCallback callback = mCallbacks;
        if (callback == null) {
            return;
        }

        callback.setActionBarVisibility(visible);
    }
}
