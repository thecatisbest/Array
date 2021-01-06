package me.array.ArrayPractice.event.impl.parkour.command;

import com.qrakn.honcho.command.CommandMeta;
import me.array.ArrayPractice.Practice;
import me.array.ArrayPractice.event.impl.parkour.Parkour;
import me.array.ArrayPractice.profile.Profile;
import me.array.ArrayPractice.util.external.CC;
import org.bukkit.entity.Player;

@CommandMeta(label = { "parkour host" }, permission = "practice.host")
public class ParkourHostCommand {

	public static void execute(Player player) {
		if (Practice.get().getParkourManager().getActiveParkour() != null) {
			player.sendMessage(CC.RED + "There is already an active Parkour Event.");
			return;
		}

		if (!Practice.get().getParkourManager().getCooldown().hasExpired()) {
			player.sendMessage(CC.RED + "There is an active cooldown for the Parkour Event.");
			return;
		}

		Practice.get().getParkourManager().setActiveParkour(new Parkour(player));

		for (Player other : Practice.get().getServer().getOnlinePlayers()) {
			Profile profile = Profile.getByUuid(other.getUniqueId());

			if (profile.isInLobby()) {
				if (!profile.getKitEditor().isActive()) {
					profile.refreshHotbar();
				}
			}
		}
	}

}
