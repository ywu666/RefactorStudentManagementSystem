package com.softeng306.Managers;

import com.softeng306.Entities.CourseRegistration;
import com.softeng306.SupportMgr.SupportCourseMgr;
import com.softeng306.SupportMgr.SupportCourseRegistrationMgr;
import com.softeng306.SupportMgr.SupportStudentMgr;

import java.util.List;

/**
 * Interface for implementing a course registration manager.
 */
public interface ICourseRegistrationMgr {

    void registerCourse();

    void printStudents();

    void setMarkMgr(IMarkMgr markMgr);

    List<CourseRegistration> getCourseRegistrations();

    void setSupportCourseRegistrationMgr(SupportCourseRegistrationMgr supportCourseRegistrationMgr);

    void setSupportStudentMgr(SupportStudentMgr supportStudentMgr);

    void setSupportCourseMgr(SupportCourseMgr supportCourseMgr);
}
