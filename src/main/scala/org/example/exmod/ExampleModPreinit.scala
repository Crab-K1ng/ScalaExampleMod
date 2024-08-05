package org.example.exmod

import com.github.puzzle.loader.entrypoint.interfaces.PreModInitializer

class ExampleModPreinit extends PreModInitializer {


  override def onPreInit(): Unit = {
    Constants.LOGGER.info("Hello From PRE-INIT")
  }
}
