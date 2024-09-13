package pl.krabmc.utils;

import com.destroystokyo.paper.Title;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatUtil {

    private static final Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");

    public static String colored(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static List<String> colored(List<String> text) {
        return text.stream().map(ChatUtil::colored).toList();
    }

    public static String coloredHex(String message) {
        Matcher matcher = pattern.matcher(message);
        StringBuffer sb = new StringBuffer();

        while (matcher.find()) {
            String hexCode = matcher.group();
            StringBuilder builder = new StringBuilder();
            builder.append("x");

            for (int i = 1; i < hexCode.length(); i++) {
                char c = hexCode.charAt(i);
                builder.append("&").append(c);
            }

            matcher.appendReplacement(sb, builder.toString());
        }

        matcher.appendTail(sb);
        message = sb.toString();

        return ChatColor.translateAlternateColorCodes('&', message);
    }
    public static List<String> coloredHex(List<String> text) {
        return text.stream().map(ChatUtil::coloredHex).toList();
    }

    public static void sendTitle(Player player, String m1, String m2) {
        player.sendTitle(Title.builder().title(coloredHex(m1)).subtitle(coloredHex(m2)).stay(3).build());
    }
}
