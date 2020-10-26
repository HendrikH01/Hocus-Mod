package com.xX_deadbush_Xx.hocus.common.items;

import java.util.List;

import org.codehaus.plexus.util.StringUtils;

import com.xX_deadbush_Xx.hocus.Hocus;
import com.xX_deadbush_Xx.hocus.client.ModItemGroups;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SacrificeKnife extends Item {

	public SacrificeKnife() {
		super(new Properties().maxDamage(50).group(ModItemGroups.ITEMS));
	    this.addPropertyOverride(new ResourceLocation(Hocus.MOD_ID, "bloody"), new IItemPropertyGetter() {
			@Override
			@OnlyIn(Dist.CLIENT)
			public float call(ItemStack stack, World p_call_2_, LivingEntity p_call_3_) {
		    	return isBloody(stack) ? 1.0F : 0.0F;
			}
	    });
	}
	
	@Override
	public boolean onBlockStartBreak(ItemStack itemstack, BlockPos pos, PlayerEntity player) {
		return super.onBlockStartBreak(itemstack, pos, player);
	}

	public float getAttackDamage() {
		return 5.0F;
	}
	
	public static boolean isBloody(ItemStack stack) {
		if(stack.hasTag()) {
 			return !StringUtils.isEmpty(stack.getTag().getString("mobname"));
		}
		return false;
	}
	
	public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		stack.damageItem(1, attacker, (player) -> {
	         player.sendBreakAnimation(EquipmentSlotType.MAINHAND);
	    });
		
		String targetname = Registry.ENTITY_TYPE.getKey(target.getType()).getPath();
		stack.getOrCreateTag().putString("mobname", targetname);
	    return true;
	}
	
	public static void cleanKnife(ItemStack stack) {
		stack.removeChildTag("mobname");
	}
	
	public String readMob(ItemStack stack) {
		if(stack.getOrCreateTag().contains("mobname")) return stack.getTag().getString("mobname");
		return "";
	}
	
    public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
    	String mob = readMob(stack);
        if(StringUtils.isNotEmpty(mob)) tooltip.add(new StringTextComponent("\u00A77Covered in " + mob + " blood"));
    }
}
