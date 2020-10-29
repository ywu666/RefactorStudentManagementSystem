package com.softeng306.Managers;

import com.softeng306.Entities.Course;
import com.softeng306.Entities.CourseworkComponent;
import com.softeng306.Entities.MainComponent;
import com.softeng306.Entities.SubComponent;
import com.softeng306.SupportMgr.SupportCourseMgr;

import java.util.ArrayList;
import java.util.Scanner;

public class CourseComponentMgr implements ICourseComponentMgr{
    private static final Scanner scanner = new Scanner(System.in);

    private SupportCourseMgr supportCourseMgr;

    // maximum exam weight allowed
    private final double MAX_EXAM_WEIGHT = 80;

    @Override
    public void enterCourseWorkComponentWeightage(Course currentCourse) {
        // Assume when course is created, no components are added yet
        // Assume once components are created and set, cannot be changed.
        int numberOfMain;
        int weight;
        int noOfSub;
        int sub_weight;

        System.out.println("enterCourseWorkComponentWeightage is called");
//        if entered from main -- get user to input current course
        if (currentCourse == null) {
            currentCourse = supportCourseMgr.checkCourseExists();
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
            /*                                 Add course work main components                                                             */
            String mainComponentName;
            String subComponentName;
            do {
                int totalWeightage = 100 - examWeight;
//                loop through each main component and add sub components
                for (int i = 0; i < numberOfMain; i++) {
                    ArrayList<SubComponent> subComponents = new ArrayList<>(0);
//                  set main component name
                    mainComponentName = setComponentName(mainComponents, totalWeightage, i,": ","main");
//                    set main component weight
                    weight = setComponentWeight(totalWeightage, i, "Enter main component ", " weightage:");
                    scanner.nextLine();
                    totalWeightage -= weight;
//                    set number of sub components
                    noOfSub = setNoOfSub(i);
                    scanner.nextLine();

                    boolean flagSub = true;
                    while (flagSub) {
                        int sub_totWeight = 100;
                        for (int j = 0; j < noOfSub; j++) {
//                            set subcomponent name
                            subComponentName = setComponentName(subComponents,sub_totWeight,j," to sub component: ","sub");
//                          set sub component weight
                            sub_weight = setComponentWeight(sub_totWeight, j, "Enter sub component ", " weightage (out of the main component): ");
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
                    //Create main component and add
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
//        print course components after add
        supportCourseMgr.printCourseComponentsAfterAdd(currentCourse);
        // Update course into course.csv
    }
    /**
     * Ask user for exam. If yes add to main components list.
     * @param mainComponents The main components list of the course
     * @return The total weight of Exam mark. If no exam, then weight = 0.
     */
    private int setExamWeight(ArrayList<MainComponent> mainComponents) {
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
                while (examWeight > MAX_EXAM_WEIGHT || examWeight <= 0) {
                    if (examWeight > MAX_EXAM_WEIGHT && examWeight <= 100) {
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
     * Sets the number of sub components in main component from user
     * @param i The index of main component
     * @return The number of sub components under main component
     */
    private int setNoOfSub(int i) {
        int noOfSub;
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
        return noOfSub;
    }

    /**
     * Set weight of components (either sub or main)
     * @param totalWeightage The total weight of main componenets or subcomponents
     * @param i The index of component
     * @param s Place holder string
     * @param s2 Sub component or main component
     * @return The weight of the component
     */
    private int setComponentWeight(int totalWeightage, int i, String s, String s2) {
        int weight;
        do {
            System.out.println(s + (i + 1) + " weightage: ");
            while (!scanner.hasNextInt()) {
                String input = scanner.next();
                System.out.println("Sorry. " + input + " is not an integer.");
                System.out.println(s + (i + 1) + s2);
            }
            weight = scanner.nextInt();
            if (weight < 0 || weight > totalWeightage) {
                System.out.println("Please enter a weight between 0 ~ " + totalWeightage + ":");
                continue;
            }
            break;
        } while (true);
        return weight;
    }

    /**
     * Set the name of sub or main component
     * @param components The array of sub or main components
     * @param totalWeightage The total weightage of sub or main components
     * @param i Index of component
     * @param s1 Placeholder string
     * @param s2 Placeholder string fro main or sub
     * @return The name of component
     */
    private String setComponentName(ArrayList<? extends CourseworkComponent> components, int totalWeightage, int i, String s1, String s2) {
        boolean componentExist;
        String ComponentName;
        do {
            componentExist = false;
            System.out.println("Total weightage left to assign" + s1 + totalWeightage);
            System.out.println("Enter " + s2 +  " component " + (i + 1) + " name: ");
            ComponentName = scanner.nextLine();

            if (components.size() == 0) {
                break;
            }
            if (ComponentName.equals("Exam")) {
                System.out.println("Exam is a reserved assessment.");
                componentExist = true;
                continue;
            }
            for (CourseworkComponent mainComponent : components) {
                if (mainComponent.getComponentName().equals(ComponentName)) {
                    componentExist = true;
                    System.out.println("This sub component already exist. Please enter.");
                    break;
                }
            }
        } while (componentExist);
        return ComponentName;
    }

    /**
     * Set number of main components in course
     * @return The number of main components in course
     */
    private int setNumberOfMainComponents() {
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

    public void setSupportCourseMgr(SupportCourseMgr supportCourseMgr) {
        this.supportCourseMgr = supportCourseMgr;
    }

}
