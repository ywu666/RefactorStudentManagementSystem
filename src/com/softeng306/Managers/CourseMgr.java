package com.softeng306.Managers;


import com.softeng306.Entities.*;
import  com.softeng306.FILEMgr.CourseFILEMgr;
import com.softeng306.FILEMgr.FILEMgr;


import com.softeng306.SupportMgr.SupportCourseMgr;
import com.softeng306.SupportMgr.SupportProfessorMgr;

import java.util.*;
import java.io.PrintStream;
import java.io.OutputStream;

public class CourseMgr implements ICourseMgr {
    private static final Scanner scanner = new Scanner(System.in);
    private static final PrintStream originalStream = System.out;
    private static final PrintStream dummyStream = new PrintStream(new OutputStream() {
        public void write(int b) {
            // NO-OP
        }
    });

    private FILEMgr<Course> courseFILEMgr = new CourseFILEMgr();

    private List<Course> courses = courseFILEMgr.loadFromFile();

    private IProfessorMgr professorMgr;

    private SupportCourseMgr supportCourseMgr;

    private SupportProfessorMgr supportProfessorMgr;

    // the maximum number of academic units a course may have
    private final int maxNumAU = 10;

    /**
     * Creates a new course and stores it in the file.
     */
    public void addCourse() {
        String courseID;
        String courseName;
        int seatsLeft;
        // Can make the sameCourseID as boolean, set to false.
        do {
            System.out.println("Give this course an ID: ");
            courseID = scanner.nextLine();

        } while (!supportCourseMgr.checkValidCourseIDInput(courseID) || supportCourseMgr.checkCourseExists(courseID) != null);


        System.out.println("Enter course Name: ");
        courseName = scanner.nextLine();
       // set total seats
        int totalSeats = setTotalSeats();
        // set total AU
        int AU = setAU();
        // set department
        String courseDepartment = setDepartment();
        //  set courseType
        String courseType = setCourseType();


        /*      Lecture groups       */
        //  set number of lecture groups
        int noOfLectureGroups = setNoOfGroups(totalSeats, "lecture", "Number of lecture group must be positive but less than total seats in this course.");
        //  set number of weekly lecture hours
        int lecWeeklyHour = setWeeklyHour(AU, "lecture");


        ArrayList<IGroup> lectureGroups = new ArrayList<>();
        String lectureGroupName;
        int lectureGroupCapacity;
        seatsLeft = totalSeats;
        for (int i = 0; i < noOfLectureGroups; i++) {

            lectureGroupName = setGroupName(lectureGroups, "lecture");
            do {
                //  set lecture group capacity
                lectureGroupCapacity = setLectureGroupCapacity();
                //  check if valid, if yes create new lecture group and add to array
                seatsLeft -= lectureGroupCapacity;
                if ((seatsLeft > 0 && i != (noOfLectureGroups - 1)) || (seatsLeft == 0 && i == noOfLectureGroups - 1)) {
                    IGroup lectureGroup = new Group(lectureGroupName, lectureGroupCapacity, lectureGroupCapacity);
                    lectureGroups.add(lectureGroup);
                    break;
                } else {
                    System.out.println("Sorry, the total capacity you allocated for all the lecture groups exceeds or does not add up to the total seats for this course.");
                    System.out.println("Please re-enter the capacity for the last lecture group " + lectureGroupName + " you have entered.");
                    seatsLeft += lectureGroupCapacity;
                }
            } while (true);
        }

        /*   Tutorial groups    */
        int totalTutorialSeats = 0;
        // set number of tutorial groups
        int noOfTutorialGroups = setNoOfGroups(totalSeats, "tutorial", "Number of tutorial group must be non-negative.");
        // set tutorial weekly hours
        int tutWeeklyHour = 0;
        if (noOfTutorialGroups != 0) {
            tutWeeklyHour = setWeeklyHour(AU, "tutorial");
        }
        ArrayList<IGroup> tutorialGroups = new ArrayList<>();
        String tutorialGroupName;
        for (int i = 0; i < noOfTutorialGroups; i++) {
            tutorialGroupName = setGroupName(tutorialGroups, "tutorial");
            totalTutorialSeats = getTotalTutorialSeats(totalSeats, totalTutorialSeats, noOfTutorialGroups, tutorialGroups, tutorialGroupName, i);
        }


        /*         lab groups       */
        int totalLabSeats = 0;
        // set number of lab groups
        int noOfLabGroups = setNoOfGroups(totalSeats, "lab", "Number of lab group must be non-negative.");
        // set lab weekly hours
        int labWeeklyHour = 0;
        if (noOfLabGroups != 0) {
            labWeeklyHour = setWeeklyHour(AU, "lab");
        }
        ArrayList<IGroup> labGroups = new ArrayList<>();
        String labGroupName;
        for (int i = 0; i < noOfLabGroups; i++) {
        //  set lab group name
            labGroupName = setGroupName(labGroups, "lab");
        //  set lab seats
            totalLabSeats = getTotalLabSeats(totalSeats, totalLabSeats, noOfLabGroups, labGroups, labGroupName, i);
        }

        //   set professor in charge
        Professor profInCharge = setProfessorInCharge(courseDepartment);


        Course course = new Course(courseID, courseName, profInCharge, totalSeats, totalSeats, lectureGroups, tutorialGroups, labGroups, AU, courseDepartment, courseType, lecWeeklyHour, tutWeeklyHour, labWeeklyHour);
        // ask user to input course component --- 1: yes, 2: no
        int addCourseComponentChoice = promptUserToAddCourseComponent();
        if (addCourseComponentChoice == 2) {
            //add course into file without adding components
            addCourseIntoFile(courseID, course, " is added, but assessment components are not initialized.");
            return;
        }
        //        if 1: yes enter now and add course components
        //initialize course component
        ICourseComponentMgr courseComponentMgr = new CourseComponentMgr();
        courseComponentMgr.enterCourseWorkComponentWeightage(course);
        //add course into file
        addCourseIntoFile(courseID, course, " is added");
    }


