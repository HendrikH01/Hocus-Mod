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
	public static final int PAGE_WIDTH = 148;
	public static final int PAGE_HEIGHT = 182;
	public static final int ARROW_WIDTH = 18;
	public static final int ARROW_HEIGHT = 10;
	public int currentPage = 0;
	public int pages = 1;
	public CoverButton coverButton;
	public NextButton next;
	public PreviousButton previous;
	
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
		
		coverButton = new CoverButton((this.width - PAGE_WIDTH) / 2, (this.height - PAGE_HEIGHT) / 2, PAGE_WIDTH, PAGE_HEIGHT, I18n.format("")){
			@Override
			public void onPress() {
				currentPage = 1;
			}
		};
		
		next = new NextButton((this.width) / 2 + PAGE_WIDTH - ARROW_WIDTH - 25, (this.height) / 2 + 67, ARROW_WIDTH, ARROW_HEIGHT, I18n.format("")) {
			@Override
			public void onPress() {
				if(pages <= currentPage + 1)
				currentPage += 1;
			}
		};
		
		previous = new PreviousButton((this.width) / 2 - PAGE_WIDTH + 25, (this.height) / 2 + 67, ARROW_WIDTH, ARROW_HEIGHT, I18n.format("")) {
			@Override
			public void onPress() {
				if(currentPage - 1 > 0)
				currentPage -= 1;
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
		if(currentPage > 0 && currentPage < pages && !buttons.contains(next)) {
			buttons.add(next);
		} else if((currentPage == 0 || currentPage == pages) && buttons.contains(next)){
			buttons.remove(next);
		}
		if(currentPage > 1 && currentPage <= pages && !buttons.contains(previous)) {
			buttons.add(previous);
		} else if((currentPage == 0 || currentPage == 1) && buttons.contains(previous)){
			buttons.remove(previous);
		}
	}
	
	@Override
	public void render(int mouseX, int mouseY, float partialTicks) {
		
		if(currentPage > 0) {
			Minecraft.getInstance().getTextureManager().bindTexture(TEXTURES_SHEET);
			if(currentPage == 1) {
				AbstractGui.blit((this.width) / 2 - PAGE_WIDTH, (this.height - PAGE_HEIGHT) / 2, 0, PAGE_WIDTH - 3, PAGE_HEIGHT + 6, PAGE_WIDTH, PAGE_HEIGHT, SHEET_HEIGHT, SHEET_WIDTH);	
			} else {
				AbstractGui.blit((this.width) / 2 - PAGE_WIDTH, (this.height - PAGE_HEIGHT) / 2, 0, 0, 0, PAGE_WIDTH, PAGE_HEIGHT, SHEET_HEIGHT, SHEET_WIDTH);
			}
			AbstractGui.blit((this.width) / 2, (this.height - PAGE_HEIGHT) / 2, 0, PAGE_WIDTH, 0, PAGE_WIDTH, PAGE_HEIGHT, SHEET_HEIGHT, SHEET_WIDTH);
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