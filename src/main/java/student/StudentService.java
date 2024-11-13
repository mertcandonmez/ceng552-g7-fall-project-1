package student;

import group.Group;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StudentService implements StudentRepository {
    List<Student> listStudent;

    public StudentService() {
        listStudent = new ArrayList<Student>();
    }

    @Override
    public void saveStudent(Integer id, String fullName, LocalDate dateOfBirth, Group group) {
        // Check for null values in critical fields and throw exception if any are null
        Objects.requireNonNull(id, "ID cannot be null");
        Objects.requireNonNull(fullName, "Full name cannot be null");
        Objects.requireNonNull(dateOfBirth, "Date of birth cannot be null");
        Objects.requireNonNull(group, "Group cannot be null");

        Student student = new Student(id, fullName, dateOfBirth, group);
        listStudent.add(student);
    }

    @Override
    public void deleteStudent(Integer idStudent) {
        // Check for null ID and use Integer comparison to remove by ID
        Objects.requireNonNull(idStudent, "Student ID cannot be null");
        listStudent.removeIf(student -> student.getId().equals(idStudent));
    }

    @Override
    public Student findById(Integer id) {
        Objects.requireNonNull(id, "ID cannot be null");

        return listStudent.stream()
                .filter(student -> student.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Student with ID " + id + " not found"));
    }

    @Override
    public void updateStudent(Integer id, String fullName, LocalDate dateOfBirth, Group group) {
        Objects.requireNonNull(id, "ID cannot be null");
        Objects.requireNonNull(fullName, "Full name cannot be null");
        Objects.requireNonNull(dateOfBirth, "Date of birth cannot be null");
        Objects.requireNonNull(group, "Group cannot be null");

        // Find and update the student if exists
        Student student = findById(id);
        student.setFullName(fullName);
        student.setDateBirth(dateOfBirth);
        student.setGroup(group);

    }

    @Override
    public List<Student> allStudents() {
        return List.copyOf(listStudent);
    }

}
