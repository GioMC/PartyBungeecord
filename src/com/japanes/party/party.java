package com.japanes.party;

import net.md_5.bungee.api.plugin.Plugin;
import org.bukkit.Bukkit;


/**
 * Created by Gio on 13/12/2016.
 */


public class party extends Plugin{



    public void onEnable(){

        getProxy().getPluginManager().registerCommand(this, new commands());
        getLogger().info("§eParty has been launched !");
    }


}
