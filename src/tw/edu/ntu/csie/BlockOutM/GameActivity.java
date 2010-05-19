package tw.edu.ntu.csie.BlockOutM;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class GameActivity extends Activity {
	GameCore mCore;
	SensorManager sm;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {

    	super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // Create our Preview view and set it as the content of our
        // Activity
        setContentView(R.layout.main);
        mGLSurfaceView = (GLSurfaceView) findViewById(R.id.GLSurfaceView);
        mGLSurfaceView.setRenderer(new GameRenderer());
        //setContentView(mGLSurfaceView);
        mCore = new GameCore((TextView)findViewById(R.id.text1));
		sm=(SensorManager)getSystemService(Context.SENSOR_SERVICE);
		sm.registerListener(new AccelListener(mCore), sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);


//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    public void onResume() {
//    protected void onResume() {
        // Ideally a game should implement onResume() and onPause()
        // to take appropriate action when the activity looses focus
        super.onResume();
        mGLSurfaceView.onResume();
    }

    @Override
    public void onPause() {
//    protected void onPause() {

    	// Ideally a game should implement onResume() and onPause()
        // to take appropriate action when the activity looses focus
        super.onPause();
        mGLSurfaceView.onPause();
    }

    private GLSurfaceView mGLSurfaceView;
}