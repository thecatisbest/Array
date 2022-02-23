package xyz.refinedev.practice.profile.killeffect.menu;

import org.bukkit.entity.Player;
import xyz.refinedev.practice.Array;
import xyz.refinedev.practice.profile.killeffect.KillEffect;
import xyz.refinedev.practice.profile.killeffect.menu.buttons.KEButton;
import xyz.refinedev.practice.util.config.impl.FoldersConfigurationFile;
import xyz.refinedev.practice.util.menu.Button;
import xyz.refinedev.practice.util.menu.pagination.PaginatedMenu;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This Project is property of Refine Development © 2021
 * Redistribution of this Project is not allowed
 *
 * @author Drizzy
 * Created: 8/14/2021
 * Project: Array
 */

public class KEPaginatedMenu extends PaginatedMenu {

    private final FoldersConfigurationFile config;

    public KEPaginatedMenu(Array plugin) {
        super(plugin);
        
        this.config = plugin.getMenuHandler().getConfigByName("profile_killeffects");
    }

    /**
     * @param player player viewing the inventory
     * @return title of the inventory before the page number is added
     */
    @Override
    public String getPrePaginatedTitle(Player player) {
        return config.getString("TITLE");
    }

    /**
     * Get the size of the Menu's Inventory
     *
     * @return {@link Integer} size
     */
    @Override
    public int getSize() {
        return config.getInteger("SIZE");
    }

    /**
     * @param player player viewing the inventory
     * @return a map of button that will be paginated and spread across pages
     */
    @Override
    public Map<Integer, Button> getAllPagesButtons(Player player) {
        Map<Integer, Button> buttons = new HashMap<>();
        List<KillEffect> effects = this.getPlugin().getKillEffectManager().getKillEffects();
        Comparator.comparingInt(KillEffect::getPriority).reversed();
        for ( KillEffect killEffect : effects) {
            buttons.put(buttons.size(), new KEButton(this.getPlugin(), killEffect));
        }
        return buttons;
    }
}
