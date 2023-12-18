package com.example.people.helper

import android.app.AlertDialog
import android.content.Context
import com.example.people.R
import com.example.people.extensions.toArrayList

class FilterDialog {

    interface FilterDialogListener {
        fun onItemsSelected(selectedItems: List<String>)
    }

    fun showSelectListDialog(
        title: String,
        context: Context,
        items: List<String>,
        selectedList: List<String>?,
        listener: FilterDialogListener,
    ) {

        val selectedArrayList = selectedList.toArrayList()
        val checkedItems = getCheckedItems(items, selectedList)

        val builder = AlertDialog.Builder(context)
        builder.setTitle(title)
            .setMultiChoiceItems(items.toTypedArray(), checkedItems) { _, which, isChecked ->
                if (isChecked) {
                    selectedArrayList.add(items[which])
                } else if (selectedArrayList.contains(items[which])) {
                    selectedArrayList.remove(items[which])
                }
            }
            .setPositiveButton(R.string.ok) { _, _ ->
                listener.onItemsSelected(selectedArrayList)
            }
            .setNegativeButton(R.string.cancel, null)

        val dialog = builder.create()
        dialog.show()
    }

    private fun getCheckedItems(
        items: List<String>,
        selectedItems: List<String>?,
    ): BooleanArray {
        return items.map {
            it in (selectedItems ?: arrayListOf())
        }.toBooleanArray()
    }
}