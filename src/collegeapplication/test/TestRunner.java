package collegeapplication.test;

import collegeapplication.student.Student;

// Demonstrating: Unit Testing
public class TestRunner {

    private static void assertEquals(Object expected, Object actual) {
        if (expected == null && actual == null) return;
        if (expected != null && !expected.equals(actual)) {
            throw new AssertionError("Expected " + expected + " but got " + actual);
        }
    }

    private static void assertTrue(boolean condition) {
        if (!condition) {
            throw new AssertionError("Expected true but got false");
        }
    }

    public static void main(String[] args) {
        System.out.println("Running Unit Tests...");
        int passed = 0;
        int failed = 0;

        // Test 1: Student UserID Generation (Logic Test)
        try {
            System.out.println("Test 1: Student UserID Generation");
            Student student = new Student();
            student.setCourceCode("IT");
            student.setSemorYear(1);
            student.setRollNumber(1001);
            String userId = student.generateUserId();
            
            assertEquals("IT-1-1001", userId);
            System.out.println(" [PASS] Test 1 Passed");
            passed++;
        } catch (Throwable e) {
            System.out.println(" [FAIL] Test 1: " + e.getMessage());
            failed++;
        }

        // Test 2: Student Admission Date (NotNull Test)
        try {
             System.out.println("Test 2: Admission Date Generation");
            Student student = new Student();
            String date = student.generateAdmissionDate();
            
            if (date == null || date.isEmpty()) {
                throw new AssertionError("Date should not be null or empty");
            }
            System.out.println(" [PASS] Test 2 Passed: " + date);
            passed++;
        } catch (Throwable e) {
             System.out.println(" [FAIL] Test 2: " + e.getMessage());
             failed++;
        }

        // Test 3: Synchronization (Keyword) Test
        try {
            System.out.println("Test 3: Synchronization (Keyword)");
            collegeapplication.common.DataBaseConnection.incrementConnectionRequest();
            collegeapplication.common.DataBaseConnection.incrementConnectionRequest();
            int count = collegeapplication.common.DataBaseConnection.getConnectionRequestCount();
             // We can't easily test concurrent race conditions in a simple runner without spawning threads, 
             // but we verify the method works mechanially.
            assertTrue(count >= 2); 
            System.out.println(" [PASS] Test 3 Passed: Count is " + count);
            passed++;
        } catch (Throwable e) {
            System.out.println(" [FAIL] Test 3: " + e.getMessage());
            failed++;
        }

        // Test 4: Exception Handling & Inheritance
        try {
            System.out.println("Test 4: Exception Handling & Inheritance");
            throw new collegeapplication.common.InvalidUserException("Invalid User Demo");
        } catch (collegeapplication.common.CollegeException e) {
            // We caught the specific child exception
            System.out.println(" [PASS] Test 4 Passed: Caught " + e.getClass().getSimpleName());
            passed++;
        } catch (Exception e) {
             System.out.println(" [FAIL] Test 4: Caught wrong exception " + e.getClass().getSimpleName());
             failed++;
        }

        // Test 5: Locks (ReadWriteLock)
        try {
            System.out.println("Test 5: Locks (ReadWriteLock)");
            collegeapplication.common.SecureGradeRepository repo = new collegeapplication.common.SecureGradeRepository();
            repo.addGrade("IT-101", 95.5);
            Double grade = repo.getGrade("IT-101");
            assertEquals(95.5, grade);
             System.out.println(" [PASS] Test 5 Passed");
            passed++;
        } catch (Throwable e) {
             System.out.println(" [FAIL] Test 5: " + e.getMessage());
             failed++;
        }

        System.out.println("-------------------------");
        System.out.println("Total Tests: " + (passed + failed));
        System.out.println("Passed: " + passed);
        System.out.println("Failed: " + failed);
        
        if (failed == 0) {
            System.out.println("ALL TESTS PASSED");
        } else {
            System.out.println("SOME TESTS FAILED");
        }
    }
}
