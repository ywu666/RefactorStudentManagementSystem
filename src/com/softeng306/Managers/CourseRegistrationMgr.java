package com.softeng306.Managers;

import com.softeng306.*;
import com.softeng306.FILEMgr.CourseRegistrationFILEMgr;
import com.softeng306.Managers.MarkMgr;
import com.softeng306.SupportMgr.SupportCourseMgr;
import com.softeng306.SupportMgr.SupportCourseRegistrationMgr;
import com.softeng306.SupportMgr.SupportStudentMgr;

import java.util.*;
import java.util.stream.Collectors;

import static com.softeng306.CourseRegistration.LabComparator;
import static com.softeng306.CourseRegistration.LecComparator;
import static com.softeng306.CourseRegistration.TutComparator;


public class CourseRegistrationMgr {
    private static Scanner scanner = new Scanner(System.in);
    private static CourseRegistrationFILEMgr courseRegistrationFILEMgr = new CourseRegistrationFILEMgr();

    private static SupportCourseRegistrationMgr supportCourseRegistrationMgr;
    private static SupportCourseMgr supportCourseMgr;
    private static SupportStudentMgr supportStudentMgr;

    /**
     * Registers a course for a student
     */
    public static void registerCourse() {
        System.out.println("registerCourse is called");
        String selectedLectureGroupName = null;
        String selectedTutorialGroupName = null;
        String selectedLabGroupName = null;

        Student currentStudent = supportStudentMgr.checkStudentExists();
        String studentID = currentStudent.getStudentID();

        supportCourseRegistrationMgr.checkCourseDepartmentExists();

        Course currentCourse = supportCourseMgr.checkCourseExists();
        String courseID = currentCourse.getCourseID();


        if (SupportCourseRegistrationMgr.checkCourseRegistrationExists(studentID, courseID) != null) {
            return;
        }

        if (currentCourse.getMainComponents().size() == 0) {
            System.out.println("Professor " + currentCourse.getProfInCharge().getProfName() + " is preparing the assessment. Please try to register other courses.");
            return;
        }

        if (currentCourse.getVacancies() == 0) {
            System.out.println("Sorry, the course has no vacancies any more.");
            return;
        }

        System.out.println("Student " + currentStudent.getStudentName() + " with ID: " + currentStudent.getStudentID() +
                " wants to register " + currentCourse.getCourseID() + " " + currentCourse.getCourseName());

        ArrayList<Group> lecGroups = new ArrayList<>(0);
        lecGroups.addAll(currentCourse.getLectureGroups());

        selectedLectureGroupName = CourseRegistrationMgr.printGroupWithVacancyInfo("lecture", lecGroups);

        ArrayList<Group> tutGroups = new ArrayList<>(0);
        tutGroups.addAll(currentCourse.getTutorialGroups());

        selectedTutorialGroupName = CourseRegistrationMgr.printGroupWithVacancyInfo("tutorial", tutGroups);

        ArrayList<Group> labGroups = new ArrayList<>(0);
        labGroups.addAll(currentCourse.getLabGroups());

        selectedLabGroupName = CourseRegistrationMgr.printGroupWithVacancyInfo("lab", labGroups);

        currentCourse.enrolledIn();
        CourseRegistration courseRegistration = new CourseRegistration(currentStudent, currentCourse, selectedLectureGroupName, selectedTutorialGroupName, selectedLabGroupName);

        courseRegistrationFILEMgr.writeIntoFile(courseRegistration);

        Main.courseRegistrations.add(courseRegistration);

        Main.marks.add(MarkMgr.initializeMark(currentStudent, currentCourse));

        System.out.println("Course registration successful!");
        System.out.print("Student: " + currentStudent.getStudentName());
        System.out.print("\tLecture Group: " + selectedLectureGroupName);
        if (currentCourse.getTutorialGroups().size() != 0) {
            System.out.print("\tTutorial Group: " + selectedTutorialGroupName);
        }
        if (currentCourse.getLabGroups().size() != 0) {
            System.out.print("\tLab Group: " + selectedLabGroupName);
        }
        System.out.println();
    }

