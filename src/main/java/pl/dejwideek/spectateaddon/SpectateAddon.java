package pl.dejwideek.spectateaddon;

import co.aikar.commands.PaperCommandManager;
import de.marcely.bedwars.api.BedwarsAddon;
import pl.dejwideek.spectateaddon.commands.ReloadCmd;
import pl.dejwideek.spectateaddon.commands.SpectateCmd;

@SuppressWarnings("ALL")
public class SpectateAddon extends BedwarsAddon {
    private static SpectatePlugin plugin;

    public SpectateAddon(SpectatePlugin plugin) {
        super(plugin);
        this.plugin = plugin;
    }

    @Override
    public String getName() {
        return plugin.getName();
    }

    public void registerCommands() {
        PaperCommandManager manager =
                new PaperCommandManager(plugin);

        manager.registerCommand(new SpectateCmd(plugin));
        manager.registerCommand(new ReloadCmd(plugin));
    }
}
