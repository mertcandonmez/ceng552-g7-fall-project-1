package group;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import student.Student;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for GroupService.
 * 
 * Test Strategy:
 * - Null Checks: Verify that methods handle null values properly.
 * - Boundary Value Analysis: Test the boundaries for group references and
 * student lists.
 * - Decision Tree: Test the decision logic for finding and saving groups.
 * - Equivalence Partitioning: Test typical values for group references and
 * student lists.
 */
public class GroupServiceTest {

    private GroupService groupService;

    @BeforeEach
    public void setUp() {
        groupService = new GroupService();
    }

    // Test saving a group with a unique reference (Equivalence Partitioning)
    @Test
    @DisplayName("Test saving a group with a unique reference")
    public void testSaveGroupUniqueReference() {
        groupService.saveGroup(GroupName.MSIR);
        Group group = groupService.findByReference(GroupName.MSIR.toString());
        assertNotNull(group);
        assertEquals(GroupName.MSIR, group.getReference());
    }

    // Test saving a group with a duplicate reference (Boundary Value Analysis)
    @Test
    @DisplayName("Test saving a group with a duplicate reference")
    public void testSaveGroupDuplicateReference() {
        groupService.saveGroup(GroupName.MSIR);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            groupService.saveGroup(GroupName.MSIR);
        });
        assertEquals("Group with the same reference already exists", exception.getMessage());
    }

    // Test saving a group with null reference
    @Test
    @DisplayName("Test saving a group with null reference")
    public void testSaveGroupNullReference() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            groupService.saveGroup(null);
        });
        assertEquals("Group reference cannot be null", exception.getMessage());
    }

    // Test finding a group by reference (Decision Tree)
    @Test
    @DisplayName("Test finding a group by reference")
    public void testFindByReference() {
        groupService.saveGroup(GroupName.MSIR);
        Group group = groupService.findByReference(GroupName.MSIR.toString());
        assertNotNull(group);
        assertEquals(GroupName.MSIR, group.getReference());
    }

    // Test finding a group with null reference
    @Test
    @DisplayName("Test finding a group with null reference")
    public void testFindByReferenceNull() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            groupService.findByReference(null);
        });
        assertEquals("Group reference cannot be null", exception.getMessage());
    }

    // Test finding a non-existent group (Decision Tree)
    @Test
    @DisplayName("Test finding a non-existent group by reference")
    public void testFindByReferenceNonExistent() {
        Group group = groupService.findByReference("NON_EXISTENT");
        assertNull(group);
    }

    // Test updating the number of students with a valid list (Equivalence
    // Partitioning)
    @Test
    @DisplayName("Test updating the number of students with a valid list")
    public void testUpdateNumberOfStudentValidList() {
        groupService.saveGroup(GroupName.MSIR);
        groupService.saveGroup(GroupName.MIAD);

        Group groupMSIR = groupService.findByReference(GroupName.MSIR.toString());
        Group groupMIAD = groupService.findByReference(GroupName.MIAD.toString());
        assertNotNull(groupMSIR);
        assertNotNull(groupMIAD);

        Student student1 = new Student(1, "John Doe", LocalDate.of(2000, 1, 1), groupMSIR);
        Student student2 = new Student(2, "Jane Smith", LocalDate.of(1999, 5, 15), groupMIAD);
        Student student3 = new Student(3, "Bob Brown", LocalDate.of(1998, 3, 10), groupMSIR);

        List<Student> students = new ArrayList<>();
        students.add(student1);
        students.add(student2);
        students.add(student3);

        groupService.updateNumberOfStudent(students);

        groupMSIR = groupService.findByReference(GroupName.MSIR.toString());
        groupMIAD = groupService.findByReference(GroupName.MIAD.toString());

        assertEquals(2, groupMSIR.getNumberStudent());
        assertEquals(1, groupMIAD.getNumberStudent());
    }

    // Test updating the number of students with a null list (Boundary Value
    // Analysis)
    @Test
    @DisplayName("Test updating the number of students with a null list")
    public void testUpdateNumberOfStudentNullList() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            groupService.updateNumberOfStudent(null);
        });
        assertEquals("Student list cannot be null", exception.getMessage());
    }

    // Test getting all groups
    @Test
    @DisplayName("Test getting all groups")
    public void testAllGroups() {
        groupService.saveGroup(GroupName.MSIR);
        groupService.saveGroup(GroupName.MIAD);
        List<Group> groups = groupService.allGroups();
        assertEquals(2, groups.size());
    }

    // Parameterized test for saving groups with null and valid references
    @ParameterizedTest
    @MethodSource("provideGroupReferences")
    @DisplayName("Test saving groups with various references")
    public void testSaveGroupVariousReferences(GroupName reference, String expectedMessage) {
        if (expectedMessage == null) {
            assertDoesNotThrow(() -> groupService.saveGroup(reference));
            Group group = groupService.findByReference(reference.toString());
            assertNotNull(group);
            assertEquals(reference, group.getReference());
        } else {
            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                groupService.saveGroup(reference);
            });
            assertEquals(expectedMessage, exception.getMessage());
        }
    }

    // Method source providing group references
    private static Stream<Arguments> provideGroupReferences() {
        return Stream.of(
                Arguments.of(GroupName.MSIR, null),
                Arguments.of(null, "Group reference cannot be null"));
    }

    // Parameterized test for finding groups by various references
    @ParameterizedTest
    @MethodSource("provideFindGroupReferences")
    @DisplayName("Test finding groups with various references")
    public void testFindByReferenceVariousReferences(String reference, String expectedMessage, boolean shouldExist) {
        groupService.saveGroup(GroupName.MSIR);
        if (expectedMessage == null) {
            Group group = groupService.findByReference(reference);
            if (shouldExist) {
                assertNotNull(group, "Expected group to exist");
                assertEquals(reference.toUpperCase(), group.getReference().toString().toUpperCase());
            } else {
                assertNull(group, "Expected group to be null for non-existent reference");
            }
        } else {
            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                groupService.findByReference(reference);
            });
            assertEquals(expectedMessage, exception.getMessage());
        }
    }

    // Method source providing references for findByReference
    private static Stream<Arguments> provideFindGroupReferences() {
        return Stream.of(
                Arguments.of("MSIR", null, true), // Group exists
                Arguments.of(null, "Group reference cannot be null", false), // Invalid reference
                Arguments.of("NON_EXISTENT", null, false) // Group does not exist
        );
    }

    @ParameterizedTest
    @MethodSource("provideStudentLists")
    @DisplayName("Test updating number of students with various student lists")
    public void testUpdateNumberOfStudentWithVariousLists(List<Student> students, String expectedMessage) {
        groupService.saveGroup(GroupName.MSIR);

        if (expectedMessage == null) {
            assertDoesNotThrow(() -> groupService.updateNumberOfStudent(students));
            Group group = groupService.findByReference(GroupName.MSIR.toString());
            int expectedCount = (students == null) ? 0
                    : (int) students.stream()
                            .filter(s -> s.getGroup() != null && s.getGroup().getReference() == GroupName.MSIR)
                            .count();
            assertEquals(expectedCount, group.getNumberStudent());
        } else {
            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                groupService.updateNumberOfStudent(students);
            });
            assertEquals(expectedMessage, exception.getMessage());
        }
    }

    // Update the method source to include the null case
    private static Stream<Arguments> provideStudentLists() {
        Student studentWithGroup = new Student(1, "John Doe", LocalDate.of(2000, 1, 1), new Group(GroupName.MSIR));
        // Student studentWithoutGroup = new Student(2, "Jane Smith", LocalDate.of(1999,
        // 5, 15), null);

        List<Student> validList = new ArrayList<>();
        validList.add(studentWithGroup);
        // validList.add(studentWithoutGroup);

        List<Student> emptyList = new ArrayList<>();

        return Stream.of(
                Arguments.of(validList, null), // Valid list of students
                Arguments.of(emptyList, null), // Empty list of students
                Arguments.of(null, "Student list cannot be null") // Null list should trigger exception
        );
    }
}