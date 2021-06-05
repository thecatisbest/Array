package me.drizzy.practice.party.menu;

import lombok.AllArgsConstructor;
import me.drizzy.practice.Locale;
import me.drizzy.practice.arena.Arena;
import me.drizzy.practice.essentials.Essentials;
import me.drizzy.practice.kit.Kit;
import me.drizzy.practice.match.Match;
import me.drizzy.practice.match.team.Team;
import me.drizzy.practice.match.team.TeamPlayer;
import me.drizzy.practice.match.types.HCFMatch;
import me.drizzy.practice.party.Party;
import me.drizzy.practice.party.enums.PartyEventType;
import me.drizzy.practice.profile.Profile;
import me.drizzy.practice.util.chat.CC;
import me.drizzy.practice.util.inventory.ItemBuilder;
import me.drizzy.practice.util.menu.Button;
import me.drizzy.practice.util.menu.Menu;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class PartyEventSelectEventMenu extends Menu {

    @Override
    public String getTitle(final Player player) {
        return "&cSelect a party events";
    }

    @Override
    public Map<Integer, Button> getButtons(final Player player) {
        final Map<Integer, Button> buttons = new HashMap<>();
        if (Essentials.getMeta().isHCFEnabled()) {
            buttons.put(2, new SelectEventButton(PartyEventType.SPLIT));
            buttons.put(4, new SelectEventButton(PartyEventType.FFA));
            buttons.put(6, new SelectEventButton(PartyEventType.HCF));
        } else {
            buttons.put(3, new SelectEventButton(PartyEventType.SPLIT));
            buttons.put(5, new SelectEventButton(PartyEventType.FFA));
        }
        return buttons;
    }

    @AllArgsConstructor
    private static class SelectEventButton extends Button {

        private final PartyEventType partyEventType;

        @Override
        public ItemStack getButtonItem(final Player player) {
            List<String> lore = new ArrayList<>();
            lore.add(CC.SB_BAR);
            if (this.partyEventType == PartyEventType.FFA) {
                lore.add(CC.translate("&7&oUnleash your party in"));
                lore.add(CC.translate("&7&oa Free For All Match"));
                lore.add(CC.translate("&7&oLast player standing wins!"));
            }
            if (this.partyEventType == PartyEventType.SPLIT) {
                lore.add(CC.translate("&7&oSplit your party into"));
                lore.add(CC.translate("&7&oTwo Teams and the Last"));
                lore.add(CC.translate("&7&oTeam remaining wins!"));
            }
            if (this.partyEventType == PartyEventType.HCF) {
                lore.add(CC.translate("&7&oSplit your party into"));
                lore.add(CC.translate("&7&oTwo Teams with HCF Kits,"));
                lore.add(CC.translate("&7&oLast Team standing wins!"));
            }
            lore.add(CC.SB_BAR);
            return new ItemBuilder(this.partyEventType.getMaterial()).name("&c&l" + this.partyEventType.getName()).lore(lore).build();
        }

        @Override
        public void clicked(final Player player, final ClickType clickType) {
            Profile profile = Profile.getByUuid(player.getUniqueId());

            if (profile.getParty() == null) {
                player.sendMessage(Locale.PARTY_DONOTHAVE.toString());
                return;
            }

            for (Player players : profile.getParty().getPlayers()) {
                Profile profile1 = Profile.getByPlayer(players);
                if (profile1.isBusy()) {
                    player.sendMessage(CC.translate("&cYour party members are busy right now, please try again."));
                    return;
                }
            }

            if (this.partyEventType.equals(PartyEventType.HCF) && profile.getParty().getPlayers().size() < 3) {
                player.sendMessage(CC.translate("&7You need at least 3  members to start an HCF Event"));
                return;
            }

            if (this.partyEventType == PartyEventType.FFA || this.partyEventType == PartyEventType.SPLIT) {
                Menu.currentlyOpenedMenus.get(player.getName()).setClosedByMenu(true);
                new PartyEventSelectKitMenu(this.partyEventType).openMenu(player);
            } else {
                player.closeInventory();

                if (profile.getParty() == null) {
                    player.sendMessage(Locale.PARTY_DONOTHAVE.toString());
                    return;
                }
                if (profile.getParty().getTeamPlayers().size() <= 1) {
                    player.sendMessage(CC.RED + "You do not have enough players in your party to start a party events.");
                    return;
                }

                Party party = profile.getParty();
                Arena arena;

                if (this.partyEventType.equals(PartyEventType.HCF)) {
                    arena = Arena.getRandom(Kit.getByName("HCFTeamFight"));
                } else {
                    player.sendMessage(CC.RED + "There are no available arenas.");
                    return;
                }

                if (arena == null) {
                    player.sendMessage(CC.RED + "There are no available arenas.");
                    return;
                }

                Team teamA = new Team(new TeamPlayer(party.getPlayers().get(0)));
                Team teamB = new Team(new TeamPlayer(party.getPlayers().get(1)));

                List<Player> players = new ArrayList<>(party.getPlayers());
                Collections.shuffle(players);

                Match match = new HCFMatch(teamA, teamB, arena);

                for (Player otherPlayer : players) {
                    if (!teamA.getLeader().getUuid().equals(otherPlayer.getUniqueId())) {
                        if (teamB.getLeader().getUuid().equals(otherPlayer.getUniqueId())) {
                            continue;
                        }
                        if (teamA.getTeamPlayers().size() > teamB.getTeamPlayers().size()) {
                            teamB.getTeamPlayers().add(new TeamPlayer(otherPlayer));
                        }
                        else {
                            teamA.getTeamPlayers().add(new TeamPlayer(otherPlayer));
                        }
                    }
                }

                match.start();
            }
        }
    }
}
