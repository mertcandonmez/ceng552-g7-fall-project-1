import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MainTest {

    @Test
    @DisplayName("Test main method output")
    public void testMainMethodOutput() {
        
        Main main = new Main();
        // Capture the output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        // Run the main method
        main.main(null);

        // Restore the original System.out
        System.setOut(originalOut);

        // Get the output
        String output = outputStream.toString();

        // Expected output
        String expectedOutput = 
                "listGroups \n" + //
                "Group{reference=MIAD , number student=0}\n" + //
                "Group{reference=MSIA , number student=0}\n" + //
                "Group{reference=MSIR , number student=0}\n" + //
                "-------------------------------------------\n" + //
                "listModules \n" + //
                "Module{reference=BDA, name='base de donnée avancé', numberHours=40}\n" + //
                "Module{reference=CRY, name='cryptographie', numberHours=35}\n" + //
                "Module{reference=RI, name='réseaux Informatique', numberHours=28}\n" + //
                "Module{reference=GL, name='génie logiciel', numberHours=30}\n" + //
                "----------------------------------------\n" + //
                "listStudents \n" + //
                "Student{id=1, fullName='sofian gasb', dateBirth=2000-01-08, group=Group{reference=MIAD}}\n" + //
                "Student{id=2, fullName='amine kaci', dateBirth=1999-05-11, group=Group{reference=MSIA}}\n" + //
                "Student{id=3, fullName='hamid jebri', dateBirth=1997-10-26, group=Group{reference=MIAD}}\n" + //
                "Student{id=4, fullName='hanane safi', dateBirth=1995-10-26, group=Group{reference=MSIR}}\n" + //
                "listGroups after adding students\n" + //
                "Group{reference=MIAD , number student=2}\n" + //
                "Group{reference=MSIA , number student=1}\n" + //
                "Group{reference=MSIR , number student=1}\n" + //
                "------------------------------------------\n" + //
                "listTeachers \n" + //
                "Teacher{id=1, fullName='khalifa ahmed', grade=MCA, listModules=[Module{reference=BDA, name='base de donnée avancé', numberHours=40}], listGroup=[Group{reference=MIAD}, Group{reference=MSIA}]}\n"
                + //
                "Teacher{id=2, fullName='brahim gasbi', grade=MCB, listModules=[Module{reference=GL, name='génie logiciel', numberHours=30}, Module{reference=RI, name='réseaux Informatique', numberHours=28}], listGroup=[Group{reference=MSIR}]}\n"
                + //
                "-------------------------------------\n" + //
                "listMarks  \n" + //
                "Mark{student=Student{id=1, fullName='sofian gasb', dateBirth=2000-01-08, group=Group{reference=MIAD}}, mark=15, module=Module{reference=BDA, name='base de donnée avancé', numberHours=40}}\n"
                + //
                "Mark{student=Student{id=1, fullName='sofian gasb', dateBirth=2000-01-08, group=Group{reference=MIAD}}, mark=11, module=Module{reference=CRY, name='cryptographie', numberHours=35}}\n"
                + //
                "Mark{student=Student{id=2, fullName='amine kaci', dateBirth=1999-05-11, group=Group{reference=MSIA}}, mark=10, module=Module{reference=CRY, name='cryptographie', numberHours=35}}\n"
                + //
                "-------------------------------------\n" + //
                "the student which took best mark in CRY is :\n" + //
                "Student{id=1, fullName='sofian gasb', dateBirth=2000-01-08, group=Group{reference=MIAD}}\n" + //
                "-------------------------------------\n" + //
                "list of mark filtered by CRY module\n" + //
                "Mark{student=Student{id=1, fullName='sofian gasb', dateBirth=2000-01-08, group=Group{reference=MIAD}}, mark=11, module=Module{reference=CRY, name='cryptographie', numberHours=35}}\n"
                + //
                "Mark{student=Student{id=2, fullName='amine kaci', dateBirth=1999-05-11, group=Group{reference=MSIA}}, mark=10, module=Module{reference=CRY, name='cryptographie', numberHours=35}}";

        // Remove any trailing whitespace and compare
        assertEquals(expectedOutput.trim(), output.trim());
    }
}