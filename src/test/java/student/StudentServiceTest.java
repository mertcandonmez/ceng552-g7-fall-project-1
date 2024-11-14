package student;

import group.Group;
import group.GroupName;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

public class StudentServiceTest {

    private StudentService studentService;

    @Before
    public void setUp() {
        studentService = new StudentService();
        studentService.saveStudent(1, "John Doe", LocalDate.of(2000, 1, 1), new Group(GroupName.MSIR));
    }

    // Boundary Value Tests for the `saveStudent` method parameters
    @Test
    public void testSaveStudent_BoundaryId() {
        // Minimum boundary for ID
        studentService.saveStudent(1, "Min Id", LocalDate.of(2000, 1, 1), new Group(GroupName.MSIR));
        assertEquals("ID should match minimum boundary value", Integer.valueOf(1), studentService.findById(1).getId());

        // Typical value just above the minimum boundary
        studentService.saveStudent(2, "Next Min Id", LocalDate.of(2000, 1, 1), new Group(GroupName.MSIR));
        assertEquals("ID should match just above minimum boundary", Integer.valueOf(2),
                studentService.findById(2).getId());

        // High boundary example (assuming Integer.MAX_VALUE as upper limit)
        studentService.saveStudent(Integer.MAX_VALUE, "Max Id", LocalDate.of(2000, 1, 1), new Group(GroupName.MSIR));
        assertEquals("ID should match maximum boundary value", Integer.valueOf(Integer.MAX_VALUE),
                studentService.findById(Integer.MAX_VALUE).getId());
    }

    @Test
    public void testSaveStudent_BoundaryFullName() {
        // Boundary for fullName length, assuming non-empty is required
        studentService.saveStudent(3, "A", LocalDate.of(2000, 1, 1), new Group(GroupName.MSIR));
        assertEquals("Full name should match single character boundary", "A", studentService.findById(3).getFullName());

        // Typical longer name at upper boundary (assuming 255 characters as a limit)
        String longName = "A".repeat(255);
        studentService.saveStudent(4, longName, LocalDate.of(2000, 1, 1), new Group(GroupName.MSIR));
        assertEquals("Full name should match long boundary value", longName, studentService.findById(4).getFullName());
    }

    @Test
    public void testSaveStudent_BoundaryDateOfBirth() {
        LocalDate minDate = LocalDate.of(1900, 1, 1);
        studentService.saveStudent(5, "Old Student", minDate, new Group(GroupName.MSIR));
        assertEquals("Date of birth should match minimum boundary date", minDate,
                studentService.findById(5).getDateBirth());

        LocalDate currentDate = LocalDate.now();
        studentService.saveStudent(6, "New Student", currentDate, new Group(GroupName.MSIR));
        assertEquals("Date of birth should match upper boundary date", currentDate,
                studentService.findById(6).getDateBirth());
    }

    @Test
    public void testSaveStudent_BoundaryGroup() {
        Group groupMin = new Group(GroupName.MSIR);
        studentService.saveStudent(7, "Student Min Group", LocalDate.of(2000, 1, 1), groupMin);
        assertEquals("Group should match minimum valid boundary", groupMin, studentService.findById(7).getGroup());

        Group groupMax = new Group(GroupName.MIAD);
        studentService.saveStudent(8, "Student Max Group", LocalDate.of(2000, 1, 1), groupMax);
        assertEquals("Group should match maximum valid boundary", groupMax, studentService.findById(8).getGroup());
    }

