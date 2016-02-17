package com.uptome.client.ui.common;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.uptome.client.R;

import flow.Flow;
import flow.path.Path;
import flow.path.PathContainer;
import flow.path.PathContainerView;

/**
 * Container layout to handle flow paths.
 *
 * @author Vladimir Rybkin
 */
public class PathFrameLayout extends FrameLayout implements PathContainerView {

    /**
     * Path handler
     */
    protected PathContainer mPathContainer;

    /**
     * Whether the container is disabled.
     */
    private boolean mIsDisabled;

    /**
     * Cosntructor.
     *
     * @param context Context to use
     */
    public PathFrameLayout(Context context) {
        super(context);
        initPathContainer();
    }

    /**
     * Cosntructor.
     *
     * @param context Context to use
     * @param attrs Attributes
     */
    public PathFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPathContainer();
    }

    /**
     * Cosntructor.
     *
     * @param context Context to use
     * @param attrs Attributes
     * @param defStyleAttr Default style attributes
     */
    public PathFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPathContainer();
    }

    /**
     * Apply a path container.
     */
    protected void initPathContainer() {
        mPathContainer = new DefaultPathContainer(R.id.default_container_tag,
                Path.contextFactory());
    }

    @Override
    public ViewGroup getCurrentChild() {
        return (ViewGroup) getContainerView().getChildAt(0);
    }

    @Override
    public ViewGroup getContainerView() {
        return this;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return !mIsDisabled && super.dispatchTouchEvent(ev);
    }

//    @Override
//    public boolean onBackPressed() {
//        return BackSupport.onBackPressed(getCurrentChild());
//        return false;
//    }

    @Override
    public void dispatch(Flow.Traversal traversal, final Flow.TraversalCallback callback) {
        mIsDisabled = true;
        mPathContainer.executeTraversal(this, traversal, new Flow.TraversalCallback() {
            @Override
            public void onTraversalCompleted() {
                callback.onTraversalCompleted();
                mIsDisabled = false;
            }
        });
    }
}
