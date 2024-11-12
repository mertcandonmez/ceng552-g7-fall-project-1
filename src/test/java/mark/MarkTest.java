package mark;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import student.Student;
import module.Module;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MarkTest {

    private Student student;
    private Module module;

    @BeforeEach
    public void setUp() {
        student = mock(Student.class);
        module = mock(Module.class);
        when(student.toString()).thenReturn("Student{id=1, fullName='John Doe', birthDate=2000-01-01, group=null}");
        when(module.toString()).thenReturn("Module{name='Math', code='MATH101'}");
    }

    // Parameterized test for constructor with null parameters
    @ParameterizedTest
    @MethodSource("provideNullParameters")
    @DisplayName("Test constructor with null parameters")
    public void testConstructorWithNullParameters(Student testStudent, Integer testMark, Module testModule,
            String expectedMessage) {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Mark(testStudent, testMark, testModule);
        });
        assertEquals(expectedMessage, exception.getMessage());
    }

    // Method source providing null parameters and expected exception messages
    private static Stream<Arguments> provideNullParameters() {
        Student validStudent = mock(Student.class);
        Module validModule = mock(Module.class);
        return Stream.of(
                Arguments.of(null, 85, validModule, "Student cannot be null"),
                Arguments.of(validStudent, 85, null, "Module cannot be null"),
                Arguments.of(validStudent, null, validModule, "Mark cannot be null or negative"),
                Arguments.of(validStudent, -1, validModule, "Mark cannot be null or negative"));
    }

    // Parameterized test for constructor with valid and invalid marks
    @ParameterizedTest
    @ValueSource(ints = { 0, 50, 100 })
    @DisplayName("Test constructor with various valid marks")
    public void testConstructorWithValidMarks(int validMark) {
        Mark mark = new Mark(student, validMark, module);
        assertEquals(validMark, mark.getMark());
    }

    // Test for the constructor with valid parameters
    @Test
    @DisplayName("Test constructor with valid parameters")
    public void testConstructorValid() {
        Mark mark = new Mark(student, 85, module);
        assertNotNull(mark);
        assertEquals(student, mark.getStudent());
        assertEquals(85, mark.getMark());
        assertEquals(module, mark.getModule());
    }

    // Test for the toString method
    @Test
    @DisplayName("Test toString method")
    public void testToString() {
        Mark mark = new Mark(student, 85, module);
        String expected = "Mark{student=Student{id=1, fullName='John Doe', birthDate=2000-01-01, group=null}, mark=85, module=Module{name='Math', code='MATH101'}}";
        assertEquals(expected, mark.toString());
    }
}