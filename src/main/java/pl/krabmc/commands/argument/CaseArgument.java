package pl.krabmc.commands.argument;

import dev.rollczi.litecommands.argument.Argument;
import dev.rollczi.litecommands.argument.parser.ParseResult;
import dev.rollczi.litecommands.argument.resolver.ArgumentResolver;
import dev.rollczi.litecommands.invocation.Invocation;
import dev.rollczi.litecommands.suggestion.SuggestionContext;
import dev.rollczi.litecommands.suggestion.SuggestionResult;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import pl.krabmc.Main;
import pl.krabmc.utils.ChatUtil;

import java.util.ArrayList;
import java.util.List;

public class CaseArgument extends ArgumentResolver<CommandSender, String> {

    @Override
    protected ParseResult<String> parse(Invocation<CommandSender> invocation, Argument<String> argument, String s) {
        ConfigurationSection itemsSection = Main.getInstance().getConfig().getConfigurationSection("cases");

        if (itemsSection == null || !itemsSection.contains(s)) {
            if (invocation.sender() instanceof Player) {
                Player player = (Player) invocation.sender();

                player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 1.0f, 1.0f);
            }
            return ParseResult.failure(ChatUtil.coloredHex("&4&l❌ &8× &cSkrzynka &4" + s + " &cnie została znaleziona!"));
        }

        return ParseResult.success(s);
    }

    @Override
    public SuggestionResult suggest(Invocation<CommandSender> invocation, Argument<String> argument, SuggestionContext context) {
        ConfigurationSection itemsSection = Main.getInstance().getConfig().getConfigurationSection("cases");

        if (itemsSection == null) {
            return SuggestionResult.of("Brak dostępnych przedmiotów!");
        }


        List<String> suggestions = new ArrayList<>(itemsSection.getKeys(false));

        return SuggestionResult.of(suggestions);
    }
}
