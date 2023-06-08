package pl.dejwideek.spectateaddon;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import pl.dejwideek.spectateaddon.configs.Config;

import java.io.File;
import java.nio.file.Path;

@SuppressWarnings("ALL")
public class SpectatePlugin extends JavaPlugin {

    private static File configFile;
    private static Path directory;
    public static Config config = Config.IMP;

    public void onEnable() {
        if(!mbwCheck()) return;
        if(!registerAddon()) return;

        this.directory = new SpectateAddon(this)
                .getDataFolder().toPath();
        this.configFile = directory.resolve("config.yml").toFile();

        reloadConfig();
        new SpectateAddon(this).registerCommands();
    }

    public void reloadConfig() {
        config.reload(configFile);
    }

    private boolean mbwCheck() {
        if(Bukkit.getPluginManager().getPlugin("MBedwars") != null) {
            final int supportedAPIVersion = 16;

            try {
                Class apiClass = Class.forName("de.marcely.bedwars.api.BedwarsAPI");
                int apiVersion = (int) apiClass.getMethod("getAPIVersion").invoke(null);

                if (apiVersion < supportedAPIVersion)
                    throw new IllegalStateException();
            } catch (Exception e) {
                this.getLogger().warning("Your MBedwars version is not supported. Supported version: 5.1.1 or higher!");
                Bukkit.getPluginManager().disablePlugin(this);
                return false;
            }
        }
        else return false;
        return true;
    }

    private boolean registerAddon() {
        SpectateAddon addon = new SpectateAddon(this);

        if(!addon.register()) {
            this.getLogger().warning("It seems like this addon has already been loaded. Please delete duplicates and try again.");
            Bukkit.getPluginManager().disablePlugin(this);
            return false;
        }
        return true;
    }
}
