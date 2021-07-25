package xyz.refinedev.practice;

import lombok.Getter;
import xyz.refinedev.practice.util.chat.CC;
import xyz.refinedev.practice.util.config.BasicConfigurationFile;
import xyz.refinedev.practice.util.config.Replacement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * This Project is the property of Refine Development © 2021
 * Redistribution of this Project is not allowed
 *
 * @author Drizzy
 * Created at 4/11/2021
 * Project: Array
 */

public enum Locale {

    CHAT_BAR("CHAT_BAR", "&c&m--------&7&m-------------------------------------&c&m--------"),
    MENU_BAR("MENU_BAR", "&c&m-------&7&m---------&c&m--------"),

    PARTY_INVITED("PARTY.INVITED", "&8[&c&lParty&8] &7You have been invited to join &c<leader>'s &7party."),
    PARTY_CLICK_TO_JOIN("PARTY.CLICK_TO_JOIN", "&8[&c&lParty&8] &c(Click to accept)"),
    PARTY_INVITE_HOVER("PARTY.INVITE_HOVER", "&aClick to to accept this party invite"),
    PARTY_PLAYER_INVITED("PARTY.PLAYER_INVITED", "&8[&c&lParty&8] &c<invited> &7has been invited to your party."),
    PARTY_PLAYER_JOINED("PARTY.PLAYER_JOINED", "&8[&c&lParty&8] &c<joiner> &7joined your party."),
    PARTY_PLAYER_LEFT("PARTY.PLAYER_LEFT", "&8[&c&lParty&8] &c<leaver> &7has left your party."),
    PARTY_PLAYER_KICKED("PARTY.PLAYER_KICK", "&8[&c&lParty&8] &c<leaver> &7has been kicked from your party."),
    PARTY_CREATED("PARTY.CREATED", "&8[&c&lParty&8] &aYou created a party."),
    PARTY_DISBANDED("PARTY.DISBANDED", "&8[&c&lParty&8] &cYour party has been disbanded."),
    PARTY_PUBLIC("PARTY.PUBLIC", "&8[&c&lParty&8] &c<host> &ais hosting a public party"),
    PARTY_PRIVACY("PARTY.PRIVACY", "&8[&c&lParty&8] &7Your party privacy has been changed to &c<privacy>"),
    PARTY_PROMOTED("PARTY.PROMOTED", "&8[&c&lParty&8] &c<promoted> &ahas been promoted to Leader in your party."),
    PARTY_DONOTHAVE("PARTY.DO_NOT_HAVE", "&8[&c&lParty&8] &7You don't have a party!"),
    PARTY_NOTLOBBY("PARTY.NOT_IN_LOBBY", "&8[&c&lParty&8] &7You are not in lobby, please finish your current task!"),
    PARTY_CHAT_FORMAT("PARTY.CHAT_FORMAT", "&8[&cParty&8] &c<player_displayname>&7: &a<message>"),
    PARTY_ALREADYINVITED("PARTY.ALREADY_INVITED", "&8[&cParty&8] &7That player has already been invited to your party."),
    PARTY_WRONG_LEADER("PARTY.DONT_HAVE_PARTY", "&8[&cParty&8] &7That player does not have a party."),
    PARTY_HCF_UPDATED("PARTY.HCF_CLASS_CHANGE", "&c<target>'s &7HCF Class was changed to &c<class>&7."),
    PARTY_NOT_INVITED("PARTY.NOT_INVITED", "&8[&cParty&8] &7You are not invited to this party."),
    PARTY_TOURNAMENT("PARTY.IN_TOURNAMENT", "&8[&cParty&8] &7That party is currently in a tournament."),
    PARTY_FULL("PARTY.FULL", "&8[&cParty&8] &7That party is currently full!"),
    PARTY_NOTLEADER("PARTY.NOTLEADER", "&8[&c&lParty&8] &7You are not the leader of this party!"),
    PARTY_NOT_MEMBER("PARTY.NOT_MEMBER", "&8[&cParty&8] &7That player is not a member of this party."),
    PARTY_NOT_BANNED("PARTY.NOT_BANNED", "&8[&cParty&8] &7That player is not banned from your party."),
    PARTY_BAN_SELF("PARTY.BAN_SELF", "&8[&cParty&8] &7You can't ban yourself from your own party."),
    PARTY_UNBAN_SELF("PARTY.KICK_SELF", "&8[&cParty&8] &7You can't unban yourself from your own party."),
    PARTY_LEAVE_SELF("PARTY.LEAVE_SELF", "&8[&cParty&8] &7You can't leave your own party, please do /party disband."),
    PARTY_KICK_SELF("PARTY.KICK_SELFT", "&8[&cParty&8] &7You can't kick yourself from your own party."),
    PARTY_BANNED("PARTY.BANNED", "&8[&cParty&8] &7You have been banned from the party."),
    PARTY_BAN("PARTY.BAN", "&8[&cParty&8] &7Successfully banned &c<target> &7from the party."),
    PARTY_KICKED("PARTY.KICKED", "&8[&cParty&8] &7You have been kicked from the party."),
    PARTY_UNBANNED("PARTY.UNBANNED", "&8[&cParty&8] &c<target> &7has been unbanned from the party."),
    PARTY_ALREADYHAVE("PARTY.ALREAD_HAVE", "&8[&c&lParty&8] &7You already have a party!"),
    PARTY_ALREADYLEADER("PARTY.ALREADY_LEADER", "&8[&cParty&8] &7You are already the leader of your party."),
    PARTY_ALREADYINPARTY("PARTY.ALREADY_JOINED", "&8[&cParty&8] &7That player is already in your party."),
    PARTY_ALREADYBANNED("PARTY.ALREADY_BANNED", "&8[&cParty&8] &7That player is already banned from your party."),
    PARTY_CHAT("PARTY.ENABLED_CHAT", "&aYou are now speaking in party chat!"),
    PARTY_GLOBAL("PARY.DISABLED_CHAT", "&aYou are now speaking in global chat!"),
    PARTY_INFO("PARTY.INFO", Arrays.asList("&c&m--------&7&m-------------------------------------&c&m--------", "&cParty Information", "&c&m--------&7&m-------------------------------------&c&m--------", "&8 \u2022 &cLeader: <party_leader_name>", "&8 \u2022 &cPrivacy: <party_privacy>", "&8 \u2022 &cMembers: <party_members_formatted>", "&c&m--------&7&m-------------------------------------&c&m--------")),
    PARTY_DONATOR("PARTY.DONATOR_MESSAGE", Arrays.asList("&7You do not have permission to use Party Settings.", "&7&oPlease upgrade your Rank at &c&ostore.purgemc.club &7")),
    PARTY_HELP("PARTY.HELP_MESSAGE", Arrays.asList("&c&m--------&7&m-------------------------------------&c&m--------", "&cParty Commands", "&c&m--------&7&m-------------------------------------&c&m--------", " &8\u2022 &c/party help &8(&7&oDisplays the help message&8)", " &8\u2022 &c/party create &8(&7&oCreates a party instance&8)", " &8\u2022 &c/party leave &8(&7&oLeave your current party&8)", " &8\u2022 &c/party info &8(&7&oDisplays your party information&8)", " &8\u2022 &c/party join &8<&7leader&8> &8(&7&oJoin a party&8)", " &8\u2022 &c/party chat &8(&7&oToggle party chat&8)", " &8\u2022 &c/party open &8(&7&oOpen your party for others to join&8)", " &8\u2022 &c/party close &8(&7&oClose your party for others to join&8)", " &8\u2022 &c/party invite &8<&7profile&8> &8(&7&oInvites a profile to your party&8)", " &8\u2022 &c/party kick &8<&7profile&8> &8(&7&oKicks a profile from your party&8)", " &8\u2022 &c/party ban &8<&7profile&8> &8(&7&oBans a profile from your party&8)", " &8\u2022 &c/party unban &8<&7profile&8> &8(&7&oUnbans a profile from your party&8)", " &8\u2022 &c/party promote &8<&7profile&8> &8(&7&oTransfers Ownership of your party&8)", "&c&m--------&7&m-------------------------------------&c&m--------")),

