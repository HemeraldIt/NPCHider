package it.ohalee.npchider;

import net.citizensnpcs.api.persistence.Persist;
import net.citizensnpcs.api.trait.Trait;
import net.citizensnpcs.api.trait.TraitName;
import net.citizensnpcs.trait.Toggleable;

@TraitName("hide")
public class HideTrait extends Trait implements Toggleable {

    @Persist
    private boolean enabled;

    public HideTrait() {
        super("hide");
    }

    public void playerHide(boolean playerhide) {
        this.enabled = playerhide;
    }

    public boolean hasPlayerHide() {
        return !this.enabled;
    }

    @Override
    public boolean toggle() {
        return this.enabled = !this.enabled;
    }
}