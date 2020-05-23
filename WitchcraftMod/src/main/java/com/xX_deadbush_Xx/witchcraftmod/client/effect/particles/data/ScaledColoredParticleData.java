package com.xX_deadbush_Xx.witchcraftmod.client.effect.particles.data;

import java.util.Locale;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleType;
import net.minecraft.util.registry.Registry;

public class ScaledColoredParticleData extends ParticleType<ScaledColoredParticleData> implements IParticleData {
	private int color;
	private float scale;
	private boolean show;
	private ParticleType<ScaledColoredParticleData> type;
	
	private static final IDeserializer<ScaledColoredParticleData> DESERIALIZER = new IParticleData.IDeserializer<ScaledColoredParticleData>() {
	      public ScaledColoredParticleData deserialize(ParticleType<ScaledColoredParticleData> particleTypeIn, StringReader reader) throws CommandSyntaxException {
	          reader.expect(' ');
	          boolean show = reader.readBoolean();
	          reader.expect(' ');
	          int color = reader.readInt();
	          reader.expect(' ');
	          float scale = (float)reader.readDouble();
	           System.out.println("deserialized");
	          return new ScaledColoredParticleData(particleTypeIn, show, color, scale);
	       }

	       public ScaledColoredParticleData read(ParticleType<ScaledColoredParticleData> particleTypeIn, PacketBuffer buffer) {
	           System.out.println("read");
	    	   return new ScaledColoredParticleData(particleTypeIn, buffer.readBoolean(), buffer.readInt(), buffer.readFloat());
	       }
	};

   	public ScaledColoredParticleData(ParticleType<ScaledColoredParticleData> particleTypeIn, boolean alwaysShow, int color, float scale) {
	   super(alwaysShow, DESERIALIZER);
	   this.type = particleTypeIn;
	   this.show = alwaysShow;
	   this.color = color;
	   this.scale = scale;
	   
	   System.out.println(color);
   	}

   	public ParticleType<ScaledColoredParticleData> getType() {
	   	return this.type;
   	}

   	public void write(PacketBuffer buffer) {
        buffer.writeBoolean(this.show);
        buffer.writeInt(this.color);
        buffer.writeFloat(this.scale);
   	}

   	public String getParameters() {
   		String formatted = String.format(Locale.ROOT, "%b %d %.4f", Registry.PARTICLE_TYPE.getKey(this.getType()), this.show, this.color, this. scale);
   		System.out.println(formatted);
        return formatted;
   	}
	
	public float getScale() {
		return scale;
	}
	
	public int getColor() {
		return color;
	}
}
