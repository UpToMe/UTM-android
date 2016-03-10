package com.uptome.client.core.engine;

import com.uptome.client.core.model.ICurrentCourse;
import com.uptome.client.core.model.IRegistration;
import com.uptome.client.core.model.ISelfTesting;
import com.uptome.client.core.model.ISkills;

/**
 * Engine interface
 *
 * @author Vladimir Rybkin
 */
public interface IEngine {

    /*
     * States
     */
    int STATE_UNKNOWN = -1;
    int STATE_INIT = 0;
    int STATE_INTRO = 1;
    int STATE_REGISTRATION = 2;
    int STATE_SELF_TEST = 3;
    int STATE_SKILLS = 4;
    int STATE_COURSE = 5;
    int STATE_ERROR = 6;

    /**
     * Get the engine state.
     *
     * @return value
     */
    int getState();

    /**
     * Get a registration interface.
     *
     * @return instance
     */
    IRegistration getRegistrationInterface();

    /**
     * Get the selftesting interface.
     *
     * @return instance
     */
    ISelfTesting getSelfTestingInterface();

    /**
     * Get the skills interface.
     *
     * @return instance
     */
    ISkills getSkillsInterface();

    /**
     * Get the current course interface.
     *
     * @return instance or null
     */
    ICurrentCourse getCurrentCourseInterface();
}
