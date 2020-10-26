package com.xX_deadbush_Xx.hocus.api.util;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.world.biome.Biome;

public class BiomeHelper {

    /*
        Die 3 Zahle in der SpawnListEntry:
        Erste Zahl = Chance zum Spawnen
        Zweite Zahl: Mindest Größe einer Gruppe
        Dritte Zahl: Maximale Größe einer Gruppe
     */
    public static final List<BiomeEntityData> DEFAULT_ENTITIES = Lists.newArrayList(
            new BiomeEntityData(EntityClassification.CREATURE, new Biome.SpawnListEntry(EntityType.SHEEP, 12, 4, 4)),
            new BiomeEntityData(EntityClassification.CREATURE, new Biome.SpawnListEntry(EntityType.PIG, 10, 4, 4)),
            new BiomeEntityData(EntityClassification.CREATURE, new Biome.SpawnListEntry(EntityType.CHICKEN, 10, 4, 4)),
            new BiomeEntityData(EntityClassification.CREATURE, new Biome.SpawnListEntry(EntityType.COW, 8, 4, 4)),
            new BiomeEntityData(EntityClassification.CREATURE, new Biome.SpawnListEntry(EntityType.WOLF, 8, 4, 4)),
            new BiomeEntityData(EntityClassification.CREATURE, new Biome.SpawnListEntry(EntityType.RABBIT, 4, 2, 3)),
            new BiomeEntityData(EntityClassification.CREATURE, new Biome.SpawnListEntry(EntityType.FOX, 8, 2, 4)),
            new BiomeEntityData(EntityClassification.AMBIENT, new Biome.SpawnListEntry(EntityType.BAT, 10, 8, 8)),
            new BiomeEntityData(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.SPIDER, 100, 4, 4)),
            new BiomeEntityData(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.ZOMBIE, 100, 4, 4)),
            new BiomeEntityData(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.SKELETON, 100, 4, 4)),
            new BiomeEntityData(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.ZOMBIE_VILLAGER, 25, 1, 1)),
            new BiomeEntityData(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.CREEPER, 100, 4, 4)),
            new BiomeEntityData(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.SLIME, 100, 4, 4)),
            new BiomeEntityData(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.ENDERMAN, 10, 1, 4)),
            new BiomeEntityData(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.WITCH, 5, 1, 1)));

    public static class BiomeEntityData {

        private EntityClassification entityClassification;
        private Biome.SpawnListEntry spawnListEntry;

        public BiomeEntityData(EntityClassification entityClassification, Biome.SpawnListEntry spawnListEntry) {
            this.entityClassification = entityClassification;
            this.spawnListEntry = spawnListEntry;
        }

        public EntityClassification getEntityClassification() {
            return entityClassification;
        }

        public Biome.SpawnListEntry getSpawnListEntry() {
            return spawnListEntry;
        }
    }

}
