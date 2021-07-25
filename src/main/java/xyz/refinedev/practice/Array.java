package xyz.refinedev.practice;

import com.google.gson.Gson;
import com.lunarclient.bukkitapi.cooldown.LCCooldown;
import com.lunarclient.bukkitapi.cooldown.LunarClientAPICooldown;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import xyz.refinedev.practice.adapters.NameTagAdapter;
import xyz.refinedev.practice.adapters.ScoreboardAdapter;
import xyz.refinedev.practice.adapters.TablistAdapter;
import xyz.refinedev.practice.api.API;
import xyz.refinedev.practice.api.ArrayAPI;
import xyz.refinedev.practice.arena.Arena;
import xyz.refinedev.practice.arena.ArenaProvider;
import xyz.refinedev.practice.arena.ArenaTypeProvider;
import xyz.refinedev.practice.arena.meta.RatingType;
import xyz.refinedev.practice.arena.meta.RatingTypeProvider;
import xyz.refinedev.practice.clan.Clan;
import xyz.refinedev.practice.essentials.Essentials;
import xyz.refinedev.practice.events.EventManager;
import xyz.refinedev.practice.events.EventProvider;
import xyz.refinedev.practice.events.EventType;
import xyz.refinedev.practice.managers.CMDManager;
import xyz.refinedev.practice.managers.ListenersManager;
import xyz.refinedev.practice.managers.RatingsManager;
import xyz.refinedev.practice.profile.divisions.Divisions;
import xyz.refinedev.practice.arena.ArenaType;
import xyz.refinedev.practice.kit.KitProvider;
import xyz.refinedev.practice.managers.ClassManager;
import xyz.refinedev.practice.pvpclasses.bard.EffectRestorer;
import xyz.refinedev.practice.leaderboards.external.LeaderboardPlaceholders;
import xyz.refinedev.practice.profile.hotbar.Hotbar;
import xyz.refinedev.practice.kit.Kit;
import xyz.refinedev.practice.hook.SpigotHook;
import xyz.refinedev.practice.match.Match;
import xyz.refinedev.practice.party.Party;
import xyz.refinedev.practice.profile.Profile;
import xyz.refinedev.practice.profile.ProfileProvider;
import xyz.refinedev.practice.profile.rank.Rank;
import xyz.refinedev.practice.queue.Queue;

import xyz.refinedev.practice.util.command.CommandService;
import xyz.refinedev.practice.util.command.Drink;
import xyz.refinedev.practice.util.nametags.NameTagHandler;
import xyz.refinedev.practice.util.other.Description;
import xyz.refinedev.practice.util.other.EntityHider;
import xyz.refinedev.practice.util.chat.CC;
import xyz.refinedev.practice.util.config.BasicConfigurationFile;
import xyz.refinedev.practice.util.scoreboard.Assemble;
import xyz.refinedev.practice.util.scoreboard.AssembleStyle;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.refinedev.tablist.TablistHandler;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * This Project is the property of Refine Development © 2021
 * Redistribution of this Project is not allowed
 *
 * @author Drizzy
 * Created at 2/13/2021
 * Project: Array
 */

@Getter @Setter
public class Array extends JavaPlugin {

    @Getter private static CommandService drink;
    @Getter private static Array instance;
    @Getter private static API api;

    public static Gson GSON = new Gson();

    /*
     * All ours Configs
     */
    private BasicConfigurationFile mainConfig, arenasConfig, kitsConfig, eventsConfig, menuConfig,
            messagesConfig, scoreboardConfig, tablistConfig, divisionsConfig, hotbarConfig, rateConfig;//, brawlConfig;

    /*
     * All ours Async Threads
     */
    public Executor mainThread;
    public Executor taskThread;
    public Executor mongoThread;

    /*
     * Mongo Database
     */
    private MongoDatabase mongoDatabase;

    /*
     * Tab and Scoreboard Adapters
     */
    private Assemble scoreboardHandler;
    private TablistHandler tablistHandler;
    private NameTagHandler nameTagHandler;

    /*
     * All Managers
     */
    private EventManager eventManager;
    private ListenersManager listenersManager;
    private CMDManager cmdManager;
    private Divisions divisionsManager;
    private RatingsManager ratingsManager;
    private ClassManager classManager;
    private EffectRestorer effectRestorer;

