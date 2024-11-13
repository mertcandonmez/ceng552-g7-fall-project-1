package student;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import group.Group;
import group.GroupName;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StudentServiceTest {

    private StudentService studentService;

    @BeforeEach
    void setUp() {
        studentService = new StudentService();
    }

    // Boundary Value Analysis: Test minimum and maximum boundaries for number of
    // students
    @Test
    void testAddStudent_MinBoundary() {
        // Min boundary: 0 students
        assertTrue(studentService.allStudents().isEmpty(), "List should be empty initially");

        // Add one student to transition from empty state
        studentService.saveStudent(1, "John Doe", LocalDate.of(2000, 1, 1), new Group(GroupName.MSIR));
        assertEquals(1, studentService.allStudents().size(), "List should contain 1 student after adding");
    }

    @Test
    void testAddStudent_MaxBoundary() {
        // Max boundary example (assuming 100 students as max for this test)
        for (int i = 1; i <= 100; i++) {
            studentService.saveStudent(i, "Student " + i, LocalDate.of(2000, 1, 1), new Group(GroupName.MSIR));
        }
        assertEquals(100, studentService.allStudents().size(), "List should contain 100 students at max boundary");
    }

    // Decision Tree: Test decision logic in delete and update methods
    @Test
    void testDeleteStudent_DecisionTree() {
        studentService.saveStudent(1, "John Doe", LocalDate.of(2000, 1, 1), new Group(GroupName.MSIR));
        studentService.saveStudent(2, "Jane Doe", LocalDate.of(1999, 5, 10), new Group(GroupName.MIAD));

        studentService.deleteStudent(1);
        assertEquals(1, studentService.allStudents().size(), "List should contain 1 student after deletion");
        assertEquals(2, studentService.allStudents().get(0).getId(), "Remaining student should have ID 2");

        // Test deleting non-existing student
        studentService.deleteStudent(3);
        assertEquals(1, studentService.allStudents().size(), "List should remain the same if student doesn't exist");
    }

    // Equivalence Partitioning: Test adding students with typical data
    @Test
    void testAddStudent_EquivalencePartitioning() {
        studentService.saveStudent(1, "Alice", LocalDate.of(1998, 2, 2), new Group(GroupName.MSIA));
        studentService.saveStudent(2, "Bob", LocalDate.of(2001, 3, 15), new Group(GroupName.MSIR));

        List<Student> students = studentService.allStudents();
        assertEquals(2, students.size(), "List should contain 2 students in typical case");
    }

    // Null Checks: Ensure methods handle nulls properly
    @Test
    void testSaveStudent_NullChecks() {
        assertThrows(NullPointerException.class,
                () -> studentService.saveStudent(null, "John Doe", LocalDate.of(2000, 1, 1), new Group(GroupName.MSIR)),
                "Should throw exception for null ID");

        assertThrows(NullPointerException.class,
                () -> studentService.saveStudent(1, null, LocalDate.of(2000, 1, 1), new Group(GroupName.MSIR)),
                "Should throw exception for null full name");

        assertThrows(NullPointerException.class,
                () -> studentService.saveStudent(1, "John Doe", null, new Group(GroupName.MSIR)),
                "Should throw exception for null date of birth");

        assertThrows(NullPointerException.class,
                () -> studentService.saveStudent(1, "John Doe", LocalDate.of(2000, 1, 1), null),
                "Should throw exception for null group");
    }

    @Test
    void testUpdateStudent_NullChecks() {
        studentService.saveStudent(1, "John Doe", LocalDate.of(2000, 1, 1), new Group(GroupName.MSIR));

        assertThrows(NullPointerException.class,
                () -> studentService.updateStudent(1, null, LocalDate.of(2000, 1, 1), new Group(GroupName.MSIA)),
                "Should throw exception for null full name in update");

        assertThrows(NullPointerException.class,
                () -> studentService.updateStudent(1, "John Doe", null, new Group(GroupName.MSIA)),
                "Should throw exception for null date of birth in update");

        assertThrows(NullPointerException.class,
                () -> studentService.updateStudent(1, "John Doe", LocalDate.of(2000, 1, 1), null),
                "Should throw exception for null group in update");
    }

    // Additional tests for Group behavior
    @Test
    void testGroupNumberStudent_BoundaryValues() {
        Group group = new Group(GroupName.MSIR);

        // Min boundary
        group.setNumberStudent(0);
        assertEquals(0, group.getNumberStudent(), "Number of students should be 0 at min boundary");

        // Negative boundary
        assertThrows(IllegalArgumentException.class, () -> group.setNumberStudent(-1),
                "Should throw exception for negative number of students");

        // Typical value
        group.setNumberStudent(10);
        assertEquals(10, group.getNumberStudent(), "Number of students should be 10");
    }
}