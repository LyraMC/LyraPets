package dev.lyramc.pets;

import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftLivingEntity;
import org.bukkit.entity.ArmorStand;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class LyraArmorStand extends EntityArmorStand {

    private final Location location;
    private final List<UUID> visibilityOfPlayers;

    public LyraArmorStand(World world, Location location) {
        super(world);
        this.location = location;
        this.visibilityOfPlayers = new ArrayList<>();
        applyBasicMethods();
    }

    public void makeHide(List<UUID> players) {
        LyraPetsUtils.showOrHidePlayerTo(this, visibilityOfPlayers, false, players);
    }

    public void makeShow(List<UUID> players) {
        LyraPetsUtils.showOrHidePlayerTo(this, visibilityOfPlayers, true, players);
    }

    private void applyBasicMethods() {
        ((CraftLivingEntity) this.getBukkitEntity()).setRemoveWhenFarAway(false);
        ((CraftLivingEntity) this.getBukkitEntity()).setCanPickupItems(false);
        this.setPosition(location.getX(), location.getY(), location.getZ());
        this.setYawPitch(location.getYaw(), location.getPitch());
    }

    public LyraArmorStand lootBoxApply() {
        invisible(true);
        LyraPetsUtils.NoAI(this);
        LyraPetsUtils.setInvulnerable(this);
        noName();
        return this;
    }

    public LyraArmorStand withName(String name) {
        this.setCustomNameVisible(true);
        this.setCustomName(name);
        return this;
    }

    public LyraArmorStand invisible(boolean flag) {
        this.setInvisible(flag);
        return this;
    }

    public LyraArmorStand noName() {
        this.setCustomNameVisible(false);
        return this;
    }

    public LyraArmorStand gravity(boolean flag) {
        this.setGravity(flag);
        return this;
    }

    public LyraArmorStand withMovableArms(Vector3f leftArm, Vector3f rightArm) {
        this.setLeftArmPose(leftArm);
        this.setRightArmPose(rightArm);
        return this;
    }

    public LyraArmorStand withMovableLegs(Vector3f leftLeg, Vector3f rightLeg) {
        this.setLeftLegPose(leftLeg);
        this.setRightLegPose(rightLeg);
        return this;
    }

    public LyraArmorStand withMovableBody(Vector3f bodyPose) {
        this.setBodyPose(bodyPose);
        return this;
    }

    public LyraArmorStand withEquipment(int value, net.minecraft.server.v1_8_R3.ItemStack itemNMS) {
        this.setEquipment(value, itemNMS);
        return this;
    }

    public LyraArmorStand isSmall(boolean flag) {
        this.setSmall(flag);
        return this;
    }

    public LyraArmorStand setSprint(boolean flag) {
        this.setSprinting(flag);
        return this;
    }

    public LyraArmorStand setSneak(boolean flag) {
        this.setSneaking(flag);
        return this;
    }

    public LyraArmorStand withPassenger(org.bukkit.entity.Entity entity) {

        if(this.getBukkitEntity().getPassenger() != null) {
            this.getBukkitEntity().getPassenger().eject();
            this.getBukkitEntity().getPassenger().remove();
        }

        this.getBukkitEntity().setPassenger(entity);
        return this;
    }

    public ArmorStand asBukkit() {
        return (ArmorStand) this.getBukkitEntity();
    }

    public EntityArmorStand done() {
        world.addEntity(this, CreatureSpawnEvent.SpawnReason.CUSTOM);
        return this;
    }
}