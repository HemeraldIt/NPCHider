package it.ohalee.npchider.listeners;

import it.ohalee.npchider.NPCHider;
import it.ohalee.npchider.citizens.HideTrait;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.*;

public class MoveEvents implements Listener {

    private final NPCHider plugin;
    private final double distance = 2.5;
    private final double radius = 2.0;

    public MoveEvents(NPCHider plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player player = e.getPlayer();

        for (Entity en : player.getNearbyEntities(this.radius, this.radius, this.radius)) {
            if (CitizensAPI.getNPCRegistry().isNPC(en)) {
                NPC npc = CitizensAPI.getNPCRegistry().getNPC(en);
                if (npc.hasTrait(HideTrait.class)) {
                    if (player.getLocation().distance(npc.getStoredLocation()) >= this.distance) {
                        // Diventa Visibile

                        for (Entity pass : player.getPassengers()) {
                            if (pass.getType() == EntityType.PLAYER) {
                                player.removePassenger(pass);
                            }
                        }

                        player.removePotionEffect(PotionEffectType.INVISIBILITY);

                        // TODO: 24/01/2022 Attivare gadgets
                    }
                    else {
                        // Diventa Invisibile

                        for (Entity pass : player.getPassengers()) {
                            if (pass.getType() == EntityType.PLAYER) {
                                player.removePassenger(pass);
                            }
                        }

                        player.setCollidable(false);
                        player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 6000, 0, false, false));

                        // TODO: 24/01/2022 Disattivare gadgets
                    }
                }
            }
        }
    }

}
