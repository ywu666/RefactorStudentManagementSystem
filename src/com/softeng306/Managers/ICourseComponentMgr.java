package com.softeng306.Managers;

import com.softeng306.Entities.Course;
import com.softeng306.SupportMgr.SupportCourseMgr;

/**
 * Interface for implementing a course component manager.
 */
public interface ICourseComponentMgr {
    void enterCourseWorkComponentWeightage(Course course);

    void setSupportCourseMgr(SupportCourseMgr supportCourseMgr);
}
