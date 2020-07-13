package com.xX_deadbush_Xx.witchcraftmod.client.gui.guide_book;

import java.util.List;

import com.xX_deadbush_Xx.witchcraftmod.WitchcraftMod;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GuideBookScreen extends Screen {

	
	public static final ResourceLocation TEXTURES_SHEET = new ResourceLocation(WitchcraftMod.MOD_ID, "textures/gui/guide_book/witch_guide_book.png");
	public static final int SHEET_WIDTH = 512;
	public static final int SHEET_HEIGHT = 512;
	public int currentPage = 0;
	public int pages = 1;
	public CoverButton coverButton;
	
	public static final GuideBookScreen SCREEN = new GuideBookScreen();
	
	protected GuideBookScreen(ITextComponent titleIn) {
		super(titleIn);
	}
	
	public GuideBookScreen() {
		this(getDefaultName());
	}
	
	@Override
	public boolean isPauseScreen() {
		return false;
	}
	
	@Override
	public void init() {
		super.init();
		
		coverButton = new CoverButton((this.width - 148) / 2, (this.height - 182) / 2, 148, 182, I18n.format("")){
			@Override
			public void onPress() {
				currentPage = 1;
			}
		};
		
		buttons.clear();
		buttons.add(coverButton);
		
		this.addButtons();
	}
	
	public void addButtons() {
		// TO-DO add buttons
	}
	
	@Override
	public void onClose() {
		this.currentPage = 0;
		super.onClose();
	}
	
	@Override
	public void tick() {
		super.tick();
		
		if(currentPage > 0 && buttons.contains(coverButton)) {
			buttons.remove(coverButton);
			}
	}
	
	@Override
	public void render(int mouseX, int mouseY, float partialTicks) {
		
		if(currentPage > 0) {
			Minecraft.getInstance().getTextureManager().bindTexture(TEXTURES_SHEET);
			AbstractGui.blit((this.width) / 2 - 148, (this.height - 182) / 2, 0, 0, 0, 148, 182, SHEET_HEIGHT, SHEET_WIDTH);
			AbstractGui.blit((this.width) / 2, (this.height - 182) / 2, 0, 148, 0, 148, 182, SHEET_HEIGHT, SHEET_WIDTH);
		}
		
		super.render(mouseX, mouseY, partialTicks);
	}
	
	// FOR INITIALIZATION ONLY
	
	private static ITextComponent getDefaultName() {
		return new TranslationTextComponent("GuideBook");
	}
	
	public List<Widget> getButtons() {
		return this.buttons;
	}
	
}
