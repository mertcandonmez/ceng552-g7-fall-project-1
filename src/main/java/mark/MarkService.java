package mark;

import mark.exception.MarkException;
import student.Student;
import module.Module;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MarkService implements MarkRepository {
    List<Mark> listMarks;

    public MarkService() {
        this.listMarks = new ArrayList<Mark>();
    }

    @Override
    public void createMark(Student student, Integer mark, Module module) {
        if (student == null) {
            throw new IllegalArgumentException("Student cannot be null");
        }
        if (module == null) {
            throw new IllegalArgumentException("Module cannot be null");
        }
        if (mark == null || !isValid(mark)) {
            throw new MarkException("invalid mark, it should be between 5 and 20");
        }
        Mark markObj = new Mark(student, mark, module);
        listMarks.add(markObj);
    }

    private boolean isValid(Integer mark) {
        return mark >= 5 && mark <= 20;
    }

    @Override
    public List<Mark> allMarks() {

        return listMarks;
    }

    @Override
    public List<Mark> findMarkByModule(Module module) {
        if (module == null) {
            throw new IllegalArgumentException("Module cannot be null");
        }
        return listMarks.stream().filter(m -> m.getModule().getReference()
                .toString().equalsIgnoreCase(module.getReference().toString()))
                .collect(Collectors.toList());

    }

    @Override
    public Student bestMarkByModule(Module module) {
        if (module == null) {
            throw new IllegalArgumentException("Module cannot be null");
        }
        Integer maxMark = null;
        Mark bestMark = null;

        for (Mark mark : listMarks) {
            if (mark.getModule().getReference().toString().equalsIgnoreCase(module.getReference().toString())) {
                if (maxMark == null || mark.getMark() > maxMark) {
                    maxMark = mark.getMark();
                    bestMark = mark;
                }
            }
        }
        return bestMark != null ? bestMark.getStudent() : null;
    }

}
