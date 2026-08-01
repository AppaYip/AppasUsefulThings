package org.appa.appasUsefulThings.betterPowerTools;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;

import java.util.Optional;

// This entire thing is bad and should probably be rewritten.
@SuppressWarnings("UnstableApiUsage")
public class BetterPowerToolsCommand {
    private final BetterPowerTools betterPowerTools;

    public BetterPowerToolsCommand(BetterPowerTools betterPowerTools) {
        this.betterPowerTools = betterPowerTools;
    }

    private @Nullable Player requirePlayer(CommandSourceStack source) {
        if (!(source.getExecutor() instanceof Player player)) {
            source.getSender().sendMessage(Component.text("Only players can execute this command!"));
            return null;
        }

        return player;
    }

    @SuppressWarnings("SameReturnValue")
    private int handleAction(Player player, Player target, String action, String id) {
        switch (action.toLowerCase()) {
            case "set" -> {
                ItemStack item = player.getInventory().getItemInMainHand();

                if (item.getType().isAir()) {
                    player.sendMessage(Component.text("Hold an item first!",  NamedTextColor.RED));
                    return Command.SINGLE_SUCCESS;
                }

                betterPowerTools.bind(item, id);
                player.sendMessage(Component.text("Set power tool id of item to '%s'".formatted(id), NamedTextColor.GREEN));
                return Command.SINGLE_SUCCESS;
            }

            case "clear" -> {
                ItemStack item = player.getInventory().getItemInMainHand();
                betterPowerTools.clear(item);
                player.sendMessage(Component.text("Cleared power tool of held item!", NamedTextColor.GREEN));
                return Command.SINGLE_SUCCESS;
            }

            case "toggle" -> {
                boolean toggle = betterPowerTools.toggle(player);

                player.sendMessage(Component.text("Toggle power tools ", NamedTextColor.YELLOW)
                        .append(toggle
                                ? Component.text("On", NamedTextColor.GREEN)
                                : Component.text("Off", NamedTextColor.RED)
                        )
                );

                return Command.SINGLE_SUCCESS;
            }

            case "get" -> {
                Optional<String> itemId = betterPowerTools.getId(player.getInventory().getItemInMainHand());

                if (itemId.isEmpty()) {
                    player.sendMessage(Component.text("No Power Tool ID found.", NamedTextColor.YELLOW));
                    return Command.SINGLE_SUCCESS;
                }

                Component mm = MiniMessage.miniMessage().deserialize(
                        "<green>Power Tool ID: <white>'<id>'",
                        Placeholder.unparsed("id", itemId.get())
                );

                player.sendMessage(mm);
                return Command.SINGLE_SUCCESS;
            }

            default -> player.sendMessage(Component.text("Usage: clear | set| get | toggle"));
        }
        return Command.SINGLE_SUCCESS;
    }

    @NotNull
    public LiteralCommandNode<CommandSourceStack> createCommand() {
        return Commands.literal("betterpowertools")
                .then(
                        Commands.argument("action", StringArgumentType.word())
                                .suggests((ctx, builder) -> {
                                    builder.suggest("clear");
                                    builder.suggest("get");
                                    builder.suggest("set");
                                    builder.suggest("toggle");
                                    return builder.buildFuture();
                                })

                                // action only
                                .executes(ctx -> {
                                    Player player = requirePlayer(ctx.getSource());
                                    if (player == null) return 0;

                                    String action = StringArgumentType.getString(ctx, "action");

                                    return handleAction(player, player, action, null);
                                })

                                // id branch
                                .then(
                                        Commands.argument("id", StringArgumentType.word())
                                                .suggests((ctx, builder) -> {
                                                    betterPowerTools.getPowerToolRegistry()
                                                            .getIds()
                                                            .forEach(builder::suggest);
                                                    return builder.buildFuture();
                                                })

                                                // id only
                                                .executes(ctx -> {
                                                    Player player = requirePlayer(ctx.getSource());
                                                    if (player == null) return 0;

                                                    String action = StringArgumentType.getString(ctx, "action");
                                                    String id = StringArgumentType.getString(ctx, "id");

                                                    return handleAction(player, player, action, id);
                                                })

                                                // id + target
                                                .then(
                                                        Commands.argument("target", StringArgumentType.word())
                                                                .suggests((ctx, builder) -> {
                                                                    Bukkit.getOnlinePlayers().forEach(p -> builder.suggest(p.getName()));
                                                                    return builder.buildFuture();
                                                                })
                                                                .executes(ctx -> {
                                                                    Player player = requirePlayer(ctx.getSource());
                                                                    if (player == null) return 0;

                                                                    String action = StringArgumentType.getString(ctx, "action");
                                                                    String id = StringArgumentType.getString(ctx, "id");
                                                                    String targetName = StringArgumentType.getString(ctx, "target");

                                                                    Player target = Bukkit.getPlayer(targetName);
                                                                    if (target == null) {
                                                                        player.sendMessage(Component.text("Player not found"));
                                                                        return 0;
                                                                    }

                                                                    return handleAction(player, target, action, id);
                                                                })
                                                )
                                )
                )
                .build();
    }
}
