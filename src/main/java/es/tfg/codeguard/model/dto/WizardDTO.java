package es.tfg.codeguard.model.dto;

import es.tfg.codeguard.model.entity.DeadWizard;
import es.tfg.codeguard.model.entity.Spell;
import es.tfg.codeguard.model.entity.Wizard;

import java.util.List;

public class WizardDTO {
    private String wizardName;
    private boolean tester;
    private boolean creator;
//    private List<Spell> spells;

    public WizardDTO(Wizard wizard) {
        setWizardName(wizard.getWizardName());
        setTester(wizard.isTester());
        setCreator(wizard.isCreator());
//        setSpells(wizard.getSpells());
    }

    public WizardDTO(DeadWizard deadWizard) {
        setWizardName(deadWizard.getWizardName());
        setTester(deadWizard.isTester());
        setCreator(deadWizard.isCreator());
//        setSpells(deadWizard.getSpells());
    }


    public String getWizardName() {
        return wizardName;
    }

    public void setWizardName(String wizardName) {
        this.wizardName = wizardName;
    }

    public boolean isTester() {
        return tester;
    }

    public void setTester(boolean tester) {
        this.tester = tester;
    }

    public boolean isCreator() {
        return creator;
    }

    public void setCreator(boolean creator) {
        this.creator = creator;
    }

//    public List<Spell> getSpells() {
//        return spells;
//    }
//
//    public void setSpells(List<Spell> spells) {
//        this.spells = spells;
//    }
}
