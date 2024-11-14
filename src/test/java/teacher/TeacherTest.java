package teacher;

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

    @Test
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