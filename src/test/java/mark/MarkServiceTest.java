package mark;

import mark.exception.MarkException;
import org.junit.Before;
import org.junit.Test;
import student.Student;
import module.Module;
import module.ModuleName;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class MarkServiceTest {

    private MarkService markService;
    private Student student;
    private Module module;

    @Before
    public void setUp() {
        markService = new MarkService();
        student = mock(Student.class);
        module = mock(Module.class);

        when(student.getId()).thenReturn(1);
        when(student.getFullName()).thenReturn("John Doe");
        when(student.getDateBirth()).thenReturn(LocalDate.of(2000, 1, 1));
        when(module.getName()).thenReturn("Math");
        when(module.getReference()).thenReturn(ModuleName.BDA);
    }

    // Test creating a mark with valid values
    @Test
    public void testCreateMarkValid5() throws MarkException {
        markService.createMark(student, 5, module);
        List<Mark> marks = markService.allMarks();
        assertEquals(5, marks.get(marks.size() - 1).getMark().intValue());
    }

    @Test
    public void testCreateMarkValid10() throws MarkException {
        markService.createMark(student, 10, module);
        List<Mark> marks = markService.allMarks();
        assertEquals(10, marks.get(marks.size() - 1).getMark().intValue());
    }

    @Test
    public void testCreateMarkValid15() throws MarkException {
        markService.createMark(student, 15, module);
        List<Mark> marks = markService.allMarks();
        assertEquals(15, marks.get(marks.size() - 1).getMark().intValue());
    }

    @Test
    public void testCreateMarkValid20() throws MarkException {
        markService.createMark(student, 20, module);
        List<Mark> marks = markService.allMarks();
        assertEquals(20, marks.get(marks.size() - 1).getMark().intValue());
    }

    // Test creating a mark with invalid values
    @Test
    public void testCreateMarkInvalidNull() {
        Exception exception = null;
        try {
            markService.createMark(student, null, module);
        } catch (MarkException e) {
            exception = e;
        }
        assertNotNull("Expected MarkException for null mark", exception);
        assertEquals("Invalid mark, it should be between 5 and 20", exception.getMessage());
    }

    @Test
    public void testCreateMarkInvalid4() {
        Exception exception = null;
        try {
            markService.createMark(student, 4, module);
        } catch (MarkException e) {
            exception = e;
        }
        assertNotNull("Expected MarkException for mark less than 5", exception);
        assertEquals("Invalid mark, it should be between 5 and 20", exception.getMessage());
    }

    @Test
    public void testCreateMarkInvalid21() {
        Exception exception = null;
        try {
            markService.createMark(student, 21, module);
        } catch (MarkException e) {
            exception = e;
        }
        assertNotNull("Expected MarkException for mark greater than 20", exception);
        assertEquals("Invalid mark, it should be between 5 and 20", exception.getMessage());
    }

    @Test
    public void testCreateMarkInvalidNegative1() {
        Exception exception = null;
        try {
            markService.createMark(student, -1, module);
        } catch (MarkException e) {
            exception = e;
        }
        assertNotNull("Expected MarkException for negative mark", exception);
        assertEquals("Invalid mark, it should be between 5 and 20", exception.getMessage());
    }

    @Test
    public void testCreateMarkInvalid0() {
        Exception exception = null;
        try {
            markService.createMark(student, 0, module);
        } catch (MarkException e) {
            exception = e;
        }
        assertNotNull("Expected MarkException for zero mark", exception);
        assertEquals("Invalid mark, it should be between 5 and 20", exception.getMessage());
    }

    @Test
    public void testCreateMarkInvalid25() {
        Exception exception = null;
        try {
            markService.createMark(student, 25, module);
        } catch (MarkException e) {
            exception = e;
        }
        assertNotNull("Expected MarkException for mark greater than 20", exception);
        assertEquals("Invalid mark, it should be between 5 and 20", exception.getMessage());
    }

    // Test creating a mark with null student
    @Test
    public void testCreateMarkNullStudent() {
        Exception exception = null;
        try {
            markService.createMark(null, 15, module);
        } catch (IllegalArgumentException | MarkException e) {
            exception = e;
        }
        assertNotNull("Expected IllegalArgumentException for null student", exception);
        assertEquals("Student cannot be null", exception.getMessage());
    }

    // Test creating a mark with null module
    @Test
    public void testCreateMarkNullModule() {
        Exception exception = null;
        try {
            markService.createMark(student, 15, null);
        } catch (IllegalArgumentException | MarkException e) {
            exception = e;
        }
        assertNotNull("Expected IllegalArgumentException for null module", exception);
        assertEquals("Module cannot be null", exception.getMessage());
    }

    // Test finding marks by null module
    @Test
    public void testFindMarkByModuleNullModule() {
        Exception exception = null;
        try {
            markService.findMarkByModule(null);
        } catch (IllegalArgumentException e) {
            exception = e;
        }
        assertNotNull("Expected IllegalArgumentException for null module", exception);
        assertEquals("Module cannot be null", exception.getMessage());
    }

    // Test finding marks by valid module
    @Test
    public void testFindMarkByModuleValid() throws MarkException {
        markService.createMark(student, 15, module);
        List<Mark> marks = markService.findMarkByModule(module);
        assertFalse(marks.isEmpty());
        assertEquals(15, marks.get(0).getMark().intValue());
    }

    // Test find marks by module with no marks
    @Test
    public void testFindMarkByModuleNoMarks() {
        List<Mark> marks = markService.findMarkByModule(module);
        assertTrue("Expected empty marks list", marks.isEmpty());
    }

    // Test bestMarkByModule with null module
    @Test
    public void testBestMarkByModuleNullModule() {
        Exception exception = null;
        try {
            markService.bestMarkByModule(null);
        } catch (IllegalArgumentException e) {
            exception = e;
        }
        assertNotNull("Expected IllegalArgumentException for null module", exception);
        assertEquals("Module cannot be null", exception.getMessage());
    }

    // Test bestMarkByModule with marks
    @Test
    public void testBestMarkByModuleWithMarks() throws MarkException {
        Student student1 = mock(Student.class);
        when(student1.getFullName()).thenReturn("Student 1");

        Student student2 = mock(Student.class);
        when(student2.getFullName()).thenReturn("Student 2");

        markService.createMark(student1, 15, module);
        markService.createMark(student2, 18, module);

        Student bestStudent = markService.bestMarkByModule(module);
        assertNotNull(bestStudent);
        assertEquals("Student 2", bestStudent.getFullName());
    }

    @Test
    public void testBestMarkByModuleSingleMark() throws MarkException {
        markService.createMark(student, 12, module);

        Student bestStudent = markService.bestMarkByModule(module);
        assertNotNull(bestStudent);
        assertEquals("John Doe", bestStudent.getFullName());
    }

    // Test bestMarkByModule with no marks
    @Test
    public void testBestMarkByModuleNoMarks() {
        Student bestStudent = markService.bestMarkByModule(module);
        assertNull("Expected null best student when no marks are present", bestStudent);
    }

}