    /*
     * Essential Utilities
     */
    public static Random random;
    private Essentials essentials;
    private EntityHider entityHider;
    private boolean disabling = false;

    @Override
    public void onEnable() {
        instance = this;
        random = new Random();
        api = new ArrayAPI();
        drink = Drink.get(this);

        System.setProperty("file.encoding", "UTF-8");

        /*
         * Async Executor Threads
         */
        this.mainThread = Executors.newSingleThreadExecutor();
        this.mongoThread = Executors.newSingleThreadExecutor();
        this.taskThread = Executors.newSingleThreadExecutor();

        /*
         * Main Configs
         */
        mainConfig = new BasicConfigurationFile(this, "config");
        arenasConfig = new BasicConfigurationFile(this, "arenas");
        kitsConfig = new BasicConfigurationFile(this, "kits");
        eventsConfig = new BasicConfigurationFile(this, "events");
        hotbarConfig = new BasicConfigurationFile(this, "hotbar");
        messagesConfig = new BasicConfigurationFile(this, "lang");
        tablistConfig = new BasicConfigurationFile(this, "tablist");
        scoreboardConfig = new BasicConfigurationFile(this, "scoreboard");
        divisionsConfig = new BasicConfigurationFile(this, "divisions");
        menuConfig = new BasicConfigurationFile(this, "menus");
        rateConfig = new BasicConfigurationFile(this, "ratings");
        //brawlConfig = new BasicConfigurationFile(this, "brawl");

        if (!Description.getAuthor().contains("RefineDevelopment") || !Description.getName().contains("Array")
                || !Description.getAuthor().contains("Nick_0251") || !Description.getWebsite().equalsIgnoreCase("https://dsc.gg/refine")) {
            logger(CC.CHAT_BAR);
            logger("  &cYou edited the plugin.yml, haha get caught in 4k");
            logger("  &cPlease check your plugin.yml and try again.");
            logger("                 &cDisabling Array");
            logger(CC.CHAT_BAR);
            shutDown();
            return;
        }

        this.loadMessages();

        this.essentials = new Essentials();
        this.essentials.init();

        this.preload();

        this.divisionsManager = new Divisions();
        this.eventManager = new EventManager();
        this.eventManager.load();

        if (this.essentials.getMeta().isRatingEnabled()) {
            this.ratingsManager = new RatingsManager();
            this.ratingsManager.init();
        }

        this.cmdManager = new CMDManager();
        this.cmdManager.init();

        this.listenersManager = new ListenersManager();
        this.listenersManager.init();

        this.classManager = new ClassManager();
        this.classManager.init();

        this.effectRestorer = new EffectRestorer();
        this.effectRestorer.init();

        this.entityHider = EntityHider.enable();
        this.preloadAdapters();
    }

    @Override
    public void onDisable() {
        //Stop all matches and Remove the placed Block
        Match.cleanup();

        //Save Everything before disabling to prevent data loss
        Kit.getKits().forEach(Kit::save);
        Arena.getArenas().forEach(Arena::save);
        Profile.getProfiles().values().forEach(Profile::save);
        Clan.getClans().forEach(Clan::save);

        //Save our Values to Config
        this.essentials.save();
        this.eventManager.save();
        this.classManager.onDisable();

        //Clear out the PlayerList for Vanilla Tab
        Profile.getPlayerList().clear();
        this.disabling = true;
    }

    public void preload() {
        //Static Abuse be like, but i aint using managers rn
        //cuz praxi was always static af
        this.preLoadMongo();
        Kit.preload();
        Profile.preload();
        Clan.preload();
        Arena.preload();
        Hotbar.preload();
        Match.preload();
        Party.preLoad();
        Queue.preLoad();
        Rank.preLoad();
        SpigotHook.preload();

        drink.bind(Arena.class).toProvider(new ArenaProvider());
        drink.bind(ArenaType.class).toProvider(new ArenaTypeProvider());
        drink.bind(Kit.class).toProvider(new KitProvider());
        drink.bind(Profile.class).toProvider(new ProfileProvider());
        drink.bind(EventType.class).toProvider(new EventProvider());
        drink.bind(RatingType.class).toProvider(new RatingTypeProvider());
    }

