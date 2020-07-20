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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BookPage {
	
	private List<TextParagraph> textParagraphs = Lists.newArrayList();
	private List<BookImage> images = Lists.newArrayList();
	private boolean isFirstPage = false;
	private Side side;
	
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
		for(TextParagraph txt : textParagraphs) {
			txt.draw();
		}
		for(BookImage image : images) {
			image.blit();
		}
	}

	public boolean isFirstPage() {
		return isFirstPage;
	}
	
	public Side getSide() {
		return side;
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
		
		/**
		 * Texture must be on a 256x256 sheet
		 */
		public Builder addImage(ResourceLocation image, int x, int y, int textureX, int textureY, int textureWidth, int textureHeight) {
			images.add(new BookImage(image, x, y, textureX, textureY, textureWidth, textureHeight));
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
		
		public Builder isFirstPage() {
			BookPage.this.isFirstPage = true;
			return this;
		}
		
		public Builder setSide(Side side) {
			BookPage.this.side = side;
			return this;
		}

		public void build() {
			//Format and add to page
			
			// Offsets based on Minecraft window dimensions
			int OffsetX = Minecraft.getInstance().getMainWindow().getScaledWidth() / 2 - GuideBookScreen.PAGE_WIDTH;
			int OffsetY = (Minecraft.getInstance().getMainWindow().getScaledWidth()) / 2 - GuideBookScreen.PAGE_HEIGHT + 16;
			
			// Offsets based on the page
			int leftPageOffset = 16;
			int rightPageOffset = GuideBookScreen.PAGE_WIDTH + 8;
			
			// TODO limit of the line
			int wrapWidth = 120;
			
			// Starting positions of the lines
			int startingX = 0;
			int startingY = 0;
			if(isFirstPage) {
				startingY = 18;
			}
			// Turn paragraphs into lines
			for(ITextComponent textComponent : paragraphs) {	
				wrapWidth = 120;
				startingX = 0;
				String text = textComponent.getFormattedText();
				
				if(!images.isEmpty()) {
					for(BookImage image : images) {
						if(image.getY() + image.getHeight() >= startingY) {
							if(image.getX() + image.getWidth() + 2 > wrapWidth * 3 / 4) {
								if(image.getX() <= wrapWidth - (image.getX() + image.getWidth() + 2)) {
									startingX = image.getX() + image.getWidth() + 2;
									wrapWidth -= startingX; 
								} else {
									wrapWidth -= image.getX() + image.getWidth() + 2;
								}
							}
						}
					}
				}
				
				// Checks in how many lines will be drawn and set the offset accordingly
				List<String> list = this.listFormattedStringToWidth(text, wrapWidth);
				int newOffsetY = 0;
				for(String s : list) {
					newOffsetY += 9;
				}
			
				if(side == Side.LEFT)
					textParagraphs.add(new TextParagraph(textComponent, startingX + OffsetX + leftPageOffset, startingY + OffsetY));
				else textParagraphs.add(new TextParagraph(textComponent, startingX + OffsetX + rightPageOffset, startingY + OffsetY));
				
				startingY += newOffsetY + 9;
			}
		}
		
		private List<String> listFormattedStringToWidth(String str, int wrapWidth) {
			while(str != null && str.endsWith("\n")) {
				str = str.substring(0, str.length() - 1);
		    }
			List<String> list = fontrenderer.listFormattedStringToWidth(str, wrapWidth);
			return list;
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
		
		public int getX() {
			return this.x;
		}
		
		public int getY() {
			return this.y;
		}
	}
	
	private static class TextParagraph {
		private ITextComponent text;
		private int startingCoordinateX;
		private int startingCoordinateY;
		
		public TextParagraph(ITextComponent text, int x, int y) {
			this.startingCoordinateX = x;
			this.startingCoordinateY = y;
			this.text = text;
		}
		
		public void draw() {
			fontrenderer.drawSplitString(text.getFormattedText(), startingCoordinateX, startingCoordinateY, 120, 0xFF000000);
		}
	}
	
	enum Side{
		LEFT,
		RIGHT
	}
	
}
