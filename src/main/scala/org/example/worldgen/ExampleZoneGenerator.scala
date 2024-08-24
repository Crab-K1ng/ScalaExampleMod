package org.example.worldgen

import finalforeach.cosmicreach.blocks.BlockState
import finalforeach.cosmicreach.savelib.blockdata.LayeredBlockData
import finalforeach.cosmicreach.world.Chunk
import finalforeach.cosmicreach.world.Zone
import finalforeach.cosmicreach.worldgen.ChunkColumn
import finalforeach.cosmicreach.worldgen.ZoneGenerator
import finalforeach.cosmicreach.worldgen.noise.SimplexNoise


class ExampleZoneGenerator extends ZoneGenerator {
  final private val baseLevel = 64f
  final private val seaLevel = 64f
  final private val noiseAmplitude = 32f
  final private val noiseScale = 0.01f
  final private val softMaxY = 255
  final private val softMinY = 0
  // Fetches on class instantiation, so will not be null
  private[worldgen] val airBlock = this.getBlockStateInstance("base:air[default]")
  private[worldgen] val stoneBlock = this.getBlockStateInstance("base:stone_basalt[default]")
  private[worldgen] val waterBlock = this.getBlockStateInstance("base:water[default]")
  private var noise: SimplexNoise = null

  override def getSaveKey = "exmod:example_terrain"

  override protected def getName: String = {
    // Not fetched from the lang file
    "Example Terrain"
  }

  // Called on world load/create, after this.seed is set
  override def create(): Unit = {
    // Create noise generators
    noise = new SimplexNoise(this.seed)
  }

  // Generate a chunk-column of the world at once (easier for the lighting engine this way)
  override def generateForChunkColumn(zone: Zone, col: ChunkColumn): Unit = {
    val maxChunkY = Math.floorDiv(this.softMaxY, 16)
    val minChunkY = Math.floorDiv(this.softMinY, 16)
    // Only generate the regions containing minY < y < maxY
    if (col.chunkY < minChunkY || col.chunkY > maxChunkY) return
    for (chunkY <- minChunkY to maxChunkY) {
      var chunk = zone.getChunkAtChunkCoords(col.chunkX, chunkY, col.chunkZ)
      if (chunk == null) {
        // Create a new chunk if it doesn't exist
        chunk = new Chunk(col.chunkX, chunkY, col.chunkZ)
        // Create the chunk data
        chunk.initChunkData(() => new LayeredBlockData[BlockState](airBlock))
        // Register the chunks in the world and column
        zone.addChunk(chunk)
        col.addChunk(chunk)
      }
      // === Block placing logic ===
      // Loop through all blocks in the chunk
      for (localX <- 0 until Chunk.CHUNK_WIDTH) {
        val globalX = chunk.blockX + localX
        for (localZ <- 0 until Chunk.CHUNK_WIDTH) {
          val globalZ = chunk.blockZ + localZ
          // Only have to sample height once for each Y column (not going to change on the Y axis ;p)
          val columnHeight = noise.noise2(globalX * noiseScale, globalZ * noiseScale) * noiseAmplitude + baseLevel
          for (localY <- 0 until Chunk.CHUNK_WIDTH) {
            val globalY = chunk.blockY + localY
            // Below the ground
            if (globalY <= columnHeight) {
              // Don't want to set existing solid blocks to air in unloaded chunks (what about structures?)
              chunk.setBlockState(stoneBlock, localX, localY, localZ)
            }
            else {
              // Below the sea level
              if (globalY <= seaLevel) chunk.setBlockState(waterBlock, localX, localY, localZ)
            }
          }
        }
      }
    }
  }

  override def getDefaultRespawnYLevel = 0
}
