package dev.shiro8613.lib.parallelrunnable;


import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public final class ParallelRunnable {
    private final JavaPlugin javaPlugin; //Plugin
    private final HashMap<String, RunnableStruct> StructMap; //登録用Map

    public ParallelRunnable(JavaPlugin plugin) {
        //初期化
        this.javaPlugin = plugin;
        this.StructMap = new HashMap<>();
    }

    public void Register(String name,
                         RegisterInterface registerInterface,
                         Long tick) {
        //BukkitRunnable作成
        BukkitRunnable bukkitRunnable = new BukkitRunnable() {
            @Override
            public void run() {
                registerInterface.Run(javaPlugin);
            }
        };

        //型にはめて作成
        RunnableStruct runnableStruct = new RunnableStruct(bukkitRunnable, tick);

        //Mapに追加
        StructMap.put(name, runnableStruct);

    }

    public void UnRegister(String name) {
        //特定キーを持つRunnableを停止
        this.Stop(name);
        //Mapから削除
        StructMap.remove(name);
    }
    
    public void Start() {
        //ループを回して、すべて実行開始
        StructMap.forEach((Key, RunnableStruct) ->
                RunnableStruct.bukkitRunnable
                        .runTaskTimer(javaPlugin,
                                0L,
                                RunnableStruct.tick));
    }

    public void Stop(String name) {
        //Mapから特定のキーのRunnableを取得してキャンセル
        StructMap.get(name).bukkitRunnable.cancel();
    }

    public void StopAll() {
        //起動してないRunnableを取得して停止
        StructMap.forEach((Key, RunnableStruct) -> {
            if(!RunnableStruct.bukkitRunnable
                    .isCancelled()) RunnableStruct.bukkitRunnable.cancel();
        });
    }

}