    /**
     * Prints the students in a course according to their lecture group, tutorial group or lab group.
     */
    public static void printStudents() {
        System.out.println("printStudent is called");
        Course currentCourse = supportCourseMgr.checkCourseExists();

        System.out.println("Print student by: ");
        System.out.println("(1) Lecture group");
        System.out.println("(2) Tutorial group");
        System.out.println("(3) Lab group");
        // READ courseRegistrationFILE
        // return ArrayList of Object(student,course,lecture,tut,lab)
        /**
         * This is changed due to the refactor
         */
        List<CourseRegistration> allStuArray = courseRegistrationFILEMgr.loadFromFile() ;


        ArrayList<CourseRegistration> stuArray = new ArrayList<CourseRegistration>(0);
        for (CourseRegistration courseRegistration : allStuArray) {
            if (courseRegistration.getCourse().getCourseID().equals(currentCourse.getCourseID())) {
                stuArray.add(courseRegistration);
            }
        }


        int opt;
        do {
            opt = scanner.nextInt();
            scanner.nextLine();

            System.out.println("------------------------------------------------------");

            if (stuArray.size() == 0) {
                System.out.println("No one has registered this course yet.");
            }

            if (opt == 1) { // print by LECTURE
                String newLec = "";
                Collections.sort(stuArray, LecComparator);   // Sort by Lecture group
                if (stuArray.size() > 0) {
                    for (int i = 0; i < stuArray.size(); i++) {  // loop through all of CourseRegistration Obj
                        if (!newLec.equals(stuArray.get(i).getLectureGroup())) {  // if new lecture group print out group name
                            newLec = stuArray.get(i).getLectureGroup();
                            System.out.println("Lecture group : " + newLec);
                        }
                        System.out.print("Student Name: " + stuArray.get(i).getStudent().getStudentName());
                        System.out.println(" Student ID: " + stuArray.get(i).getStudent().getStudentID());
                    }
                    System.out.println();
                }


            } else if (opt == 2) { // print by TUTORIAL
                String newTut = "";
                Collections.sort(stuArray, TutComparator);
                if (stuArray.size() > 0 && stuArray.get(0).getCourse().getTutorialGroups().size() == 0) {
                    System.out.println("This course does not contain any tutorial group.");
                } else if (stuArray.size() > 0) {
                    for (int i = 0; i < stuArray.size(); i++) {
                        if (!newTut.equals(stuArray.get(i).getTutorialGroup())) {
                            newTut = stuArray.get(i).getTutorialGroup();
                            System.out.println("Tutorial group : " + newTut);
                        }
                        System.out.print("Student Name: " + stuArray.get(i).getStudent().getStudentName());
                        System.out.println(" Student ID: " + stuArray.get(i).getStudent().getStudentID());
                    }
                    System.out.println();
                }

            } else if (opt == 3) { // print by LAB
                String newLab = "";
                Collections.sort(stuArray, LabComparator);
                if (stuArray.size() > 0 && stuArray.get(0).getCourse().getLabGroups().size() == 0) {
                    System.out.println("This course does not contain any lab group.");
                } else if (stuArray.size() > 0) {
                    for (int i = 0; i < stuArray.size(); i++) {
                        if (!newLab.equals(stuArray.get(i).getLabGroup())) {
                            newLab = stuArray.get(i).getLabGroup();
                            System.out.println("Lab group : " + newLab);
                        }
                        System.out.print("Student Name: " + stuArray.get(i).getStudent().getStudentName());
                        System.out.println(" Student ID: " + stuArray.get(i).getStudent().getStudentID());
                    }
                    System.out.println();
                }

            } else {
                System.out.println("Invalid input. Please re-enter.");
            }
            System.out.println("------------------------------------------------------");
        } while (opt < 1 || opt > 3);


    }


    /**
     * Checks whether the inputted department is valid.
     *
     * @param groupType The type of this group.
     * @param groups    An array list of a certain type of groups in a course.
     * @return the name of the group chosen by the user.
     */
    public static String printGroupWithVacancyInfo(String groupType, ArrayList<Group> groups) {
        int index;
        HashMap<String, Integer> groupAssign = new HashMap<String, Integer>(0);
        int selectedGroupNum;
        String selectedGroupName = null;

        if (groups.size() != 0) {
            System.out.println("Here is a list of all the " + groupType + " groups with available slots:");
            do {
                index = 0;
                for (Group group : groups) {
                    if (group.getAvailableVacancies() == 0) {
                        continue;
                    }
                    index++;
                    System.out.println(index + ": " + group.getGroupName() + " (" + group.getAvailableVacancies() + " vacancies)");
                    groupAssign.put(group.getGroupName(), index);
                }
                System.out.println("Please enter an integer for your choice:");
                selectedGroupNum = scanner.nextInt();
                scanner.nextLine();
                if (selectedGroupNum < 1 || selectedGroupNum > index) {
                    System.out.println("Invalid choice. Please re-enter.");
                } else {
                    break;
                }
            } while (true);

            for (HashMap.Entry<String, Integer> entry : groupAssign.entrySet()) {
                String groupName = entry.getKey();
                int num = entry.getValue();
                if (num == selectedGroupNum) {
                    selectedGroupName = groupName;
                    break;
                }
            }

            for (Group group : groups) {
                if (group.getGroupName().equals(selectedGroupName)) {
                    group.enrolledIn();
                    break;
                }
            }
        }
        return selectedGroupName;
    }






}
