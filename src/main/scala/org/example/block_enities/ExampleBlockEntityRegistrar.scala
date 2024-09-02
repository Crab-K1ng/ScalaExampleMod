package org.example.block_enities

import com.badlogic.gdx.graphics.Camera
import com.github.puzzle.core.Identifier
import com.github.puzzle.game.blockentities.{IRenderable}
import com.github.puzzle.game.util.BlockUtil
import finalforeach.cosmicreach.blockentities.{BlockEntity, BlockEntityCreator}
import finalforeach.cosmicreach.blocks.{Block, BlockState}
import finalforeach.cosmicreach.world.Zone
import org.example.exmod.Constants

object ExampleBlockEntityRegistrar {
  val id = new Identifier(Constants.MOD_ID, "example_entity")


  class ExampleBlockEntity(zone: Zone, x: Int, y: Int, z: Int) extends BlockEntity(zone, x, y, z), IRenderable {

    override def onRender(camera: Camera): Unit = {}

    override def onCreate(blockState: BlockState): Unit = {
      setTicking(true)
      super.onCreate(blockState)
    }

    override def onRemove(): Unit = {
      setTicking(false)
      super.onRemove()
    }

    override def onTick(): Unit = {

      val above = BlockUtil.getBlockPosAtVec(zone, x, y, z).getOffsetBlockPos(zone, 0, 1, 0)
      val current = above.getBlockState
      if (current.getBlock == Block.AIR) {
        above.setBlockState(Block.GRASS.getDefaultBlockState)
        above.flagTouchingChunksForRemeshing(zone, false)
      }
    }

    override def getBlockEntityId: String = ExampleBlockEntityRegistrar.id.toString
  }

  def register() = {
    BlockEntityCreator.registerBlockEntityCreator(id.toString, (block, zone, x, y, z) => new ExampleBlockEntity(zone, x, y, z))
  }

}
