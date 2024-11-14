package group;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

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

	@Before
	public void setUp() {
		group = new Group(GroupName.MSIR);
	}

	// Test constructor with valid reference
	@Test
	public void testConstructorValid() {
		assertEquals(GroupName.MSIR, group.getReference());
		assertEquals(Integer.valueOf(0), group.getNumberStudent());
	}

	// Test constructor with null reference
	@Test
	public void testConstructorNullReference() {
		Exception exception = null;
		try {
			new Group(null);
		} catch (IllegalArgumentException e) {
			exception = e;
		}
		assertNotNull("Expected IllegalArgumentException to be thrown", exception);
		assertEquals("Group reference cannot be null", exception.getMessage());
	}

	// Test setting valid number of students: 0
	@Test
	public void testSetNumberStudentZero() {
		group.setNumberStudent(0);
		assertEquals(Integer.valueOf(0), group.getNumberStudent());
	}

	// Test setting valid number of students: 1
	@Test
	public void testSetNumberStudentOne() {
		group.setNumberStudent(1);
		assertEquals(Integer.valueOf(1), group.getNumberStudent());
	}

	// Test setting valid number of students: 10
	@Test
	public void testSetNumberStudentTen() {
		group.setNumberStudent(10);
		assertEquals(Integer.valueOf(10), group.getNumberStudent());
	}

	// Test setting valid number of students: 100
	@Test
	public void testSetNumberStudentHundred() {
		group.setNumberStudent(100);
		assertEquals(Integer.valueOf(100), group.getNumberStudent());
	}

	// Test setting invalid (negative) number of students: -1
	@Test
	public void testSetNumberStudentNegativeOne() {
		Exception exception = null;
		try {
			group.setNumberStudent(-1);
		} catch (IllegalArgumentException e) {
			exception = e;
		}
		assertNotNull("Expected IllegalArgumentException to be thrown", exception);
		assertEquals("Number of students cannot be negative", exception.getMessage());
	}

	// Test setting invalid (negative) number of students: -10
	@Test
	public void testSetNumberStudentNegativeTen() {
		Exception exception = null;
		try {
			group.setNumberStudent(-10);
		} catch (IllegalArgumentException e) {
			exception = e;
		}
		assertNotNull("Expected IllegalArgumentException to be thrown", exception);
		assertEquals("Number of students cannot be negative", exception.getMessage());
	}

	// Test setting invalid (negative) number of students: -100
	@Test
	public void testSetNumberStudentNegativeHundred() {
		Exception exception = null;
		try {
			group.setNumberStudent(-100);
		} catch (IllegalArgumentException e) {
			exception = e;
		}
		assertNotNull("Expected IllegalArgumentException to be thrown", exception);
		assertEquals("Number of students cannot be negative", exception.getMessage());
	}

	// Test setting null as the number of students
	@Test
	public void testSetNumberStudentNull() {
		Exception exception = null;
		try {
			group.setNumberStudent(null);
		} catch (IllegalArgumentException e) {
			exception = e;
		}
		assertNotNull("Expected IllegalArgumentException to be thrown", exception);
		assertEquals("Number of students cannot be null", exception.getMessage());
	}

	// Test toString method
	@Test
	public void testToString() {
		String expected = "Group{reference=MSIR}";
		assertEquals(expected, group.toString());
	}

	// Test showGroup method
	@Test
	public void testShowGroup() {
		String expected = "Group{reference=MSIR , number student=0}";
		assertEquals(expected, group.showGroup());
	}
}