    @Test
    public void testSaveStudent_NullChecks() {
        try {
            studentService.saveStudent(null, "John Doe", LocalDate.of(2000, 1, 1), new Group(GroupName.MSIR));
            fail("Should throw exception for null ID");
        } catch (NullPointerException e) {
            // Expected exception
        }

        try {
            studentService.saveStudent(1, null, LocalDate.of(2000, 1, 1), new Group(GroupName.MSIR));
            fail("Should throw exception for null full name");
        } catch (NullPointerException e) {
            // Expected exception
        }

        try {
            studentService.saveStudent(1, "John Doe", null, new Group(GroupName.MSIR));
            fail("Should throw exception for null date of birth");
        } catch (NullPointerException e) {
            // Expected exception
        }

        try {
            studentService.saveStudent(1, "John Doe", LocalDate.of(2000, 1, 1), null);
            fail("Should throw exception for null group");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    @Test
    public void testFindById_ValidId() {
        Student student = studentService.findById(1);
        assertNotNull("Student should be found", student);
        assertEquals("Student ID should match the requested ID", 1, student.getId().intValue());
        assertEquals("Student full name should match", "John Doe", student.getFullName());
    }

    @Test
    public void testFindById_InvalidId() {
        try {
            studentService.findById(99);
            fail("Should throw exception for non-existing ID");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    @Test
    public void testFindById_NullId() {
        try {
            studentService.findById(null);
            fail("Should throw exception for null ID");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    @Test
    public void testUpdateStudent_ValidId() {
        studentService.updateStudent(1, "Jane Doe", LocalDate.of(1999, 5, 10), new Group(GroupName.MIAD));
        Student updatedStudent = studentService.findById(1);
        assertEquals("Student's name should be updated", "Jane Doe", updatedStudent.getFullName());
        assertEquals("Student's date of birth should be updated", LocalDate.of(1999, 5, 10),
                updatedStudent.getDateBirth());
        assertEquals("Student's group should be updated", GroupName.MIAD, updatedStudent.getGroup().getReference());
    }

    @Test
    public void testUpdateStudent_NullValues() {
        Group group = new Group(GroupName.MSIR);
        LocalDate dob = LocalDate.of(2000, 1, 1);

        try {
            studentService.updateStudent(null, "John Doe", dob, group);
            fail("Should throw exception for null ID");
        } catch (NullPointerException e) {
            // Expected exception
        }

        try {
            studentService.updateStudent(1, null, dob, group);
            fail("Should throw exception for null full name");
        } catch (NullPointerException e) {
            // Expected exception
        }

        try {
            studentService.updateStudent(1, "John Doe", null, group);
            fail("Should throw exception for null date of birth");
        } catch (NullPointerException e) {
            // Expected exception
        }

        try {
            studentService.updateStudent(1, "John Doe", dob, null);
            fail("Should throw exception for null group");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    @Test
    public void testUpdateStudent_InvalidId() {
        Group group = new Group(GroupName.MIAD);
        LocalDate dob = LocalDate.of(1999, 5, 10);

        try {
            studentService.updateStudent(99, "Jane Doe", dob, group);
            fail("Should throw exception for non-existing ID");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    @Test
    public void testDeleteStudent_DecisionTree() {
        studentService.saveStudent(2, "Jane Doe", LocalDate.of(1999, 5, 10), new Group(GroupName.MIAD));
        studentService.deleteStudent(1);
        assertEquals("List should contain 1 student after deletion", 1, studentService.allStudents().size());
        assertEquals("Remaining student should have ID 2", Integer.valueOf(2),
                studentService.allStudents().get(0).getId());

        studentService.deleteStudent(99); // Non-existing ID
        assertEquals("List should remain the same if student doesn't exist", 1, studentService.allStudents().size());
    }

    @Test
    public void testAddStudent_EquivalencePartitioning() {
        studentService.saveStudent(2, "Alice", LocalDate.of(1998, 2, 2), new Group(GroupName.MSIA));
        studentService.saveStudent(3, "Bob", LocalDate.of(2001, 3, 15), new Group(GroupName.MSIR));

        List<Student> students = studentService.allStudents();
        assertEquals("List should contain 3 students in typical case", 3, students.size());
    }

    @Test
    public void testGroupNumberStudent_BoundaryValues() {
        Group group = new Group(GroupName.MSIR);

        group.setNumberStudent(0);
        assertEquals("Number of students should be 0 at min boundary", Integer.valueOf(0), group.getNumberStudent());

        try {
            group.setNumberStudent(-1);
            fail("Should throw exception for negative number of students");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }

        group.setNumberStudent(10);
        assertEquals("Number of students should be 10", Integer.valueOf(10), group.getNumberStudent());
    }
}