package pl.dejwideek.spectateaddon.configs;

import net.elytrium.java.commons.config.YamlConfig;

@SuppressWarnings("ALL")
public class Config extends YamlConfig {

    @Ignore public static Config IMP = new Config();

    @Comment("Spectate Addon for MBedwars by dejwideek")

    @Create public PERMISSIONS PERMISSIONS;

    @Comment("Permissions")
    public static class PERMISSIONS {
        public String SPECTATE = "bw.spectaddon.spectate";
        public String RELOAD = "bw.spectaddon.reload";
    }

    @Create public MESSAGES MESSAGES;

    @Comment({"Messages", "RGB colors are supported. (1.16+ only)"})
    public static class MESSAGES {
        public String USAGE = "&cUsage: /watch <player>";
        public String YOURSELF = "&cYou cannot spectate yourself!";
        public String NOT_FOUND = "&cThis player isn't online!";
        public String NO_INSIDE_ARENA = "&cThis player isn't playing!";
        public String ALREADY_ENDING = "&cThis player's game is over!";

        @Comment("Available placeholder: %permission%")
        public String NO_PERMISSION = "&cYou must have permission &4%permission% &cto do this!";

        @Comment("Available placeholders: %player%, %arena%")
        public String TELEPORTED = "&eTeleported to &6%player% &7(&c%arena%&7)";

        public String RELOADED = "&eSuccessfully reloaded configuration file!";
    }
}
