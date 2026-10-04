package Student_management_System;
import java.util.Scanner;
public class Main {
    
	public static void main(String[] args) {

	        Scanner sc = new Scanner(System.in);

	        // Create all required database tables
	        tablesCreation.createStudentsTable();
	        tablesCreation.createCoursesTable();
	        tablesCreation.createEnrollmentsTable();
	        tablesCreation.createAttendanceTable();
	        tablesCreation.createMarksTable();

	        int choice;

	        do {

	            System.out.println("\n==========================================");
	            System.out.println("       STUDENT MANAGEMENT SYSTEM");
	            System.out.println("==========================================");

	            System.out.println("\n----- STUDENT MANAGEMENT -----");
	            System.out.println("1. Add Student");
	            System.out.println("2. View Students");
	            System.out.println("3. Update Student");
	            System.out.println("4. Delete Student");

	            System.out.println("\n----- COURSE MANAGEMENT -----");
	            System.out.println("5. Add Course");
	            System.out.println("6. View Courses");
	            System.out.println("7. Enroll Student");

	            System.out.println("\n----- ACADEMIC MANAGEMENT -----");
	            System.out.println("8. Add Attendance");
	            System.out.println("9. Add Marks");
	            System.out.println("10. View Student Courses");
	            System.out.println("11. Generate Result");

	            System.out.println("\n12. Exit");

	            System.out.print("\nEnter your choice: ");
	            choice = sc.nextInt();
	            sc.nextLine();

	            switch (choice) {

	                // Student Management
	                case 1:
	                    tablesCreation.addStudent(sc);
	                    break;

	                case 2:
	                    tablesCreation.fetchStudents();
	                    break;

	                case 3:
	                    tablesCreation.updateStudent(sc);
	                    break;

	                case 4:
	                    tablesCreation.deleteStudent(sc);
	                    break;

	                // Course Management
	                case 5:
	                    tablesCreation.addCourse(sc);
	                    break;

	                case 6:
	                    tablesCreation.fetchCourses();
	                    break;

	                case 7:
	                    tablesCreation.enrollStudent(sc);
	                    break;

	                // Academic Management
	                case 8:
	                    tablesCreation.addAttendance(sc);
	                    break;

	                case 9:
	                    tablesCreation.addMarks(sc);
	                    break;

	                case 10:
	                    tablesCreation.fetchStudentCourses();
	                    break;

	                case 11:
	                    tablesCreation.generateResult(sc);
	                    break;

	                // Exit
	                case 12:
	                    System.out.println(
	                            "Thank you for using Student Management System."
	                    );
	                    break;

	                default:
	                    System.out.println(
	                            "Invalid choice. Please enter 1 to 12."
	                    );
	            }

	        } while (choice != 12);

	        sc.close();
	    }
	

}
