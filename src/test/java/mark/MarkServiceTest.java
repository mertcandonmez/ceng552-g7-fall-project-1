package mark;

import mark.exception.MarkException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.MethodSource;
import student.Student;
import module.Module;
import module.ModuleName;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MarkServiceTest {

    private MarkService markService;
    private Student student;
    private Module module;

    @BeforeEach
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

    // Parametric test for creating a mark with valid values (Equivalence
    // Partitioning)
    @ParameterizedTest
    @ValueSource(ints = { 5, 10, 15, 20 })
    @DisplayName("Test creating a mark with valid values")
    public void testCreateMarkValid(int validMark) {
        markService.createMark(student, validMark, module);
        List<Mark> marks = markService.allMarks();
        assertEquals(validMark, marks.get(marks.size() - 1).getMark());
    }

    @ParameterizedTest
    @MethodSource("invalidMarkProvider")
    @DisplayName("Test creating a mark with invalid values")
    public void testCreateMarkInvalid(Integer invalidMark) {
        Exception exception = assertThrows(MarkException.class, () -> {
            markService.createMark(student, invalidMark, module);
        });
        assertEquals("invalid mark, it should be between 5 and 20", exception.getMessage());
    }

    private static Stream<Integer> invalidMarkProvider() {
        return Stream.of(null, 4, 21, -1, 0, 25);
    }

    @Test
    @DisplayName("Test creating a mark with null student")
    public void testCreateMarkNullStudent() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            markService.createMark(null, 15, module);
        });
        assertEquals("Student cannot be null", exception.getMessage());
    }

    @Test
    @DisplayName("Test creating a mark with null module")
    public void testCreateMarkNullModule() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            markService.createMark(student, 15, null);
        });
        assertEquals("Module cannot be null", exception.getMessage());
    }

    @Test
    @DisplayName("Test finding marks by null module")
    public void testFindMarkByModuleNullModule() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            markService.findMarkByModule(null);
        });
        assertEquals("Module cannot be null", exception.getMessage());
    }

    @Test
    @DisplayName("Test finding marks by valid module")
    public void testFindMarkByModuleValid() {
        markService.createMark(student, 15, module);
        List<Mark> marks = markService.findMarkByModule(module);
        assertFalse(marks.isEmpty());
        assertEquals(15, marks.get(0).getMark());
    }

    @Test
    @DisplayName("Test bestMarkByModule method with null module")
    public void testBestMarkByModuleNullModule() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            markService.bestMarkByModule(null);
        });
        assertEquals("Module cannot be null", exception.getMessage());
    }

    @ParameterizedTest
    @MethodSource("bestMarkProvider")
    @DisplayName("Test bestMarkByModule method")
    public void testBestMarkByModule(Integer mark1, Integer mark2, Integer expectedBestMark) {
        // İlk notu oluştur
        if (mark1 != null) {
            markService.createMark(student, mark1, module);
        }
        // İkinci notu oluştur
        if (mark2 != null) {
            markService.createMark(student, mark2, module);
        }

        Student bestStudent = markService.bestMarkByModule(module);

        if (expectedBestMark != null) {
            assertNotNull(bestStudent);
            // En iyi not değerini kontrol et
            List<Mark> marks = markService.findMarkByModule(module);
            int bestMark = marks.stream().mapToInt(Mark::getMark).max().orElse(0);
            assertEquals(expectedBestMark, bestMark);
        } else {
            assertNull(bestStudent);
        }
    }

    private static Stream<org.junit.jupiter.params.provider.Arguments> bestMarkProvider() {
        return Stream.of(
                org.junit.jupiter.params.provider.Arguments.of(15, 18, 18),
                org.junit.jupiter.params.provider.Arguments.of(12, null, 12),
                org.junit.jupiter.params.provider.Arguments.of(null, null, null));
    }

    @Test
    @DisplayName("Test find mark by module with no marks")
    public void testFindMarkByModuleNoMarks() {
        List<Mark> marks = markService.findMarkByModule(module);
        assertTrue(marks.isEmpty());
    }

    @Test
    @DisplayName("Test bestMarkByModule method with no marks")
    public void testBestMarkByModuleNoMarks() {
        Student bestStudent = markService.bestMarkByModule(module);
        assertNull(bestStudent);
    }
}