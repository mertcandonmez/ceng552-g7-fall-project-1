package student;

import org.junit.jupiter.api.Test;
import group.Group;
import group.GroupName;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class StudentTest {

    @Test
    void testConstructor_ValidInput() {
        Group group = new Group(GroupName.MSIR);
        Student student = new Student(1, "John Doe", LocalDate.of(2000, 1, 1), group);

        assertEquals(1, student.getId());
        assertEquals("John Doe", student.getFullName());
        assertEquals(LocalDate.of(2000, 1, 1), student.getDateBirth());
        assertEquals(group, student.getGroup());
    }

    @Test
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
    void testToString() {
        Group group = new Group(GroupName.MSIR);
        Student student = new Student(1, "John Doe", LocalDate.of(2000, 1, 1), group);

        String expected = "Student{id=1, fullName='John Doe', dateBirth=2000-01-01, group=Group{reference=MSIR , number student=0}}";
        assertEquals(expected, student.toString(), "toString output should match expected format");
    }
}