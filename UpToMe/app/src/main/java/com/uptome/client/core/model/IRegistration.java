package com.uptome.client.core.model;

/**
 * Registration interface
 *
 * @author Vladimir Rybkin
 */
public interface IRegistration {

    /**
     * Question interface
     */
    interface IQuestion {

        /**
         * Get whether the answer is answered.
         *
         * @return true if answered, false otherwise
         */
        boolean isAnswered();

        /**
         * Get the question text.
         *
         * @return string
         */
        String getText();

        /**
         * Get the answer stored.
         *
         * @return string or null
         */
        String getStoredAnswer();

        /**
         * Set the answer.
         */
        void setAnswer(String answer);

        /**
         * Get whether number allowed.
         *
         * @return true or false
         */
        boolean isNumbersAllowed();

        /**
         * Get whether the text symbols allowed.
         *
         * @return true or false
         */
        boolean isTextAllowed();

        /**
         * Get whether an empty answer allowed.
         *
         * @return true or false
         */
        boolean isEmptyAllowed();
    }

    /**
     * Whether the registration is completed.
     *
     * @return flag value
     */
    boolean isCompleted();

    /**
     * Get the current question.
     *
     * @param index Question index
     *
     * @return instance or null
     */
    IQuestion getQuestion(int index);

    /**
     * Get the next open question index.
     *
     * @param currentIndex The current index or -1
     * @return next question index to answer or -1
     */
    int getNextQuestionIndex(int currentIndex);

    /**
     * Get the questions number.
     *
     * @return number of questions
     */
    int getCount();
}
