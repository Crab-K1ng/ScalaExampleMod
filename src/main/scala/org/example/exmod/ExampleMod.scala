package org.example.exmod

import com.github.puzzle.core.PuzzleRegistries
import com.github.puzzle.core.localization.{ILanguageFile, LanguageManager}
import com.github.puzzle.core.localization.files.LanguageFileVersion1
import com.github.puzzle.core.resources.ResourceLocation
import com.github.puzzle.game.block.DataModBlock
import com.github.puzzle.loader.entrypoint.interfaces.ModInitializer
import com.github.puzzle.game.events.{OnPreLoadAssetsEvent, OnRegisterBlockEvent}
import org.example.blocks.Bedrock
import org.greenrobot.eventbus.Subscribe

import java.io.IOException

class ExampleMod extends ModInitializer{

  override def onInit(): Unit = {
    PuzzleRegistries.EVENT_BUS.register(this)

  }

  @Subscribe
  def onEvent(event: OnRegisterBlockEvent): Unit = {
    event.registerBlock(() => new DataModBlock("diamond_block", new ResourceLocation(Constants.MOD_ID, "blocks/diamond_block.json")))
    event.registerBlock(() => new Bedrock())
  }

  @Subscribe
  def onEvent(event: OnPreLoadAssetsEvent): Unit = {
    var lang: ILanguageFile= null
    try lang = LanguageFileVersion1.loadLanguageFromString(ResourceLocation(Constants.MOD_ID, "languages/en-US.json").locate.readString)
    catch {
      case e : IOException => throw new RuntimeException(e)
    }
    LanguageManager.registerLanguageFile(lang)
  }
}
