package edu.coursera.parallel;

import java.util.Random;

import junit.framework.TestCase;

public class StudentAnalyticsTest extends TestCase {
    final static int REPEATS = 10;
    private final static String[] firstNames = {"Sanjay", "Yunming", "John", "Vivek", "Shams", "Max"};
    private final static String[] lastNames = {"Chatterjee", "Zhang", "Smith", "Sarkar", "Imam", "Grossman"};

    private static int getNCores() {
        String ncoresStr = System.getenv("COURSERA_GRADER_NCORES");
        if (ncoresStr == null) {
            return Runtime.getRuntime().availableProcessors();
        } else {
            return Integer.parseInt(ncoresStr);
        }
    }

    private Student[] generateStudentData() {
        final int N_STUDENTS = 2000000;
        final int N_CURRENT_STUDENTS = 600000;

        Student[] students = new Student[N_STUDENTS];
        Random r = new Random(123);

        for (int s = 0; s < N_STUDENTS; s++) {
            final String firstName = firstNames[r.nextInt(firstNames.length)];
            final String lastName = lastNames[r.nextInt(lastNames.length)];
            final double age = r.nextDouble() * 100.0;
            final int grade = 1 + r.nextInt(100);
            final boolean current = (s < N_CURRENT_STUDENTS);

            students[s] = new Student(firstName, lastName, age, grade, current);
        }

        return students;
    }

    private double averageAgeOfEnrolledStudentsHelper(final int repeats) {
        final Student[] students = generateStudentData();
        final StudentAnalytics analytics = new StudentAnalytics();

        final double ref = analytics.averageAgeOfEnrolledStudentsImperative(students);

        final long startSequential = System.currentTimeMillis();
        for (int r = 0; r < repeats; r++) {
            analytics.averageAgeOfEnrolledStudentsImperative(students);
        }
        final long endSequential = System.currentTimeMillis();

        final double calc = analytics.averageAgeOfEnrolledStudentsParallelStream(students);
        final double err = Math.abs(calc - ref);
        final String msg = "Expected " + ref + " but found " + calc + ", err = " + err;
        assertTrue(msg, err < 1E-5);

        final long startParallel = System.currentTimeMillis();
        for (int r = 0; r < repeats; r++) {
            analytics.averageAgeOfEnrolledStudentsParallelStream(students);
        }
        final long endParallel = System.currentTimeMillis();

        return (double)(endSequential - startSequential) / (double)(endParallel - startParallel);
    }

    /*
     * Test correctness of averageAgeOfEnrolledStudentsParallelStream.
     */
    public void testAverageAgeOfEnrolledStudents() {
        averageAgeOfEnrolledStudentsHelper(1);
    }

    /*
     * Test performance of averageAgeOfEnrolledStudentsParallelStream.
     */
    public void testAverageAgeOfEnrolledStudentsPerf() {
        final int ncores = getNCores();
        final double speedup = averageAgeOfEnrolledStudentsHelper(REPEATS);
        String msg = "Expected parallel version to run at least 1.2x faster but speedup was " + speedup;
        assertTrue(msg, speedup > 1.2);
    }

    private double mostCommonFirstNameOfInactiveStudentsHelper(final int repeats) {
        final Student[] students = generateStudentData();
        final StudentAnalytics analytics = new StudentAnalytics();

        final String ref = analytics.mostCommonFirstNameOfInactiveStudentsImperative(students);

        final long startSequential = System.currentTimeMillis();
        for (int r = 0; r < repeats; r++) {
            analytics.mostCommonFirstNameOfInactiveStudentsImperative(students);
        }
        final long endSequential = System.currentTimeMillis();

        final String calc = analytics.mostCommonFirstNameOfInactiveStudentsParallelStream(students);
        assertEquals("Mismatch in calculated values", ref, calc);

        final long startParallel = System.currentTimeMillis();
        for (int r = 0; r < repeats; r++) {
            analytics.mostCommonFirstNameOfInactiveStudentsParallelStream(students);
        }
        final long endParallel = System.currentTimeMillis();

        return (double)(endSequential - startSequential) / (double)(endParallel - startParallel);
    }

    /*
     * Test correctness of mostCommonFirstNameOfInactiveStudentsParallelStream.
     */
    public void testMostCommonFirstNameOfInactiveStudents() {
        mostCommonFirstNameOfInactiveStudentsHelper(1);
    }

    /*
     * Test performance of mostCommonFirstNameOfInactiveStudentsParallelStream.
     */
    public void testMostCommonFirstNameOfInactiveStudentsPerf() {
        final int ncores = getNCores();
        final double speedup = mostCommonFirstNameOfInactiveStudentsHelper(REPEATS);
        final double expectedSpeedup = (double)ncores * 0.5;
        String msg = "Expected speedup to be at least " + expectedSpeedup + " but was " + speedup;
        assertTrue(msg, speedup >= expectedSpeedup);

    }

    private double countNumberOfFailedStudentsOlderThan20Helper(final int repeats) {
        final Student[] students = generateStudentData();
        final StudentAnalytics analytics = new StudentAnalytics();

        final int ref = analytics.countNumberOfFailedStudentsOlderThan20Imperative(students);

        final long startSequential = System.currentTimeMillis();
        for (int r = 0; r < repeats; r++) {
            analytics.countNumberOfFailedStudentsOlderThan20Imperative(students);
        }
        final long endSequential = System.currentTimeMillis();

        final int calc = analytics.countNumberOfFailedStudentsOlderThan20ParallelStream(students);
        assertEquals("Mismatch in calculated values", ref, calc);

        final long startParallel = System.currentTimeMillis();
        for (int r = 0; r < repeats; r++) {
            analytics.countNumberOfFailedStudentsOlderThan20ParallelStream(students);
        }
        final long endParallel = System.currentTimeMillis();

        return (double)(endSequential - startSequential) / (double)(endParallel - startParallel);
    }

    /*
     * Test correctness of countNumberOfFailedStudentsOlderThan20ParallelStream.
     */
    public void testCountNumberOfFailedStudentsOlderThan20() {
        countNumberOfFailedStudentsOlderThan20Helper(1);
    }

    /*
     * Test performance of countNumberOfFailedStudentsOlderThan20ParallelStream.
     */
    public void testCountNumberOfFailedStudentsOlderThan20Perf() {
        final int ncores = getNCores();
        final double speedup = countNumberOfFailedStudentsOlderThan20Helper(REPEATS);
        String msg = "Expected parallel version to run at least 1.2x faster but speedup was " + speedup;
        assertTrue(msg, speedup > 1.2);
    }

}
