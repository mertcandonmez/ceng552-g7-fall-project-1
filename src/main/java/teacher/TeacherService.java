package teacher;

import group.Group;
import module.Module;

import java.util.ArrayList;
import java.util.List;

public class TeacherService implements TeacherRepository {
    List<Teacher> listTeachers;

    public TeacherService() {
        this.listTeachers = new ArrayList();
    }

    @Override
    public void saveTeacher(Integer id, String fullName, Grade grade, List<Module> listModules, List<Group> listGroup) {
        Teacher teacher = new Teacher(id, fullName, grade, listModules, listGroup);
        listTeachers.add(teacher);

    }

    @Override
    public List<Teacher> allTeachers() {
        return listTeachers;
    }

    @Override
    public void deleteTeacher() {
        if (listTeachers.size() > 0) {
            listTeachers.remove(listTeachers.size() - 1);
        }
    }
}
