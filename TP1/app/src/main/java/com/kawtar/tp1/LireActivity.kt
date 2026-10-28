package com.kawtar.tp1

import android.os.Bundle
import android.os.CountDownTimer
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.bumptech.glide.Glide
import com.bumptech.glide.disklrucache.DiskLruCache
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class LireActivity : AppCompatActivity() {
    lateinit var vue: ImageView
    lateinit var seekBar: SeekBar
    lateinit var titre: TextView
    lateinit var temps: TextView
    lateinit var artist: TextView
    lateinit var play : ImageButton
    lateinit var right : ImageButton
    lateinit var left : ImageButton
    var player: ExoPlayer?= null
    lateinit var vuePlayer: PlayerView
    var artists = ArrayList<String>()
    var titres = ArrayList<String>()
    var pochettes = ArrayList<String>()
    var audios = ArrayList<String>()
    var position = 0
    var montimer : MonTimer? = null
    var tempsAffiche : LocalTime = LocalTime.MIDNIGHT
    val format = DateTimeFormatter.ofPattern("mm:ss")
     b
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_lire)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        vuePlayer = findViewById(R.id.vue)
        vue = findViewById(R.id.image)
        seekBar = findViewById(R.id.seekBar2)
        titre = findViewById(R.id.titre)
        temps = findViewById(R.id.temps)
        artist = findViewById(R.id.artiste)
        play =findViewById(R.id.play)
        right =findViewById(R.id.imageButton)
        left =findViewById(R.id.imageButton2)




        artists = intent.getStringArrayListExtra("artists")?: ArrayList()
        titres =  intent.getStringArrayListExtra("titres")?: ArrayList()
        pochettes =  intent.getStringArrayListExtra("pochettes")?: ArrayList()
        audios =  intent.getStringArrayListExtra("audios")?: ArrayList()
        position = intent.getIntExtra("position", 0)

        vuePlayer.setUseController(false)
        play.setOnClickListener {
            if(player?.isPlaying == true) {
                player?.pause()
            }
            else {
                player?.play()
            }
        }
        right.setOnClickListener {

            if(player?.hasPreviousMediaItem() == false){
                Toast.makeText(this,"vous etes rendu a toute premiere chanson",Toast.LENGTH_LONG).show()
            }else {   player?.seekToPreviousMediaItem()}
        }
        left.setOnClickListener {

            if(player?.hasNextMediaItem() == false){
                Toast.makeText(this,"vous etes rendu a toute derniere chanson",Toast.LENGTH_LONG).show()
            }
            else{player?.seekToNextMediaItem()}
        }


    }
    override fun onStart() {
        super.onStart()
        player = ExoPlayer.Builder(this).build()
        vuePlayer.player= player
        for(l in audios){
            val mediaItem = MediaItem.fromUri(l)
            player?.addMediaItem(mediaItem)
        }
        player?.seekTo(position,0)

        player?.prepare()
        player?.play()
        titre.setText(titres.get(position))
        artist.setText(artists.get(position))
        Glide.with(this)
            .load(pochettes[position])
            .into(vue)

    }

    override fun onStop() {
        super.onStop()
        player?.release()
        player = null
    }
    fun DemarrerTimer() {
        if (m )

    }
    inner class MonTimer(duree : Long) :
        CountDownTimer(duree,1000) {
        override fun onFinish() {
            seekBar.setProgress(0)

        }

        override fun onTick(millisUntilFinished: Long) {
         seekBar.setProgress(millisUntilFinished.toInt())
        }
    }
}