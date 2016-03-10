package com.uptome.client.ui.registration;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.uptome.client.R;
import com.uptome.client.RootComponent;
import com.uptome.client.UpToMeApplication;
import com.uptome.client.core.model.IRegistration;
import com.uptome.client.ui.common.IPathApply;

import javax.inject.Inject;

import flow.path.Path;

/**
 * Registration screen view.
 *
 * @author Vladimir Rybkin
 */
public class RegistrationScreenView extends RelativeLayout implements IPathApply {

    @Inject
    RegistrationScreen.Presenter mPresenter;

    /**
     * Screen component
     */
    private RegistrationScreenComponent mRegistrationScreenComponent;

    public RegistrationScreenView(Context context) {
        super(context);
    }

    public RegistrationScreenView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RegistrationScreenView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * Initialize.
     *
     * @param to Path
     */
    protected void init(Path to) {
        Context context = getContext();
        RegistrationScreen screen = (RegistrationScreen) to;
        RootComponent root = (RootComponent) context.getApplicationContext().getSystemService(UpToMeApplication.ROOT_NAME);
        mRegistrationScreenComponent = DaggerRegistrationScreenComponent.builder().rootComponent(root).module(screen.new Module()).build();
        mRegistrationScreenComponent.inject(this);
    }

    @Override
    public void applyPath(Path to) {
        init(to);
    }

    @Override protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mPresenter.takeView(this);
    }

    @Override protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mPresenter.dropView(this);
    }

    /**
     * Set the question.
     *
     * @param question Question instance
     * @param lastQuestion True if the question is last
     */
    void setQuestion(final IRegistration.IQuestion question, boolean lastQuestion) {
        ((TextView)findViewById(R.id.text_question)).setText(question.getText());

        String answer = question.getStoredAnswer();
        if (answer != null) {

        }

        TextView ok = (TextView) findViewById(R.id.btn_ok);
        ok.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String answer = ((EditText) findViewById(R.id.edit_answer)).getText().toString();
                mPresenter.onAnswer(answer);
            }
        });

        ok.setText(!lastQuestion ? R.string.btn_ok : R.string.btn_finish);
    }

    /**
     * Get the screen component.
     *
     * @return instance
     */
    public RegistrationScreenComponent getRegistrationScreenComponent() {
        return mRegistrationScreenComponent;
    }
}
