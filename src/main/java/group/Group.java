package group;

public class Group {
    private final GroupName reference;
    private Integer numberStudent;

    public Group(GroupName reference) {
        if (reference == null) {
            throw new IllegalArgumentException("Group reference cannot be null");
        }
        this.reference = reference;
        this.numberStudent = 0;
    }

    public Integer getNumberStudent() {
        return numberStudent;
    }

    /**
     * Sets the number of students in the group.
     *
     * @param numberStudent the number of students
     * @throws IllegalArgumentException if the number of students is negative
     */
    public void setNumberStudent(Integer numberStudent) {
        if (numberStudent == null) {
            throw new IllegalArgumentException("Number of students cannot be null");
        }
        if (numberStudent < 0) {
            throw new IllegalArgumentException("Number of students cannot be negative");
        }
        this.numberStudent = numberStudent;
    }

    public GroupName getReference() {
        return reference;
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

}
