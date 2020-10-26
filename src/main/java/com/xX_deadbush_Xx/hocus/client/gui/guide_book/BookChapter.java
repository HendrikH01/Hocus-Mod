package com.xX_deadbush_Xx.hocus.client.gui.guide_book;

import java.util.List;
import java.util.function.Consumer;

import com.google.common.collect.Lists;
import com.xX_deadbush_Xx.hocus.client.gui.guide_book.BookPage.Side;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BookChapter {
	
	private GuideBookContent parentContent;
	private ITextComponent title;
	private List<BookPage> pages = Lists.newArrayList();
	private final int id;
	public int startingPage;
	
	public BookChapter(ITextComponent title, GuideBookContent content, int id) {
		this.title = title;
		this.parentContent = content;
		this.id = id;
		
		this.startingPage = 1; //skip cover page
		for(BookChapter chapter : this.parentContent.chapters) {
			if(!chapter.equals(this)) {
				this.startingPage += chapter.getNumberOfPages();
			} else break;
		}
	}
	
	@SuppressWarnings("resource")
	public void drawTitle(Side side) {
		FontRenderer renderer = Minecraft.getInstance().fontRenderer;

		int offsetX = (GuideBookScreen.INSTANCE.width) / 2 - GuideBookScreen.PAGE_WIDTH + 8;
		int offsetY = (GuideBookScreen.INSTANCE.height - GuideBookScreen.PAGE_HEIGHT) / 2 + 16;
		offsetX += side == Side.RIGHT ? GuideBookScreen.PAGE_WIDTH : 8;
		renderer.drawSplitString(this.title.getFormattedText(), offsetX, offsetY, 120, 0xFF000000);
	}
	
	public BookChapter getNextChapter() {
		for(int i = 0; i < this.parentContent.chapters.size() - 1; i++) {
			if(this.parentContent.chapters.get(i).equals(this)) {
				return this.parentContent.chapters.get(i + 1);
			}
		}
		
		return null;
	}
	
	public int getNumberOfPages() {
		return pages.size();
	}
	
	public List<BookPage> getPages() {
		return pages;
	}
	
	public BookPage getPage(int index) {
		return this.pages.get(index);
	}
	
	public void addPage(BookPage page) {
		pages.add(page);
	}

	public BookChapter blankPage() {
		this.addPage(new BookPage(this, pages.size()));
		return this;
	}
	
	public BookChapter newPage(Consumer<BookPage.Builder> builder) {
		BookPage page = new BookPage(this, pages.size());
		builder.accept(page.new Builder());
		this.addPage(page);
		return this;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof BookChapter) {
			return ((BookChapter)obj).id == this.id;
		} else return false;
	}

	public ITextComponent getTitle() {
		return title;
	}
}
