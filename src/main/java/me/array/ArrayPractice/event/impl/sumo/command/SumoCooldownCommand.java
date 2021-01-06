package me.array.ArrayPractice.event.impl.sumo.command;

import me.array.ArrayPractice.Practice;
import me.array.ArrayPractice.util.external.CC;
import me.array.ArrayPractice.util.external.Cooldown;
import com.qrakn.honcho.command.CommandMeta;
import org.bukkit.command.CommandSender;

@CommandMeta(label = "sumo cooldown", permission = "practice.staff")
public class SumoCooldownCommand {

	public void execute(CommandSender sender) {
		if (Practice.get().getSumoManager().getCooldown().hasExpired()) {
			sender.sendMessage(CC.RED + "There isn't any cooldown for the Sumo Event.");
			return;
		}

		sender.sendMessage(CC.GREEN + "You reset the Sumo Event cooldown.");

		Practice.get().getSumoManager().setCooldown(new Cooldown(0));
	}

}
