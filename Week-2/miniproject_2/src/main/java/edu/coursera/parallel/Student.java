package edu.coursera.parallel;

/**
 * A class representing a single student in a single class.
 */
public final class Student {
    /**
     * First name of the student.
     */
    private final String firstName;
    /**
     * Surname of the student.
     */
    private final String lastName;
    /**
     * Age of the student.
     */
    private final double age;
    /**
     * Grade the student has received in the class so far.
     */
    private final int grade;
    /**
     * Whether the student is currently enrolled, or has already completed the
     * course.
     */
    private final boolean isCurrent;

    /**
     * Constructor.
     * @param setFirstName Student first name
     * @param setLastName Student last name
     * @param setAge Student age
     * @param setGrade Student grade in course
     * @param setIsCurrent Student currently enrolled?
     */
    public Student(final String setFirstName, final String setLastName,
            final double setAge, final int setGrade,
            final boolean setIsCurrent) {
        this.firstName = setFirstName;
        this.lastName = setLastName;
        this.age = setAge;
        this.grade = setGrade;
        this.isCurrent = setIsCurrent;
    }

    /**
     * Get the first name of this student.
     * @return The student's first name.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Get the last name of this student.
     * @return The student's last name.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Get the age of this student.
     * @return The student's age.
     */
    public double getAge() {
        return age;
    }

    /**
     * Get the grade this student has achieved in this course so far.
     * @return The student's current grade.
     */
    public int getGrade() {
        return grade;
    }

    /**
     * Check if this student is active, or has taken the course in the past.
     * @return true if the student is currently enrolled, false otherwise
     */
    public boolean checkIsCurrent() {
        return isCurrent;
    }
}
