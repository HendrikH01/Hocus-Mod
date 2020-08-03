package com.xX_deadbush_Xx.witchcraftmod.client.gui.guide_book;

import java.util.ArrayList;
import java.util.List;

import com.xX_deadbush_Xx.witchcraftmod.WitchcraftMod;
import com.xX_deadbush_Xx.witchcraftmod.client.gui.guide_book.BookPage.Side;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GuideBookContent {
	private List<BookChapter> chapters = new ArrayList<>();
	private int maxPageCount;
	
	public GuideBookContent() {
		
	}
	
	public int getNumberOfPages() {
		return maxPageCount;
	}
	
	public GuideBookContent addChapter(BookChapter chapter) {
		chapters.add(chapter);
		maxPageCount += chapter.getNumberOfPages();
		return this;
	}
	
	public List<BookChapter> getChapters() {
		return chapters;
	}
	
	public static void loadBookContent() {
		GuideBookContent instance = new GuideBookContent();
		instance.addChapter(
				new BookChapter(new StringTextComponent("testchapter0"))
					.newPage((builder) -> builder.setSide(Side.LEFT).build(), instance.getNumberOfPages())
					.newPage((builder) -> builder.addParagraph(new StringTextComponent("TEST TEXT TEXT TEXT TEXT TEXT TEXT TEXT TEXT TEXT TEXT TEXT ")).addParagraph(new StringTextComponent("TEST TEXT TEXT TEXT TEXT TEXT TEXT TEXT TEXT ")).addRecipe(0, 16).isFirstPage().setSide(Side.RIGHT).build(), instance.getNumberOfPages())
					.newPage((builder) -> builder.addParagraph(new StringTextComponent("TEST TEXT 2 TEXT TEXT TEXT TEXT TEXT TEXT TEXT TEXT TEXT TEXT ")).addParagraph(new StringTextComponent("TEST TEXT TEXT TEXT TEXT TEXT TEXT TEXT TEXT ")).addImage(new ResourceLocation(WitchcraftMod.MOD_ID, "textures/gui/guide_book/testing_book_image.png"), 60, 0, 64, 0, 64, 64).setSide(Side.LEFT).build(), instance.getNumberOfPages())
					.newPage((builder) -> builder.addParagraph(new StringTextComponent("TEST TEXT 3")).addParagraph(new StringTextComponent("TEST TEXT TEXT TEXT TEXT TEXT TEXT TEXT TEXT ")).addImage(new ResourceLocation(WitchcraftMod.MOD_ID, "textures/gui/guide_book/testing_book_image.png"), 0, 0, 0, 64, 64, 64).setSide(Side.RIGHT).build(), instance.getNumberOfPages())
					.newPage((builder) -> builder.addParagraph(new StringTextComponent("TEST TEXT 4")).setSide(Side.LEFT).build(), instance.getNumberOfPages())
					.newPage((builder) -> builder.addParagraph(new StringTextComponent("TEST TEXT 5")).setSide(Side.RIGHT).build(), instance.getNumberOfPages())
					.newPage((builder) -> builder.addParagraph(new StringTextComponent("TEST TEXT 6")).setSide(Side.LEFT).build(), instance.getNumberOfPages())
					.newPage((builder) -> builder.addParagraph(new StringTextComponent("TEST TEXT 7")).setSide(Side.RIGHT).build(), instance.getNumberOfPages())
			).addChapter(
					new BookChapter(new StringTextComponent("testchapter1"))
					.newPage((builder) -> builder.addParagraph(new StringTextComponent("TEST TEXT")).isFirstPage().setSide(Side.LEFT).build(), instance.getNumberOfPages())
					.newPage((builder) -> builder.addParagraph(new StringTextComponent("TEST TEXT 2")).setSide(Side.RIGHT).build(), instance.getNumberOfPages())
					.newPage((builder) -> builder.addParagraph(new StringTextComponent("TEST TEXT 3")).setSide(Side.LEFT).build(), instance.getNumberOfPages())
					.newPage((builder) -> builder.addParagraph(new StringTextComponent("TEST TEXT 4")).setSide(Side.RIGHT).build(), instance.getNumberOfPages())
					.newPage((builder) -> builder.addParagraph(new StringTextComponent("TEST TEXT 5")).setSide(Side.LEFT).build(), instance.getNumberOfPages())
					.newPage((builder) -> builder.addParagraph(new StringTextComponent("TEST TEXT 6")).setSide(Side.RIGHT).build(), instance.getNumberOfPages())
					.newPage((builder) -> builder.addParagraph(new StringTextComponent("TEST TEXT 7")).setSide(Side.LEFT).build(), instance.getNumberOfPages())
					.newPage((builder) -> builder.addParagraph(new StringTextComponent("TEST TEXT 8")).setSide(Side.RIGHT).build(), instance.getNumberOfPages())
					.newPage((builder) -> builder.addParagraph(new StringTextComponent("TEST TEXT 9")).setSide(Side.LEFT).build(), instance.getNumberOfPages())
					.newPage((builder) -> builder.addParagraph(new StringTextComponent("TEST TEXT 10")).setSide(Side.RIGHT).build(), instance.getNumberOfPages())
					.newPage((builder) -> builder.addParagraph(new StringTextComponent("TEST TEXT 11")).setSide(Side.LEFT).build(), instance.getNumberOfPages())
			).addChapter(
					new BookChapter(new StringTextComponent("testchapter2"))
					.newPage((builder) -> builder.addParagraph(new StringTextComponent("TEST TEXT")).isFirstPage().setSide(Side.RIGHT).build(), instance.getNumberOfPages())
					.newPage((builder) -> builder.addParagraph(new StringTextComponent("TEST TEXT 2")).setSide(Side.LEFT).build(), instance.getNumberOfPages())
					.newPage((builder) -> builder.addParagraph(new StringTextComponent("TEST TEXT 3")).setSide(Side.RIGHT).build(), instance.getNumberOfPages())
					.newPage((builder) -> builder.addParagraph(new StringTextComponent("TEST TEXT 4")).setSide(Side.LEFT).build(), instance.getNumberOfPages())
					.newPage((builder) -> builder.addParagraph(new StringTextComponent("TEST TEXT 5")).setSide(Side.RIGHT).build(), instance.getNumberOfPages())
					.newPage((builder) -> builder.addParagraph(new StringTextComponent("TEST TEXT 6")).setSide(Side.LEFT).build(), instance.getNumberOfPages())
					.newPage((builder) -> builder.addParagraph(new StringTextComponent("TEST TEXT 7")).setSide(Side.RIGHT).build(), instance.getNumberOfPages())
			).addChapter(
					new BookChapter(new StringTextComponent("testchapter3"))
					.newPage((builder) -> builder.addParagraph(new StringTextComponent("TEST TEXT")).isFirstPage().setSide(Side.LEFT).build(), instance.getNumberOfPages())
					.newPage((builder) -> builder.addParagraph(new StringTextComponent("TEST TEXT 2")).setSide(Side.RIGHT).build(), instance.getNumberOfPages())
					.newPage((builder) -> builder.addParagraph(new StringTextComponent("TEST TEXT 3")).setSide(Side.LEFT).build(), instance.getNumberOfPages())
					.newPage((builder) -> builder.addParagraph(new StringTextComponent("TEST TEXT 4")).setSide(Side.RIGHT).build(), instance.getNumberOfPages())
			).addChapter(
					new BookChapter(new StringTextComponent("testchapter4"))
					.newPage((builder) -> builder.addParagraph(new StringTextComponent("TEST TEXT")).isFirstPage().setSide(Side.LEFT).build(), instance.getNumberOfPages())
					.newPage((builder) -> builder.addParagraph(new StringTextComponent("TEST TEXT 2")).setSide(Side.RIGHT).build(), instance.getNumberOfPages())
					.newPage((builder) -> builder.addParagraph(new StringTextComponent("TEST TEXT 3")).setSide(Side.LEFT).build(), instance.getNumberOfPages())
			).addChapter(
					new BookChapter(new StringTextComponent("testchapter5"))
					.newPage((builder) -> builder.addParagraph(new StringTextComponent("TEST TEXT")).isFirstPage().setSide(Side.RIGHT).build(), instance.getNumberOfPages())
					.newPage((builder) -> builder.addParagraph(new StringTextComponent("TEST TEXT 2")).setSide(Side.LEFT).build(), instance.getNumberOfPages())
					.newPage((builder) -> builder.addParagraph(new StringTextComponent("TEST TEXT 3")).setSide(Side.RIGHT).build(), instance.getNumberOfPages())
			).addChapter(
					new BookChapter(new StringTextComponent("testchapter6"))
					.newPage((builder) -> builder.addParagraph(new StringTextComponent("TEST TEXT")).isFirstPage().setSide(Side.LEFT).build(), instance.getNumberOfPages())
					.newPage((builder) -> builder.addParagraph(new StringTextComponent("TEST TEXT 2")).setSide(Side.RIGHT).build(), instance.getNumberOfPages())
					.newPage((builder) -> builder.addParagraph(new StringTextComponent("TEST TEXT 3")).setSide(Side.LEFT).build(), instance.getNumberOfPages())
			).addChapter(
					new BookChapter(new StringTextComponent("testchapter7"))
					.newPage((builder) -> builder.addParagraph(new StringTextComponent("TEST TEXT")).isFirstPage().setSide(Side.RIGHT).build(), instance.getNumberOfPages())
					.newPage((builder) -> builder.addParagraph(new StringTextComponent("TEST TEXT 2")).setSide(Side.LEFT).build(), instance.getNumberOfPages())
					.newPage((builder) -> builder.addParagraph(new StringTextComponent("TEST TEXT 3")).setSide(Side.RIGHT).build(), instance.getNumberOfPages())
			);
		
		
		GuideBookScreen.INSTANCE.setContent(instance);
	}
}
