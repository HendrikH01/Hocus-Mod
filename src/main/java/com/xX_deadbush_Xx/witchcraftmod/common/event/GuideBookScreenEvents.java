package com.xX_deadbush_Xx.witchcraftmod.common.event;

import java.util.List;

import com.xX_deadbush_Xx.witchcraftmod.WitchcraftMod;
import com.xX_deadbush_Xx.witchcraftmod.client.gui.guide_book.CoverButton;
import com.xX_deadbush_Xx.witchcraftmod.client.gui.guide_book.GuideBookScreen;
import com.xX_deadbush_Xx.witchcraftmod.client.gui.guide_book.NextButton;
import com.xX_deadbush_Xx.witchcraftmod.client.gui.guide_book.PreviousButton;

import net.minecraft.client.gui.widget.Widget;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.GuiScreenEvent.MouseClickedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = WitchcraftMod.MOD_ID, bus = Bus.FORGE, value = Dist.CLIENT)
public class GuideBookScreenEvents {

	@SubscribeEvent
	public static void onButtonPressed(MouseClickedEvent.Post event) {
		if (event.getGui() instanceof GuideBookScreen) {
			List<Widget> buttons = GuideBookScreen.SCREEN.getButtons();
			for (Widget button : buttons) {
				int widthIn = button.x;
				int heightIn = button.y;
				int width = button.getWidth();
				int height = button.getHeight();
				int x = (int) event.getMouseX();
				int y = (int) event.getMouseY();
				if (x >= widthIn && x < widthIn + width && y >= heightIn && y < heightIn + height) {
					if (button instanceof CoverButton) {
						((CoverButton) button).onPress();
					} else if(button instanceof NextButton) {
						((NextButton) button).onPress();
					}
					else if(button instanceof PreviousButton) {
						((PreviousButton) button).onPress();
					}
				}
			}
		}
	}
}