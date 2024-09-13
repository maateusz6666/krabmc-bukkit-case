package pl.krabmc.commands.command.admin;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.krabmc.Main;
import pl.krabmc.inventories.CaseEditInventory;
import pl.krabmc.managers.CaseManager;
import pl.krabmc.objects.CaseObject;
import pl.krabmc.utils.ChatUtil;

@Command(name = "case")
@Permission({"krabmc.bukkit.case"})
public class CaseCommand {

    @Execute(name = "create")
    public void create(@Context Player player, @Arg("name") String name) {
        Main.getInstance().getConfig().set("cases." + name + ".key", name);
        Main.getInstance().saveConfig();

        player.sendMessage(ChatUtil.coloredHex("&#F0AE28☀ &8× &7Utworzyłeś skrzynie o nazwie &#F0AE28" + name));
    }

    @Execute(name = "edit")
    public void edit(@Context Player player, @Arg("nazwa") String name) {
        for (CaseObject caseObject : CaseManager.caseObjectList) {
            if (caseObject.getName() != null && caseObject.getName().equals(name)) {
                CaseEditInventory.open(player, caseObject);
                return;
            }
        }
    }

    @Execute(name = "give")
    public void give(@Context CommandSender player, @Arg("gracz") Player targetPlayer, @Arg("nazwa") String name, @Arg("amount") int amount) {
        for (CaseObject caseObject : CaseManager.caseObjectList) {
            if (caseObject.getName() != null && caseObject.getName().equals(name)) {
                ItemStack itemStack = new ItemStack(Material.TRIPWIRE_HOOK, amount);
                ItemMeta itemMeta = itemStack.getItemMeta();
                itemMeta.setDisplayName(ChatUtil.coloredHex(caseObject.getKey()));
                itemMeta.addEnchant(Enchantment.DURABILITY, 1, true);
                itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                itemStack.setItemMeta(itemMeta);
                targetPlayer.getInventory().addItem(itemStack);
                player.sendMessage(ChatUtil.coloredHex("&#F0AE28☀ &8× &7Nadałeś &#F0AE28x" + amount + " &7kluczy do skrzynki &#F0AE28" + name + " &7dla gracza &#F0AE28" + targetPlayer.getName() + "&7."));
                return;
            }
        }
    }

    @Execute(name = "giveAll")
    public void giveAll(@Context CommandSender player, @Arg("nazwa") String name, @Arg("amount") int amount) {
        for (CaseObject caseObject : CaseManager.caseObjectList) {
            if (caseObject.getName() != null && caseObject.getName().equals(name)) {
                Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), () -> {
                    for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                        ItemStack itemStack = new ItemStack(Material.TRIPWIRE_HOOK, amount);
                        ItemMeta itemMeta = itemStack.getItemMeta();
                        itemMeta.setDisplayName(ChatUtil.coloredHex(caseObject.getKey()));
                        itemMeta.addEnchant(Enchantment.DURABILITY, 1, true);
                        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                        itemStack.setItemMeta(itemMeta);
                        onlinePlayer.getInventory().addItem(itemStack);
                        ChatUtil.sendTitle(onlinePlayer, "&#6EADE5&ls&#77A5E1&lᴋ&#809DDC&lʀ&#8995D8&lᴢ&#928ED3&lʏ&#9B86CF&lɴ&#A47ECA&lɪ&#AD76C6&lᴇ", "&7Każdy otrzymał &#F0AE28x" + amount + " &7klucz do &#F0AE28" + caseObject.getName() + "&7.");
                    }
                });
                player.sendMessage(ChatUtil.coloredHex("&#F0AE28☀ &8× &7Nadałeś &#F0AE28x" + amount + " &7kluczy do skrzynki &#F0AE28" + name + " &7dla wszystkich graczy&7."));
                return;
            }
        }
    }

    @Execute(name = "reload")
    public void reload(@Context Player player) {
        Main.getInstance().reloadConfig();
        CaseManager.reload();

        player.sendMessage(ChatUtil.coloredHex("&#F0AE28☀ &8× &7Pomyślnie przeładowałeś konfiguracje."));
    }

    @Execute(name = "setKey")
    public void setKey(@Context Player player, @Arg("nazwa") String nazwa) {
        Main.getInstance().getConfig().set("cases." + nazwa + ".key", player.getInventory().getItemInMainHand().getItemMeta().getDisplayName());
        Main.getInstance().saveConfig();

        player.sendMessage(ChatUtil.coloredHex("&#F0AE28☀ &8× &7Ustwaiłeś nowy klucz dla skrzyni o nazwie &#F0AE28" + nazwa));
    }

    @Execute(name = "setLocation")
    public void setLocation(@Context Player player, @Arg("nazwa") String nazwa) {
        Block block = player.getTargetBlock(null, 10);

        if (block != null && block.getType() != Material.AIR) {
            Main.getInstance().getConfig().set("cases." + nazwa + ".location", block.getLocation());
            Main.getInstance().saveConfig();
        }

        player.sendMessage(ChatUtil.coloredHex("&#F0AE28☀ &8× &7Ustwaiłeś nową lokalizację dla skrzyni o nazwie &#F0AE28" + nazwa));
    }
}
