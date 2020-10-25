package com.softeng306.Managers;

import com.softeng306.Course;
import com.softeng306.Mark;
import com.softeng306.Student;

import java.util.ArrayList;
import java.util.List;

public interface IMarkMgr {

    Mark initializeMark(Student student, Course course);

    void setCourseWorkMark(boolean isExam);

    double computeMark(ArrayList<Mark> thisCourseMark, String thisComponentName);

    void printCourseStatistics();

    void printStudentTranscript();

    double gpaCalculator(double result);

    void setCourseMgr(ICourseMgr courseMgr);

    void setStudentMgr(IStudentMgr studentMgr);

    List<Mark> getMarks();

    void addMark(Mark mark);
}
