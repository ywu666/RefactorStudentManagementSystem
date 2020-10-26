package com.softeng306.Managers;



import com.softeng306.FILEMgr.FILEMgr;
import com.softeng306.FILEMgr.StudentFILEMgr;
import com.softeng306.Entities.Student;

import java.util.List;
import java.util.Scanner;

import com.softeng306.SupportMgr.SupportStudentMgr;

import java.io.OutputStream;
import java.io.PrintStream;

/**
 * Manages the student related operations.
 * Contains addStudent.
 */

public class StudentMgr implements IStudentMgr {
    private static Scanner scanner = new Scanner(System.in);
    public static int lastGeneratedIDNum = 1800000;
    private StudentFILEMgr studentFileMgr = new StudentFILEMgr();
    /**
     * A list of all the registered students.
     */
    private List<Student> students = studentFileMgr.loadFromFile();
    private SupportStudentMgr supportStudentMgr;

    /**
     * Sets the lastGeneratedID variable of this  class. USed for generating new ID.
     *
     * @param lastGeneratedIDNum static variable idNumber of this class.
     */
    public static void setLastGeneratedIDNum(int lastGeneratedIDNum) {
        StudentMgr.lastGeneratedIDNum = lastGeneratedIDNum;
    }

    private static PrintStream originalStream = System.out;
    private static PrintStream dummyStream = new PrintStream(new OutputStream() {
        public void write(int b) {
            // NO-OP
        }
    });

    /**
     * Adds a student and put the student into file
     */
    public void addStudent() {
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
            } while (!supportStudentMgr.checkValidStudentIDInput(studentID) || supportStudentMgr.checkStudentExists(studentID) != null);

        }
//      if choice == 2 then generate automated student ID
        else {
            studentID = generateStudentID();
        }
//      Set name
        do {
            System.out.println("Enter student Name: ");
            studentName = scanner.nextLine();

        } while (!supportStudentMgr.checkValidPersonNameInput(studentName));
//        create new student with name and student ID
        currentStudent = new Student(studentID, studentName);


        //set student department
        setStudentDepartment(currentStudent);
        //set gender
        setStudentGender(currentStudent);

        //student year
        setStudentYear(currentStudent);
//        write into file
        FILEMgr<Student> studentFileEMgr = new StudentFILEMgr();
        studentFileEMgr.writeIntoFile(currentStudent);

        students.add(currentStudent);
//        print out current students after added
        printStudentsAfterAdd(currentStudent);

    }

    /**
     * Generates the ID of a new student.
     *
     * @return the generated student ID.
     */
    private String generateStudentID() {
        String generateStudentID;
        boolean studentIDUsed;

        do {
            int rand = (int) (Math.random() * ((76 - 65) + 1)) + 65;
            String lastPlace = Character.toString((char) rand);
            lastGeneratedIDNum += 1;
            generateStudentID = "U" + lastGeneratedIDNum + lastPlace;

            studentIDUsed = false;
            for (Student student : students) {
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
    private String manualPromptUserID() {
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
    private void setStudentDepartment(Student currentStudent) {
        String studentSchool;
        while (true) {
            System.out.println("Enter student's school (uppercase): ");
            System.out.println("Enter -h to print all the schools.");
            studentSchool = scanner.nextLine();
            while ("-h".equals(studentSchool)) {
                supportStudentMgr.printAllDepartment();
                studentSchool = scanner.nextLine();
            }
            if (supportStudentMgr.checkDepartmentValidation(studentSchool)) {

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
    private void setStudentGender(Student currentStudent) {
        String studentGender;
        while (true) {
            System.out.println("Enter student gender (uppercase): ");
            System.out.println("Enter -h to print all the genders.");
            studentGender = scanner.nextLine();
            while ("-h".equals(studentGender)) {
                supportStudentMgr.printAllGender();
                studentGender = scanner.nextLine();
            }

                if (supportStudentMgr.checkGenderValidation(studentGender)) {
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
    private void setStudentYear(Student currentStudent) {
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
    private void printStudentsAfterAdd(Student currentStudent) {
        String GPA = "not available";
        System.out.println("Student named: " + currentStudent.getStudentName() + " is added, with ID: " + currentStudent.getStudentID());

        System.out.println("Student List: ");
        System.out.println("| Student ID | Student Name | Student School | Gender | Year | GPA |");
        for (Student student : students) {
            if (Double.compare(student.getGPA(), 0.0) != 0) {
                GPA = String.valueOf(student.getGPA());
            }
            System.out.println(" " + student.getStudentID() + " | " + student.getStudentName() + " | " + student.getStudentSchool() + " | " + student.getGender() + " | " + student.getStudentYear() + " | " + GPA);
        }
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setSupportStudentMgr(SupportStudentMgr supportStudentMgr) {
        this.supportStudentMgr = supportStudentMgr;
    }

}
