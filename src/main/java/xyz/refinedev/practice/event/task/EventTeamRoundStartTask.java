package xyz.refinedev.practice.event.task;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import xyz.refinedev.practice.Array;
import xyz.refinedev.practice.Locale;
import xyz.refinedev.practice.event.Event;
import xyz.refinedev.practice.event.EventState;
import xyz.refinedev.practice.event.meta.EventTask;
import xyz.refinedev.practice.event.meta.player.EventPlayer;
import xyz.refinedev.practice.util.other.PlayerUtil;

import java.util.stream.Collectors;

/**
 * This Project is property of Refine Development © 2021
 * Redistribution of this Project is not allowed
 *
 * @author Drizzy
 * Created: 12/22/2021
 * Project: Array
 */

public class EventTeamRoundStartTask extends EventTask {

    public EventTeamRoundStartTask(Array plugin, Event event) {
        super(plugin, event, EventState.ROUND_STARTING);
    }

    @Override
    public void onRun() {
        if (getTicks() >= 3) {
            this.getEvent().broadcastMessage(Locale.EVENT_ROUND_STARTED.toString());
            this.getEvent().setEventTask(null);
            this.getEvent().setState(EventState.ROUND_FIGHTING);

            for ( Player playerA : this.getEvent().getRoundTeamB().getPlayers().stream().filter(sumoTeamPlayer -> sumoTeamPlayer != null && sumoTeamPlayer.getPlayer() != null && sumoTeamPlayer.getPlayer().isOnline()).map(EventPlayer::getPlayer).collect(Collectors.toList())) {
                if (playerA != null) {
                    playerA.playSound(playerA.getLocation(), Sound.NOTE_BASS, 1.0F, 1.0F);
                    PlayerUtil.allowMovement(playerA);
                }
            }
            for (Player playerB : this.getEvent().getRoundTeamB().getPlayers().stream().filter(sumoTeamPlayer -> sumoTeamPlayer != null && sumoTeamPlayer.getPlayer() != null && sumoTeamPlayer.getPlayer().isOnline()).map(EventPlayer::getPlayer).collect(Collectors.toList())) {
                if (playerB != null) {
                    playerB.playSound(playerB.getLocation(), Sound.NOTE_BASS, 1.0F, 1.0F);
                    PlayerUtil.allowMovement(playerB);
                }
            }

            this.getEvent().setRoundStart(System.currentTimeMillis());
        } else {
            int seconds = getSeconds();
            for (Player player : this.getEvent().getRoundTeamA().getPlayers().stream().filter(sumoTeamPlayer -> sumoTeamPlayer != null && sumoTeamPlayer.getPlayer() != null && sumoTeamPlayer.getPlayer().isOnline()).map(EventPlayer::getPlayer).collect(Collectors.toList())) {
                player.playSound(player.getLocation(), Sound.NOTE_PLING, 1.0f, 1.0f);
            }
            for (Player player : this.getEvent().getRoundTeamB().getPlayers().stream().filter(sumoTeamPlayer -> sumoTeamPlayer != null && sumoTeamPlayer.getPlayer() != null && sumoTeamPlayer.getPlayer().isOnline()).map(EventPlayer::getPlayer).collect(Collectors.toList())) {
                player.playSound(player.getLocation(), Sound.NOTE_PLING, 1.0f, 1.0f);
            }
            this.getEvent().broadcastMessage(Locale.EVENT_START_COUNTDOWN.toString().replace("<seconds>", String.valueOf(seconds)));
        }
    }
}