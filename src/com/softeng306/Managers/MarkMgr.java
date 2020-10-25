package com.softeng306.Managers;

import com.softeng306.*;
import com.softeng306.FILEMgr.MarkFILEMgr;

import java.util.*;

/**
 * Manages all the mark related operations.
 */

public class MarkMgr implements IMarkMgr{
    private static Scanner scanner = new Scanner(System.in);
    private MarkFILEMgr markFILEMgr = new MarkFILEMgr();
    /**
     * A list of all the stored marks.
     */
    private List<Mark> marks = markFILEMgr.loadFromFile();

    private ICourseMgr courseMgr;
    private IStudentMgr studentMgr;

    /**
     * Initializes marks for a student when he/she just registered a course.
     *
     * @param student the student this mark record belongs to.
     * @param course  the course this mark record about.
     * @return the new added mark.
     */
    public Mark initializeMark(Student student, Course course) {
        HashMap<CourseworkComponent, Double> courseWorkMarks = new HashMap<>();
        double totalMark = 0d;
        ArrayList<MainComponent> mainComponents = course.getMainComponents();

        for (MainComponent mainComponent : mainComponents) {
            courseWorkMarks.put(mainComponent, 0d);
            if (mainComponent.getSubComponents().size() <= 0) {
                continue;
            }

            for (SubComponent subComponent : mainComponent.getSubComponents()) {
                courseWorkMarks.put(subComponent, 0d);
            }

        }
        Mark mark = new Mark(student, course, courseWorkMarks, totalMark);
        /**
         * This patt is changed due to the refactor
         */
        MarkFILEMgr markFILEMgr = new MarkFILEMgr();
        markFILEMgr.writeIntoFile(mark);

        return mark;
    }

    /**
     * Sets the main course work marks of this student mark record.
     *
     * @param courseWorkName The name of this main course work.
     * @param result         The mark obtained in this main course work.
     */
    public void setMainCourseWorkMarks(Mark mark, String courseWorkName, double result) {
        HashMap<CourseworkComponent, Double> courseWorkMarks = mark.getCourseWorkMarks();
        double totalMark = mark.getTotalMark();

        for (HashMap.Entry<CourseworkComponent, Double> entry : courseWorkMarks.entrySet()) {
            CourseworkComponent courseworkComponent = entry.getKey();
            double previousResult = entry.getValue();
            if (!(courseworkComponent instanceof MainComponent)) {
                continue;
            }
            if (courseworkComponent.getComponentName().equals(courseWorkName)) {
                if (((MainComponent) courseworkComponent).getSubComponents().size() != 0) {
                    System.out.println("This main assessment is not stand alone");
                    return;
                }
                totalMark += (result - previousResult) * courseworkComponent.getComponentWeight() / 100d;
                mark.setTotalMark(totalMark);
                entry.setValue(result);

                System.out.println("The course work component is successfully set to: " + result);
                System.out.println("The course total mark is updated to: " + mark.getTotalMark());
                return;
            }
        }
        System.out.println("This main assessment component does not exist...");
    }


    /**
     * Sets the sub course work marks of this student mark record.
     *
     * @param courseWorkName The name of this sub course work.
     * @param result         The mark obtained in this sub course work.
     */
    public void setSubCourseWorkMarks(Mark mark, String courseWorkName, double result) {
        HashMap<CourseworkComponent, Double> courseWorkMarks = mark.getCourseWorkMarks();
        double totalMark = mark.getTotalMark();

        double markIncInMain = 0d;
        for (HashMap.Entry<CourseworkComponent, Double> entry : courseWorkMarks.entrySet()) {
            CourseworkComponent courseworkComponent = entry.getKey();
            double previousResult = entry.getValue();
            if (!(courseworkComponent instanceof SubComponent)) {
                continue;
            }
            if (courseworkComponent.getComponentName().equals(courseWorkName)) {
                // Set the subComponent mark, calculate the main component increment
                markIncInMain = (result - previousResult) * courseworkComponent.getComponentWeight() / 100d;
                entry.setValue(result);

                System.out.println("The sub course work component is successfully set to: " + result);
                System.out.println("The main course work component increase by: " + markIncInMain);
            }
        }

        // Find its main component and update
        for (HashMap.Entry<CourseworkComponent, Double> entry : courseWorkMarks.entrySet()) {
            CourseworkComponent courseworkComponent = entry.getKey();
            double previousResult = entry.getValue();
            if (!(courseworkComponent instanceof MainComponent)) {
                continue;
            }
            if ( ((MainComponent) courseworkComponent).getSubComponents().size() == 0 ) {
                continue;
            }

            for (SubComponent subComponent : ((MainComponent) courseworkComponent).getSubComponents()) {
                if (subComponent.getComponentName().equals(courseWorkName)) {
                    // We find the main component it is in
                    totalMark += markIncInMain * courseworkComponent.getComponentWeight() / 100d;
                    mark.setTotalMark(totalMark);
                    entry.setValue(previousResult + markIncInMain);

                    System.out.println("The course total mark is updated to: " + mark.getTotalMark());
                    return;
                }
            }

        }
    }



