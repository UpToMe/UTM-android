package com.uptome.client.core;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import javax.inject.Inject;

/**
 * Preferences helper class.
 *
 * @author Vladimir Rybkin
 */
public class PreferencesHelper {

    /**
     * Preferences instance
     */
    private final SharedPreferences mPrefs;

    /**
     * Constructor.
     *
     * @param context Context
     */
    @Inject
    public PreferencesHelper(Context context) {
        mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    /**
     * Get the "show intro" value.
     *
     * @return true to show, false to skip
     */
    public boolean getShowIntro() {
        return true;
    }

    /**
     * Set the show intro flag value.
     * @param value New value
     */
    public void setShowIntro(boolean value) {

    }
}
