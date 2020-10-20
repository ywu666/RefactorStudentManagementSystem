package com.softeng306.Managers;

import com.softeng306.Mark;

public interface IMarkMgr {

    Mark initializeMark();

    void setCourseWorkMark();

    double computeMark();

    void printCourseStatistics();

    void printStudentTranscript();

    double gpaCalculator();

}
