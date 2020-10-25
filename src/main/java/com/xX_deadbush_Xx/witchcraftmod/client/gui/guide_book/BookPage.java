package com.xX_deadbush_Xx.witchcraftmod.client.gui.guide_book;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import com.xX_deadbush_Xx.witchcraftmod.WitchcraftMod;
import com.xX_deadbush_Xx.witchcraftmod.api.util.CraftingHelper;
import com.xX_deadbush_Xx.witchcraftmod.client.gui.guide_book.GuideBookScreen.Coords;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BookPage {
	private List<TextParagraph> textParagraphs = Lists.newArrayList();
	private List<BookImage> images = Lists.newArrayList();
	private List<Recipe> recipes = Lists.newArrayList();
	private Side side;
	private final int chapterPageNum; // page number in the chapter! First page of chapter has number 0
	private final boolean isFull = false;
	private BookChapter parentChapter;
	private static final FontRenderer fontrenderer = Minecraft.getInstance().fontRenderer;
	
	public BookPage(BookChapter bookChapter, int pagenumber) {
		this.chapterPageNum = pagenumber;
		this.parentChapter = bookChapter;
	}
	
	public BookChapter getChapter() {
		return this.parentChapter;
	}
	
	public boolean isFull() {
		return this.isFull;
	}
	
	public boolean isFirstPage() {
		return this.chapterPageNum == 0;
	}
	
	public void drawPage() {
		Coords pagecoords = this.side == Side.LEFT ? GuideBookScreen.getLeftPageCoords() : GuideBookScreen.getRightPageCoords();
		for(TextParagraph txt : textParagraphs) {
			txt.draw(this.side, pagecoords);
		}
		
		for(BookImage image : images) {
			image.blit(this.side, pagecoords);
		}
		
		for(Recipe recipe : recipes) {
			recipe.blit(pagecoords);
		}
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
	
	@Override
	public String toString() {
		return String.format("BookPage {Chapter: %s, PageNumber: %s, Paragraphs: %d, Page: %s}", parentChapter.getTitle().getString(), chapterPageNum + parentChapter.startingPage, textParagraphs.size(), side.toString());
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
		
		public Builder addRecipe(RecipeManager recipeManager, int x, int y, ItemStack result) {
			ShapedRecipe recipe = CraftingHelper.getShapedRecipeFromResult(recipeManager, result.getItem());
			if(recipe != null) {
				List<ItemStack> inputs = new ArrayList<>();
				for(int i = 0; i < 9; i++) inputs.add(ItemStack.EMPTY);
				
				for(int i = 0; i < recipe.getHeight(); i++) {
					for(int j = 0; j < recipe.getWidth(); j++) {
						Ingredient ingred = recipe.getIngredients().get(i * recipe.getWidth() + j);
						System.out.println(ingred.getMatchingStacks() + " " + (i * recipe.getWidth() + j));
						inputs.set(i * 3 + j, ingred.hasNoMatchingItems() ? ItemStack.EMPTY : ingred.getMatchingStacks()[0]);
					}
				}
				
				recipes.add(new Recipe(x, y, inputs, result));
			} else {
				System.out.println("NO RECIPE FOUND FOR: " + result.toString());
			}
			
			return this;
		}

		public void build() {
			BookPage.this.side =  (1 + parentChapter.startingPage + chapterPageNum) % 2 == 0 ? Side.RIGHT : Side.LEFT;
			
			//Coords pagecoords = BookPage.this.side == Side.LEFT ? GuideBookScreen.getLeftPageCoords() : GuideBookScreen.getRightPageCoords();
			
			// TODO limit of the line
			int wrapWidth = 120;
			
			// Starting positions of the lines
			int startingX = 0;
			int startingY = 0;
			
			if(parentChapter.getNumberOfPages() == 0) { //if first page
				startingY = 18;
			}
			// Turn paragraphs into lines
			for(ITextComponent textComponent : paragraphs) {	
				wrapWidth = 120;
				startingX = 0;
				int newOffsetY = 0;
				// String text = textComponent.getFormattedText();
				
				if(!images.isEmpty()) {
					for(BookImage image : images) {
						if(image.getY() + image.getHeight() >= startingY) {
							newOffsetY += image.getHeight();
							if(image.getWidth() + 2 < wrapWidth * 3 / 4) {
								if(image.getX() <= wrapWidth - (image.getX() + image.getWidth() + 2)) {
									startingX = image.getX() + image.getWidth() + 2;
									wrapWidth -= startingX; 
								} else {
									wrapWidth -= image.getWidth() + 2;
								}
							}
						}
					}
				}
				
				if(!recipes.isEmpty()) {
					for(Recipe recipe : recipes) {
						if(recipe.getY() + recipe.getHeight() >= startingY) {
							newOffsetY += recipe.getHeight();
						}
					}
				}
				
//				// Checks in how many lines will be drawn and set the offset accordingly
//				List<String> list = this.listFormattedStringToWidth(text, wrapWidth);
//				for(String s : list) {
//					newOffsetY += 9;
//				}
			
				if(side == Side.LEFT)
					textParagraphs.add(new TextParagraph(textComponent, startingX, startingY, wrapWidth));
				else textParagraphs.add(new TextParagraph(textComponent, startingX, startingY, wrapWidth));
				
				startingY += newOffsetY + 9;
			}
		}
		
		@SuppressWarnings("unused")
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
			this.y = y + 16;
			this.textureHeight = textureHeight;
			this.textureWidth = textureWidth;
			this.textureX = textureX;
			this.textureY = textureY;
			this.image = image;
		}
		
		public void blit(Side side, Coords pagecoords) {			
			if(this.image != null) Minecraft.getInstance().getTextureManager().bindTexture(image); //if null it uses the last RL
				AbstractGui.blit(x + pagecoords.x + (side == Side.LEFT ? 16 : 8), y + pagecoords.y, textureX, textureY, textureWidth, textureHeight, 256, 256);
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
	
	private static class Recipe {
		
		private ResourceLocation background = new ResourceLocation(WitchcraftMod.MOD_ID, "textures/gui/guide_book/crafting_table_background.png");
		private ItemStack result;
		private List<ItemStack> list; // contains 9 ItemStacks 
		private int x;
		private int y;
		
		
		public Recipe(int x, int y, List<ItemStack> list, ItemStack item) {
			this.x = x;
			this.y = y;
			this.result = item;
			this.list = list;

		}
		
		public void blit(Coords pagecoords) {
			ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
			if(this.background != null) Minecraft.getInstance().getTextureManager().bindTexture(this.background); //if null it uses the last RL
			AbstractGui.blit(x + pagecoords.x + 8, y + pagecoords.y, 0, 0, 120, 64, 120, 64);

			RenderSystem.pushMatrix();
			RenderSystem.scaled(1.5, 1.5, 1.5);
			itemRenderer.renderItemAndEffectIntoGUI(result, (int)((x + pagecoords.x)/1.5 + 68), (int)((y + pagecoords.y)/1.5 + 13));
			RenderSystem.popMatrix();

			int collumn = 0;
			int row = 0;
			for(ItemStack stack : this.list) {
				itemRenderer.renderItemAndEffectIntoGUI(stack, x + pagecoords.x + 18 * collumn + 11, pagecoords.y + y + 6 + 18 * row);
				collumn++;
				if(collumn > 2) {
					collumn = 0;
					row++;
				}
			}
		}
		
		@SuppressWarnings("unused")
		public int getWidth() {
			return 120;
		}
		
		public int getHeight() {
			return 64;
		}
		
		@SuppressWarnings("unused")
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
		private int wrapWidth;
		
		public TextParagraph(ITextComponent text, int x, int y, int wrapWidth) {
			this.startingCoordinateX = x;
			this.startingCoordinateY = y;
			this.text = text;
			this.wrapWidth = wrapWidth;
		}
		
		public void draw(Side side, Coords pagecoords) {
			fontrenderer.drawSplitString(text.getFormattedText(), pagecoords.x + startingCoordinateX + (side == Side.LEFT ? 16 : 8), pagecoords.y + startingCoordinateY + 16, wrapWidth, 0xFF000000);
		}
	}
	
	public enum Side{
		LEFT,
		RIGHT
	}
}
