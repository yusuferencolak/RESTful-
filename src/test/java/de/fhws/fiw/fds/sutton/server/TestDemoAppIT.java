package de.fhws.fiw.fds.sutton.server;


import com.github.javafaker.Faker;
import de.fhws.fiw.fds.suttondemo.client.models.UniversityClientModel;
import de.fhws.fiw.fds.suttondemo.client.models.ModuleClientModel;
import de.fhws.fiw.fds.suttondemo.client.rest.DemoRestClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestDemoAppIT {
    final private Faker faker = new Faker();
    private DemoRestClient client;

    @BeforeEach
    public void setUp() throws IOException {
        this.client = new DemoRestClient();
        this.client.resetDatabase();
    }

    @Test
    public void test_dispatcher_is_available() throws IOException {
        client.start();
        assertEquals(200, client.getLastStatusCode());
    }

    @Test
    public void test_dispatcher_is_get_all_universities_allowed() throws IOException {
        client.start();
        assertTrue(client.isGetAllUniversitiesAllowed());
    }

    @Test
    public void test_create_university_is_create_university_allowed() throws IOException {
        client.start();
        assertTrue(client.isCreateUniversityAllowed());
    }

    @Test
    public void testCreateModule() throws IOException {
        client.start();

        var module = new ModuleClientModel();
        module.setModuleName("Introduction to Quantum Computing");
        module.setSemester(1);
        module.setCreditPoints(5);

        client.createModule(module);
        assertEquals(201, client.getLastStatusCode());
    }

    @Test
    public void testGetSingleModule() throws IOException {
        client.start();

        var module = new ModuleClientModel();
        module.setModuleName("Introduction to Quantum Computing");
        module.setSemester(1);
        module.setCreditPoints(5);

        client.createModule(module);
        assertEquals(201, client.getLastStatusCode());

        assertTrue(client.isGetSingleModuleAllowed());

        client.getSingleModule();
        assertEquals(200, client.getLastStatusCode());

        var moduleFromServer = client.moduleData().getFirst();
        assertEquals("Introduction to Quantum Computing", moduleFromServer.getModuleName());
        assertEquals(1, moduleFromServer.getSemester());
        assertEquals(5, moduleFromServer.getCreditPoints());
    }

    @Test
    public void testGetAllModules() throws IOException {
        // Create 5 modules
        for (int i = 0; i < 5; i++) {
            client.start();

            var module = new ModuleClientModel();
            module.setModuleName("Introduction to Quantum Computing");
            module.setSemester(1);
            module.setCreditPoints(5);

            client.createModule(module);
            assertEquals(201, client.getLastStatusCode());
        }

        // Retrieve all modules
        client.start();
        assertTrue(client.isGetAllModulesAllowed());

        client.getAllModules();
        assertEquals(200, client.getLastStatusCode());
        assertEquals(5, client.moduleData().size());
    }
}