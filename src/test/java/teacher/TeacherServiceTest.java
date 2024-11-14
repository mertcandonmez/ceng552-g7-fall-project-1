package teacher;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import group.Group;
import group.GroupName;
import module.Module;
import module.ModuleName;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TeacherServiceTest {

    private TeacherService teacherService;

    @BeforeEach
    @DisplayName("Initialize TeacherService")
    void setUp() {
        teacherService = new TeacherService();
    }

    @Test
    @DisplayName("Save teacher with boundary values for ID")
    void testSaveTeacher_BoundaryId() {
        List<Module> modules = List.of(new Module(ModuleName.RI, "Robotics and IoT", null));
        List<Group> groups = List.of(new Group(GroupName.MSIR));

        // Minimum boundary for ID
        teacherService.saveTeacher(1, "Min ID Teacher", Grade.MCA, modules, groups);
        assertEquals(1, teacherService.allTeachers().get(0).getId(), "ID should match minimum boundary value");

        // Maximum boundary example for ID (Integer.MAX_VALUE)
        teacherService.saveTeacher(Integer.MAX_VALUE, "Max ID Teacher", Grade.MCB, modules, groups);
        assertEquals(Integer.MAX_VALUE, teacherService.allTeachers().get(1).getId(),
                "ID should match maximum boundary value");
    }

    @Test
    @DisplayName("Save teacher with boundary values for fullName")
    void testSaveTeacher_BoundaryFullName() {
        List<Module> modules = List.of(new Module(ModuleName.RI, "Robotics and IoT", null));
        List<Group> groups = List.of(new Group(GroupName.MSIR));

        // Minimum boundary for fullName (1 character)
        teacherService.saveTeacher(2, "A", Grade.MCA, modules, groups);
        assertEquals("A", teacherService.allTeachers().get(0).getFullName(), "Full name should match minimum boundary");

        // Maximum boundary for fullName (255 characters)
        String longName = "A".repeat(255);
        teacherService.saveTeacher(3, longName, Grade.MCB, modules, groups);
        assertEquals(longName, teacherService.allTeachers().get(1).getFullName(),
                "Full name should match maximum boundary");
    }

    @Test
    @DisplayName("Save teacher with boundary values for modules list")
    void testSaveTeacher_BoundaryModules() {
        List<Group> groups = List.of(new Group(GroupName.MSIR));

        // Minimum boundary for modules (1 module)
        List<Module> minModules = List.of(new Module(ModuleName.BDA, "Big Data Analytics", null));
        teacherService.saveTeacher(4, "Teacher Min Modules", Grade.MCB, minModules, groups);
        assertEquals(minModules, teacherService.allTeachers().get(0).getListModules(),
                "Modules list should match minimum boundary");

        // Typical case with multiple modules
        List<Module> multipleModules = List.of(
                new Module(ModuleName.RI, "Robotics and IoT", null),
                new Module(ModuleName.DEV_OPS, "DevOps", null));
        teacherService.saveTeacher(5, "Teacher Multiple Modules", Grade.MCB, multipleModules, groups);
        assertEquals(multipleModules, teacherService.allTeachers().get(1).getListModules(),
                "Modules list should contain multiple entries");
    }

    @Test
    @DisplayName("Save teacher with boundary values for groups list")
    void testSaveTeacher_BoundaryGroups() {
        List<Module> modules = List.of(new Module(ModuleName.RI, "Robotics and IoT", null));

        // Minimum boundary for groups (1 group)
        List<Group> minGroups = List.of(new Group(GroupName.MSIR));
        teacherService.saveTeacher(6, "Teacher Min Groups", Grade.MCA, modules, minGroups);
        assertEquals(minGroups, teacherService.allTeachers().get(0).getListGroup(),
                "Groups list should match minimum boundary");

        // Typical case with multiple groups
        List<Group> multipleGroups = List.of(
                new Group(GroupName.MSIR),
                new Group(GroupName.MIAD));
        teacherService.saveTeacher(7, "Teacher Multiple Groups", Grade.MCB, modules, multipleGroups);
        assertEquals(multipleGroups, teacherService.allTeachers().get(1).getListGroup(),
                "Groups list should contain multiple entries");
    }

    @Test
    @DisplayName("Save teacher should throw exception for null values")
    void testSaveTeacher_NullChecks() {
        List<Module> modules = List.of(new Module(ModuleName.RI, "Robotics and IoT", null));
        List<Group> groups = List.of(new Group(GroupName.MSIR));

        assertThrows(NullPointerException.class,
                () -> teacherService.saveTeacher(null, "Alice", Grade.MCA, modules, groups),
                "Should throw exception for null ID");
        assertThrows(NullPointerException.class, () -> teacherService.saveTeacher(1, null, Grade.MCA, modules, groups),
                "Should throw exception for null full name");
        assertThrows(NullPointerException.class, () -> teacherService.saveTeacher(1, "Alice", null, modules, groups),
                "Should throw exception for null grade");
        assertThrows(NullPointerException.class, () -> teacherService.saveTeacher(1, "Alice", Grade.MCA, null, groups),
                "Should throw exception for null modules list");
        assertThrows(NullPointerException.class, () -> teacherService.saveTeacher(1, "Alice", Grade.MCA, modules, null),
                "Should throw exception for null groups list");
    }

    @Test
    @DisplayName("Retrieve all teachers should return unmodifiable list")
    void testAllTeachers_UnmodifiableList() {
        List<Module> modules = List.of(new Module(ModuleName.RI, "Robotics and IoT", null));
        List<Group> groups = List.of(new Group(GroupName.MSIR));

        teacherService.saveTeacher(1, "Alice", Grade.MCA, modules, groups);
        List<Teacher> teachers = teacherService.allTeachers();

        assertThrows(UnsupportedOperationException.class,
                () -> teachers.add(new Teacher(2, "Bob", Grade.MCB, modules, groups)),
                "Should throw exception when modifying unmodifiable list");
    }

    @Test
    @DisplayName("Delete last teacher from list and verify removal")
    void testDeleteTeacher_RemoveLastTeacher() {
        List<Module> modules = List.of(new Module(ModuleName.RI, "Robotics and IoT", null));
        List<Group> groups = List.of(new Group(GroupName.MSIR));

        teacherService.saveTeacher(1, "Alice", Grade.MCA, modules, groups);
        teacherService.saveTeacher(2, "Bob", Grade.MCB, modules, groups);

        teacherService.deleteTeacher();
        assertEquals(1, teacherService.allTeachers().size(), "List should contain 1 teacher after deletion");
        assertEquals("Alice", teacherService.allTeachers().get(0).getFullName(), "Remaining teacher should be Alice");

        // Test deleting from an empty list
        teacherService.deleteTeacher(); // Now the list is empty
        teacherService.deleteTeacher(); // Attempting to delete again
        assertTrue(teacherService.allTeachers().isEmpty(),
                "List should remain empty if delete is called on empty list");
    }
}