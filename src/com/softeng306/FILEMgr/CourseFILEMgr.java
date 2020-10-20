package com.softeng306.FILEMgr;

import com.softeng306.*;
import com.softeng306.FILEMgr.FILEMgr;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class CourseFILEMgr implements FILEMgr<Course> {
    /**
     * The index of course vacancies in courseFile.csv.
     */
    private static final int vacanciesIndex = 3;

    /**
     * The index of course total seats in courseFile.csv.
     */
    private static final int totalSeatsIndex = 4;

    /**
     * The index of course lecture groups in courseFile.csv.
     */
    private static final int lectureGroupsIndex = 5;

    /**
     * The index of course tutorial groups in courseFile.csv.
     */
    private static final int tutorialGroupIndex = 6;

    /**
     * The index of course lab group in courseFile.csv.
     */
    private static final int labGroupIndex = 7;

    /**
     * The index of course main components in courseFile.csv.
     */
    private static final int mainComponentsIndex = 8;

    /**
     * The index of course AU in courseFile.csv.
     */
    private static final int AUIndex = 9;

    /**
     * The index of course department in courseFile.csv.
     */
    private static final int courseDepartmentIndex = 10;

    /**
     * The index of course type in courseFile.csv.
     */
    private static final int courseTypeIndex = 11;

    /**
     * The index of the course ID in courseFile.csv.
     */
    private static final int courseIdIndex = 0;

    /**
     * The index of the course name in courseFile.csv.
     */
    private static final int courseNameIndex = 1;

    /**
     * The index of course weekly lecture hour in courseFile.csv.
     */
    private static final int lecHrIndex = 12;

    /**
     * The index of course weekly tutorial hour in courseFile.csv.
     */
    private static final int tutHrIndex = 13;

    /**
     * The index of course weekly lab hour in courseFile.csv.
     */
    private static final int labHrIndex = 14;

    /**
     * The index of the professor in charge of this course in courseFile.csv.
     */
    private static final int profInChargeIndex = 2;

    /**
     * The file name of courseFile.csv.
     */
    private static final String courseFileName = "data/courseFile.csv";

    /**
     * The header of courseFile.csv.
     */
    private static final String course_HEADER = "courseID,courseName,profInCharge,vacancies,totalSeats,lectureGroups,TutorialGroups,LabGroups,MainComponents,AU,courseDepartment,courseType,lecHr,tutHr,labHr";

    @Override
    public void writeIntoFile(Course course) {
        File file;
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(courseFileName, true);
            //initialize file header if have not done so
            file = new File(courseFileName);
            if (file.length() == 0) {
                fileWriter.append(course_HEADER);
                fileWriter.append(NEW_LINE_SEPARATOR);
            }

            fileWriter.append(course.getCourseID());
            fileWriter.append(COMMA_DELIMITER);

            fileWriter.append(course.getCourseName());
            fileWriter.append(COMMA_DELIMITER);

            fileWriter.append(course.getProfInCharge().getProfID());
            fileWriter.append(COMMA_DELIMITER);

            fileWriter.append(String.valueOf(course.getVacancies()));
            fileWriter.append(COMMA_DELIMITER);

            fileWriter.append(String.valueOf(course.getTotalSeats()));
            fileWriter.append(COMMA_DELIMITER);

            ArrayList<LectureGroup> lectureGroups = course.getLectureGroups();
            if (lectureGroups.size() != 0) {
                int index = 0;
                for (LectureGroup lectureGroup : lectureGroups) {
                    fileWriter.append(lectureGroup.getGroupName());
                    fileWriter.append(EQUAL_SIGN);
                    fileWriter.append(String.valueOf(lectureGroup.getAvailableVacancies()));
                    fileWriter.append(EQUAL_SIGN);
                    fileWriter.append(String.valueOf(lectureGroup.getTotalSeats()));
                    index++;
                    if (index != lectureGroups.size()) {
                        fileWriter.append(LINE_DELIMITER);
                    }
                }
            } else {
                fileWriter.append("NULL");
            }
            fileWriter.append(COMMA_DELIMITER);

            ArrayList<TutorialGroup> tutorialGroups = course.getTutorialGroups();
            if (tutorialGroups.size() != 0) {
                int index = 0;
                for (TutorialGroup tutorialGroup : tutorialGroups) {
                    fileWriter.append(tutorialGroup.getGroupName());
                    fileWriter.append(EQUAL_SIGN);
                    fileWriter.append(String.valueOf(tutorialGroup.getAvailableVacancies()));
                    fileWriter.append(EQUAL_SIGN);
                    fileWriter.append(String.valueOf(tutorialGroup.getTotalSeats()));
                    index++;
                    if (index != tutorialGroups.size()) {
                        fileWriter.append(LINE_DELIMITER);
                    }
                }
            } else {
                fileWriter.append("NULL");
            }
            fileWriter.append(COMMA_DELIMITER);

            ArrayList<LabGroup> labGroups = course.getLabGroups();
            if (labGroups.size() != 0) {
                int index = 0;
                for (LabGroup labGroup : labGroups) {
                    fileWriter.append(labGroup.getGroupName());
                    fileWriter.append(EQUAL_SIGN);
                    fileWriter.append(String.valueOf(labGroup.getAvailableVacancies()));
                    fileWriter.append(EQUAL_SIGN);
                    fileWriter.append(String.valueOf(labGroup.getTotalSeats()));
                    index++;
                    if (index != labGroups.size()) {
                        fileWriter.append(LINE_DELIMITER);
                    }
                }
            } else {
                fileWriter.append("NULL");
            }
            fileWriter.append(COMMA_DELIMITER);

            ArrayList<MainComponent> mainComponents = course.getMainComponents();
            if (mainComponents.size() != 0) {
                int index = 0;
                for (MainComponent mainComponent : mainComponents) {
                    fileWriter.append(mainComponent.getComponentName());
                    fileWriter.append(EQUAL_SIGN);
                    fileWriter.append(String.valueOf(mainComponent.getComponentWeight()));
                    fileWriter.append(EQUAL_SIGN);
                    ArrayList<SubComponent> subComponents = mainComponent.getSubComponents();
                    int inner_index = 0;
                    for (SubComponent subComponent : subComponents) {
                        fileWriter.append(subComponent.getComponentName());
                        fileWriter.append(HYPHEN);
                        fileWriter.append(String.valueOf(subComponent.getComponentWeight()));
                        inner_index++;
                        if (inner_index != subComponents.size()) {
                            fileWriter.append(SLASH);
                        }
                    }
                    index++;
                    if (index != mainComponents.size()) {
                        fileWriter.append(LINE_DELIMITER);
                    }
                }
            } else {
                fileWriter.append("NULL");
            }
            fileWriter.append(COMMA_DELIMITER);
            fileWriter.append(String.valueOf(course.getAU()));
            fileWriter.append(COMMA_DELIMITER);
            fileWriter.append(course.getCourseDepartment());
            fileWriter.append(COMMA_DELIMITER);
            fileWriter.append(course.getCourseType());
            fileWriter.append(COMMA_DELIMITER);
            fileWriter.append(String.valueOf(course.getLecWeeklyHour()));
            fileWriter.append(COMMA_DELIMITER);
            fileWriter.append(String.valueOf(course.getTutWeeklyHour()));
            fileWriter.append(COMMA_DELIMITER);
            fileWriter.append(String.valueOf(course.getLabWeeklyHour()));
            fileWriter.append(NEW_LINE_SEPARATOR);
        } catch (Exception e) {
            System.out.println("Error in adding a course to the file.");
            e.printStackTrace();
        } finally {
            try {
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                System.out.println("Error occurs occurs when flushing or closing the file.");
                e.printStackTrace();
            }
        }

    }

    @Override
    public List<Course> loadFromFile() {
        ArrayList<Course> courses = new ArrayList<Course>(0);
        BufferedReader fileReader = null;
        try {
            String line;
            int thisProfessor = 0;
            Professor currentProfessor = null;

            /**
             * This is changed due to the reafacor
             */
            ProfessorFILEMgr professorFILEMgr = new ProfessorFILEMgr();
            List<Professor> professors = professorFILEMgr.loadFromFile();

            fileReader = new BufferedReader(new FileReader(courseFileName));
            fileReader.readLine();//read the header to skip it
            while ((line = fileReader.readLine()) != null) {
                String[] tokens = line.split(COMMA_DELIMITER);
                if (tokens.length > 0) {
                    String courseID = tokens[courseIdIndex];
                    String courseName = tokens[courseNameIndex];
                    String profInCharge = tokens[profInChargeIndex];
                    for (Professor professor : professors) {
                        if (professor.getProfID().equals(profInCharge)) {
                            currentProfessor = professor;
                            break;
                        }
                    }
                    int vacancies = Integer.parseInt(tokens[vacanciesIndex]);
                    int totalSeats = Integer.parseInt(tokens[totalSeatsIndex]);
                    int AU = Integer.parseInt(tokens[AUIndex]);
                    String courseDepartment = tokens[courseDepartmentIndex];
                    String courseType = tokens[courseTypeIndex];
                    int lecWeeklyHr = Integer.parseInt(tokens[lecHrIndex]);
                    int tutWeeklyHr = Integer.parseInt(tokens[tutHrIndex]);
                    int labWeeklyHr = Integer.parseInt(tokens[labHrIndex]);

                    String lectureGroupsString = tokens[lectureGroupsIndex];
                    ArrayList<LectureGroup> lectureGroups = new ArrayList<LectureGroup>(0);
                    String[] eachLectureGroupsString = lectureGroupsString.split(Pattern.quote(LINE_DELIMITER));

                    for (int i = 0; i < eachLectureGroupsString.length; i++) {
                        String[] thisLectureGroup = eachLectureGroupsString[i].split(EQUAL_SIGN);
                        lectureGroups.add(new LectureGroup(thisLectureGroup[0], Integer.parseInt(thisLectureGroup[1]), Integer.parseInt(thisLectureGroup[2])));
                    }

                    Course course = new Course(courseID, courseName, currentProfessor, vacancies, totalSeats, lectureGroups, AU, courseDepartment, courseType, lecWeeklyHr);

                    String tutorialGroupsString = tokens[tutorialGroupIndex];
                    ArrayList<TutorialGroup> tutorialGroups = new ArrayList<TutorialGroup>(0);

                    if (!tutorialGroupsString.equals("NULL")) {
                        String[] eachTutorialGroupsString = tutorialGroupsString.split(Pattern.quote(LINE_DELIMITER));
                        for (int i = 0; i < eachTutorialGroupsString.length; i++) {
                            String[] thisTutorialGroup = eachTutorialGroupsString[i].split(EQUAL_SIGN);
                            tutorialGroups.add(new TutorialGroup(thisTutorialGroup[0], Integer.parseInt(thisTutorialGroup[1]), Integer.parseInt(thisTutorialGroup[2])));
                        }
                    }
                    course.setTutorialGroups(tutorialGroups);
                    course.setTutWeeklyHour(tutWeeklyHr);

                    String labGroupsString = tokens[labGroupIndex];
                    ArrayList<LabGroup> labGroups = new ArrayList<LabGroup>(0);
                    if (!labGroupsString.equals("NULL")) {
                        String[] eachLabGroupString = labGroupsString.split(Pattern.quote(LINE_DELIMITER));
                        for (int i = 0; i < eachLabGroupString.length; i++) {
                            String[] thisLabGroup = eachLabGroupString[i].split(EQUAL_SIGN);
                            labGroups.add(new LabGroup(thisLabGroup[0], Integer.parseInt(thisLabGroup[1]), Integer.parseInt(thisLabGroup[2])));
                        }
                    }
                    course.setLabGroups(labGroups);
                    course.setLabWeeklyHour(labWeeklyHr);

                    String mainComponentsString = tokens[mainComponentsIndex];
                    ArrayList<MainComponent> mainComponents = new ArrayList<MainComponent>(0);
                    if (!mainComponentsString.equals("NULL")) {
                        String[] eachMainComponentsString = mainComponentsString.split(Pattern.quote(LINE_DELIMITER));
                        for (int i = 0; i < eachMainComponentsString.length; i++) {
                            String[] thisMainComponent = eachMainComponentsString[i].split(EQUAL_SIGN);
                            ArrayList<SubComponent> subComponents = new ArrayList<SubComponent>(0);
                            if (thisMainComponent.length > 2) {
                                String[] subComponentsString = thisMainComponent[2].split(SLASH);
                                for (int j = 0; j < subComponentsString.length; j++) {
                                    String[] thisSubComponent = subComponentsString[j].split(HYPHEN);
                                    subComponents.add(new SubComponent(thisSubComponent[0], Integer.parseInt(thisSubComponent[1])));
                                }
                            }

                            mainComponents.add(new MainComponent(thisMainComponent[0], Integer.parseInt(thisMainComponent[1]), subComponents));
                        }
                    }
                    course.setMainComponents(mainComponents);
                    course.setVacancies(vacancies);
                    courses.add(course);
                }
            }
        } catch (Exception e) {
            System.out.println("Error happens when loading courses.");
            e.printStackTrace();
        } finally {
            try {
                fileReader.close();
            } catch (IOException e) {
                System.out.println("Error happens when closing the fileReader.");
                e.printStackTrace();
            }
        }
        return courses;
    }
}
