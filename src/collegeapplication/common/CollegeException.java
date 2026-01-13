package collegeapplication.common;

/**
 * Custom Exception for College Application to handle business logic errors.
 * Demonstrates: Exception Handling (Custom Exceptions)
 */
public class CollegeException extends Exception {
    public CollegeException(String message) {
        super(message);
    }

    public CollegeException(String message, Throwable cause) {
        super(message, cause);
    }
}
