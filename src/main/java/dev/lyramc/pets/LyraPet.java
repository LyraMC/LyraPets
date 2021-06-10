package dev.lyramc.pets;

import dev.lyramc.hub.LyraHub;
import net.minecraft.server.v1_8_R3.EntityLiving;
import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftLivingEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class LyraPet<E extends EntityLiving> {

    private final E relativeNMSClass;
    private final Entity relativeBukkitEntity;
    private final List<UUID> visibilityOfPlayers;

    public LyraPet(EntityType entityType, Location location) {
        this.visibilityOfPlayers = new ArrayList<>();
        this.relativeBukkitEntity = location.getWorld().spawnEntity(location, entityType);
        this.relativeNMSClass = (E) ((CraftEntity) this.relativeBukkitEntity).getHandle();

        ((CraftLivingEntity) relativeBukkitEntity).setRemoveWhenFarAway(false);
        ((CraftLivingEntity) relativeBukkitEntity).setCanPickupItems(false);
        LyraPetsUtils.pets.add(this);
    }

    public dev.lyramc.hub.api.lyra.entities.LyraPet<E> invincible() {
        LyraPetsUtils.setInvulnerable(relativeNMSClass);
        LyraPetsUtils.NoAI(relativeNMSClass);
        return this;
    }

    public void makeHide(List<UUID> players) {
        LyraPetsUtils.showOrHidePlayerTo(relativeNMSClass, visibilityOfPlayers, false, players);
    }

    public void makeShow(List<UUID> players) {
        LyraPetsUtils.showOrHidePlayerTo(relativeNMSClass, visibilityOfPlayers, true, players);
    }

    public dev.lyramc.hub.api.lyra.entities.LyraPet<E> setName(final String name) {
        this.relativeBukkitEntity.setCustomNameVisible(true);
        this.relativeBukkitEntity.setCustomName(name);
        return this;
    }

    public dev.lyramc.hub.api.lyra.entities.LyraPet<E> noName() {
        this.relativeBukkitEntity.setCustomNameVisible(false);
        return this;
    }

    public dev.lyramc.hub.api.lyra.entities.LyraPet<E> setMetaData(String metaData, Object relative) {
        this.relativeBukkitEntity.setMetadata(metaData, new FixedMetadataValue(LyraHub.getInstance(), relative));
        return this;
    }

    public dev.lyramc.hub.api.lyra.entities.LyraPet<E> playEffect(EntityEffect effect) {
        this.relativeBukkitEntity.playEffect(effect);
        return this;
    }

    public dev.lyramc.hub.api.lyra.entities.LyraPet<E> invisible(boolean flag) {
        this.relativeNMSClass.setInvisible(flag);
        return this;
    }

    public dev.lyramc.hub.api.lyra.entities.LyraPet<E> sneaking(boolean flag) {
        this.relativeNMSClass.setSneaking(flag);
        return this;
    }

    public dev.lyramc.hub.api.lyra.entities.LyraPet<E> sprinting(boolean flag) {
        this.relativeNMSClass.setSprinting(flag);
        return this;
    }

    public List<UUID> getVisibilityOfPlayers() {
        return visibilityOfPlayers;
    }

    public E getRelativeNMSClass() {
        return relativeNMSClass;
    }

    public Entity getRelativeBukkitEntity() {
        return relativeBukkitEntity;
    }
}