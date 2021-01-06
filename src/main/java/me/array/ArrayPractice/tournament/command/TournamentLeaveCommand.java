package me.array.ArrayPractice.tournament.command;

import com.qrakn.honcho.command.CommandMeta;
import me.array.ArrayPractice.tournament.Tournament;
import me.array.ArrayPractice.party.Party;
import me.array.ArrayPractice.profile.Profile;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

@CommandMeta(label = "tournament leave")
public class TournamentLeaveCommand {

    public void execute(Player player) {
        if (Tournament.CURRENT_TOURNAMENT == null || Tournament.CURRENT_TOURNAMENT.hasStarted()) {
            player.sendMessage(ChatColor.RED + "There isn't a Tournament you can leave");
            return;
        }
        Party party = Profile.getByUuid(player.getUniqueId()).getParty();
        if (party == null) {
            player.sendMessage("You aren't currently in a Tournament");
            return;
        }
        if (!Tournament.CURRENT_TOURNAMENT.isParticipating(player)) {
            player.sendMessage("You aren't currently in a Tournament");
            return;
        }
        if (!party.isLeader(player.getUniqueId())) {
            player.sendMessage(ChatColor.RED + "&cOnly Leaders can do this");
            return;
        }
        Tournament.CURRENT_TOURNAMENT.leave(party);
    }
}


