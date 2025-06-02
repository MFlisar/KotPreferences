package com.michaelflisar.kotpreferences.demo.utils

import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.SaverScope
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList

object ListSaverUtil {

    fun <Original, Saveable> listSaverKeepEmpty(
        save: SaverScope.(value: Original) -> List<Saveable>,
        restore: (list: List<Saveable>) -> Original?
    ): Saver<Original, Any> = @Suppress("UNCHECKED_CAST") (Saver(
        save = {
            val list = save(it)
            for (index in list.indices) {
                val item = list[index]
                if (item != null) {
                    require(canBeSaved(item))
                }
            }
            ArrayList(list)
        },
        restore = restore as (Any) -> Original?
    ))

    fun <T : Any> listSaverKeepEntryStateList() = listSaverKeepEmpty<SnapshotStateList<T>, T>(
        save = { stateList ->
            if (stateList.isNotEmpty()) {
                val first = stateList.first()
                if (!canBeSaved(first)) {
                    throw IllegalStateException("${first::class} cannot be saved. By default only types which can be stored in the Bundle class can be saved.")
                }
                stateList.toList()
            } else {
                emptyList()
            }
        },
        restore = {
            it.toMutableStateList()
        }
    )
}