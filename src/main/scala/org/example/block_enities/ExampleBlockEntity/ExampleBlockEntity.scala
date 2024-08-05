package org.example.block_enities.ExampleBlockEntity

import com.github.puzzle.game.blockentities.ExtendedBlockEntity
import com.badlogic.gdx.graphics.Camera
import com.github.puzzle.core.Identifier
import com.github.puzzle.game.blockentities.ExtendedBlockEntity
import com.github.puzzle.game.blockentities.IRenderable
import com.github.puzzle.game.blockentities.ITickable
import finalforeach.cosmicreach.blockentities.BlockEntityCreator
import finalforeach.cosmicreach.blocks.Block
import finalforeach.cosmicreach.blocks.BlockPosition
import finalforeach.cosmicreach.blocks.BlockState
import finalforeach.cosmicreach.world.Zone
import org.example.exmod.Constants



object ExampleBlockEntity {
  val id = new Identifier(Constants.MOD_ID, "example_entity")

  def register() = {
    BlockEntityCreator.registerBlockEntityCreator(id.toString, (block, zone, x, y, z) => new ExampleBlockEntity(zone, x, y, z))
  }
}
class ExampleBlockEntity(zone: Zone, x: Int, y: Int, z: Int) extends ExtendedBlockEntity(zone, x, y, z), IRenderable, ITickable{
 

  override def onRender(camera: Camera): Unit = {}

  override def onTick(v: Float): Unit = {
    val above = position.getOffsetBlockPos(position.chunk.region.zone, 0, 1, 0)
    val current = above.getBlockState
    if (current.getBlock eq Block.AIR) {
      above.setBlockState(Block.GRASS.getDefaultBlockState)
      above.flagTouchingChunksForRemeshing(position.chunk.region.zone, false)
    }
  }

  override def getBlockEntityId: String = ExampleBlockEntity.id.toString
}