    /**
     * Write course into file
     * @param courseID The course ID of course being added
     * @param course The course to be added
     * @param s The string dependant on if components are added or not
     */
    private void addCourseIntoFile(String courseID, Course course, String s) {
        FILEMgr<Course> courseFileEMgr = new CourseFILEMgr();
        courseFileEMgr.writeIntoFile(course);
        courses.add(course);
        System.out.println("Course " + courseID + s);
        supportCourseMgr.printCourses();
    }

    /**
     * Ask user to add coursework component?
     * @return 1 if Yes or 2 if no
     */
    private int promptUserToAddCourseComponent() {
        System.out.println("Create course components and set component weightage now?");
        System.out.println("1. Yes");
        System.out.println("2. Not yet");
        int addCourseComponentChoice;
        addCourseComponentChoice = scanner.nextInt();
        scanner.nextLine();

        while (addCourseComponentChoice > 2 || addCourseComponentChoice < 0) {
            System.out.println("Invalid choice, please choose again.");
            System.out.println("1. Yes");
            System.out.println("2. Not yet");
            addCourseComponentChoice = scanner.nextInt();
            scanner.nextLine();
        }
        return addCourseComponentChoice;
    }

    /**
     * Set professor in charge of course
     * @param courseDepartment The department of current course
     * @return Professor in charge of course
     */
    private Professor setProfessorInCharge(String courseDepartment) {
        String profID;
        Professor profInCharge;
        List<String> professorsInDepartment;
        professorsInDepartment = professorMgr.printProfInDepartment(courseDepartment, false);
        while (true) {

            System.out.println("Enter the ID for the professor in charge please:");
            System.out.println("Enter -h to print all the professors in " + courseDepartment + ".");
            profID = scanner.nextLine();
            while ("-h".equals(profID)) {
                professorsInDepartment = professorMgr.printProfInDepartment(courseDepartment, true);
                profID = scanner.nextLine();
            }

            System.setOut(dummyStream);
            profInCharge = supportProfessorMgr.checkProfExists(profID);
            System.setOut(originalStream);
            if (profInCharge != null) {
                assert professorsInDepartment != null;
                if (professorsInDepartment.contains(profID)) {
                    break;
                } else {
                    System.out.println("This prof is not in " + courseDepartment + ".");
                    System.out.println("Thus he/she cannot teach this course.");
                }
            } else {
                System.out.println("Invalid input. Please re-enter.");

            }
        }
        return profInCharge;
    }

    /**
     * Set total lab seats
     * @param totalSeats    The total seats in course
     * @param totalLabSeats The total lab seats in course
     * @param noOfLabGroups Number of lab groups in course
     * @param labGroups     Array containing labGroups
     * @param labGroupName  Current labGroup
     * @param i             index of lab group being added
     * @return The total number of lab seats
     */
    private int getTotalLabSeats(int totalSeats, int totalLabSeats, int noOfLabGroups, ArrayList<IGroup> labGroups, String labGroupName, int i) {
        int labGroupCapacity;
        do {
            System.out.println("Enter this lab group's capacity: ");
            labGroupCapacity = scanner.nextInt();
            scanner.nextLine();
            totalLabSeats += labGroupCapacity;
            if ((i != noOfLabGroups - 1) || (totalLabSeats >= totalSeats)) {
                IGroup labGroup = new Group(labGroupName, labGroupCapacity, labGroupCapacity);
                labGroups.add(labGroup);
                break;
            } else {
                System.out.println("Sorry, the total capacity you allocated for all the lab groups is not enough for this course.");
                System.out.println("Please re-enter the capacity for the last lab group " + labGroupName + " you have entered.");
                totalLabSeats -= labGroupCapacity;
            }
        } while (true);
        return totalLabSeats;
    }

