package com.softeng306.Managers;

import com.softeng306.CourseRegistration;

public interface ICourseRegistrationMgr {

    void registerCourse();

    void printStudents();

    CourseRegistration checkCourseRegistrationExists(String studentID, String courseID);

    void setCourseMgr(ICourseMgr courseMgr);

    void setMarkMgr(IMarkMgr markMgr);

    void setStudentMgr(IStudentMgr studentMgr);
}
