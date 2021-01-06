

package me.array.ArrayPractice.duel.command;

import me.array.ArrayPractice.duel.DuelProcedure;
import me.array.ArrayPractice.profile.Profile;
import me.array.ArrayPractice.duel.menu.DuelSelectKitMenu;
import me.array.ArrayPractice.util.external.CC;
import com.qrakn.honcho.command.CPL;
import org.bukkit.entity.Player;
import com.qrakn.honcho.command.CommandMeta;

@CommandMeta(label = { "duel" })
public class DuelCommand
{
    public void execute(final Player player, @CPL("player") final Player target) {
        if (target == null) {
            player.sendMessage(CC.RED + "A player with that name could not be found.");
            return;
        }
        if (player.hasMetadata("frozen")) {
            player.sendMessage(CC.RED + "You cannot duel a player while being frozen.");
            return;
        }
        if (target.hasMetadata("frozen")) {
            player.sendMessage(CC.RED + "You cannot duel a player who's frozen.");
            return;
        }
        if (player.getUniqueId().equals(target.getUniqueId())) {
            player.sendMessage(CC.RED + "You cannot duel yourself.");
            return;
        }
        final Profile senderProfile = Profile.getByUuid(player.getUniqueId());
        final Profile receiverProfile = Profile.getByUuid(target.getUniqueId());
        if (senderProfile.isBusy(player)) {
            player.sendMessage(CC.RED + "You cannot duel anyone right now.");
            return;
        }
        if (receiverProfile.isBusy(target)) {
            player.sendMessage(CC.translate(CC.RED + target.getDisplayName()) + CC.RED + " is currently busy.");
            return;
        }
        if (!receiverProfile.getOptions().isReceiveDuelRequests()) {
            player.sendMessage(CC.RED + "That player is not accepting any duel requests at the moment.");
            return;
        }
        if (!senderProfile.canSendDuelRequest(player)) {
            player.sendMessage(CC.RED + "You have already sent that player a duel request.");
            return;
        }
        if (senderProfile.getParty() != null && receiverProfile.getParty() == null) {
            player.sendMessage(CC.RED + "That player is not in a party.");
            return;
        }
        final DuelProcedure procedure = new DuelProcedure(player, target, senderProfile.getParty() != null);
        senderProfile.setDuelProcedure(procedure);
        new DuelSelectKitMenu().openMenu(player);
    }
}