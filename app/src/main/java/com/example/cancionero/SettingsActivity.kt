package com.example.cancionero

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.preference.SwitchPreferenceCompat
import com.example.cancionero.PreferenceUtils.saveChordVisibilityPreference


class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_settings)




        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        supportFragmentManager
            .beginTransaction()
            .replace(R.id.settings_container, SettingsFragment()) // Carga el fragmento de preferencias
            .commit()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home){
            finish()
            return true
        }

        return super.onOptionsItemSelected(item)
    }
}