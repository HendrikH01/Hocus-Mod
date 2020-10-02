package com.xX_deadbush_Xx.witchcraftmod.client.effect.particles.data;

import java.util.Locale;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleType;
import net.minecraft.util.registry.Registry;

public abstract class ScaledColoredParticleData extends ParticleType<ScaledColoredParticleData> implements IParticleData {

	private int color;
	private float scale;
	private boolean show;
	
   	public ScaledColoredParticleData(IFactory factory, boolean alwaysShow, int color, float scale) {
	   super(alwaysShow, new IParticleData.IDeserializer<ScaledColoredParticleData>() {
		      public ScaledColoredParticleData deserialize(ParticleType<ScaledColoredParticleData> particleTypeIn, StringReader reader) throws CommandSyntaxException {
		          reader.expect(' ');
		          boolean show = reader.readBoolean();
		          reader.expect(' ');
		          int color = reader.readInt();
		          reader.expect(' ');
		          float scale = (float)reader.readDouble();
		          return factory.create(show, color, scale);
		       }

		       public ScaledColoredParticleData read(ParticleType<ScaledColoredParticleData> particleTypeIn, PacketBuffer buffer) {
		    	   return factory.create(buffer.readBoolean(), buffer.readInt(), buffer.readFloat());
		       }
	   });
	   this.show = alwaysShow;
	   this.color = color;
	   this.scale = scale;
   	}
   	
	public void write(PacketBuffer buffer) {
        buffer.writeBoolean(this.show);
        buffer.writeInt(this.color);
        buffer.writeFloat(this.scale);
   	}

   	public String getParameters() {
   		return String.format(Locale.ROOT, "%b %d %.4f", Registry.PARTICLE_TYPE.getKey(this), this.show, this.color, this.scale);
   	}
	
	public float getScale() {
		return scale;
	}
	
	public int getColor() {
		return color;
	}
	
	@FunctionalInterface
	public interface IFactory {
		public ScaledColoredParticleData create(boolean alwaysShow, int color, float scale);
	}
}