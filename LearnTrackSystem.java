package LearnTrack;

import java.util.*;

// Main management system
class LearnTrackSystem {
    private Map<String, Student> students;
    private Map<String, Course> courses;
    private List<Enrollment> enrollments;
    private Scanner scanner;

    public LearnTrackSystem() {
        students = new HashMap<>();
        courses = new HashMap<>();
        enrollments = new ArrayList<>();
        scanner = new Scanner(System.in);
    }

    // Student Management Methods
    public void addStudent() {
        try {
            System.out.print("\nEnter Student ID: ");
            String id = scanner.nextLine().trim();

            if (students.containsKey(id)) {
                throw new LearnTrackException("Student ID already exists!");
            }

            System.out.print("Enter Name: ");
            String name = scanner.nextLine().trim();

            System.out.print("Enter Email: ");
            String email = scanner.nextLine().trim();

            if (name.isEmpty() || email.isEmpty()) {
                throw new LearnTrackException("Name and Email cannot be empty!");
            }

            Student student = new Student(id, name, email);
            students.put(id, student);
            System.out.println("✓ Student added successfully!");

        } catch (LearnTrackException e) {
            System.out.println("✗ Error: " + e.getMessage());
        }
    }

    public void viewAllStudents() {
        if (students.isEmpty()) {
            System.out.println("\nNo students found.");
            return;
        }

        System.out.println("\n=== All Students ===");
        System.out.println("Total Students: " + Student.getStudentCount());
        for (Student student : students.values()) {
            student.displayInfo();
        }
    }

    public void searchStudent() {
        System.out.print("\nEnter Student ID to search: ");
        String id = scanner.nextLine().trim();

        Student student = students.get(id);
        if (student != null) {
            student.displayInfo();
            List<String> courses = student.getEnrolledCourses();
            if (!courses.isEmpty()) {
                System.out.println("\nEnrolled in:");
                for (String courseId : courses) {
                    Course course = this.courses.get(courseId);
                    if (course != null) {
                        System.out.println("  - " + course.getCourseName());
                    }
                }
            }
        } else {
            System.out.println("✗ Student not found!");
        }
    }

    // Course Management Methods
    public void addCourse() {
        try {
            System.out.print("\nEnter Course ID: ");
            String id = scanner.nextLine().trim();

            if (courses.containsKey(id)) {
                throw new LearnTrackException("Course ID already exists!");
            }

            System.out.print("Enter Course Name: ");
            String name = scanner.nextLine().trim();

            System.out.print("Enter Instructor: ");
            String instructor = scanner.nextLine().trim();

            System.out.print("Enter Max Capacity: ");
            int capacity = Integer.parseInt(scanner.nextLine().trim());

            if (capacity <= 0) {
                throw new LearnTrackException("Capacity must be greater than 0!");
            }

            Course course = new Course(id, name, instructor, capacity);
            courses.put(id, course);
            System.out.println("✓ Course added successfully!");

        } catch (NumberFormatException e) {
            System.out.println("✗ Error: Invalid number format!");
        } catch (LearnTrackException e) {
            System.out.println("✗ Error: " + e.getMessage());
        }
    }

    public void viewAllCourses() {
        if (courses.isEmpty()) {
            System.out.println("\nNo courses found.");
            return;
        }

        System.out.println("\n=== All Courses ===");
        System.out.println("Total Courses: " + Course.getTotalCourses());
        for (Course course : courses.values()) {
            course.displayInfo();
        }
    }

