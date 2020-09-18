package com.xX_deadbush_Xx.witchcraftmod.api.ritual.activation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.xX_deadbush_Xx.witchcraftmod.common.tile.AbstractRitualCore;
import com.xX_deadbush_Xx.witchcraftmod.common.world.data.PlayerManaStorage;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.eventbus.api.Event;

public abstract class RitualActivationTaskHandler {

	public List<RitualTask<? extends Event>> tasks = new ArrayList<>();
	public final PlayerEntity player;
	public final AbstractRitualCore ritualStone;
	private int ticks;
	private int stopTime = 72000;
	private LazyOptional<PlayerManaStorage> playerenergy;

	protected RitualActivationTaskHandler(AbstractRitualCore tile, PlayerEntity player) {
		this.ritualStone = tile;
		this.player = player;
		playerenergy = player.getCapability(PlayerManaStorage.Capability.get());
	}

	/**
	 * Called when ritual activation is started. Add Tasks here.
	 */
	public abstract void init();

	/**
	 * Removes energy from the player. Returns true if successful
	 * 
	 * @param amount to remove
	 * @return if removing was successful
	 */
	protected boolean consumeEnergy(int amount) {
		if (amount > 0) {
			boolean[] success = { false }; // array fixes scope error

			playerenergy.ifPresent((s) -> {
				if (s.getEnergy() >= amount) {
					success[0] = true;
					s.removeEnergy(amount);
				}
			});
			return success[0];
		} else
			return false;
	}

	public final void tick() {
		this.ticks++;
		List<Integer> indexes = new ArrayList<>();

		for (int i = 0; i < this.tasks.size(); i++) {
			RitualTask<? extends Event> task = this.tasks.get(i);
			if (task.isCompleted()) {
				indexes.add(i);
			} else if (task.getTick() == this.ticks) {
				task.expire();
				if (task.isNecessary()) {
					nextPhase();
					return;
				}

				indexes.add(i);
			}
		}

		// remove tasks
		Collections.sort(indexes, Collections.reverseOrder());
		for (int i : indexes)
			tasks.remove(i);

		if (this.tasks.isEmpty() || this.stopTime <= this.ticks)
			nextPhase();
	}

	private void nextPhase() {
		this.onActivationEnd();
		this.ritualStone.currentritual.ifPresent(ritual -> ritual.enterEffectPhase());
	}

	public void addTask(RitualTask<? extends Event> task) {
		task.handler = this;
		this.tasks.add(task);
	}

	protected void setStopTime(int ticks) {
		this.stopTime = ticks;
	}

	protected void onActivationEnd() {
	}

	public <T extends Event> void handleEvent(T event, Class<? extends RitualTask<T>> clazz) {
		new ArrayList<>(this.tasks).stream().forEach(task -> {
			task.checkEvent(event, clazz);
		});
	}
}