    CLAN_NAME_LENGTH("CLAN.NAME_LENGTH", "&8[&cClan&8] &7Clan names must be greater than or equal to 2 characters long and less than or equal to 8 characters long."),
    CLAN_NAME_LETTER("CLAN.NAME_LETTER", "&8[&cClan&8] &7Clan names must only contain alpha characters (letters only)."),
    CLAN_ALREADY_PARTOF("CLAN.ALREADY_PARTOF", "&8[&cClan&8] &7You are already part of &c<clan_name> &7Clan!"),
    CLAN_NAME_ALREADYEXIST("CLAN.ALREADY_EXISTS", "&8[&cClan&8] &7A clan with the name &c<name> &7already exists!"),
    CLAN_CREATED("CLAN.CREATED", "&8[&cClan&8] &7You created a &cClan &7with the name &c<name>&7, use &c/clan invite <profile> &7to invite members."),
    CLAN_DOESNT_EXIST("CLAN.DOES_NOT_EXIST", "&8[&cClan&8] &7That clan does not exist!"),
    CLAN_PASSWORD_REQURED("CLAN.PASSWORD_REQUIRED", "&8[&cClan&8] &7You need the password or an invitation to join this clan.\nTo join with a password, use &c/clan join <clan_name> <password> &7."),
    CLAN_INCORRECT_PASS("CLAN.INCORRECT_PASSWORD", "&8[&cClan&8] &7Incorrect Password!"),
    CLAN_NOT_LEADER("CLAN.NOT_LEADER", "&8[&cClan&8] &7You are not the leader of any Clan!"),
    CLAN_NOT_IN("CLAN.NOT_IN", "&8[&cClan&8] &7You are not in a Clan!"),
    CLAN_NOT_PARTOF("CLAN.NOT_PART_OF", "&8[&cClan&8] &7That player is not a part of your Clan!"),
    CLAN_DISBANDED("CLAN.DISBANDED", "&8[&cClan&8] &cYour clan has been disbanded by the Leader!"),
    CLAN_INVITE_SELF("CLAN.INVITE_SELF", "&8[&cClan&8] &7You can not invite yourself!"),
    CLAN_DONOTHAVE("CLAN.DO_NOT_HAVE", "&8[&cClan&8] &7You are not part of any clan!"),
    CLAN_ALREADYHAVE("CLAN.ALREADY_HAVE", "&8[&cClan&8] &7That player already has a Clan!"),
    CLAN_ALREADYINVITED("CLAN.ALREADY_INVITED", "&8[&cClan&8] &7That player has already been invited!"),
    CLAN_INVITED_BROADCAST("CLAN.INVITE_BROADCAST", "&8[&c&lClan&8] &c<invited> &7has been invited to the clan!"),
    CLAN_NOT_CAPTAIN("CLAN.NOT_CAPTAIN", "&8[&c&lClan&8] &7You are not the captain of any Clan!"),
    CLAN_SELF_PROMOTE("CLAN.SELF_PROMOTE", "&8[&c&lClan&8] &7You can't self promote!"),
    CLAN_KICKED("CLAN.KICKED_BROADCAST", "&8[&cClan&8] &c<player> &7has been kicked from the Clan!"),
    CLAN_BANNED("CLAN.BANNED_BROADCAST", "&8[&cClan&8] &c<player> &7has been banned from the Clan!"),
    CLAN_JOIN("CLAN.JOINED", "&8[&cClan&8] &c<player> &7has joined the Clan!"),
    CLAN_PROMOTE("CLAN.PROMOTE", "&8[&cClan&8] &c<player> &7has been promoted to &c&l<role>&7!"),
    CLAN_DEMOTE("CLAN.DEMOTE", "&8[&cClan&8] &c<player> &7has been demoted to &c&l<role>&7!"),
    CLAN_LEFT("CLAN.LEFT", "&8[&cClan&8] &c<player> &7has left the Clan!"),
    CLAN_NO_PERM("CLAN.NO_PERM", "&8[&cClan&8] &7You do not have permission to execute this task!"),
    CLAN_SETDESC("CLAN.SET_DESC", "&8[&cClan&8] &7Successfully updated the &cdescription &7of your Clan!"),
    CLAN_SETPASS("CLAN.SET_PASS", "&8[&cClan&8] &7Successfully updated the &cpassword &7of your Clan!"),
    CLAN_LEADER_LEAVE("CLAN.LEADER_LEAVE", "&8[&cClan&8] &7The Leader can not leave the Clan, use &c/clan disband &7to disband your Clan!"),
    CLAN_IS_BANNED("CLAN.PLAYER_BANNED", "&8[&c&lClan&8] &7That player has been banned from this clan, use &c/clan unban <profile> &7to unban."),
    CLAN_HELP("CLAN.HELP_MESSAGE", Arrays.asList("&c&m--------&7&m-------------------------------------&c&m--------", "&cClan Commands", "&c&m--------&7&m-------------------------------------&c&m--------", " &8\u2022 &c/clan create &8<&7name&8> &8(&7&oCreate a Clan&8)", " &8\u2022 &c/clan disband &8(&7&oDisband your Clan&8)", " &8\u2022 &c/clan chat &8(&7&oToggle your Clan Chat Mode&8)", " &8\u2022 &c/clan accept &8<&7leader&8> &8(&7&oAccept a Clan Invitation&8)", " &8\u2022 &c/clan leave &8(&7&oLeave your Current Clan&8)", " &8\u2022 &c/clan info &8(&7&oView information about your Clan&8)", " &8\u2022 &c/clan invite &8<&7profile&8> &8(&7&oInvite a Profile to your Clan&8)", " &8\u2022 &c/clan kick &8<&7profile&8> &8(&7&oKick a Profile from your Clan&8)", " &8\u2022 &c/clan ban &8<&7profile&8> &8(&7&oBan a Profile from your Clan&8)", " &8\u2022 &c/clan promote &8<&7profile&8> &8(&7&oPromote a Profile to Captain&8)", " &8\u2022 &c/clan demote &8<&7profile&8> &8(&7&oDemote a Profile to Member&8)", " &8\u2022 &c/clan leader &8<&7profile&8> &8(&7&oPromote a Profile to Leader&8)", " &8\u2022 &c/clan description &8<&7text&8> &8(&7&oSet your Clan's Description&8)", "&c&m--------&7&m-------------------------------------&c&m--------")),
    CLAN_INFO("CLAN.INFO", Arrays.asList(
            "&c&m--------&7&m-------------------------------------&c&m--------",
            "&c<clan_name> Information &8[&7<clan_members_size>/<clan_members_limit>&8]",
            "&c&m--------&7&m-------------------------------------&c&m--------",
            "&8 \u2022 &cLeader: &f<clan_leader>",
            "&8 \u2022 &cMembers: &f<clan_members>",
            "&8 \u2022 &cClan ELO: &f<clan_elo>",
            "&8 \u2022 &cDate Created: &f<clan_created>",
            "&8 \u2022 &cWinstreak | Highest WS: &f<clan_winstreak> &7\uff5c &f<clan_highest_winstreak>",
            "&c&m--------&7&m-------------------------------------&c&m--------")),

