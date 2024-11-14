package teacher;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import group.Group;
import group.GroupName;
import module.Module;
import module.ModuleName;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TeacherTest {

    @Test
    @DisplayName("Constructor with valid input values should create Teacher correctly")
    void testConstructor_ValidInput() {
        List<Module> modules = new ArrayList<>();
        modules.add(new Module(ModuleName.RI, "Robotics and IoT", null));

        List<Group> groups = new ArrayList<>();
        groups.add(new Group(GroupName.MSIR));

        Teacher teacher = new Teacher(1, "Alice", Grade.MCB, modules, groups);

        assertEquals(1, teacher.getId());
        assertEquals("Alice", teacher.getFullName());
        assertEquals(Grade.MCB, teacher.getGrade());
        assertEquals(modules, teacher.getListModules());
        assertEquals(groups, teacher.getListGroup());
    }

    @Test
    @DisplayName("Constructor should throw NullPointerException for null parameters")
    void testConstructor_NullChecks() {
        List<Module> modules = new ArrayList<>();
        modules.add(new Module(ModuleName.BDA, "Big Data Analytics", null));

        List<Group> groups = new ArrayList<>();
        groups.add(new Group(GroupName.MSIA));

        assertThrows(NullPointerException.class, () -> new Teacher(null, "Alice", Grade.MCA, modules, groups),
                "Should throw exception for null ID");
        assertThrows(NullPointerException.class, () -> new Teacher(1, null, Grade.MCA, modules, groups),
                "Should throw exception for null full name");
        assertThrows(NullPointerException.class, () -> new Teacher(1, "Alice", null, modules, groups),
                "Should throw exception for null grade");
        assertThrows(NullPointerException.class, () -> new Teacher(1, "Alice", Grade.MCA, null, groups),
                "Should throw exception for null modules list");
        assertThrows(NullPointerException.class, () -> new Teacher(1, "Alice", Grade.MCA, modules, null),
                "Should throw exception for null groups list");
    }

    // Boundary Value Tests for the `Teacher` class parameters
    @Test
    @DisplayName("Boundary values for ID in Teacher constructor")
    void testConstructor_BoundaryId() {
        List<Module> modules = List.of(new Module(ModuleName.RI, "Robotics and IoT", null));
        List<Group> groups = List.of(new Group(GroupName.MSIR));

        // Minimum boundary for ID
        Teacher teacherMin = new Teacher(1, "Min ID Teacher", Grade.MCA, modules, groups);
        assertEquals(1, teacherMin.getId(), "ID should match minimum boundary value");

        // Maximum boundary example for ID (Integer.MAX_VALUE)
        Teacher teacherMax = new Teacher(Integer.MAX_VALUE, "Max ID Teacher", Grade.MCB, modules, groups);
        assertEquals(Integer.MAX_VALUE, teacherMax.getId(), "ID should match maximum boundary value");
    }

    @Test
    @DisplayName("Boundary values for fullName in Teacher constructor")
    void testConstructor_BoundaryFullName() {
        List<Module> modules = List.of(new Module(ModuleName.RI, "Robotics and IoT", null));
        List<Group> groups = List.of(new Group(GroupName.MSIR));

        // Minimum boundary for fullName (1 character)
        Teacher teacherMin = new Teacher(2, "A", Grade.MCA, modules, groups);
        assertEquals("A", teacherMin.getFullName(), "Full name should match minimum boundary");

        // Maximum boundary for fullName (255 characters)
        String longName = "A".repeat(255);
        Teacher teacherMax = new Teacher(3, longName, Grade.MCB, modules, groups);
        assertEquals(longName, teacherMax.getFullName(), "Full name should match maximum boundary");
    }

    @Test
    @DisplayName("Boundary values for modules list in Teacher constructor")
    void testConstructor_BoundaryModules() {
        List<Group> groups = List.of(new Group(GroupName.MSIR));

        // Minimum boundary for modules (1 module)
        List<Module> minModules = List.of(new Module(ModuleName.BDA, "Big Data Analytics", null));
        Teacher teacherMinModules = new Teacher(4, "Teacher Min Modules", Grade.MCB, minModules, groups);
        assertEquals(minModules, teacherMinModules.getListModules(), "Modules list should match minimum boundary");

        // Typical case with multiple modules
        List<Module> multipleModules = List.of(
                new Module(ModuleName.RI, "Robotics and IoT", null),
                new Module(ModuleName.DEV_OPS, "DevOps", null));
        Teacher teacherMultipleModules = new Teacher(5, "Teacher Multiple Modules", Grade.MCB, multipleModules, groups);
        assertEquals(multipleModules, teacherMultipleModules.getListModules(),
                "Modules list should contain multiple entries");
    }

    @Test
    @DisplayName("Boundary values for groups list in Teacher constructor")
    void testConstructor_BoundaryGroups() {
        List<Module> modules = List.of(new Module(ModuleName.RI, "Robotics and IoT", null));

        // Minimum boundary for groups (1 group)
        List<Group> minGroups = List.of(new Group(GroupName.MSIR));
        Teacher teacherMinGroups = new Teacher(6, "Teacher Min Groups", Grade.MCA, modules, minGroups);
        assertEquals(minGroups, teacherMinGroups.getListGroup(), "Groups list should match minimum boundary");

        // Typical case with multiple groups
        List<Group> multipleGroups = List.of(
                new Group(GroupName.MSIR),
                new Group(GroupName.MIAD));
        Teacher teacherMultipleGroups = new Teacher(7, "Teacher Multiple Groups", Grade.MCB, modules, multipleGroups);
        assertEquals(multipleGroups, teacherMultipleGroups.getListGroup(),
                "Groups list should contain multiple entries");
    }

    @Test
    @DisplayName("toString method should return correctly formatted Teacher details")
    void testToString() {
        List<Module> modules = new ArrayList<>();
        modules.add(new Module(ModuleName.DEV_OPS, "DevOps", null));

        List<Group> groups = new ArrayList<>();
        groups.add(new Group(GroupName.MSIR));

        Teacher teacher = new Teacher(1, "Alice", Grade.MCB, modules, groups);
        String expectedString = "Teacher{id=1, fullName='Alice', grade=MCB, listModules=" + modules + ", listGroup="
                + groups + "}";
        assertEquals(expectedString, teacher.toString(), "toString output should match expected format");
    }
}