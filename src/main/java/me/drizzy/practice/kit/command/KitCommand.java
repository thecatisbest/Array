package me.drizzy.practice.kit.command;

import me.drizzy.practice.util.chat.CC;
import me.drizzy.practice.util.command.command.CommandMeta;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

@CommandMeta(label = { "kit", "kithelp" }, permission = "array.dev")
public class KitCommand {
    public void execute(final CommandSender player) {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&m--------&7&m" + StringUtils.repeat("-", 37) + "&b&m--------"));
        player.sendMessage(CC.translate( "&bArray &7» Kit Commands"));
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&m--------&7&m" + StringUtils.repeat("-", 37) + "&b&m--------"));
        player.sendMessage(CC.translate(" &8• &b/kit create &8<&7kit&8> &8- &8&o(&7&oCreate a Kit&8&o)"));
        player.sendMessage(CC.translate(" &8• &b/kit setkb &8<&7kit&8> &8- &8&o(&7&oSet a Kit's Knockback Profile&8&o)"));
        player.sendMessage(CC.translate(" &8• &b/kit remove &8<&7kit&8> &8- &8&o(&7&oDelete a Kit&8&o)"));
        player.sendMessage(CC.translate(" &8• &b/kit hitdelay &8<&7kit&8> (1-20) &8- &8&o(&7&oSet a Kit's Hitdelay&8&o)"));
        player.sendMessage(CC.translate(" &8• &b/kit elo &8<&7kit&8> &8- &8&o(&7&oToggle elo mode for a Kit&8&o)"));
        player.sendMessage(CC.translate(" &8• &b/kit build &8<&7kit&8> &8- &8&o(&7&oToggle build mode for a Kit&8&o)"));
        player.sendMessage(CC.translate(" &8• &b/kit bridge &8<&7kit&8> &8- &8&o(&7&oToggle bridge mode for a Kit&8&o)"));
        player.sendMessage(CC.translate(" &8• &b/kit combo &8<&7kit&8> &8- &8&o(&7&oToggle combo mode for a Kit&8&o)"));
        player.sendMessage(CC.translate(" &8• &b/kit editable &8<&7kit&8> &8- &8&o(&7&oToggle editable mode for a Kit&8&o)"));
        player.sendMessage(CC.translate(" &8• &b/kit lavaKill &8<&7kit&8> &8- &8&o(&7&oToggle lava-kill mode for a Kit&8&o)"));
        player.sendMessage(CC.translate(" &8• &b/kit parkour &8<&7kit&8> &8- &8&o(&7&oToggle parkour mode for a Kit&8&o)"));
        player.sendMessage(CC.translate(" &8• &b/kit disablePartyFFA &8<&7kit&8> &8- &8&o(&7&oToggle party-ffa mode for a Kit&8&o)"));
        player.sendMessage(CC.translate(" &8• &b/kit disablePartySplit &8<&7kit&8> &8- &8&o(&7&oToggle party-split mode for a Kit&8&o)"));
        player.sendMessage(CC.translate(" &8• &b/kit antiFoodLoss &8<&7kit&8> &8- &8&o(&7&oToggle anti-food-loss mode for a Kit&8&o)"));
        player.sendMessage(CC.translate(" &8• &b/kit bowHP &8<&7kit&8> &8- &8&o(&7&oToggle bow-hp mode for a Kit&8&o)"));
        player.sendMessage(CC.translate(" &8• &b/kit healthregen &8<&7kit&8> &8- &8&o(&7&oToggle health-regen mode for a Kit&8&o)"));
        player.sendMessage(CC.translate(" &8• &b/kit infiniteSpeed &8<&7kit&8> &8- &8&o(&7&oToggle infinite-speed mode for a Kit&8&o)"));
        player.sendMessage(CC.translate(" &8• &b/kit infiniteStrength &8<&7kit&8> &8- &8&o(&7&oToggle infinite-strength mode for a Kit&8&o)"));
        player.sendMessage(CC.translate(" &8• &b/kit noItems &8<&7kit&8> &8- &8&o(&7&oToggle no-items mode for a Kit&8&o)"));
        player.sendMessage(CC.translate(" &8• &b/kit showhealth &8<&7kit&8> &8- &8&o(&7&oToggle show-health mode for a Kit&8&o)"));
        player.sendMessage(CC.translate(" &8• &b/kit stickSpawn &8<&7kit&8> &8- &8&o(&7&oToggle Stick-spawn mode for a Kit&8&o)"));
        player.sendMessage(CC.translate(" &8• &b/kit boxuhc &8<&7kit&8> &8- &8&o(&7&oToggle Box-UHC mode for a Kit&8&o)"));
        player.sendMessage(CC.translate(" &8• &b/kit falldamage &8<&7kit&8> &8- &8&o(&7&oToggle Fall Damage for a Kit&8&o)"));
        player.sendMessage(CC.translate(" &8• &b/kit spleef &8<&7kit&8> &8- &8&o(&7&oToggle spleef mode for a Kit&8&o)"));
        player.sendMessage(CC.translate(" &8• &b/kit timed &8<&7kit&8> &8- &8&o(&7&oToggle timed mode for a Kit&8&o)"));
        player.sendMessage(CC.translate(" &8• &b/kit setinv &8- &8&o(&7&oSets the inventory of the kit as your inventory&8&o)"));
        player.sendMessage(CC.translate(" &8• &b/kit getinv &8- &8&o(&7&oGet the inventory of the kit&8&o)"));
        player.sendMessage(CC.translate(" &8• &b/kit list &8- &8&o(&7&oLists All Kits&8&o)"));
        player.sendMessage(CC.translate(" &8• &b/kit save &8- &8&o(&7&oSave All the Kits&8&o)"));
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&m--------&7&m" + StringUtils.repeat("-", 37) + "&b&m--------"));
    }
}
