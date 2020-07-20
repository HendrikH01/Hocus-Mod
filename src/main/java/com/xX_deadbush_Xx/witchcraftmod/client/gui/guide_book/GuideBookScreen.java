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
	private int currentPage = 0;
	private static GuideBookContent content;
	private CoverButton coverButton; 
	private NextButton next;
	private PreviousButton previous;
	
	public static final GuideBookScreen INSTANCE = new GuideBookScreen();
	
	protected GuideBookScreen(ITextComponent titleIn) {
		super(titleIn);
	}
	
	public GuideBookScreen() {
		this(getDefaultName());
	}
	
	public void setContent(GuideBookContent content) {
		GuideBookScreen.content = content;
	}
	
	@Override
	public boolean isPauseScreen() {
		return false;
	}
	
	public int getNumberOfPages() {
		return content.getNumberOfPages();
	}
	
	@Override
	public void init() {
		super.init();
		coverButton = new CoverButton((this.width - PAGE_WIDTH) / 2, (this.height - PAGE_HEIGHT) / 2, PAGE_WIDTH, PAGE_HEIGHT, I18n.format(""), (button) -> {
				currentPage = 2;
				updateButtons();
		});
		
		next = new NextButton((this.width) / 2 + PAGE_WIDTH - ARROW_WIDTH - 25, (this.height) / 2 + PAGE_HEIGHT / 2 - 24, ARROW_WIDTH, ARROW_HEIGHT, I18n.format(""), (button) -> {
					if (getNumberOfPages() >= currentPage) {
						currentPage += 2;
						updateButtons();
					}
				});

		previous = new PreviousButton((this.width) / 2 - PAGE_WIDTH + 25, (this.height) / 2 + PAGE_HEIGHT / 2 - 24, ARROW_WIDTH, ARROW_HEIGHT, I18n.format(""), (button) -> {
					if (currentPage > 2) {
						currentPage -= 2;
						if(currentPage == 0) currentPage = 2;
						updateButtons();
					}
				});

		updateButtons();
	}
	
	public void addButtons() {
		// TO-DO add buttons
	}
	
	private void updateButtons() {
		this.buttons.clear();
		
		if (currentPage == 0) {
			this.addButton(coverButton);
		} else {
			if (currentPage > 0 && currentPage <= getNumberOfPages()) {
				this.addButton(next);
			}
			
			if (currentPage > 2) {
				this.addButton(previous);
			}
		}
	}
	
	@Override
	public void onClose() {
		this.currentPage = 0;
		super.onClose();
	}
	
	@Override
	public void tick() {
		super.tick();
		//moved button calculations to updateButtons()
	}
	
	@Override
	public void render(int mouseX, int mouseY, float partialTicks) {
		if(currentPage > 0) {
			Minecraft.getInstance().getTextureManager().bindTexture(TEXTURES_SHEET);
			if(currentPage == 2) {
				AbstractGui.blit((this.width) / 2 - PAGE_WIDTH, (this.height - PAGE_HEIGHT) / 2, 0, PAGE_WIDTH - 3, PAGE_HEIGHT + 6, PAGE_WIDTH, PAGE_HEIGHT, SHEET_HEIGHT, SHEET_WIDTH);	
			} else {
				AbstractGui.blit((this.width) / 2 - PAGE_WIDTH, (this.height - PAGE_HEIGHT) / 2, 0, 0, 0, PAGE_WIDTH, PAGE_HEIGHT, SHEET_HEIGHT, SHEET_WIDTH);
			}
			AbstractGui.blit((this.width) / 2, (this.height - PAGE_HEIGHT) / 2, 0, PAGE_WIDTH, 0, PAGE_WIDTH, PAGE_HEIGHT, SHEET_HEIGHT, SHEET_WIDTH);
			
			int pages = 0;
			for(BookChapter chapter : content.getChapters()) {
				pages += chapter.getNumberOfPages();
				if(currentPage <= pages) {
					for(int i = 0 ; i + 1 < chapter.getPages().size(); i+=2) {
						BookPage bookPage = chapter.getPages().get(i);
						if(currentPage - 2 == bookPage.getPage()) {
							if(bookPage.isFirstPage()) {
								chapter.drawTitle();
							}
							bookPage.drawPage();						
						}
						if(currentPage >= 3) {
							System.out.println(currentPage);
							bookPage = chapter.getPages().get(i-1);
							System.out.println(i);
							if(currentPage - 1 == bookPage.getPage()) {
								bookPage.drawPage();
							}	
						}
					}
				}
			}
		}
		
		super.render(mouseX, mouseY, partialTicks);
	}
	
	// FOR INITIALIZATION ONLY
	
	private static ITextComponent getDefaultName() {
		return new TranslationTextComponent(WitchcraftMod.MOD_ID + ".guidebook.title");
	}
	
	public List<Widget> getButtons() {
		return this.buttons;
	}
}