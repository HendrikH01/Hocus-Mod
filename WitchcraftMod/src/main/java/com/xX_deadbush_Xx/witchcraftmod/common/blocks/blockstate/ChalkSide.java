package com.xX_deadbush_Xx.witchcraftmod.common.blocks.blockstate;

import net.minecraft.util.IStringSerializable;

public enum ChalkSide implements IStringSerializable {
   UP("up"),
   SIDE("side"),
   NONE("none");

   private final String name;

   private ChalkSide(String name) {
      this.name = name;
   }

   public String toString() {
      return this.name;
   }

   public String getName() {
      return this.name;
   }
}
