package student;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import group.Group;
import group.GroupName;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StudentServiceTest {

    private StudentService studentService;

    @BeforeEach
    @DisplayName("Initialize StudentService with one default student")
    void setUp() {
        studentService = new StudentService();
        studentService.saveStudent(1, "John Doe", LocalDate.of(2000, 1, 1), new Group(GroupName.MSIR));
    }

    // Boundary Value Tests for the `saveStudent` method parameters
    @Test
    @DisplayName("Save Student with boundary values for ID")
    void testSaveStudent_BoundaryId() {
        // Minimum boundary for ID
        studentService.saveStudent(1, "Min Id", LocalDate.of(2000, 1, 1), new Group(GroupName.MSIR));
        assertEquals(1, studentService.findById(1).getId(), "ID should match minimum boundary value");

        // Typical value just above the minimum boundary
        studentService.saveStudent(2, "Next Min Id", LocalDate.of(2000, 1, 1), new Group(GroupName.MSIR));
        assertEquals(2, studentService.findById(2).getId(), "ID should match just above minimum boundary");

        // High boundary example (assuming Integer.MAX_VALUE as upper limit)
        studentService.saveStudent(Integer.MAX_VALUE, "Max Id", LocalDate.of(2000, 1, 1), new Group(GroupName.MSIR));
        assertEquals(Integer.MAX_VALUE, studentService.findById(Integer.MAX_VALUE).getId(),
                "ID should match maximum boundary value");
    }

    @Test
    @DisplayName("Save Student with boundary values for fullName")
    void testSaveStudent_BoundaryFullName() {
        // Boundary for fullName length, assuming non-empty is required
        studentService.saveStudent(3, "A", LocalDate.of(2000, 1, 1), new Group(GroupName.MSIR));
        assertEquals("A", studentService.findById(3).getFullName(), "Full name should match single character boundary");

        // Typical longer name at upper boundary (assuming 255 characters as a limit)
        String longName = "A".repeat(255);
        studentService.saveStudent(4, longName, LocalDate.of(2000, 1, 1), new Group(GroupName.MSIR));
        assertEquals(longName, studentService.findById(4).getFullName(), "Full name should match long boundary value");
    }

    @Test
    @DisplayName("Save Student with boundary values for dateOfBirth")
    void testSaveStudent_BoundaryDateOfBirth() {
        LocalDate minDate = LocalDate.of(1900, 1, 1);
        studentService.saveStudent(5, "Old Student", minDate, new Group(GroupName.MSIR));
        assertEquals(minDate, studentService.findById(5).getDateBirth(),
                "Date of birth should match minimum boundary date");

        LocalDate currentDate = LocalDate.now();
        studentService.saveStudent(6, "New Student", currentDate, new Group(GroupName.MSIR));
        assertEquals(currentDate, studentService.findById(6).getDateBirth(),
                "Date of birth should match upper boundary date");
    }

    @Test
    @DisplayName("Save Student with boundary values for Group")
    void testSaveStudent_BoundaryGroup() {
        Group groupMin = new Group(GroupName.MSIR);
        studentService.saveStudent(7, "Student Min Group", LocalDate.of(2000, 1, 1), groupMin);
        assertEquals(groupMin, studentService.findById(7).getGroup(), "Group should match minimum valid boundary");

        Group groupMax = new Group(GroupName.MIAD);
        studentService.saveStudent(8, "Student Max Group", LocalDate.of(2000, 1, 1), groupMax);
        assertEquals(groupMax, studentService.findById(8).getGroup(), "Group should match maximum valid boundary");
    }

    @Test
    @DisplayName("Save Student should throw exception for null parameters")
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
    @DisplayName("Find Student by valid ID")
    void testFindById_ValidId() {
        Student student = studentService.findById(1);
        assertNotNull(student, "Student should be found");
        assertEquals(1, student.getId(), "Student ID should match the requested ID");
        assertEquals("John Doe", student.getFullName(), "Student full name should match");
    }

    @Test
    @DisplayName("Find Student by invalid ID should throw exception")
    void testFindById_InvalidId() {
        assertThrows(IllegalArgumentException.class, () -> studentService.findById(99),
                "Should throw exception for non-existing ID");
    }

    @Test
    @DisplayName("Find Student by null ID should throw exception")
    void testFindById_NullId() {
        assertThrows(NullPointerException.class, () -> studentService.findById(null),
                "Should throw exception for null ID");
    }

    @Test
    @DisplayName("Update Student by valid ID should modify Student details")
    void testUpdateStudent_ValidId() {
        studentService.updateStudent(1, "Jane Doe", LocalDate.of(1999, 5, 10), new Group(GroupName.MIAD));
        Student updatedStudent = studentService.findById(1);
        assertEquals("Jane Doe", updatedStudent.getFullName(), "Student's name should be updated");
        assertEquals(LocalDate.of(1999, 5, 10), updatedStudent.getDateBirth(),
                "Student's date of birth should be updated");
        assertEquals(GroupName.MIAD, updatedStudent.getGroup().getReference(), "Student's group should be updated");
    }

    @Test
    @DisplayName("Update Student should throw exception for null values")
    void testUpdateStudent_NullValues() {
        Group group = new Group(GroupName.MSIR);
        LocalDate dob = LocalDate.of(2000, 1, 1);

        assertThrows(NullPointerException.class, () -> studentService.updateStudent(null, "John Doe", dob, group),
                "Should throw exception for null ID");
        assertThrows(NullPointerException.class, () -> studentService.updateStudent(1, null, dob, group),
                "Should throw exception for null full name");
        assertThrows(NullPointerException.class, () -> studentService.updateStudent(1, "John Doe", null, group),
                "Should throw exception for null date of birth");
        assertThrows(NullPointerException.class, () -> studentService.updateStudent(1, "John Doe", dob, null),
                "Should throw exception for null group");
    }

    @Test
    @DisplayName("Update Student with invalid ID should throw exception")
    void testUpdateStudent_InvalidId() {
        Group group = new Group(GroupName.MIAD);
        LocalDate dob = LocalDate.of(1999, 5, 10);

        assertThrows(IllegalArgumentException.class, () -> studentService.updateStudent(99, "Jane Doe", dob, group),
                "Should throw exception for non-existing ID");
    }

    @Test
    @DisplayName("Delete Student and verify list size and contents")
    void testDeleteStudent_DecisionTree() {
        studentService.saveStudent(2, "Jane Doe", LocalDate.of(1999, 5, 10), new Group(GroupName.MIAD));
        studentService.deleteStudent(1);
        assertEquals(1, studentService.allStudents().size(), "List should contain 1 student after deletion");
        assertEquals(2, studentService.allStudents().get(0).getId(), "Remaining student should have ID 2");

        studentService.deleteStudent(99); // Non-existing ID
        assertEquals(1, studentService.allStudents().size(), "List should remain the same if student doesn't exist");
    }

    @Test
    @DisplayName("Add students using Equivalence Partitioning")
    void testAddStudent_EquivalencePartitioning() {
        studentService.saveStudent(2, "Alice", LocalDate.of(1998, 2, 2), new Group(GroupName.MSIA));
        studentService.saveStudent(3, "Bob", LocalDate.of(2001, 3, 15), new Group(GroupName.MSIR));

        List<Student> students = studentService.allStudents();
        assertEquals(3, students.size(), "List should contain 3 students in typical case");
    }

    @Test
    @DisplayName("Test boundary values for Group's number of students")
    void testGroupNumberStudent_BoundaryValues() {
        Group group = new Group(GroupName.MSIR);

        group.setNumberStudent(0);
        assertEquals(0, group.getNumberStudent(), "Number of students should be 0 at min boundary");

        assertThrows(IllegalArgumentException.class, () -> group.setNumberStudent(-1),
                "Should throw exception for negative number of students");

        group.setNumberStudent(10);
        assertEquals(10, group.getNumberStudent(), "Number of students should be 10");
    }
}