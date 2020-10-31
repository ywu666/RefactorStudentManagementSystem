# TEAM 9

Kia ora, we are Team 9 and this is our refactored student management system.

This system provides functionality for managing a school.

We managed our project through a combination of Github, Trello, Google Drive, Zoom, and Facebook Messenger.


## Team
| GitHub Username | Full Name | UPI |
| --------------- | --------- | --- |
| hdr00 | Hana Drysdale | hdry406 |
| saaaaraaaah | Sarah Trenberth | stre757 |
| jge385 | Jiawei Ge | jge385 |
| Crazyclerkm | Callum Daniel  | cdan879 |
| xinyi98 | Xinyi Guo | xguo679 |
| absternator | Anmol Thapar | atha969 |
| ywu660 | Yujia Wu | ywu660 |

## Using the System

When the system is run, the user will be presented with a number of 
options. To select an option, the user should type in the number of the 
desired option and press enter. A description of these different use options
is as follows:

#####0: Print options
Simply prints the list of options again.

#####1: Add a student
This allows the user to add a student. 
First, a user ID can either be entered manually or automatically generated.
Then the user will be prompted to enter a name, school (department),
gender, and school year.
When being prompted for the school or gender, you can press '-h' to see a list of all possible options.

#####2: Add a course
This allows the user to add a course.
First, a course ID must be entered - this should have the format of 2 letters followed by 4 numbers.
Then the user will be prompted to enter a name, course vacancy, number of academic units, 
department, course type, number of lecture groups, lecture hours,
names and capacities for lecture groups,
number of tutorial groups, tutorial hours, names and capacities for tutorial groups,
number of lab groups, lab hours, names and capacities for lab groups,
and professor in charge or course.
When being prompted for the department, course type, or professor, you can press '-h' to see a list of all possible options.

After adding this information, you will be asked if you would like to set course components
and weightage. If you select not yet, you can set these components later.
If you select yes, the process will follow as described in option 6 below.

#####3: Register student for a course including tutorial/lab classes
This allows a student to be registered into a course and related tutorial + lab
First you will be asked to select a student by ID. Then you will be prompted for a department
and a course ID. You will then select a lecture group, tutoral group, and lab group
to register the student in. The system will then print out a representation of
the registration.
When being prompted for the student ID, department, and course ID you can press '-h' to see a list of all possible options.

#####4: Check available slots in a class (vacancy in a class)
This lets the user check how many spots are free in a class.
First enter the ID of an existing course. The system will print information
about the availability for the course as a whole, as well as each of the 
lectures/labs/tutorials.
When being prompted for the course ID you can press '-h' to see a list of all possible options.

#####5: Print student list by lecture, tutorial or laboratory session for a course
This lets the user print lists of student as defined by their associated lab/lecture/tutorial within a course.
First select whether you would like to print by lecture (1), tutorial (2), or lab(3)
The system will then print a list of the students in each of those groups for the given course.
When being prompted for the course ID you can press '-h' to see a list of all possible options.

#####6: Enter course assessment components weightage
This lets the user set course components and their weight for a course.
First enter the ID of an existing course. Then select whether the course will have
a final exam. IF yes, enter the exam weight. Then add the number of main components the 
course should have.
For each component you will then enter a name, weight, and number of subcomponents
For each subcomponent you will enter a name and weight.
Note that all components (+final exam) must total to a weight of 100,
and all subcomponents within a single component must total to a weight of 100.
When being prompted for the course ID you can press '-h' to see a list of all possible options.
The system will then print out the set of components and their respective weights
as you have just assigned.

#####7: Enter coursework mark – inclusive of its components
This lets the user enter marks for coursework.
First, the user will be asked for a student ID and the ID of a course that
student is enrolled in. You will then select a component, and enter a mark.
The system will then print out information about that student's mark in the course.
When being prompted for the student ID and course ID you can press '-h' to see a list of all possible options.

#####8: Enter exam mark
This lets the user enter an exam mark for a student.
First, the user will be asked for a student ID and the ID of a course that
student is enrolled in. You will then enter an exam mark.
The system will then print out information about that student's mark in the course.
When being prompted for the student ID and course ID you can press '-h' to see a list of all possible options.

#####9: Print course statistics
This lets the user print out statistics for any available course.
The user will be asked to enter an ID for an existing course, and then
the system will print out statistics for that course, including enrolment rate
and mark information.
When being prompted for the course ID you can press '-h' to see a list of all possible options.

#####10: Print student transcript
This lets the user print a transcript for a student.
First, enter an ID for an existing student.
The system will then print out a transcript for that student, including all
enrolments and grades, as well as GPA and comment about academic performance.
When being prompted for the student ID you can press '-h' to see a list of all possible options.

#####11: Quit Main System
This option quits the student management system.