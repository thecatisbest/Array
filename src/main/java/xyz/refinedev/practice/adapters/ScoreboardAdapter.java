package xyz.refinedev.practice.adapters;

import lombok.RequiredArgsConstructor;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import xyz.refinedev.practice.Array;
import xyz.refinedev.practice.clan.Clan;
import xyz.refinedev.practice.event.Event;
import xyz.refinedev.practice.match.Match;
import xyz.refinedev.practice.match.team.Team;
import xyz.refinedev.practice.match.team.TeamPlayer;
import xyz.refinedev.practice.match.types.kit.BoxingMatch;
import xyz.refinedev.practice.match.types.kit.solo.SoloBridgeMatch;
import xyz.refinedev.practice.match.types.kit.team.TeamBridgeMatch;
import xyz.refinedev.practice.party.Party;
import xyz.refinedev.practice.profile.Profile;
import xyz.refinedev.practice.pvpclasses.PvPClass;
import xyz.refinedev.practice.pvpclasses.classes.Bard;
import xyz.refinedev.practice.queue.Queue;
import xyz.refinedev.practice.queue.QueueType;
import xyz.refinedev.practice.tournament.Tournament;
import xyz.refinedev.practice.tournament.TournamentType;
import xyz.refinedev.practice.util.chat.CC;
import xyz.refinedev.practice.util.config.impl.BasicConfigurationFile;
import xyz.refinedev.practice.util.other.PlayerUtil;
import xyz.refinedev.practice.util.other.TimeUtil;
import xyz.refinedev.practice.util.other.TrackUtil;
import xyz.refinedev.practice.util.scoreboard.AssembleAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This Project is the property of Refine Development © 2021
 * Redistribution of this Project is not allowed
 *
 * @author Drizzy
 * Created at 5/24/2021
 * Project: Array
 */

@RequiredArgsConstructor
public class ScoreboardAdapter implements AssembleAdapter {

    private final Array plugin;
    private final BasicConfigurationFile config;

    /**
     * Get's the scoreboard title.
     *
     * @param player who's title is being displayed.
     * @return title.
     */
    @Override
    public String getTitle(Player player) {
        return config.getStringOrDefault("SCOREBOARD.HEADER", CC.translate("&c&lRefine &7&l%splitter% &fPractice")).replace("%splitter%", "┃").replace("|", "┃");
    }

