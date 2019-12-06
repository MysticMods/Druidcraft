package com.vulp.druidcraft.command.impl;

import com.mojang.brigadier.CommandDispatcher;
import com.vulp.druidcraft.api.CropLifeStageType;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.text.TranslationTextComponent;

public class QueryPlantStageCommand {

    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        dispatcher.register(Commands.literal("queryfruittime").requires((p_198599_0_) -> {
            return p_198599_0_.hasPermissionLevel(1);
        }).executes((p_198598_0_) -> {
            p_198598_0_.getSource().sendFeedback(new TranslationTextComponent("commands.queryfruittime.query", CropLifeStageType.checkCropLife(p_198598_0_.getSource().getWorld()).toString()), true);
            return 1;
        }));
    }
}