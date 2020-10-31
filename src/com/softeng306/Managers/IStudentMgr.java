package com.softeng306.Managers;

import com.softeng306.Entities.Student;
import com.softeng306.SupportMgr.SupportStudentMgr;

import java.util.List;

/**
 * Interface for implementing a student manager.
 */
public interface IStudentMgr {

    void addStudent();

    List<Student> getStudents();

    void setSupportStudentMgr(SupportStudentMgr supportStudentMgr);
}
