package com.example.locationfinder.ui.location

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.locationfinder.BR
import com.example.locationfinder.R
import com.example.locationfinder.constant.McConstants.DIALOG_WIDTH
import com.example.locationfinder.databinding.FragmentLocationBinding
import com.example.locationfinder.db.McItemEntity
import com.example.locationfinder.ui.base.BaseFragment
import kotlin.random.Random

/**
 * LocationFragment
 */
class LocationFragment :
    BaseFragment<FragmentLocationBinding, LocationViewModel>(),
    LocationNavigator {

    private val vm: LocationViewModel by viewModels()
    override fun getLayoutId() = R.layout.fragment_location
    override fun getViewModel(): LocationViewModel = vm
    override fun getBindingVariable(): Int = BR.viewModel

    private val locationRecyclerviewAdapter: LocationRecyclerviewAdapter by lazy {
        LocationRecyclerviewAdapter()
    }
    private lateinit var dialog: Dialog
    private lateinit var anim: Animation

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        locationRecyclerviewAdapter = LocationRecyclerviewAdapter()
        locationRecyclerviewAdapter.likedClick = object : LocationRecyclerviewAdapter.LikedClick {
            override fun onClick(view: View, item: McItemEntity) {
                if (item.favorite) {
                    view.findViewById<ImageView>(R.id.iv_liked)
                        .setImageResource(R.drawable.ic_gray_star)
                } else {
                    view.findViewById<ImageView>(R.id.iv_liked)
                        .setImageResource(R.drawable.ic_yellow_star)
                }
                item.favorite = !item.favorite
                vm.updateMcItemEntity(item)
            }
        }

        initRecyclerview()
        searchDialogSetting()

        vm.savedEntityList.observe(viewLifecycleOwner, { list ->
            binding().emptyListBackground.visibility = View.VISIBLE
            if (list.isNotEmpty()) {
                locationRecyclerviewAdapter.differ.submitList(list)
                binding().emptyListBackground.visibility = View.GONE
            }
        })

        binding().apply {
            viewModel = vm
            savedRecyclerview.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = locationRecyclerviewAdapter
            }
        }
        setRandomPick()
    }

    private fun setRandomPick() {
        binding().randomPickButton.setOnClickListener {
            var savedItems = vm.savedResult.value
            if (savedItems != null) {
                val num = Random.nextInt(savedItems.size)
                val title = dialog.findViewById<TextView>(R.id.edit)
                title.apply {
                    text = savedItems[num].placeName
                    animation = anim
                }
                dialog.show()
                anim.start()
            } else {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.inform_no_saved_list),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    /**
     * initRecyclerview
     */
    private fun initRecyclerview() {
        binding().savedRecyclerview.apply {
            adapter = locationRecyclerviewAdapter
            addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))
            ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    vm.deleteMcItemEntity(locationRecyclerviewAdapter.differ.currentList[viewHolder.adapterPosition])
                    Log.d(
                        "log : ",
                        "onSwiped ${locationRecyclerviewAdapter.differ.currentList[viewHolder.adapterPosition]}"
                    )
//                    locationViewModel.getSavedEntityList()
                }
            }).attachToRecyclerView(this)
        }
    }

    /**
     * searchDialogSetting
     */
    private fun searchDialogSetting() {
        dialog = Dialog(requireContext())

        dialog.apply {
            setContentView(R.layout.fragment_dialog)
            window?.setLayout(DIALOG_WIDTH, DIALOG_WIDTH)
        }

        anim = AnimationUtils.loadAnimation(context, R.anim.dialog_text_anim)
        val dialogButton = dialog.findViewById<TextView>(R.id.ok_button)
        dialogButton.setOnClickListener {
            dialog.dismiss()
            anim.cancel()
        }
    }

    /**
     * Event
     */
    open class Event<out T>(private val content: T) {

        var hasBeenHandled = false
            private set // Allow external read but not write

        /**
         * Returns the content and prevents its use again.
         */
        fun getContentIfNotHandled(): T? {
            return if (hasBeenHandled) {
                null
            } else {
                hasBeenHandled = true
                content
            }
        }

        /**
         * Returns the content, even if it's already been handled.
         */
        fun peekContent(): T = content
    }

}