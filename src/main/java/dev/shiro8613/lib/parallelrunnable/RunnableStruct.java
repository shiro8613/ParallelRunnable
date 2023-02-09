package dev.shiro8613.lib.parallelrunnable;

import org.bukkit.scheduler.BukkitRunnable;

public class RunnableStruct {
    BukkitRunnable bukkitRunnable;
    Long tick;

    public RunnableStruct(BukkitRunnable bukkitRunnable, Long tick) {
        this.bukkitRunnable = bukkitRunnable;
        this.tick = tick;
    }
}