    public void preloadAdapters() {

        logger("&7Setting up Scoreboard");
        this.scoreboardHandler = new Assemble(this, new ScoreboardAdapter());
        this.scoreboardHandler.setAssembleStyle(AssembleStyle.MODERN);
        this.scoreboardHandler.setTicks(2);

        logger("&7Setting up NameTags");
        this.nameTagHandler = new NameTagHandler();
        this.nameTagHandler.hook();
        this.nameTagHandler.registerProvider(new NameTagAdapter());

        if (Essentials.getMeta().isTabEnabled()) {
            logger("&7Setting up Tablist");
            this.tablistHandler = new TablistHandler(new TablistAdapter(), this, 20);
        }

        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new LeaderboardPlaceholders().register();
            logger("&7Found PlaceholderAPI, Registering Expansions....");
        } else {
            logger("&cPlaceholderAPI was NOT found, Holograms will NOT work!");
        }

        if (Bukkit.getPluginManager().isPluginEnabled("LunarClient-API")) {
            logger("&7Found LunarClient-API, Registering Cooldowns....");
            LunarClientAPICooldown.registerCooldown(new LCCooldown("Enderpearl", Essentials.getMeta().getEnderpearlCooldown(), TimeUnit.SECONDS, Material.ENDER_PEARL));
            LunarClientAPICooldown.registerCooldown(new LCCooldown("Bow", Essentials.getMeta().getBowCooldown(), TimeUnit.SECONDS, Material.BOW));
        }
    }

    private void preLoadMongo() {
        if (mainConfig.getBoolean("MONGO.URI-MODE")) {
            MongoClient client = new MongoClient(new MongoClientURI(mainConfig.getString("MONGO.URI.CONNECTION_STRING")));
            this.mongoDatabase = client.getDatabase(mainConfig.getString("MONGO.URI.DATABASE"));
        } else {
            MongoClient client;
            if (mainConfig.getBoolean("MONGO.NORMAL.AUTHENTICATION.ENABLED")) {
                MongoCredential credential = MongoCredential.createCredential(
                        mainConfig.getString("MONGO.NORMAL.AUTHENTICATION.USERNAME"),
                        mainConfig.getString("MONGO.NORMAL.DATABASE"),
                        mainConfig.getString("MONGO.NORMAL.AUTHENTICATION.PASSWORD").toCharArray()
                );

                client = new MongoClient(new ServerAddress(mainConfig.getString("MONGO.NORMAL.HOST"),
                        mainConfig.getInteger("MONGO.NORMAL.PORT")), Collections.singletonList(credential));
            } else {
                client = new MongoClient(mainConfig.getString("MONGO.NORMAL.HOST"),
                        mainConfig.getInteger("MONGO.NORMAL.PORT"));
            }
            this.mongoDatabase = client.getDatabase(mainConfig.getString("MONGO.NORMAL.DATABASE"));
        }
    }

    public void loadMessages() {
        mainConfig.getConfiguration().options().header(
                "######################################################################\n" +
                "                                                                     #\n" +
                "          Array Practice Core - Developed By Drizzy#0278             #\n" +
                "       Bought at Refine Development - https://dsc.gg/refine          #\n" +
                "                                                                     #\n" +
                "######################################################################");
        mainConfig.save();

        if (this.messagesConfig == null) return;

        Arrays.stream(Locale.values()).forEach(language -> {
            if (this.messagesConfig.getConfiguration().getString(language.getPath()) == null || this.messagesConfig.getConfiguration().getStringList(language.getPath()) == null) {

                if (language.getListValue() != null) {
                    this.messagesConfig.getConfiguration().set(language.getPath(), language.getListValue());
                }

                if (language.getValue() != null) {
                    this.messagesConfig.getConfiguration().set(language.getPath(), language.getValue());
                }
            }

        });
        messagesConfig.save();
    }

    public static void shutDown() {
        logger("Shutting down Array!");
        Bukkit.shutdown();
    }

    public static void logger(String message) {
        String msg = CC.translate("&8[&cArray&8] &r" + message);
        Bukkit.getConsoleSender().sendMessage(msg);
    }
}
