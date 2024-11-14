package teacher;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import group.Group;
import group.GroupName;
import module.Module;
import module.ModuleName;

import java.util.List;
import java.util.ArrayList;

public class TeacherServiceTest {

        private TeacherService teacherService;

        @Before
        public void setUp() {
                teacherService = new TeacherService();
        }

        @Test
        public void testSaveTeacher_BoundaryId() {
                List<Module> modules = new ArrayList<>();
                modules.add(new Module(ModuleName.RI, "Robotics and IoT", null));

                List<Group> groups = new ArrayList<>();
                groups.add(new Group(GroupName.MSIR));

                // Minimum boundary for ID
                teacherService.saveTeacher(1, "Min ID Teacher", Grade.MCA, modules, groups);
                assertEquals("ID should match minimum boundary value", 1,
                                teacherService.allTeachers().get(0).getId().intValue());

                // Maximum boundary example for ID (Integer.MAX_VALUE)
                teacherService.saveTeacher(Integer.MAX_VALUE, "Max ID Teacher", Grade.MCB, modules, groups);
                assertEquals("ID should match maximum boundary value", Integer.MAX_VALUE,
                                teacherService.allTeachers().get(1).getId().intValue());
        }

        @Test
        public void testSaveTeacher_BoundaryFullName() {
                List<Module> modules = new ArrayList<>();
                modules.add(new Module(ModuleName.RI, "Robotics and IoT", null));

                List<Group> groups = new ArrayList<>();
                groups.add(new Group(GroupName.MSIR));

                // Minimum boundary for fullName (1 character)
                teacherService.saveTeacher(2, "A", Grade.MCA, modules, groups);
                assertEquals("Full name should match minimum boundary", "A",
                                teacherService.allTeachers().get(0).getFullName());

                // Maximum boundary for fullName (255 characters)
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < 255; i++) {
                        sb.append('A');
                }
                String longName = sb.toString();

                teacherService.saveTeacher(3, longName, Grade.MCB, modules, groups);
                assertEquals("Full name should match maximum boundary", longName,
                                teacherService.allTeachers().get(1).getFullName());
        }

        @Test
        public void testSaveTeacher_BoundaryModules() {
                List<Group> groups = new ArrayList<>();
                groups.add(new Group(GroupName.MSIR));

                // Minimum boundary for modules (1 module)
                List<Module> minModules = new ArrayList<>();
                minModules.add(new Module(ModuleName.BDA, "Big Data Analytics", null));
                teacherService.saveTeacher(4, "Teacher Min Modules", Grade.MCB, minModules, groups);
                assertEquals("Modules list should match minimum boundary", minModules,
                                teacherService.allTeachers().get(0).getListModules());

                // Typical case with multiple modules
                List<Module> multipleModules = new ArrayList<>();
                multipleModules.add(new Module(ModuleName.RI, "Robotics and IoT", null));
                multipleModules.add(new Module(ModuleName.DEV_OPS, "DevOps", null));
                teacherService.saveTeacher(5, "Teacher Multiple Modules", Grade.MCB, multipleModules, groups);
                assertEquals("Modules list should contain multiple entries", multipleModules,
                                teacherService.allTeachers().get(1).getListModules());
        }

        @Test
        public void testSaveTeacher_BoundaryGroups() {
                List<Module> modules = new ArrayList<>();
                modules.add(new Module(ModuleName.RI, "Robotics and IoT", null));

                // Minimum boundary for groups (1 group)
                List<Group> minGroups = new ArrayList<>();
                minGroups.add(new Group(GroupName.MSIR));
                teacherService.saveTeacher(6, "Teacher Min Groups", Grade.MCA, modules, minGroups);
                assertEquals("Groups list should match minimum boundary", minGroups,
                                teacherService.allTeachers().get(0).getListGroup());

                // Typical case with multiple groups
                List<Group> multipleGroups = new ArrayList<>();
                multipleGroups.add(new Group(GroupName.MSIR));
                multipleGroups.add(new Group(GroupName.MIAD));
                teacherService.saveTeacher(7, "Teacher Multiple Groups", Grade.MCB, modules, multipleGroups);
                assertEquals("Groups list should contain multiple entries", multipleGroups,
                                teacherService.allTeachers().get(1).getListGroup());
        }

        @Test
        public void testSaveTeacher_NullChecks() {
                List<Module> modules = new ArrayList<>();
                modules.add(new Module(ModuleName.RI, "Robotics and IoT", null));
                List<Group> groups = new ArrayList<>();
                groups.add(new Group(GroupName.MSIR));

                try {
                        teacherService.saveTeacher(null, "Alice", Grade.MCA, modules, groups);
                        fail("Should throw exception for null ID");
                } catch (NullPointerException e) {
                        // Expected exception
                }

                try {
                        teacherService.saveTeacher(1, null, Grade.MCA, modules, groups);
                        fail("Should throw exception for null full name");
                } catch (NullPointerException e) {
                        // Expected exception
                }

                try {
                        teacherService.saveTeacher(1, "Alice", null, modules, groups);
                        fail("Should throw exception for null grade");
                } catch (NullPointerException e) {
                        // Expected exception
                }

                try {
                        teacherService.saveTeacher(1, "Alice", Grade.MCA, null, groups);
                        fail("Should throw exception for null modules list");
                } catch (NullPointerException e) {
                        // Expected exception
                }

                try {
                        teacherService.saveTeacher(1, "Alice", Grade.MCA, modules, null);
                        fail("Should throw exception for null groups list");
                } catch (NullPointerException e) {
                        // Expected exception
                }
        }

        @Test
        public void testAllTeachers_UnmodifiableList() {
                List<Module> modules = new ArrayList<>();
                modules.add(new Module(ModuleName.RI, "Robotics and IoT", null));
                List<Group> groups = new ArrayList<>();
                groups.add(new Group(GroupName.MSIR));

                teacherService.saveTeacher(1, "Alice", Grade.MCA, modules, groups);
                List<Teacher> teachers = teacherService.allTeachers();

                try {
                        teachers.add(new Teacher(2, "Bob", Grade.MCB, modules, groups));
                        fail("Should throw exception when modifying unmodifiable list");
                } catch (UnsupportedOperationException e) {
                        // Expected exception
                }
        }

        @Test
        public void testDeleteTeacher_RemoveLastTeacher() {
                List<Module> modules = new ArrayList<>();
                modules.add(new Module(ModuleName.RI, "Robotics and IoT", null));
                List<Group> groups = new ArrayList<>();
                groups.add(new Group(GroupName.MSIR));

                teacherService.saveTeacher(1, "Alice", Grade.MCA, modules, groups);
                teacherService.saveTeacher(2, "Bob", Grade.MCB, modules, groups);

                teacherService.deleteTeacher();
                assertEquals("List should contain 1 teacher after deletion", 1, teacherService.allTeachers().size());
                assertEquals("Remaining teacher should be Alice", "Alice",
                                teacherService.allTeachers().get(0).getFullName());

                // Test deleting from an empty list
                teacherService.deleteTeacher(); // Now the list is empty
                teacherService.deleteTeacher(); // Attempting to delete again
                assertTrue("List should remain empty if delete is called on empty list",
                                teacherService.allTeachers().isEmpty());
        }
}