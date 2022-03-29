package it.ohalee.npchider;

import it.ohalee.npchider.listeners.MoveEvents;
import it.ohalee.npchider.util.Region;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public final class NPCHider extends JavaPlugin {

    private final Set<UUID> inZone = new HashSet<>();
    private Region region;

    @Override
    public void onEnable() {
        World w = Bukkit.getWorlds().get(0);
        Location pos1 = new Location(w, 24, 64, 13);
        Location pos2 = new Location(w, 32, 54, -6);

        this.region = new Region(pos1, pos2);

        Bukkit.getPluginManager().registerEvents(new MoveEvents(this), this);

        //    this.info = TraitInfo.create(HideTrait.class);

        //    CitizensAPI.getTraitFactory().registerTrait(this.info);
    }

    @Override
    public void onDisable() {
        //    CitizensAPI.getTraitFactory().deregisterTrait(this.info);
    }

    public Region getRegion() {
        return region;
    }

    public Set<UUID> getInZone() {
        return inZone;
    }
}