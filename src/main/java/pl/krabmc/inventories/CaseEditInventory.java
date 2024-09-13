package pl.krabmc.inventories;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import pl.krabmc.objects.CaseObject;
import pl.krabmc.utils.ChatUtil;

public class CaseEditInventory {

    public static void open(Player player, CaseObject caseObject) {
        Inventory inventory = Bukkit.createInventory(null, 54, ChatUtil.coloredHex("&8Edytujesz skrzynie: &#F0AE28" + caseObject.getName()));

        for (ItemStack itemStack : caseObject.getItems()) {
            if (itemStack != null) {
                inventory.addItem(itemStack);
            }
        }

        player.openInventory(inventory);
    }
}

