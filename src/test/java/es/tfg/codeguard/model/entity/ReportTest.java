package es.tfg.codeguard.model.entity;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;

import es.tfg.codeguard.model.entity.exercisereport.ExerciseReport;

@SpringBootTest
public class ReportTest {

    private ExerciseReport report;

    @BeforeEach
    void setUp() {
        report = new ExerciseReport();
    }

    @Test
    void defaultConstructor() {
        assertNotNull(report);
        assertEquals("", report.getAdminResponse());
        assertFalse(report.isResolved());
    }

    @Test
    void partialConstructor() {
        ExerciseReport partialReport = new ExerciseReport("Valid description", "User123");
        assertEquals("Valid description", partialReport.getDescription());
        assertEquals("User123", partialReport.getUserName());
        assertEquals("", partialReport.getAdminResponse());
        assertNull(partialReport.getAdminName());
        assertFalse(partialReport.isResolved());
    }

    @Test
    void fullConstructor() {
        ExerciseReport fullReport = new ExerciseReport(
            "Description example",
            "Admin response example",
            "User123",
            "Admin456",
            true
        );
        assertEquals("Description example", fullReport.getDescription());
        assertEquals("Admin response example", fullReport.getAdminResponse());
        assertEquals("User123", fullReport.getUserName());
        assertEquals("Admin456", fullReport.getAdminName());
        assertTrue(fullReport.isResolved());
    }

    @ParameterizedTest
    @ValueSource(strings = {"Here is a desc for an example error", "Hello I have a problem", "Help meeeeee"}) 
    void validDescription(String exampleText) {
        assertDoesNotThrow(() -> report.setDescription(exampleText));
        assertEquals(exampleText, report.getDescription());
    }

    @Test
    void notValidDescription() {
        assertThrows(IllegalArgumentException.class, () -> report.setDescription(null));
        assertThrows(IllegalArgumentException.class, () -> report.setDescription(""));
    }

    @Test
    void setAndGetAdminResponse() {
        report.setAdminResponse("This is the admin response.");
        assertEquals("This is the admin response.", report.getAdminResponse());
    }

    @Test
    void setAndGetResolved() {
        report.setResolved(true);
        assertTrue(report.isResolved());

        report.setResolved(false);
        assertFalse(report.isResolved());
    }

    @Test
    void setAndGetUserName() {
        report.setUserName("User123");
        assertEquals("User123", report.getUserName());
    }

    @Test
    void notValidUserName() {
        assertThrows(IllegalArgumentException.class, () -> report.setUserName(null));
        assertThrows(IllegalArgumentException.class, () -> report.setUserName(""));
    }

    @Test
    void setAndGetAdminName() {
        report.setAdminName("Admin456");
        assertEquals("Admin456", report.getAdminName());

        report.setAdminName(null);
        assertNull(report.getAdminName());
    }

    @Test
    void createReportAndVerifyDefaultValues() {
        ExerciseReport defaultReport = new ExerciseReport();
        assertEquals("", defaultReport.getAdminResponse());
        assertFalse(defaultReport.isResolved());
        assertNull(defaultReport.getDescription());
        assertNull(defaultReport.getUserName());
        assertNull(defaultReport.getAdminName());
    }
}
