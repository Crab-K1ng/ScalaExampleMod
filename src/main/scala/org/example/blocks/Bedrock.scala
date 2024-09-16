package org.example.blocks

import com.github.puzzle.game.block.IModBlock
import com.github.puzzle.game.generators.BlockEventGenerator
import com.github.puzzle.game.generators.BlockGenerator
import com.github.puzzle.game.generators.BlockModelGenerator
import finalforeach.cosmicreach.blocks.BlockPosition
import finalforeach.cosmicreach.blocks.BlockState
import finalforeach.cosmicreach.entities.player.Player
import finalforeach.cosmicreach.items.Item
import finalforeach.cosmicreach.items.ItemSlot
import finalforeach.cosmicreach.ui.UI
import finalforeach.cosmicreach.util.Identifier
import finalforeach.cosmicreach.world.Zone
import org.example.block_enities.ExampleBlockEntityRegistrar
import org.example.exmod.Constants

import java.util
import scala.jdk.CollectionConverters.*

class Bedrock extends IModBlock {
  val BLOCK_ID = Identifier.of(Constants.MOD_ID, "bedrock")

  val ALL_TEXTURE = Identifier.of("base", "textures/blocks/lunar_soil.png")

  override def getIdentifier: Identifier = BLOCK_ID

  override def onBreak(zone: Zone, player: Player, blockState: BlockState, position: BlockPosition): Unit = {
    val slot = UI.hotbar.getSelectedSlot
    if (slot == null) return
    if (slot.itemStack != null) {
      val selected = slot.itemStack.getItem
      val itemId = selected.getID
      if (itemId.startsWith(BLOCK_ID.toString)) {
        // make the block breakable when the player holds bedrock
        super.onBreak(zone, player, blockState, position)
      }
    }
    // make the block unbreakable, by omitting the super call here
  }

  override def getBlockGenerator: BlockGenerator = {
    val generator = new BlockGenerator(BLOCK_ID)
    generator.createBlockState("default", "model", true, "events", true)
    generator.addBlockEntity(ExampleBlockEntityRegistrar.id.toString, util.Map.of())
    generator
  }

  override def getBlockModelGenerators(blockId: Identifier): java.util.List[BlockModelGenerator] = {
    val generator = new BlockModelGenerator(blockId, "model")
    generator.createTexture("all", ALL_TEXTURE)
    generator.createCuboid(0, 0, 0, 16, 16, 16, "all")
    List(generator).asJava
  }

  override def getBlockEventGenerators(blockId: Identifier): java.util.List[BlockEventGenerator] = {
    val generator = new BlockEventGenerator(blockId, "events")
    List(generator).asJava
  }
}
