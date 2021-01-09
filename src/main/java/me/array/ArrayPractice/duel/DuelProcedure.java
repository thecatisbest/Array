package me.array.ArrayPractice.duel;

import me.array.ArrayPractice.arena.Arena;
import me.array.ArrayPractice.kit.Kit;
import me.array.ArrayPractice.profile.Profile;
import me.array.ArrayPractice.util.external.CC;
import me.array.ArrayPractice.util.external.ChatComponentBuilder;
import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.entity.Player;

public class DuelProcedure {

    @Getter
    private final boolean party;
    @Getter
    private final Player sender;
    @Getter
    private final Player target;
    @Getter
    @Setter
    private Kit kit;
    @Getter
    @Setter
    private Arena arena;

    public DuelProcedure(Player sender, Player target, boolean party) {
        this.sender = sender;
        this.target = target;
        this.party = party;
    }

    public void send() {
        if (!sender.isOnline() || !target.isOnline()) {
            return;
        }

        DuelRequest request = new DuelRequest(sender.getUniqueId(), party);
        request.setKit(kit);
        request.setArena(arena);

        Profile senderProfile = Profile.getByUuid(sender.getUniqueId());
        senderProfile.setDuelProcedure(null);
        senderProfile.getSentDuelRequests().put(target.getUniqueId(), request);

        this.sender.sendMessage(CC.translate("&e&l(Duel) &fYou sent a duel request to &b" + this.target.getName() + "&f with kit &b" + (this.kit.getName())));
        this.target.sendMessage(CC.translate("&e&l(Duel) &b" + this.sender.getName() + " &fhas sent you a duel request with kit &b" + (this.kit.getName())));
        target.spigot().sendMessage(new ChatComponentBuilder("")
                .parse("&a(Click to accept)")
                .attachToEachPart(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/duel accept " + sender.getName()))
                .attachToEachPart(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ChatComponentBuilder(CC.GREEN + "Click to accept this duel invite.").create()))
                .create());
    }

}
