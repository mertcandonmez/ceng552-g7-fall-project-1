package teacher;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import group.Group;
import group.GroupName;
import module.Module;
import module.ModuleName;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TeacherServiceTest {

    private TeacherService teacherService;

    @BeforeEach
    void setUp() {
        teacherService = new TeacherService();
    }

    @Test
    void testSaveTeacher_ValidInput() {
        List<Module> modules = new ArrayList<>();
        modules.add(new Module(null, "Mathematics", null));

        List<Group> groups = new ArrayList<>();
        groups.add(new Group(GroupName.MSIR));

        teacherService.saveTeacher(1, "John Doe", Grade.MCA, modules, groups);

        assertEquals(1, teacherService.allTeachers().size(), "Teacher list should contain 1 teacher after saving");
        Teacher savedTeacher = teacherService.allTeachers().get(0);
        assertEquals("John Doe", savedTeacher.getFullName());
        assertEquals(Grade.MCA, savedTeacher.getGrade());
    }

    @Test
    void testSaveTeacher_NullChecks() {
        List<Module> modules = new ArrayList<>();
        modules.add(new Module(ModuleName.BDA, "Big Data Analytics", null));

        List<Group> groups = new ArrayList<>();
        groups.add(new Group(GroupName.MIAD));

        assertThrows(NullPointerException.class,
                () -> teacherService.saveTeacher(null, "John Doe", Grade.MCA, modules, groups),
                "Should throw exception for null ID");
        assertThrows(NullPointerException.class, () -> teacherService.saveTeacher(1, null, Grade.MCA, modules, groups),
                "Should throw exception for null full name");
        assertThrows(NullPointerException.class, () -> teacherService.saveTeacher(1, "John Doe", null, modules, groups),
                "Should throw exception for null grade");
        assertThrows(NullPointerException.class,
                () -> teacherService.saveTeacher(1, "John Doe", Grade.MCA, null, groups),
                "Should throw exception for null modules list");
        assertThrows(NullPointerException.class,
                () -> teacherService.saveTeacher(1, "John Doe", Grade.MCA, modules, null),
                "Should throw exception for null groups list");
    }

    @Test
    void testAllTeachers() {
        assertTrue(teacherService.allTeachers().isEmpty(), "Teacher list should initially be empty");

        List<Module> modules = new ArrayList<>();
        modules.add(new Module(ModuleName.CRY, "Cryptography", null));

        List<Group> groups = new ArrayList<>();
        groups.add(new Group(GroupName.MSIA));

        teacherService.saveTeacher(1, "John Doe", Grade.MCA, modules, groups);
        teacherService.saveTeacher(2, "Jane Doe", Grade.MCB, modules, groups);

        List<Teacher> teachers = teacherService.allTeachers();
        assertEquals(2, teachers.size(), "Teacher list should contain 2 teachers after saving");
    }

    @Test
    void testDeleteTeacher() {
        List<Module> modules = new ArrayList<>();
        modules.add(new Module(ModuleName.GL, "Game Logic", null));

        List<Group> groups = new ArrayList<>();
        groups.add(new Group(GroupName.MIAD));

        teacherService.saveTeacher(1, "John Doe", Grade.MCA, modules, groups);
        teacherService.deleteTeacher();

        assertTrue(teacherService.allTeachers().isEmpty(), "Teacher list should be empty after deletion");

        // Deletion when list is already empty
        teacherService.deleteTeacher();
        assertTrue(teacherService.allTeachers().isEmpty(), "Deleting from an empty list should keep it empty");
    }

}