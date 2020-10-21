package com.softeng306;


import java.util.*;
import java.io.PrintStream;
import java.io.OutputStream;


public class CourseMgr {
    private static Scanner scanner = new Scanner(System.in);
    private static PrintStream originalStream = System.out;
    private static PrintStream dummyStream = new PrintStream(new OutputStream() {
        public void write(int b) {
            // NO-OP
        }
    });


    /**
     * Creates a new course and stores it in the file.
     */
    public static void addCourse() {
        String courseID;
        String courseName;
        String profID;

        int seatsLeft;
        // Can make the sameCourseID as boolean, set to false.
        do {
            System.out.println("Give this course an ID: ");
            courseID = scanner.nextLine();
        } while (!ValidationMgr.checkValidCourseIDInput(courseID) || ValidationMgr.checkCourseExists(courseID) != null);

        System.out.println("Enter course Name: ");
        courseName = scanner.nextLine();

//        set total seats
        int totalSeats = setTotalSeats();
//        set total AU
        int AU = setAU();
//        set department
        String courseDepartment = setDepartment();
//        set courseType
        String courseType = setCourseType();

//        set number of lecture groups
        int noOfLectureGroups = setNoOfLectureGroups(totalSeats);
//        set number of weekly lecture hours
        int lecWeeklyHour = setLecWeeklyHour(AU);
/*                        Lecture groups                                  */
//      set LectureGroups
        ArrayList<LectureGroup> lectureGroups = new ArrayList<>();
        String lectureGroupName;
        int lectureGroupCapacity;
        seatsLeft = totalSeats;
        for (int i = 0; i < noOfLectureGroups; i++) {
//            set lecture group name
            lectureGroupName = setLectureGroupName(lectureGroups);
            do {
//                set lecture group capacity
                lectureGroupCapacity = setLectureGroupCapacity();
//                check if valid, if yes create new lecture group and add to array
                seatsLeft -= lectureGroupCapacity;
                if ((seatsLeft > 0 && i != (noOfLectureGroups - 1)) || (seatsLeft == 0 && i == noOfLectureGroups - 1)) {
                    LectureGroup lectureGroup = new LectureGroup(lectureGroupName, lectureGroupCapacity, lectureGroupCapacity);
                    lectureGroups.add(lectureGroup);
                    break;
                } else {
                    System.out.println("Sorry, the total capacity you allocated for all the lecture groups exceeds or does not add up to the total seats for this course.");
                    System.out.println("Please re-enter the capacity for the last lecture group " + lectureGroupName + " you have entered.");
                    seatsLeft += lectureGroupCapacity;
                }
            } while (true);
        }

        /*                        Tutorial groups                                  */
        int totalTutorialSeats = 0;
//        set number of tutorial groups
        int noOfTutorialGroups = setNoOfTutorialGroups(totalSeats, noOfLectureGroups);
//        set tutorial weekly hours
        int tutWeeklyHour = setTutWeeklyHour(AU, noOfTutorialGroups);

        ArrayList<TutorialGroup> tutorialGroups = new ArrayList<>();
        String tutorialGroupName;

        for (int i = 0; i < noOfTutorialGroups; i++) {
//            set tutorial group name
            tutorialGroupName = setTutorialGroupName(tutorialGroups);
//            set group tutorial seats
            totalTutorialSeats = getTotalTutorialSeats(totalSeats, totalTutorialSeats, noOfTutorialGroups, tutorialGroups, tutorialGroupName, i);
        }

        /*                        lab groups                                  */
        int totalLabSeats = 0;
//      set number of lab groups
        int noOfLabGroups = setNoOfLabGroups(totalSeats, noOfLectureGroups);
//        set lab weekly hours
        int labWeeklyHour = setLabWeeklyHour(AU, noOfLabGroups);

        ArrayList<LabGroup> labGroups = new ArrayList<>();
        String labGroupName;

        for (int i = 0; i < noOfLabGroups; i++) {
//            set lab group name
            labGroupName = setLabGroupName(labGroups);
//            set lab seats
            totalLabSeats = getTotalLabSeats(totalSeats, totalLabSeats, noOfLabGroups, labGroups, labGroupName, i);
        }

//        set professor in charge
        Professor profInCharge = setProfessorInCharge(courseDepartment);

//        setup course
        Course course = new Course(courseID, courseName, profInCharge, totalSeats, totalSeats, lectureGroups, tutorialGroups, labGroups, AU, courseDepartment, courseType, lecWeeklyHour, tutWeeklyHour, labWeeklyHour);
//        ask user to input course component
        int addCourseComponentChoice = promptUserToAddCourseComponent();

        if (addCourseComponentChoice == 2) {
            //add course into file
            FILEMgr.writeCourseIntoFile(course);
            Main.courses.add(course);
            System.out.println("Course " + courseID + " is added, but assessment components are not initialized.");
            printCourses();
            return;
        }
//        if 1: yes enter now
        enterCourseWorkComponentWeightage(course);

        FILEMgr.writeCourseIntoFile(course);
        Main.courses.add(course);
        System.out.println("Course " + courseID + " is added");
        printCourses();
    }
    /**
     * Ask user to add coursework component?
     * @return 1 if Yes or 2 if no
     */
    private static int promptUserToAddCourseComponent() {
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
    private static Professor setProfessorInCharge(String courseDepartment) {
        String profID;
        Professor profInCharge;
        List<String> professorsInDepartment;
        professorsInDepartment = HelpInfoMgr.printProfInDepartment(courseDepartment, false);
        while (true) {
            System.out.println("Enter the ID for the professor in charge please:");
            System.out.println("Enter -h to print all the professors in " + courseDepartment + ".");
            profID = scanner.nextLine();
            while ("-h".equals(profID)) {
                professorsInDepartment = HelpInfoMgr.printProfInDepartment(courseDepartment, true);
                profID = scanner.nextLine();
            }

            System.setOut(dummyStream);
            profInCharge = ValidationMgr.checkProfExists(profID);
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
     * @param totalSeats The total seats in course
     * @param totalLabSeats The total lab seats in course
     * @param noOfLabGroups Number of lab groups in course
     * @param labGroups Array containing labGroups
     * @param labGroupName Current labGroup
     * @param i index of lab group being added
     * @return The total number of lab seats
     */
    private static int getTotalLabSeats(int totalSeats, int totalLabSeats, int noOfLabGroups, ArrayList<LabGroup> labGroups, String labGroupName, int i) {
        int labGroupCapacity;
        do {
            System.out.println("Enter this lab group's capacity: ");
            labGroupCapacity = scanner.nextInt();
            scanner.nextLine();
            totalLabSeats += labGroupCapacity;
            if ((i != noOfLabGroups - 1) || (totalLabSeats >= totalSeats)) {
                LabGroup labGroup = new LabGroup(labGroupName, labGroupCapacity, labGroupCapacity);
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
     * Set lab group name
     * @param labGroups The list of LabGroups in course
     * @return Name of lab group
     */
    private static String setLabGroupName(ArrayList<LabGroup> labGroups) {
        boolean groupNameExists;
        String labGroupName;
        System.out.println("Give a name to this lab group");
        do {
            groupNameExists = false;
            System.out.println("Enter a group Name: ");
            labGroupName = scanner.nextLine();
            if (!ValidationMgr.checkValidGroupNameInput(labGroupName)) {
                groupNameExists = true;
                continue;
            }
            if (labGroups.size() == 0) {
                break;
            }
            for (LabGroup labGroup : labGroups) {
                if (labGroup.getGroupName().equals(labGroupName)) {
                    groupNameExists = true;
                    System.out.println("This lab group already exist for this course.");
                    break;
                }
            }
        } while (groupNameExists);
        return labGroupName;
    }

    /**
     * Set weekly hours for labs
     * @param AU Number of Academic units in course
     * @param noOfLabGroups number of lab groups in course
     * @return The number of weekly hours of labs
     */
    private static int setLabWeeklyHour(int AU, int noOfLabGroups) {
        int labWeeklyHour = 0;
        if (noOfLabGroups != 0) {
            while (true) {
                System.out.println("Enter the weekly lab hour for this course: ");
                if (scanner.hasNextInt()) {
                    labWeeklyHour = scanner.nextInt();
                    scanner.nextLine();
                    if (labWeeklyHour < 0 || labWeeklyHour > AU) {
                        System.out.println("Weekly lab hour out of bound. Please re-enter.");
                    } else {
                        break;
                    }
                } else {
                    System.out.println("Your input " + scanner.nextLine() + " is not an integer.");
                }
            }
        }
        return labWeeklyHour;
    }

    /**
     * Set number of lab groups
     * @param totalSeats Total seats in course
     * @param noOfLectureGroups Number of lecture groups in course
     * @return Number of lab groups
     */
    private static int setNoOfLabGroups(int totalSeats, int noOfLectureGroups) {
        int noOfLabGroups;
        do {
            System.out.println("Enter the number of lab groups: ");
            if (scanner.hasNextInt()) {
                noOfLabGroups = scanner.nextInt();
                scanner.nextLine();
                if (noOfLabGroups >= 0 && noOfLectureGroups <= totalSeats) {
                    break;
                }
                System.out.println("Invalid input.");
                System.out.println("Number of lab group must be non-negative.");
                System.out.println("Please re-enter");
            } else {
                System.out.println("Your input " + scanner.nextLine() + " is not an integer.");
            }
        } while (true);
        return noOfLabGroups;
    }

    /**
     * Set tutorial groups for course
     * @param totalSeats The total seats in course
     * @param totalTutorialSeats The total tutorial seats in lab group
     * @param noOfTutorialGroups The total number of tutorial groups
     * @param tutorialGroups The list of tutorial groups
     * @param tutorialGroupName The current name of the tutorial group
     * @param i The index of current tutorial group
     * @return The total tutorial seats in lab group
     */
    private static int getTotalTutorialSeats(int totalSeats, int totalTutorialSeats, int noOfTutorialGroups, ArrayList<TutorialGroup> tutorialGroups, String tutorialGroupName, int i) {
        int tutorialGroupCapacity;
        do {
            System.out.println("Enter this tutorial group's capacity: ");
            if (scanner.hasNextInt()) {
                tutorialGroupCapacity = scanner.nextInt();
                scanner.nextLine();
                totalTutorialSeats += tutorialGroupCapacity;
                if ((i != noOfTutorialGroups - 1) || (totalTutorialSeats >= totalSeats)) {
                    TutorialGroup tutorialGroup = new TutorialGroup(tutorialGroupName, tutorialGroupCapacity, tutorialGroupCapacity);
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
     * Set the tutorial group name
     * @param tutorialGroups The list of tutorial groups
     * @return The name of the tutorial groups
     */
    private static String setTutorialGroupName(ArrayList<TutorialGroup> tutorialGroups) {
        String tutorialGroupName;
        boolean groupNameExists;
        System.out.println("Give a name to the tutorial group");
        do {
            groupNameExists = false;
            System.out.println("Enter a group Name: ");
            tutorialGroupName = scanner.nextLine();
            if (!ValidationMgr.checkValidGroupNameInput(tutorialGroupName)) {
                groupNameExists = true;
                continue;
            }
            if (tutorialGroups.size() == 0) {
                break;
            }
            for (TutorialGroup tutorialGroup : tutorialGroups) {
                if (tutorialGroup.getGroupName().equals(tutorialGroupName)) {
                    groupNameExists = true;
                    System.out.println("This tutorial group already exist for this course.");
                    break;
                }
            }
        } while (groupNameExists);
        return tutorialGroupName;
    }

    /**
     * Set Weekly tutorial hours
     * @param AU The number of AU in course
     * @param noOfTutorialGroups Number of tutorial groups
     * @return The total tutorial hours per week
     */
    private static int setTutWeeklyHour(int AU, int noOfTutorialGroups) {
        int tutWeeklyHour = 0;
        if (noOfTutorialGroups != 0) {
            while (true) {
                System.out.println("Enter the weekly tutorial hour for this course: ");
                if (scanner.hasNextInt()) {
                    tutWeeklyHour = scanner.nextInt();
                    scanner.nextLine();
                    if (tutWeeklyHour < 0 || tutWeeklyHour > AU) {
                        System.out.println("Weekly tutorial hour out of bound. Please re-enter.");
                    } else {
                        break;
                    }
                } else {
                    System.out.println("Your input " + scanner.nextLine() + " is not an integer.");
                }
            }
        }
        return tutWeeklyHour;
    }

    /**
     * Set Number of tutorial groups
     * @param totalSeats The total seats in course
     * @param noOfLectureGroups The number of lecture groups in course
     * @return The number of tutorial groups in course
     */
    private static int setNoOfTutorialGroups(int totalSeats, int noOfLectureGroups) {
        int noOfTutorialGroups;
        do {
            System.out.println("Enter the number of tutorial groups:");
            if (scanner.hasNextInt()) {
                noOfTutorialGroups = scanner.nextInt();
                scanner.nextLine();
                if (noOfTutorialGroups >= 0 && noOfLectureGroups <= totalSeats) {
                    break;
                }
                System.out.println("Invalid input.");
                System.out.println("Number of tutorial group must be non-negative.");
                System.out.println("Please re-enter");
            } else {
                System.out.println("Your input " + scanner.nextLine() + " is not an integer.");
            }
        } while (true);
        return noOfTutorialGroups;
    }

    /**
     * Set the capacity of lecture groups
     * @return The lecture group capacity
     */
    private static int setLectureGroupCapacity() {
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
     * Set the lecture group name
     * @param lectureGroups The list of all lecture groups
     * @return The name of lecture group
     */
    private static String setLectureGroupName(ArrayList<LectureGroup> lectureGroups) {
        boolean groupNameExists;
        String lectureGroupName;
        System.out.println("Give a name to the lecture group");
        do {
            groupNameExists = false;
            System.out.println("Enter a group Name: ");
            lectureGroupName = scanner.nextLine();
            if (!ValidationMgr.checkValidGroupNameInput(lectureGroupName)) {
                groupNameExists = true;
                continue;
            }
            if (lectureGroups.size() == 0) {
                break;
            }
            for (LectureGroup lectureGroup : lectureGroups) {
                if (lectureGroup.getGroupName().equals(lectureGroupName)) {
                    groupNameExists = true;
                    System.out.println("This lecture group already exist for this course.");
                    break;
                }
            }
        } while (groupNameExists);
        return lectureGroupName;
    }

    /**
     * Set the weekly lecture hours
     * @param AU The AU of course
     * @return The weekly lecture hours for course
     */
    private static int setLecWeeklyHour(int AU) {
        int lecWeeklyHour;
        while (true) {
            System.out.println("Enter the weekly lecture hour for this course: ");
            if (scanner.hasNextInt()) {
                lecWeeklyHour = scanner.nextInt();
                scanner.nextLine();
                if (lecWeeklyHour < 0 || lecWeeklyHour > AU) {
                    System.out.println("Weekly lecture hour out of bound. Please re-enter.");
                } else {
                    break;
                }
            } else {
                System.out.println("Your input " + scanner.nextLine() + " is not an integer.");
            }
        }
        return lecWeeklyHour;
    }

    /**
     * Set the number of lecture groups in course
     * @param totalSeats The total seats in lecture groups
     * @return The number of lecture groups in course
     */
    private static int setNoOfLectureGroups(int totalSeats) {
        int noOfLectureGroups;
        do {
            System.out.println("Enter the number of lecture groups: ");
            // lecture group number cannot be 0 and also cannot be larger than totalSeats
            if (scanner.hasNextInt()) {
                noOfLectureGroups = scanner.nextInt();
                scanner.nextLine();
                if (noOfLectureGroups > 0 && noOfLectureGroups <= totalSeats) {
                    break;
                }
                System.out.println("Invalid input.");
                System.out.println("Number of lecture group must be positive but less than total seats in this course.");
                System.out.println("Please re-enter");
            } else {
                System.out.println("Your input " + scanner.nextLine() + " is not an integer.");
            }
        } while (true);
        return noOfLectureGroups;
    }

    /**
     * Set course type of course
     * @return The course type of course
     */
    private static String setCourseType() {
        String courseType;
        do {
            System.out.println("Enter course type (uppercase): ");
            System.out.println("Enter -h to print all the course types.");
            courseType = scanner.nextLine();
            while (courseType.equals("-h")) {
                HelpInfoMgr.printAllCourseType();
                courseType = scanner.nextLine();
            }
        } while (!ValidationMgr.checkCourseTypeValidation(courseType));
        return courseType;
    }

    /**
     * Set department of course
     * @return The department of course
     */
    private static String setDepartment() {
        String courseDepartment;
        do {
            System.out.println("Enter course's department (uppercase): ");
            System.out.println("Enter -h to print all the departments.");
            courseDepartment = scanner.nextLine();
            while ("-h".equals(courseDepartment)) {
                HelpInfoMgr.printAllDepartment();
                courseDepartment = scanner.nextLine();
            }
        } while (!ValidationMgr.checkDepartmentValidation(courseDepartment));
        return courseDepartment;
    }

    /**
     * Set AU of course
     * @return The AU of course
     */
    private static int setAU() {
        int AU;
        while (true) {
            System.out.println("Enter number of academic unit(s): ");
            if (scanner.hasNextInt()) {
                AU = scanner.nextInt();
                scanner.nextLine();
                if (AU < 0 || AU > 10) {
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
    private static int setTotalSeats() {
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
    public static void checkAvailableSlots() {
        //printout the result directly
        System.out.println("checkAvailableSlots is called");
        Course currentCourse;

        do {
            currentCourse = ValidationMgr.checkCourseExists();
            if (currentCourse != null) {
                System.out.println(currentCourse.getCourseID() + " " + currentCourse.getCourseName() + " (Available/Total): " + currentCourse.getVacancies() + "/" + currentCourse.getTotalSeats());
                System.out.println("--------------------------------------------");
                for (LectureGroup lectureGroup : currentCourse.getLectureGroups()) {
                    System.out.println("Lecture group " + lectureGroup.getGroupName() + " (Available/Total): " + lectureGroup.getAvailableVacancies() + "/" + lectureGroup.getTotalSeats());
                }
                if (currentCourse.getTutorialGroups() != null) {
                    System.out.println();
                    for (TutorialGroup tutorialGroup : currentCourse.getTutorialGroups()) {
                        System.out.println("Tutorial group " + tutorialGroup.getGroupName() + " (Available/Total):  " + tutorialGroup.getAvailableVacancies() + "/" + tutorialGroup.getTotalSeats());
                    }
                }
                if (currentCourse.getLabGroups() != null) {
                    System.out.println();
                    for (LabGroup labGroup : currentCourse.getLabGroups()) {
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
    private  static int setExamWeight(ArrayList<MainComponent> mainComponents) {
        int hasFinalExamChoice;
        int examWeight = 0;
        while (true) {
            System.out.println("Does this course have a final exam? Enter your choice:");
            System.out.println("1. Yes! ");
            System.out.println("2. No, all CAs.");
            hasFinalExamChoice = scanner.nextInt();
            scanner.nextLine();
            if (hasFinalExamChoice == 1) {
                System.out.println("Please enter weight of the exam: ");
                examWeight = scanner.nextInt();
                scanner.nextLine();
                while (examWeight > 80 || examWeight <= 0) {
                    if (examWeight > 80 && examWeight <= 100) {
                        System.out.println("According to the course assessment policy, final exam cannot take up more than 80%...");
                    }
                    System.out.println("Weight entered is invalid, please enter again: ");
                    examWeight = scanner.nextInt();
                    scanner.nextLine();
                }
                MainComponent exam = new MainComponent("Exam", examWeight, new ArrayList<SubComponent>(0));
                mainComponents.add(exam);
                break;
            } else if (hasFinalExamChoice == 2) {
                System.out.println("Okay, please enter some continuous assessments");
                break;
            }
        }
        return examWeight;
    }
    /**
     * Sets the course work component weightage of a course.
     *
     * @param currentCourse The course which course work component is to be set.
     */
    public static void enterCourseWorkComponentWeightage(Course currentCourse) {
        // Assume when course is created, no components are added yet
        // Assume once components are created and set, cannot be changed.
        int numberOfMain;
        int weight;
        int noOfSub;
        int sub_weight;

        System.out.println("enterCourseWorkComponentWeightage is called");
//        if entered from main -- get user to input current course
        if (currentCourse == null) {
            currentCourse = ValidationMgr.checkCourseExists();
        }

        ArrayList<MainComponent> mainComponents = new ArrayList<>(0);
        // Check if mainComponent is empty
        if (currentCourse.getMainComponents().size() == 0) {
            // empty course
            System.out.println("Currently course " + currentCourse.getCourseID() + " " + currentCourse.getCourseName() + " does not have any assessment component.");

//          set exam weight if there is a exam
            int examWeight = setExamWeight(mainComponents);
//            set number of main components
            numberOfMain = setNumberOfMainComponents();
            scanner.nextLine();

            boolean componentExist;
            String mainComponentName;
            String subComponentName;
            do {
                int totalWeightage = 100 - examWeight;
//                loop through each main component and add sub componenets
                for (int i = 0; i < numberOfMain; i++) {
                    ArrayList<SubComponent> subComponents = new ArrayList<>(0);
                    do {
                        componentExist = false;
                        System.out.println("Total weightage left to assign: " + totalWeightage);
                        System.out.println("Enter main component " + (i + 1) + " name: ");
                        mainComponentName = scanner.nextLine();

                        if (mainComponents.size() == 0) {
                            break;
                        }
                        if (mainComponentName.equals("Exam")) {
                            System.out.println("Exam is a reserved assessment.");
                            componentExist = true;
                            continue;
                        }
                        for (MainComponent mainComponent : mainComponents) {
                            if (mainComponent.getComponentName().equals(mainComponentName)) {
                                componentExist = true;
                                System.out.println("This sub component already exist. Please enter.");
                                break;
                            }
                        }
                    } while (componentExist);

                    do {
                        System.out.println("Enter main component " + (i + 1) + " weightage: ");
                        while (!scanner.hasNextInt()) {
                            String input = scanner.next();
                            System.out.println("Sorry. " + input + " is not an integer.");
                            System.out.println("Enter main component " + (i + 1) + " weightage:");
                        }
                        weight = scanner.nextInt();
                        if (weight < 0 || weight > totalWeightage) {
                            System.out.println("Please enter a weight between 0 ~ " + totalWeightage + ":");
                            continue;
                        }
                        break;
                    } while (true);
                    scanner.nextLine();
                    totalWeightage -= weight;
                    do {
                        System.out.println("Enter number of sub component under main component " + (i + 1) + ":");
                        while (!scanner.hasNextInt()) {
                            String input = scanner.next();
                            System.out.println("Sorry. " + input + " is not an integer.");
                            System.out.println("Enter number of sub component under main component " + (i + 1) + ":");
                        }
                        noOfSub = scanner.nextInt();
                        if (noOfSub < 0) {
                            System.out.println("Please enter a valid integer:");
                            continue;
                        }
                        break;
                    } while (true);
                    scanner.nextLine();
                    boolean flagSub = true;
                    while (flagSub) {

                        int sub_totWeight = 100;
                        for (int j = 0; j < noOfSub; j++) {


                            do {
                                componentExist = false;
                                System.out.println("Total weightage left to assign to sub component: " + sub_totWeight);
                                System.out.println("Enter sub component " + (j + 1) + " name: ");
                                subComponentName = scanner.nextLine();

                                if (subComponents.size() == 0) {
                                    break;
                                }
                                if (subComponentName.equals("Exam")) {
                                    System.out.println("Exam is a reserved assessment.");
                                    componentExist = true;
                                    continue;
                                }
                                for (SubComponent subComponent : subComponents) {
                                    if (subComponent.getComponentName().equals(subComponentName)) {
                                        componentExist = true;
                                        System.out.println("This sub component already exist. Please enter.");
                                        break;
                                    }
                                }
                            } while (componentExist);


                            do {
                                System.out.println("Enter sub component " + (j + 1) + " weightage: ");
                                while (!scanner.hasNextInt()) {
                                    String input = scanner.next();
                                    System.out.println("Sorry. " + input + " is not an integer.");
                                    System.out.println("Enter sub component " + (j + 1) + " weightage (out of the main component): ");
                                }
                                sub_weight = scanner.nextInt();
                                if (sub_weight < 0 || sub_weight > sub_totWeight) {
                                    System.out.println("Please enter a weight between 0 ~ " + sub_totWeight + ":");
                                    continue;
                                }
                                break;
                            } while (true);
                            scanner.nextLine();

                            //Create Subcomponent

                            SubComponent sub = new SubComponent(subComponentName, sub_weight);
                            subComponents.add(sub);
                            sub_totWeight -= sub_weight;
                        }
                        if (sub_totWeight != 0 && noOfSub != 0) {
                            System.out.println("ERROR! sub component weightage does not tally to 100");
                            System.out.println("You have to reassign!");
                            subComponents.clear();
                            flagSub = true;
                        } else {
                            flagSub = false;
                        }
                        //exit if weight is fully allocated
                    }
                    //Create main component
                    MainComponent main = new MainComponent(mainComponentName, weight, subComponents);
                    mainComponents.add(main);
                }

                if (totalWeightage != 0) {
                    // weightage assign is not tallied
                    System.out.println("Weightage assigned does not tally to 100!");
                    System.out.println("You have to reassign!");
                    mainComponents.clear();
                } else {
                    break;
                }
            } while (true);


            //set main component to course
            currentCourse.setMainComponents(mainComponents);

        } else {
            System.out.println("Course Assessment has been settled already!");
        }
        System.out.println(currentCourse.getCourseID() + " " + currentCourse.getCourseName() + " components: ");
        for (MainComponent each_comp : currentCourse.getMainComponents()) {
            System.out.println("    " + each_comp.getComponentName() + " : " + each_comp.getComponentWeight() + "%");
            for (SubComponent each_sub : each_comp.getSubComponents()) {
                System.out.println("        " + each_sub.getComponentName() + " : " + each_sub.getComponentWeight() + "%");
            }
        }
        // Update course into course.csv
    }

    private static int setNumberOfMainComponents() {
        int numberOfMain;
        do {
            System.out.println("Enter number of main component(s) to add:");
            while (!scanner.hasNextInt()) {
                String input = scanner.next();
                System.out.println("Sorry. " + input + " is not an integer.");
                System.out.println("Enter number of main component(s) to add:");
            }
            numberOfMain = scanner.nextInt();
            if (numberOfMain < 0) {
                System.out.println("Please enter a valid positive integer:");
                continue;
            }
            break;
        } while (true);
        return numberOfMain;
    }

    /**
     * Prints the list of courses
     */
    public static void printCourses() {
        System.out.println("Course List: ");
        System.out.println("| Course ID | Course Name | Professor in Charge |");
        for (Course course : Main.courses) {
            System.out.println("| " + course.getCourseID() + " | " + course.getCourseName() + " | " + course.getProfInCharge().getProfName() + " |");
        }
        System.out.println();
    }
}