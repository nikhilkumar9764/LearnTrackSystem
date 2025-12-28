package LearnTrack;

import java.util.ArrayList;
import java.util.List;

// Student class extending Person
class Student extends Person {
    private static int studentCount = 0; // Static member
    private List<String> enrolledCourses;

    public Student(String id, String name, String email) {
        super(id, name, email);
        this.enrolledCourses = new ArrayList<>();
        studentCount++;
    }

    public static int getStudentCount() {
        return studentCount;
    }

    public List<String> getEnrolledCourses() {
        return new ArrayList<>(enrolledCourses);
    }

    public void enrollInCourse(String courseId) {
        if (!enrolledCourses.contains(courseId)) {
            enrolledCourses.add(courseId);
        }
    }

    public void dropCourse(String courseId) {
        enrolledCourses.remove(courseId);
    }

    @Override
    public void displayInfo() {
        System.out.println("\n=== Student Information ===");
        System.out.println("ID: " + getId());
        System.out.println("Name: " + getName());
        System.out.println("Email: " + getEmail());
        System.out.println("Enrolled Courses: " +
                (enrolledCourses.isEmpty() ? "None" : enrolledCourses.size()));
    }
}

