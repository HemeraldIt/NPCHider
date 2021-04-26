package it.ohalee.npchider;

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

public final class NPCHider extends JavaPlugin implements Listener {

    private final List<UUID> players = new ArrayList<>();
    private TraitInfo info;

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
        info = TraitInfo.create(HideTrait.class);
        CitizensAPI.getTraitFactory().registerTrait(info);
    }

    @Override
    public void onDisable() {
        CitizensAPI.getTraitFactory().deregisterTrait(info);
    }

    @EventHandler
    public void onCollision(PlayerMoveEvent e) {
        Player player = e.getPlayer();

        if(e.getTo().getY() < -20) {
            Location loc = player.getWorld().getSpawnLocation().clone();
            loc.setYaw(90);
            player.teleport(loc);
            return;
        }

        List<Entity> entities = player.getNearbyEntities(2,2,2);
        for (Entity en : entities) {
            if (CitizensAPI.getNPCRegistry().isNPC(en)) {
                NPC npc = CitizensAPI.getNPCRegistry().getNPC(en);
                if (npc.hasTrait(HideTrait.class)) {
                    // se è > di 1.5 player visibili
                    // se è < di 1.5 il player diventa invisibile
                    boolean b = player.getLocation().distance(npc.getStoredLocation()) >= 2.5;
                    if (b) {
                        // visibile
                        players.remove(player.getUniqueId());

                        for (Player online : Bukkit.getOnlinePlayers()) {
                            if (online != player) {
                                online.showPlayer(this, player);
                            }
                        }
                    }
                    else {
                        // invisibile
                        if (!players.contains(player.getUniqueId())) {
                            players.add(player.getUniqueId());
                        }

                        for (Player online : Bukkit.getOnlinePlayers()) {
                            if (online != player) {
                                online.hidePlayer(this, player);
                            }
                        }
                    }
                }
            }
        }
    }
}