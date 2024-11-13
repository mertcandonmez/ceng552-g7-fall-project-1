package module;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ModuleTest {

    private ModuleName reference;
    private String name;
    private Integer numberHours;

    @BeforeEach
    public void setUp() {
        reference = ModuleName.BDA;
        name = "Big Data Analytics";
        numberHours = 40;
    }

    @Test
    @DisplayName("Test constructor with valid parameters")
    public void testConstructorValid() {
        Module module = new Module(reference, name, numberHours);
        assertNotNull(module);
        assertEquals(reference, module.getReference());
        assertEquals(name, module.getName());
        assertEquals(numberHours, module.getNumberHours());
    }

    @Test
    @DisplayName("Test constructor with null parameters")
    public void testConstructorWithNullParameters() {
        Module module = new Module(null, null, null);
        assertNotNull(module);
        assertNull(module.getReference());
        assertNull(module.getName());
        assertNull(module.getNumberHours());
    }

    @Test
    @DisplayName("Test getter methods")
    public void testGetMethods() {
        Module module = new Module(reference, name, numberHours);
        assertEquals(reference, module.getReference());
        assertEquals(name, module.getName());
        assertEquals(numberHours, module.getNumberHours());
    }

    @Test
    @DisplayName("Test toString method")
    public void testToString() {
        Module module = new Module(reference, name, numberHours);
        String expected = "Module{reference=" + reference + ", name='" + name + "', numberHours=" + numberHours + "}";
        assertEquals(expected, module.toString());
    }
}