    /**
     * Get's the scoreboard lines.
     *
     * @param player who's lines are being displayed.
     * @return lines.
     */
    @Override
    public List<String> getLines(Player player) {
        List<String> lines = new ArrayList<>();

        Profile profile = plugin.getProfileManager().getByUUID(player.getUniqueId());

        if (!profile.getSettings().isScoreboardEnabled()) {
            return lines;
        }

        lines.add(config.getStringOrDefault("SCOREBOARD.LINES", CC.SB_BAR));
        if (profile.isInLobby() || profile.isInQueue()) {
            config.getStringList("SCOREBOARD.LOBBY").forEach(line -> lines.add(CC.translate(line
                    .replace("<online>", String.valueOf(TrackUtil.getOnline()))
                    .replace("<in_fights>", String.valueOf(TrackUtil.getInFights()))
                    .replace("<in_queues>", String.valueOf(TrackUtil.getInQueues()))
                    .replace("<elo_league>", plugin.getProfileManager().getDivision(profile).getDisplayName())
                    .replace("<global_elo>", String.valueOf(profile.getGlobalElo())))
                    .replace("%splitter%", "┃")
                    .replace("|", "┃")));

            if (profile.getParty() != null && plugin.getTournamentManager().getCurrentTournament() == null) {
                Party party = profile.getParty();
                String armorClass = party.getKits().get(player.getUniqueId());

                config.getStringList("SCOREBOARD.PARTY").forEach(line -> lines.add(CC.translate(line
                        .replace("<party_leader>", party.getLeader().getUsername())
                        .replace("<party_size>", String.valueOf(party.getPlayers().size()))
                        .replace("<party_limit>", String.valueOf(party.getLimit()))
                        .replace("<party_privacy>", party.getPrivacy()))
                        .replace("<party_class>", armorClass == null ? "None" : armorClass)
                        .replace("%splitter%", "┃").replace("|", "┃")));

            } else if (profile.hasClan() && !profile.isInQueue()) {
                Clan clan = profile.getClan();

                config.getStringList("SCOREBOARD.CLAN").forEach(line -> lines.add(CC.translate(line
                        .replace("<clan_leader>", plugin.getProfileManager().getByUUID(clan.getLeader().getUuid()).getName())
                        .replace("<clan_members_online>", String.valueOf(clan.getOnlineMembers().size()))
                        .replace("<clan_members_total>", String.valueOf(clan.getAllMembers().size()))
                        .replace("<clan_elo>",String.valueOf(clan.getElo()))
                        .replace("<clan_name>", clan.getName())
                        .replace("%splitter%", "┃").replace("|", "┃"))));

            }
            if (profile.isInQueue()) {
                Queue queue = profile.getQueue();
                if (queue.getType() == QueueType.UNRANKED) {
                    config.getStringList("SCOREBOARD.UNRANKED_QUEUE").forEach(line -> lines.add(CC.translate(line
                            .replace("<queue_kit>", queue.getKit().getDisplayName())
                            .replace("<queue_duration>", queue.getDuration(player))
                            .replace("<queue_name>", queue.getQueueName()))
                            .replace("%splitter%", "┃").replace("|", "┃")));

                } else if (queue.getType() == QueueType.RANKED) {
                    config.getStringList("SCOREBOARD.RANKED_QUEUE").forEach(line -> lines.add(CC.translate(line
                            .replace("<queue_kit>", queue.getKit().getDisplayName())
                            .replace("<queue_duration>", queue.getDuration(player))
                            .replace("<queue_range>", this.getEloRangeFormat(profile))
                            .replace("<queue_name>", queue.getQueueName()))
                            .replace("%splitter%", "┃").replace("|", "┃")));

                } else if (queue.getType() == QueueType.CLAN) {
                    Clan clan = profile.getClan();
                    config.getStringList("SCOREBOARD.CLAN_QUEUE").forEach(line -> lines.add(CC.translate(line
                            .replace("<queue_kit>", queue.getKit().getDisplayName())
                            .replace("<queue_duration>", queue.getDuration(player))
                            .replace("<queue_range>", this.getEloRangeFormat(profile))
                            .replace("<queue_name>", queue.getQueueName()))
                            .replace("<clan_leader>", plugin.getProfileManager().getByUUID(clan.getLeader().getUuid()).getName())
                            .replace("<clan_members_online>", String.valueOf(clan.getOnlineMembers().size()))
                            .replace("<clan_members_total>", String.valueOf(clan.getAllMembers().size()))
                            .replace("<clan_elo>",String.valueOf(clan.getElo()))
                            .replace("<clan_name>", clan.getName())
                            .replace("%splitter%", "┃").replace("|", "┃")));
                }
            } else if (plugin.getTournamentManager().getCurrentTournament() != null) {
                Tournament<?> tournament = plugin.getTournamentManager().getCurrentTournament();
                String round = tournament.getRound() > 0 ? String.valueOf(tournament.getRound()) : "&fStarting";
                String participantType = tournament.getType().equals(TournamentType.TEAM) ? "Parties" : "Players";

                config.getStringList("SCOREBOARD.TOURNAMENT").forEach(line -> lines.add(CC.translate(line
                        .replace("<round>", round)
                        .replace("<kit>", tournament.getKit().getName())
                        .replace("<team>", String.valueOf(tournament.getIndividualSize()))
                        .replace("<participant_type>", participantType)
                        .replace("<participant_count>", String.valueOf(tournament.getParticipatingCount()))
                        .replace("<participant_max>", String.valueOf(tournament.getMaxPlayers()))
                        .replace("%splitter%", "┃")
                        .replace("|", "┃"))));
            }
        } else if (profile.isInFight()) {
            Match match = profile.getMatch();
            if (match.isEnding()) {

                config.getStringList("SCOREBOARD.MATCH.ENDING").forEach(line -> lines.add(CC.translate(line
                        .replace("<match_duration>", match.getDuration())
                        .replace("<player_count>", String.valueOf(match.getPlayers().size()))).replace("%splitter%", "┃").replace("|", "┃")));

            } else if (match.isSoloMatch()) {
                TeamPlayer self = match.getTeamPlayer(player);
                TeamPlayer opponent = match.getOpponentTeamPlayer(player);

                for ( String line : config.getStringList("SCOREBOARD.MATCH.SOLO") ) {
                    if (line.contains("cps>") && !profile.getSettings().isCpsScoreboard() && plugin.getConfigHandler().isCPS_SCOREBOARD_SETTING()) {
                        continue;
                    }

                    if (line.contains("ping>") && !profile.getSettings().isPingScoreboard() && plugin.getConfigHandler().isPING_SCOREBOARD_SETTING()) {
                        continue;
                    }

                    lines.add(CC.translate(line
                            .replace("<opponent_name>", opponent.getUsername())
                            .replace("<match_arena>", match.getArena().getDisplayName())
                            .replace("<match_duration>", match.getDuration())
                            .replace("<match_kit>", match.getKit().getDisplayName())
                            .replace("<player_count>", String.valueOf(match.getPlayers().size()))
                            .replace("<your_name>", player.getName())
                            .replace("<match_type>", "Solo"))
                            .replace("%splitter%", "┃")
                            .replace("<your_ping>", String.valueOf(self.getPing()))
                            .replace("<opponent_ping>", String.valueOf(opponent.getPing()))
                            .replace("<your_cps>", String.valueOf(self.getCps()))
                            .replace("<opponent_cps>", String.valueOf(opponent.getCps()))
                            .replace("|", "┃"));
                }

                if (match instanceof BoxingMatch) {
                    int hitDifference = self.getHits() - opponent.getHits();
                    String hits;
                    if (hitDifference < 0) {
                        hits = CC.RED + hitDifference;
                    } else if (hitDifference == 0) {
                        hits = CC.YELLOW + hitDifference;
                    } else {
                        hits = CC.GREEN + hitDifference;
                    }

                    config.getStringList("MATCH.SOLO_BOXING_ADDITION").forEach(line -> lines.add(CC.translate(line
                                .replace("<hit_difference>", hits)
                                .replace("<your_combo>", self.getCombo() == 0 ? "" : String.valueOf(self.getCombo()))
                                .replace("<your_hits>", String.valueOf(self.getHits()))
                                .replace("<opponent_hits>", String.valueOf(opponent.getHits())))));
                }

                if (match instanceof SoloBridgeMatch) {
                    SoloBridgeMatch soloBridgeMatch = (SoloBridgeMatch) match;

                    int yourPoints = match.getTeamPlayerA().equals(self) ? soloBridgeMatch.getPlayerAPoints() : soloBridgeMatch.getPlayerBPoints();
                    int opponentPoints = match.getTeamPlayerA().equals(opponent) ? soloBridgeMatch.getPlayerAPoints() : soloBridgeMatch.getPlayerBPoints();

                    config.getStringList("MATCH.SOLO_BRIDGE_ADDITION").forEach(line -> lines.add(CC.translate(line
                            .replace("<opponent_points>", String.valueOf(opponentPoints))
                            .replace("<your_points>", String.valueOf(yourPoints))
                            .replace("<bow_cooldown>", (profile.getBowCooldown().hasExpired() ? "None" : profile.getBowCooldown().getTimeLeft() + "s"))
                            .replace("<red_points_formatted>", this.getFormattedPoints(match.getTeamPlayerA().getPlayer()) + (match.getTeamPlayerA().getPlayer() == player ? " &7(You)" : ""))
                            .replace("<blue_points_formatted>", this.getFormattedPoints(match.getTeamPlayerB().getPlayer()) + (match.getTeamPlayerB().getPlayer() == player ? " &7(You)" : "")))
                    ));
                }
            } else if (match.isTeamMatch()) {
                Team team = match.getTeam(player);
                Team opponentTeam = match.getOpponentTeam(player);

                if (match instanceof TeamBridgeMatch) {
                    TeamBridgeMatch teamBridgeMatch = (TeamBridgeMatch) match;

                    int yourPoints = match.getTeamA().equals(team) ? teamBridgeMatch.getTeamAPoints() : teamBridgeMatch.getTeamBPoints();
                    int opponentPoints = match.getTeamB().equals(opponentTeam) ? teamBridgeMatch.getTeamAPoints() : teamBridgeMatch.getTeamBPoints();

                    config.getStringList("MATCH.TEAM_BRIDGE").forEach(line -> lines.add(CC.translate(line
                            .replace("<opponent_points>", String.valueOf(opponentPoints))
                            .replace("<your_points>", String.valueOf(yourPoints))
                            .replace("<bridge_round>", String.valueOf(teamBridgeMatch.getRound()))
                            .replace("<bow_cooldown>", (profile.getBowCooldown().hasExpired() ? "None" : profile.getBowCooldown().getTimeLeft() + "s"))
                            .replace("<red_points_formatted>", this.getFormattedPoints(match.getTeamA().getLeader().getPlayer()) + (match.getTeamA().containsPlayer(player) ? " &7(You)" : ""))
                            .replace("<blue_points_formatted>", this.getFormattedPoints(match.getTeamB().getLeader().getPlayer()) + (match.getTeamB().containsPlayer(player) ? " &7(You)" : ""))
                            .replace("<player_count>", String.valueOf(match.getPlayers().size()))
                            .replace("<match_type>", "Bridge")
                            .replace("%splitter%", "┃")
                            .replace("|", "┃"))
                    ));

                } else {
                    config.getStringList("SCOREBOARD.MATCH.TEAM").forEach(line -> lines.add(CC.translate(line
                            .replace("<match_duration>", match.getDuration())
                            .replace("<match_kit>", match.getKit().getDisplayName())
                            .replace("<match_type>", "Team")
                            .replace("<match_arena>", match.getArena().getDisplayName())
                            .replace("<player_count>", String.valueOf(match.getPlayers().size()))
                            .replace("<your_name>", player.getName())
                            .replace("<your_team_alive>", String.valueOf(team.getAliveCount()))
                            .replace("<opponent_team_alive>", String.valueOf(opponentTeam.getAliveCount()))
                            .replace("<your_team_count>", String.valueOf(team.getPlayers().size()))
                            .replace("<opponent_team_count>", String.valueOf(opponentTeam.getPlayers().size()))).replace("%splitter%", "┃").replace("|", "┃")));
                }
            } else if (match.isHCFMatch()) {
                Team team = match.getTeam(player);
                Team opponentTeam = match.getOpponentTeam(player);
                PvPClass pvpClass = plugin.getPvpClassManager().getEquippedClass(player);

                config.getStringList("SCOREBOARD.MATCH.HCF").forEach(line -> lines.add(CC.translate(line
                        .replace("<match_duration>", match.getDuration())
                        .replace("<match_kit>", "HCF")
                        .replace("<match_arena>", match.getArena().getDisplayName())
                        .replace("<match_type>", "HCF")
                        .replace("<player_count>", String.valueOf(match.getPlayers().size()))
                        .replace("<your_name>", player.getName())
                        .replace("<your_class>", pvpClass == null ? "None" : pvpClass.getName())
                        .replace("<your_team_alive>", String.valueOf(team.getAliveCount()))
                        .replace("<opponent_team_alive>", String.valueOf(opponentTeam.getAliveCount()))
                        .replace("<your_team_count>", String.valueOf(team.getPlayers().size()))
                        .replace("<opponent_team_count>", String.valueOf(opponentTeam.getPlayers().size())))
                        .replace("%splitter%", "┃").replace("|", "┃")));

                if (pvpClass instanceof Bard) {
                    final Bard bardClass = (Bard) pvpClass;

                    config.getStringList("SCOREBOARD.MATCH.HCF_BARD_ADDITION").forEach(line -> lines.add(CC.translate(line
                            .replace( "<your_bard_energy>", String.valueOf(bardClass.getEnergy(player)))
                            .replace("%splitter%", "┃").replace("|", "┃"))));
                }
            } else if (match.isFreeForAllMatch()) {
                Team team = match.getTeam(player);

                config.getStringList("SCOREBOARD.MATCH.FFA").forEach(line -> lines.add(CC.translate(line
                        .replace("<match_duration>", match.getDuration())
                        .replace("<match_kit>", match.getKit().getDisplayName())
                        .replace("<match_arena>", match.getArena().getDisplayName())
                        .replace("<match_type>", "FFA")
                        .replace("<total_count>", String.valueOf(team.getPlayers().size()))
                        .replace("<your_name>", player.getName())
                        .replace("<alive_count>", String.valueOf(team.getAliveCount())))
                        .replace("%splitter%", "┃").replace("|", "┃")));

            }
        } else if (profile.isSpectating()) {
            Match match = profile.getMatch();
            if (match.isEnding()) {
                config.getStringList("SCOREBOARD.SPECTATOR.MATCH.ENDING").forEach(line -> lines.add(CC.translate(line
                        .replace("<match_duration>", match.getDuration())
                        .replace("<player_count>", String.valueOf(match.getPlayers().size())))
                        .replace("%splitter%", "┃").replace("|", "┃")));

            } else if (match.isSoloMatch()) {
                int playerA = PlayerUtil.getPing(match.getTeamPlayerA().getPlayer());
                int playerB = PlayerUtil.getPing(match.getTeamPlayerB().getPlayer());

                if (match instanceof BoxingMatch) {
                    config.getStringList("SCOREBOARD.SPECTATOR.MATCH.BOXING").forEach(line -> lines.add(CC.translate(line
                            .replace("<match_kit>", match.getKit().getDisplayName())
                            .replace("<match_duration>", match.getDuration())
                            .replace("<playerA_name>", match.getTeamPlayerA().getUsername())
                            .replace("<playerB_name>", match.getTeamPlayerB().getUsername())
                            .replace("<match_arena>", match.getArena().getDisplayName())
                            .replace("<playerA_ping>", String.valueOf(playerA))
                            .replace("<playerB_ping>", String.valueOf(playerB))
                            .replace("<playerA_hits>", String.valueOf(match.getTeamPlayerA().getHits()))
                            .replace("<playerB_hits>", String.valueOf(match.getTeamPlayerB().getHits()))
                            .replace("<player_count>", String.valueOf(match.getPlayers().size()))
                            .replace("<match_type>", "Boxing"))
                            .replace("%splitter%", "┃")
                            .replace("|", "┃")));

                } else if (match instanceof SoloBridgeMatch) {
                    SoloBridgeMatch soloBridgeMatch = (SoloBridgeMatch) match;
                    config.getStringList("SCOREBOARD.SPECTATOR.MATCH.BRIDGE").forEach(line -> lines.add(CC.translate(line
                            .replace("<match_kit>", match.getKit().getDisplayName())
                            .replace("<match_duration>", match.getDuration())
                            .replace("<playerA_name>", match.getTeamPlayerA().getUsername())
                            .replace("<playerB_name>", match.getTeamPlayerB().getUsername())
                            .replace("<match_arena>", match.getArena().getDisplayName())
                            .replace("<playerA_ping>", String.valueOf(playerA))
                            .replace("<playerB_ping>", String.valueOf(playerB))
                            .replace("<bridge_round>", String.valueOf(soloBridgeMatch.getRound()))
                            .replace("<blue_points_formatted>", this.getFormattedPoints(match.getTeamPlayerB().getPlayer()))
                            .replace("<red_points_formatted>", this.getFormattedPoints(match.getTeamPlayerA().getPlayer()))
                            .replace("<player_count>", String.valueOf(match.getPlayers().size()))
                            .replace("<match_type>", "Bridge"))
                            .replace("%splitter%", "┃")
                            .replace("|", "┃")));

                } else {
                    config.getStringList("SCOREBOARD.SPECTATOR.MATCH.SOLO").forEach(line -> lines.add(CC.translate(line
                            .replace("<match_kit>", match.getKit().getDisplayName())
                            .replace("<match_duration>", match.getDuration())
                            .replace("<match_arena>", match.getArena().getDisplayName())
                            .replace("<playerA_name>", match.getTeamPlayerA().getUsername())
                            .replace("<playerB_name>", match.getTeamPlayerB().getUsername())
                            .replace("<playerA_ping>", String.valueOf(playerA))
                            .replace("<playerB_ping>", String.valueOf(playerB))
                            .replace("<player_count>", String.valueOf(match.getPlayers().size()))
                            .replace("<match_type>", "Solo"))
                            .replace("%splitter%", "┃").replace("|", "┃")));
                }
            } else if (match.isTeamMatch()) {
                if (match instanceof TeamBridgeMatch) {
                    TeamBridgeMatch teamBridgeMatch = (TeamBridgeMatch) match;

                    int teamAPoints = teamBridgeMatch.getTeamAPoints();
                    int teamBPoints = teamBridgeMatch.getTeamBPoints();

                    config.getStringList("MATCH.TEAM_BRIDGE").forEach(line -> lines.add(CC.translate(line
                            .replace("<teamA_points>", String.valueOf(teamAPoints))
                            .replace("<teamB_points>", String.valueOf(teamBPoints))
                            .replace("<bridge_round>", String.valueOf(teamBridgeMatch.getRound()))
                            .replace("<bow_cooldown>", (profile.getBowCooldown().hasExpired() ? "None" : profile.getBowCooldown().getTimeLeft() + "s"))
                            .replace("<red_points_formatted>", this.getFormattedPoints(match.getTeamA().getLeader().getPlayer()))
                            .replace("<blue_points_formatted>", this.getFormattedPoints(match.getTeamB().getLeader().getPlayer()))
                            .replace("<player_count>", String.valueOf(match.getPlayers().size()))
                            .replace("<match_type>", "Bridge")
                            .replace("%splitter%", "┃")
                            .replace("|", "┃"))
                    ));

                } else {
                    config.getStringList("SCOREBOARD.SPECTATOR.MATCH.TEAM").forEach(line -> lines.add(CC.translate(line
                            .replace("<match_kit>", match.getKit().getDisplayName())
                            .replace("<match_duration>", match.getDuration())
                            .replace("<player_count>", String.valueOf(match.getPlayers().size()))
                            .replace("<match_type>", "Team")
                            .replace("<match_arena>", match.getArena().getDisplayName())
                            .replace("<teamA_leader_name>", match.getTeamA().getLeader().getUsername())
                            .replace("<teamB_leader_name>", match.getTeamB().getLeader().getUsername())
                            .replace("<teamA_size>", String.valueOf(match.getTeamA().getPlayers().size()))
                            .replace("<teamB_size>", String.valueOf(match.getTeamB().getPlayers().size()))).replace("%splitter%", "┃").replace("|", "┃")));
                }
            } else if (match.isHCFMatch()) {
                config.getStringList("SCOREBOARD.SPECTATOR.MATCH.HCF").forEach(line -> lines.add(CC.translate(line
                        .replace("<match_kit>", "HCF")
                        .replace("<match_duration>", match.getDuration())
                        .replace("<player_count>", String.valueOf(match.getPlayers().size()))
                        .replace("<match_type>", "HCF")
                        .replace("<match_arena>", match.getArena().getDisplayName())
                        .replace("<teamA_leader_name>", match.getTeamA().getLeader().getUsername())
                        .replace("<teamB_leader_name>", match.getTeamB().getLeader().getUsername())
                        .replace("<teamA_size>", String.valueOf(match.getTeamA().getPlayers().size()))
                        .replace("<teamB_size>", String.valueOf(match.getTeamB().getPlayers().size()))).replace("%splitter%", "┃").replace("|", "┃")));
            } else if (match.isFreeForAllMatch()) {
                final Team team = match.getTeam(player);

                config.getStringList("SCOREBOARD.SPECTATOR.MATCH.FFA").forEach(line -> lines.add(CC.translate(line
                        .replace("<match_kit>", match.getKit().getDisplayName())
                        .replace("<match_duration>", match.getDuration())
                        .replace("<match_type>", "FFA")
                        .replace("<match_arena>", match.getArena().getDisplayName())
                        .replace("<alive_count>", String.valueOf(team.getAliveCount()))
                        .replace("<total_count>", String.valueOf(team.getPlayers().size()))).replace("%splitter%", "┃").replace("|", "┃")));
            }
        } else if ((profile.isSpectating() && profile.getEvent() != null) || profile.isInEvent()) {
            Event event = profile.getEvent();
            if (!event.isTeam()) {
                if (event.isWaiting()) {
                    String status;
                    if (event.getCooldown() == null) {
                        status=CC.translate(config.getString("SCOREBOARD.EVENT.SOLO.STATUS_WAITING")
                                .replace("<event_name>", event.getName())
                                .replace("<event_host_name>", event.getHost().getUsername())
                                .replace("<event_player_count>", String.valueOf(event.getEventPlayers().size()))
                                .replace("<event_max_players>", String.valueOf(event.getMaxPlayers()))).replace("%splitter%", "┃").replace("|", "┃");

                    } else {
                        String remaining=TimeUtil.millisToSeconds(event.getCooldown().getRemaining());
                        if (remaining.startsWith("-")) remaining="0.0";

                        String finalRemaining=remaining;
                        status=CC.translate(config.getString("SCOREBOARD.EVENT.SOLO.STATUS_COUNTING")
                                .replace("<event_host_name>", event.getHost().getUsername())
                                .replace("<event_name>", event.getName())
                                .replace("<event_remaining>", finalRemaining)
                                .replace("<event_player_count>", String.valueOf(event.getEventPlayers().size()))
                                .replace("<event_max_players>", String.valueOf(event.getMaxPlayers()))).replace("%splitter%", "┃").replace("|", "┃");

                    }
                    config.getStringList("SCOREBOARD.EVENT.SOLO.WAITING").forEach(line -> lines.add(CC.translate(line
                            .replace("<event_host_name>", event.getHost().getUsername())
                            .replace("<event_name>", event.getName())
                            .replace("<event_status>", status)
                            .replace("<event_player_count>", String.valueOf(event.getEventPlayers().size()))
                            .replace("<event_max_players>", String.valueOf(event.getMaxPlayers()))).replace("%splitter%", "┃").replace("|", "┃")));

                } else {
                    config.getStringList("SCOREBOARD.EVENT.SOLO.FIGHTING").forEach(line -> lines.add(CC.translate(line
                            .replace("<event_host_name>", event.getName())
                            .replace("<event_name>", event.getName())
                            .replace("<event_duration>", event.getDuration())
                            .replace("<event_players_alive>", String.valueOf(event.getRemainingPlayers().size()))
                            .replace("<event_player_count>", String.valueOf(event.getEventPlayers().size()))
                            .replace("<event_max_players>", String.valueOf(event.getMaxPlayers()))).replace("%splitter%", "┃").replace("|", "┃")));

                    if (!event.isFreeForAll()) {
                        config.getStringList("SCOREBOARD.EVENT.SOLO_ROUND_ADDITION").forEach(line -> lines.add(CC.translate(line
                                .replace("<event_playerA_name>", event.getRoundPlayerA().getUsername())
                                .replace("<event_playerA_ping>", String.valueOf(event.getRoundPlayerA().getPing()))
                                .replace("<event_playerB_name>", event.getRoundPlayerB().getUsername())
                                .replace("<event_playerB_ping>", String.valueOf(event.getRoundPlayerB().getPing()))
                                .replace("<event_host_name>", event.getName())
                                .replace("<event_name>", event.getName())
                                .replace("<event_duration>", event.getDuration())
                                .replace("<event_players_alive>", String.valueOf(event.getRemainingPlayers().size()))
                                .replace("<event_player_count>", String.valueOf(event.getEventPlayers().size()))
                                .replace("<event_max_players>", String.valueOf(event.getMaxPlayers()))).replace("%splitter%", "┃").replace("|", "┃")));
                    }
                }
            } else {
                if (event.isWaiting()) {
                    String status;
                    if (event.getCooldown() == null) {
                        status=CC.translate(config.getString("SCOREBOARD.EVENT.TEAM.STATUS_WAITING")
                                .replace("<event_name>", event.getName())
                                .replace("<event_host_name>", event.getHost().getUsername())
                                .replace("<event_player_count>", String.valueOf(event.getEventTeamPlayers().size()))
                                .replace("<event_max_players>", String.valueOf(event.getMaxPlayers()))).replace("%splitter%", "┃").replace("|", "┃");

                    } else {
                        String remaining=TimeUtil.millisToSeconds(event.getCooldown().getRemaining());
                        if (remaining.startsWith("-")) remaining="0.0";

                        String finalRemaining=remaining;
                        status=CC.translate(config.getString("SCOREBOARD.EVENT.TEAM.STATUS_COUNTING")
                                .replace("<event_host_name>", event.getHost().getUsername())
                                .replace("<event_name>", event.getName())
                                .replace("<event_remaining>", finalRemaining)
                                .replace("<event_player_count>", String.valueOf(event.getEventPlayers().size()))
                                .replace("<event_max_players>", String.valueOf(event.getMaxPlayers()))).replace("%splitter%", "┃").replace("|", "┃");

                    }
                    config.getStringList("SCOREBOARD.EVENT.TEAM.WAITING").forEach(line -> lines.add(CC.translate(line
                            .replace("<event_host_name>", event.getHost().getUsername())
                            .replace("<event_name>", event.getName())
                            .replace("<event_status>", status)
                            .replace("<event_player_count>", String.valueOf(event.getEventPlayers().size()))
                            .replace("<event_max_players>", String.valueOf(event.getMaxPlayers()))).replace("%splitter%", "┃").replace("|", "┃")));

                } else {
                    config.getStringList("SCOREBOARD.EVENT.TEAM.FIGHTING").forEach(line -> lines.add(CC.translate(line
                            .replace("<event_host_name>", event.getHost().getUsername())
                            .replace("<event_duration>", event.getDuration())
                            .replace("<event_name>", event.getName())
                            .replace("<event_players_alive>", String.valueOf(event.getRemainingPlayers().size()))
                            .replace("<event_player_count>", String.valueOf(event.getEventPlayers().size()))
                            .replace("<event_max_players>", String.valueOf(event.getMaxPlayers()))).replace("%splitter%", "┃").replace("|", "┃")));

                    if (!event.isFreeForAll()) {
                        config.getStringList("SCOREBOARD.EVENT.TEAM_ROUND_ADDITION").forEach(line -> lines.add(CC.translate(line
                                .replace("<event_teamA_name>", event.getRoundTeamA().getColor().getTitle())
                                .replace("<event_teamB_name>", event.getRoundTeamB().getColor().getTitle())
                                .replace("<event_teamA_size>", String.valueOf(event.getRoundTeamA().getPlayers().size()))
                                .replace("<event_teamB_size>", String.valueOf(event.getRoundTeamB().getPlayers().size()))
                                .replace("<event_host_name>", event.getHost().getUsername())
                                .replace("<event_name>", event.getName())
                                .replace("<event_duration>", event.getDuration())
                                .replace("<event_players_alive>", String.valueOf(event.getRemainingPlayers().size()))
                                .replace("<event_player_count>", String.valueOf(event.getEventTeamPlayers().size()))
                                .replace("<event_max_players>", String.valueOf(event.getMaxPlayers()))).replace("%splitter%", "┃").replace("|", "┃")));
                    }
                }
            }
        }
        if (config.getBoolean("SCOREBOARD.FOOTER_ENABLED")) {
            lines.add("");
            lines.add(config.getStringOrDefault("SCOREBOARD.FOOTER", "&7&odemo.refinedev.xyz"));
        }
        lines.add(config.getStringOrDefault("SCOREBOARD.LINES", CC.SB_BAR));

        return lines.stream().map(line -> {
            if (line != null && Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
              return PlaceholderAPI.setPlaceholders(player, line);
            }
            return line;
        }).collect(Collectors.toList());
    }


