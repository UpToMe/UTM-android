package com.uptome.client.ui.selftest;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import javax.inject.Inject;


/**
 * Self test screen view.
 *
 * @author Vladimir Rybkin
 */
public class SelfTestScreenView extends FrameLayout {

    @Inject SelfTestScreen.Presenter mPresenter;

    public SelfTestScreenView(Context context) {
        super(context);
        init(context);
    }

    public SelfTestScreenView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SelfTestScreenView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);

    }

    /**
     * Initialize.
     * @param context
     */
    protected void init(Context context) {
        //ObjectGraphService.inject(context, this);
    }

    @Override protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mPresenter.takeView(this);
    }


    @Override protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mPresenter.dropView(this);
    }
}
