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
        Group group = new Group(reference);
        listGroups.add(group);
    }

    @Override
    public List<Group> allGroups() {
        return listGroups;
    }

    @Override
    public Group findByReference(String reference) {
        Integer index = findIndex(reference);
        if (index != null)
            return listGroups.get(index);
        else return null;
    }

    @Override
    public void updateNumberOfStudent(List<Student> listStudents) {
        for (int i = 0; i < listGroups.size(); i++) {
            int count = 0;
            String ref = listGroups.get(i).getReference().toString();
            Integer index = findIndex(ref);
            for (int j = 0; j < listStudents.size(); j++) {
                if (ref.equalsIgnoreCase(listStudents.get(j)
                        .getGroup().getReference().toString()))
                    count++;
            }
            listGroups.get(index).setNumberStudent(count);
        }
    }

    private Integer findIndex(String reference) {
        for (int i = 0; i < listGroups.size(); i++) {
            if (listGroups.get(i).getReference().toString().equalsIgnoreCase(reference))
                return i;
        }
        return null;
    }

}
