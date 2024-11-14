package student;

import group.Group;
import java.time.LocalDate;
import java.util.Objects;

public class Student {
    private final Integer id;
    private String fullName;
    private LocalDate dateBirth;
    private Group group;

    public Student(Integer id, String fullName, LocalDate dateBirth, Group group) {
        // Null kontrolleri
        this.id = Objects.requireNonNull(id, "ID cannot be null");
        this.fullName = Objects.requireNonNull(fullName, "Full name cannot be null");
        this.dateBirth = Objects.requireNonNull(dateBirth, "Date of birth cannot be null");
        this.group = Objects.requireNonNull(group, "Group cannot be null");
    }

    public void setFullName(String fullName) {
        this.fullName = Objects.requireNonNull(fullName, "Full name cannot be null");
    }

    public void setDateBirth(LocalDate dateBirth) {
        this.dateBirth = Objects.requireNonNull(dateBirth, "Date of birth cannot be null");
    }

    public void setGroup(Group group) {
        this.group = Objects.requireNonNull(group, "Group cannot be null");
    }

    public Group getGroup() {
        return group;
    }

    public Integer getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public LocalDate getDateBirth() {
        return dateBirth;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + getId() +
                ", fullName='" + getFullName() + '\'' +
                ", dateBirth=" + getDateBirth() +
                ", group=" + getGroup().toString() +
                '}';
    }
}