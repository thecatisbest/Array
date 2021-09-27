package xyz.refinedev.practice.party.menu;

import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import xyz.refinedev.practice.profile.Profile;
import xyz.refinedev.practice.util.chat.CC;
import xyz.refinedev.practice.util.inventory.ItemBuilder;
import xyz.refinedev.practice.util.menu.Button;
import xyz.refinedev.practice.util.menu.Menu;
import xyz.refinedev.practice.util.other.SkullCreator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PartyListMenu extends Menu {
    @Override
    public String getTitle(final Player player) {
        return "&cClick to Manage a Member";
    }
    
    @Override
    public Map<Integer, Button> getButtons(final Player player) {
        final Map<Integer, Button> buttons =new HashMap<>();
        final Profile profile = plugin.getProfileManager().getByUUID(player.getUniqueId());
        profile.getParty().getPlayers().forEach(pplayer -> buttons.put(buttons.size(), new PartyDisplayButton(pplayer)));
        return buttons;
    }

    @AllArgsConstructor
    public static class PartyDisplayButton extends Button {

        private final Player pplayer;
        
        @Override
        public ItemStack getButtonItem(final Player player) {
            final List<String> lore = new ArrayList<>();
            lore.add(CC.MENU_BAR);
            lore.add("&7Click here to manage");
            lore.add("&7" + pplayer.getName() + "!");
            lore.add(CC.MENU_BAR);
            return new ItemBuilder(SkullCreator.itemFromUuid(pplayer.getUniqueId())).name("&a&l" + this.pplayer.getName()).lore(lore).durability(3).build();
        }
        
        @Override
        public void clicked(final Player player, final ClickType clickType) {
            Menu.currentlyOpenedMenus.get(player.getName()).setClosedByMenu(true);
            final Profile senderProfile = plugin.getProfileManager().getByUUID(player.getUniqueId());
            final Profile receiverProfile = plugin.getProfileManager().getByUUID(this.pplayer.getUniqueId());
            if (!player.getUniqueId().equals(senderProfile.getParty().getLeader().getPlayer().getUniqueId())) {
                player.sendMessage(CC.RED + "You can only manage players as a leader.");
                return;
            }
            if (this.pplayer.getUniqueId().equals(receiverProfile.getParty().getLeader().getPlayer().getUniqueId())) {
                player.sendMessage(CC.RED + "You cannot manage yourself.");
                return;
            }
            if (senderProfile.getParty() != null && receiverProfile.getParty() == null) {
                player.sendMessage(CC.RED + "That player left your party!");
                return;
            }
            new PartyMemberMenu(this.pplayer).openMenu(player);
        }
    }
}
