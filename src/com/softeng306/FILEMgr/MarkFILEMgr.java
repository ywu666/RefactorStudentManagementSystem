package com.softeng306.FILEMgr;

import com.softeng306.Entities.*;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

public class MarkFILEMgr extends FILEMgr<Mark> {
    /**
     * The file name of markFile.csv.
     */
    private static final String markFileName = "data/markFile.csv";

    /**
     * The header of markFile.csv.
     */
    private static final String mark_HEADER = "studentID,courseID,courseWorkMarks,totalMark";

    /**
     * The index of studentID in markFile.csv.
     */
    private static final int studentIdIndexInMarks = 0;

    /**
     * The index of courseID in markFile.csv.
     */
    private static final int courseIdIndexInMarks = 1;

    /**
     * The index of totalMark in markFile.csv.
     */
    private static final int totalMarkIndex = 3;

    /**
     * The index of courseWorkMark in markFile.csv..
     */
    private static final int courseWorkMarksIndex = 2;

    @Override
    public void writeIntoFile(Mark mark) {
        FileWriter fileWriter = null;
        try {
            fileWriter = initialiseFileWriter(markFileName, mark_HEADER);
            appendMarkToFile(fileWriter, mark);
        } catch (Exception e) {
            System.out.println("Error in adding a mark to the file.");
            e.printStackTrace();
        } finally {
            printFinallyBlock(fileWriter);
        }
    }

    @Override
    public  List<Mark> loadFromFile() {
        BufferedReader fileReader = null;
        ArrayList<Mark> marks = new ArrayList<Mark>(0);
        try {
            String line;

            /**
             * This part is changed due to refactor
             */
            StudentFILEMgr studentFILEMgr = new StudentFILEMgr();
            CourseFILEMgr courseFILEMgr = new CourseFILEMgr();
            List<Student> students = studentFILEMgr.loadFromFile();
            List<Course> courses = courseFILEMgr.loadFromFile();


            fileReader = new BufferedReader(new FileReader(markFileName));
            //read the header to skip it
            fileReader.readLine();
            while ((line = fileReader.readLine()) != null) {
                Student currentStudent = null;
                Course currentCourse = null;

                HashMap<CourseworkComponent, Double> courseWorkMarks = new HashMap<CourseworkComponent, Double>(0);
                String[] thisCourseWorkMark;

                String[] tokens = line.split(COMMA_DELIMITER);
                if (tokens.length > 0) {
                    String studentID = tokens[studentIdIndexInMarks];

                    for (Student student : students) {
                        if (student.getStudentID().equals(studentID)) {
                            currentStudent = student;
                            break;
                        }
                    }

                    String courseID = tokens[courseIdIndexInMarks];

                    for (Course course : courses) {
                        if (course.getCourseID().equals(courseID)) {
                            currentCourse = course;
                            break;
                        }
                    }

                    String courseWorkMarksString = tokens[courseWorkMarksIndex];

                    String[] eachCourseWorkMark = courseWorkMarksString.split(Pattern.quote(LINE_DELIMITER));

                    for (int i = 0; i < eachCourseWorkMark.length; i++) {
                        thisCourseWorkMark = eachCourseWorkMark[i].split(EQUAL_SIGN);

                        ArrayList<SubComponent> subComponents = new ArrayList<SubComponent>(0);
                        HashMap<SubComponent, Double> subComponentMarks = new HashMap<SubComponent, Double>();
                        for (int j = 3; j < thisCourseWorkMark.length; j++) {
                            if (thisCourseWorkMark[3].equals("")) {
                                break;
                            }
                            String[] thisSubComponent = thisCourseWorkMark[j].split(SLASH);
                            subComponents.add(new SubComponent(thisSubComponent[0], Integer.parseInt(thisSubComponent[1])));
                            subComponentMarks.put(new SubComponent(thisSubComponent[0], Integer.parseInt(thisSubComponent[1])), Double.parseDouble(thisSubComponent[2]));
                        }

                        courseWorkMarks.put(new MainComponent(thisCourseWorkMark[0], Integer.parseInt(thisCourseWorkMark[1]), subComponents), Double.parseDouble(thisCourseWorkMark[2]));
                        // Put sub component
                        for (HashMap.Entry<SubComponent, Double> entry : subComponentMarks.entrySet()) {
                            SubComponent subComponent = entry.getKey();
                            Double subComponentResult = entry.getValue();
                            courseWorkMarks.put(subComponent, subComponentResult);
                        }
                    }
                    Double totalMark = Double.parseDouble(tokens[totalMarkIndex]);
                    Mark mark = new Mark(currentStudent, currentCourse, courseWorkMarks, totalMark);
                    marks.add(mark);
                }
            }
        } catch (Exception e) {
            System.out.println("Error occurs when loading student marks.");
            e.printStackTrace();
        } finally {
            printFinallyBlock(fileReader);
        }
        return marks;
    }