    ERROR_BUSY("ERROR.NOT_AVAILABLE", "&7That player is currently busy, please try again."),
    ERROR_NOTACTIVE("ERROR.NOT_ACTIVE", "&7There is no current on-going event!"),
    ERROR_NOTPARTOF("ERROR.NOT_PARTOF", "&7You are not part of any on-going <event> event!"),
    ERROR_NOTSPECTATING("ERROR.NOT_SPECTATING", "&7You are not spectating!"),
    ERROR_UNAVAILABLE("ERROR.UNAVAILABLE", "&7You are currently busy, please finish your task and then try again."),
    ERROR_NOREMATCH("ERROR.NO_REMATCH", "&7You don't have anyone to rematch with!"),
    ERROR_EXPIREREMATCH("ERROR.REMATCH_EXPIRED", "&7Your rematch time has been expired!"),
    ERROR_PEARLSDISABLED("ERROR.PEARLS_DISABLED", "&cYou can't enderpearl in this arena!"),
    ERROR_REMATCHSENT("ERROR.REMATCH_SENT", "&7You have already sent the rematch request!"),
    ERROR_PARTY("ERROR.IN_PARTY", "&7Please leave your party to execute this task."),
    ERROR_NOSPEC("ERROR.NO_SPEC", "&7That player is not allowing spectators currently."),
    ERROR_MATCHNOSPEC("ERROR.MATCH_NOSPEC", "&7This match contains a player that is not allowing spectators currently."),
    ERROR_FREE("ERROR.FREE", "&7That player is not in any match or any events."),
    ERROR_NOTMATCH("ERROR.NOT_IN_MATCH", "&7You are not in any match."),
    ERROR_NOTABLE("ERROR.NOT_ABLE", "&7You are not able to execute this task currently."),
    ERROR_PLAYERNOTFOUND("ERROR.PLAYER_NOT_FOUND", "&7That player does not exist or is not currently online."),
    ERROR_SETTING_NOPERM("ERROR.SETTINGS_NO_PERM", Arrays.asList("&7You don't have permission to use this setting", "&7&oyou can upgrade your rank at &c&ostore.purgecommunity.com&7&o.")),

