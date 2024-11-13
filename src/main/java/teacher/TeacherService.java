package teacher;

import group.Group;
import module.Module;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TeacherService implements TeacherRepository {
    List<Teacher> listTeachers;

    public TeacherService() {
        this.listTeachers = new ArrayList<Teacher>();
    }

    @Override
    public void saveTeacher(Integer id, String fullName, Grade grade, List<Module> listModules, List<Group> listGroup) {
        // Check for null values and throw IllegalArgumentException if any required
        // field is null
        Objects.requireNonNull(id, "ID cannot be null");
        Objects.requireNonNull(fullName, "Full name cannot be null");
        Objects.requireNonNull(grade, "Grade cannot be null");
        Objects.requireNonNull(listModules, "Modules list cannot be null");
        Objects.requireNonNull(listGroup, "Groups list cannot be null");

        // Create and add the teacher only if all validations pass
        Teacher teacher = new Teacher(id, fullName, grade, listModules, listGroup);
        listTeachers.add(teacher);
    }

    @Override
    public List<Teacher> allTeachers() {
        // Return an unmodifiable list to prevent external modification
        return List.copyOf(listTeachers);
    }

    @Override
    public void deleteTeacher() {
        // Check if list is not empty before attempting to remove
        if (!listTeachers.isEmpty()) {
            listTeachers.remove(listTeachers.size() - 1);
        } else {
            System.out.println("No teachers to delete");
        }
    }
}