    /**
     * Set tutorial groups for course
     * @param totalSeats         The total seats in course
     * @param totalTutorialSeats The total tutorial seats in lab group
     * @param noOfTutorialGroups The total number of tutorial groups
     * @param tutorialGroups     The list of tutorial groups
     * @param tutorialGroupName  The current name of the tutorial group
     * @param i                  The index of current tutorial group
     * @return The total tutorial seats in lab group
     */
    private int getTotalTutorialSeats(int totalSeats, int totalTutorialSeats, int noOfTutorialGroups, ArrayList<IGroup> tutorialGroups, String tutorialGroupName, int i) {
        int tutorialGroupCapacity;
        do {
            System.out.println("Enter this tutorial group's capacity: ");
            if (scanner.hasNextInt()) {
                tutorialGroupCapacity = scanner.nextInt();
                scanner.nextLine();
                totalTutorialSeats += tutorialGroupCapacity;
                if ((i != noOfTutorialGroups - 1) || (totalTutorialSeats >= totalSeats)) {
                    IGroup tutorialGroup = new Group(tutorialGroupName, tutorialGroupCapacity, tutorialGroupCapacity);
                    tutorialGroups.add(tutorialGroup);
                    break;
                } else {
                    System.out.println("Sorry, the total capacity you allocated for all the tutorial groups is not enough for this course.");
                    System.out.println("Please re-enter the capacity for the last tutorial group " + tutorialGroupName + " you have entered.");
                    totalTutorialSeats -= tutorialGroupCapacity;
                }
            } else {
                System.out.println("Your input " + scanner.nextLine() + " is not an integer.");
            }
        } while (true);
        return totalTutorialSeats;
    }

    /**
     * Set the name of group
     * @param groups The list of groups
     * @param s1     The type of group
     * @return The name of group
     */
    private String setGroupName(ArrayList<IGroup> groups, String s1) {
        String GroupName;
        boolean groupNameExists;
        System.out.println("Give a name to the " + s1 + " group");
        do {
            groupNameExists = false;
            System.out.println("Enter a group Name: ");
            GroupName = scanner.nextLine();
            if (!supportCourseMgr.checkValidGroupNameInput(GroupName)) {
                groupNameExists = true;
                continue;
            }
            if (groups.size() == 0) {
                break;
            }
            for (IGroup group : groups) {
                if (group.getGroupName().equals(GroupName)) {
                    groupNameExists = true;
                    System.out.println("This " + s1 +  " group already exist for this course.");
                    break;
                }
            }
        } while (groupNameExists);
        return GroupName;
    }

    /**
     * Sets the number of groups
     * @param totalSeats The Total seats in course
     * @param s1         The type of group
     * @param s2         The invalid string output
     * @return The number of groups
     */
    private int setNoOfGroups(int totalSeats, String s1, String s2) {
        int noOfGroups;
        do {
            System.out.println("Enter the number of " + s1 + " groups:");
            if (scanner.hasNextInt()) {
                noOfGroups = scanner.nextInt();
                scanner.nextLine();
                if (noOfGroups > 0 && noOfGroups <= totalSeats) {
                    break;
                }
                System.out.println("Invalid input.");
                System.out.println(s2);
                System.out.println("Please re-enter");
            } else {
                System.out.println("Your input " + scanner.nextLine() + " is not an integer.");
            }
        } while (true);
        return noOfGroups;
    }


    /**
     * Set the capacity of lecture groups
     * @return The lecture group capacity
     */
    private int setLectureGroupCapacity() {
        int lectureGroupCapacity;
        System.out.println("Enter this lecture group's capacity: ");
        do {
            if (scanner.hasNextInt()) {
                lectureGroupCapacity = scanner.nextInt();
                scanner.nextLine();
                if (lectureGroupCapacity > 0) {
                    break;
                }
                System.out.println("Capacity must be positive. Please re-enter.");
            } else {
                System.out.println("Your input " + scanner.nextLine() + " is not an integer.");
            }
        } while (true);
        return lectureGroupCapacity;
    }

    /**
     * Set weekly hours for lecture,lab,tutorial
     * @param AU The AU of the course
     * @param s1 The type of group
     * @return The number of weekly hours
     */
    private int setWeeklyHour(int AU, String s1) {
        int WeeklyHour;
        while (true) {
            System.out.println("Enter the weekly " + s1 + " hour for this course: ");
            if (scanner.hasNextInt()) {
                WeeklyHour = scanner.nextInt();
                scanner.nextLine();
                if (WeeklyHour < 0 || WeeklyHour > AU) {
                    System.out.println("Weekly " + s1 + " hour out of bound. Please re-enter.");
                } else {
                    break;
                }
            } else {
                System.out.println("Your input " + scanner.nextLine() + " is not an integer.");
            }
        }
        return WeeklyHour;
    }