    public String getFormattedPoints(Player player) {
        Profile profile = plugin.getProfileManager().getByPlayer(player);
        Match match = profile.getMatch();
        int points = 0;

        if (match instanceof SoloBridgeMatch) {
            TeamPlayer teamPlayer = match.getTeamPlayer(player);
            SoloBridgeMatch soloBridgeMatch = (SoloBridgeMatch) match;
            points = match.getTeamPlayerA().equals(teamPlayer) ? soloBridgeMatch.getPlayerAPoints() : soloBridgeMatch.getPlayerBPoints();
        } else if (match instanceof TeamBridgeMatch) {
            Team team = match.getTeam(player);
            TeamBridgeMatch teamBridgeMatch = (TeamBridgeMatch) match;
            points = match.getTeamA().equals(team) ? teamBridgeMatch.getTeamAPoints() : teamBridgeMatch.getTeamBPoints();
        }

        switch (points) {
            case 3:
                return CC.translate("&a\u2b24&a\u2b24&a\u2b24");
            case 2:
                return CC.translate("&a\u2b24&a\u2b24&7\u2b24");
            case 1:
                return CC.translate("&a\u2b24&7\u2b24\u2b24");
        }
        return CC.translate("&7\u2b24\u2b24\u2b24");
    }


    public String getEloRangeFormat(Profile profile) {
        return config.getStringOrDefault("SCOREBOARD.ELO_RANGE_FORMAT", "<min_range> -> <max_range>")
                .replace("<min_range>", String.valueOf(profile.getQueueProfile().getMinRange()))
                .replace("<max_range>", String.valueOf(profile.getQueueProfile().getMaxRange()));
    }
}
