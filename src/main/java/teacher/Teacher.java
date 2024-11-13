package teacher;

import group.Group;
import module.Module;

import java.util.List;
import java.util.Objects;

public class Teacher {
    private Integer id;
    private String fullName;
    private Grade grade;
    private List<Module> listModules;
    private List<Group> listGroup;

    public Teacher(Integer id, String fullName, Grade grade, List<Module> listModules, List<Group> listGroups) {

        this.id = Objects.requireNonNull(id, "ID cannot be null");
        this.fullName = Objects.requireNonNull(fullName, "Full name cannot be null");
        this.grade = Objects.requireNonNull(grade, "Grade cannot be null");
        this.listModules = Objects.requireNonNull(listModules, "Modules list cannot be null");
        this.listGroup = Objects.requireNonNull(listGroups, "Groups list cannot be null");
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", grade=" + grade +
                ", listModules=" + listModules +
                ", listGroup=" + listGroup +
                '}';
    }
}
