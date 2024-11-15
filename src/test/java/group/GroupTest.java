package group;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test class for Group.
 *
 */
public class GroupTest {

	private Group group;

	@Before
	public void setUp() {
		// Initialize group with a valid reference before each test
		group = new Group(GroupName.MSIR);
	}

	/**
	 * Test the constructor with a valid GroupName reference.
	 * Equivalence Class: Valid non-null references.
	 */
	@Test
	public void testConstructorValidReference() {
		// Verify that the group is initialized correctly
		assertEquals("Group reference should be MSIR", GroupName.MSIR, group.getReference());
		assertEquals("Number of students should be initialized to 0", Integer.valueOf(0), group.getNumberStudent());
	}

	/**
	 * Test the constructor with a null GroupName reference.
	 * Equivalence Class: Null references.
	 * Expected to throw IllegalArgumentException.
	 */
	@Test
	public void testConstructorNullReference() {
		Exception exception = null;
		try {
			new Group(null);
		} catch (IllegalArgumentException e) {
			exception = e;
		}
		// Verify that an exception is thrown and contains the correct message
		assertNotNull("Expected IllegalArgumentException to be thrown", exception);
		assertEquals("Exception message should be 'Group reference cannot be null'",
				"Group reference cannot be null", exception.getMessage());
	}

	/**
	 * Test getNumberStudent method for initial value.
	 * The number of students should be initialized to 0.
	 */
	@Test
	public void testGetNumberStudentInitialValue() {
		// Verify that the initial number of students is 0
		assertEquals("Initial number of students should be 0", Integer.valueOf(0), group.getNumberStudent());
	}

	/**
	 * Test setNumberStudent with valid values.
	 * Boundary Values: 0, 1, 50, 100
	 * Equivalence Class: Valid non-negative integers.
	 */
	@Test
	public void testSetNumberStudentValidValues() {
		// Test setting number of students to 0
		group.setNumberStudent(0);
		assertEquals("Number of students should be 0", Integer.valueOf(0), group.getNumberStudent());

		// Test setting number of students to 1
		group.setNumberStudent(1);
		assertEquals("Number of students should be 1", Integer.valueOf(1), group.getNumberStudent());

		// Test setting number of students to a typical value (e.g., 50)
		group.setNumberStudent(50);
		assertEquals("Number of students should be 50", Integer.valueOf(50), group.getNumberStudent());

		// Test setting number of students to a large number (boundary value)
		group.setNumberStudent(100);
		assertEquals("Number of students should be 100", Integer.valueOf(100), group.getNumberStudent());
	}

	/**
	 * Test setNumberStudent with invalid negative values.
	 * Boundary Values: -1, -100
	 * Equivalence Class: Negative integers.
	 * Expected to throw IllegalArgumentException.
	 */
	@Test
	public void testSetNumberStudentNegativeValues() {
		// Test setting number of students to -1
		Exception exception = null;
		try {
			group.setNumberStudent(-1);
		} catch (IllegalArgumentException e) {
			exception = e;
		}
		assertNotNull("Expected IllegalArgumentException for negative value", exception);
		assertEquals("Exception message should be 'Number of students cannot be negative'",
				"Number of students cannot be negative", exception.getMessage());

		// Test setting number of students to a larger negative value
		exception = null;
		try {
			group.setNumberStudent(-100);
		} catch (IllegalArgumentException e) {
			exception = e;
		}
		assertNotNull("Expected IllegalArgumentException for negative value", exception);
		assertEquals("Exception message should be 'Number of students cannot be negative'",
				"Number of students cannot be negative", exception.getMessage());
	}

	/**
	 * Test setNumberStudent with null value.
	 * Expected to throw IllegalArgumentException.
	 */
	@Test
	public void testSetNumberStudentNullValue() {
		Exception exception = null;
		try {
			group.setNumberStudent(null);
		} catch (IllegalArgumentException e) {
			exception = e;
		}
		assertNotNull("Expected IllegalArgumentException for null value", exception);
		assertEquals("Exception message should be 'Number of students cannot be null'",
				"Number of students cannot be null", exception.getMessage());
	}

