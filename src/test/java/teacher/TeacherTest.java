package teacher;

import org.junit.Test;
import static org.junit.Assert.*;
import group.Group;
import group.GroupName;
import module.Module;
import module.ModuleName;

import java.util.ArrayList;
import java.util.List;

public class TeacherTest {

        @Test
        public void testConstructor_ValidInput() {
                List<Module> modules = new ArrayList<>();
                modules.add(new Module(ModuleName.RI, "Robotics and IoT", null));

                List<Group> groups = new ArrayList<>();
                groups.add(new Group(GroupName.MSIR));

                Teacher teacher = new Teacher(1, "Alice", Grade.MCB, modules, groups);

                assertEquals("ID should match", 1, teacher.getId().intValue());
                assertEquals("Full name should match", "Alice", teacher.getFullName());
                assertEquals("Grade should match", Grade.MCB, teacher.getGrade());
                assertEquals("Modules list should match", modules, teacher.getListModules());
                assertEquals("Groups list should match", groups, teacher.getListGroup());
        }

        @Test
        public void testConstructor_NullChecks() {
                List<Module> modules = new ArrayList<>();
                modules.add(new Module(ModuleName.BDA, "Big Data Analytics", null));

                List<Group> groups = new ArrayList<>();
                groups.add(new Group(GroupName.MSIA));

                try {
                        new Teacher(null, "Alice", Grade.MCA, modules, groups);
                        fail("Should throw exception for null ID");
                } catch (NullPointerException e) {
                        System.out.println();
                }

                try {
                        new Teacher(1, null, Grade.MCA, modules, groups);
                        fail("Should throw exception for null full name");
                } catch (NullPointerException e) {
                        // Expected exception
                }

                try {
                        new Teacher(1, "Alice", null, modules, groups);
                        fail("Should throw exception for null grade");
                } catch (NullPointerException e) {
                        // Expected exception
                }

                try {
                        new Teacher(1, "Alice", Grade.MCA, null, groups);
                        fail("Should throw exception for null modules list");
                } catch (NullPointerException e) {
                        // Expected exception
                }

                try {
                        new Teacher(1, "Alice", Grade.MCA, modules, null);
                        fail("Should throw exception for null groups list");
                } catch (NullPointerException e) {
                        // Expected exception
                }
        }

        // Boundary Value Tests for the `Teacher` class parameters
        @Test
        public void testConstructor_BoundaryId() {
                List<Module> modules = new ArrayList<>();
                modules.add(new Module(ModuleName.RI, "Robotics and IoT", null));

                List<Group> groups = new ArrayList<>();
                groups.add(new Group(GroupName.MSIR));

                // Minimum boundary for ID
                Teacher teacherMin = new Teacher(1, "Min ID Teacher", Grade.MCA, modules, groups);
                assertEquals("ID should match minimum boundary value", 1, teacherMin.getId().intValue());

                // Maximum boundary example for ID (Integer.MAX_VALUE)
                Teacher teacherMax = new Teacher(Integer.MAX_VALUE, "Max ID Teacher", Grade.MCB, modules, groups);
                assertEquals("ID should match maximum boundary value", Integer.MAX_VALUE,
                                teacherMax.getId().intValue());
        }

        @Test
        public void testConstructor_BoundaryFullName() {
                List<Module> modules = new ArrayList<>();
                modules.add(new Module(ModuleName.RI, "Robotics and IoT", null));

                List<Group> groups = new ArrayList<>();
                groups.add(new Group(GroupName.MSIR));

                // Minimum boundary for fullName (1 character)
                Teacher teacherMin = new Teacher(2, "A", Grade.MCA, modules, groups);
                assertEquals("Full name should match minimum boundary", "A", teacherMin.getFullName());

                // Maximum boundary for fullName (255 characters)
                String longName = new String(new char[255]).replace('\0', 'A');
                Teacher teacherMax = new Teacher(3, longName, Grade.MCB, modules, groups);
                assertEquals("Full name should match maximum boundary", longName, teacherMax.getFullName());
        }

        @Test
        public void testConstructor_BoundaryModules() {
                List<Group> groups = new ArrayList<>();
                groups.add(new Group(GroupName.MSIR));

                // Minimum boundary for modules (1 module)
                List<Module> minModules = new ArrayList<>();
                minModules.add(new Module(ModuleName.BDA, "Big Data Analytics", null));
                Teacher teacherMinModules = new Teacher(4, "Teacher Min Modules", Grade.MCB, minModules, groups);
                assertEquals("Modules list should match minimum boundary", minModules,
                                teacherMinModules.getListModules());

                // Typical case with multiple modules
                List<Module> multipleModules = new ArrayList<>();
                multipleModules.add(new Module(ModuleName.RI, "Robotics and IoT", null));
                multipleModules.add(new Module(ModuleName.DEV_OPS, "DevOps", null));
                Teacher teacherMultipleModules = new Teacher(5, "Teacher Multiple Modules", Grade.MCB, multipleModules,
                                groups);
                assertEquals("Modules list should contain multiple entries", multipleModules,
                                teacherMultipleModules.getListModules());
        }

        @Test
        public void testConstructor_BoundaryGroups() {
                List<Module> modules = new ArrayList<>();
                modules.add(new Module(ModuleName.RI, "Robotics and IoT", null));

                // Minimum boundary for groups (1 group)
                List<Group> minGroups = new ArrayList<>();
                minGroups.add(new Group(GroupName.MSIR));
                Teacher teacherMinGroups = new Teacher(6, "Teacher Min Groups", Grade.MCA, modules, minGroups);
                assertEquals("Groups list should match minimum boundary", minGroups, teacherMinGroups.getListGroup());

                // Typical case with multiple groups
                List<Group> multipleGroups = new ArrayList<>();
                multipleGroups.add(new Group(GroupName.MSIR));
                multipleGroups.add(new Group(GroupName.MIAD));
                Teacher teacherMultipleGroups = new Teacher(7, "Teacher Multiple Groups", Grade.MCB, modules,
                                multipleGroups);
                assertEquals("Groups list should contain multiple entries", multipleGroups,
                                teacherMultipleGroups.getListGroup());
        }

        @Test
        public void testToString() {
                List<Module> modules = new ArrayList<>();
                modules.add(new Module(ModuleName.DEV_OPS, "DevOps", null));

                List<Group> groups = new ArrayList<>();
                groups.add(new Group(GroupName.MSIR));

                Teacher teacher = new Teacher(1, "Alice", Grade.MCB, modules, groups);
                String expectedString = "Teacher{id=1, fullName='Alice', grade=MCB, listModules=" + modules
                                + ", listGroup=" + groups + "}";
                assertEquals("toString output should match expected format", expectedString, teacher.toString());
        }
}