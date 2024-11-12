package group;

import student.Student;

import java.util.ArrayList;
import java.util.List;

public class GroupService implements GroupRepository {
    List<Group> listGroups;

    public GroupService() {
        this.listGroups = new ArrayList<>();
    }

    @Override
    public void saveGroup(GroupName reference) {
        if (reference == null) {
            throw new IllegalArgumentException("Group reference cannot be null");
        }
        if (findByReference(reference.toString()) != null) {
            throw new IllegalArgumentException("Group with the same reference already exists");
        }
        Group group = new Group(reference);
        listGroups.add(group);
    }

    @Override
    public List<Group> allGroups() {
        return listGroups;
    }

    @Override
    public Group findByReference(String reference) {
        if (reference == null) {
            throw new IllegalArgumentException("Group reference cannot be null");
        }
        Integer index = findIndex(reference);
        if (index != null) {
            return listGroups.get(index);
        } else {
            return null;
        }
    }

    @Override
    public void updateNumberOfStudent(List<Student> listStudents) {
        if (listStudents == null) {
            throw new IllegalArgumentException("Student list cannot be null");
        }
        for (Group group : listGroups) {
            int count = 0;
            String ref = group.getReference().toString();
            for (Student student : listStudents) {
                if (student.getGroup() != null && ref.equalsIgnoreCase(student.getGroup().getReference().toString())) {
                    count++;
                }
            }
            group.setNumberStudent(count);
        }
    }

    private Integer findIndex(String reference) {
        if (reference == null) {
            throw new IllegalArgumentException("Group reference cannot be null");
        }
        for (int i = 0; i < listGroups.size(); i++) {
            if (listGroups.get(i).getReference().toString().equalsIgnoreCase(reference)) {
                return i;
            }
        }
        return null;
    }
}
