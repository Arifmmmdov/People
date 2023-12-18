package com.example.people.helper

import android.app.AlertDialog
import android.content.Context
import dagger.Provides
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class FilterDialog {

    interface FilterDialogListener {
        fun onItemsSelected(selectedItems: List<String>)
    }

    fun showSelectListDialog(
        title: String,
        context: Context,
        items: List<String>,
        checkedItems: BooleanArray,
        listener: FilterDialogListener,
    ) {
        val selectedItems = ArrayList<String>()

        val builder = AlertDialog.Builder(context)
        builder.setTitle(title)
            .setMultiChoiceItems(items.toTypedArray(), checkedItems) { _, which, isChecked ->
                if (isChecked) {
                    selectedItems.add(items[which])
                } else if (selectedItems.contains(items[which])) {
                    selectedItems.remove(items[which])
                }
            }
            .setPositiveButton("OK") { _, _ ->
                listener.onItemsSelected(selectedItems)
            }
            .setNegativeButton("Cancel", null)

        val dialog = builder.create()
        dialog.show()
    }
}