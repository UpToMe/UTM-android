package com.uptome.client.ui.error;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import javax.inject.Inject;

/**
 * Splash screen view.
 */
public class ErrorScreenView extends FrameLayout {

    @Inject ErrorScreen.Presenter mPresenter;

    public ErrorScreenView(Context context) {
        super(context);
        init(context);
    }

    public ErrorScreenView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ErrorScreenView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);

    }

    /**
     * Initialize
     * @param context
     */
    protected void init(Context context) {
        //ObjectGraphService.inject(context, this);
    }

    @Override protected void onAttachedToWindow() {
        super.onAttachedToWindow();
      //  mPresenter.takeView(this);
    }


    @Override protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
      //  mPresenter.dropView(this);
    }
}
