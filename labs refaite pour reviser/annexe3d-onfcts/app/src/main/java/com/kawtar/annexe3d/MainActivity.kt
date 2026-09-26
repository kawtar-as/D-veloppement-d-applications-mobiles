package com.kawtar.annexe3d

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.util.Log.i
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.LifecycleOwner
import kotlin.math.log

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        Log.i("cycle","onCreate")
        print("oncreate")
    }



    override fun onRestart() {
        super.onRestart()
        Log.i("cycle","onRestart")
        print("onRestart")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("cycle","onDestroy")
        print("onDestroy")
    }

    override fun onStart() {
        super.onStart()
        Log.i("cycle","onStart")
        print("onStart")
    }


    override fun onPause() {
        super.onPause()
        Log.i("cycle","onPause")
        print("onPause")
    }

    override fun onResume() {
        super.onResume()
        Log.i("cycle","onResume")
        print("onResume")
    }

    override fun onStop() {
        super.onStop()
        Log.i("cycle","onStop")
        print("onStop")
    }
}