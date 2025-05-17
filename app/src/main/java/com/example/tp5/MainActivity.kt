package com.example.tp5

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.tp5.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, SensorEventListener {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    private lateinit var sensorMgr: SensorManager
    private lateinit var monAccelero: Sensor
    private var sensor: Sensor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sensorMgr = getSystemService(SENSOR_SERVICE) as SensorManager


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        binding.appBarMain.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .setAnchorView(R.id.fab).show()
        }

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)

        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        navView.setNavigationItemSelectedListener(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        sensor = when (item.itemId) {
            R.id.nav_Accelerometer -> sensorMgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

            R.id.nav_Gyroscope -> sensorMgr.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
            R.id.nav_magnetometer -> sensorMgr.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
            R.id.nav_proxymity -> sensorMgr.getDefaultSensor(Sensor.TYPE_PROXIMITY)
            R.id.nav_photometer -> sensorMgr.getDefaultSensor(Sensor.TYPE_LIGHT)
            else -> null
        }

        if (sensor != null) {
            sensorMgr.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
            Toast.makeText(this, "Capteur activé", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Capteur non disponible", Toast.LENGTH_SHORT).show()
        }

        return true
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event != null){
            when(event.sensor.type){
                Sensor.TYPE_ACCELEROMETER -> {
                    val x = event.values[0]
                    val y = event.values[1]
                    val z = event.values[2]

                    Log.i("Test Accelero", "$x, $y, $z")

                }
            }
            when(event.sensor.type){
                Sensor.TYPE_GYROSCOPE -> {
                    val x = event.values[0]
                    val y = event.values[1]
                    val z = event.values[2]

                    Log.i("Test gyro", "$x, $y, $z")

                }
            }
            when(event.sensor.type){
                Sensor.TYPE_MAGNETIC_FIELD -> {
                    val x = event.values[0]
                    val y = event.values[1]
                    val z = event.values[2]

                    Log.i("Test magnétomètre", "$x, $y, $z")

                }
            }
            when(event.sensor.type){
                Sensor.TYPE_PROXIMITY -> {
                    val x = event.values[0]
                    Log.i("Test proximity", "$x")

                }
            }
            when(event.sensor.type){
                Sensor.TYPE_LIGHT -> {
                    val x = event.values[0]
                    Log.i("Test luminosité", "$x")

                }
            }
        }
    }


    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    override fun onDestroy() {
        super.onDestroy()
        sensorMgr.unregisterListener(this)
    }


}
