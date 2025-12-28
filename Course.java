package LearnTrack;

import java.util.ArrayList;
import java.util.List;

// Course class demonstrating encapsulation
class Course {
    private String courseId;
    private String courseName;
    private String instructor;
    private int maxCapacity;
    private static int totalCourses = 0;
    private List<String> enrolledStudents;

    public Course(String courseId, String courseName, String instructor, int maxCapacity) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.instructor = instructor;
        this.maxCapacity = maxCapacity;
        this.enrolledStudents = new ArrayList<>();
        totalCourses++;
    }

    public String getCourseId() { return courseId; }
    public String getCourseName() { return courseName; }
    public String getInstructor() { return instructor; }
    public int getMaxCapacity() { return maxCapacity; }
    public int getEnrolledCount() { return enrolledStudents.size(); }

    public void setCourseName(String courseName) { this.courseName = courseName; }
    public void setInstructor(String instructor) { this.instructor = instructor; }

    public static int getTotalCourses() {
        return totalCourses;
    }

    public boolean addStudent(String studentId) {
        if (enrolledStudents.size() >= maxCapacity) {
            return false;
        }
        if (!enrolledStudents.contains(studentId)) {
            enrolledStudents.add(studentId);
            return true;
        }
        return false;
    }

    public void removeStudent(String studentId) {
        enrolledStudents.remove(studentId);
    }

    public void displayInfo() {
        System.out.println("\n=== Course Information ===");
        System.out.println("Course ID: " + courseId);
        System.out.println("Name: " + courseName);
        System.out.println("Instructor: " + instructor);
        System.out.println("Capacity: " + enrolledStudents.size() + "/" + maxCapacity);
    }
}