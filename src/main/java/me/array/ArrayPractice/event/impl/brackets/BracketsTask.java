package me.array.ArrayPractice.event.impl.brackets;

import lombok.Getter;
import me.array.ArrayPractice.Array;
import org.bukkit.scheduler.BukkitRunnable;

@Getter
public abstract class BracketsTask extends BukkitRunnable {

	private int ticks;
	private Brackets brackets;
	private BracketsState eventState;

	public BracketsTask(Brackets brackets, BracketsState eventState) {
		this.brackets = brackets;
		this.eventState = eventState;
	}

	@Override
	public void run() {
		if (Array.get().getBracketsManager().getActiveBrackets() == null ||
		    !Array.get().getBracketsManager().getActiveBrackets().equals(brackets) || brackets.getState() != eventState) {
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