package com.anandy.motoechargetracker.ui.common

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.anandy.motoechargetracker.R
import com.anandy.motoechargetracker.basicDiffUtil
import kotlinx.coroutines.launch
import java.lang.Exception

abstract class AsyncExpandableRecyclerViewAdapter<P, CH>(private val itemsLoader: AsyncItemLoad<P, CH>) :
    RecyclerView.Adapter<AsyncViewHolder>(), Scope by Scope.Impl() {

    var expandFirstGroup = true

    protected var items: List<Row<P, CH>> by basicDiffUtil(
        emptyList()
    )

    init {
        initScope()
    }

    abstract fun createParentViewHolder(parent: ViewGroup): AsyncViewHolder
    abstract fun bindParentViewHolder(
        holder: AsyncViewHolder,
        parentItem: P
    )

    abstract fun createChildViewHolder(parent: ViewGroup): AsyncViewHolder
    abstract fun bindChildViewHolder(
        holder: AsyncViewHolder,
        rowIndex: Int,
        itemIndex: Int
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsyncViewHolder {
        val viewHolder: AsyncViewHolder
        if (viewType == 0) {
            viewHolder = createParentViewHolder(parent)
            viewHolder.parentViewHolder = true
        } else {
            viewHolder = createChildViewHolder(parent)
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: AsyncViewHolder, position: Int) {
        if (holder.parentViewHolder) {
            val row = findItemFromPosition<Row<P, CH>>(position)
            bindParentViewHolder(holder, row.parent)
            holder.itemView.setOnClickListener { onParentClicked(holder, row, position) }
            if (position == 0 && expandFirstGroup && !row.expanded) {
                onParentClicked(holder, row, position)
            }
        } else {
            val itemPair = findItemFromPosition<Pair<Int, Int>>(position)
            bindChildViewHolder(holder, itemPair.first, itemPair.second)
        }

    }

    private fun <R> findItemFromPosition(position: Int): R {
        var rowCount = 0
        for ((rowIndex, item) in items.withIndex()) {
            if (rowCount == position) {
                return item as R
            }
            rowCount++
            for (index in item.childs.indices) {
                if (rowCount == position) {
                    return Pair(rowIndex, index) as R
                }
                rowCount++
            }
        }
        throw Exception("Item not found in $position")
    }

    private fun onParentClicked(
        holder: AsyncViewHolder,
        row: Row<P, CH>,
        position: Int
    ) {
        if (row.expanded) {
            val childSize = row.childs.size
            (row.childs as ArrayList).clear()
            notifyItemRangeRemoved(position + 1, childSize)
            holder.arrow?.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down)
            row.expanded = false
        } else {
            //holder.progressBar?.indeterminateDrawable?.setColorFilter(BlendModeColorFilter(0, BlendMode.MULTIPLY))
            holder.progressBar?.visibility = View.VISIBLE
            holder.arrow?.visibility = View.GONE
            launch {
                row.childs = itemsLoader.onLoadChildRecords(row.parent)
                notifyItemRangeInserted(position + 1, row.childs.size)
                holder.progressBar?.visibility = View.GONE
                holder.arrow?.visibility = View.VISIBLE
                holder.arrow?.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up)
                row.expanded = true
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (findItemFromPosition<Any>(position) is Row<*, *>) 0 else 1
    }

    override fun getItemCount(): Int = items.size + items.sumBy { it.childs.size }

    fun setParentRecords(records: List<P>) {
        val rows = ArrayList<Row<P, CH>>()
        records.forEach { record ->
            run {
                rows.add(Row(record))
            }
        }
        items = rows
    }

    fun notifyChildRemoved(child: CH) {
        var position = 0
        var removed = false
        for (item in items) {
            position++
            for (ch in item.childs) {
                if (child == ch) {
                    (item.childs as ArrayList).remove(ch)
                    removed = true
                    break
                }
                position++
            }
            if (removed) {
                break
            }
        }
        notifyItemRemoved(position)
    }
}

interface AsyncItemLoad<P, CH> {
    suspend fun onLoadChildRecords(parent: P): List<CH>
}

abstract class AsyncViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    var parentViewHolder = false
    var progressBar: ProgressBar? = view.findViewWithTag("progressBar")
    var arrow: ImageView? = view.findViewWithTag("arrow")
}

class Row<P, CH>(val parent: P) {
    var childs: List<CH> = ArrayList<CH>()
    var expanded = false
}