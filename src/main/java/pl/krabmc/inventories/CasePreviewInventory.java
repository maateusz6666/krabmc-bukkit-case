package pl.krabmc.inventories;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.krabmc.objects.CaseObject;
import pl.krabmc.utils.ChatUtil;
import pl.krabmc.utils.PercentageUtil;

import java.util.ArrayList;
import java.util.List;

public class CasePreviewInventory {

    public static int[] orangeGlassSlots = {0,8,36,44};
    public static int[] yellowGlassSlots = {1,7,9,17,27,35,37,43};
    public static int[] whiteGlassSlots = {2,3,5,6,38,39,41,42};
    public static int[] freeSlots = {10,11,12,13,14,15,16,19,20,21,22,23,24,25,28,29,30,31,32,33,34,37,38,39,40,41,42,43};

    public static void open(Player player, CaseObject caseObject) {
        Inventory inventory = Bukkit.createInventory(null, 45, ChatUtil.coloredHex("&8Podgląd skrzynki: &#F0AE28" + caseObject.getName()));

        for (ItemStack itemStack : caseObject.getItems()) {
            double percentage = PercentageUtil.calculatePercentage(caseObject.getItems().size());
            if (itemStack != null) {
                ItemMeta meta = itemStack.getItemMeta();

                if (meta != null) {
                    List<String> lore = meta.hasLore() ? meta.getLore() : new ArrayList<>();

                    boolean loreAlreadyAdded = false;
                    for (String line : lore) {
                        if (line.contains("Szansa:")) {
                            loreAlreadyAdded = true;
                            break;
                        }
                    }

                    if (!loreAlreadyAdded) {
                        lore.add(" ");
                        lore.add(ChatUtil.coloredHex(" &8× &7Szansa: &#F0AE28" + percentage + "%"));
                        meta.setLore(lore);
                        itemStack.setItemMeta(meta);
                    }
                }
                for (int slot : freeSlots) {
                    if (inventory.getItem(slot) == null) {
                        inventory.setItem(slot, itemStack);
                        break;
                    }
                }
            }
        }

        for (Integer slot : orangeGlassSlots) {
            ItemStack itemStack = new ItemStack(Material.ORANGE_STAINED_GLASS_PANE);
            ItemMeta itemMeta = itemStack.getItemMeta();

            itemMeta.setDisplayName(" ");
            itemStack.setItemMeta(itemMeta);

            inventory.setItem(slot, itemStack);
        }

        for (Integer slot : yellowGlassSlots) {
            ItemStack itemStack = new ItemStack(Material.YELLOW_STAINED_GLASS_PANE);
            ItemMeta itemMeta = itemStack.getItemMeta();

            itemMeta.setDisplayName(" ");
            itemStack.setItemMeta(itemMeta);

            inventory.setItem(slot, itemStack);
        }

        for (Integer slot : whiteGlassSlots) {
            ItemStack itemStack = new ItemStack(Material.WHITE_STAINED_GLASS_PANE);
            ItemMeta itemMeta = itemStack.getItemMeta();

            itemMeta.setDisplayName(" ");
            itemStack.setItemMeta(itemMeta);

            inventory.setItem(slot, itemStack);
        }

        player.openInventory(inventory);
    }
}
