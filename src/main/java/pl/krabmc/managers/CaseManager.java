package pl.krabmc.managers;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import pl.krabmc.Main;
import pl.krabmc.objects.CaseObject;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CaseManager {

    public static List<CaseObject> caseObjectList = new ArrayList<>();

    public static void load() {
        FileConfiguration configuration = Main.getInstance().getConfig();

        for (String key : configuration.getConfigurationSection("cases").getKeys(false)) {
            String key2 = configuration.getString("cases." + key + ".key");
            Location location = configuration.getLocation("cases." + key + ".location");
            List<?> itemList = configuration.getList("cases." + key + ".items");

            List<ItemStack> items = new ArrayList<>();
            if (itemList != null) {
                items = itemList.stream()
                        .filter(item -> item instanceof ItemStack)
                        .map(item -> (ItemStack) item)
                        .collect(Collectors.toList());
            }

            CaseObject caseObject = new CaseObject(key, key2, location, items);
            caseObjectList.add(caseObject);
        }
    }

    public static void reload() {
        caseObjectList.clear();
        load();
    }
}
