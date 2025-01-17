package ru.me.a10sounds

import android.content.res.AssetFileDescriptor
import android.content.res.AssetManager
import android.media.AudioAttributes
import android.media.SoundPool
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var soundPool: SoundPool // SoundPool for handling sound effects
    private lateinit var assetManager: AssetManager // AssetManager for loading audio files

    private var glassSound: Int = 0 // ID for glass breaking sound
    private var akashiSound: Int = 0 // ID for Akashi movement sound
    private var glockSound: Int = 0 // ID for Glock shooting sound
    private var explosionSound: Int = 0 // ID for explosion sound

    private var streamID = 0 // Stream ID for managing playback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Enable edge-to-edge UI
        enableEdgeToEdge()

        // Set the content layout
        setContentView(R.layout.activity_main)

        // Find views in layout
        val akashiImage: ImageView = findViewById(R.id.akashi_image)
        val explosionImage: ImageView = findViewById(R.id.explosion_image)
        val glockImage: ImageView = findViewById(R.id.glock_image)
        val glassImage: ImageView = findViewById(R.id.glass_image)

        // Configure SoundPool attributes
        val attributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_GAME) // Set usage as game sounds
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION) // Define content type
            .build()
        soundPool = SoundPool.Builder()
            .setAudioAttributes(attributes)
            .build()

        // Initialize AssetManager
        assetManager = assets

        // Load sounds into SoundPool
        glassSound = loadSound("glass_break.mp3")
        akashiSound = loadSound("moving_in_water.mp3")
        glockSound = loadSound("pistol_shot.mp3")
        explosionSound = loadSound("small_explosion.mp3")

        // Set click listeners for each ImageView
        akashiImage.setOnClickListener {
            Log.w("MainActivity", "Playing Akashi sound")
            playSound(akashiSound) // Play Akashi movement sound
        }

        explosionImage.setOnClickListener {
            playSound(explosionSound) // Play explosion sound
        }

        glockImage.setOnClickListener {
            playSound(glockSound) // Play Glock shooting sound
        }

        glassImage.setOnClickListener {
            playSound(glassSound) // Play glass breaking sound
        }

        // Adjust padding to account for system bars and insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onPause() {
        super.onPause()
        // Release SoundPool resources to avoid memory leaks
        soundPool.release()
    }

    // Function to play a sound from SoundPool
    private fun playSound(sound: Int): Int {
        if (sound > 0) {
            Log.w("MainActivity", "Attempting to play sound ID: $sound")
            streamID = soundPool.play(sound, 1F, 1F, 1, 0, 1F) // Play the sound with full volume
        }
        return streamID
    }

    // Function to load a sound from the `assets` folder
    private fun loadSound(fileName: String): Int {
        val afd: AssetFileDescriptor = try {
            application.assets.openFd(fileName) // Load file descriptor from assets
        } catch (e: IOException) {
            e.printStackTrace()
            Log.d("MainActivity", "Could not load file: $fileName")
            return -1 // Return -1 if the sound file is not found
        }
        return soundPool.load(afd, 1) // Load the sound into SoundPool
    }
}
