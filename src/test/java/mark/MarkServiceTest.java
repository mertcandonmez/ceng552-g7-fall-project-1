package mark;

import mark.exception.MarkException;
import module.Module;
import module.ModuleName;
import org.junit.Before;
import org.junit.Test;
import student.Student;

import java.time.LocalDate;
import java.util.Arrays;
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

    // Test creating marks with valid values
    @Test
    public void testCreateMarkWithValidValues() throws MarkException {
        List<Integer> validMarks = Arrays.asList(5, 10, 15, 20);

        for (Integer validMark : validMarks) {
            markService.createMark(student, validMark, module);
            List<Mark> marks = markService.allMarks();
            assertEquals(validMark.intValue(), marks.get(marks.size() - 1).getMark().intValue());
        }
    }

    // Test creating marks with invalid values
    @Test
    public void testCreateMarkWithInvalidValues() {
        List<Integer> invalidMarks = Arrays.asList(null, 4, 21, -1, 0, 25);

        for (Integer invalidMark : invalidMarks) {
            Exception exception = null;
            try {
                markService.createMark(student, invalidMark, module);
            } catch (MarkException e) {
                exception = e;
            }
            assertNotNull("Expected MarkException for invalid mark value: " + invalidMark, exception);
            assertEquals("Invalid mark, it should be between 5 and 20", exception.getMessage());
        }
    }

    // Test creating a mark with null student
    @Test
    public void testCreateMarkWithNullStudent() {
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
    public void testCreateMarkWithNullModule() {
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
    public void testFindMarkByModuleWithNullModule() {
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
    public void testFindMarkByValidModule() throws MarkException {
        markService.createMark(student, 15, module);
        List<Mark> marks = markService.findMarkByModule(module);
        assertFalse("Expected non-empty marks list", marks.isEmpty());
        assertEquals(15, marks.get(0).getMark().intValue());
    }

    // Test finding marks by module with no marks
    @Test
    public void testFindMarkByModuleWithNoMarks() {
        List<Mark> marks = markService.findMarkByModule(module);
        assertTrue("Expected empty marks list", marks.isEmpty());
    }

    // Test bestMarkByModule with null module
    @Test
    public void testBestMarkByModuleWithNullModule() {
        Exception exception = null;
        try {
            markService.bestMarkByModule(null);
        } catch (IllegalArgumentException e) {
            exception = e;
        }
        assertNotNull("Expected IllegalArgumentException for null module", exception);
        assertEquals("Module cannot be null", exception.getMessage());
    }

    // Test bestMarkByModule with multiple marks
    @Test
    public void testBestMarkByModuleWithMultipleMarks() throws MarkException {
        Student student1 = mock(Student.class);
        when(student1.getFullName()).thenReturn("Student 1");

        Student student2 = mock(Student.class);
        when(student2.getFullName()).thenReturn("Student 2");

        markService.createMark(student1, 15, module);
        markService.createMark(student2, 18, module);

        Student bestStudent = markService.bestMarkByModule(module);
        assertNotNull("Expected a best student", bestStudent);
        assertEquals("Student 2", bestStudent.getFullName());
    }

    // Test bestMarkByModule with a single mark
    @Test
    public void testBestMarkByModuleWithSingleMark() throws MarkException {
        markService.createMark(student, 12, module);

        Student bestStudent = markService.bestMarkByModule(module);
        assertNotNull("Expected a best student", bestStudent);
        assertEquals("John Doe", bestStudent.getFullName());
    }

    // Test bestMarkByModule with no marks
    @Test
    public void testBestMarkByModuleWithNoMarks() {
        Student bestStudent = markService.bestMarkByModule(module);
        assertNull("Expected null best student when no marks are present", bestStudent);
    }
}