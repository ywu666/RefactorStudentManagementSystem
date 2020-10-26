package com.softeng306.FILEMgr;

import com.softeng306.Entities.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class CourseFILEMgr extends FILEMgr<Course> {
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
        FileWriter fileWriter = null;
        try {
            fileWriter = initialiseFileWriter(courseFileName, course_HEADER);
            appendCourseToFileWriter(fileWriter, course);

        } catch (Exception e) {
            System.out.println("Error in adding a course to the file.");
            e.printStackTrace();
        } finally {
            printFinallyBlock(fileWriter);
        }

    }

    @Override
    public List<Course> loadFromFile() {
        ArrayList<Course> courses = new ArrayList<Course>(0);
        BufferedReader fileReader = null;
        try {
            String line;
            Professor currentProfessor = null;

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
                    ArrayList<Group> lectureGroups = new ArrayList<>(0);
                    splitLine(lectureGroupsString, lectureGroups);

                    Course course = new Course(courseID, courseName, currentProfessor, vacancies, totalSeats, lectureGroups, AU, courseDepartment, courseType, lecWeeklyHr);

                    String tutorialGroupsString = tokens[tutorialGroupIndex];
                    ArrayList<Group> tutorialGroups = new ArrayList<>(0);

                    if (!tutorialGroupsString.equals("NULL")) {
                        splitLine(tutorialGroupsString, tutorialGroups);
                    }
                    course.setTutorialGroups(tutorialGroups);
                    course.setTutWeeklyHour(tutWeeklyHr);

                    String labGroupsString = tokens[labGroupIndex];
                    ArrayList<Group> labGroups = new ArrayList<>(0);
                    if (!labGroupsString.equals("NULL")) {
                        splitLine(labGroupsString, labGroups);
                    }
                    course.setLabGroups(labGroups);
                    course.setLabWeeklyHour(labWeeklyHr);

                    String mainComponentsString = tokens[mainComponentsIndex];
                    ArrayList<MainComponent> mainComponents = new ArrayList<>(0);
                    if (!mainComponentsString.equals("NULL")) {
                        String[] eachMainComponentsString = mainComponentsString.split(Pattern.quote(LINE_DELIMITER));
                        for (int i = 0; i < eachMainComponentsString.length; i++) {
                            String[] thisMainComponent = eachMainComponentsString[i].split(EQUAL_SIGN);
                            ArrayList<SubComponent> subComponents = new ArrayList<>(0);
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
            printFinallyBlock(fileReader);
        }
        return courses;
    }

    private void splitLine(String lectureGroupsString, ArrayList<Group> lectureGroups) {
        String[] eachLectureGroupsString = lectureGroupsString.split(Pattern.quote(LINE_DELIMITER));

        for (int i = 0; i < eachLectureGroupsString.length; i++) {
            String[] thisLectureGroup = eachLectureGroupsString[i].split(EQUAL_SIGN);
            lectureGroups.add(new Group(thisLectureGroup[0], Integer.parseInt(thisLectureGroup[1]), Integer.parseInt(thisLectureGroup[2])));
        }
    }

    /**
     * Backs up all the changes of courses made into the file.
     *
     * @param courses courses to be backed up
     */
    public static void backUpCourse(List<Course> courses) {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(courseFileName);
            fileWriter.append(course_HEADER);
            fileWriter.append(NEW_LINE_SEPARATOR);

            for (Course course : courses) {
                appendCourseToFileWriter(fileWriter, course);
            }

        } catch (Exception e) {
            System.out.println("Error in backing up courses.");
            e.printStackTrace();
        } finally {
            try {
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                System.out.println("Error occurs when flushing or closing the file.");
                e.printStackTrace();
            }
        }
    }

    /**
     * Appends the specified course's information to the file writer
     * Used by backUpCourse and writeIntoFile
     */
    private static void appendCourseToFileWriter(FileWriter fileWriter, Course course) {
        try {
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

            List<Group> lectureGroups = course.getLectureGroups();
            appendGroupToFile(fileWriter, lectureGroups);

            List<Group> tutorialGroups = course.getTutorialGroups();
            appendGroupToFile(fileWriter, tutorialGroups);

            List<Group> labGroups = course.getLabGroups();
            appendGroupToFile(fileWriter, labGroups);

            List<MainComponent> mainComponents = course.getMainComponents();
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void appendGroupToFile(FileWriter fileWriter, List<? extends Group> groups) throws IOException {
        if (groups.size() != 0) {
            int index = 0;
            for (Group group : groups) {
                fileWriter.append(group.getGroupName());
                fileWriter.append(EQUAL_SIGN);
                fileWriter.append(String.valueOf(group.getAvailableVacancies()));
                fileWriter.append(EQUAL_SIGN);
                fileWriter.append(String.valueOf(group.getTotalSeats()));
                index++;
                if (index != groups.size()) {
                    fileWriter.append(LINE_DELIMITER);
                }
            }
        } else {
            fileWriter.append("NULL");
        }
        fileWriter.append(COMMA_DELIMITER);
    }
}
