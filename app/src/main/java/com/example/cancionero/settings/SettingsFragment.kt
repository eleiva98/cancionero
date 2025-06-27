@file:Suppress("DEPRECATION")

package com.example.cancionero.settings

import android.app.ProgressDialog
import android.os.Bundle
import android.widget.Toast
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import com.example.cancionero.R
import com.example.cancionero.htmls.FileDownloader


class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        val switchPreference = findPreference<SwitchPreferenceCompat>("show_chords_switch")
        val manualUpdatePref = findPreference<Preference>("manual_update")


        switchPreference?.setOnPreferenceChangeListener { _, newValue ->
            val visible = newValue as Boolean
            PreferenceUtils.saveChordVisibilityPreference(
                requireContext(),
                visible
            )
            true
        }

        manualUpdatePref?.setOnPreferenceClickListener {
            val progressDialog = ProgressDialog(requireContext()).apply {
                setMessage("Descargando archivos...")
                setCancelable(false)
                show()
            }


            // Lógica para descargar archivos
            FileDownloader.downloadHtmlFiles(requireContext()) {
                Toast.makeText(requireContext(), "Actualización completada", Toast.LENGTH_SHORT).show()
            }
            true
        }

    }
}