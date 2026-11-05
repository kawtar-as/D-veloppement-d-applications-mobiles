package com.kawtar.tp1
// Cette classe gère un lecteur audio complet (avec ExoPlayer, listes de lecture,
// contrôles et gestion du temps) tout en sauvegardant et restaurant automatiquement la position de lecture grâce à la sérialisation.
import android.content.Intent
import android.net.Uri
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
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
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
    lateinit var goLink :Button
    var player: ExoPlayer? = null
    var artists = ArrayList<String>()
    var titres = ArrayList<String>()
    var pochettes = ArrayList<String>()
    var audios = ArrayList<String>()
    var position = 0
    var montimer: MonTimer? = null
    var tempsAffiche : LocalTime = LocalTime.MIDNIGHT // l'initialiser a  00:00
    val format = DateTimeFormatter.ofPattern("mm:ss")
    var tempsSauvegarde: Long = 0L// temps où on a arrêté la chanson pour la serialisation


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
        goLink = findViewById(R.id.lien)


        artists = intent.getStringArrayListExtra("artists") ?: ArrayList()
        titres = intent.getStringArrayListExtra("titres") ?: ArrayList()
        pochettes = intent.getStringArrayListExtra("pochettes") ?: ArrayList()
        audios = intent.getStringArrayListExtra("audios") ?: ArrayList()
        position = intent.getIntExtra("position", 0)

        // genre si on l'ouvre depuis mainActivity les audios sont vides
        if (audios.isEmpty()) {      // lancée sans extras → on restaure la sauvegarde
            deserialisation()
        }
        goLink.setOnClickListener {
            // on p-rens juste l url qui est sauvegarder dans la liste audios
            val i = Intent(Intent.ACTION_VIEW,Uri.parse(audios.get(position)))
            startActivity(i)
        }
        play.setOnClickListener { // jouer et arreter la chanson
            if (player?.isPlaying == true) {
                player?.pause()
            } else {
                player?.play()
            }
        }
        // précédent / suivant: on vérifie d'abord s'il reste une chanson avant de changer
        // sinon on affiche un Toast
        right.setOnClickListener {
            if (player?.hasPreviousMediaItem() == true) {
                position--
                player?.seekToPreviousMediaItem()
                titre.setText(titres.get(position))
                artist.setText(artists.get(position))
                Glide.with(this)
                    .load(pochettes[position])
                    .into(vue)
            } else {
                Toast.makeText(this, "Vous êtes à la toute première chanson", Toast.LENGTH_LONG).show()
            }

        }
        left.setOnClickListener {

            if (player?.hasNextMediaItem() == true) {
                position++
                player?.seekToNextMediaItem()
                titre.setText(titres.get(position))
                artist.setText(artists.get(position))
                Glide.with(this)
                    .load(pochettes[position])
                    .into(vue)
            } else {
                Toast.makeText(this, "Vous êtes à la toute dernière chanson", Toast.LENGTH_LONG).show()
            }

        }// ajouter et diminuer de 10 s
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

    override fun onStart() {
        super.onStart()
        player = ExoPlayer.Builder(this).build()
        // ajouter toutes les chansons dans la playlist
        for (l in audios) {
            val mediaItem = MediaItem.fromUri(l)
            player?.addMediaItem(mediaItem)
        }
        // écouteur d'événements: STATE_READY veut dire que la chanson est chargée
        //mettre le max et demarrer le timer
        player?.addListener(object : Player.Listener {
            override fun onPlaybackStateChanged(playbackState: Int) {
                // STATE_READY signifie que le lecteur a chargé la piste et connaît sa durée
                if (playbackState == Player.STATE_READY) {
                    val duration = player?.duration ?: 0L
                    if (duration > 0) {
                        seekBar.max = duration.toInt()
                        demarrerTimer(duration)
                        temps.setText(duration.toString())
                    }
                }
            }
        })
        //se placer  sur la bonne chanson au bon temps
        player?.seekTo(position, tempsSauvegarde)
        player?.prepare()
        player?.play()

        // afficher les infos de la chanson actuelle
        titre.setText(titres.get(position))
        artist.setText(artists.get(position))
        Glide.with(this)
            .load(pochettes[position])
            .into(vue)


    }

    override fun onStop() {
        super.onStop()
        tempsSauvegarde = player?.currentPosition ?: 0L
        player?.release()
        player = null
        //serialisation
        // (chanson courante, listes et temps) pour reprendre à la réouverture
        try {

            var fos = openFileOutput("fichier.ser",MODE_PRIVATE)
            var oos = ObjectOutputStream(fos)
            oos.use{
                oos.writeObject(Sauvegarde(position, artists, titres, audios, pochettes,tempsSauvegarde))

            }
        } catch (e: Exception) {
            print("serialisation ne marche pas ")
        }
    }

    fun demarrerTimer(duree: Long) {
        montimer?.cancel()
        montimer = MonTimer(30000,1000)
        montimer?.start()


    }
    // CountDownTimer: toutes les secondes, on lit la position du player
    // puis on met à jour la SeekBar et le temps affiché (mm:ss)
    inner class MonTimer(millisInFuture: Long, countDownInterval: Long) :
        CountDownTimer(millisInFuture, countDownInterval) {
        override fun onFinish() {
        }

        override fun onTick(millisUntilFinished: Long) {

                val currentPos = player?.currentPosition ?: 0L
                seekBar.progress = currentPos.toInt()

                val secondes = currentPos / 1000
            // Transformer les secondes en LocalTime (  24*60*60 )
                tempsAffiche = LocalTime.ofSecondOfDay(secondes % 86400)
                temps.text = tempsAffiche.format(format)

        }
    }
    //deserialisation
    // lit l'objet Sauvegarde du fichier et remet les variables comme elles étaient
    fun deserialisation(){
        try {
            var fis = openFileInput("fichier.ser")
            var ois = ObjectInputStream(fis)
            ois.use{
                val objetSauvegarde = ois.readObject() as Sauvegarde
                position = objetSauvegarde.index
                artists = objetSauvegarde.artists
                titres  = objetSauvegarde.titres
                audios  = objetSauvegarde.audios
                pochettes = objetSauvegarde.pochettes
                tempsSauvegarde = objetSauvegarde.tempsMs

            }
        } catch (e: Exception) {
            position = 0
            print("desserialisation ne marche pas ")
        }


    }
}