package com.xX_deadbush_Xx.witchcraftmod.common.register;

import com.xX_deadbush_Xx.witchcraftmod.WitchcraftMod;
import com.xX_deadbush_Xx.witchcraftmod.common.container.BottomLessBagContainer;
import com.xX_deadbush_Xx.witchcraftmod.common.container.ToolTableContainer;

import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModContainers {
	public static final DeferredRegister<ContainerType<?>> CONTAINER_TYPES = new DeferredRegister<>(ForgeRegistries.CONTAINERS, WitchcraftMod.MOD_ID);

	public static final RegistryObject<ContainerType<ToolTableContainer>> TOOL_TABLE = CONTAINER_TYPES
			.register("tool_table", () -> IForgeContainerType.create(ToolTableContainer::new));

	public static final RegistryObject<ContainerType<BottomLessBagContainer>> BOTTOM_LESS_BAG = CONTAINER_TYPES.register("bottomless_bag",
			() -> IForgeContainerType.create(BottomLessBagContainer::new));
}
