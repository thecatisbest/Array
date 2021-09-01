package xyz.refinedev.practice.cmds.standalone;

import org.bukkit.entity.Player;
import xyz.refinedev.practice.Locale;
import xyz.refinedev.practice.match.menu.MatchSpectateMenu;
import xyz.refinedev.practice.profile.Profile;
import xyz.refinedev.practice.util.command.annotation.Command;
import xyz.refinedev.practice.util.command.annotation.Sender;

/**
 * This Project is property of Refine Development © 2021
 * Redistribution of this Project is not allowed
 *
 * @author Drizzy
 * Created: 8/30/2021
 * Project: Array
 */

public class SpectateMenuCommand {

    @Command(name = "", desc = "Open spectate menu")
    public void spectateMenu(@Sender Player player) {
        Profile profile = Profile.getByPlayer(player);
        if (profile.getMatch() == null) {
            player.sendMessage(Locale.MATCH_NOT_IN_SELF.toString());
            return;
        }
        new MatchSpectateMenu(profile.getMatch()).openMenu(player);
    }
}