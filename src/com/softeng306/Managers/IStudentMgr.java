package com.softeng306.Managers;

import com.softeng306.Student;
import com.softeng306.SupportMgr.SupportStudentMgr;

import java.util.List;

public interface IStudentMgr {

    void addStudent();

    List<Student> getStudents();

    void setSupportStudentMgr(SupportStudentMgr supportStudentMgr);
}
