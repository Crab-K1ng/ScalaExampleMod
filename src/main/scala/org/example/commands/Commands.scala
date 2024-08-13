package org.example.commands

import com.github.puzzle.game.commands.CommandManager
import com.github.puzzle.game.commands.PuzzleCommandSource
import com.github.puzzle.game.util.BlockUtil
import com.mojang.brigadier.arguments.IntegerArgumentType
import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import finalforeach.cosmicreach.blocks.BlockState
import finalforeach.cosmicreach.gamestates.InGame


object Commands {
  def register(): Int = {
    val cmd = CommandManager.literal("setBlock")
    cmd.`then`(CommandManager.argument("x", IntegerArgumentType.integer).`then`(CommandManager.argument("y", IntegerArgumentType.integer).`then`(CommandManager.argument("z", IntegerArgumentType.integer).`then`(CommandManager.argument("blockstate", StringArgumentType.greedyString).executes((context: CommandContext[PuzzleCommandSource]) => {
      val x = IntegerArgumentType.getInteger(context, "x")
      val y = IntegerArgumentType.getInteger(context, "y")
      val z = IntegerArgumentType.getInteger(context, "z")
      val blockState = StringArgumentType.getString(context, "blockstate")
      BlockUtil.setBlockAt(InGame.getLocalPlayer.getZone(InGame.world), BlockState.getInstance(blockState), x, y, z)
      0
    })))))
    CommandManager.dispatcher.register(cmd)
    return 0;
  }
}