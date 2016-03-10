package com.uptome.client.ui.registration;

import com.uptome.client.R;
import com.uptome.client.core.android.ActionBarOwner;
import com.uptome.client.core.engine.Engine;
import com.uptome.client.core.log.ClientLog;
import com.uptome.client.core.model.IRegistration;
import com.uptome.client.core.scopes.ViewScope;
import com.uptome.client.ui.common.Layout;
import com.uptome.client.ui.common.ViewPresenter;

import javax.inject.Inject;

import dagger.Provides;
import flow.Flow;
import flow.path.Path;

/**
 * Registration representer.
 *
 * @author Vladimir Rybkin
 */
@Layout(R.layout.registration_layout)
public class RegistrationScreen extends Path {

    final int mIndex;
    final int mNextIndex;

    public RegistrationScreen(int index, int nextIndex) {
        mIndex = index;
        mNextIndex = nextIndex;
    }

    @dagger.Module
    public class Module {

        @ViewScope
        @Provides
        RegistrationScreen.Presenter providePresenter(IRegistration registration) {
            IRegistration.IQuestion question = registration.getQuestion(mIndex);
            return new Presenter(question, mNextIndex);
        }
    }

    @ViewScope
    public static class Presenter extends ViewPresenter<RegistrationScreenView> {

        private static final String LOG_TAG = Presenter.class.getSimpleName();

        @Inject
        Engine mEngine;

        @Inject
        ActionBarOwner mActionBarOwner;

        /*
         * Question data
         */
        IRegistration.IQuestion mQuestion;
        int mNextIndex;

        @Inject
        public Presenter(IRegistration.IQuestion question, int nextIndex) {
            mQuestion = question;
            mNextIndex = nextIndex;
        }

        @Override
        public void takeView(RegistrationScreenView view) {
            super.takeView(view);
            if (hasView() == false) {
                return;
            }

            view.getRegistrationScreenComponent().inject(this);

            mActionBarOwner.setActionBarVisibility(true);
            getView().setQuestion(mQuestion, mNextIndex < 0);
        }

        /**
         * Set question answer.
         *
         * @param answer Answer string
         */
        void onAnswer(String answer) {
            mQuestion.setAnswer(answer);
            onNextQuestion();
        }

        /**
         * Transition to the next question
         */
        private void onNextQuestion() {
            if (mNextIndex > -1) {
                int indexAfter = mEngine.getRegistrationInterface().getNextQuestionIndex(mNextIndex);
                Flow.get(getView().getContext()).set(new RegistrationScreen(mNextIndex, indexAfter));
            } else {
                ClientLog.d(LOG_TAG, "Registration is completed successfully");
                mEngine.onRegistrationCompleted();
            }
        }
    }
}
