package com.softeng306.Managers;

import com.softeng306.Entities.Course;
import com.softeng306.Entities.Mark;
import com.softeng306.Entities.Student;
import com.softeng306.SupportMgr.SupportCourseMgr;
import com.softeng306.SupportMgr.SupportStudentMgr;

import java.util.ArrayList;
import java.util.List;

public interface IMarkMgr {

    Mark initializeMark(Student student, Course course);

    void setCourseWorkMark(boolean isExam);

    double computeMark(ArrayList<Mark> thisCourseMark, String thisComponentName);

    void printCourseStatistics();

    void printStudentTranscript();

    double gpaCalculator(double result);

    List<Mark> getMarks();

    void addMark(Mark mark);

    void setSupportCourseMgr(SupportCourseMgr supportCourseMgr);

    void setSupportStudentMgr(SupportStudentMgr supportStudentMgr);
}
