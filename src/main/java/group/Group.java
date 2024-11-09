package group;

public class Group {
    private final GroupName reference;
    private Integer numberStudent;

    public Group(GroupName reference) {
        this.reference = reference;
        numberStudent = 0;
    }

    public Integer getNumberStudent() {
        return numberStudent;
    }
    
    public void setNumberStudent(Integer numberStudent) {
        if (numberStudent < 0) {
            throw new IllegalArgumentException("Number of students cannot be negative");
        }
        this.numberStudent = numberStudent;
    }

    @Override
    public String toString() {
        return "Group{" +
                "reference=" + reference +
                '}';
    }

    public String showGroup() {
        return "Group{" +
                "reference=" + reference + " , number student=" + numberStudent +
                '}';
    }

    public GroupName getReference() {
        return reference;
    }
}