    SETTINGS_ENABLED("SETTINGS.ENABLED", "&7You enabled &c<setting_name>&7 for your profile!"),
    SETTINGS_DISABLED("SETTINGS.DISABLED", "&7You disabled &c<setting_name>&7 for your profile!"),

    RANKED_DISABLED("RANKED.DISABLED", "&7Ranked has been disabled by an Admin!"),
    RANKED_REQUIRED("RANKED.REQUIRED", Arrays.asList("&7You need to win at least &c10 Unranked Matches &7 to queue Ranked!", "&7&oYou can bypass this limit by upgrading your rank at &c&ostore.purgecommunity.com")),

    HCF_CLASS_ENABLED("HCF.CLASS_ENABLED", "&cClass: &f<class> &aenabled!"),
    HCF_COOLDOWN("HCF.COOLDOWN", "&7You cannot use this for another <duration>!"),
    HCF_ARCHER_RANGE("HCF.ARCHER_RANGE", "&cRange: &f<range>"),
    HCF_ARCHER_MARKED("HCF.ARCHER_MARKED_SHOOTER", "&7You Marked &c<damaged> &7for &c10 seconds &c<damagedhealth> &4❤"),
    HCF_ARCHER_DAMAGEMARKED("HCF.ARCHER_MARKED_DAMAGED", "&7Marked! &c<shooter> &7has shot &cyou &7and &cmarked &7you (+25% damage) for &c10 seconds&7. &8(&c<distance> blocks away&8)"),
    HCF_BARD_ENERGY("HCF.BARD_ENERGY", "&cBard Energy: &f<energy>"),
    HCF_BARD_BARDBUFF("HCF.BARD_BARDBUFF", "&7You have just used a &c&lBard Buff &7that cost you &c<cost> &7of your Energy."),
    HCF_BARD_NOTENOUGHENERGY("HCF.BARD_NOTENOUGHENERGY", "&7You do not have enough energy for this! You need &c<cost> &7energy, but you only have &c<energy>&7!"),
    HCF_ROUGE_BACKSTABBED("HCF.ROUGE_BEENBACKSTABBED", "&c<attacker> &ehas backstabbed you!"),
    HCF_ROUGE_BACKSTABBER("HCF.ROGUE_HAVEBACKSTABBED", "&eYou have backstabbed &c<target>&e."),

