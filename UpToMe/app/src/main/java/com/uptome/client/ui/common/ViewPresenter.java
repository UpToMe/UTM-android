package com.uptome.client.ui.common;

import android.view.View;

/**
 * View presenter parent class
 *
 * @author Vladimir Rybkin
 */
public class ViewPresenter<T extends View> {

    /**
     * View instance
     */
    private T mView;

    /**
     * Take the view instance
     * @param view View instance
     */
    public void takeView(T view) {
        mView = view;
    }

    /**
     * Drop the view
     * @param view View instance
     */
    public void dropView(T view) {
        mView = null;
    }

    /**
     * Get the view.
     *
     * @return instance or null
     */
    protected T getView() {
        return mView;
    }

    /**
     * Get whether a view attached.
     *
     * @return value
     */
    public boolean hasView() {
        return mView != null;
    }
}
