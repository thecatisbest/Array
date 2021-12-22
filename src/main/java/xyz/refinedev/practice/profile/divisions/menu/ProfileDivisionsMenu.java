package xyz.refinedev.practice.profile.divisions.menu;

import org.bukkit.entity.Player;
import xyz.refinedev.practice.Array;
import xyz.refinedev.practice.profile.Profile;
import xyz.refinedev.practice.profile.divisions.menu.buttons.ProfileELODivisionsButton;
import xyz.refinedev.practice.profile.divisions.menu.buttons.ProfileXPDivisionsButton;
import xyz.refinedev.practice.util.config.impl.FoldersConfigurationFile;
import xyz.refinedev.practice.util.menu.Button;
import xyz.refinedev.practice.util.menu.pagination.PaginatedMenu;

import java.util.HashMap;
import java.util.Map;

/**
 * This Project is property of Refine Development © 2021
 * Redistribution of this Project is not allowed
 *
 * @author Drizzy
 * Created: 9/25/2021
 * Project: Array
 */

//TODO: Include a back button in each and every profile menu button
public class ProfileDivisionsMenu extends PaginatedMenu {

    private final Array plugin = this.getPlugin();
    private final FoldersConfigurationFile config = plugin.getMenuManager().getConfigByName("profile_divisions");

    /**
     * @param player player viewing the inventory
     * @return title of the inventory before the page number is added
     */
    @Override
    public String getPrePaginatedTitle(Player player) {
        return config.getString("TITLE");
    }

    /**
     * Size of the inventory
     *
     * @return {@link Integer}
     */
    @Override
    public int getMaxItemsPerPage(Player player) {
        return config.getInteger("SIZE");
    }

    /**
     * @param player player viewing the inventory
     * @return a map of button that will be paginated and spread across pages
     */
    @Override
    public Map<Integer, Button> getAllPagesButtons(Player player) {
        Map<Integer, Button> buttons = new HashMap<>();
        Profile profile = plugin.getProfileManager().getByUUID(player.getUniqueId());
        if (plugin.getDivisionsManager().isXPBased()) {
            buttons.put(buttons.size(), new ProfileXPDivisionsButton(profile, plugin.getDivisionsManager().getDivisionByXP(profile.getExperience())));
        } else {
            buttons.put(buttons.size(), new ProfileELODivisionsButton(profile, plugin.getDivisionsManager().getDivisionByELO(profile.getGlobalElo())));
        }
        return buttons;
    }
}