    MATCH_HCF_STARTMESSAGE("MATCH.HCF_START_MESSAGE", Arrays.asList("", "&c&lHCF Match&7!", "", "&7Pick between &cBard&7, &cArcher&7, &cRogue&7 and &cDiamond", "&7Kits and Fight to the death to &cWin!", "")),
    MATCH_TEAM_STARTMESSAGE("MATCH.TEAM_START_MESSAGE", Arrays.asList("&c&lMatch Found!", "", "&fYou are playing on &c<arena>&f on a &cTeam Match!")),
    MATCH_SOLO_STARTMESSAGE("MATCH.SOLO_START_MESSAGE", Arrays.asList("&c&lMatch Found!", "", "&fYou are playing on &c<arena>&f with &fPlayers: &c<player1> &7vs &c<player2>")),
    MATCH_ROUND_MESSAGE("MATCH.ROUND_MESSAGE", Arrays.asList(" &c&lRound #<round_number>", "  &fYour Points: &c<your_points>", "  &fTheir Points: &c<their_points>")),
    MATCH_DISCLAIMER("MATCH.DISCLAIMER_MESSAGE", Collections.singletonList("&c&lReminder: &fButterfly clicking is &cdiscouraged &fand could result in a &cban. Use at your own risk.")),
    MATCH_COUNTDOWN("MATCH.COUNTDOWN", "&fStarting in &c<seconds>&f..."),
    MATCH_ROUND_COUNTDOWN("MATCH.ROUND_COUNTDOWN", "&c<seconds>&f..."),
    MATCH_STARTED("MATCH.STARTED", "&aMatch Started!"),
    MATCH_ROUND("MATCH.ROUND_STARTED", "&aThe Round has Started!"),
    MATCH_SPECTATE("MATCH.SPECTATE_JOIN", "&c<spectator> &eis now spectating your match!"),
    MATCH_STOPSPEC("MATCH.SPECTATE_LEAVE", "&c<spectator> &eis no longer spectating your match!"),
    MATCH_CHECKPOINT("MATCH.CHECKPOINT_ACCQUIRED", "&8[&cParkour&8] &cCheckpoint Accquired!"),
    MATCH_DISCONNECTED("MATCH.PARTICIPANT_DISCONNECTED", "<relation_color><participant_name> &7has disconnected."),
    MATCH_WON("MATCH.PARTICIPANT_WON", "<relation_color><participant_name> &ahas won!"),
    MATCH_EPEARL_EXPIRE("MATCH.EPEAR_EXPIRE", "&aYou may pearl again."),
    MATCH_DIED("MATCH.PARTICIPANT_DIED", "<relation_color><participant_name> &7has died!"),
    MATCH_KILLED("MATCH.PARTICIPANT_KILLED", "<relation_color_dead><dead_name> &7was killed by <relation_color_killer><killer_name>&7."),
    MATCH_MAX_BUILD("MATCH.MAX_BUILD_LIMIT", "&cYou have reached the build height limit!"),
    MATCH_BOW_HIT("MATCH.BOW_HIT", "&c<damaged_name> &7is now at &c<damaged_health> &4\u2764"),
    MATCH_PEARL_COOLDOWN("MATCH.PEARL_COOLDOWN", "&cYou are on pearl cooldown for <cooldown>!"),
    MATCH_BOW_COOLDOWN("MATCH.BOW_COOLDOWN", "&cYou are on bow cooldown for <cooldown>!"),
    MATCH_BRIDGE_BLOCK("MATCH.BRIDGE_BLOCK_PLACE", "&cYou cannot place blocks here!"),
    MATCH_BRIDGE_WRONG_PORTAL("MATCH.BRIDGE_WRONG_PORTAL", "&cYou jumped in the wrong portal!"),
    MATCH_BRIDGE_SCORED("MATCH.BRIDGE_SCORED", "<relation_color_scored><scored_name> &fhas scored a Point!"),
    MATCH_BRIDGE_WON("MATCH.BRIDGE_WON_ROUND", Arrays.asList("", "&c<winner_name> &7has won this round!", "")),
    MATCH_INVENTORY_MESSAGE("MATCH.INVENTORY_MESSAGE", Arrays.asList("&c&m--------&7&m-------------------------------------&c&m--------", "&c&lMatch Details &7(Click name to view inventory)", "", "<inventories>", "<elo_changes>", "&c&m--------&7&m-------------------------------------&c&m--------")),
    MATCH_INVENTORY_HOVER("MATCH.INVENTORY_HOVER", "&7Click to view &c<inventory_name>'s &7inventory."),
    MATCH_INVENTORY_WINNER("MATCH.INVENTORY_SOLO_WINNER", "&aWinner: "),
    MATCH_INVENTORY_WINNERS("MATCH.INVENTORY_TEAM_WINNERS", "&aWinners: "),
    MATCH_INVENTORY_SPLITTER("MATCH.INVENTORY_SPLITTER", "&7 \uff5c "),
    MATCH_INVENTORY_LOSER("MATCH.INVENTORY_SOLO_LOSER", "&cLoser: "),
    MATCH_INVENTORY_LOSERS("MATCH.INVENTORY_TEAM_LOSERS", "&cLosers: "),
    MATCH_ELO_CHANGES("MATCH.ELO_CHANGE", "&a<winner_name> +<winner_elo_change> (<winner_elo>) &7⎜ &c<loser_name> -<loser_elo_change> (<loser_elo>)"),
    MATCH_SPEC_MESSAGE("MATCH.SPEC_MESSAGE", "&cSpectators (<spec_size>): &7<spectators>"),
    MATCH_SWORD_DROP("MATCH.SWORD_DROP", "&7You can't drop that while holding it in slot 1."),
    MATCH_BUILD_OUTSIDE("MATCH.BUILD_OUTSIDE", "&7You cannot build outside of the arena!"),
    MATCH_RATING("MATCH.RATING", "&aThanks for rating the map! We have recorded your rating."),

