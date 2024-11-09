package student;

import group.Group;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StudentService implements StudentRepository {
    List<Student> listStudent;

    public StudentService() {
        listStudent = new ArrayList<>();
    }

    @Override
    public void saveStudent(Integer id, String fullName, LocalDate dateOfBirth, Group group) {
        Student student = new Student(id, fullName, dateOfBirth, group);
        listStudent.add(student);
    }

    @Override
    public void deleteStudent(Integer idStudent) {
        listStudent.remove(idStudent);

    }

    @Override
    public Student findById(Integer id) {
        return listStudent.get(id);
    }

    @Override
    public void updateStudent(Integer id, String fullName, LocalDate dateOfBirth, Group group) {
        Student student = findById(id);
        student.setGroup(group);
        student.setDateBirth(dateOfBirth);
        student.setFullName(fullName);
        listStudent.set(id, student);

    }

    @Override
    public List<Student> allStudents() {
        return listStudent;
    }

}
