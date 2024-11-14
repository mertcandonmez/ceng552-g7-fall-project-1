

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	group.GroupTest.class,
	group.GroupServiceTest.class,
	mark.MarkTest.class,
	mark.MarkServiceTest.class,
	module.ModuleTest.class,
	module.ModuleServiceTest.class,
    student.StudentTest.class,
    student.StudentServiceTest.class,
    teacher.TeacherTest.class,
    teacher.TeacherServiceTest.class,
    MainTest.class,
    
})

public class AllTests {

}
