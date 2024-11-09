package group;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import student.Student;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class GroupServiceTest {

    private GroupService groupService;

    @BeforeEach
    public void setUp() {
        groupService = new GroupService();
    }

    // Test for creating a group
    @Test
    public void testSaveGroup() {
    	groupService = new GroupService();
        System.err.println("testSaveGroup");
        groupService.saveGroup(GroupName.MSIR);
        Group group = groupService.findByReference(GroupName.MSIR.toString());
        assertNotNull(group);
        assertEquals(GroupName.MSIR, group.getReference());
    }

    // Test for adding a student to a group
    @Test
    public void testAddStudentToGroup() {
        groupService.saveGroup(GroupName.MSIR);
        Group group = groupService.findByReference(GroupName.MSIR.toString());
        assertNotNull(group);

        Student student = new Student(1, "John Doe", LocalDate.of(2000, 1, 1), group);
        List<Student> students = new ArrayList<>();
        students.add(student);

        groupService.updateNumberOfStudent(students);
        group = groupService.findByReference(GroupName.MSIR.toString());
        
    }

    // Test for finding a group by reference
    @Test
    public void testFindByReference() {
        groupService.saveGroup(GroupName.MSIR);
        Group group = groupService.findByReference(GroupName.MSIR.toString());
        assertNotNull(group);
        assertEquals(GroupName.MSIR, group.getReference());

        Group nonExistentGroup = groupService.findByReference("NON_EXISTENT");
        assertNull(nonExistentGroup);
    }

    // Test for getting all groups
    @Test
    public void testAllGroups() {
        groupService.saveGroup(GroupName.MSIR);
        groupService.saveGroup(GroupName.MIAD);
        List<Group> groups = groupService.allGroups();
        assertEquals(2, groups.size());
    }
}
