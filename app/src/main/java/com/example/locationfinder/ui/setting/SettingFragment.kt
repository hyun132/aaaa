package com.example.locationfinder.ui.setting

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.locationfinder.BR
import com.example.locationfinder.R
import com.example.locationfinder.databinding.FragmentSettingBinding
import com.example.locationfinder.ui.base.BaseFragment
import com.google.android.material.snackbar.Snackbar

/**
 * SettingFragment
 */
class SettingFragment :
    BaseFragment<FragmentSettingBinding, SettingViewModel>(),
    SettingNavigator {

    private val settingViewModel: SettingViewModel by viewModels()
    override fun getLayoutId() = R.layout.fragment_setting
    override fun getViewModel() = settingViewModel
    override fun getBindingVariable(): Int = BR.viewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuList = listOf<SettingMenuItem>(
            SettingMenuItem(getString(R.string.open_source_license_title), R.drawable.ic_open_source, SettingOption.OPEN_LICENSE),
            SettingMenuItem(getString(R.string.delete_saved_list_title), R.drawable.ic_trash_bin, SettingOption.DELETE_ALL)
        )

        binding().settingRecyclerview.apply {
            val settingRecyclerviewAdapter = SettingRecyclerviewAdapter()
            settingRecyclerviewAdapter.setOnItemClickListener {
                when (it.menuType) {
                    SettingOption.DELETE_ALL -> showRemoveAllDialog(getViewModel())
                    SettingOption.OPEN_LICENSE -> findNavController().navigate(R.id.action_settingFragment_to_licenseFragment)
                }
            }
            settingRecyclerviewAdapter.differ.submitList(menuList)
            adapter = settingRecyclerviewAdapter
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))
        }
    }

    private fun showRemoveAllDialog(viewModel: SettingViewModel) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage(getString(R.string.ask_remove_saved_list))
            .setPositiveButton(getString(R.string.positive_dialog_button_text)) { dialog, id ->
                viewModel.deleteAll()
                dialog.dismiss()
                showSnackBar(this.requireView())
            }
            .setNegativeButton(R.string.negative_dialog_button_text) { dialog, id ->
                dialog.dismiss()
            }
        builder.create().show()
    }

    private fun showSnackBar(view: View) {
        Snackbar.make(
            view,
            getString(R.string.inform_removed_saved_list), Snackbar.LENGTH_SHORT
        ).show()
    }
}

/**
 * SettingMenuItem
 */
data class SettingMenuItem(val menuTitle: String, val menuImage: Int, val menuType: SettingOption)

/**
 * SettingOption
 */
enum class SettingOption {
    DELETE_ALL,
    OPEN_LICENSE
}