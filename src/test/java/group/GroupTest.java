package group;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for Group.
 *
 * Test Strategy:
 * - Boundary Value Analysis: Test the boundaries for the number of students.
 * - Decision Tree: Test the decision logic for setting the number of students.
 * - Equivalence Partitioning: Test typical values for the number of students.
 * - Null Checks: Verify that null values are handled properly.
 */
public class GroupTest {

    private Group group;

    @BeforeEach
    public void setUp() {
        group = new Group(GroupName.MSIR);
    }

    // Test for the constructor with valid reference
    @Test
    @DisplayName("Test constructor with valid reference")
    public void testConstructorValid() {
        assertEquals(GroupName.MSIR, group.getReference());
        assertEquals(0, group.getNumberStudent());
    }

    // Test for the constructor with null reference
    @Test
    @DisplayName("Test constructor with null reference")
    public void testConstructorNullReference() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Group(null);
        });
        assertEquals("Group reference cannot be null", exception.getMessage());
    }

    // Parameterized test for setting a valid number of students (Equivalence
    // Partitioning)
    @ParameterizedTest
    @ValueSource(ints = { 0, 1, 10, 100 })
    @DisplayName("Test setting valid number of students")
    public void testSetNumberStudentValid(int numberStudent) {
        group.setNumberStudent(numberStudent);
        assertEquals(numberStudent, group.getNumberStudent());
    }

    // Parameterized test for setting an invalid number of students (Boundary Value
    // Analysis)
    @ParameterizedTest
    @ValueSource(ints = { -1, -10, -100 })
    @DisplayName("Test setting negative number of students")
    public void testSetNumberStudentNegative(int numberStudent) {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            group.setNumberStudent(numberStudent);
        });
        assertEquals("Number of students cannot be negative", exception.getMessage());
    }

    // Test for setting null as the number of students
    @Test
    @DisplayName("Test setting null number of students")
    public void testSetNumberStudentNull() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            group.setNumberStudent(null);
        });
        assertEquals("Number of students cannot be null", exception.getMessage());
    }

    // Test for the toString method
    @Test
    @DisplayName("Test toString method")
    public void testToString() {
        String expected = "Group{reference=MSIR}";
        assertEquals(expected, group.toString());
    }

    // Test for the showGroup method
    @Test
    @DisplayName("Test showGroup method")
    public void testShowGroup() {
        String expected = "Group{reference=MSIR , number student=0}";
        assertEquals(expected, group.showGroup());
    }
}