	/**
	 * Test the toString method.
	 * Ensures that the string representation matches the expected format.
	 */
	@Test
	public void testToString() {
		String expected = "Group{reference=MSIR}";
		assertEquals("toString should return correct format", expected, group.toString());
	}

	/**
	 * Test the showGroup method.
	 * Ensures that the detailed string representation is correct.
	 */
	@Test
	public void testShowGroup() {
		group.setNumberStudent(25);
		String expected = "Group{reference=MSIR , number student=25}";
		assertEquals("showGroup should return correct format", expected, group.showGroup());
	}

	/**
	 * Test setting numberStudent after multiple valid set operations.
	 * Decision Table Testing: Verify state after several updates.
	 */
	@Test
	public void testSetNumberStudentMultipleUpdates() {
		// Set number of students to 10
		group.setNumberStudent(10);
		assertEquals("Number of students should be 10", Integer.valueOf(10), group.getNumberStudent());

		// Update number of students to 20
		group.setNumberStudent(20);
		assertEquals("Number of students should be updated to 20", Integer.valueOf(20), group.getNumberStudent());

		// Update number of students back to 0
		group.setNumberStudent(0);
		assertEquals("Number of students should be updated to 0", Integer.valueOf(0), group.getNumberStudent());
	}

	/**
	 * Test the behavior of setNumberStudent with maximum integer value.
	 * Boundary Value Analysis: Integer.MAX_VALUE
	 */
	@Test
	public void testSetNumberStudentMaxIntegerValue() {
		group.setNumberStudent(Integer.MAX_VALUE);
		assertEquals("Number of students should be Integer.MAX_VALUE",
				Integer.valueOf(Integer.MAX_VALUE), group.getNumberStudent());
	}

	/**
	 * Test that the reference of the group remains unchanged.
	 * Ensures that the reference is final and cannot be modified.
	 */
	@Test
	public void testGroupReferenceImmutability() {
		// Attempting to modify the reference should not be possible,
		// but we can check that the reference remains the same after operations
		assertEquals("Group reference should remain MSIR", GroupName.MSIR, group.getReference());
		// Create a new group and check its reference
		Group newGroup = new Group(GroupName.MIAD);
		assertEquals("New group's reference should be MIAD", GroupName.MIAD, newGroup.getReference());
	}

	/**
	 * Test creating multiple Group instances with different references.
	 * Ensures that each instance maintains its own state.
	 */
	@Test
	public void testMultipleGroupInstances() {
		Group group1 = new Group(GroupName.MSIR);
		Group group2 = new Group(GroupName.MIAD);

		group1.setNumberStudent(15);
		group2.setNumberStudent(10);

		assertEquals("Group1 should have reference MSIR", GroupName.MSIR, group1.getReference());
		assertEquals("Group1 should have 15 students", Integer.valueOf(15), group1.getNumberStudent());

		assertEquals("Group2 should have reference MIAD", GroupName.MIAD, group2.getReference());
		assertEquals("Group2 should have 10 students", Integer.valueOf(10), group2.getNumberStudent());
	}

	/**
	 * Test edge cases for integer overflow.
	 * Although unlikely, test behavior with Integer.MAX_VALUE + 1.
	 */
	@Test
	public void testSetNumberStudentOverflow() {
		Exception exception = null;
		try {
			group.setNumberStudent(Integer.MAX_VALUE + 1);
		} catch (IllegalArgumentException e) {
			exception = e;
		} catch (ArithmeticException e) {
			// Catch potential overflow exception
			exception = e;
		}
		// Depending on how the method handles overflow, adjust the test
		// In Java, Integer.MAX_VALUE + 1 results in Integer.MIN_VALUE due to overflow
		// Thus, setting numberStudent to Integer.MIN_VALUE should throw an exception
		assertNotNull("Expected exception due to overflow", exception);
		assertTrue("Exception should be IllegalArgumentException or ArithmeticException",
				exception instanceof IllegalArgumentException || exception instanceof ArithmeticException);
	}
}