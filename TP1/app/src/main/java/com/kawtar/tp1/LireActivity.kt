package com.kawtar.tp1

import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
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
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.bumptech.glide.Glide
import java.io.ObjectInputStream
import java.io.ObjectOutput
import java.io.ObjectOutputStream
import java.time.LocalTime
import java.time.format.DateTimeFormatter


class LireActivity : AppCompatActivity() {
    lateinit var vue: ImageView
    lateinit var seekBar: SeekBar
    lateinit var titre: TextView
    lateinit var temps: TextView
    lateinit var artist: TextView
    lateinit var play: ImageButton
    lateinit var right: ImageButton
    lateinit var left: ImageButton
    lateinit var moins10: Button
    lateinit var plus10: Button
    lateinit var ec: Ecouteur
    var player: ExoPlayer? = null
    var artists = ArrayList<String>()
    var titres = ArrayList<String>()
    var pochettes = ArrayList<String>()
    var audios = ArrayList<String>()
    var position = 0
    var montimer: MonTimer? = null
    var nbreSeconde:Int = 0
    var tempsAffiche : LocalTime = LocalTime.MIDNIGHT // l'initialiser a  00:00
    val format = DateTimeFormatter.ofPattern("mm:ss")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_lire)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        vue = findViewById(R.id.image)
        seekBar = findViewById(R.id.seekBar2)
        titre = findViewById(R.id.titre)
        temps = findViewById(R.id.temps)
        artist = findViewById(R.id.artiste)
        play = findViewById(R.id.play)
        right = findViewById(R.id.imageButton)
        left = findViewById(R.id.imageButton2)
        moins10 = findViewById(R.id.moinsdix)
        plus10 = findViewById(R.id.plusdix)
        ec = Ecouteur()



        artists = intent.getStringArrayListExtra("artists") ?: ArrayList()
        titres = intent.getStringArrayListExtra("titres") ?: ArrayList()
        pochettes = intent.getStringArrayListExtra("pochettes") ?: ArrayList()
        audios = intent.getStringArrayListExtra("audios") ?: ArrayList()
        position = intent.getIntExtra("position", 0)

        if (intent.hasExtra("position")) {
            position = intent.getIntExtra("position", 0)
        } else {
            deserialisation()
        }

        seekBar.setOnSeekBarChangeListener(ec)
        play.setOnClickListener {
            if (player?.isPlaying == true) {
                player?.pause()
            } else {
                player?.play()
            }
        }
        right.setOnClickListener {
            position--;
            if (player?.hasPreviousMediaItem() == false) {
                Toast.makeText(this, "vous etes rendu a toute premiere chanson", Toast.LENGTH_LONG)
                    .show()
            } else {
                player?.seekToPreviousMediaItem()
                titre.setText(titres.get(position))
                artist.setText(artists.get(position))
                Glide.with(this)
                    .load(pochettes[position])
                    .into(vue)

            }
        }
        left.setOnClickListener {
            position++
            if (player?.hasNextMediaItem() == false) {
                Toast.makeText(this, "vous etes rendu a toute derniere chanson", Toast.LENGTH_LONG)
                    .show()
            } else {
                player?.seekToNextMediaItem()
                titre.setText(titres.get(position))
                artist.setText(artists.get(position))
                Glide.with(this)
                    .load(pochettes[position])
                    .into(vue)
            }
        }
        plus10.setOnClickListener {
            var pos = player?.currentPosition ?: 0L
            val pos2 = pos + 10000
            player?.seekTo(pos2)
        }
        moins10.setOnClickListener {
            var pos = player?.currentPosition ?: 0L
            val pos2 = pos - 10000
            player?.seekTo(pos2)
        }

    }

    inner class Ecouteur : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(
            seekBar: SeekBar?,
            progress: Int,
            fromUser: Boolean
        ) {
            // le temps
        }

        override fun onStartTrackingTouch(seekBar: SeekBar?) {

        }

        override fun onStopTrackingTouch(seekBar: SeekBar?) {
            var p = seekBar?.progress
            player?.seekTo(p?.toLong()!!)
        }
    }

    override fun onStart() {
        super.onStart()
        player = ExoPlayer.Builder(this).build()

        for (l in audios) {
            val mediaItem = MediaItem.fromUri(l)
            player?.addMediaItem(mediaItem)
        }

        player?.addListener(object : Player.Listener {
            override fun onPlaybackStateChanged(playbackState: Int) {
                // STATE_READY signifie que le lecteur a chargé la piste et connaît sa durée
                if (playbackState == Player.STATE_READY) {
                    val duration = player?.duration ?: 0L
                    if (duration > 0) {
                        seekBar.max = duration.toInt()
                        DemarrerTimer(duration)
                        temps.setText(duration.toString())
                    }
                }
            }
        })

        player?.seekTo(position, 0)
        player?.prepare()
        player?.play()

        // afficher les infos de la chanson actuelle
        titre.setText(titres.get(position))
        artist.setText(artists.get(position))
        Glide.with(this)
            .load(pochettes[position])
            .into(vue)

//        montimer?.cancel()
//        var duration = player?.duration
//        seekBar.max = 30000
//        DemarrerTimer(30000)


    }

    override fun onStop() {
        super.onStop()
        player?.release()
        player = null
        try {

            var fos = openFileOutput("fichier.ser",MODE_PRIVATE)
            var oos = ObjectOutputStream(fos)
            oos.use{
                it.writeInt(position)
                it.writeBoolean(true)
            }
        } catch (e: Exception) {
            print("serialisation ne marche pas ")
        }
    }

    fun DemarrerTimer(duree: Long) {
        montimer?.cancel()
        montimer = MonTimer(30000,1000)
        montimer?.start()


    }

    inner class MonTimer(millisInFuture: Long, countDownInterval: Long) :
        CountDownTimer(millisInFuture, countDownInterval) {
        override fun onFinish() {
        }

        override fun onTick(millisUntilFinished: Long) {

                val currentPos = player?.currentPosition ?: 0L
                seekBar.progress = currentPos.toInt()
                //nbreSeconde +=1000
                //seekBar.progress = nbreSeconde

                val secondes = currentPos / 1000
            // Transformer les secondes en LocalTime (  24*60*60 )
                tempsAffiche = LocalTime.ofSecondOfDay(secondes % 86400)
                temps.text = tempsAffiche.format(format)

        }
    }
    fun deserialisation(){
        try {
            var fis = openFileInput("fichier.ser")
            var ois = ObjectInputStream(fis)
            ois.use{
                position = it.readInt()
                val page = it.readBoolean()
            }
        } catch (e: Exception) {

            position = 0
            print("desserialisation ne marche pas ")
        }


    }
}