    // Enrollment Management Methods
    public void enrollStudent() {
        try {
            System.out.print("\nEnter Student ID: ");
            String studentId = scanner.nextLine().trim();

            System.out.print("Enter Course ID: ");
            String courseId = scanner.nextLine().trim();

            Student student = students.get(studentId);
            Course course = courses.get(courseId);

            if (student == null) {
                throw new LearnTrackException("Student not found!");
            }

            if (course == null) {
                throw new LearnTrackException("Course not found!");
            }

            if (student.getEnrolledCourses().contains(courseId)) {
                throw new LearnTrackException("Student already enrolled in this course!");
            }

            if (!course.addStudent(studentId)) {
                throw new LearnTrackException("Course is full!");
            }

            student.enrollInCourse(courseId);
            Enrollment enrollment = new Enrollment(studentId, courseId);
            enrollments.add(enrollment);

            System.out.println("✓ Student enrolled successfully!");
            System.out.println("Enrollment ID: " + enrollment.getEnrollmentId());

        } catch (LearnTrackException e) {
            System.out.println("✗ Error: " + e.getMessage());
        }
    }

    public void dropEnrollment() {
        try {
            System.out.print("\nEnter Student ID: ");
            String studentId = scanner.nextLine().trim();

            System.out.print("Enter Course ID: ");
            String courseId = scanner.nextLine().trim();

            Student student = students.get(studentId);
            Course course = courses.get(courseId);

            if (student == null || course == null) {
                throw new LearnTrackException("Student or Course not found!");
            }

            student.dropCourse(courseId);
            course.removeStudent(studentId);

            enrollments.removeIf(e ->
                    e.getStudentId().equals(studentId) &&
                            e.getCourseId().equals(courseId)
            );

            System.out.println("✓ Enrollment dropped successfully!");

        } catch (LearnTrackException e) {
            System.out.println("✗ Error: " + e.getMessage());
        }
    }

    public void viewAllEnrollments() {
        if (enrollments.isEmpty()) {
            System.out.println("\nNo enrollments found.");
            return;
        }

        System.out.println("\n=== All Enrollments ===");
        System.out.println("Total Enrollments: " + enrollments.size());

        for (Enrollment enrollment : enrollments) {
            Student student = students.get(enrollment.getStudentId());
            Course course = courses.get(enrollment.getCourseId());

            System.out.println("\nEnrollment ID: " + enrollment.getEnrollmentId());
            System.out.println("Student: " + (student != null ? student.getName() : "N/A"));
            System.out.println("Course: " + (course != null ? course.getCourseName() : "N/A"));
            System.out.println("Date: " + enrollment.getEnrollmentDate());
        }
    }

    public void displayMenu() {
        System.out.println("\n╔════════════════════════════════════╗");
        System.out.println("║      LearnTrack System Menu       ║");
        System.out.println("╚════════════════════════════════════╝");
        System.out.println("1.  Add Student");
        System.out.println("2.  View All Students");
        System.out.println("3.  Search Student");
        System.out.println("4.  Add Course");
        System.out.println("5.  View All Courses");
        System.out.println("6.  Enroll Student in Course");
        System.out.println("7.  Drop Student from Course");
        System.out.println("8.  View All Enrollments");
        System.out.println("9.  Exit");
        System.out.print("\nEnter your choice: ");
    }

    public void run() {
        System.out.println("╔════════════════════════════════════╗");
        System.out.println("║   Welcome to LearnTrack System!   ║");
        System.out.println("╚════════════════════════════════════╝");

        boolean running = true;

        while (running) {
            try {
                displayMenu();
                int choice = Integer.parseInt(scanner.nextLine().trim());

                switch (choice) {
                    case 1: addStudent(); break;
                    case 2: viewAllStudents(); break;
                    case 3: searchStudent(); break;
                    case 4: addCourse(); break;
                    case 5: viewAllCourses(); break;
                    case 6: enrollStudent(); break;
                    case 7: dropEnrollment(); break;
                    case 8: viewAllEnrollments(); break;
                    case 9:
                        System.out.println("\nThank you for using LearnTrack!");
                        running = false;
                        break;
                    default:
                        System.out.println("✗ Invalid choice! Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("✗ Invalid input! Please enter a number.");
            } catch (Exception e) {
                System.out.println("✗ An error occurred: " + e.getMessage());
            }
        }

        scanner.close();
    }
}
