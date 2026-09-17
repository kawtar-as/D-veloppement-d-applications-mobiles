package com.kawtar.annexe4

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    lateinit var connaitre: Button;
    lateinit var text: TextView;
    lateinit var lanceur: ActivityResultLauncher<Intent>;

    var util: Utilisateur? = null;  // soit utilisateur soit null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        connaitre = findViewById(R.id.connaitre);
        text = findViewById(R.id.bonjour);

       lanceur = registerForActivityResult(ActivityResultContracts.StartActivityForResult(),CallBackUtilisateur())
        connaitre.setOnClickListener { lanceur.launch(Intent(this@MainActivity,
            IdentificationActivity::class.java)) }
        // en cas d'avoir tourné le telephone ou changer un parametre sur e tel comme la langue etc
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//            util = savedInstanceState?.getSerializable("util",Utilisateur::class.java )
//        }else {
//            util = savedInstanceState?.getSerializable("util" )  as Utilisateur?
//            text.text= ("Bonjour ${util?.prenom?:""} ${util?.nom?:""}")
//        }
    }

//    override fun onSaveInstanceState(outState: Bundle) {
//        super.onSaveInstanceState(outState)
//        outState.putSerializable("util", util)
//    }

    inner class CallBackUtilisateur: ActivityResultCallback<ActivityResult> {
        // c'est la que le boomerang reviendra pcq on est tjrs dans l'activité initiale
        override fun onActivityResult(result: ActivityResult) {
            if(result.resultCode == RESULT_OK){
                val intentRetour = result.data // retourne l'intent
                if(intentRetour != null){
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        util = intentRetour.getSerializableExtra("util",Utilisateur::class.java)

                    }
                    else {
                        util = intentRetour.getSerializableExtra("util") as Utilisateur?
                    }
                    text.text= ("Bonjour ${util?.prenom} ${util?.nom}")
                }
            }
        }
    }

}