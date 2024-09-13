package pl.krabmc.objects;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class CaseObject {
    private String name;
    private String key;
    private Location location;
    private List<ItemStack> items;

    public CaseObject(String name, String key, Location location, List<ItemStack> items) {
        this.name = name;
        this.key = key;
        this.location = location;
        this.items = items;
    }

    public String getName() {
        return name;
    }

    public String getKey() {
        return key;
    }

    public Location getLocation() {
        return location;
    }

    public List<ItemStack> getItems() {
        return items;
    }
}
