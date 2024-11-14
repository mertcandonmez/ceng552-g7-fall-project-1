package module;


import java.util.List;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ModuleServiceTest {

    private ModuleService moduleService;

    @Before
    public void setUp() {
        moduleService = new ModuleService();
    }

    @Test
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
    public void testSaveModuleBoundaryValues() {
        // Test with 0 hours - lower boundary
        moduleService.saveModule(ModuleName.CRY, "Cryptography", 0);
        List<Module> modules = moduleService.allModules();
        Module module = modules.get(0);
        assertEquals(Integer.valueOf(0), module.getNumberHours());

        // Test with maximum integer hours - upper boundary
        moduleService.saveModule(ModuleName.CRY, "Cryptography", Integer.MAX_VALUE);
        modules = moduleService.allModules();
        assertEquals(Integer.valueOf(Integer.MAX_VALUE), modules.get(1).getNumberHours());
    }

    @Test
    public void testSaveModuleEmptyNameAndReference() {
        moduleService.saveModule(ModuleName.CRY, "", 10);
        List<Module> modules = moduleService.allModules();
        assertEquals(1, modules.size());
        assertEquals("", modules.get(0).getName());
    }

    @Test
    public void testAllModules() {
        moduleService.saveModule(ModuleName.BDA, "Big Data Analytics", 40);
        moduleService.saveModule(ModuleName.CRY, "Cryptography", 30);
        List<Module> modules = moduleService.allModules();
        assertEquals(2, modules.size());
    }

    @Test
    public void testFindByReferenceExisting() {
        moduleService.saveModule(ModuleName.BDA, "Big Data Analytics", 40);
        Module module = moduleService.findByReference("BDA");
        assertNotNull(module);
        assertEquals(ModuleName.BDA, module.getReference());
    }

    @Test
    public void testFindByReferenceNonExisting() {
        moduleService.saveModule(ModuleName.BDA, "Big Data Analytics", 40);
        Module module = moduleService.findByReference("NON_EXISTENT");
        assertNull(module);
    }

    @Test
    public void testFindByReferenceNull() {
        moduleService.saveModule(ModuleName.BDA, "Big Data Analytics", 40);
        Module module = moduleService.findByReference(null);
        assertNull(module);
    }

    @Test
    public void testSaveLargeNumberOfModules() {
        for (int i = 0; i < 1000; i++) {
            moduleService.saveModule(ModuleName.CRY, "Module " + i, i);
        }
        List<Module> modules = moduleService.allModules();
        assertEquals(1000, modules.size());
    }
}
