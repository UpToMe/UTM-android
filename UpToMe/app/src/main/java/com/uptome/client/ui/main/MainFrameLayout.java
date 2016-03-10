package com.uptome.client.ui.main;

import android.content.Context;
import android.util.AttributeSet;

import com.uptome.client.ui.common.PathFrameLayout;

/**
 * The main container.
 *
 * @author Vladimir Rybkin
 */
public class MainFrameLayout extends PathFrameLayout {

    /**
     * Cosntructor.
     *
     * @param context Context to use
     */
    public MainFrameLayout(Context context) {
        super(context);
    }

    /**
     * Cosntructor.
     *
     * @param context Context to use
     * @param attrs Attributes
     */
    public MainFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Cosntructor.
     *
     * @param context Context to use
     * @param attrs Attributes
     * @param defStyleAttr Default style attributes
     */
    public MainFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
