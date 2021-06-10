package dev.lyramc.pets;

import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.util.UnsafeList;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class LyraPetsUtils {

    public static final List<LyraPet> pets = new ArrayList<>();

    private LyraPetsUtils() {}

    public static void unregisterBukkitEntities() {
        pets.forEach(l -> l.getRelativeBukkitEntity().remove());
    }

    public static void setInvulnerable(EntityLiving relativeNMSClass) {
        NBTTagCompound compound = createNBTTagCompound(relativeNMSClass);
        relativeNMSClass.c(compound);
        compound.set("Invulnerable", new NBTTagByte((byte) 1));
        relativeNMSClass.f(compound);
    }

    public static void NoAI(EntityLiving relativeNMSClass) {
        NBTTagCompound compound = createNBTTagCompound(relativeNMSClass);
        relativeNMSClass.c(compound);
        compound.setInt("NoAI", 1);
        relativeNMSClass.f(compound);
    }

    private static NBTTagCompound createNBTTagCompound(EntityLiving relativeNMSClass) {
        NBTTagCompound compound = relativeNMSClass.getNBTTag();
        if(compound == null) return new NBTTagCompound();
        return null;
    }

    public static void clearPathFinderGoals(PathfinderGoalSelector goalSelector, PathfinderGoalSelector targetSelector) {
        try {
            Field bField = PathfinderGoalSelector.class.getDeclaredField("b");
            bField.setAccessible(true);

            Field cField = PathfinderGoalSelector.class.getDeclaredField("c");
            cField.setAccessible(true);

            bField.set(goalSelector, new UnsafeList<PathfinderGoalSelector>());
            bField.set(targetSelector, new UnsafeList<PathfinderGoalSelector>());
            cField.set(goalSelector, new UnsafeList<PathfinderGoalSelector>());
            cField.set(targetSelector, new UnsafeList<PathfinderGoalSelector>());

        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    public static void moveEntity(EntityLiving relativeBukkitClass, Location to, float speed) {
        relativeBukkitClass.getBukkitEntity().setVelocity(to.toVector().subtract(relativeBukkitClass.getBukkitEntity().getLocation().toVector()).normalize().multiply(speed));
    }

    public static void showOrHidePlayerTo(EntityLiving relativeNMSClass, List<UUID> visibility, boolean isShow, List<UUID> players) {
        if(isShow) {
            players.forEach(p -> {
                visibility.add(p);
                PacketPlayOutSpawnEntityLiving packet = new PacketPlayOutSpawnEntityLiving(relativeNMSClass);
                ((CraftPlayer) Bukkit.getPlayer(p)).getHandle().playerConnection.sendPacket(packet);
            });
        } else {
            players.forEach(p -> {
                visibility.remove(p);
                PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(relativeNMSClass.getId());
                ((CraftPlayer) Bukkit.getPlayer(p)).getHandle().playerConnection.sendPacket(packet);
            });
        }
    }

    public void registerEntity(String name, int id, Class<? extends EntityInsentient> nmsClass, Class<? extends EntityInsentient> customClass){
        try {

            List<Map<?, ?>> dataMap = new ArrayList<Map<?, ?>>();
            for (Field f : EntityTypes.class.getDeclaredFields()){
                if (f.getType().getSimpleName().equals(Map.class.getSimpleName())){
                    f.setAccessible(true);
                    dataMap.add((Map<?, ?>) f.get(null));
                }
            }

            if (dataMap.get(2).containsKey(id)){
                dataMap.get(0).remove(name);
                dataMap.get(2).remove(id);
            }

            Method method = EntityTypes.class.getDeclaredMethod("a", Class.class, String.class, int.class);
            method.setAccessible(true);
            method.invoke(null, customClass, name, id);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static org.bukkit.inventory.ItemStack createEgg(String name, short id) {
        org.bukkit.inventory.ItemStack it = new ItemStack(Material.MONSTER_EGG, 1, id);
        ItemMeta meta = it.getItemMeta();
        meta.setDisplayName(name);
        it.setItemMeta(meta);
        return it;
    }

}

























