# LyraPets


- Create Customized Entities with NBT, PathFinders...
- Working from 1.8.x to 1.16.x (class imports on 1.8 actually)
- Highly customizable
- Easy to use
- Multiple entity interfaces usages (any LivingEntity)

## Manual

Copy ```LyraPet.java``` and ```LyraPetsUtils.java``` in your project

## Usage

Now you can create easily Entities by instancing a new LyraPet object like this.

```java
Location location = /* your location */;
LyraPet<EntityVillager> pet = new LyraPet<EntityVillager>(EntityType.VILLAGER, loc);
```

## Customization
Just by looking the code of ```LyraPet.java``` you can see all the methods available to customize your Entities like:

- MetaData 💾
- Display Name 📋
- Visibility 🕯️
- Amount 🤏🏽
- Invicibility 📊
- Much more ...

There are more methods that can be used with NMSClass or BukkitEntity
