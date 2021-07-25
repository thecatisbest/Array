package xyz.refinedev.practice.profile.hotbar;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
public enum HotbarType {

    QUEUE_JOIN_RANKED(null), 
    QUEUE_JOIN_UNRANKED(null),
    QUEUE_JOIN_CLAN(null),
    QUEUE_LEAVE(null),
    PARTY_EVENTS(null), 
    PARTY_CREATE("party create"), 
    PARTY_DISBAND("party disband"), 
    PARTY_LEAVE("party leave"),
    PARTY_INFORMATION("party info"),
    PARTY_CLASSES(null),
    PARTY_SETTINGS(null), 
    OTHER_PARTIES(null), 
    PARTY_INFO(null),
    KIT_EDITOR(null),
    MAIN_MENU(null),
    SPECTATOR_SHOW(null),
    SPECTATOR_HIDE(null),
    SPECTATE_STOP("stopspectating"), 
    EVENT_JOIN("event join"),
    EVENT_TEAM(null),
    EVENT_LEAVE("event leave"),
    REMATCH_REQUEST("rematch"),
    REMATCH_ACCEPT("rematch"),
    PARKOUR_SPAWN(null),
    SPLEEF_MATCH(null),
    GULAG_GUN(null),
    DEFAULT_KIT(null);
    
    @Getter private final String command;
    
}
