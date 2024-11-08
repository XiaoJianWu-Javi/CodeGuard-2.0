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

class WizardTests {

    private Wizard saruman;

    @BeforeEach
    void setup() {
        saruman = new Wizard();
    }

    @Test
    void notValidSetWizardName() {
        assertThrows(IllegalArgumentException.class, () -> saruman.setWizardName(null));
        assertThrows(IllegalArgumentException.class, () -> saruman.setWizardName(""));
        assertThrows(IllegalArgumentException.class, () -> saruman.setWizardName("   "));
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
    void isAndSetTesterOrCreator() {
        assertFalse(saruman.isTester());    //Not Initialized
        assertFalse(saruman.isCreator());   //Not Initialized

        saruman.setTester(true);
        assertTrue(saruman.isTester());
        assertFalse(saruman.isCreator());

        saruman.setCreator(true);
        saruman.setTester(false);
        assertFalse(saruman.isTester());
        assertTrue(saruman.isCreator());

        saruman.setTester(true);
        assertTrue(saruman.isTester());
        assertTrue(saruman.isCreator());
    }

    @Test
    void setSpellsTests() {
        java.util.List<Spell> spells = new java.util.ArrayList<>() {{
            add(null); add(new Spell()); add(new Spell());
        }};
        assertThrows(IllegalArgumentException.class, () -> saruman.setSpells(spells));

        spells.clear();
        spells.add(new Spell());
        assertDoesNotThrow(() -> saruman.setSpells(spells));
    }

    @Test
    void getSpellsTests() {
        assertTrue(saruman.getSpells().isEmpty());  //Not Initialized

        java.util.List<Spell> spells = new java.util.ArrayList<>() {{
            add(new Spell()); add(new Spell()); add(new Spell());
        }};
        saruman.setSpells(spells);
        assertArrayEquals(spells.toArray(), saruman.getSpells().toArray());
        assertEquals(spells.size(), saruman.getSpells().size());

        saruman.getSpells().clear();
        assertArrayEquals(spells.toArray(), saruman.getSpells().toArray());
        assertEquals(spells.size(), saruman.getSpells().size());
    }
}
