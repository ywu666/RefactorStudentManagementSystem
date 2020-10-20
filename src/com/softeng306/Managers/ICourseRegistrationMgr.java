package com.softeng306.Managers;

import com.softeng306.CourseRegistration;

public interface ICourseRegistrationMgr {

    void registerCourse();

    void printStudents();

    CourseRegistration checkCourseRegistrationExists();

}
