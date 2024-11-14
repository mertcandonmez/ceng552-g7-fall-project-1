package module;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ModuleTest {

    private ModuleName reference;
    private String name;
    private Integer numberHours;

    @Before
    public void setUp() {
        reference = ModuleName.BDA;
        name = "Big Data Analytics";
        numberHours = 40;
    }

    @Test
    public void testConstructorValid() {
        Module module = new Module(reference, name, numberHours);
        assertNotNull(module);
        assertEquals(reference, module.getReference());
        assertEquals(name, module.getName());
        assertEquals(numberHours, module.getNumberHours());
    }

    @Test
    public void testConstructorWithNullParameters() {
        Module module = new Module(null, null, null);
        assertNotNull(module);
        assertNull(module.getReference());
        assertNull(module.getName());
        assertNull(module.getNumberHours());
    }

    @Test
    public void testConstructorBoundaryValues() {
        // Testing with 0 hours
        Module module = new Module(reference, name, 0);
        assertEquals(Integer.valueOf(0), module.getNumberHours());

        // Testing with maximum integer hours
        module = new Module(reference, name, Integer.MAX_VALUE);
        assertEquals(Integer.valueOf(Integer.MAX_VALUE), module.getNumberHours());
    }

    @Test
    public void testConstructorEmptyNameAndReference() {
        Module module = new Module(ModuleName.CRY, "", 10);
        assertEquals("", module.getName());
        assertEquals(ModuleName.CRY, module.getReference());
    }

    @Test
    public void testGetMethods() {
        Module module = new Module(reference, name, numberHours);
        assertEquals(reference, module.getReference());
        assertEquals(name, module.getName());
        assertEquals(numberHours, module.getNumberHours());
    }

    @Test
    public void testToString() {
        Module module = new Module(reference, name, numberHours);
        String expected = "Module{reference=" + reference + ", name='" + name + "', numberHours=" + numberHours + "}";
        assertEquals(expected, module.toString());
    }
}
