package com.uptome.client.core.model.pojo;

import android.content.Context;
import android.content.res.Resources;

import com.uptome.client.R;
import com.uptome.client.core.log.ClientLog;
import com.uptome.client.core.model.IRegistration;

import java.util.ArrayList;
import java.util.List;

/**
 * Registration instance.
 *
 * @author Vladimir Rybkin
 */
public class Registration implements IRegistration {

    /**
     * Tag for logging.
     */
    private static final String LOG_TAG = Registration.class.getSimpleName();

    /**
     * List of questions
     */
    private List<Question> mQuestions = new ArrayList<>(4);

    /**
     * Constructor.
     *
     * @param context Context to use
     */
    private Registration(Context context) {
        runtimeInit(context);
    }

    /**
     * Initialize the instance
     */
    public static Registration create(Context context) {
        ClientLog.d(LOG_TAG, "Start the registration proxy initializaation");
        Registration instance = new Registration(context);
        ClientLog.d(LOG_TAG, "The registration proxy initializaation completed successfully");
        return instance;
    }

    @Override
    public boolean isCompleted() {
        for (Question q : mQuestions) {
            if (q.isAnswered() == false) {
                return false;
            }
        }

        return true;
    }

    @Override
    public IQuestion getQuestion(int index) {
        if (index > mQuestions.size() - 1) {
            return null;
        }

        return mQuestions.get(index);
    }

    @Override
    public int getNextQuestionIndex(int currentIndex) {
        if (currentIndex >= mQuestions.size() - 1) {
            return -1;
        }

        for (int i = currentIndex + 1; i < mQuestions.size(); i++) {
            if (mQuestions.get(i).isAnswered() == false) {
                return i;
            }
        }


        return -1;
    }

    @Override
    public int getCount() {
        return 0;
    }

    /**
     * Runtime initialization
     *
     * TODO: replace with something better
     *
     * @param context Context to use
     */
    private void runtimeInit(Context context) {
        Resources res = context.getResources();
        mQuestions.add(new Question(res.getString(R.string.registration_q1), false, true, false));
        mQuestions.add(new Question(res.getString(R.string.registration_q2), true, false, false));
        mQuestions.add(new Question(res.getString(R.string.registration_q3), true, false, false));
        mQuestions.add(new Question(res.getString(R.string.registration_q4), true, false, false));
    }

    /**
     * Local question class.
     */
    private class Question implements IQuestion {

        /*
         * Question fileds
         */
        private final String mText;
        private final boolean mNumbersAllowed;
        private final boolean mTextAllowed;
        private final boolean mEmptyAllowed;

        /**
         * Answer
         */
        private String mAnswer;

        /**
         * Contructor.
         *
         * @param text Question text
         * @param numbersAllowed Whether numbers are allowed
         * @param textAllowed Whether text is allowed
         * @param emptyAllowed Whether the empty answer is allowed
         */
        Question(String text, boolean numbersAllowed, boolean textAllowed, boolean emptyAllowed) {
            mText = text;
            mNumbersAllowed = numbersAllowed;
            mTextAllowed = textAllowed;
            mEmptyAllowed = emptyAllowed;
        }

        @Override
        public boolean isAnswered() {
            return mAnswer != null;
        }

        @Override
        public String getText() {
            return mText;
        }

        @Override
        public String getStoredAnswer() {
            return mAnswer;
        }

        @Override
        public void setAnswer(String answer) {
            mAnswer = answer;
            // TODO store the answer
        }

        @Override
        public boolean isNumbersAllowed() {
            return mNumbersAllowed;
        }

        @Override
        public boolean isTextAllowed() {
            return mTextAllowed;
        }

        @Override
        public boolean isEmptyAllowed() {
            return mEmptyAllowed;
        }
    }
}