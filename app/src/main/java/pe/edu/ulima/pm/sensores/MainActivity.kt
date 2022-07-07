package pe.edu.ulima.pm.sensores

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView

class MainActivity : AppCompatActivity(), SensorEventListener {
    private lateinit var mSensorManager : SensorManager
    private lateinit var tviValoresSensor : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tviValoresSensor = findViewById(R.id.tviValoresSensor)

        mSensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        val sensorList =  mSensorManager.getSensorList(Sensor.TYPE_ALL)
        sensorList.forEach {
            Log.i("MainActivity", "${it.name} ${it.stringType}")
        }

    }

    override fun onSensorChanged(sensorEvent: SensorEvent?) {
        tviValoresSensor.text = "Presion (hPa): ${sensorEvent!!.values[0]}"
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {}

    override fun onResume() {
        super.onResume()
        val sensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE)
        if ( sensor != null) {
            // Si hay un sensor de Luz
            mSensorManager.registerListener(
                this,
                sensor,
                SensorManager.SENSOR_DELAY_NORMAL
            )
        } else{
            // No hay un sensor
            Log.e("MainActivity", "No hay sensor de este tipo")
        }
    }

    override fun onPause() {
        super.onPause()
        mSensorManager.unregisterListener(this)
    }
}