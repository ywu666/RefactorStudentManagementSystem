package com.softeng306;

import com.softeng306.FILEMgr.*;
import com.softeng306.Managers.*;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static Scanner scanner = new Scanner(System.in);

    /**
     * The main function of the system.
     * Command line interface.
     * @param args The command line parameters.
     */
    public static void main(String[] args) {
        printWelcome();

        int choice;
        do {
            printOptions();
            do {
                System.out.println("Enter your choice, let me help you:");
                while (!scanner.hasNextInt()) {
                    System.out.println("Sorry. " + scanner.nextLine() + " is not an integer.");
                    System.out.println("Enter your choice, let me help you:");
                }
                choice = scanner.nextInt();
                scanner.nextLine();
                if (choice < 0 || choice > 11) {
                    System.out.println("Please enter 0 ~ 11 for your choice:");
                    continue;
                }
                break;
            } while (true);

            switch (choice) {
                case 0:
                    break;
                case 1:
                    StudentMgr.addStudent();
                    break;
                case 2:
                    CourseMgr.addCourse();
                    break;
                case 3:
                    CourseRegistrationMgr.registerCourse();
                    break;
                case 4:
                    CourseMgr.checkAvailableSlots();
                    break;
                case 5:
                    CourseRegistrationMgr.printStudents();
                    break;
                case 6:
                    CourseMgr.enterCourseWorkComponentWeightage(null);
                    break;
                case 7:
                    MarkMgr.setCourseWorkMark(false);
                    break;
                case 8:
                    MarkMgr.setCourseWorkMark(true);
                    break;
                case 9:
                    MarkMgr.printCourseStatistics();
                    break;
                case 10:
                    MarkMgr.printStudentTranscript();
                    break;
                case 11:
                    exitApplication();
                    break;

            }

        } while (choice != 11);
    }

    /**
     * Displays the welcome message.
     */

    public static void printWelcome() {
        System.out.println();
        System.out.println("****************** Hello! Welcome to SOFTENG 306 Project 2! ******************");
        System.out.println("Please note this application is not developed in The University of Auckland. All rights reserved for the original developers.");
        System.out.println("Permission has been granted by the original developers to anonymize the code and use for education purposes.");
        System.out.println("******************************************************************************************************************************");
        System.out.println();
    }
    /**
     * Displays the exiting message.
     */
    public static void exitApplication() {

        System.out.println("Backing up data before exiting...");
        MarkMgr markMgr = new MarkMgr();
        CourseMgr courseMgr = new CourseMgr();

        CourseFILEMgr.backUpCourse(courseMgr.getCourses());
        MarkFILEMgr.backUpMarks(markMgr.getMarks());
        System.out.println("********* Bye! Thank you for using Main! *********");
        System.out.println();
        System.out.println("                 ######    #      #   #######                   ");
        System.out.println("                 #    ##    #    #    #                         ");
        System.out.println("                 #    ##     #  #     #                         ");
        System.out.println("                 ######       ##      #######                   ");
        System.out.println("                 #    ##      ##      #                         ");
        System.out.println("                 #    ##      ##      #                         ");
        System.out.println("                 ######       ##      #######                   ");
        System.out.println();

    }

    /**
     * Displays all the options of the system.
     */
    public static void printOptions() {
        System.out.println("************ I can help you with these functions: *************");
        System.out.println(" 0. Print Options");
        System.out.println(" 1. Add a student");
        System.out.println(" 2. Add a course");
        System.out.println(" 3. Register student for a course including tutorial/lab classes");
        System.out.println(" 4. Check available slots in a class (vacancy in a class)");
        System.out.println(" 5. Print student list by lecture, tutorial or laboratory session for a course");
        System.out.println(" 6. Enter course assessment components weightage");
        System.out.println(" 7. Enter coursework mark – inclusive of its components");
        System.out.println(" 8. Enter exam mark");
        System.out.println(" 9. Print course statistics");
        System.out.println("10. Print student transcript");
        System.out.println("11. Quit Main System");
        System.out.println();
    }
}