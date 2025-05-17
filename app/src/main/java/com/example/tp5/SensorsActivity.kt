package com.example.tp5

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tp5.databinding.ActivityMainBinding
import com.example.tp5.databinding.ActivitySensorsBinding

class SensorsActivity : AppCompatActivity(), SensorEventListener {
    private lateinit var binding: ActivitySensorsBinding
    private lateinit var sensorManager: SensorManager
    private lateinit var sensorList: List<Sensor>
    private var currentSensor: Sensor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySensorsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL)

        binding.nombre.text = "Total Sensors: ${sensorList.size}"
        val sensorNames = sensorList.map { it.name }
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, sensorNames)
        binding.nom.adapter = adapter

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.nom.setOnItemClickListener { _, _, position, _ ->
            currentSensor?.let {
                sensorManager.unregisterListener(this, it)
            }
            currentSensor = sensorList[position]
            sensorManager.registerListener(this, currentSensor, SensorManager.SENSOR_DELAY_NORMAL)

            Toast.makeText(this, "Capteur activé: ${currentSensor!!.name}", Toast.LENGTH_SHORT).show()
        }}


    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            val values = it.values

            binding.x.text = "X: ${values[0]}"
            binding.y.text = if (values.size > 1) "Y: ${values[1]}" else ""
            binding.z.text = if (values.size > 2) "Z: ${values[2]}" else ""
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    override fun onPause() {
        super.onPause()
        currentSensor?.let {
            sensorManager.unregisterListener(this, it)
        }
    }


}

