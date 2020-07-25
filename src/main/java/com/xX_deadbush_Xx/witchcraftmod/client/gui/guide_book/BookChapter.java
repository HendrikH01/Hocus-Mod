package com.xX_deadbush_Xx.witchcraftmod.client.gui.guide_book;

import java.util.List;
import java.util.function.Consumer;

import com.google.common.collect.Lists;
import com.xX_deadbush_Xx.witchcraftmod.client.gui.guide_book.BookPage.Side;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BookChapter {
	
	private ITextComponent title;
	private List<BookPage> pages = Lists.newArrayList();
	
	public BookChapter(ITextComponent title) {
		this.title = title;
	}
	
	public void drawTitle() {
		FontRenderer renderer = Minecraft.getInstance().fontRenderer;
		//TODO
		
		// Offsets based on Minecraft window dimensions
		int OffsetX = (GuideBookScreen.INSTANCE.width) / 2 - GuideBookScreen.PAGE_WIDTH;
		int OffsetY = (GuideBookScreen.INSTANCE.height - GuideBookScreen.PAGE_HEIGHT) / 2 + 16;
					
		// Offsets based on the page
		int side = 16;
		if(pages.get(0).getSide() == Side.RIGHT)
		side = GuideBookScreen.PAGE_WIDTH + 8;
		
		renderer.drawSplitString(this.title.getFormattedText(), OffsetX + side, OffsetY, 120, 0xFF000000);
	}
	
	public int getNumberOfPages() {
		return pages.size();
	}
	
	public List<BookPage> getPages() {
		return pages;
	}
	
	public void addPage(BookPage page) {
		pages.add(page);
	}

	
	public BookChapter newPage(Consumer<BookPage.Builder> builder) {
		BookPage page = new BookPage(pages.size());
		builder.accept(page.new Builder());
		this.addPage(page);
		return this;
	}
}
