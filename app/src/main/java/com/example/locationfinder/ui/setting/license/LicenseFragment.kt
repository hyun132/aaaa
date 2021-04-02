package com.example.locationfinder.ui.setting.license

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.locationfinder.BR
import com.example.locationfinder.R
import com.example.locationfinder.databinding.FragmentLicenseBinding
import com.example.locationfinder.ui.base.BaseFragment
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream

/**
 * LicenseFragment
 */
class LicenseFragment :
    BaseFragment<FragmentLicenseBinding, LicenseViewModel>(),
    LicenseNavigator {

    private val vm: LicenseViewModel by viewModels()
    override fun getLayoutId() = R.layout.fragment_license
    override fun getViewModel() = vm
    override fun getBindingVariable(): Int = BR.viewModel

    private val fileNames = arrayOf(R.raw.lottie, R.raw.okhttp, R.raw.retrofit)
    private val contents = arrayListOf<LicenseData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        for (filename in fileNames) {
            readTxtFile(requireContext(), filename).let { contents.add(it) }
        }
    }

    private fun readTxtFile(context: Context, resId: Int): LicenseData {
        var result = ""
        var filename = ""
        val txtResource: InputStream = context.resources.openRawResource(resId)
        val byteArrayOutputStream =
            ByteArrayOutputStream()
        var i: Int
        try {
            i = txtResource.read()
            while (i != -1) {
                byteArrayOutputStream.write(i)
                i = txtResource.read()
            }
            result = String(byteArrayOutputStream.toByteArray()).trim(' ')
            filename = requireContext().resources.getResourceName(resId).split('/')[1]
            txtResource.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return LicenseData(filename, result)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeButtonEnabled(true)
            title = getString(R.string.open_source_license_title)
        }

        binding().lisenceRecyclerview.apply {
            adapter = LicenseAdapter(contents)
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    /**
     * LicenseData
     */
    data class LicenseData(
        val title: String,
        val contents: String
    )
}