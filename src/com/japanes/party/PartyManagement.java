package com.japanes.party;

import com.japanes.utils.prefix;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.connection.Server;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by Gio on 13/12/2016.
 */
public class PartyManagement {


    public static void createparty(String p){
        ;
        ProxiedPlayer m = ProxyServer.getInstance().getPlayer(p);
        if(PartyUtils.players.containsKey(p)){
            m.sendMessage(prefix.prefix+" §cYou have already in a party ! !");
            return;
        }
        if(PartyUtils.lead.containsKey(p)){
            m.sendMessage(prefix.prefix+" §cYou have already a lead of a party !");
            return;
        }

        PartyUtils.i++;
        PartyUtils.lead.put(p, PartyUtils.i);

        m.sendMessage(prefix.prefix+" §fYou have create a party ! Make /p invite <pseudo> to invite a player");

    }


    public static void inviteParty(String p, String p1){
        ProxiedPlayer m = ProxyServer.getInstance().getPlayer(p);
        ProxiedPlayer m1 = ProxyServer.getInstance().getPlayer(p1);
        if(!(PartyUtils.lead.containsKey(p))){
            m.sendMessage(prefix.prefix+ "§cFor the moment, create a party to invite a player ! §e/party create");
        }
        if(PartyUtils.lead.containsKey(p1) || PartyUtils.players.containsKey(p1)){

            m.sendMessage(prefix.prefix+" §cThe player "+p1+" §c has already in party !");
            return;
    }
        if(PartyUtils.players.containsKey(p)){
            m.sendMessage(prefix.prefix+" §cYou are the leader !");
            return;
        }
        PartyUtils.invite.put(p, p1);
        m.sendMessage(prefix.prefix+" §fThe invitation has juste been sent, the player at all times to accept it !");
        m1.sendMessage(prefix.prefix+ " §f§bYou received a invite of "+p+ "§f, for join this party: §e/p §bjoin "+p);
    }

    public static void joinParty(String p, String p1){
        ProxiedPlayer m = ProxyServer.getInstance().getPlayer(p1);
        ProxiedPlayer m1 = ProxyServer.getInstance().getPlayer(p);
     for(HashMap.Entry<String, String> entry: PartyUtils.invite.entrySet()) {
            if (p.equalsIgnoreCase(entry.getKey()) && p1.equalsIgnoreCase(entry.getValue())) {
              PartyUtils.invite.remove(p, p1);
              int i = PartyUtils.lead.get(p);
              PartyUtils.players.put(p1, i);
              m.sendMessage(prefix.prefix + " §fYou have been added in the party of " + p );
              for(ProxiedPlayer players : ProxyServer.getInstance().getPlayers()){
                  for(HashMap.Entry<String, Integer> entry1: PartyUtils.players.entrySet()){
                      if(entry1.getValue() == i){
                          players.sendMessage(prefix.prefix+ " §b"+p1+" §f has join the party !");
                          return;
                      }
                  }
              }
            }
            if(!(p.equalsIgnoreCase(entry.getKey()) && p1.equalsIgnoreCase(entry.getValue()))){
                m.sendMessage(prefix.prefix+ " §cThis player not invited !");
                return;
            }
            for(ProxiedPlayer players : ProxyServer.getInstance().getPlayers()){
                if(!(m1.equals(players))){
                    m.sendMessage(prefix.prefix+ "§cThis player has not connected !");
                    return;
                }
            }
        }
    }

