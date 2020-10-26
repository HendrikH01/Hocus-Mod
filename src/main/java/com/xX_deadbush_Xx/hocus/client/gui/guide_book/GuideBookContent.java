package com.xX_deadbush_Xx.hocus.client.gui.guide_book;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import com.xX_deadbush_Xx.hocus.Hocus;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GuideBookContent {
	public List<BookChapter> chapters = new ArrayList<>();
	private int maxPageCount = 10000;
	private int pageCount = 0;
	private int nextchapterid = 0;
	
	public GuideBookContent() {}
	
	public int getNumberOfPages() {
		return pageCount;
	}

	public GuideBookContent addChapter(ITextComponent title, Consumer<BookChapter> builder) {
		BookChapter chapter = new BookChapter(title, this, nextchapterid++);
		builder.accept(chapter);
		this.chapters.add(chapter);
		this.pageCount += chapter.getNumberOfPages();
		return this;
	}
	
	public List<BookChapter> getChapters() {
		return chapters;
	}
	
	public static void loadBookContent(RecipeManager recipeManager) {
		GuideBookContent instance = new GuideBookContent();
		instance.addChapter(new StringTextComponent("testchapter0"), (chapter) ->
					chapter
					.newPage((builder) -> builder.addParagraph(new StringTextComponent("TEST TEXT TEXT TEXT TEXT TEXT TEXT TEXT TEXT TEXT TEXT TEXT ")).addParagraph(new StringTextComponent("TEST TEXT TEXT TEXT TEXT TEXT TEXT TEXT TEXT ")).addRecipe(recipeManager, 0, 32, new ItemStack(Items.ACACIA_PRESSURE_PLATE)).build())
					.newPage((builder) -> builder.addParagraph(new StringTextComponent("TEST TEXT 2 TEXT TEXT TEXT TEXT TEXT TEXT TEXT TEXT TEXT TEXT ")).addParagraph(new StringTextComponent("TEST TEXT TEXT TEXT TEXT TEXT TEXT TEXT TEXT ")).addImage(new ResourceLocation(Hocus.MOD_ID, "textures/gui/guide_book/testing_book_image.png"), 60, 0, 64, 0, 64, 64).build())
					.newPage((builder) -> builder.addParagraph(new StringTextComponent("TEST TEXT 3")).addParagraph(new StringTextComponent("TEST TEXT TEXT TEXT TEXT TEXT TEXT TEXT TEXT ")).addImage(new ResourceLocation(Hocus.MOD_ID, "textures/gui/guide_book/testing_book_image.png"), 0, 0, 0, 64, 64, 64).build())
					.newPage((builder) -> builder.addParagraph(new StringTextComponent("TEST TEXT 4")).build())
					.newPage((builder) -> builder.addParagraph(new StringTextComponent("TEST TEXT 5")).build())
					.newPage((builder) -> builder.addParagraph(new StringTextComponent("TEST TEXT 6")).build())
					.newPage((builder) -> builder.addParagraph(new StringTextComponent("TEST TEXT 7")).build())
			).addChapter(new StringTextComponent("testchapter1"), (chapter) ->
					chapter.newPage((builder) -> builder.addParagraph(new StringTextComponent("TEST TEXT")).build())
					.newPage((builder) -> builder.addParagraph(new StringTextComponent("TEST TEXT 2")).build())
					.newPage((builder) -> builder.addParagraph(new StringTextComponent("TEST TEXT 3")).build())
					.newPage((builder) -> builder.addParagraph(new StringTextComponent("TEST TEXT 4")).build())
					.newPage((builder) -> builder.addParagraph(new StringTextComponent("TEST TEXT 5")).build())
					.newPage((builder) -> builder.addParagraph(new StringTextComponent("TEST TEXT 6")).build())
					.newPage((builder) -> builder.addParagraph(new StringTextComponent("TEST TEXT 7")).build())
					.newPage((builder) -> builder.addParagraph(new StringTextComponent("TEST TEXT 8")).build())
					.newPage((builder) -> builder.addParagraph(new StringTextComponent("TEST TEXT 9")).build())
					.newPage((builder) -> builder.addParagraph(new StringTextComponent("TEST TEXT 10")).build())
					.newPage((builder) -> builder.addParagraph(new StringTextComponent("TEST TEXT 11")).build())
			).addChapter(new StringTextComponent("testchapter2"), (chapter) ->
					chapter.newPage((builder) -> builder.addParagraph(new StringTextComponent("TEST TEXT")).build())
					.newPage((builder) -> builder.addParagraph(new StringTextComponent("TEST TEXT 2")).build())
					.newPage((builder) -> builder.addParagraph(new StringTextComponent("TEST TEXT 3")).build())
					.newPage((builder) -> builder.addParagraph(new StringTextComponent("TEST TEXT 4")).build())
					.newPage((builder) -> builder.addParagraph(new StringTextComponent("TEST TEXT 5")).build())
					.newPage((builder) -> builder.addParagraph(new StringTextComponent("TEST TEXT 6")).build())
					.newPage((builder) -> builder.addParagraph(new StringTextComponent("TEST TEXT 7")).build())
			).addChapter(new StringTextComponent("testchapter3"), (chapter) ->
					chapter.newPage((builder) -> builder.addParagraph(new StringTextComponent("TEST TEXT")).build())
					.newPage((builder) -> builder.addParagraph(new StringTextComponent("TEST TEXT 2")).build())
					.newPage((builder) -> builder.addParagraph(new StringTextComponent("TEST TEXT 3")).build())
					.newPage((builder) -> builder.addParagraph(new StringTextComponent("TEST TEXT 4")).build())
			).addChapter(new StringTextComponent("testchapter4"), (chapter) ->
					chapter.newPage((builder) -> builder.addParagraph(new StringTextComponent("TEST TEXT")).build())
					.newPage((builder) -> builder.addParagraph(new StringTextComponent("TEST TEXT 2")).build())
					.newPage((builder) -> builder.addParagraph(new StringTextComponent("TEST TEXT 3")).build())
			).addChapter(new StringTextComponent("testchapter5"), (chapter) ->
					chapter.newPage((builder) -> builder.addParagraph(new StringTextComponent("TEST TEXT")).build())
					.newPage((builder) -> builder.addParagraph(new StringTextComponent("TEST TEXT 2")).build())
					.newPage((builder) -> builder.addParagraph(new StringTextComponent("TEST TEXT 3")).build())
			).addChapter(new StringTextComponent("testchapter6"), (chapter) ->
					chapter.newPage((builder) -> builder.addParagraph(new StringTextComponent("TEST TEXT")).build())
					.newPage((builder) -> builder.addParagraph(new StringTextComponent("TEST TEXT 2")).build())
					.newPage((builder) -> builder.addParagraph(new StringTextComponent("TEST TEXT 3")).build())
			).addChapter(new StringTextComponent("testchapter7"), (chapter) ->
					chapter.newPage((builder) -> builder.addParagraph(new StringTextComponent("TEST TEXT")).build())
					.newPage((builder) -> builder.addParagraph(new StringTextComponent("TEST TEXT 2")).build())
					.newPage((builder) -> builder.addParagraph(new StringTextComponent("TEST TEXT 3")).build())
			);
		
		
		GuideBookScreen.INSTANCE.setContent(instance);
	}
}
