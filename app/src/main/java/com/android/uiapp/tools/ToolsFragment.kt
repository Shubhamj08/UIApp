package com.android.uiapp.tools

import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraManager
import android.net.wifi.WifiManager
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import androidx.databinding.DataBindingUtil
import com.android.uiapp.R
import com.android.uiapp.databinding.ToolsFragmentBinding

@Suppress("DEPRECATION")
class ToolsFragment : Fragment() {

    private lateinit var cameraId: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<ToolsFragmentBinding>(inflater, R.layout.tools_fragment, container, false)

        binding.wifiToggle.setOnClickListener {
            val wifi: WifiManager = activity?.getSystemService(Context.WIFI_SERVICE) as WifiManager
            wifi.isWifiEnabled = !wifi.isWifiEnabled
        }

        binding.bluetoothToggle.setOnClickListener {
            val bluetooth: BluetoothManager =
                activity?.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager

            try {
                if (bluetooth.adapter.isEnabled) {
                    bluetooth.adapter.disable()
                } else {
                    bluetooth.adapter.enable()
                }
            } catch (e: Exception) {
                Toast.makeText(activity, "bluetooth not available", Toast.LENGTH_SHORT).show()
            }

        }

        binding.torchToggle.setOnClickListener {
            val isFlashAvailable =
                activity?.applicationContext?.packageManager?.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)
            val cameraManager = activity?.getSystemService(Context.CAMERA_SERVICE) as CameraManager
            if (binding.torchToggle.isChecked) {
                if (!isFlashAvailable!!) {
                    Toast.makeText(activity, "Flash not available", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                try {
                    cameraId = cameraManager.cameraIdList[0]
                } catch (e: CameraAccessException) {
                    Toast.makeText(activity, "${e.message}", Toast.LENGTH_LONG).show()
                    return@setOnClickListener
                }
                cameraManager.setTorchMode(cameraId, true)
            } else {
                try {
                    cameraManager.setTorchMode(cameraId, false)
                } catch (e: Exception) {

                }
            }

        }

        val brightness =
            Settings.System.getInt(activity?.contentResolver, Settings.System.SCREEN_BRIGHTNESS, 0)

        val canWriteSettings = Settings.System.canWrite(activity?.applicationContext)
        if(!canWriteSettings){
            val intent = Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS)
            startActivity(intent)
        }

        binding.brightnessToggle.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                Settings.System.putInt(
                    activity?.applicationContext?.contentResolver,
                    Settings.System.SCREEN_BRIGHTNESS,
                    progress
                )
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })

        return binding.root
    }

}