    /**
     * Backs up all the changes of student mark records made into the file.
     *
     * @param marks marks to be backed up into file
     */
    public static void backUpMarks(List<Mark> marks) {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(markFileName);
            fileWriter.append(mark_HEADER);
            fileWriter.append(NEW_LINE_SEPARATOR);

            for (Mark mark : marks) {
                appendMarkToFile(fileWriter, mark);
            }
        } catch (Exception e) {
            System.out.println("Error in adding a mark to the file.");
            e.printStackTrace();
        } finally {
            try {
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                System.out.println("Error occurs in flushing or closing the file.");
                e.printStackTrace();
            }
        }
    }

    /**
     * Writes the marks for one studnent in a single course to the marks CSV.
     * @param fileWriter
     * @param mark
     * @throws IOException
     */
    private static void appendMarkToFile(FileWriter fileWriter, Mark mark) throws IOException {
        fileWriter.append(mark.getStudent().getStudentID());
        fileWriter.append(COMMA_DELIMITER);
        fileWriter.append(mark.getCourse().getCourseID());
        fileWriter.append(COMMA_DELIMITER);
        HashMap<CourseworkComponent, Double> courseworkMarks = mark.getCourseWorkMarks();
        if (!courseworkMarks.isEmpty()) {
            int index = 0;
            for (HashMap.Entry<CourseworkComponent, Double> entry : courseworkMarks.entrySet()) {
                CourseworkComponent key = entry.getKey();
                Double value = entry.getValue();
                if (key instanceof MainComponent) {
                    fileWriter.append(key.getComponentName());
                    fileWriter.append(EQUAL_SIGN);
                    fileWriter.append(String.valueOf(key.getComponentWeight()));
                    fileWriter.append(EQUAL_SIGN);
                    fileWriter.append(String.valueOf(value));
                    fileWriter.append(EQUAL_SIGN);
                    ArrayList<SubComponent> subComponents = ((MainComponent) key).getSubComponents();
                    int subComponent_index = 0;
                    for (SubComponent subComponent : subComponents) {
                        String subComponentName = subComponent.getComponentName();
                        fileWriter.append(subComponentName);
                        fileWriter.append(SLASH);
                        fileWriter.append(String.valueOf(subComponent.getComponentWeight()));
                        fileWriter.append(SLASH);
                        double subComponentMark = 0d;
                        for (HashMap.Entry<CourseworkComponent, Double> subEntry : mark.getCourseWorkMarks().entrySet()) {
                            CourseworkComponent subKey = subEntry.getKey();
                            Double subValue = subEntry.getValue();
                            if (subKey instanceof SubComponent && subKey.getComponentName().equals(subComponentName)) {
                                subComponentMark = subValue;
                                break;
                            }
                        }
                        fileWriter.append(String.valueOf(subComponentMark));
                        subComponent_index++;
                        if (subComponent_index != subComponents.size()) {
                            fileWriter.append(EQUAL_SIGN);
                        }
                    }
                }
                index++;
                if (index != mark.getCourseWorkMarks().size() && (key instanceof MainComponent)) {
                    fileWriter.append(LINE_DELIMITER);
                }
            }
        } else {
            fileWriter.append("NULL");
        }
        fileWriter.append(COMMA_DELIMITER);
        fileWriter.append(String.valueOf(mark.getTotalMark()));
        fileWriter.append(NEW_LINE_SEPARATOR);
    }

}
