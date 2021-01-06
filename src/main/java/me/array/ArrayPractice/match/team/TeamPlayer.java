package me.array.ArrayPractice.match.team;

import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class TeamPlayer {

	@Getter private final UUID uuid;
	@Getter private final String username;
	@Getter @Setter private boolean alive = true;
	@Getter @Setter private boolean disconnected;
	@Getter @Setter private int elo;
	@Getter @Setter private int potionsThrown;
	@Getter @Setter private int potionsMissed;
	@Getter @Setter private int hits;
	@Getter @Setter private int combo;
	@Getter @Setter private int longestCombo;

	public TeamPlayer(Player player) {
		this.uuid = player.getUniqueId();
		this.username = player.getName();
	}

	public TeamPlayer(UUID uuid, String username) {
		this.uuid = uuid;
		this.username = username;
	}

	public Player getPlayer() {
		return Bukkit.getPlayer(uuid);
	}

	public String getDisplayName() {
		Player player = getPlayer();
		return player == null ? username : player.getDisplayName();
	}

	public int getPing() {
		Player player = getPlayer();
		return player == null ? 0 : ((CraftPlayer)player).getHandle().ping;
	}

	public double getPotionAccuracy() {
		if (potionsMissed == 0) {
			return 100.0;
		} else if (potionsThrown == potionsMissed) {
			return 50.0;
		}

		return Math.round(100.0D - (((double) potionsMissed / (double) potionsThrown) * 100.0D));
	}

	public void incrementPotionsThrown() {
		potionsThrown++;
	}

	public void incrementPotionsMissed() {
		potionsMissed++;
	}

	public void handleHit() {
		hits++;
		combo++;

		if (combo > longestCombo) {
			longestCombo = combo;
		}
	}

	public void resetCombo() {
		combo = 0;
	}

}