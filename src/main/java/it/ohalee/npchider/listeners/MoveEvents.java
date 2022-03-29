package it.ohalee.npchider.listeners;

import it.ohalee.npchider.NPCHider;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class MoveEvents implements Listener {

    private final NPCHider plugin;

    public MoveEvents(NPCHider plugin) {
        this.plugin = plugin;
    }

    private void playerCheck(Player player, Location location) {
        UUID uniqueId = player.getUniqueId();

        if (this.plugin.getRegion().isInRegion(location)) {
            // Nella region -> sta entrando

            // Non è nascosto quindi lo nascondiamo
            if (!this.plugin.getInZone().contains(uniqueId)) {
                this.plugin.getInZone().add(uniqueId);

                if (Bukkit.getPluginManager().getPlugin("ProCosmetics") != null) {
                    se.file14.procosmetics.api.ProCosmeticsAPI.getUser(player).unequipCosmetics(true);
                }

                // Hide
                for (Player online : Bukkit.getOnlinePlayers()) {
                    if (online != player) {
                        online.hidePlayer(this.plugin, player);
                    }
                }
            }
        } else {
            // Fuori dalla region -> sta uscendo

            // E' nascosto quindi lo rendiamo visibile
            if (this.plugin.getInZone().contains(uniqueId)) {
                this.plugin.getInZone().remove(uniqueId);

                if (Bukkit.getPluginManager().getPlugin("ProCosmetics") != null) {
                    se.file14.procosmetics.api.ProCosmeticsAPI.getUser(player).equipLastCosmetics(true);
                }

                // Show
                for (Player online : Bukkit.getOnlinePlayers()) {
                    if (online != player) {
                        online.showPlayer(this.plugin, player);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        this.playerCheck(player, e.getTo());
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent e) {
        Player player = e.getPlayer();
        this.playerCheck(player, e.getTo());
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();

        // Nascondo tutti i player che sono nella zona a chi entra
        for (UUID uniqueId : this.plugin.getInZone()) {
            Player inZone = Bukkit.getPlayer(uniqueId);
            if (inZone != null) {
                player.hidePlayer(this.plugin, inZone);
            }
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        this.plugin.getInZone().remove(player.getUniqueId());
    }

}