    TOURNAMENT_ROUND("TOURNAMENT.ROUND_MESSAGE", "&8[&9&lRound&8] &c<round> &7has started!"),
    TOURNAMENT_BROADCAST("TOURNAMENT.BROADCAST", "&8[&c&lTournament&8] &c<host_name> &fis hosting a &c<kit> &7Tournament! &7(<tournament_type>)"),
    TOURNAMENT_ACCEPT("TOURNAMENT.ACCEPT", "&8[&c&lTournament&8] &a(Click to accept)"),
    TOURNAMENT_HOVER("TOURNAMENT.HOVER", "Click to join the Tournament"),
    TOURNAMENT_CANCELLED("TOURNAMENT.CANCELLED", "&8[&c&lTournament&8] &cThe Tournament has been cancelled."),
    TOURNAMENT_NOT_PICKED("TOURNAMENT.NOT_PICKED", "&8[&c&lTournament&8] &7You weren't picked this round, please wait for your turn!"),
    TOURNAMENT_ELIMINATED("TOURNAMENT.ELIMINATED", "&8[&c&lTournament&8] &c<eliminated> &7has been eliminated. &8(&c<participants_size>&7/&c<participants_count>&8)"),
    TOURNAMENT_WON("TOURNAMENT.WON", "&8[&c&lTournament&8] &c<won> &7won the &ctournament&7!"),
    TOURNAMENT_JOIN("TOURNAMENT.JOIN", "&8[&c&lTournament&8] &c<joined_party>'s Party &7has joined the tournament! &8(&c<participants_size>/50&8)"),
    TOURNAMENT_LEAVE("TOURNAMENT.LEAVE", "&8[&c&lTournament&8] &c<left_party>'s Party &7has left the tournament! &8(&c<participants_size>/50&8)"),
    TOURNAMENT_HELP("TOURNAMENT.HELP", Arrays.asList("&c&m--------&7&m-------------------------------------&c&m--------", "&cArray &7» Tournament Commands", "&c&m--------&7&m-------------------------------------&c&m--------", " &8\u2022 &c/tournament list &8(&7&oList Active Tournaments&8)", " &8\u2022 &c/tournament host (team-size(1/2)) &8(&7&oHost a tournament8&o)", " &8\u2022 &c/tournament cancel &8(&7&oCancel a tournament8&o) &8- &c&l[ADMIN]", " &8\u2022 &c/tournament join &8(&7&oJoin an on-going tournament8&o)", " &8\u2022 &c/tournament leave &8(&7&oLeave an on-going tournament8&o)", "&c&m--------&7&m-------------------------------------&c&m--------")),