    /**
     * Sets the coursework mark for the mark record.
     *
     * @param isExam whether this coursework component refers to "Exam"
     */
    public void setCourseWorkMark(boolean isExam) {
        System.out.println("enterCourseWorkMark is called");

        String studentID = studentMgr.checkStudentExists().getStudentID();
        String courseID = courseMgr.checkCourseExists().getCourseID();

        for (Mark mark : marks) {

            if (!(mark.getCourse().getCourseID().equals(courseID))) {
                continue;
            }
            if (!(mark.getStudent().getStudentID().equals(studentID))) {
                continue;
            }

            if (isExam) {
                // The user want to enter exam mark.
                double examMark;
                System.out.println("Enter exam mark:");
                examMark = scanner.nextDouble();
                scanner.nextLine();
                while (examMark > 100 || examMark < 0) {
                    System.out.println("Please enter mark in range 0 ~ 100.");
                    examMark = scanner.nextDouble();
                    scanner.nextLine();
                }
                setMainCourseWorkMarks(mark, "Exam", examMark);

            } else {
                System.out.println("Here are the choices you can have: ");
                printChoicesForCourseWorkMark(mark);
            }
            return;
        }
        System.out.println("This student haven't registered " + courseID);
    }

    public void printChoicesForCourseWorkMark(Mark mark){
        ArrayList<String> availableChoices = new ArrayList<>(0);
        ArrayList<Double> weights = new ArrayList<>(0);
        ArrayList<Boolean> isMainAss = new ArrayList<>(0);
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
            setMainCourseWorkMarks(mark, availableChoices.get(choice - 1), assessmentMark);
        } else {
            setSubCourseWorkMarks(mark, availableChoices.get(choice - 1).split("-")[1], assessmentMark);
        }

    }

    /**
     * Computes the sum of marks for a particular component of a particular course
     *
     * @param thisCourseMark    the array list of mark records belong to a particular course
     * @param thisComponentName the component name interested.
     * @return the sum of component marks
     */
    public double computeMark(ArrayList<Mark> thisCourseMark, String thisComponentName) {
        double averageMark = 0;
        for (Mark mark : thisCourseMark) {
            HashMap<CourseworkComponent, Double> thisComponentMarks = mark.getCourseWorkMarks();
            for (HashMap.Entry<CourseworkComponent, Double> entry : thisComponentMarks.entrySet()) {
                CourseworkComponent key = entry.getKey();
                double value = entry.getValue();
                if (key.getComponentName().equals(thisComponentName)) {
                    averageMark += value;
                    break;
                }
            }
        }
        return averageMark;
    }

    /**
     * Prints the course statics including enrollment rate, average result for every assessment component and the average overall performance of this course.
     */
    public void printCourseStatistics() {
        System.out.println("printCourseStatistics is called");

        Course currentCourse = courseMgr.checkCourseExists();
        String courseID = currentCourse.getCourseID();

        ArrayList<Mark> thisCourseMark = new ArrayList<>(0);
        for (Mark mark : marks) {
            if (mark.getCourse().getCourseID().equals(courseID)) {
                thisCourseMark.add(mark);
            }
        }

        System.out.println("*************** Course Statistic ***************");
        System.out.println("Course ID: " + currentCourse.getCourseID() + "\tCourse Name: " + currentCourse.getCourseName());
        System.out.println("Course AU: " + currentCourse.getAU());
        System.out.println();
        System.out.print("Total Slots: " + currentCourse.getTotalSeats());
        int enrolledNumber = (currentCourse.getTotalSeats() - currentCourse.getVacancies());
        System.out.println("\tEnrolled Student: " + enrolledNumber);
        System.out.printf("Enrollment Rate: %4.2f %%\n", ((double) enrolledNumber / (double) currentCourse.getTotalSeats() * 100d));
        System.out.println();

        printAssessmentComponent(currentCourse, thisCourseMark);

        System.out.println();
        System.out.print("Overall Performance: ");
        double averageMark = 0;
        for (Mark mark : thisCourseMark) {
            averageMark += mark.getTotalMark();
        }
        averageMark = averageMark / thisCourseMark.size();
        System.out.printf("%4.2f \n", averageMark);

        System.out.println();
        System.out.println("***********************************************");
        System.out.println();

    }

    public void printAssessmentComponent(Course currentCourse, ArrayList<Mark> thisCourseMark){

        int examWeight = 0;
        boolean hasExam = false;
        double averageMark = 0;

        // Find marks for every assessment components
        for (CourseworkComponent courseworkComponent : currentCourse.getMainComponents()) {
            String thisComponentName = courseworkComponent.getComponentName();

            if (thisComponentName.equals("Exam")) {
                examWeight = courseworkComponent.getComponentWeight();
                hasExam = true;

            } else {
                averageMark = 0;
                System.out.print("Main Component: " + courseworkComponent.getComponentName());
                System.out.print("\tWeight: " + courseworkComponent.getComponentWeight() + "%");

                averageMark += computeMark(thisCourseMark, thisComponentName);

                averageMark = averageMark / thisCourseMark.size();
                System.out.println("\t Average: " + averageMark);

                ArrayList<SubComponent> thisSubComponents = ((MainComponent) courseworkComponent).getSubComponents();
                if (thisSubComponents.size() == 0) {
                    continue;
                }
                for (SubComponent subComponent : thisSubComponents) {
                    averageMark = 0;
                    System.out.print("Sub Component: " + subComponent.getComponentName());
                    System.out.print("\tWeight: " + subComponent.getComponentWeight() + "% (in main component)");
                    String thisSubComponentName = subComponent.getComponentName();

                    averageMark += computeMark(thisCourseMark, thisSubComponentName);

                    averageMark = averageMark / thisCourseMark.size();
                    System.out.println("\t Average: " + averageMark);
                }
                System.out.println();
            }

        }

        if (hasExam) {
            printExamComponent (examWeight, thisCourseMark );
        } else {
            System.out.println("This course does not have final exam.");
        }

    }

    public void printExamComponent (int examWeight, ArrayList<Mark> thisCourseMark ){

        double averageMark = 0 ;
        System.out.print("Final Exam");
        System.out.print("\tWeight: " + examWeight + "%");

        String examComponentName = "Exam";
        averageMark = computeMark(thisCourseMark, examComponentName);

        averageMark = averageMark / thisCourseMark.size();
        System.out.println("\t Average: " + averageMark);
    }

    /**
     * Prints transcript (Results of course taken) for a particular student
     */
    public void printStudentTranscript() {
        String studentID = studentMgr.checkStudentExists().getStudentID();


        int thisStudentAU = 0;
        ArrayList<Mark> thisStudentMark = new ArrayList<Mark>(0);

        for (Mark mark : marks) {
            if (mark.getStudent().getStudentID().equals(studentID)) {
                thisStudentMark.add(mark);
                thisStudentAU += mark.getCourse().getAU();
            }
        }

        if (thisStudentMark.size() == 0) {
            System.out.println("------ No transcript ready for this student yet ------");
            return;
        }
        System.out.println("----------------- Official Transcript ------------------");
        System.out.print("Student Name: " + thisStudentMark.get(0).getStudent().getStudentName());
        System.out.println("\tStudent ID: " + thisStudentMark.get(0).getStudent().getStudentID());
        System.out.println("AU for this semester: " + thisStudentAU);
        System.out.println();

        printMarkForTranscript(thisStudentMark, thisStudentAU);

        System.out.println("------------------ End of Transcript -------------------");
    }

    /**
     * helper function for printStudentTranscript()
     * print the marks for the student
     *
     * @param  thisStudentMark list of the student's mark
     * @param  thisStudentAU
     *
     */
    public void printMarkForTranscript(ArrayList<Mark> thisStudentMark, int thisStudentAU){
        double studentGPA = 0d;
        for (Mark mark : thisStudentMark) {
            System.out.print("Course ID: " + mark.getCourse().getCourseID());
            System.out.println("\tCourse Name: " + mark.getCourse().getCourseName());

            for (HashMap.Entry<CourseworkComponent, Double> entry : mark.getCourseWorkMarks().entrySet()) {
                CourseworkComponent assessment = entry.getKey();
                Double result = entry.getValue();

                if ( !(assessment instanceof MainComponent) ){
                    continue;
                }

                System.out.println("Main Assessment: " + assessment.getComponentName() + " ----- (" + assessment.getComponentWeight() + "%)");
                int mainAssessmentWeight = assessment.getComponentWeight();
                ArrayList<SubComponent> subAssessments = ((MainComponent) assessment).getSubComponents();

                for (SubComponent subAssessment : subAssessments) {
                    System.out.print("Sub Assessment: " + subAssessment.getComponentName() + " -- (" + subAssessment.getComponentWeight() + "% * " + mainAssessmentWeight + "%) --- ");
                    String subAssessmentName = subAssessment.getComponentName();
                    for (HashMap.Entry<CourseworkComponent, Double> subEntry : mark.getCourseWorkMarks().entrySet()) {
                        CourseworkComponent subKey = subEntry.getKey();
                        Double subValue = subEntry.getValue();
                        if (subKey instanceof SubComponent && subKey.getComponentName().equals(subAssessmentName)) {
                            System.out.println("Mark: " + String.valueOf(subValue));
                            break;
                        }
                    }
                }
                System.out.println("Main Assessment Total: " + result);
                System.out.println();
            }
            System.out.println("Course Total: " + mark.getTotalMark());
            studentGPA += gpaCalculator(mark.getTotalMark()) * mark.getCourse().getAU();
            System.out.println();
        }

        studentGPA /= thisStudentAU;
        System.out.println("GPA for this semester: " + studentGPA);
        if (studentGPA >= 4.50) {
            System.out.println("On track of First Class Honor!");
        } else if (studentGPA >= 4.0) {
            System.out.println("On track of Second Upper Class Honor!");
        } else if (studentGPA >= 3.5) {
            System.out.println("On track of Second Lower Class Honor!");
        } else if (studentGPA >= 3) {
            System.out.println("On track of Third Class Honor!");
        } else {
            System.out.println("Advice: Study hard");
        }


    }

    /**
     * Computes the gpa gained for this course from the result of this course.
     *
     * @param result result of this course
     * @return the grade (in A, B ... )
     */
    public double gpaCalculator(double result) {
        if (result > 85) {
            // A+, A
            return 5d;
        } else if (result > 80) {
            // A-
            return 4.5;
        } else if (result > 75) {
            // B+
            return 4d;
        } else if (result > 70) {
            // B
            return 3.5;
        } else if (result > 65) {
            // B-
            return 3d;
        } else if (result > 60) {
            // C+
            return 2.5d;
        } else if (result > 55) {
            // C
            return 2d;
        } else if (result > 50) {
            // D+
            return 1.5d;
        } else if (result > 45) {
            // D
            return 1d;
        } else {
            // F
            return 0d;
        }

    }

    public void addMark(Mark mark) {
        marks.add(mark);
    }

    public List<Mark> getMarks() {
        return marks;
    }

    public void setCourseMgr(ICourseMgr courseMgr) {
        this.courseMgr = courseMgr;
    }

    public void setStudentMgr(IStudentMgr studentMgr) {
        this.studentMgr = studentMgr;
    }
}
