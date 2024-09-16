package org.example.exmod

import com.github.puzzle.core.PuzzleRegistries
import com.github.puzzle.core.localization.{ILanguageFile, LanguageManager}
import com.github.puzzle.core.localization.files.LanguageFileVersion1
import com.github.puzzle.core.resources.PuzzleGameAssetLoader
import com.github.puzzle.game.block.DataModBlock
import com.github.puzzle.loader.entrypoint.interfaces.ModInitializer
import com.github.puzzle.game.events.{OnPreLoadAssetsEvent, OnRegisterBlockEvent, OnRegisterZoneGenerators}
import org.example.blocks.Bedrock
import org.greenrobot.eventbus.Subscribe
import com.github.puzzle.game.items.IModItem
import com.github.puzzle.game.items.impl.{BasicItem, BasicTool}
import finalforeach.cosmicreach.util.Identifier
import org.example.block_enities.ExampleBlockEntityRegistrar
import org.example.commands.Commands
import org.example.item.{ExampleCyclingItem, ExamplePickaxe}
import org.example.worldgen.ExampleZoneGenerator

import java.io.IOException

class ExampleMod extends ModInitializer{

  override def onInit(): Unit = {
    PuzzleRegistries.EVENT_BUS.register(this)

    ExampleBlockEntityRegistrar.register()

    Commands.register()

    IModItem.registerItem(new ExamplePickaxe());
    IModItem.registerItem(new ExampleCyclingItem());
    IModItem.registerItem(new BasicItem(Identifier.of(Constants.MOD_ID, "example_item")));
    IModItem.registerItem(new BasicTool(Identifier.of(Constants.MOD_ID, "stone_sword")));
  }

  @Subscribe
  def onEvent(event: OnRegisterBlockEvent): Unit = {
    event.registerBlock(() => new DataModBlock(Identifier.of(Constants.MOD_ID, "diamond_block.json")))
    event.registerBlock(() => new Bedrock())
  }

  @Subscribe
  def onEvent(event: OnRegisterZoneGenerators): Unit = {
    event.registerGenerator(() => {
      new ExampleZoneGenerator()
    })
  }

  @Subscribe
  def onEvent(event: OnPreLoadAssetsEvent): Unit = {
    var lang: ILanguageFile= null
    try lang = LanguageFileVersion1.loadLanguageFile(PuzzleGameAssetLoader.locateAsset(Identifier.of(Constants.MOD_ID, "languages/en-US.json")))
    catch {
      case e : IOException => throw new RuntimeException(e)
    }
    LanguageManager.registerLanguageFile(lang)
  }
}
