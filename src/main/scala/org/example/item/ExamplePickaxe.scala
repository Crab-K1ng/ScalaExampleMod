package org.example.item

import com.github.puzzle.game.items.IModItem
import com.github.puzzle.game.items.data.DataTagManifest
import finalforeach.cosmicreach.blocks.BlockState
import finalforeach.cosmicreach.items.ItemStack
import finalforeach.cosmicreach.util.Identifier
import org.example.exmod.Constants

class ExamplePickaxe extends IModItem {

    var tagManifest: DataTagManifest = new DataTagManifest()
    var id: Identifier = Identifier.of(Constants.MOD_ID,"example_pickaxe");

    {
        addTexture(IModItem.MODEL_2_5D_ITEM, Identifier.of(Constants.MOD_ID, "textures/items/example_pickaxe.png"))
    }

    override def isTool = true
    
    override def getEffectiveBreakingSpeed(stack: ItemStack): Float = if (stack.getItem.equals(this)) 2.0f
    else 1.0f

    override def isEffectiveBreaking(itemStack: ItemStack, blockState: BlockState): Boolean = blockState.getBlockId.equals("base:aluminium_panel") || blockState.getBlockId.equals("base:asphalt") || blockState.getBlockId.equals("base:boombox") || blockState.getBlockId.equals("base:c4") || blockState.getBlockId.equals("base:hazard") || blockState.getBlockId.equals("base:light") || blockState.getBlockId.equals("base:magma") || blockState.getBlockId.equals("base:metal_panel") || blockState.getBlockId.equals("base:stone_basalt") || blockState.getBlockId.equals("base:stone_gabbro") || blockState.getBlockId.equals("base:stone_limestone") || blockState.getBlockId.equals("base:lunar_soil_packed")

    override def getIdentifier: Identifier = id

    override def getTagManifest: DataTagManifest = tagManifest

    override def isCatalogHidden = false

    override def toString: String = id.toString

    override def getName: String = "Example Pickaxe"
    
}