    EVENT_PREFIX("EVENTS.PREFIX", "&8[&c<event_name>&8] &r"),
    EVENT_JOIN("EVENTS.JOIN", "&c<joined> &7has joined the &c<event_name> Event&8! &8(&c<event_participants_size>/<event_max_players>&8)"),
    EVENT_PLAYER_JOIN("EVENTS.PLAYER_JOIN", "&8[&a+&8] &7You have successfully joined the &c<event_name> Event&8!"),
    EVENT_LEAVE("EVENTS.LEAVE", "&c<left> &7has left the &c<event_name> Event&8! &8(&c<event_participants_size>/<event_max_players>&8)"),
    EVENT_PLAYER_LEAVE("EVENTS.PLAYER_LEAVE", "&8[&c-&8] &7You have successfully left the &c<event_name> Event&8!"),
    EVENT_NOTABLE_JOIN("EVENTS.NOT_ABLE_TO_JOIN", "&7You are not currently able to join the event!"),
    EVENT_ON_GOING("EVENTS.ON_GOING", "&7There is already an active &cEvent&7!"),
    EVENT_COOLDOWN_ACTIVE("EVENTS.COOLDOWN_ACTIVE", "&7There is an active cooldown for events. (Expires in: <expire_time>)!"),
    EVENT_NO_COOLDOWN("EVENTS.NO_COOLDOWN", "&7There is no currently active &cEvent cooldown."),
    EVENT_COOLDOW_RESET("EVENTS.COOLDOWN_RESET", "&7Successfully reset the &cEvent &7cooldown."),
    EVENT_ALREADY_STARTED("EVENTS.ALREADY_STARTED", "&7This event has already!"),
    EVENT_ELIMINATED("EVENTS.ELIMINATED", "&c<eliminated_name> &7was eliminated by &c<eliminator_name>&7!"),
    EVENT_DIED("EVENTS.DIED", "&c<eliminated_name> &7has died&7!"),
    EVENT_NO_PERMISSION("EVENTS.NO_PERM", Arrays.asList("&7You do not have permission to use this.", "&7&oPlease upgrade your Rank at &c&<store> &7")),
    EVENT_CANCELLED("EVENTS.CANCELLED", "&cThe <event_name> Event has been cancelled!"),
    EVENT_START_COUNTDOWN("EVENTS.START_COUNTDOWN", "&c<seconds>&f..."),
    EVENT_NOT_SETUP("EVENTS.NOT_SETUP", "&7The event you are trying to join is not correctly setup!"),
    EVENT_PARKOUR_WON("EVENTS.PARKOUR_WON", "&c<winner> &ehas reached the end!"),
    EVENT_ROUND_STARTED("EVENTS.ROUND_STARTED", "&cThe round has started!"),
    EVENT_STARTED("EVENTS.STARTED", "&cThe event has started!"),
    EVENT_FULL("EVENTS.FULL", "&cThis event is full on its player capacity!"),
    EVENT_FORCESTART("EVENTS.FORCESTART", "&7Successfully force started the &c<event_name> Event&7!"),
    EVENT_KNOCKBACK("EVENTS.KNOCKBACK", "&7Successfully updated the knockback profile to &c<knockback>"),
    EVENT_SPAWN("EVENTS.SPAWWN", "&7Updated &cEvent's &7spawn location &c<position>&7."),
    EVENT_TELEPORT("EVENTS.TELEPORT", "&7Teleported to &c<event_name>'s &7spawn location."),
    EVENT_STARTING("EVENTS.STARTING", "&fThe &c<event_name> &fEvent will start in &c10 seconds&f..."),
    EVENT_NOT_ENOUGH_PLAYERS("EVENTS.NOT_ENOUGH_PLAYERS", "&cThere are not enough players to start this event!"),
    EVENT_KILLED("EVENTS.KILLED", "&c<killed_name> &7has died!"),
    EVENT_ANNOUNCE("EVENTS.ANNOUCE", Arrays.asList(" ", "&7\u2b1b\u2b1b\u2b1b\u2b1b\u2b1b\u2b1b\u2b1b\u2b1b", "&7\u2b1b\u2b1b&c\u2b1b\u2b1b\u2b1b\u2b1b&7\u2b1b\u2b1b &c&l[<event_name> Event]", "&7\u2b1b\u2b1b&c\u2b1b&7\u2b1b\u2b1b\u2b1b\u2b1b\u2b1b ", "&7\u2b1b\u2b1b&c\u2b1b\u2b1b\u2b1b\u2b1b&7\u2b1b\u2b1b &fA &c<event_name> &fevent is being hosted by &c<event_host>", "&7\u2b1b\u2b1b&c\u2b1b&7\u2b1b\u2b1b\u2b1b\u2b1b\u2b1b &fEvent is starting in 60 seconds!", "&7\u2b1b\u2b1b&c\u2b1b\u2b1b\u2b1b\u2b1b&7\u2b1b\u2b1b &a&l[Click to Join]", "&7\u2b1b\u2b1b\u2b1b\u2b1b\u2b1b\u2b1b\u2b1b\u2b1b", "")),
    EVENT_HOVER("EVENTS.HOVER", "&7Click to join <event_name> Event"),
    EVENT_TEAM_WON("EVENTS.TEAM_WON", Arrays.asList(" ", "&7\u2b1b\u2b1b\u2b1b\u2b1b\u2b1b\u2b1b\u2b1b\u2b1b", "&7\u2b1b\u2b1b&c\u2b1b\u2b1b\u2b1b\u2b1b&7\u2b1b\u2b1b &c&l[<event_name> Event]", "&7\u2b1b\u2b1b&c\u2b1b&7\u2b1b\u2b1b\u2b1b\u2b1b\u2b1b ", "&7\u2b1b\u2b1b&c\u2b1b\u2b1b\u2b1b\u2b1b&7\u2b1b\u2b1b &c<winner> &ehas won the <event_name> Event.", "&7\u2b1b\u2b1b&c\u2b1b&7\u2b1b\u2b1b\u2b1b\u2b1b\u2b1b &7&oThe team consisted of the following players", "&7\u2b1b\u2b1b&c\u2b1b\u2b1b\u2b1b\u2b1b&7\u2b1b\u2b1b &7<players>", "&7\u2b1b\u2b1b\u2b1b\u2b1b\u2b1b\u2b1b\u2b1b\u2b1b", "")),
    EVENT_WON("EVENTS.WON", Arrays.asList(" ", "&7\u2b1b\u2b1b\u2b1b\u2b1b\u2b1b\u2b1b\u2b1b\u2b1b", "&7\u2b1b\u2b1b&c\u2b1b\u2b1b\u2b1b\u2b1b&7\u2b1b\u2b1b &c&l[<event_name> Event]", "&7\u2b1b\u2b1b&c\u2b1b&7\u2b1b\u2b1b\u2b1b\u2b1b\u2b1b ", "&7\u2b1b\u2b1b&c\u2b1b\u2b1b\u2b1b\u2b1b&7\u2b1b\u2b1b &c<winner> &ehas won the <event_name> Event.", "&7\u2b1b\u2b1b&c\u2b1b&7\u2b1b\u2b1b\u2b1b\u2b1b\u2b1b &7&oGood game!", "&7\u2b1b\u2b1b&c\u2b1b\u2b1b\u2b1b\u2b1b&7\u2b1b\u2b1b", "&7\u2b1b\u2b1b\u2b1b\u2b1b\u2b1b\u2b1b\u2b1b\u2b1b", "")),

