package module;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

public class ModuleServiceTest {

    private ModuleService moduleService;

    @BeforeEach
    public void setUp() {
        moduleService = new ModuleService();
    }

    @Test
    @DisplayName("Test saveModule with valid parameters")
    public void testSaveModuleValid() {
        moduleService.saveModule(ModuleName.BDA, "Big Data Analytics", 40);
        List<Module> modules = moduleService.allModules();
        assertEquals(1, modules.size());
        Module module = modules.get(0);
        assertEquals(ModuleName.BDA, module.getReference());
        assertEquals("Big Data Analytics", module.getName());
        assertEquals(Integer.valueOf(40), module.getNumberHours());
    }

    @Test
    @DisplayName("Test saveModule with null parameters")
    public void testSaveModuleWithNullParameters() {
        moduleService.saveModule(null, null, null);
        List<Module> modules = moduleService.allModules();
        assertEquals(1, modules.size());
        Module module = modules.get(0);
        assertNull(module.getReference());
        assertNull(module.getName());
        assertNull(module.getNumberHours());
    }

    @Test
    @DisplayName("Test allModules method")
    public void testAllModules() {
        moduleService.saveModule(ModuleName.BDA, "Big Data Analytics", 40);
        moduleService.saveModule(ModuleName.CRY, "Cryptography", 30);
        List<Module> modules = moduleService.allModules();
        assertEquals(2, modules.size());
    }

    @Test
    @DisplayName("Test findByReference with existing reference")
    public void testFindByReferenceExisting() {
        moduleService.saveModule(ModuleName.BDA, "Big Data Analytics", 40);
        moduleService.saveModule(ModuleName.CRY, "Cryptography", 30);
        Module module = moduleService.findByReference("BDA");
        assertNotNull(module);
        assertEquals(ModuleName.BDA, module.getReference());
    }

    @Test
    @DisplayName("Test findByReference with non-existing reference")
    public void testFindByReferenceNonExisting() {
        moduleService.saveModule(ModuleName.BDA, "Big Data Analytics", 40);
        Module module = moduleService.findByReference("NON_EXISTENT");
        assertNull(module);
    }

    @Test
    @DisplayName("Test findByReference with null reference")
    public void testFindByReferenceNull() {
        moduleService.saveModule(ModuleName.BDA, "Big Data Analytics", 40);
        Module module = moduleService.findByReference(null);
        assertNull(module);
    }

    @Test
    @DisplayName("Test findByReference with null module reference")
    public void testFindByReferenceNullModuleReference() {
        moduleService.saveModule(null, "No Reference Module", 20);
        Module module = moduleService.findByReference(null);
        assertNull(module);  // Expect null since there's no reference match with null
    }

}
