package mark;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import student.Student;
import module.Module;


import static org.mockito.Mockito.*;

public class MarkTest {

    private Student student;
    private Module module;

    @Before
    public void setUp() {
        student = mock(Student.class);
        module = mock(Module.class);
        when(student.toString()).thenReturn("Student{id=1, fullName='John Doe', birthDate=2000-01-01, group=null}");
        when(module.toString()).thenReturn("Module{name='Math', code='MATH101'}");
    }

    // Test constructor with null student
    @Test
    public void testConstructorWithNullStudent() {
        Exception exception = null;
        try {
            new Mark(null, 85, module);
        } catch (IllegalArgumentException e) {
            exception = e;
        }
        assertNotNull("Expected IllegalArgumentException for null student", exception);
        assertEquals("Student cannot be null", exception.getMessage());
    }

    // Test constructor with null module
    @Test
    public void testConstructorWithNullModule() {
        Exception exception = null;
        try {
            new Mark(student, 85, null);
        } catch (IllegalArgumentException e) {
            exception = e;
        }
        assertNotNull("Expected IllegalArgumentException for null module", exception);
        assertEquals("Module cannot be null", exception.getMessage());
    }

    // Test constructor with null mark
    @Test
    public void testConstructorWithNullMark() {
        Exception exception = null;
        try {
            new Mark(student, null, module);
        } catch (IllegalArgumentException e) {
            exception = e;
        }
        assertNotNull("Expected IllegalArgumentException for null mark", exception);
        assertEquals("Mark cannot be null or negative", exception.getMessage());
    }

    // Test constructor with negative mark
    @Test
    public void testConstructorWithNegativeMark() {
        Exception exception = null;
        try {
            new Mark(student, -1, module);
        } catch (IllegalArgumentException e) {
            exception = e;
        }
        assertNotNull("Expected IllegalArgumentException for negative mark", exception);
        assertEquals("Mark cannot be null or negative", exception.getMessage());
    }

    // Test constructor with valid mark: 0
    @Test
    public void testConstructorWithMarkZero() {
        Mark mark = new Mark(student, 0, module);
        assertEquals(Integer.valueOf(0), mark.getMark());
    }

    // Test constructor with valid mark: 50
    @Test
    public void testConstructorWithMarkFifty() {
        Mark mark = new Mark(student, 50, module);
        assertEquals(Integer.valueOf(50), mark.getMark());
    }

    // Test constructor with valid mark: 100
    @Test
    public void testConstructorWithMarkHundred() {
        Mark mark = new Mark(student, 100, module);
        assertEquals(Integer.valueOf(100), mark.getMark());
    }

    // Test for the constructor with valid parameters
    @Test
    public void testConstructorValid() {
        Mark mark = new Mark(student, 85, module);
        assertNotNull(mark);
        assertEquals(student, mark.getStudent());
        assertEquals(Integer.valueOf(85), mark.getMark());
        assertEquals(module, mark.getModule());
    }

    // Test for the toString method
    @Test
    public void testToString() {
        Mark mark = new Mark(student, 85, module);
        String expected = "Mark{student=Student{id=1, fullName='John Doe', birthDate=2000-01-01, group=null}, mark=85, module=Module{name='Math', code='MATH101'}}";
        assertEquals(expected, mark.toString());
    }
}