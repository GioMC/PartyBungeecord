package com.japanes.party;

import com.japanes.utils.prefix;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

/**
 * Created by Gio on 13/12/2016.
 */
public class commands extends Command{

    public commands(){
        super("party");

    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if(!(commandSender instanceof ProxiedPlayer)){
            commandSender.sendMessage("§cCould not execute this command with console.");
            return;
        }

        ProxiedPlayer p = (ProxiedPlayer)commandSender;

        if(strings.length==0){
            p.sendMessage(" §6--------------------------------");
            p.sendMessage("§e/party §bcreate §f- Create a party !");
            p.sendMessage("§e/party §binvite <pseudo> §f- Invite a player into the group");
            p.sendMessage("§e/party §baccept <pseudo> §f- Accepting a player's invitation ");
            p.sendMessage("§e/party §bleave §f-Leave you group (if you are leader delete the group)");
            p.sendMessage("§e/party §btp §f- Teleport to your leader");
            p.sendMessage( "§e/party §bleader <pseudo> §f- (Only if you are the leader) Give the leader to a player in your group.");
            p.sendMessage(" §6--------------------------------");
            return;
        }
        if (strings[0].equalsIgnoreCase("create")){
            PartyManagement.createparty(p.getName());
            return;
        }
        if(strings[0].equalsIgnoreCase("invite")){
            if(strings[1]==null){
                // §cPlease, select a name at player /p invite <name of player>
                p.sendMessage(prefix.prefix+" §fSelect the name of the player to be invited /p invite §c<pseudo>");
                return;
            }
            ProxiedPlayer p1 = ProxyServer.getInstance().getPlayer(strings[1]);
            PartyManagement.inviteParty(p.getName(), p1.getDisplayName());
            return;
        }
        if(strings[0].equalsIgnoreCase("accept")){
           if (strings[1]==null){
               p.sendMessage(prefix.prefix+ "§fSelect the name of player who invited you /p join §c<pseudo>");
               return;
           }

            ProxiedPlayer p1 = ProxyServer.getInstance().getPlayer(strings[1]);
            PartyManagement.joinParty(p1.getName(), p.getDisplayName());
            return;
        }
        if(strings[0].equalsIgnoreCase("leave")){
            PartyManagement.leave(p.getName());
        }
        if(strings[0].equalsIgnoreCase("tp")){
            PartyManagement.tp(p.getName());
        }
        if(strings[0].equalsIgnoreCase("leader")){

            if(strings[1]==null){
                p.sendMessage(prefix.prefix+ " §cSpecifies the pseudo of the player /p lead <pseudo>");
                return;
            }
            ProxiedPlayer p1 = ProxyServer.getInstance().getPlayer(strings[1]);
            PartyManagement.lead(p.getName() , p1.getDisplayName());
        }
        if(strings[0].equalsIgnoreCase("list")){
            PartyManagement.list(p.getName());
        }
    }
}
