package collegeapplication.common;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * A repository for Grades that allows concurrent readers but exclusive writer.
 * Demonstrates: Locks (ReadWriteLock)
 */
public class SecureGradeRepository {
    
    private final Map<String, Double> studentGrades = new HashMap<>();
    
    // Demonstrating: Locks (ReadWriteLock)
    // Allows multiple threads to READ at the same time, but only one to WRITE.
    // This is much more efficient than 'synchronized' for read-heavy operations.
    private final ReadWriteLock rwLock = new ReentrantReadWriteLock();
    private final Lock readLock = rwLock.readLock();
    private final Lock writeLock = rwLock.writeLock();

    public void addGrade(String studentId, double grade) {
        writeLock.lock();
        try {
            System.out.println("Acquired Write Lock: Adding grade for " + studentId);
            studentGrades.put(studentId, grade);
            try { Thread.sleep(100); } catch (Exception e) {} // Simulate delay
        } finally {
            writeLock.unlock();
            System.out.println("Released Write Lock");
        }
    }

    public Double getGrade(String studentId) {
        readLock.lock();
        try {
            System.out.println("Acquired Read Lock: Getting grade for " + studentId);
            return studentGrades.get(studentId);
        } finally {
            readLock.unlock();
        }
    }
}
