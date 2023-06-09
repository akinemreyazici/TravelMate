package com.works.odev9

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.findNavController
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.works.odev9.databinding.ActivityAppBinding

class AppActivity : AppCompatActivity() {


    private lateinit var binding: ActivityAppBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAppBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = findNavController(R.id.nav_host_fragment_content_app)

        binding.fab.setOnClickListener { view ->
            navController.navigate(R.id.nav_AddPlace)

        }
        navController.addOnDestinationChangedListener{ _,destination,_ ->
            when(destination.id){
                R.id.nav_AddPlace -> binding.fab.hide()
                else -> binding.fab.show()
            }
        }

    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.app, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId)
        {
            R.id.action_logout -> {
                AlertDialog.Builder(this)
                    .setTitle("Uygulamadan çıkış yap")
                    .setMessage("Hesabınızdan çıkış yapmak istediğinize emin misiniz?")
                    .setPositiveButton("Evet") { dialog, which ->
                        FirebaseAuth.getInstance().signOut() // Kullanıcı çıkış yapar
                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    .setNegativeButton("Hayır", null)
                    .show()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}