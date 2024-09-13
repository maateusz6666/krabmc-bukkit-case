package pl.krabmc.listeners;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.krabmc.Main;
import pl.krabmc.inventories.CasePreviewInventory;
import pl.krabmc.managers.CaseManager;
import pl.krabmc.objects.CaseObject;
import pl.krabmc.utils.ChatUtil;
import pl.krabmc.utils.PercentageUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class InteractionListener implements Listener {

    private static final int MAX_CHESTS = 5;
    private static final long COOLDOWN_PERIOD_MS = 10000;

    private final Map<Player, Long> playerLastOpenTimes = new ConcurrentHashMap<>();
    private final Map<String, Integer> serverOpenCount = new ConcurrentHashMap<>();
    private long lastServerOpenTime = 0;

    @EventHandler
    public void onInteractionListener(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Block clickedBlock = event.getClickedBlock();

        if (clickedBlock == null) {
            return;
        }

        for (CaseObject caseObject : CaseManager.caseObjectList) {
            if (caseObject.getLocation().equals(clickedBlock.getLocation())) {
                event.setCancelled(true);

                long currentTime = System.currentTimeMillis();

                if (currentTime - lastServerOpenTime > COOLDOWN_PERIOD_MS) {
                    serverOpenCount.clear();
                    lastServerOpenTime = currentTime;
                }

                int currentOpenCount = serverOpenCount.getOrDefault("server_open_count", 0);

                if (event.getAction().isLeftClick()) {
                    CasePreviewInventory.open(player, caseObject);
                }

                if (event.getAction().isRightClick()) {
                    ItemStack itemStack = player.getInventory().getItemInMainHand();
                    if (itemStack.getItemMeta() == null || !itemStack.getItemMeta().getDisplayName().equals(caseObject.getKey())) {
                        player.setVelocity(player.getLocation().getDirection().multiply(-1).multiply(1.5));
                        player.sendMessage(ChatUtil.coloredHex("&4&l❌ &8× &cNie posiadasz klucza do tej skrzynki. Kup klucz używając &4/itemshop&c lub doładuj portfel na &4https://krabmc.pl&c."));
                        return;
                    }

                    if (currentOpenCount >= MAX_CHESTS) {
                        player.sendMessage(ChatUtil.coloredHex("&4&l❌ &8× &cOsiągnięto limit otwierania skrzynek na serwerze. Poczekaj chwilę przed ponowną próbą."));
                        return;
                    }

                    List<ItemStack> items = caseObject.getItems();
                    ItemStack randomItemStack = items.get(new Random().nextInt(items.size()));

                    ItemMeta meta = randomItemStack.getItemMeta();
                    if (meta != null && meta.hasLore()) {
                        List<String> lore = meta.getLore();
                        if (lore != null && lore.size() > 2) {
                            lore = lore.subList(0, lore.size() - 2);
                            meta.setLore(lore);
                            randomItemStack.setItemMeta(meta);
                        }
                    }

                    if (!hasSpaceForItem(player.getInventory(), randomItemStack)) {
                        player.sendMessage(ChatUtil.coloredHex("&4&l❌ &8× &cTwój ekwipunek jest pełny. Opróżnij go za pomocą &4/kosz&c."));
                        return;
                    }

                    itemStack.setAmount(itemStack.getAmount() - 1);
                    player.getInventory().addItem(randomItemStack);

                    serverOpenCount.put("server_open_count", currentOpenCount + 1);
                    playerLastOpenTimes.put(player, currentTime);

                    Main.getInstance().getServer().broadcastMessage(ChatUtil.coloredHex("&#6EADE5&ls&#77A5E1&lᴋ&#809DDC&lʀ&#8995D8&lᴢ&#928ED3&lʏ&#9B86CF&lɴ&#A47ECA&lɪ&#AD76C6&lᴇ &8× &7Gracz &#F0AE28" + player.getName() + " &7wylosował przedmiot " + randomItemStack.getItemMeta().getDisplayName() + " &7z skrzynki &#F0AE28" + caseObject.getName() + "&7."));
                }
            }
        }
    }

    private boolean hasSpaceForItem(Inventory inventory, ItemStack item) {
        int amount = item.getAmount();
        for (ItemStack stack : inventory.getStorageContents()) {
            if (stack == null || stack.getType() == item.getType() && stack.getAmount() < stack.getMaxStackSize()) {
                int space = stack == null ? item.getMaxStackSize() : stack.getMaxStackSize() - stack.getAmount();
                if (amount <= space) {
                    return true;
                }
                amount -= space;
            }
        }
        return amount <= 0;
    }
}
