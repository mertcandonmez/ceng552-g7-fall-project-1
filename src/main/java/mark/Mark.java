package mark;

import student.Student;
import module.Module;

public class Mark {
    private Student student;
    private Integer mark;
    private Module module;

    public Mark(Student student, Integer mark, Module module) {
        if (student == null) {
            throw new IllegalArgumentException("Student cannot be null");
        }
        if (module == null) {
            throw new IllegalArgumentException("Module cannot be null");
        }
        if (mark == null || mark < 0) {
            throw new IllegalArgumentException("Mark cannot be null or negative");
        }
        this.student = student;
        this.mark = mark;
        this.module = module;
    }

    public Module getModule() {
        return module;
    }

    public Student getStudent() {
        return student;
    }

    public Integer getMark() {
        return mark;
    }

    @Override
    public String toString() {
        return "Mark{" +
                "student=" + student.toString() +
                ", mark=" + Integer.toString(mark) +
                ", module=" + module.toString() +
                '}';
    }
}