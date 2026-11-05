package com.kawtar.tp1
// l'ecran principal dse l'application, qui gere l'acceuil, l'identification de 'utilisateur, de la selection de l'artiste
//pour aller a la deuxieme activité d'une liste des chansons de cet artist
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.ObjectInputStream

class MainActivity : AppCompatActivity() {
    lateinit var bonjour : TextView
    lateinit var limpBizkit : Button
    lateinit var korn : Button
    lateinit var nirvana : Button
    lateinit var sindentifier : Button
    lateinit var lanceur: ActivityResultLauncher<Intent>
    var u : Utilisateur? = null
    lateinit var ec: Ecouteur
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        nirvana = findViewById(R.id.artist1)
        limpBizkit = findViewById(R.id.artist2)
        korn = findViewById(R.id.artist3)
        sindentifier = findViewById(R.id.ident)
        bonjour = findViewById(R.id.textView)

        // création du lanceur de boomerang, objet sera appelé au retour du boomerang dans cette classe
        lanceur = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
            CallBackUtilisateur()
        )

        sindentifier.setOnClickListener { lanceur.launch(Intent(this@MainActivity,
            IdentificationlActivity::class.java)) }

        ec = Ecouteur()
        nirvana.setOnClickListener(ec)
        limpBizkit.setOnClickListener(ec)
        korn.setOnClickListener(ec)
        // si un fichier de sauvegarde existe, on rouvre directement le lecteur
        try {
            val fis = openFileInput("fichier.ser")
            val ois = ObjectInputStream(fis)

            ois.use {
                ois.readObject() as Sauvegarde
                startActivity(Intent(this, LireActivity::class.java))
            }
        }
        catch (e: Exception){}
    }
    inner class Ecouteur : View.OnClickListener {
        override fun onClick(v: View?) {
            var artist = ""
            if(v == nirvana ){
                artist = "nirvana"
            }else if (v == limpBizkit){
                artist = "limpBizkit"
            }else{
                artist = "korn"
            }
            val i = Intent(this@MainActivity, AfficherActiviy::class.java)
            i.putExtra("artist",artist)
            startActivity(i)
        }

    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable("utilisateur", u)
    }
    inner class CallBackUtilisateur: ActivityResultCallback<ActivityResult>{
        // retour du boom, vérifier si  RESULT_OK puis on lit Utilisateur
        override fun onActivityResult(result: ActivityResult) {
            if(result.resultCode == RESULT_OK){
                val intentRetour = result.data  // retourne l'intent
                if(intentRetour != null){
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
                        u = intentRetour.getSerializableExtra("utilisateur", Utilisateur::class.java)
                    }
                    else {
                        u = intentRetour.getSerializableExtra("utilisateur") as Utilisateur
                    }
                    bonjour.text =  ("Bonjour ${u?.prenom} ${u?.nom} !")
                }
            }
        }

    }

}