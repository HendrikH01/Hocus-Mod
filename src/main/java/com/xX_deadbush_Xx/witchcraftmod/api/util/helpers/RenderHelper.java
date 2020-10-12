package com.xX_deadbush_Xx.witchcraftmod.api.util.helpers;

import net.minecraft.client.renderer.RenderType;

public class RenderHelper {
	public static boolean isSolidOrTranslucent(RenderType layerToCheck) {
		return layerToCheck == RenderType.getSolid() || layerToCheck == RenderType.getTranslucent();
	}
	
	public static boolean isSolidOrCutout(RenderType layerToCheck) {
	    return layerToCheck == RenderType.getSolid() || layerToCheck == RenderType.getCutout();
	}
}
