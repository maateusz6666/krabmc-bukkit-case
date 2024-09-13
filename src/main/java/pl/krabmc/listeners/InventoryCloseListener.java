package pl.krabmc.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import pl.krabmc.Main;
import pl.krabmc.managers.CaseManager;
import pl.krabmc.objects.CaseObject;
import pl.krabmc.utils.ChatUtil;

import java.util.ArrayList;
import java.util.List;

public class InventoryCloseListener implements Listener {

    @EventHandler
    public void onInventoryCloseListener(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();

        for (CaseObject caseObject : CaseManager.caseObjectList) {
            if (event.getView().getTitle().equals(ChatUtil.coloredHex("&8Edytujesz skrzynie: &#F0AE28" + caseObject.getName()))) {
                ItemStack[] itemStacks = event.getInventory().getContents();
                List<ItemStack> itemStackList = new ArrayList<>();

                for (ItemStack itemStack : itemStacks) {
                    if (itemStack != null)
                        itemStackList.add(itemStack);
                }

                Main.getInstance().getConfig().set("cases." + caseObject.getName() + ".items", itemStackList);
                Main.getInstance().saveConfig();

                ChatUtil.sendTitle(player, "&#6EADE5&ls&#77A5E1&lᴋ&#809DDC&lʀ&#8995D8&lᴢ&#928ED3&lʏ&#9B86CF&lɴ&#A47ECA&lɪ&#AD76C6&lᴇ", "&aZapisałeś zawartość skrzynki");
            }
        }
    }
}
