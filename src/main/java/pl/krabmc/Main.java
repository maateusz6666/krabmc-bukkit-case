package pl.krabmc;

import dev.rollczi.litecommands.LiteCommands;
import dev.rollczi.litecommands.argument.ArgumentKey;
import dev.rollczi.litecommands.bukkit.LiteBukkitFactory;
import dev.rollczi.litecommands.bukkit.LiteBukkitMessages;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import pl.krabmc.commands.argument.CaseArgument;
import pl.krabmc.commands.command.admin.CaseCommand;
import pl.krabmc.commands.handler.InvalidUsageHandler;
import pl.krabmc.commands.handler.MissingPermissionHandler;
import pl.krabmc.listeners.InteractionListener;
import pl.krabmc.listeners.InventoryCloseListener;
import pl.krabmc.listeners.InventoryListener;
import pl.krabmc.managers.CaseManager;

public final class Main extends JavaPlugin {

    private static Main instance;
    private LiteCommands<CommandSender> liteCommands;

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();
        CaseManager.load();

        Bukkit.getScheduler().runTaskTimerAsynchronously(Main.getInstance(), () -> {
            reloadConfig();
            CaseManager.reload();
        }, 0, 20L);

        getServer().getPluginManager().registerEvents(new InteractionListener(), this);
        getServer().getPluginManager().registerEvents(new InventoryCloseListener(), this);
        getServer().getPluginManager().registerEvents(new InventoryListener(), this);

        liteCommands = LiteBukkitFactory.builder()
                .settings(setings -> setings.fallbackPrefix("krabmc").nativePermissions(false))
                .commands(new CaseCommand())
                .message(LiteBukkitMessages.PLAYER_NOT_FOUND, input -> "&4&l❌ &8× &cGracz &4" + input + " &cnie został odnaleziony!")
                .argument(String.class, ArgumentKey.of("nazwa"), new CaseArgument())
                .invalidUsage(new InvalidUsageHandler())
                .missingPermission(new MissingPermissionHandler())
                .build();
    }

    @Override
    public void onDisable() {
        if (liteCommands != null) {
            liteCommands.unregister();
        }
    }

    public static Main getInstance() {
        return instance;
    }
}
