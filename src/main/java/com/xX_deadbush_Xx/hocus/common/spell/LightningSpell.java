package com.xX_deadbush_Xx.hocus.common.spell;

import java.util.Random;

import com.xX_deadbush_Xx.hocus.api.spell.ISpellRenderer;
import com.xX_deadbush_Xx.hocus.api.spell.SpellCast;
import com.xX_deadbush_Xx.hocus.api.util.render.LightningTree;
import com.xX_deadbush_Xx.hocus.client.renderers.spell_renderers.LightningSpellRenderer;
import com.xX_deadbush_Xx.hocus.common.world.data.PlayerSpellCapability;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.Vec3d;

public class LightningSpell extends SpellCast {

	private static final Random rand = new Random();
	private LightningTree tree;
	
	public LightningSpell(PlayerEntity player, ItemStack wand, int... args) {
		super(player, wand, args);
		
		if(args.length > 0)
			this.tree = new LightningTree(Vec3d.ZERO, new Vec3d((double)args[0]/10000, (double)args[1]/10000, (double)args[2]/10000), 6, 1);
	}
	
	public LightningSpell(int ticks, int... args) {
		super(ticks, args);
		
		if(args.length > 0)
			this.tree = new LightningTree(Vec3d.ZERO, new Vec3d((double)args[0]/10000, (double)args[1]/10000, (double)args[2]/10000), 6, 1);
	}
	
	@Override
	public void readBuffer(PacketBuffer buf) {
		super.readBuffer(buf);
		this.tree = new LightningTree(Vec3d.ZERO, new Vec3d((double)args[0]/10000, (double)args[1]/10000, (double)args[2]/10000), 6, 1);
	}
	
	@Override
	public int expectedAmountOfArgs() {
		return 3;
	}
	
	@Override
	public void tick(PlayerEntity player) {
		super.tick(player);
		if(this.ticks > 6)
			PlayerSpellCapability.getSpellCap(this.caster).stopSpell();
		else {
			tree.generateNext(rand, 0.3f, 0.5f);
			tree.generateNext(rand, 0.3f, 0.5f);
		}
	}
	
	@Override
	public CompoundNBT serializeNBT() {
		CompoundNBT nbt =  super.serializeNBT();
		nbt.put("tree", this.tree.serializeNBT());
		return nbt;
	}
	
	@Override
	public void deserializeNBT(CompoundNBT nbt) {
		super.deserializeNBT(nbt);
		this.tree = new LightningTree(Vec3d.ZERO, new Vec3d(args[0]*10000, args[1]*10000, args[2]*10000), 6, 1);
		this.tree.deserializeNBT(nbt);
	}
	
	public LightningTree getTree() {
		return this.tree;
	}

	@Override
	public ISpellRenderer<? extends SpellCast> getRenderer() {
		return LightningSpellRenderer.INSTANCE;
	}
}
