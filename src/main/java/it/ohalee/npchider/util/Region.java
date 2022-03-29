package it.ohalee.npchider.util;

import org.bukkit.Location;

public class Region {

    private final int minX;
    private final int maxX;
    private final int minZ;
    private final int maxZ;
    private final int minY;
    private final int maxY;

    public Region(Location pos1, Location pos2) {
        this.minX = Math.min(pos1.getBlockX(), pos2.getBlockX());
        this.maxX = Math.max(pos1.getBlockX(), pos2.getBlockX());
        this.minY = Math.min(pos1.getBlockY(), pos2.getBlockY());
        this.maxY = Math.max(pos1.getBlockY(), pos2.getBlockY());
        this.minZ = Math.min(pos1.getBlockZ(), pos2.getBlockZ());
        this.maxZ = Math.max(pos1.getBlockZ(), pos2.getBlockZ());
    }

    public boolean isInRegion(final Location location) {
        return location.getBlockX() <= this.maxX && location.getBlockX() >= this.minX && location.getY() <= this.maxY &&
                location.getY() >= this.minY && location.getBlockZ() <= this.maxZ && location.getBlockZ() >= this.minZ;
    }

}