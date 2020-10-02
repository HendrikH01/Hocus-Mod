package com.xX_deadbush_Xx.witchcraftmod.client.effect.particles.data;

import java.util.Locale;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.xX_deadbush_Xx.witchcraftmod.client.effect.particles.ModParticles;

import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleType;
import net.minecraft.util.registry.Registry;

public class ManawaveParticleData extends ParticleType<ManawaveParticleData> implements IParticleData {

	private int color;
	private int tick;
	private boolean show;
	private double thickness;
	
   	public ManawaveParticleData(boolean alwaysShow, int color, int tick, double thickness) {
	   super(alwaysShow, new IParticleData.IDeserializer<ManawaveParticleData>() {
		      public ManawaveParticleData deserialize(ParticleType<ManawaveParticleData> particleTypeIn, StringReader reader) throws CommandSyntaxException {
		          reader.expect(' ');
		          boolean show = reader.readBoolean();
		          reader.expect(' ');
		          int color = reader.readInt();
		          reader.expect(' ');
		          int tick = reader.readInt();
		          reader.expect(' ');
		          double thickness = reader.readDouble();
		          return new ManawaveParticleData(show, color, tick, thickness);
		       }

		       public ManawaveParticleData read(ParticleType<ManawaveParticleData> particleTypeIn, PacketBuffer buffer) {
		    	   return new ManawaveParticleData(buffer.readBoolean(), buffer.readInt(), buffer.readInt(), buffer.readDouble());
		       }
	   });
	   this.show = alwaysShow;
	   this.color = color;
	   this.tick = tick;
	   this.thickness = thickness;
   	}
   	
	public void write(PacketBuffer buffer) {
        buffer.writeBoolean(this.show);
        buffer.writeInt(this.color);
        buffer.writeInt(this.tick);
   	}

   	public String getParameters() {
        return String.format(Locale.ROOT, "%b %d %d", Registry.PARTICLE_TYPE.getKey(this), this.show, this.color, this.tick);
   	}
	
	public int getTick() {
		return tick;
	}
	
	public int getColor() {
		return color;
	}

	@Override
	public ParticleType<?> getType() {
		return ModParticles.MANAWAVE.get();
	}

	public double getThickness() {
		return this.thickness;
	}
}