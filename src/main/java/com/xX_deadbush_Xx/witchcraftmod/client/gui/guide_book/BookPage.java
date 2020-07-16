package com.xX_deadbush_Xx.witchcraftmod.client.gui.guide_book;

import java.util.List;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;

public class BookPage {
	
	private List<ITextComponent> paragraphs = Lists.newArrayList();
	private List<BookImage> images = Lists.newArrayList();
	private ITextComponent title;
	
	private int pagenumber;
	private boolean isFull = false;
	private static final FontRenderer fontrenderer = Minecraft.getInstance().fontRenderer;
	
	public BookPage(int pagenumber) {
		this.pagenumber = pagenumber;
	}
	
	public int getPage() {
		return this.pagenumber;
	}
	
	public boolean isFull() {
		return this.isFull;
	}
	
	public void drawPage() {
		
	}
	
	
	//Save yourself some work: If the text doesn't fit on the page anymore just draw it anyway, the dev who writes the book has to make sure that this doesn't happen.
	/*
	public boolean checkIfFullWithParagraph(ITextComponent content) {
		return false;
	}
	*/
	
	public ITextComponent splitParagraphToFitAndGetLeftovers(ITextComponent content) {
		return null;
	}
	
	public class Builder {
		//TODO Crafting recipes
		private List<ITextComponent> paragraphs = Lists.newArrayList();
		private List<BookImage> images = Lists.newArrayList();
		private ITextComponent title;
		
		/**
		 * Texture must be on a 256x256 sheet
		 */
		public Builder addImage(ResourceLocation image, int x, int y, int textureX, int textureY, int textureWidth, int textureHeight) {
			return this;
		}

		public Builder addImage(int x, int y, int textureX, int textureY, int textureWidth, int textureHeight) {
			return this; // pass null for BookImage imageRL
		}

		public Builder addParagraph(ITextComponent paragraph, Style style) {
			paragraphs.add(paragraph.setStyle(style));
			return this;
		}

		public Builder addParagraph(ITextComponent paragraph, TextFormatting... styles) {
			paragraphs.add(paragraph.applyTextStyles(styles));
			return this;
		}

		public Builder addParagraph(ITextComponent paragraph) {
			paragraphs.add(paragraph);
			return this;
		}

		public Builder setTitle(ITextComponent title) {
			this.title = title;
			return this;
		}

		public BookPage build(int pagenum) {
			BookPage page = new BookPage(pagenum);
			//Format and add to page
			
			return page;
		}
	}
	
	private static class BookImage {
		private ResourceLocation image;
		private int x;
		private int y;
		private int textureX;
		private int textureY;
		private int textureWidth;
		private int textureHeight;
		
		public BookImage(@Nullable ResourceLocation image, int x, int y, int textureX, int textureY, int textureWidth, int textureHeight) {
			this.x = x;
			this.y = y;
			this.textureHeight = textureHeight;
			this.textureWidth = textureWidth;
			this.textureX = textureX;
			this.textureY = textureY;
		}
		
		public void blit() {
			if(this.image != null) Minecraft.getInstance().getTextureManager().bindTexture(image); //if null it uses the last RL
			AbstractGui.blit(x, y, textureX, textureY, textureWidth, textureHeight, 256, 256);
		}
		
		public int getWidth() {
			return this.textureWidth;
		}
		
		public int getHeight() {
			return this.textureHeight;
		}
	}
	
	private static class TextLine {
		private ITextComponent text;
		private int startingCoordinateX;
		private int lineNumber;
		
		public TextLine(ITextComponent text, int lineNumber, int x) {
			this.startingCoordinateX = x;
			this.lineNumber = lineNumber;
			this.text = text;
		}
		
		public void draw() {
			//fontrenderer.drawString(text.getFormattedText(), startingCoordinateX + OFFSETX, lineNumber*WIDTH + OFFSETY, text.getStyle().getColor());
		}
	}
}
