package pl.krabmc.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import pl.krabmc.utils.ChatUtil;

public class InventoryListener implements Listener {

    @EventHandler
    public void onInventoryListener(InventoryClickEvent event) {
        if (event.getView().getTitle().startsWith(ChatUtil.coloredHex("&8Podgląd skrzynki:"))) {
            event.setCancelled(true);
        }
    }
}
