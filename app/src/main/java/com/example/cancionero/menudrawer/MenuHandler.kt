package com.example.cancionero.menudrawer

import android.content.Context
import android.view.MenuInflater
import android.view.View
import android.widget.Button
import android.widget.PopupMenu
import com.example.cancionero.R

class MenuHandler(private val context: Context, button: Button) {
    init {
        button.setOnClickListener{showPopupMenu(it)}
    }

    private fun showPopupMenu(view: View)
        {
        val popupMenu = PopupMenu(context, view)
        val inflater: MenuInflater = popupMenu.menuInflater

        inflater.inflate(R.menu.more_menu, popupMenu.menu)

         /*   try {
                val fieldPopup = PopupMenu::class.java.getDeclaredField("mPopup")
                fieldPopup.isAccessible = true
                val menuPopupHelper = fieldPopup.get(popupMenu)
                val classPopupHelper = Class.forName(menuPopupHelper.javaClass.name)
                val setForceShowIcon = classPopupHelper.getMethod("setForceShowIcon", Boolean::class.java)
                setForceShowIcon.invoke(menuPopupHelper, true)
            } catch (e: Exception) {
                e.printStackTrace()
            }*/

        popupMenu.show()
        }


    }


