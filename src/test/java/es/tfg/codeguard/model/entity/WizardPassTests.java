package es.tfg.codeguard.model.entity;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WizardPassTests {

    private WizardPass saruman;

    @BeforeEach
    void setup() {
        saruman = new WizardPass();
    }

    @Test
    void notValidSetWizardName() {
        assertThrows(IllegalArgumentException.class, () -> saruman.setWizardName(null));
        assertThrows(IllegalArgumentException.class, () -> saruman.setWizardName(""));
        assertThrows(IllegalArgumentException.class, () -> saruman.setWizardName("   "));
        assertThrows(IllegalArgumentException.class, () -> saruman.setWizardName("\n"));
        assertThrows(IllegalArgumentException.class, () -> saruman.setWizardName("a"));
        assertThrows(IllegalArgumentException.class, () -> saruman.setWizardName("a.aa2"));
        assertThrows(IllegalArgumentException.class, () -> saruman.setWizardName("2213"));
        assertThrows(IllegalArgumentException.class, () -> saruman.setWizardName("a??asda"));
        assertThrows(IllegalArgumentException.class, () -> saruman.setWizardName("a<a223e"));
        assertThrows(IllegalArgumentException.class, () -> saruman.setWizardName("a1122"));
        assertThrows(IllegalArgumentException.class, () -> saruman.setWizardName("1aaa"));
        assertThrows(IllegalArgumentException.class, () -> saruman.setWizardName("aa324a1"));
        assertThrows(IllegalArgumentException.class, () -> saruman.setWizardName("a1231aa1"));
    }
    
    @Test
    void validSetWizardName() {
        assertDoesNotThrow(() -> saruman.setWizardName("aaa1"));
        assertDoesNotThrow(() -> saruman.setWizardName("aaa1aaa43535"));
        assertDoesNotThrow(() -> saruman.setWizardName("AAA1"));
        assertDoesNotThrow(() -> saruman.setWizardName("Aaa1"));
        assertDoesNotThrow(() -> saruman.setWizardName("aaAA321"));
        assertDoesNotThrow(() -> saruman.setWizardName("aaa1fewes"));
        assertDoesNotThrow(() -> saruman.setWizardName("sarumanName"));
        assertDoesNotThrow(() -> saruman.setWizardName("reaaa"));
    }

    @Test
    void getNotInitializedWizardName() {
        assertThrows(NoSuchElementException.class, () -> saruman.getWizardName());
        try { saruman.setWizardName(""); } catch(Exception e) {}
        assertThrows(NoSuchElementException.class, () -> saruman.getWizardName());
    }

    @Test
    void getInitializedWizardName() {
        String sarumanName = "saruman";

        saruman.setWizardName(sarumanName);
        assertEquals(sarumanName, saruman.getWizardName());
        try { saruman.setWizardName(""); } catch(Exception e) {}
        assertEquals(sarumanName, saruman.getWizardName());
    }

    @Test
    void isAndSetElder() {
        assertFalse(saruman.isElder());    //Not Initialized

        saruman.setElder(true);
        assertTrue(saruman.isElder());

        saruman.setElder(false);
        assertFalse(saruman.isElder());
    }

    @Test
    void notValidSetHashedPass() {
        assertThrows(IllegalArgumentException.class, () -> saruman.setHashedPass(null));
        assertThrows(IllegalArgumentException.class, () -> saruman.setHashedPass(""));
        assertThrows(IllegalArgumentException.class, () -> saruman.setHashedPass("   "));
        assertThrows(IllegalArgumentException.class, () -> saruman.setHashedPass("\n"));
    }

    @Test
    void validSetHashedPass() {
        assertDoesNotThrow(() -> saruman.setWizardName("11111111111111"));
        assertDoesNotThrow(() -> saruman.setWizardName("83874fo8w7ycoiureovq'¡'¡´+´12+`3ç"));
    }

    @Test
    void getNotInitializedHashedPass() {
        assertThrows(NoSuchElementException.class, () -> saruman.getHashedPass());
        try { saruman.setHashedPass(""); } catch(Exception e) {}
        assertThrows(NoSuchElementException.class, () -> saruman.getHashedPass());
    }

    @Test
    void getInitializedHashedPass() {
        String hashedPass = "s4rum4n";

        saruman.setHashedPass(hashedPass);
        assertEquals(hashedPass, saruman.getHashedPass());
        try { saruman.setHashedPass(""); } catch(Exception e) {}
        assertEquals(hashedPass, saruman.getHashedPass());
    }
}
