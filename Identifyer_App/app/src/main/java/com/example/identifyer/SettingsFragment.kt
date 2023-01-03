package com.example.identifyer

import android.os.Bundle
import android.view.Menu
import androidx.preference.PreferenceFragmentCompat

public class SettingsFragment : PreferenceFragmentCompat() {
    //override fun which needs to be implemented and points at the fragments xml
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.pref_settings)
    }

    //nothing added here
    override fun onOptionsMenuClosed(menu: Menu) {
        super.onOptionsMenuClosed(menu)
    }
}