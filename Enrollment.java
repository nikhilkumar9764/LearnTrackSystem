package LearnTrack;

import java.util.Date;

class Enrollment {
    private String enrollmentId;
    private String studentId;
    private String courseId;
    private Date enrollmentDate;

    public Enrollment(String studentId, String courseId) {
        this.enrollmentId = "ENR" + System.currentTimeMillis();
        this.studentId = studentId;
        this.courseId = courseId;
        this.enrollmentDate = new Date();
    }

    public String getEnrollmentId() { return enrollmentId; }
    public String getStudentId() { return studentId; }
    public String getCourseId() { return courseId; }
    public Date getEnrollmentDate() { return enrollmentDate; }
}

