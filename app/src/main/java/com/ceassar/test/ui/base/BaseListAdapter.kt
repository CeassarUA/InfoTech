package com.ceassar.test.ui.base

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ceassar.test.ui.base.click.ClicksFilter
import com.ceassar.test.ui.base.click.debounce
import kotlinx.android.extensions.LayoutContainer

abstract class BaseListAdapter<T, H : BaseListAdapter.BaseViewHolder>(
    diffCallback: DiffUtil.ItemCallback<T>,
) : ListAdapter<T, H>(diffCallback) {

    protected var recyclerView: RecyclerView? = null
    private val clicksFilter = ClicksFilter()
    var provider: ((pos: Int) -> T)? = null
    fun View.onClick(durationMillis: Long, action: (view: View) -> Unit) {
        setOnClickListener {
            clicksFilter.debounce(durationMillis, it, action)
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    override fun onCreateViewHolder(parent: ViewGroup, @LayoutRes viewType: Int): H {
        return createViewHolder(inflate(parent, viewType), viewType)
    }

    internal abstract fun createViewHolder(view: View, @LayoutRes viewType: Int): H

    @LayoutRes
    abstract override fun getItemViewType(position: Int): Int

    override fun onBindViewHolder(holder: H, position: Int) = holder.bind(position)

    override fun onBindViewHolder(holder: H, position: Int, payloads: MutableList<Any>) {
        if (payloads.isEmpty()) holder.bind(position)
        else holder.bind(position, payloads)
    }

    protected fun BaseViewHolder.getItem(): T? {
        return if (bindingAdapterPosition != RecyclerView.NO_POSITION)
            provider?.invoke(bindingAdapterPosition) ?: getItem(bindingAdapterPosition)
        else null
    }

    protected fun getItemSafe(position: Int): T? {
        return if (position != RecyclerView.NO_POSITION)
            provider?.invoke(position) ?: this@BaseListAdapter.getItem(position)
        else null
    }


    abstract class BaseViewHolder(val view: View) : RecyclerView.ViewHolder(view), LayoutContainer {
        override val containerView: View?
            get() = itemView

        abstract fun bind(pos: Int)
        open fun bind(pos: Int, payloads: MutableList<Any>) {}
        protected val context: Context by lazy { itemView.context }
    }

    companion object {
        fun inflate(parent: ViewGroup, @LayoutRes res: Int): View {
            return LayoutInflater.from(parent.context).inflate(res, parent, false)
        }

        class Diffuser<T : Any> : DiffUtil.ItemCallback<T>() {
            override fun areItemsTheSame(
                oldItem: T,
                newItem: T
            ): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(
                oldItem: T,
                newItem: T
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}

