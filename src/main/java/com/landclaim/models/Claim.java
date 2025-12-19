package com.landclaim.models;

import java.util.UUID;

public class Claim {
    private final UUID owner;
    private final String world;
    private final int minX;
    private final int minZ;
    private final int maxX;
    private final int maxZ;

    public Claim(UUID owner, String world, int minX, int minZ, int maxX, int maxZ) {
        this.owner = owner;
        this.world = world;
        this.minX = minX;
        this.minZ = minZ;
        this.maxX = maxX;
        this.maxZ = maxZ;
    }

    public UUID getOwner() {
        return owner;
    }

    public String getWorld() {
        return world;
    }

    public int getMinX() {
        return minX;
    }

    public int getMinZ() {
        return minZ;
    }

    public int getMaxX() {
        return maxX;
    }

    public int getMaxZ() {
        return maxZ;
    }

    public boolean contains(int x, int z) {
        return x >= minX && x <= maxX && z >= minZ && z <= maxZ;
    }

    public boolean overlaps(int otherMinX, int otherMinZ, int otherMaxX, int otherMaxZ) {
        return !(maxX < otherMinX || minX > otherMaxX || maxZ < otherMinZ || minZ > otherMaxZ);
    }

    public int getArea() {
        return (maxX - minX + 1) * (maxZ - minZ + 1);
    }
}

