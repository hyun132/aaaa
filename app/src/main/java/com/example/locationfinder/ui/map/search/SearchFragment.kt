package com.example.locationfinder.ui.map.search

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.locationfinder.BR
import com.example.locationfinder.R
import com.example.locationfinder.databinding.FragmentSearchBinding
import com.example.locationfinder.db.McItemEntity.Companion.mapToEntity
import com.example.locationfinder.models.McItemDto
import com.example.locationfinder.repository.SearchResult
import com.example.locationfinder.ui.base.BaseBottomSheetFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.bottomsheet.BottomSheetBehavior

/**
 * SearchFragment
 */

class SearchFragment :
    BaseBottomSheetFragment<FragmentSearchBinding, SearchViewModel>(),
    SearchNavigator {

    private val vm: SearchViewModel by viewModels()
    override fun getLayoutId() = R.layout.fragment_search
    override fun getViewModel() = vm
    override fun getBindingVariable(): Int = BR.viewModel

    private lateinit var currentLocation: LatLng

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentLocation = requireArguments().getParcelable<LatLng>("currentLocation") ?: LatLng(
            127.toDouble(),
            127.toDouble()
        )
        initBottomSheet(view)

        val searchRecyclerviewAdapter = SearchRecyclerviewAdapter()
        searchRecyclerviewAdapter.setOnItemClickListener {
            showAddDialog(it)
        }

        viewDataBinding.apply {
            homeRecyclerview.apply {
                adapter = searchRecyclerviewAdapter
                layoutManager = LinearLayoutManager(context)
                addItemDecoration(
                    DividerItemDecoration(
                        requireContext(),
                        LinearLayoutManager.VERTICAL
                    )
                )
            }
        }

        vm.searchSearchResult.observe(viewLifecycleOwner, { response ->
            when (response) {
                is SearchResult.Success -> {
                    response.data?.let { searchResponse ->
                        searchRecyclerviewAdapter.differ.submitList(searchResponse.documents)
                    }
                }
                is SearchResult.Error -> {
                    //검색결과 없음 이미지 보여주기
                }
                is SearchResult.Loading -> {
                    // 로딩
                }
            }
        })

        viewDataBinding.searchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (!newText.isNullOrBlank()) {
                    vm.getSearchItem(
                        query = newText,
                        posY = currentLocation.latitude,
                        posX = currentLocation.longitude
                    )
                }
                return false
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT)) //추가
    }

    /**
     * initBottomSheet
     */
    private fun initBottomSheet(view: View) {

        dialog?.let {
            val bottomSheet: View = it.findViewById(R.id.design_bottom_sheet)
            bottomSheet.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
        }

        view.post {
            val parent = view.parent as View
            val params = parent.layoutParams as CoordinatorLayout.LayoutParams
            val behavior = params.behavior
            val bottomSheetBehavior = behavior as BottomSheetBehavior<*>?
            bottomSheetBehavior?.peekHeight =
                view.measuredHeight - viewDataBinding.searchToolbar.height
            parent.setBackgroundColor(Color.TRANSPARENT)
        }
    }

    /**
     * showAddDialog
     */
    private fun showAddDialog(mcItem: McItemDto) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage(mcItem.placeName + "을 추가하시겠습니까?")
            .setPositiveButton(
                getString(R.string.positive_dialog_button_text)
            ) { dialog, _ ->
                vm.saveItem(mcItem.mapToEntity())
                dialog.dismiss()
                this.dismiss()
            }
            .setNegativeButton(
                getString(R.string.negative_dialog_button_text)
            ) { dialog, _ ->
                dialog.dismiss()
            }
        builder.create().show()
    }

    companion object {
        fun newInstance(currentLocation: LatLng): SearchFragment {
            val searchFragment = SearchFragment()

            val args = Bundle()
            args.putParcelable("currentLocation", currentLocation)
            searchFragment.arguments = args

            return searchFragment
        }
    }
}