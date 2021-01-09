package me.array.ArrayPractice.profile.hotbar;

import me.array.ArrayPractice.Practice;
import me.array.ArrayPractice.event.impl.brackets.Brackets;
import me.array.ArrayPractice.event.impl.lms.LMS;
import me.array.ArrayPractice.event.impl.parkour.Parkour;
import me.array.ArrayPractice.event.impl.skywars.SkyWars;
import me.array.ArrayPractice.event.impl.spleef.Spleef;
import me.array.ArrayPractice.event.impl.sumo.Sumo;
import me.array.ArrayPractice.event.menu.ActiveEventSelectEventMenu;
import me.array.ArrayPractice.kit.menu.KitEditorSelectKitMenu;
import me.array.ArrayPractice.party.Party;
import me.array.ArrayPractice.party.PartyMessage;
import me.array.ArrayPractice.party.menu.ManagePartySettings;
import me.array.ArrayPractice.party.menu.OtherPartiesMenu;
import me.array.ArrayPractice.party.menu.PartyEventSelectEventMenu;
import me.array.ArrayPractice.profile.Profile;
import me.array.ArrayPractice.profile.ProfileState;
import me.array.ArrayPractice.profile.menu.PlayerMenu;
import me.array.ArrayPractice.profile.meta.ProfileRematchData;
import me.array.ArrayPractice.profile.options.OptionsMenu;
import me.array.ArrayPractice.queue.QueueType;
import me.array.ArrayPractice.queue.menu.QueueSelectKitMenu;
import me.array.ArrayPractice.util.external.CC;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class HotbarListener implements Listener
{
    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerInteract(final PlayerInteractEvent event) {
        if (event.getItem() != null && event.getAction().name().contains("RIGHT")) {
            final Player player = event.getPlayer();
            final Profile profile = Profile.getByUuid(player.getUniqueId());
            final HotbarItem hotbarItem = Hotbar.fromItemStack(event.getItem());
            if (hotbarItem == null) {
                return;
            }
            event.setCancelled(true);
            switch (hotbarItem) {
                case QUEUE_JOIN_RANKED: {
                    if (!profile.isBusy(player)) {
                        new QueueSelectKitMenu(QueueType.RANKED).openMenu(event.getPlayer());
                        break;
                    }
                    break;
                }
                case QUEUE_JOIN_UNRANKED: {
                    if (!profile.isBusy(player)) {
                        new QueueSelectKitMenu(QueueType.UNRANKED).openMenu(event.getPlayer());
                        break;
                    }
                    break;
                }
                case QUEUE_JOIN_KITPVP: {
                    if (!profile.isBusy(player)) {
                        player.sendMessage(CC.translate("&7This feature will be available in &b&lSeason 1&7!"));
                    }
                }
                case QUEUE_LEAVE: {
                    if (profile.isInQueue()) {
                        profile.getQueue().removePlayer(profile.getQueueProfile());
                        break;
                    }
                    break;
                }
                case PARTY_EVENTS: {
                    new PartyEventSelectEventMenu().openMenu(player);
                    break;
                }
                case OTHER_PARTIES: {
                    new OtherPartiesMenu().openMenu(event.getPlayer());
                    break;
                }
                case PARTY_INFO: {
                    player.performCommand("party info");
                    break;
                }
                case PARTY_SETTINGS: {
                    new ManagePartySettings().openMenu(event.getPlayer());
                    break;
                }
                case SETTINGS_MENU: {
                    new OptionsMenu().openMenu(event.getPlayer());
                    break;
                }
                case LEADERBOARDS_MENU: {
                    new PlayerMenu().openMenu(event.getPlayer());
                    break;
                }
                case KIT_EDITOR: {
                    if (profile.isInLobby() || profile.isInQueue()) {
                        new KitEditorSelectKitMenu().openMenu(event.getPlayer());
                        break;
                    }
                    break;
                }
                case PARTY_CREATE: {
                    if (profile.getParty() != null) {
                        player.sendMessage(CC.RED + "You already have a party.");
                        return;
                    }
                    if (!profile.isInLobby()) {
                        player.sendMessage(CC.RED + "You must be in the lobby to create a party.");
                        return;
                    }
                    profile.setParty(new Party(player));
                    profile.refreshHotbar();
                    player.sendMessage(PartyMessage.CREATED.format());
                    break;
                }
                case PARTY_DISBAND: {
                    if (profile.getParty() == null) {
                        player.sendMessage(CC.RED + "You do not have a party.");
                        return;
                    }
                    if (!profile.getParty().isLeader(player.getUniqueId())) {
                        player.sendMessage(CC.RED + "You are not the leader of your party.");
                        return;
                    }
                    profile.getParty().disband();
                    break;
                }
                case PARTY_INFORMATION: {
                    if (profile.getParty() == null) {
                        player.sendMessage(CC.RED + "You do not have a party.");
                        return;
                    }
                    profile.getParty().sendInformation(player);
                    break;
                }
                case PARTY_LEAVE: {
                    if (profile.getParty() == null) {
                        player.sendMessage(CC.RED + "You do not have a party.");
                        return;
                    }
                    if (profile.getParty().getLeader().getUuid().equals(player.getUniqueId())) {
                        profile.getParty().disband();
                        break;
                    }
                    profile.getParty().leave(player, false);
                    break;
                }
                case EVENT_JOIN: {
                    new ActiveEventSelectEventMenu().openMenu(player);
                    break;
                }
                case SUMO_LEAVE: {
                    final Sumo activeSumo = Practice.getInstance().getSumoManager().getActiveSumo();
                    if (activeSumo == null) {
                        player.sendMessage(CC.RED + "There is no active sumo.");
                        return;
                    }
                    if (!profile.isInSumo() || !activeSumo.getEventPlayers().containsKey(player.getUniqueId())) {
                        player.sendMessage(CC.RED + "You are not apart of the active sumo.");
                        return;
                    }
                    Practice.getInstance().getSumoManager().getActiveSumo().handleLeave(player);
                    break;
                }
                case BRACKETS_LEAVE: {
                    final Brackets activeBrackets = Practice.getInstance().getBracketsManager().getActiveBrackets();
                    if (activeBrackets == null) {
                        player.sendMessage(CC.RED + "There is no active brackets.");
                        return;
                    }
                    if (!profile.isInBrackets() || !activeBrackets.getEventPlayers().containsKey(player.getUniqueId())) {
                        player.sendMessage(CC.RED + "You are not apart of the active brackets.");
                        return;
                    }
                    Practice.getInstance().getBracketsManager().getActiveBrackets().handleLeave(player);
                    break;
                }
                case SKYWARS_LEAVE: {
                    final SkyWars activeSkywars = Practice.getInstance().getSkyWarsManager().getActiveSkyWars();
                    if (activeSkywars == null) {
                        player.sendMessage(CC.RED + "There is no active skywars event.");
                        return;
                    }
                    if (!profile.isInSkyWars() || !activeSkywars.getEventPlayers().containsKey(player.getUniqueId())) {
                        player.sendMessage(CC.RED + "You are not apart of the active skywars event.");
                        return;
                    }
                    Practice.getInstance().getSkyWarsManager().getActiveSkyWars().handleLeave(player);
                    break;
                }
                case LMS_LEAVE: {
                    final LMS activeLMS = Practice.getInstance().getLMSManager().getActiveLMS();
                    if (activeLMS == null) {
                        player.sendMessage(CC.RED + "There is no active LMS.");
                        return;
                    }
                    if (!profile.isInLMS() || !activeLMS.getEventPlayers().containsKey(player.getUniqueId())) {
                        player.sendMessage(CC.RED + "You are not apart of the active LMS.");
                        return;
                    }
                    Practice.getInstance().getLMSManager().getActiveLMS().handleLeave(player);
                    break;
                }
                case PARKOUR_LEAVE: {
                    final Parkour activeParkour = Practice.getInstance().getParkourManager().getActiveParkour();
                    if (activeParkour == null) {
                        player.sendMessage(CC.RED + "There is no active Parkour.");
                        return;
                    }
                    if (!profile.isInParkour() || !activeParkour.getEventPlayers().containsKey(player.getUniqueId())) {
                        player.sendMessage(CC.RED + "You are not apart of the active Parkour.");
                        return;
                    }
                    Practice.getInstance().getParkourManager().getActiveParkour().handleLeave(player);
                    break;
                }
                case SPLEEF_LEAVE: {
                    final Spleef activeSpleef = Practice.getInstance().getSpleefManager().getActiveSpleef();
                    if (activeSpleef == null) {
                        player.sendMessage(CC.RED + "There is no active Spleef.");
                        return;
                    }
                    if (!profile.isInSpleef() || !activeSpleef.getEventPlayers().containsKey(player.getUniqueId())) {
                        player.sendMessage(CC.RED + "You are not apart of the active Spleef.");
                        return;
                    }
                    Practice.getInstance().getSpleefManager().getActiveSpleef().handleLeave(player);
                    break;
                }
                case SPECTATE_STOP: {
                    if (profile.isInFight() && !profile.getMatch().getTeamPlayer(player).isAlive()) {
                        profile.getMatch().getTeamPlayer(player).setDisconnected(true);
                        profile.setState(ProfileState.IN_LOBBY);
                        profile.setMatch(null);
                        break;
                    }
                    if (!profile.isSpectating()) {
                        player.sendMessage(CC.RED + "You are not spectating a match.");
                        break;
                    }
                    if (profile.getMatch() != null) {
                        profile.getMatch().removeSpectator(player);
                        break;
                    }
                    if (profile.getSumo() != null) {
                        profile.getSumo().removeSpectator(player);
                        break;
                    }
                    if (profile.getBrackets() != null) {
                        profile.getBrackets().removeSpectator(player);
                        break;
                    }
                    if (profile.getLms() != null) {
                        profile.getLms().removeSpectator(player);
                        break;
                    }
                    if (profile.getParkour() != null) {
                        profile.getParkour().removeSpectator(player);
                        break;
                    }
                    if (profile.getSpleef() != null) {
                        profile.getSpleef().removeSpectator(player);
                        break;
                    }
                    if (profile.getSkyWars() != null) {
                        profile.getSkyWars().removeSpectator(player);
                        break;
                    }
                    break;
                }
                case REMATCH_REQUEST:
                case REMATCH_ACCEPT: {
                    if (profile.getRematchData() == null) {
                        player.sendMessage(CC.RED + "You do not have anyone to re-match.");
                        return;
                    }
                    profile.checkForHotbarUpdate();
                    if (profile.getRematchData() == null) {
                        player.sendMessage(CC.RED + "That player is no longer available.");
                        return;
                    }
                    final ProfileRematchData profileRematchData = profile.getRematchData();
                    if (profileRematchData.isReceive()) {
                        profileRematchData.accept();
                    }
                    else {
                        if (profileRematchData.isSent()) {
                            player.sendMessage(CC.RED + "You have already sent a rematch request to that player.");
                            return;
                        }
                        profileRematchData.request();
                    }
                    break;
                }
                default: {}
            }
        }
    }
}

