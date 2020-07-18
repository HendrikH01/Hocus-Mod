package com.xX_deadbush_Xx.witchcraftmod.client.gui.guide_book;

import java.util.List;
import java.util.function.Consumer;

import com.google.common.collect.Lists;

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
	}
	
	public int getNumberOfPages() {
		return pages.size();
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
