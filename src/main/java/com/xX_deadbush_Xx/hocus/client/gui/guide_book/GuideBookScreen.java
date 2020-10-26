package com.xX_deadbush_Xx.hocus.client.gui.guide_book;

import java.util.List;

import com.xX_deadbush_Xx.hocus.Hocus;

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

	public static final ResourceLocation TEXTURES_SHEET = new ResourceLocation(Hocus.MOD_ID, "textures/gui/guide_book/witch_guide_book.png");
	public static final int SHEET_WIDTH = 512;
	public static final int SHEET_HEIGHT = 512;
	public static final int PAGE_WIDTH = 148;
	public static final int PAGE_HEIGHT = 182;
	public static final int ARROW_WIDTH = 18;
	public static final int ARROW_HEIGHT = 10;

	private CoverButton coverButton; 
	private NextButton next;
	private PreviousButton previous;
	
	private int currentLeftPage = 0;
	private static GuideBookContent content;
	public BookPage leftPage, rightPage;
	
	public static final GuideBookScreen INSTANCE = new GuideBookScreen();
	
	protected GuideBookScreen(ITextComponent titleIn) {
		super(titleIn);
	}
	
	public GuideBookScreen() {
		this(getFormattedName());
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
					currentLeftPage = 1;
					selectPages();
					updateButtons();
				});

		next = new NextButton((this.width) / 2 + PAGE_WIDTH - ARROW_WIDTH - 25, (this.height) / 2 + PAGE_HEIGHT / 2 - 24, ARROW_WIDTH, ARROW_HEIGHT, I18n.format(""), (button) -> {
					if (getNumberOfPages() - 2 >= currentLeftPage) {
						currentLeftPage += 2;
						selectPages();
						updateButtons();
					}
				});

		previous = new PreviousButton((this.width) / 2 - PAGE_WIDTH + 25, (this.height) / 2 + PAGE_HEIGHT / 2 - 24, ARROW_WIDTH, ARROW_HEIGHT, I18n.format(""), (button) -> {
					if (currentLeftPage > 2) {
						currentLeftPage -= 2;
						if (currentLeftPage == 0)
							currentLeftPage = 2;
						selectPages();
						updateButtons();
					}
				});

		updateButtons();
	}

	public void selectPages() {
		// cover page
		if(this.currentLeftPage == 0) {
			this.leftPage = null;
			this.rightPage = null;
			return;
		} else if(currentLeftPage == 1) {
			this.leftPage = null;
			this.rightPage = content.getChapters().get(0).getPage(0);
			return;
		}
		
		BookChapter currentChapter = content.getChapters().get(0);
		int pagenum = 1;
		
		for(BookChapter chapter : content.getChapters()) {
			if(chapter.getNumberOfPages() + pagenum < this.currentLeftPage) {
				pagenum += chapter.getNumberOfPages();
			} else {
				currentChapter = chapter;
				break;
			}
		}
		 
		this.leftPage = currentChapter.getPage(currentLeftPage - pagenum - 1);
		if(currentChapter.getNumberOfPages() + pagenum == currentLeftPage) {
			BookChapter next = currentChapter.getNextChapter();
			if(next != null) {
				this.rightPage = next.getPage(0);
			}
		} else {
			this.rightPage = currentChapter.getPage(currentLeftPage - pagenum);
		}
	}

	private void updateButtons() {
		this.buttons.clear();

		if (currentLeftPage == 0) {
			this.addButton(coverButton);
		} else {
			if (currentLeftPage >= 1 && currentLeftPage < getNumberOfPages() - 1) {
				this.addButton(next);
			}

			if (currentLeftPage >= 3) {
				this.addButton(previous);
			}
		}
	}

	@Override
	public void onClose() {
		this.currentLeftPage = 0;
		super.onClose();
	}
	
	public static Coords getLeftPageCoords() {
		Minecraft mc = Minecraft.getInstance();
		int x = (int) (mc.getMainWindow().getWidth() / (2 * mc.getMainWindow().getGuiScaleFactor())  - PAGE_WIDTH);
		int y = (int) (mc.getMainWindow().getHeight() / mc.getMainWindow().getGuiScaleFactor() - PAGE_HEIGHT) / 2;
		return new Coords(x, y);
	}
	
	public static Coords getRightPageCoords() {
		Minecraft mc = Minecraft.getInstance();
		int x = (int) (mc.getMainWindow().getWidth() / (2 * mc.getMainWindow().getGuiScaleFactor()));
		int y = (int) (mc.getMainWindow().getHeight() / mc.getMainWindow().getGuiScaleFactor() - PAGE_HEIGHT) / 2;
		return new Coords(x, y);
	}

	@Override
	public void render(int mouseX, int mouseY, float partialTicks) {
		this.renderBackground();
		
		if(currentLeftPage > 0) {
			Minecraft.getInstance().getTextureManager().bindTexture(TEXTURES_SHEET);
			Coords left = getLeftPageCoords();
			Coords right = getRightPageCoords();

			if(currentLeftPage == 2) {
				AbstractGui.blit(left.x, left.y, 0, PAGE_WIDTH - 3, PAGE_HEIGHT + 6, PAGE_WIDTH, PAGE_HEIGHT, SHEET_HEIGHT, SHEET_WIDTH);	
			} else {
				//Standard left page
				AbstractGui.blit(left.x, left.y, 0, 0, 0, PAGE_WIDTH, PAGE_HEIGHT, SHEET_HEIGHT, SHEET_WIDTH);
			}
			
			AbstractGui.blit(right.x, right.y, 0, PAGE_WIDTH, 0, PAGE_WIDTH, PAGE_HEIGHT, SHEET_HEIGHT, SHEET_WIDTH);

			if(leftPage != null) {
				leftPage.drawPage();

				if(leftPage.isFirstPage()) {
					leftPage.getChapter().drawTitle(BookPage.Side.LEFT);
				}
			}
			
			if(rightPage != null) {
				rightPage.drawPage();

				if(rightPage.isFirstPage()) {
					rightPage.getChapter().drawTitle(BookPage.Side.RIGHT);
				}
			}			
		}
		
		super.render(mouseX, mouseY, partialTicks);
	}
		
	private static ITextComponent getFormattedName() {
		return new TranslationTextComponent(Hocus.MOD_ID + ".guidebook.title");
	}
	
	public List<Widget> getButtons() {
		return this.buttons;
	}
	
	public static class Coords {
		public int x;
		public int y;
		
		public Coords(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
}