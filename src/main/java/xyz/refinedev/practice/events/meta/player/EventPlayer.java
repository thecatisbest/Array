package xyz.refinedev.practice.events.meta.player;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import xyz.refinedev.practice.events.meta.player.EventPlayerState;
import xyz.refinedev.practice.util.other.PlayerSnapshot;

@Getter
@Setter
public class EventPlayer extends PlayerSnapshot {

	private EventPlayerState state = EventPlayerState.WAITING;
	private int roundWins = 0;

	public EventPlayer(Player player) {
		super(player.getUniqueId(), player.getName());
	}

	public void incrementRoundWins() {
		this.roundWins++;
	}

}