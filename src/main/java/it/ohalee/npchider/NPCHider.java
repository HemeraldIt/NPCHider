package it.ohalee.npchider;

import it.ohalee.npchider.citizens.HideTrait;
import it.ohalee.npchider.listeners.MoveEvents;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.trait.TraitInfo;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class NPCHider extends JavaPlugin {

    private TraitInfo info;

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(new MoveEvents(this), this);

        this.info = TraitInfo.create(HideTrait.class);

        CitizensAPI.getTraitFactory().registerTrait(this.info);
    }

    @Override
    public void onDisable() {
        CitizensAPI.getTraitFactory().deregisterTrait(this.info);
    }

}