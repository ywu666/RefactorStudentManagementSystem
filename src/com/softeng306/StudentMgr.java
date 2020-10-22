package com.softeng306;


import java.util.Scanner;

/**
 * Manages the student related operations.
 * Contains addStudent.
 */

public class StudentMgr {
    private static Scanner scanner = new Scanner(System.in);
    public static int lastGeneratedIDNum = 1800000;

    /**
     * Sets the lastGeneratedID variable of this  class. USed for generating new ID.
     *
     * @param lastGeneratedIDNum static variable idNumber of this class.
     */
    public static void setLastGeneratedIDNum(int lastGeneratedIDNum) {
        StudentMgr.lastGeneratedIDNum = lastGeneratedIDNum;
    }

    /**
     * Adds a student and put the student into file
     */
    public static void addStudent() {
        String studentName;
        String studentID;
        int choice;
        Student currentStudent;
        System.out.println("addStudent is called");
        System.out.println("Choose the way you want to add a student:");
        System.out.println("1. Manually input the student ID.");
        System.out.println("2. Let the system self-generate the student ID.");
        do {
            System.out.println("Please input your choice:");
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine();
                if (choice < 1 || choice > 2) {
                    System.out.println("Invalid input. Please re-enter.");
                } else {
                    break;
                }
            } else {
                System.out.println("Your input " + scanner.nextLine() + " is not an integer.");
            }
        } while (true);
        if (choice == 1) {
//            if valid ID and student does not exists then break from while loop
            do {
//                prompt user for with instructions for manual ID input
                studentID = manualPromptUserID();
//
            } while (!ValidationMgr.checkValidStudentIDInput(studentID) || ValidationMgr.checkStudentExists(studentID) != null);
        }
//      if choice == 2 then generate automated student ID
        else {
            studentID = generateStudentID();
        }
//      Set name
        do {
            System.out.println("Enter student Name: ");
            studentName = scanner.nextLine();
        } while (!ValidationMgr.checkValidPersonNameInput(studentName));
//        create new student with name and student ID
        currentStudent = new Student(studentID, studentName);

        //set student department
        setStudentDepartment(currentStudent);
        //set gender
        setStudentGender(currentStudent);

        //student year
        setStudentYear(currentStudent);
//        write into file
        FILEMgr.writeStudentsIntoFile(currentStudent);

        Main.students.add(currentStudent);
//        print out current students after added
        printStudentsAfterAdd(currentStudent);

    }

    /**
     * Generates the ID of a new student.
     *
     * @return the generated student ID.
     */
    private static String generateStudentID() {
        String generateStudentID;
        boolean studentIDUsed;

        do {
            int rand = (int) (Math.random() * ((76 - 65) + 1)) + 65;
            String lastPlace = Character.toString((char) rand);
            lastGeneratedIDNum += 1;
            generateStudentID = "U" + lastGeneratedIDNum + lastPlace;

            studentIDUsed = false;
            for (Student student : Main.students) {
                if (generateStudentID.equals(student.getStudentID())) {
                    studentIDUsed = true;
                    break;
                }
            }
        } while (studentIDUsed);
        return generateStudentID;
    }

    /**
     * IF User enters student ID manually, this is the prompt given
     *
     * @return the user inputted Student ID
     */
    private static String manualPromptUserID() {
        System.out.println("The student ID should follow:");
        System.out.println("Length is exactly 9");
        System.out.println("Start with U (Undergraduate)");
        System.out.println("End with a uppercase letter between A and L");
        System.out.println("Seven digits in the middle");
        System.out.println();
        System.out.println("Give this student an ID: ");
        return scanner.nextLine();
    }

    /**
     * Prompts user for department of  and assigns
     *
     * @param currentStudent Is the current student being assigned
     */
    private static void setStudentDepartment(Student currentStudent) {
        String studentSchool;
        while (true) {
            System.out.println("Enter student's school (uppercase): ");
            System.out.println("Enter -h to print all the schools.");
            studentSchool = scanner.nextLine();
            while ("-h".equals(studentSchool)) {
                HelpInfoMgr.printAllDepartment();
                studentSchool = scanner.nextLine();
            }
            if (ValidationMgr.checkDepartmentValidation(studentSchool)) {
                currentStudent.setStudentSchool(studentSchool);
                break;
            }
        }
    }

    /**
     * Prompts user for Gender  of student and assigns
     *
     * @param currentStudent Is the current student being assigned
     */
    private static void setStudentGender(Student currentStudent) {
        String studentGender;
        while (true) {
            System.out.println("Enter student gender (uppercase): ");
            System.out.println("Enter -h to print all the genders.");
            studentGender = scanner.nextLine();
            while ("-h".equals(studentGender)) {
                HelpInfoMgr.printAllGender();
                studentGender = scanner.nextLine();
            }

            if (ValidationMgr.checkGenderValidation(studentGender)) {
                currentStudent.setGender(studentGender);
                break;
            }
        }
    }

    /**
     * Prompts user for year of student  and assigns
     *
     * @param currentStudent Is the current student being assigned
     */
    private static void setStudentYear(Student currentStudent) {
        int studentYear;
        do {
            System.out.println("Enter student's school year (1-4) : ");
            if (scanner.hasNextInt()) {
                studentYear = scanner.nextInt();
                scanner.nextLine();
                if (studentYear < 1 || studentYear > 4) {
                    System.out.println("Your input is out of bound.");
                    System.out.println("Please re-enter an integer between 1 and 4");
                } else {
                    currentStudent.setStudentYear(studentYear);
                    break;
                }
            } else {
                System.out.println("Your input " + scanner.nextLine() + " is not an integer");
                System.out.println("Please re-enter.");
            }
        } while (true);
    }

    /**
     * Prints added Student and list of all student to console after student is added
     *
     * @param currentStudent Is the current student being assigned
     */
    private static void printStudentsAfterAdd(Student currentStudent) {
        String GPA = "not available";
        System.out.println("Student named: " + currentStudent.getStudentName() + " is added, with ID: " + currentStudent.getStudentID());

        System.out.println("Student List: ");
        System.out.println("| Student ID | Student Name | Student School | Gender | Year | GPA |");
        for (Student student : Main.students) {
            if (Double.compare(student.getGPA(), 0.0) != 0) {
                GPA = String.valueOf(student.getGPA());
            }
            System.out.println(" " + student.getStudentID() + " | " + student.getStudentName() + " | " + student.getStudentSchool() + " | " + student.getGender() + " | " + student.getStudentYear() + " | " + GPA);
        }
    }
}
