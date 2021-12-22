package xyz.refinedev.practice.event.impl.spleef.task;

import xyz.refinedev.practice.Locale;
import xyz.refinedev.practice.event.Event;
import xyz.refinedev.practice.event.EventState;
import xyz.refinedev.practice.event.task.EventStartTask;
import xyz.refinedev.practice.util.other.Cooldown;

/**
 * This Project is property of Refine Development © 2021
 * Redistribution of this Project is not allowed
 *
 * @author Drizzy
 * Created: 9/13/2021
 * Project: Array
 */

public class SpleefStartTask extends EventStartTask {
    
    public SpleefStartTask(Event event) {
        super(event);
    }

    @Override
    public void onRun() {
        if (getTicks() >= 120) {
            this.getEvent().handleEnd();
            return;
        }

        if (this.getEvent().getPlayers().size() <= 1 && this.getEvent().getCooldown() != null) {
            this.getEvent().setCooldown(null);
            this.getEvent().broadcastMessage(Locale.EVENT_NOT_ENOUGH_PLAYERS.toString().replace("<event_name>", getEvent().getName()));
        }

        if (this.getEvent().getPlayers().size() == this.getEvent().getMaxPlayers() || (getTicks() >= 30 && this.getEvent().getPlayers().size() >= 2)) {
            if (this.getEvent().getCooldown() == null) {
                this.getEvent().setCooldown(new Cooldown(11_000));
                this.getEvent().broadcastMessage(Locale.EVENT_STARTING.toString().replace("<event_name>", getEvent().getName()));
            } else {
                if (this.getEvent().getCooldown().hasExpired()) {
                    this.getEvent().setState(EventState.ROUND_STARTING);
                    this.getEvent().onRound();
                    this.getEvent().setTotalPlayers(this.getEvent().getPlayers().size());
                    this.getEvent().setEventTask(new SpleefRoundStartTask(this.getEvent()));
                }
            }
        }

        if (getTicks() % 10 == 0) {
            this.getEvent().announce();
        }
    }
}
