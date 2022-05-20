package com.BAVDE.atium_mod.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.datafixers.util.Pair;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.ResourceOrTagLocationArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.*;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;

public class Locate2Command {
    public Locate2Command(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("locate2").requires((commandSourceStack) -> {
            return commandSourceStack.hasPermission(2);
        }).then(Commands.argument("structure", ResourceOrTagLocationArgument.resourceOrTag(Registry.CONFIGURED_STRUCTURE_FEATURE_REGISTRY)).executes((command) -> {
            return locate(command.getSource(), ResourceOrTagLocationArgument.getStructureFeature(command, "structure"));
        })));
    }

    private static final DynamicCommandExceptionType ERROR_FAILED = new DynamicCommandExceptionType((o) -> {
        return new TranslatableComponent("commands.locate.failed", o);
    });
    private static final DynamicCommandExceptionType ERROR_INVALID = new DynamicCommandExceptionType((o) -> {
        return new TranslatableComponent("commands.locate.invalid", o);
    });

    public static int locate(CommandSourceStack commandSourceStack, ResourceOrTagLocationArgument.Result<ConfiguredStructureFeature<?, ?>> structureFeatureResult) throws CommandSyntaxException {
        Registry<ConfiguredStructureFeature<?, ?>> registry = commandSourceStack.getLevel().registryAccess().registryOrThrow(Registry.CONFIGURED_STRUCTURE_FEATURE_REGISTRY);
        HolderSet<ConfiguredStructureFeature<?, ?>> holderset = structureFeatureResult.unwrap().map((holder) -> {
            return registry.getHolder(holder).map((structureFeatureHolder) -> {
                return HolderSet.direct(structureFeatureHolder);
            });
        }, registry::getTag).orElseThrow(() -> {
            return ERROR_INVALID.create(structureFeatureResult.asPrintable());
        });
        BlockPos blockpos = new BlockPos(commandSourceStack.getPosition());
        ServerLevel serverlevel = commandSourceStack.getLevel();
        Pair<BlockPos, Holder<ConfiguredStructureFeature<?, ?>>> pair = serverlevel.getChunkSource().getGenerator().

                findNearestMapFeature(serverlevel, holderset, blockpos, 100, false);

        commandSourceStack.sendSuccess(new TranslatableComponent("stack: " + commandSourceStack), false);

        if (pair == null) {
            throw ERROR_FAILED.create(structureFeatureResult.asPrintable());
        } else {
            return showLocateResult(commandSourceStack, structureFeatureResult, blockpos, pair, "commands.locate.success");
        }
    }

    public static int showLocateResult(CommandSourceStack commandSourceStack, ResourceOrTagLocationArgument.Result<?> result, BlockPos blockPos, Pair<BlockPos, ? extends Holder<?>> blockPosPair, String s1) {
        BlockPos blockpos = blockPosPair.getFirst();
        String s = result.unwrap().map((resourceKey) -> {
            return resourceKey.location().toString();
        }, (p_207511_) -> {
            return "#" + p_207511_.location() + " (" + (String) blockPosPair.getSecond().unwrapKey().map((key) -> {
                return key.location().toString();
            }).orElse("[unregistered]") + ")";
        });
        int i = Mth.floor(dist(blockPos.getX(), blockPos.getZ(), blockpos.getX(), blockpos.getZ()));
        Component component = ComponentUtils.wrapInSquareBrackets(new TranslatableComponent("chat.coordinates", blockpos.getX(), "~", blockpos.getZ())).withStyle((style) -> {
            return style.withColor(ChatFormatting.GREEN).withClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/tp @s " + blockpos.getX() + " ~ " + blockpos.getZ())).withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TranslatableComponent("chat.coordinates.tooltip")));
        });
        commandSourceStack.sendSuccess(new TranslatableComponent(s1, s, component, i), false);
        //commandSourceStack.sendSuccess(new TranslatableComponent("stack: " + commandSourceStack), false);
        return i;
    }

    private static float dist(int pX1, int pZ1, int pX2, int pZ2) {
        int i = pX2 - pX1;
        int j = pZ2 - pZ1;
        return Mth.sqrt((float) (i * i + j * j));
    }
}