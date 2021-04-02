package com.example.locationfinder.ui.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.example.locationfinder.BR
import com.example.locationfinder.R
import com.example.locationfinder.constant.McConstants.CAMERA_ZOOM_LEVEL
import com.example.locationfinder.constant.McConstants.PERMISSION_PREFERENCE_KEY
import com.example.locationfinder.constant.McConstants.PERMISSION_REQUEST_CODE
import com.example.locationfinder.databinding.FragmentMapBinding
import com.example.locationfinder.ui.base.BaseFragment
import com.example.locationfinder.ui.map.search.SearchFragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng

/**
 * MapFragment
 */
@SuppressLint("MissingPermission")
class MapFragment :
    BaseFragment<FragmentMapBinding, MapViewModel>(),
    MapNavigator,
    GoogleMap.OnMyLocationClickListener,
    GoogleMap.OnMyLocationButtonClickListener {

    private val vm: MapViewModel by viewModels()
    override fun getLayoutId() = R.layout.fragment_map
    override fun getViewModel(): MapViewModel = vm
    override fun getBindingVariable(): Int = BR.viewModel

    private lateinit var fuseLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationManager: LocationManager
    private var map: GoogleMap? = null
    private lateinit var mapFragment: SupportMapFragment
    private var currentLocation = LatLng(127.toDouble(), 127.toDouble())

    private val permissions = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapFragment = childFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment

        binding().saveItemButton.setOnClickListener {
            val dialogFragment = SearchFragment.newInstance(currentLocation)
            dialogFragment.show(childFragmentManager, "fragment_search")
        }

        initMap()
    }

    private fun updateUI() {
        getCurrentLocation()
        mapFragment.run {
            getMapAsync(callback)
            onStop()
            onResume()
        }
    }

    private fun initMap() {
        mapFragment.getMapAsync(callback)
    }

    private val callback = OnMapReadyCallback { googleMap ->
        map = googleMap
        locationManager = context?.getSystemService(LOCATION_SERVICE) as LocationManager
        if (checkAllPermissionsGranted(permissions)) {
            googleMap.apply {
                isMyLocationEnabled = true
                setOnMyLocationButtonClickListener(this@MapFragment)
                setOnMyLocationClickListener(this@MapFragment)
            }
            getCurrentLocation()
        }
    }

    /**
     * moveCamera
     */
    private fun moveCamera(latLng: LatLng, zoom: Float) {
        map?.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom))
    }

    /**
     * getCurrentLocation
     */
    private fun getCurrentLocation() {
        fuseLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireActivity())
        if (checkAllPermissionsGranted(permissions)) {
            fuseLocationProviderClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    location?.let {
                        currentLocation = LatLng(it.latitude, it.longitude)
                        moveCamera(currentLocation, CAMERA_ZOOM_LEVEL)
                    }
                }
        }
    }

    /**
     * onMyLocationClick
     */
    override fun onMyLocationClick(location: Location) {
        currentLocation = LatLng(location.latitude, location.longitude)
    }

    /**
     * onMyLocationButtonClick
     */
    override fun onMyLocationButtonClick(): Boolean {
        return false
    }

    /**
     * onResume
     */
    override fun onResume() {
        super.onResume()
        isLocationPermissionGranted()
    }

    /**
     * checkAllPermissionsGranted
     */
    private fun checkAllPermissionsGranted(permissions: Array<String>): Boolean {
        var hasGranted = true
        for (permission in permissions) {
            hasGranted = (hasGranted && ContextCompat.checkSelfPermission(
                requireContext().applicationContext, permission
            ) == PackageManager.PERMISSION_GRANTED)
        }
        return hasGranted
    }

    /**
     * isLocationPermissionGranted
     */
    private fun isLocationPermissionGranted(): Boolean {
        val preference = requireActivity().getPreferences(Context.MODE_PRIVATE)
        val isFirstCheck = preference.getBoolean(PERMISSION_PREFERENCE_KEY, true)
        if (!checkAllPermissionsGranted(permissions)) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    requireActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
                showPermissionConfirmDialog()
            } else {
                if (isFirstCheck) {
                    preference.edit().putBoolean(PERMISSION_PREFERENCE_KEY, false)
                        .apply()
                    ActivityCompat.requestPermissions(
                        requireActivity(),
                        permissions,
                        PERMISSION_REQUEST_CODE
                    )

                } else {
                    showPermissionConfirmDialog()
                }
            }
            return false
        } else {
            updateUI()
            return true
        }
    }

    /**
     * showPermissionConfirmDialog
     */
    private fun showPermissionConfirmDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage(getString(R.string.inform_the_permission_required))
            .setPositiveButton(
                getString(R.string.positive_dialog_button_text)
            ) { dialog, _ ->
                val intent = Intent()
                intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                val uri = Uri.fromParts("package", requireActivity().packageName, null)
                intent.data = uri
                startActivity(intent)
                dialog.dismiss()
            }
            .setNegativeButton(
                getString(R.string.negative_dialog_button_text)
            ) { dialog, _ ->
                dialog.dismiss()
                requireActivity().finish()
            }
        builder.create().show()
    }
}
