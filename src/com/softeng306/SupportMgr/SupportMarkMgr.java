package com.softeng306.SupportMgr;

import com.softeng306.*;
import com.softeng306.FILEMgr.MarkFILEMgr;
import com.softeng306.Managers.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class SupportMarkMgr {

    private static Scanner scanner = new Scanner(System.in);


    public static void printChoicesForCourseWorkMark(Mark mark){
        ArrayList<String> availableChoices = new ArrayList<String>(0);
        ArrayList<Double> weights = new ArrayList<Double>(0);
        ArrayList<Boolean> isMainAss = new ArrayList<Boolean>(0);
        for (HashMap.Entry<CourseworkComponent, Double> assessmentResult : mark.getCourseWorkMarks().entrySet()) {
            CourseworkComponent key = assessmentResult.getKey();
            if ( !(key instanceof MainComponent) ){
                continue;
            }

            if ((!key.getComponentName().equals("Exam")) && ((MainComponent) key).getSubComponents().size() == 0) {
                availableChoices.add(key.getComponentName());
                weights.add((double) key.getComponentWeight());
                isMainAss.add(true);
            } else {
                for (SubComponent subComponent : ((MainComponent) key).getSubComponents()) {
                    availableChoices.add(key.getComponentName() + "-" + subComponent.getComponentName());
                    weights.add((double) key.getComponentWeight() * (double) subComponent.getComponentWeight() / 100d);
                    isMainAss.add(false);
                }
            }
        }

        for (int i = 0; i < availableChoices.size(); i++) {
            System.out.println((i + 1) + ". " + availableChoices.get(i) + " Weight in Total: " + weights.get(i) + "%");
        }
        System.out.println((availableChoices.size() + 1) + ". Quit");

        int choice;
        System.out.println("Enter your choice");
        choice = scanner.nextInt();
        scanner.nextLine();

        while (choice > (availableChoices.size() + 1) || choice < 0) {
            System.out.println("Please enter choice between " + 0 + "~" + (availableChoices.size() + 1));
            System.out.println("Enter your choice");
            choice = scanner.nextInt();
            scanner.nextLine();
        }

        if (choice == (availableChoices.size() + 1)) {
            return;
        }

        double assessmentMark;
        System.out.println("Enter the mark for this assessment:");
        assessmentMark = scanner.nextDouble();
        scanner.nextLine();
        while (assessmentMark > 100 || assessmentMark < 0) {
            System.out.println("Please enter mark in range 0 ~ 100.");
            assessmentMark = scanner.nextDouble();
            scanner.nextLine();
        }

        if (isMainAss.get(choice - 1)) {
            // This is a stand alone main assessment
            MarkMgr.setMainCourseWorkMarks(mark, availableChoices.get(choice - 1), assessmentMark);
        } else {
            MarkMgr.setSubCourseWorkMarks(mark, availableChoices.get(choice - 1).split("-")[1], assessmentMark);
        }

    }

}
