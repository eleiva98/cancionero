package com.example.cancionero

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        val switchPreference = findPreference<SwitchPreferenceCompat>("show_chords_switch")
        switchPreference?.setOnPreferenceChangeListener { _, newValue ->
            val visible = newValue as Boolean
            PreferenceUtils.saveChordVisibilityPreference(
            requireContext(),
                visible
            )
            true
        }

    }
}