    public static void leave(String p){
        ProxiedPlayer p1 = ProxyServer.getInstance().getPlayer(p);
        if(PartyUtils.players.containsKey(p)){
            PartyUtils.players.remove(p);
            p1.sendMessage(prefix.prefix+ "§cYou have leave this gamme !");
            return;

        }
        if(PartyUtils.lead.containsKey(p)){
            for(HashMap.Entry<String, Integer> entry: PartyUtils.players.entrySet()) {
                for(ProxiedPlayer pl : ProxyServer.getInstance().getPlayers()){
                    int i = PartyUtils.lead.get(p);
                    if(PartyUtils.players.containsKey(pl.getDisplayName()) && entry.getValue().equals(i)){
                        PartyUtils.players.remove(pl.getDisplayName());

                        pl.sendMessage(prefix.prefix+ " §cLThe leader of this party has deleted this party ! For create a party: /p create");

                    }
                }

            }
            PartyUtils.lead.remove(p);
            p1.sendMessage(prefix.prefix+ "§cYour group has been deleted !");
        }
    }

    public static void tp(String p){
        ProxiedPlayer m = ProxyServer.getInstance().getPlayer(p);
        if(PartyUtils.lead.containsKey(p)){
            m.sendMessage(prefix.prefix+ "§cYour the leader of this party and you don't teleport !");
            return;
        }
        if(PartyUtils.players.containsKey(p)){
            int i = PartyUtils.players.get(p);
            for(HashMap.Entry<String, Integer> entry: PartyUtils.lead.entrySet()) {
                if(entry.getValue() == i){
                    String o = entry.getKey();
                    ProxiedPlayer om = ProxyServer.getInstance().getPlayer(o);
                    Server srvo = om.getServer();
                    Server srv = m.getServer();
                    if(srv==srvo){
                        m.sendMessage(prefix.prefix+ " §cError, your already connect of this server ! (§f"+srv+"§c)");
                        return;
                    }
                    m.connect(srvo.getInfo());
                    m.sendMessage(prefix.prefix+ "§fYou join the game of you lead !");
                }

            }
        }
    }

    public static void lead(String p, String p1){
        ProxiedPlayer m = ProxyServer.getInstance().getPlayer(p);
        ProxiedPlayer m1 = ProxyServer.getInstance().getPlayer(p1);
        if(PartyUtils.players.containsKey(p)){
            m.sendMessage(prefix.prefix+ " §cOnly leader can");
            return;
        }
        int i = PartyUtils.lead.get(p);
        for(HashMap.Entry<String, Integer> entry : PartyUtils.players.entrySet()){
            if(entry.getKey().equalsIgnoreCase(p1)&& entry.getValue()==i){
                PartyUtils.players.remove(p1);
                PartyUtils.lead.remove(p);
                PartyUtils.players.put(p, i);
                PartyUtils.lead.put(p1, i);
                m.sendMessage(prefix.prefix + " §fNew leader, this is: "+p1+ " !");
                m1.sendMessage(prefix.prefix + " §fYou are leader !");
            }
        }
    }


    public static void list(String p){
        List<String> listP = new ArrayList<String>();
        ProxiedPlayer m = ProxyServer.getInstance().getPlayer(p);
        if(PartyUtils.lead.containsKey(p)){
            listP.add(p+" §7(chef)§f");
            int i = PartyUtils.lead.get(p);
            for (HashMap.Entry<String, Integer> entry : PartyUtils.players.entrySet()){
                if(entry.getValue()==i){
                    Collections.replaceAll(listP, "[", " ");
                    Collections.replaceAll(listP, "]", " ");
                    listP.add(entry.getKey());

                    m.sendMessage(prefix.prefix + "§f"+ listP );
                }
            }
            m.sendMessage(prefix.prefix + "§f"+ listP );
        }
        if(PartyUtils.players.containsKey(p)){
            int i = PartyUtils.players.get(p);
            for (HashMap.Entry<String, Integer> entry : PartyUtils.lead.entrySet()){
                if(entry.getValue()==i){
                    listP.add(entry.getKey() + " §7(leader)§f");
                }
            }
            for (HashMap.Entry<String, Integer> entry : PartyUtils.players.entrySet()){
                if(entry.getValue()==i){
                    listP.add(entry.getKey());
                    m.sendMessage(prefix.prefix+ "§f"+ listP);
                }
            }

        }
    }


}
