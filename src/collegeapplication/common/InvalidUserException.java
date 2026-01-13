package collegeapplication.common;

/**
 * Custom Exception for Invalid User.
 * Demonstrates: Inheritance (extends CollegeException which extends Exception)
 */
public class InvalidUserException extends CollegeException {
    
    // Demonstrating: Inheritance (super constructor)
    public InvalidUserException(String message) {
        super(message);
    }
}
