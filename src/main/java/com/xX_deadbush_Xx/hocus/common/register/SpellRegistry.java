package com.xX_deadbush_Xx.hocus.common.register;

import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.xX_deadbush_Xx.hocus.api.spell.SpellCast;
import com.xX_deadbush_Xx.hocus.common.spell.DestructionSpell;
import com.xX_deadbush_Xx.hocus.common.spell.LightningSpell;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;

public class SpellRegistry {
	public static final SpellRegistry INSTANCE = new SpellRegistry();
	private BiMap<Integer, Class<? extends SpellCast>> spells = HashBiMap.create();
	private Map<Integer, IFactory> factories = new HashMap<>();
	private static int id = 0;
	
	private void register(Class<? extends SpellCast> clazz, IFactory factory) {
		id++;
		if(this.spells.values().contains(clazz)) throw new IllegalArgumentException("Spell with the same class registered twice: " + clazz.getName()); 
		this.factories.put(id, factory);
		this.spells.put(id, clazz);
	}
	
	public static IFactory getFactory(int id) {
		return INSTANCE.factories.get(id);
	}
	
	public static Class<? extends SpellCast> getClass(int id) {
		return INSTANCE.spells.get(id);
	}
	
	public static SpellCast create(int id, int ticks, int... args) {
		IFactory factory = INSTANCE.factories.get(id);
		return factory == null ? null : factory.create(ticks, args);
	}
	
	public static int getID(SpellCast spell) {
		return INSTANCE.spells.inverse().get(spell.getClass());
	}
	
	public void registerSpells() {
		register(DestructionSpell.class, DestructionSpell::new);
		register(LightningSpell.class, LightningSpell::new);
	}
	
	public static INBT serializeSpell(SpellCast spell) {
		CompoundNBT nbt = new CompoundNBT();
		if(spell != null) {
			nbt.putInt("id", getID(spell));
			nbt.put("spell", spell.serializeNBT());	
		}
		
		return nbt;
	}
	
	public static SpellCast deserializeSpell(INBT nbt) {
		CompoundNBT compound = ((CompoundNBT) nbt);
		int id = compound.getInt("id");
		int ticks = compound.getInt("ticks");
		int[] args = compound.getIntArray("args");
		SpellCast spell = create(id, ticks, args);
		
		if(spell != null) {
			if(spell.expectedAmountOfArgs() != args.length)
				return null;
			else
				spell.deserializeNBT(compound);
		}
		
		return spell;
	}
	
	@FunctionalInterface
	private interface IFactory {
		public SpellCast create(int ticks, int... args);
	}
}