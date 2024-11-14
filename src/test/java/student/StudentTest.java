package student;

import org.junit.Test;
import static org.junit.Assert.*;
import group.Group;
import group.GroupName;

import java.time.LocalDate;

public class StudentTest {

        @Test
        public void testConstructor_ValidInput() {
                Group group = new Group(GroupName.MSIR);
                Student student = new Student(1, "John Doe", LocalDate.of(2000, 1, 1), group);

                assertEquals("ID should match", 1, student.getId().intValue());
                assertEquals("Full name should match", "John Doe", student.getFullName());
                assertEquals("Date of birth should match", LocalDate.of(2000, 1, 1), student.getDateBirth());
                assertEquals("Group should match", group, student.getGroup());
        }

        @Test
        public void testConstructor_NullChecks() {
                Group group = new Group(GroupName.MSIR);
                LocalDate dob = LocalDate.of(2000, 1, 1);

                try {
                        new Student(null, "John Doe", dob, group);
                        fail("Should throw exception for null ID");
                } catch (NullPointerException e) {
                        // Expected exception
                }

                try {
                        new Student(1, null, dob, group);
                        fail("Should throw exception for null full name");
                } catch (NullPointerException e) {
                        // Expected exception
                }

                try {
                        new Student(1, "John Doe", null, group);
                        fail("Should throw exception for null date of birth");
                } catch (NullPointerException e) {
                        // Expected exception
                }

                try {
                        new Student(1, "John Doe", dob, null);
                        fail("Should throw exception for null group");
                } catch (NullPointerException e) {
                        // Expected exception
                }
        }

        @Test
        public void testSetters_ValidInput() {
                Group group1 = new Group(GroupName.MSIR);
                Group group2 = new Group(GroupName.MIAD);
                Student student = new Student(1, "John Doe", LocalDate.of(2000, 1, 1), group1);

                student.setFullName("Jane Doe");
                student.setDateBirth(LocalDate.of(1999, 2, 15));
                student.setGroup(group2);

                assertEquals("Full name should be updated", "Jane Doe", student.getFullName());
                assertEquals("Date of birth should be updated", LocalDate.of(1999, 2, 15), student.getDateBirth());
                assertEquals("Group should be updated", group2, student.getGroup());
        }

        @Test
        public void testSetters_NullChecks() {
                Group group = new Group(GroupName.MSIR);
                Student student = new Student(1, "John Doe", LocalDate.of(2000, 1, 1), group);

                try {
                        student.setFullName(null);
                        fail("Should throw exception for null full name");
                } catch (NullPointerException e) {
                        // Expected exception
                }

                try {
                        student.setDateBirth(null);
                        fail("Should throw exception for null date of birth");
                } catch (NullPointerException e) {
                        // Expected exception
                }

                try {
                        student.setGroup(null);
                        fail("Should throw exception for null group");
                } catch (NullPointerException e) {
                        // Expected exception
                }
        }

        @Test
        public void testToString() {
                Group group = new Group(GroupName.MSIR);
                Student student = new Student(1, "John Doe", LocalDate.of(2000, 1, 1), group);

                String studentString = student.toString();

                assertTrue("Student string should contain ID", studentString.contains("id=1"));
                assertTrue("Student string should contain full name", studentString.contains("fullName='John Doe'"));
                assertTrue("Student string should contain date of birth",
                                studentString.contains("dateBirth=2000-01-01"));
                assertTrue("Student string should contain group reference",
                                studentString.contains("group=Group{reference=MSIR"));
        }

}