package es.tfg.codeguard.model.entity;

import java.util.List;
import java.util.ArrayList;
import java.util.NoSuchElementException;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Wizard {

    private static final String WIZARDNAME_REGEXP = "^[a-zA-Z]{3,}\\w*$";
    
    @Id
    @NotBlank
    @Pattern(regexp = WIZARDNAME_REGEXP, message = "{wizardname.invalidPattern}")
    private String wizardName;

    private Boolean tester;
    private Boolean creator;

    //private List<Spell> spells;

    public Wizard() {
        setTester(false);
        setCreator(false);
        //setSpells(new ArrayList<>());
    }

    public String getWizardName() {
        if (wizardName == null) throw new NoSuchElementException();
        return wizardName;
    }

    public void setWizardName(String wizardName) {
        if (wizardName == null || wizardName.isBlank()) throw new IllegalArgumentException();
        if (!wizardName.matches(WIZARDNAME_REGEXP)) throw new IllegalArgumentException();
        this.wizardName = wizardName;
    }

    public Boolean isTester() {
        return tester;
    }

    public void setTester(Boolean tester) {
        this.tester = tester;
    }

    public Boolean isCreator() {
        return creator;
    }

    public void setCreator(Boolean creator) {
        this.creator = creator;
    }

//    public List<Spell> getSpells() {
//        return new ArrayList<>(spells);
//    }
//
//    public void setSpells(List<Spell> spells) {
//        checkSpells(spells);
//        this.spells = spells;
//    }

    private void checkSpells(List<Spell> spells) {
        for (Spell spell : spells) if (spell == null) throw new IllegalArgumentException();
    }
}
