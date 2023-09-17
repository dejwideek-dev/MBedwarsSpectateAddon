package pl.dejwideek.spectateaddon.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.Description;
import de.marcely.bedwars.api.BedwarsAPI;
import de.marcely.bedwars.api.arena.ArenaStatus;
import de.marcely.bedwars.api.remote.RemoteAPI;
import de.marcely.bedwars.api.remote.RemoteArena;
import de.marcely.bedwars.api.remote.RemotePlayer;
import org.bukkit.entity.Player;
import pl.dejwideek.spectateaddon.color.ColorAPI;
import pl.dejwideek.spectateaddon.SpectatePlugin;
import pl.dejwideek.spectateaddon.configs.Config;

@SuppressWarnings("ALL")
public class SpectateCmd extends BaseCommand {

    private static SpectatePlugin plugin;

    public SpectateCmd(SpectatePlugin plugin) {
        this.plugin = plugin;
    }

    @CommandAlias("watch|spectate|spect|spec")
    @CommandCompletion("@players")
    @Description("Watch player")
    public void spectate(Player player, String[] strings) {
        BedwarsAPI.onReady(() -> {
            Config config = plugin.config;
            ColorAPI colorApi = new ColorAPI();

            String usageMsg = config.MESSAGES.USAGE;
            String yourselfMsg = config.MESSAGES.YOURSELF;
            String teleportedMsg = config.MESSAGES.TELEPORTED;
            String endingMsg = config.MESSAGES.ALREADY_ENDING;
            String noInsideMsg = config.MESSAGES.NO_INSIDE_ARENA;
            String notFoundMsg = config.MESSAGES.NOT_FOUND;
            String noPermsMsg = config.MESSAGES.NO_PERMISSION;
            String permission = config.PERMISSIONS.SPECTATE;

            if(player.hasPermission(permission)) {
                if(strings.length == 0) {
                    player.sendMessage(colorApi.process(usageMsg));
                }
                if(strings.length >= 1) {
                    RemotePlayer target = RemoteAPI.get().getOnlinePlayer(strings[0]);
                    RemotePlayer p = RemoteAPI.get().getOnlinePlayer(player);

                    if(target != null) {
                        if(target.asBukkit() == player) {
                            player.sendMessage(colorApi.process(yourselfMsg));
                            return;
                        }
                        RemoteArena a = RemoteAPI.get().getArenaByPlayingPlayer(target);
                        RemoteArena as = RemoteAPI.get().getArenaBySpectator(target);

                        if(a != null) {
                            ArenaStatus status = a.getStatus();

                            if (status.equals(ArenaStatus.LOBBY)) {
                                player.sendMessage(colorApi.process(teleportedMsg
                                        .replaceAll("%player%", target.getName())
                                        .replaceAll("%arena%", a.getName())));
                                a.addPlayer(p);
                            }
                            if (status.equals(ArenaStatus.RUNNING)) {
                                player.sendMessage(colorApi.process(teleportedMsg
                                        .replaceAll("%player%", target.getName())
                                        .replaceAll("%arena%", a.getName())));
                                a.addSpectator(p);
                            }
                            if (status.equals(ArenaStatus.END_LOBBY)) {
                                player.sendMessage(colorApi.process(endingMsg));
                            }
                            return;
                        }
                        if(a == null) {
                            if(as != null) {
                                ArenaStatus status = as.getStatus();

                                if (status.equals(ArenaStatus.RUNNING)) {
                                    player.sendMessage(colorApi.process(teleportedMsg
                                            .replaceAll("%player%", target == null ? "":target.getName())
                                            .replaceAll("%arena%", a == null? "": a.getName())));
                                    a.addSpectator(p);
                                }
                                if (status.equals(ArenaStatus.END_LOBBY)) {
                                    player.sendMessage(colorApi.process(endingMsg));
                                }
                                return;
                            }
                            if(as == null)
                                player.sendMessage(colorApi.process(noInsideMsg));
                        }
                    }
                    if(target == null) {
                        player.sendMessage(colorApi.process(notFoundMsg));
                    }
                    return;
                }
                if(!player.hasPermission(permission)) {
                    player.sendMessage(colorApi.process(noPermsMsg
                            .replaceAll("%permission%", permission)));
                    return;
                }
            }
        });
        return;
    }
}
