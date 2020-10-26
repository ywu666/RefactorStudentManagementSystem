package com.softeng306.Managers;

import com.softeng306.Entities.Course;
import com.softeng306.SupportMgr.SupportCourseMgr;

public interface ICourseComponentMgr {
    void enterCourseWorkComponentWeightage(Course course);

    void setSupportCourseMgr(SupportCourseMgr supportCourseMgr);
}
