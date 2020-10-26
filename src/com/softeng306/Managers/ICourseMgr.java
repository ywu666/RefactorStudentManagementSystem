package com.softeng306.Managers;

import com.softeng306.Course;
import com.softeng306.SupportMgr.SupportCourseMgr;
import com.softeng306.SupportMgr.SupportProfessorMgr;

import java.util.List;

public interface ICourseMgr {

    void addCourse();

    void checkAvailableSlots();

    List<Course> getCourses();

    void setProfessorMgr(IProfessorMgr professorMgr);

    void setSupportCourseMgr(SupportCourseMgr supportCourseMgr);

    void setSupportProfessorMgr(SupportProfessorMgr supportProfessorMgr);
}
