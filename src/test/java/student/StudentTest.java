package student;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import group.Group;
import group.GroupName;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class StudentTest {

    @Test
    @DisplayName("Constructor with valid input values should create Student correctly")
    void testConstructor_ValidInput() {
        Group group = new Group(GroupName.MSIR);
        Student student = new Student(1, "John Doe", LocalDate.of(2000, 1, 1), group);

        assertEquals(1, student.getId());
        assertEquals("John Doe", student.getFullName());
        assertEquals(LocalDate.of(2000, 1, 1), student.getDateBirth());
        assertEquals(group, student.getGroup());
    }

    @Test
    @DisplayName("Constructor should throw NullPointerException for null parameters")
    void testConstructor_NullChecks() {
        Group group = new Group(GroupName.MSIR);
        LocalDate dob = LocalDate.of(2000, 1, 1);

        assertThrows(NullPointerException.class, () -> new Student(null, "John Doe", dob, group),
                "Should throw exception for null ID");
        assertThrows(NullPointerException.class, () -> new Student(1, null, dob, group),
                "Should throw exception for null full name");
        assertThrows(NullPointerException.class, () -> new Student(1, "John Doe", null, group),
                "Should throw exception for null date of birth");
        assertThrows(NullPointerException.class, () -> new Student(1, "John Doe", dob, null),
                "Should throw exception for null group");
    }

    @Test
    @DisplayName("Setters should update Student attributes with valid input values")
    void testSetters_ValidInput() {
        Group group1 = new Group(GroupName.MSIR);
        Group group2 = new Group(GroupName.MIAD);
        Student student = new Student(1, "John Doe", LocalDate.of(2000, 1, 1), group1);

        student.setFullName("Jane Doe");
        student.setDateBirth(LocalDate.of(1999, 2, 15));
        student.setGroup(group2);

        assertEquals("Jane Doe", student.getFullName());
        assertEquals(LocalDate.of(1999, 2, 15), student.getDateBirth());
        assertEquals(group2, student.getGroup());
    }

    @Test
    @DisplayName("Setters should throw NullPointerException for null parameters")
    void testSetters_NullChecks() {
        Group group = new Group(GroupName.MSIR);
        Student student = new Student(1, "John Doe", LocalDate.of(2000, 1, 1), group);

        assertThrows(NullPointerException.class, () -> student.setFullName(null),
                "Should throw exception for null full name");
        assertThrows(NullPointerException.class, () -> student.setDateBirth(null),
                "Should throw exception for null date of birth");
        assertThrows(NullPointerException.class, () -> student.setGroup(null), "Should throw exception for null group");
    }

    @Test
    @DisplayName("toString method should return correctly formatted Student details")
    void testToString() {
        Group group = new Group(GroupName.MSIR);
        Student student = new Student(1, "John Doe", LocalDate.of(2000, 1, 1), group);

        String studentString = student.toString();

        assertTrue(studentString.contains("id=1"), "Student string should contain ID");
        assertTrue(studentString.contains("fullName='John Doe'"), "Student string should contain full name");
        assertTrue(studentString.contains("dateBirth=2000-01-01"), "Student string should contain date of birth");
        assertTrue(studentString.contains("group=Group{reference=MSIR"),
                "Student string should contain group reference");
    }

}