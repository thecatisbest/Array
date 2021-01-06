package me.array.ArrayPractice.arena;

import me.array.ArrayPractice.Practice;
import me.array.ArrayPractice.arena.impl.KoTHArena;
import me.array.ArrayPractice.arena.impl.SharedArena;
import me.array.ArrayPractice.arena.impl.StandaloneArena;
import me.array.ArrayPractice.kit.Kit;
import me.array.ArrayPractice.util.KothPoint;
import me.array.ArrayPractice.util.external.LocationUtil;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Arena {

    @Getter
    private static final List<Arena> arenas = new ArrayList<>();

    @Getter
    protected String name;
    @Setter
    protected Location spawn1;
    @Setter
    protected Location spawn2;
    @Setter
    protected Location point1;
    @Setter
    protected Location point2;
    @Getter
    protected boolean active;
    @Getter
    @Setter
    private KothPoint point;
    @Getter
    @Setter
    private List<String> kits = new ArrayList<>();

    public Arena(String name) {
        this.name = name;
    }

    public static void init() {
        FileConfiguration configuration = Practice.get().getArenasConfig().getConfiguration();

        if (configuration.contains("arenas")) {
            if (configuration.getConfigurationSection("arenas") == null) return;
            for (String arenaName : configuration.getConfigurationSection("arenas").getKeys(false)) {
                String path = "arenas." + arenaName;

                ArenaType arenaType = ArenaType.valueOf(configuration.getString(path + ".type"));

                Arena arena;

                if (arenaType == ArenaType.STANDALONE) {
                    arena = new StandaloneArena(arenaName);
                } else if (arenaType == ArenaType.SHARED) {
                    arena = new SharedArena(arenaName);
                } else if (arenaType == ArenaType.KOTH) {
                    arena = new KoTHArena(arenaName);
                } else {
                    continue;
                }

                if (configuration.contains(path + ".spawn1")) {
                    arena.setSpawn1(LocationUtil.deserialize(configuration.getString(path + ".spawn1")));
                }

                if (configuration.contains(path + ".spawn2")) {
                    arena.setSpawn2(LocationUtil.deserialize(configuration.getString(path + ".spawn2")));
                }

                if (configuration.contains(path + ".point1") && configuration.contains(path + ".point2")) {
                    arena.setPoint1(LocationUtil.deserialize(configuration.getString(path + ".point1")));
                    arena.setPoint2(LocationUtil.deserialize(configuration.getString(path + ".point2")));
                    arena.setPoint(new KothPoint(arena.point1, arena.point2));
                }


                if (configuration.contains(path + ".kits")) {
                    for (String kitName : configuration.getStringList(path + ".kits")) {
                        arena.getKits().add(kitName);
                    }
                }

                if (arena instanceof StandaloneArena && configuration.contains(path + ".duplicates")) {
                    for (String duplicateId : configuration.getConfigurationSection(path + ".duplicates").getKeys(false)) {
                        Location spawn1 = LocationUtil.deserialize(configuration.getString(path + ".duplicates." + duplicateId + ".spawn1"));
                        Location spawn2 = LocationUtil.deserialize(configuration.getString(path + ".duplicates." + duplicateId + ".spawn2"));

                        Arena duplicate = new Arena(arenaName);

                        duplicate.setSpawn1(spawn1);
                        duplicate.setSpawn2(spawn2);
                        duplicate.setKits(arena.getKits());

                        ((StandaloneArena) arena).getDuplicates().add(duplicate);

                        Arena.getArenas().add(duplicate);
                    }
                }

                Arena.getArenas().add(arena);
            }
        }

        Practice.get().getLogger().info("Loaded " + Arena.getArenas().size() + " arenas");
    }

    public static ArenaType getTypeByName(String name) {
        for (ArenaType arena : ArenaType.values()) {
            if (arena.toString().equalsIgnoreCase(name)) {
                return arena;
            }
        }
        return null;
    }

    public static Arena getByName(String name) {
        for (Arena arena : arenas) {
            if (arena.getType() != ArenaType.DUPLICATE && arena.getName() != null &&
                    arena.getName().equalsIgnoreCase(name)) {
                return arena;
            }
        }

        return null;
    }

    public static Arena getRandom(Kit kit) {
        List<Arena> _arenas = new ArrayList<>();

        for (Arena arena : arenas) {
            if (!arena.isSetup()) continue;

            if (!arena.getKits().contains(kit.getName())) continue;

            if (arena.getType() == ArenaType.KOTH) {
                _arenas.add(arena);
            }

            if (!arena.isActive() && (arena.getType() == ArenaType.STANDALONE || arena.getType() == ArenaType.DUPLICATE)) {
                _arenas.add(arena);
            } else if (!kit.getGameRules().isBuild() && arena.getType() == ArenaType.SHARED) {
                _arenas.add(arena);
            }
        }

        if (_arenas.isEmpty()) {
            return null;
        }

        return _arenas.get(ThreadLocalRandom.current().nextInt(_arenas.size()));
    }

    public ArenaType getType() {
        return ArenaType.DUPLICATE;
    }

    public boolean isSetup() {
        return spawn1 != null && spawn2 != null;
    }

    public int getMaxBuildHeight() {
        int highest = (int) (spawn1.getY() >= spawn2.getY() ? spawn1.getY() : spawn2.getY());
        return highest + 5;
    }

    public Location getSpawn1() {
        if (spawn1 == null) {
            return null;
        }

        return spawn1.clone();
    }

    public Location getSpawn2() {
        if (spawn2 == null) {
            return null;
        }

        return spawn2.clone();
    }

    public Location getPoint1() {
        if (point1 == null) {
            return null;
        }

        return point1.clone();
    }

    public Location getPoint2() {
        if (point2 == null) {
            return null;
        }

        return point2.clone();
    }

    public void setActive(boolean active) {
        if (getType() != ArenaType.SHARED && getType() != ArenaType.KOTH) {
            this.active = active;
        }
    }

    public void save() {

    }

    public void delete() {

    }

}
