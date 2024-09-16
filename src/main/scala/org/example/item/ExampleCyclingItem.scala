package org.example.item

import com.github.puzzle.core.Puzzle
import com.github.puzzle.game.items.{IModItem, ITickingItem}
import com.github.puzzle.game.items.data.attributes.{IdentifierDataAttribute, IntDataAttribute, ListDataAttribute, PairAttribute}
import com.github.puzzle.game.items.data.{DataTag, DataTagManifest}
import com.github.puzzle.game.util.DataTagUtil
import finalforeach.cosmicreach.entities.ItemEntity
import finalforeach.cosmicreach.entities.player.Player
import finalforeach.cosmicreach.io.ICRBinSerializable
import finalforeach.cosmicreach.items.{ItemSlot, ItemStack}
import finalforeach.cosmicreach.util.Identifier
import finalforeach.cosmicreach.world.Zone
import org.example.exmod.Constants

import java.awt.Desktop
import java.io.IOException
import java.net.{URI, URISyntaxException}
import scala.language.postfixOps

class ExampleCyclingItem extends IModItem, ITickingItem {

  private var tagManifest: DataTagManifest = DataTagManifest()
  private var id: Identifier = Identifier.of(Constants.MOD_ID, "example_cycling_item")

  override def toString: String = id.toString

  private var texture_count = 0

  {
    addTexture(
      IModItem.MODEL_2_5D_ITEM,
      Identifier.of(Puzzle.MOD_ID, "textures/items/null_stick.png"),
      Identifier.of("base", "textures/items/axe_stone.png"),
      Identifier.of("base", "textures/items/pickaxe_stone.png"),
      Identifier.of("base", "textures/items/shovel_stone.png"),
      Identifier.of("base", "textures/items/medkit.png"),
      Identifier.of(Puzzle.MOD_ID, "textures/items/block_wrench.png"),
      Identifier.of(Puzzle.MOD_ID, "textures/items/checker_board.png"),
      Identifier.of(Puzzle.MOD_ID, "textures/items/checker_board1.png"),
      Identifier.of(Puzzle.MOD_ID, "textures/items/checker_board2.png")
    )

    addTexture(
      IModItem.MODEL_2D_ITEM,
      Identifier.of(Puzzle.MOD_ID, "textures/items/null_stick.png"),
      Identifier.of("base", "textures/items/axe_stone.png"),
      Identifier.of("base", "textures/items/pickaxe_stone.png"),
      Identifier.of("base", "textures/items/shovel_stone.png"),
      Identifier.of("base", "textures/items/medkit.png"),
      Identifier.of(Puzzle.MOD_ID, "textures/items/block_wrench.png"),
      Identifier.of(Puzzle.MOD_ID, "textures/items/checker_board.png"),
      Identifier.of(Puzzle.MOD_ID, "textures/items/checker_board1.png"),
      Identifier.of(Puzzle.MOD_ID, "textures/items/checker_board2.png")
    )

//    texture_count = getTagManifest().getTag("textures").getTagAsType(List.getClass).attribute as ListDataAttribute[PairAttribute[IdentifierDataAttribute, ResourceLocationDataAttribute]].getValue.size()
    texture_count = getTagManifest.getTag("textures").attribute.asInstanceOf[ListDataAttribute[_ <: ICRBinSerializable]].getValue.size - 1
  }

  override def getIdentifier: Identifier = id

  override def getTagManifest: DataTagManifest = tagManifest

  override def isTool: Boolean = true

  override def use(slot: ItemSlot, player: Player): Unit = {
    if (Desktop.isDesktopSupported && Desktop.getDesktop.isSupported(Desktop.Action.BROWSE)) try Desktop.getDesktop.browse(new URI("https://discord.gg/VeEnVHwRXN"))
    catch {
      case e@(_: IOException | _: URISyntaxException) =>
        throw new RuntimeException(e)
    }
  }

  override def tickStack(fixedUpdateTimeStep: Float, stack: ItemStack, isBeingHeld: Boolean): Unit = {
    var textureEntry: Integer = getCurrentEntry(stack)
    textureEntry = if (textureEntry >= texture_count) 0
    else textureEntry + 1
    setCurrentEntry(stack, textureEntry)
  }

  override def tickEntity(zone: Zone, deltaTime: Double, entity: ItemEntity, stack: ItemStack): Unit = {
    var textureEntry: Integer = getCurrentEntry(stack)
    textureEntry = if (textureEntry >= texture_count) 0
    else textureEntry + 1
    setCurrentEntry(stack, textureEntry)
  }

  override def getName: String = "Example Pickaxe"

}