    QUEUE_JOIN_UNRANKED("QUEUE.JOIN_UNRANKED", "&7You have been added to the &c<queue_name> &7queue."),
    QUEUE_JOIN_RANKED("QUEUE.JOIN_RANKED", "&7You have been added to the &c<queue_name> &7queue. &c[<queue_elo>]"),
    QUEUE_JOIN_CLAN("QUEUE.JOIN_CLAN", "&7You have been added to the &c<queue_name> &7queue. &c[<clan_elo>]"),
    QUEUE_LEAVE("QUEUE.LEAVE", "&7You left the <queue_name> queue."),

    DUEL_SENT("DUEL.SENT", "&8[&c&lDuel&8] &fYou sent a duel request to &c<target_name> &7(<target_ping>) &f with kit &c<duel_kit> &fon the arena &c<duel_arena>"),
    DUEL_RECEIVED("DUEL.RECEIVED", "&8[&c&lDuel&8] &c<sender_name> &7(<sender_ping>&7) &fhas sent you a duel request with kit &c<duel_kit> &fon the arena &c<duel_arena>"),
    DUEL_HOVER("DUEL.INVITE_HOVER", "&7Click to accept this duel."),
    DUEL_ACCEPT("DUEL.CLICK_TO_ACCEPT", "&a(Click to Accept)"),

    KITEDITOR_LONG("KITEDITOR.TOO_LONG", "&7A name cannot be longer than &c16 &7characters!"),
    KITEDITOR_RENAMED("KITEDITOR.RENAMED", "&7Successfully &crenamed &7the kit to &c<custom_name>&7!"),
    KITEDITOR_RENAMING("KITEDITOR_RENAMING", "&7Renaming &c<old_name>&7, Enter the new name now..."),

    LEADERBOARDS_KIT_FORMAT("LEADERBOARDS.KIT_FORMAT", "&c<leaderboards_pos> &7&l\uff5c &f<leaderboards_name>: &c<leaderboards_elo> &7(<leaderboards_division>)"),
    LEADERBOARDS_KIT_HEADER("LEADERBOARDS.KIT_HEADER", "&c<kit_name> &7\uff5c &fTop 10"),
    LEADERBOARDS_GLOBAL_FORMAT("LEADERBOARDS.GLOBAL_FORMAT", "&c<leaderboards_pos> &7&l\uff5c &f<leaderboards_name>: &c<leaderboards_elo> &7(<leaderboards_division>)"),
    LEADERBOARDS_GLOBAL_HEADER("LEADERBOARDS.GLOBAL_HEADER", "&cGlobal &7\uff5c &fTop 10");

    @Getter private final String path;
    @Getter private String value;
    @Getter private List<String> listValue;

    private final BasicConfigurationFile configFile = Array.getInstance().getMessagesConfig();

    Locale(String path, String value) {
        this.path = path;
        this.value = value;
    }

    Locale(String path, List<String> listValue) {
        this.path = path;
        this.listValue = listValue;
    }

    public String toString() {
        Replacement replacement = new Replacement(CC.translate(configFile.getConfiguration().getString(this.path)));
        return replacement.toString().replace("{0}", "\n").replace("\uff5c", "┃");
    }

    public List<String> toList() {
        List<String> toReturn = new ArrayList<>();
        for ( String strings : configFile.getConfiguration().getStringList(this.path)) {
            toReturn.add(CC.translate(strings).replace("{0}", "\n"));
        }
        return toReturn;
    }
}
