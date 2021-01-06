package me.array.ArrayPractice.event.impl.parkour;

import lombok.Getter;
import me.array.ArrayPractice.Array;
import org.bukkit.scheduler.BukkitRunnable;

@Getter
public abstract class ParkourTask extends BukkitRunnable {

	private int ticks;
	private Parkour parkour;
	private ParkourState eventState;

	public ParkourTask(Parkour parkour, ParkourState eventState) {
		this.parkour = parkour;
		this.eventState = eventState;
	}

	@Override
	public void run() {
		if (Array.get().getParkourManager().getActiveParkour() == null ||
		    !Array.get().getParkourManager().getActiveParkour().equals(parkour) || parkour.getState() != eventState) {
			cancel();
			return;
		}

		onRun();

		ticks++;
	}

	public int getSeconds() {
		return 3 - ticks;
	}

	public abstract void onRun();

}