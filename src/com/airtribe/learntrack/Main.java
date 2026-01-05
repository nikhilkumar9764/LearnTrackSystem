package com.airtribe.learntrack;

import com.airtribe.learntrack.constants.AppConstants;
import com.airtribe.learntrack.constants.MenuOptions;
import com.airtribe.learntrack.entity.Course;
import com.airtribe.learntrack.entity.Enrollment;
import com.airtribe.learntrack.entity.Student;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import com.airtribe.learntrack.repository.CourseRepository;
import com.airtribe.learntrack.repository.EnrollmentRepository;
import com.airtribe.learntrack.repository.StudentRepository;
import com.airtribe.learntrack.service.CourseService;
import com.airtribe.learntrack.service.EnrollmentService;
import com.airtribe.learntrack.service.StudentService;
import com.airtribe.learntrack.util.InputValidator;

import java.util.List;
import java.util.Scanner;

/**
 * Main class - Entry point for LearnTrack application.
 * Provides a menu-driven console interface for managing students, courses, and enrollments.
 */
public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static StudentService studentService;
    private static CourseService courseService;
    private static EnrollmentService enrollmentService;

    public static void main(String[] args) {
        // Initialize repositories
        StudentRepository studentRepository = new StudentRepository();
        CourseRepository courseRepository = new CourseRepository();
        EnrollmentRepository enrollmentRepository = new EnrollmentRepository();

        // Initialize services
        studentService = new StudentService(studentRepository);
        courseService = new CourseService(courseRepository);
        enrollmentService = new EnrollmentService(enrollmentRepository, studentService, courseService);

        // Display welcome message
        System.out.println("========================================");
        System.out.println("  Welcome to " + AppConstants.APP_NAME + " v" + AppConstants.VERSION);
        System.out.println("  Student & Course Management System");
        System.out.println("========================================\n");

        // Main menu loop
        boolean running = true;
        while (running) {
            displayMainMenu();
            try {
                String input = scanner.nextLine().trim();
                if (!InputValidator.isValidInteger(input)) {
                    System.out.println(AppConstants.INVALID_OPTION + "\n");
                    continue;
                }

                int choice = Integer.parseInt(input);
                switch (choice) {
                    case MenuOptions.STUDENT_MANAGEMENT:
                        handleStudentManagement();
                        break;
                    case MenuOptions.COURSE_MANAGEMENT:
                        handleCourseManagement();
                        break;
                    case MenuOptions.ENROLLMENT_MANAGEMENT:
                        handleEnrollmentManagement();
                        break;
                    case MenuOptions.EXIT:
                        System.out.println("\nThank you for using " + AppConstants.APP_NAME + "!");
                        running = false;
                        break;
                    default:
                        System.out.println(AppConstants.INVALID_OPTION + "\n");
                }
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage() + "\n");
            }
        }

        scanner.close();
    }

    /**
     * Displays the main menu.
     */
    private static void displayMainMenu() {
        System.out.println("--- Main Menu ---");
        System.out.println("1. Student Management");
        System.out.println("2. Course Management");
        System.out.println("3. Enrollment Management");
        System.out.println("4. Exit");
        System.out.print("Enter your choice: ");
    }

    /**
     * Handles student management menu and operations.
     */
    private static void handleStudentManagement() {
        boolean backToMain = false;
        while (!backToMain) {
            System.out.println("\n--- Student Management ---");
            System.out.println("1. Add New Student");
            System.out.println("2. View All Students");
            System.out.println("3. Search Student by ID");
            System.out.println("4. Deactivate Student");
            System.out.println("5. Back to Main Menu");
            System.out.print("Enter your choice: ");

            try {
                String input = scanner.nextLine().trim();
                if (!InputValidator.isValidInteger(input)) {
                    System.out.println(AppConstants.INVALID_OPTION + "\n");
                    continue;
                }

                int choice = Integer.parseInt(input);
                switch (choice) {
                    case MenuOptions.ADD_STUDENT:
                        addStudent();
                        break;
                    case MenuOptions.VIEW_ALL_STUDENTS:
                        viewAllStudents();
                        break;
                    case MenuOptions.SEARCH_STUDENT_BY_ID:
                        searchStudentById();
                        break;
                    case MenuOptions.DEACTIVATE_STUDENT:
                        deactivateStudent();
                        break;
                    case MenuOptions.BACK_TO_MAIN:
                        backToMain = true;
                        System.out.println();
                        break;
                    default:
                        System.out.println(AppConstants.INVALID_OPTION + "\n");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage() + "\n");
            }
        }
    }

    /**
     * Adds a new student to the system.
     */
    private static void addStudent() {
        try {
            System.out.print("Enter First Name: ");
            String firstName = scanner.nextLine().trim();
            if (!InputValidator.isValidString(firstName)) {
                System.out.println("First name cannot be empty.\n");
                return;
            }

            System.out.print("Enter Last Name: ");
            String lastName = scanner.nextLine().trim();
            if (!InputValidator.isValidString(lastName)) {
                System.out.println("Last name cannot be empty.\n");
                return;
            }

            System.out.print("Enter Email (optional, press Enter to skip): ");
            String email = scanner.nextLine().trim();

            System.out.print("Enter Batch: ");
            String batch = scanner.nextLine().trim();
            if (!InputValidator.isValidString(batch)) {
                System.out.println("Batch cannot be empty.\n");
                return;
            }

            Student student;
            if (email.isEmpty()) {
                student = studentService.addStudent(firstName, lastName, batch);
            } else {
                if (!InputValidator.isValidEmail(email)) {
                    System.out.println("Invalid email format. Student added without email.\n");
                    student = studentService.addStudent(firstName, lastName, batch);
                } else {
                    student = studentService.addStudent(firstName, lastName, email, batch);
                }
            }

            System.out.println("\nStudent added successfully!");
            System.out.println("Student ID: " + student.getId());
            System.out.println("Name: " + student.getDisplayName() + "\n");
        } catch (Exception e) {
            System.out.println("Error adding student: " + e.getMessage() + "\n");
        }
    }

    /**
     * Displays all students.
     */
    private static void viewAllStudents() {
        try {
            List<Student> students = studentService.getAllStudents();
            if (students.isEmpty()) {
                System.out.println("\nNo students found.\n");
                return;
            }

            System.out.println("\n--- All Students ---");
            System.out.println(String.format("%-8s %-20s %-30s %-15s %-8s", 
                "ID", "Name", "Email", "Batch", "Active"));
            System.out.println("--------------------------------------------------------------------------------");
            for (Student student : students) {
                String email = student.getEmail() != null ? student.getEmail() : "N/A";
                System.out.println(String.format("%-8d %-20s %-30s %-15s %-8s",
                    student.getId(),
                    student.getFirstName() + " " + student.getLastName(),
                    email,
                    student.getBatch(),
                    student.isActive() ? "Yes" : "No"));
            }
            System.out.println();
        } catch (Exception e) {
            System.out.println("Error viewing students: " + e.getMessage() + "\n");
        }
    }

    /**
     * Searches for a student by ID.
     */
    private static void searchStudentById() {
        try {
            System.out.print("Enter Student ID: ");
            String input = scanner.nextLine().trim();
            if (!InputValidator.isValidInteger(input)) {
                System.out.println("Invalid ID format.\n");
                return;
            }

            int id = Integer.parseInt(input);
            Student student = studentService.findStudentById(id);
            System.out.println("\n--- Student Details ---");
            System.out.println("ID: " + student.getId());
            System.out.println("Name: " + student.getDisplayName());
            System.out.println("Email: " + (student.getEmail() != null ? student.getEmail() : "N/A"));
            System.out.println("Batch: " + student.getBatch());
            System.out.println("Active: " + (student.isActive() ? "Yes" : "No") + "\n");
        } catch (EntityNotFoundException e) {
            System.out.println(e.getMessage() + "\n");
        } catch (Exception e) {
            System.out.println("Error searching student: " + e.getMessage() + "\n");
        }
    }

    /**
     * Deactivates a student.
     */
    private static void deactivateStudent() {
        try {
            System.out.print("Enter Student ID to deactivate: ");
            String input = scanner.nextLine().trim();
            if (!InputValidator.isValidInteger(input)) {
                System.out.println("Invalid ID format.\n");
                return;
            }

            int id = Integer.parseInt(input);
            studentService.deactivateStudent(id);
            System.out.println("Student deactivated successfully!\n");
        } catch (EntityNotFoundException e) {
            System.out.println(e.getMessage() + "\n");
        } catch (Exception e) {
            System.out.println("Error deactivating student: " + e.getMessage() + "\n");
        }
    }

    /**
     * Handles course management menu and operations.
     */
    private static void handleCourseManagement() {
        boolean backToMain = false;
        while (!backToMain) {
            System.out.println("\n--- Course Management ---");
            System.out.println("1. Add New Course");
            System.out.println("2. View All Courses");
            System.out.println("3. Activate Course");
            System.out.println("4. Deactivate Course");
            System.out.println("5. Back to Main Menu");
            System.out.print("Enter your choice: ");

            try {
                String input = scanner.nextLine().trim();
                if (!InputValidator.isValidInteger(input)) {
                    System.out.println(AppConstants.INVALID_OPTION + "\n");
                    continue;
                }

                int choice = Integer.parseInt(input);
                switch (choice) {
                    case MenuOptions.ADD_COURSE:
                        addCourse();
                        break;
                    case MenuOptions.VIEW_ALL_COURSES:
                        viewAllCourses();
                        break;
                    case MenuOptions.ACTIVATE_COURSE:
                        activateCourse();
                        break;
                    case MenuOptions.DEACTIVATE_COURSE:
                        deactivateCourse();
                        break;
                    case MenuOptions.BACK_TO_MAIN_COURSE:
                        backToMain = true;
                        System.out.println();
                        break;
                    default:
                        System.out.println(AppConstants.INVALID_OPTION + "\n");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage() + "\n");
            }
        }
    }

    /**
     * Adds a new course to the system.
     */
    private static void addCourse() {
        try {
            System.out.print("Enter Course Name: ");
            String courseName = scanner.nextLine().trim();
            if (!InputValidator.isValidString(courseName)) {
                System.out.println("Course name cannot be empty.\n");
                return;
            }

            System.out.print("Enter Description: ");
            String description = scanner.nextLine().trim();
            if (!InputValidator.isValidString(description)) {
                System.out.println("Description cannot be empty.\n");
                return;
            }

            System.out.print("Enter Duration (in weeks): ");
            String durationInput = scanner.nextLine().trim();
            if (!InputValidator.isValidInteger(durationInput)) {
                System.out.println("Invalid duration. Please enter a number.\n");
                return;
            }

            int duration = Integer.parseInt(durationInput);
            if (duration <= 0) {
                System.out.println("Duration must be greater than 0.\n");
                return;
            }

            Course course = courseService.addCourse(courseName, description, duration);
            System.out.println("\nCourse added successfully!");
            System.out.println("Course ID: " + course.getId());
            System.out.println("Course Name: " + course.getCourseName() + "\n");
        } catch (Exception e) {
            System.out.println("Error adding course: " + e.getMessage() + "\n");
        }
    }

    /**
     * Displays all courses.
     */
    private static void viewAllCourses() {
        try {
            List<Course> courses = courseService.getAllCourses();
            if (courses.isEmpty()) {
                System.out.println("\nNo courses found.\n");
                return;
            }

            System.out.println("\n--- All Courses ---");
            System.out.println(String.format("%-8s %-30s %-40s %-12s %-8s",
                "ID", "Course Name", "Description", "Duration", "Active"));
            System.out.println("--------------------------------------------------------------------------------");
            for (Course course : courses) {
                String desc = course.getDescription().length() > 37 
                    ? course.getDescription().substring(0, 37) + "..." 
                    : course.getDescription();
                System.out.println(String.format("%-8d %-30s %-40s %-12d %-8s",
                    course.getId(),
                    course.getCourseName(),
                    desc,
                    course.getDurationInWeeks(),
                    course.isActive() ? "Yes" : "No"));
            }
            System.out.println();
        } catch (Exception e) {
            System.out.println("Error viewing courses: " + e.getMessage() + "\n");
        }
    }

    /**
     * Activates a course.
     */
    private static void activateCourse() {
        try {
            System.out.print("Enter Course ID to activate: ");
            String input = scanner.nextLine().trim();
            if (!InputValidator.isValidInteger(input)) {
                System.out.println("Invalid ID format.\n");
                return;
            }

            int id = Integer.parseInt(input);
            courseService.activateCourse(id);
            System.out.println("Course activated successfully!\n");
        } catch (EntityNotFoundException e) {
            System.out.println(e.getMessage() + "\n");
        } catch (Exception e) {
            System.out.println("Error activating course: " + e.getMessage() + "\n");
        }
    }

    /**
     * Deactivates a course.
     */
    private static void deactivateCourse() {
        try {
            System.out.print("Enter Course ID to deactivate: ");
            String input = scanner.nextLine().trim();
            if (!InputValidator.isValidInteger(input)) {
                System.out.println("Invalid ID format.\n");
                return;
            }

            int id = Integer.parseInt(input);
            courseService.deactivateCourse(id);
            System.out.println("Course deactivated successfully!\n");
        } catch (EntityNotFoundException e) {
            System.out.println(e.getMessage() + "\n");
        } catch (Exception e) {
            System.out.println("Error deactivating course: " + e.getMessage() + "\n");
        }
    }

    /**
     * Handles enrollment management menu and operations.
     */
    private static void handleEnrollmentManagement() {
        boolean backToMain = false;
        while (!backToMain) {
            System.out.println("\n--- Enrollment Management ---");
            System.out.println("1. Enroll Student in Course");
            System.out.println("2. View Enrollments by Student");
            System.out.println("3. Mark Enrollment as Completed");
            System.out.println("4. Mark Enrollment as Cancelled");
            System.out.println("5. Back to Main Menu");
            System.out.print("Enter your choice: ");

            try {
                String input = scanner.nextLine().trim();
                if (!InputValidator.isValidInteger(input)) {
                    System.out.println(AppConstants.INVALID_OPTION + "\n");
                    continue;
                }

                int choice = Integer.parseInt(input);
                switch (choice) {
                    case MenuOptions.ENROLL_STUDENT:
                        enrollStudent();
                        break;
                    case MenuOptions.VIEW_ENROLLMENTS_BY_STUDENT:
                        viewEnrollmentsByStudent();
                        break;
                    case MenuOptions.MARK_ENROLLMENT_COMPLETED:
                        markEnrollmentCompleted();
                        break;
                    case MenuOptions.MARK_ENROLLMENT_CANCELLED:
                        markEnrollmentCancelled();
                        break;
                    case MenuOptions.BACK_TO_MAIN_ENROLLMENT:
                        backToMain = true;
                        System.out.println();
                        break;
                    default:
                        System.out.println(AppConstants.INVALID_OPTION + "\n");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage() + "\n");
            }
        }
    }

    /**
     * Enrolls a student in a course.
     */
    private static void enrollStudent() {
        try {
            System.out.print("Enter Student ID: ");
            String studentInput = scanner.nextLine().trim();
            if (!InputValidator.isValidInteger(studentInput)) {
                System.out.println("Invalid Student ID format.\n");
                return;
            }

            System.out.print("Enter Course ID: ");
            String courseInput = scanner.nextLine().trim();
            if (!InputValidator.isValidInteger(courseInput)) {
                System.out.println("Invalid Course ID format.\n");
                return;
            }

            int studentId = Integer.parseInt(studentInput);
            int courseId = Integer.parseInt(courseInput);

            Enrollment enrollment = enrollmentService.enrollStudent(studentId, courseId);
            System.out.println("\nEnrollment successful!");
            System.out.println("Enrollment ID: " + enrollment.getId());
            System.out.println("Student ID: " + enrollment.getStudentId());
            System.out.println("Course ID: " + enrollment.getCourseId());
            System.out.println("Enrollment Date: " + enrollment.getEnrollmentDate());
            System.out.println("Status: " + enrollment.getStatus() + "\n");
        } catch (EntityNotFoundException e) {
            System.out.println(e.getMessage() + "\n");
        } catch (Exception e) {
            System.out.println("Error enrolling student: " + e.getMessage() + "\n");
        }
    }

    /**
     * Displays enrollments for a specific student.
     */
    private static void viewEnrollmentsByStudent() {
        try {
            System.out.print("Enter Student ID: ");
            String input = scanner.nextLine().trim();
            if (!InputValidator.isValidInteger(input)) {
                System.out.println("Invalid ID format.\n");
                return;
            }

            int studentId = Integer.parseInt(input);
            List<Enrollment> enrollments = enrollmentService.getEnrollmentsByStudent(studentId);

            if (enrollments.isEmpty()) {
                System.out.println("\nNo enrollments found for this student.\n");
                return;
            }

            System.out.println("\n--- Enrollments for Student ID: " + studentId + " ---");
            System.out.println(String.format("%-12s %-12s %-12s %-15s %-12s",
                "Enrollment ID", "Student ID", "Course ID", "Enrollment Date", "Status"));
            System.out.println("--------------------------------------------------------------------------------");
            for (Enrollment enrollment : enrollments) {
                System.out.println(String.format("%-12d %-12d %-12d %-15s %-12s",
                    enrollment.getId(),
                    enrollment.getStudentId(),
                    enrollment.getCourseId(),
                    enrollment.getEnrollmentDate(),
                    enrollment.getStatus()));
            }
            System.out.println();
        } catch (EntityNotFoundException e) {
            System.out.println(e.getMessage() + "\n");
        } catch (Exception e) {
            System.out.println("Error viewing enrollments: " + e.getMessage() + "\n");
        }
    }

    /**
     * Marks an enrollment as completed.
     */
    private static void markEnrollmentCompleted() {
        try {
            System.out.print("Enter Enrollment ID: ");
            String input = scanner.nextLine().trim();
            if (!InputValidator.isValidInteger(input)) {
                System.out.println("Invalid ID format.\n");
                return;
            }

            int enrollmentId = Integer.parseInt(input);
            enrollmentService.markEnrollmentCompleted(enrollmentId);
            System.out.println("Enrollment marked as completed successfully!\n");
        } catch (EntityNotFoundException e) {
            System.out.println(e.getMessage() + "\n");
        } catch (Exception e) {
            System.out.println("Error updating enrollment: " + e.getMessage() + "\n");
        }
    }

    /**
     * Marks an enrollment as cancelled.
     */
    private static void markEnrollmentCancelled() {
        try {
            System.out.print("Enter Enrollment ID: ");
            String input = scanner.nextLine().trim();
            if (!InputValidator.isValidInteger(input)) {
                System.out.println("Invalid ID format.\n");
                return;
            }

            int enrollmentId = Integer.parseInt(input);
            enrollmentService.markEnrollmentCancelled(enrollmentId);
            System.out.println("Enrollment marked as cancelled successfully!\n");
        } catch (EntityNotFoundException e) {
            System.out.println(e.getMessage() + "\n");
        } catch (Exception e) {
            System.out.println("Error updating enrollment: " + e.getMessage() + "\n");
        }
    }
}

