package com.example.cancionero.settings

import android.app.AlertDialog
import android.app.ProgressDialog
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.example.cancionero.MainActivity
import com.example.cancionero.R
import com.example.cancionero.htmls.HtmlLoader
import com.example.cancionero.htmls.HtmlUpdater

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        val manualUpdatePref = findPreference<Preference>("manual_updates")
        manualUpdatePref?.setOnPreferenceClickListener {
            val progressDialog = ProgressDialog(requireContext()).apply {
                setMessage("Buscando actualizaciones...")
                setCancelable(false)
                show()
            }

            HtmlUpdater.checkForUpdates(requireContext()) { isUpdateAvailable ->
                requireActivity().runOnUiThread {
                    progressDialog.dismiss()

                    if (isUpdateAvailable) {
                        AlertDialog.Builder(requireContext())
                            .setTitle("Actualización disponible")
                            .setMessage("Existe una nueva versión de los cantos. ¿Desea descargarla?")
                            .setPositiveButton("Sí") { _, _ ->
                                val downloadDialog = ProgressDialog(requireContext()).apply {
                                    setMessage("Descargando nuevos cantos...")
                                    setCancelable(false)
                                    show()
                                }

                                HtmlUpdater.forceDownload(requireContext()) { success ->
                                    requireActivity().runOnUiThread {
                                        downloadDialog.dismiss()

                                        if (success) {
                                            val updatedFiles = HtmlLoader.getLocalHtmlFiles(requireContext())
                                            val activity = requireActivity()

                                            if (activity is MainActivity) {
                                                activity.refreshPagerAdapter(updatedFiles)
                                            }

                                            Toast.makeText(
                                                requireContext(),
                                                "Cantos actualizados correctamente",
                                                Toast.LENGTH_LONG
                                            ).show()
                                        } else {
                                            Toast.makeText(
                                                requireContext(),
                                                "Error al descargar los cantos",
                                                Toast.LENGTH_LONG
                                            ).show()
                                        }
                                    }
                                }
                            }
                            .setNegativeButton("No") { dialog, _ -> dialog.dismiss() }
                            .show()
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Ya tienes la última versión",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }

            true
        }




    }
}