    /**
     * Set course type of course
     * @return The course type of course
     */
    private String setCourseType() {
        String courseType;
        do {
            System.out.println("Enter course type (uppercase): ");
            System.out.println("Enter -h to print all the course types.");
            courseType = scanner.nextLine();
            while (courseType.equals("-h")) {
                supportCourseMgr.printAllCourseType();
                courseType = scanner.nextLine();
            }
        } while (!supportCourseMgr.checkCourseTypeValidation(courseType));
        return courseType;
    }


    /**
     * Set department of course
     * @return The department of course
     */
    private String setDepartment() {
        String courseDepartment;
        do {
            System.out.println("Enter course's department (uppercase): ");
            System.out.println("Enter -h to print all the departments.");
            courseDepartment = scanner.nextLine();
            while ("-h".equals(courseDepartment)) {
                supportCourseMgr.printAllDepartment();
                courseDepartment = scanner.nextLine();
            }
        } while (!supportCourseMgr.checkDepartmentValidation(courseDepartment));
        return courseDepartment;
    }

    /**
     * Set AU of course
     * @return The AU of course
     */
    private int setAU() {
        int AU;
        while (true) {
            System.out.println("Enter number of academic unit(s): ");
            if (scanner.hasNextInt()) {
                AU = scanner.nextInt();
                scanner.nextLine();
                if (AU < 0 || AU > maxNumAU) {
                    System.out.println("AU out of bound. Please re-enter.");
                } else {
                    break;
                }
            } else {
                System.out.println("Your input " + scanner.nextLine() + " is not an integer.");
            }
        }
        return AU;
    }

    /**
     * Set total seats in course
     * @return The total seats in course
     */
    private int setTotalSeats() {
        int totalSeats;
        while (true) {
            System.out.println("Enter the total vacancy of this course: ");
            if (scanner.hasNextInt()) {
                totalSeats = scanner.nextInt();
                if (totalSeats <= 0) {
                    System.out.println("Please enter a valid vacancy (greater than 0)");
                } else {
                    break;
                }
            } else {
                System.out.println("Your input " + scanner.nextLine() + " is not an integer.");
                System.out.println("Please re-enter");
            }
        }
        return totalSeats;
    }

    /**
     * Checks whether a course (with all of its groups) have available slots and displays the result.
     */
    public void checkAvailableSlots() {
        //printout the result directly
        System.out.println("checkAvailableSlots is called");
        Course currentCourse;

        do {
            currentCourse = supportCourseMgr.checkCourseExists();
            if (currentCourse != null) {
                System.out.println(currentCourse.getCourseID() + " " + currentCourse.getCourseName() + " (Available/Total): " + currentCourse.getVacancies() + "/" + currentCourse.getTotalSeats());
                System.out.println("--------------------------------------------");
                for (IGroup lectureGroup : currentCourse.getLectureGroups()) {
                    System.out.println("Lecture group " + lectureGroup.getGroupName() + " (Available/Total): " + lectureGroup.getAvailableVacancies() + "/" + lectureGroup.getTotalSeats());
                }
                if (currentCourse.getTutorialGroups() != null) {
                    System.out.println();
                    for (IGroup tutorialGroup : currentCourse.getTutorialGroups()) {
                        System.out.println("Tutorial group " + tutorialGroup.getGroupName() + " (Available/Total):  " + tutorialGroup.getAvailableVacancies() + "/" + tutorialGroup.getTotalSeats());
                    }
                }
                if (currentCourse.getLabGroups() != null) {
                    System.out.println();
                    for (IGroup labGroup : currentCourse.getLabGroups()) {
                        System.out.println("Lab group " + labGroup.getGroupName() + " (Available/Total): " + labGroup.getAvailableVacancies() + "/" + labGroup.getTotalSeats());
                    }
                }
                System.out.println();
                break;
            } else {
                System.out.println("This course does not exist. Please check again.");
            }
        } while (true);

    }

    public List<Course> getCourses() {
        return courses;
    }


    public void setProfessorMgr(IProfessorMgr professorMgr) {
        this.professorMgr = professorMgr;
    }

    public void setSupportProfessorMgr(SupportProfessorMgr supportProfessorMgr) {
        this.supportProfessorMgr = supportProfessorMgr;
    }

    public void setSupportCourseMgr(SupportCourseMgr supportCourseMgr) {
        this.supportCourseMgr = supportCourseMgr;
    }

}