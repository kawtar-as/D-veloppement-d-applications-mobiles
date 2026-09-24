package com.kawtar.annexe6

import android.content.Intent
import android.content.Intent.ACTION_OPEN_DOCUMENT
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    lateinit var ok : Button
    lateinit var image : ImageView
    var lanceur: ActivityResultLauncher<Intent>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        lanceur = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
            CallBackImage()
        )
        ok = findViewById(R.id.button)
        image= findViewById(R.id.imageView)


        ok.setOnClickListener {
            val intent = Intent(ACTION_OPEN_DOCUMENT)
            intent.setType("image/*")
            lanceur?.launch(intent)
        }
    }
    inner class CallBackImage : ActivityResultCallback<ActivityResult> {
        override fun onActivityResult(result: ActivityResult) {
            var intent = result.data // retourne l'intent
            var uri = intent!!.data // uri on peut mettre un ?
            image.setImageURI(uri)

        }
    }

}