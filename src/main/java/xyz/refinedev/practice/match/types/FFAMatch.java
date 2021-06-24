package xyz.refinedev.practice.match.types;

import xyz.refinedev.practice.Array;
import xyz.refinedev.practice.arena.Arena;
import xyz.refinedev.practice.essentials.Essentials;
import xyz.refinedev.practice.queue.QueueType;
import xyz.refinedev.practice.kit.Kit;
import xyz.refinedev.practice.match.Match;
import xyz.refinedev.practice.match.MatchSnapshot;
import xyz.refinedev.practice.match.team.Team;
import xyz.refinedev.practice.match.team.TeamPlayer;
import xyz.refinedev.practice.hook.SpigotHook;
import xyz.refinedev.practice.profile.Profile;
import xyz.refinedev.practice.profile.ProfileState;
import xyz.refinedev.practice.util.location.Circle;
import xyz.refinedev.practice.util.nametags.NameTagHandler;
import xyz.refinedev.practice.util.other.PlayerUtil;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class FFAMatch extends Match {

    private final Team team;

    public FFAMatch(Team team, Kit kit, Arena arena) {
        super(null, kit, arena, QueueType.UNRANKED);

        this.team = team;
    }

    @Override
    public boolean isSoloMatch() {
        return false;
    }

    @Override
    public boolean isTeamMatch() {
        return false;
    }

    @Override
    public boolean isFreeForAllMatch() {
        return true;
    }

    @Override
    public boolean isHCFMatch() {
        return false;
    }

    @Override
    public boolean isTheBridgeMatch() {
        return false;
    }

    @Override
    public void setupPlayer(Player player) {
        TeamPlayer teamPlayer = getTeamPlayer(player);

        if (teamPlayer.isDisconnected()) {
            return;
        }

        teamPlayer.setAlive(true);

        PlayerUtil.reset(player);

        if (!getKit().getGameRules().isCombo()) {
            player.setMaximumNoDamageTicks(getKit().getGameRules().getHitDelay());
        }
        if (getKit().getGameRules().isCombo()) {
            player.setMaximumNoDamageTicks(0);
            player.setNoDamageTicks(3);
        }

        if (getKit().getGameRules().isStickSpawn()) {
            PlayerUtil.denyMovement(player);
        }

        if (getKit().getGameRules().isInfiniteSpeed()) {
            player.addPotionEffect(PotionEffectType.SPEED.createEffect(500000000, 2));
        }
        if (getKit().getGameRules().isInfiniteStrength()) {
            player.addPotionEffect(PotionEffectType.INCREASE_DAMAGE.createEffect(500000000, 2));
        }

        if (!getKit().getGameRules().isNoItems()) {
            Profile.getByUuid(player.getUniqueId()).getStatisticsData().get(this.getKit()).getKitItems().forEach((integer, itemStack) -> player.getInventory().setItem(integer, itemStack));
        }

        SpigotHook.getKnockbackType().appleKitKnockback(player, getKit());

        Team team = getTeam(player);
        for (Player enemy : team.getPlayers()) {

            Profile enemyProfile = Profile.getByPlayer(enemy);
            enemyProfile.handleVisibility();
        }
        NameTagHandler.reloadPlayer(player);
        NameTagHandler.reloadOthersFor(player);
    }

    public double getAverage(double one, double two) {
        double three = one + two;
        three = three / 2;
        return three;
    }

    @Override
    public void onStart() {
        int i = 0;
        for ( Player player : getPlayers() ) {
            Location midSpawn = this.getMidSpawn();

            List<Location> circleLocations = Circle.getCircle(midSpawn, Essentials.getMeta().getFfaSpawnRadius(), this.getPlayers().size());
            Location center = midSpawn.clone();
            Location loc = circleLocations.get(i);
            Location target = loc.setDirection(center.subtract(loc).toVector());

            player.teleport(target.add(0, 0.5, 0));
            circleLocations.remove(i);

            i++;
        }
    }

    @Override
    public boolean onEnd() {
            for ( TeamPlayer teamPlayer : team.getTeamPlayers() ) {
                if (!teamPlayer.isDisconnected() && teamPlayer.isAlive()) {
                    Player player=teamPlayer.getPlayer();

                    if (player != null) {
                        Profile profile = Profile.getByUuid(player.getUniqueId());
                        profile.handleVisibility();

                        getSnapshots().add(new MatchSnapshot(teamPlayer));
                    }
                }
            }

        new BukkitRunnable() {
            @Override
            public void run() {
                for (TeamPlayer firstTeamPlayer : team.getTeamPlayers()) {
                    if (!firstTeamPlayer.isDisconnected()) {
                        Player player = firstTeamPlayer.getPlayer();

                        if (player != null) {
                            if (firstTeamPlayer.isAlive()) {
                                getSnapshots().add(new MatchSnapshot(firstTeamPlayer));
                            }

                            player.setFireTicks(0);
                            player.updateInventory();

                            SpigotHook.getKnockbackType().appleKitKnockback(player, getKit());

                            Profile profile = Profile.getByUuid(player.getUniqueId());
                            profile.setState(ProfileState.IN_LOBBY);
                            profile.setMatch(null);
                            profile.handleVisibility();
                            profile.refreshHotbar();
                            profile.teleportToSpawn();
                        }
                    }
                }
            }
        }.runTaskLater(Array.getInstance(), (getKit().getGameRules().isWaterKill() || getKit().getGameRules().isLavaKill() || getKit().getGameRules().isParkour()) ? 0L : 40L);
        return true;
    }

    @Override
    public boolean canEnd() {
        return getAlivePlayers().size() == 1;
    }

    @Override
    public void onDeath(Player player, Player killer) {
        TeamPlayer teamPlayer = getTeamPlayer(player);
        getSnapshots().add(new MatchSnapshot(teamPlayer));

        PlayerUtil.reset(player);

        if (!canEnd() && !teamPlayer.isDisconnected()) {

            player.teleport(getMidSpawn());
            player.setAllowFlight(true);
            player.setFlying(true);

            Profile profile = Profile.getByUuid(player.getUniqueId());
            profile.refreshHotbar();
            profile.setState(ProfileState.SPECTATING);
        }
    }

    @Override
    public void onRespawn(Player player) {
       //Nothing ;)
    }

    @Override
    public Player getWinningPlayer() {
        if (getKit().getGameRules().isParkour()) {
            if (team.getDeadCount() == 1) {
                return team.getDeadTeamPlayers().get(0).getPlayer();
            } else {
                return null;
            }
        } else {
            if (team.getAliveTeamPlayers().size() == 1) {
                return team.getAliveTeamPlayers().get(0).getPlayer();
            } else {
                return null;
            }
        }
    }

    @Override
    public Team getWinningTeam() {
        throw new UnsupportedOperationException("Cannot getInstance winning team from a Brawl match");
    }

    @Override
    public TeamPlayer getTeamPlayerA() {
        throw new UnsupportedOperationException("Cannot getInstance team player from a Brawl match");
    }

    @Override
    public TeamPlayer getTeamPlayerB() {
        throw new UnsupportedOperationException("Cannot getInstance team player from a Brawl match");
    }

    @Override
    public List<TeamPlayer> getTeamPlayers() {
        throw new UnsupportedOperationException("Cannot getInstance team player from a Brawl match");
    }

    @Override
    public List<Player> getPlayers() {
        return team.getPlayers();
    }

    public List<Player> getAlivePlayers() {
        List<Player> players = new ArrayList<>();
        for (Player player : team.getPlayers()) {
            if (getTeamPlayer(player).isAlive()) {
                players.add(player);
            }
        }
        return players;
    }

    @Override
    public Team getTeamA() {
        throw new UnsupportedOperationException("Cannot getInstance team from a Brawl match");
    }

    @Override
    public Team getTeamB() {
        throw new UnsupportedOperationException("Cannot getInstance team from a Brawl match");
    }

    @Override
    public Team getTeam(Player player) {
        return team;
    }

    @Override
    public TeamPlayer getTeamPlayer(Player player) {
        for (TeamPlayer teamPlayer : team.getTeamPlayers()) {
            if (teamPlayer.getUuid().equals(player.getUniqueId())) {
                return teamPlayer;
            }
        }

        return null;
    }

    @Override
    public Team getOpponentTeam(Team team) {
        throw new UnsupportedOperationException("Cannot getInstance opponent team from a Brawl match");
    }

    @Override
    public Team getOpponentTeam(Player player) {
        throw new UnsupportedOperationException("Cannot getInstance opponent team from a Brawl match");
    }

    @Override
    public Player getOpponentPlayer(Player player) {
        throw new IllegalStateException("Cannot getInstance opponent player in Brawl match");
    }

    @Override
    public List<BaseComponent[]> generateEndComponents(Player player) {
        return null;
    }

    @Override
    public TeamPlayer getOpponentTeamPlayer(Player player) {
        throw new UnsupportedOperationException("Cannot getInstance opponent team from a Brawl match");
    }

    @Override
    public ChatColor getRelationColor(Player viewer, Player target) {
        if (viewer.equals(target)) {
            return ChatColor.GREEN;
        } else {
            if (team.containsPlayer(target)) {
                return ChatColor.RED;
            }
            return ChatColor.AQUA;
        }
    }

}