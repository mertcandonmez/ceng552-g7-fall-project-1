package group;

import org.junit.Before;
import org.junit.Test;
import student.Student;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

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

	@Before
	public void setUp() {
		groupService = new GroupService();
	}

	// Test saving a group with a unique reference
	@Test
	public void testSaveGroupUniqueReference() {
		groupService.saveGroup(GroupName.MSIR);
		Group group = groupService.findByReference(GroupName.MSIR.toString());
		assertNotNull(group);
		assertEquals(GroupName.MSIR, group.getReference());
	}

	// Test saving a group with a duplicate reference
	@Test
	public void testSaveGroupDuplicateReference() {
		groupService.saveGroup(GroupName.MSIR);
		Exception exception = null;
		try {
			groupService.saveGroup(GroupName.MSIR);
		} catch (IllegalArgumentException e) {
			exception = e;
		}
		assertNotNull("Expected IllegalArgumentException to be thrown", exception);
		assertEquals("Group with the same reference already exists", exception.getMessage());
	}

	// Test saving a group with null reference
	@Test
	public void testSaveGroupNullReference() {
		Exception exception = null;
		try {
			groupService.saveGroup(null);
		} catch (IllegalArgumentException e) {
			exception = e;
		}
		assertNotNull("Expected IllegalArgumentException to be thrown", exception);
		assertEquals("Group reference cannot be null", exception.getMessage());
	}

	// Test finding a group by reference
	@Test
	public void testFindByReference() {
		groupService.saveGroup(GroupName.MSIR);
		Group group = groupService.findByReference(GroupName.MSIR.toString());
		assertNotNull(group);
		assertEquals(GroupName.MSIR, group.getReference());
	}

	// Test finding a non-existent group
	@Test
	public void testFindByReferenceNonExistent() {
		Group group = groupService.findByReference("NON_EXISTENT");
		assertNull(group);
	}

	// Test updating the number of students with a valid list
	@Test
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

		assertEquals(Integer.valueOf(2), groupMSIR.getNumberStudent());
		assertEquals(Integer.valueOf(1), groupMIAD.getNumberStudent());
	}

	// Test updating the number of students with a null list
	@Test
	public void testUpdateNumberOfStudentNullList() {
		Exception exception = null;
		try {
			groupService.updateNumberOfStudent(null);
		} catch (IllegalArgumentException e) {
			exception = e;
		}
		assertNotNull("Expected IllegalArgumentException to be thrown", exception);
		assertEquals("Student list cannot be null", exception.getMessage());
	}

	// Test updating the number of students with an empty list
	@Test
	public void testUpdateNumberOfStudentEmptyList() {
		groupService.saveGroup(GroupName.MSIR);

		List<Student> students = new ArrayList<>();
		groupService.updateNumberOfStudent(students);

		Group group = groupService.findByReference(GroupName.MSIR.toString());
		assertEquals(Integer.valueOf(0), group.getNumberStudent());
	}

	// Test getting all groups
	@Test
	public void testAllGroups() {
		groupService.saveGroup(GroupName.MSIR);
		groupService.saveGroup(GroupName.MIAD);
		List<Group> groups = groupService.allGroups();
		assertEquals(2, groups.size());
	}

	// Test saving a group with various references
	@Test
	public void testSaveGroupWithValidReference() {
		groupService.saveGroup(GroupName.MSIR);
		Group group = groupService.findByReference(GroupName.MSIR.toString());
		assertNotNull(group);
		assertEquals(GroupName.MSIR, group.getReference());
	}

	@Test
	public void testSaveGroupWithNullReference() {
		Exception exception = null;
		try {
			groupService.saveGroup(null);
		} catch (IllegalArgumentException e) {
			exception = e;
		}
		assertNotNull(exception);
		assertEquals("Group reference cannot be null", exception.getMessage());
	}

	// Test finding groups with various references
	@Test
	public void testFindByReferenceExists() {
		groupService.saveGroup(GroupName.MSIR);
		Group group = groupService.findByReference("MSIR");
		assertNotNull(group);
		assertEquals(GroupName.MSIR, group.getReference());
	}

	@Test
	public void testFindByReferenceNonExists() {
		groupService.saveGroup(GroupName.MSIR);
		Group group = groupService.findByReference("NON_EXISTENT");
		assertNull(group);
	}

	@Test
	public void testFindByReferenceNull() {
		Exception exception = null;
		try {
			groupService.findByReference(null);
		} catch (IllegalArgumentException e) {
			exception = e;
		}
		assertNotNull(exception);
		assertEquals("Group reference cannot be null", exception.getMessage());
	}

	// Test updating number of students with various student lists
	@Test
	public void testUpdateNumberOfStudentValidStudents() {
		groupService.saveGroup(GroupName.MSIR);

		Student student1 = new Student(1, "John Doe", LocalDate.of(2000, 1, 1), new Group(GroupName.MSIR));

		List<Student> students = new ArrayList<>();
		students.add(student1);

		groupService.updateNumberOfStudent(students);

		Group group = groupService.findByReference(GroupName.MSIR.toString());
		assertEquals(Integer.valueOf(1), group.getNumberStudent());
	}

	@Test
	public void testUpdateNumberOfStudentNullStudents() {
		Exception exception = null;
		try {
			groupService.updateNumberOfStudent(null);
		} catch (IllegalArgumentException e) {
			exception = e;
		}
		assertNotNull(exception);
		assertEquals("Student list cannot be null", exception.getMessage());
	}
}