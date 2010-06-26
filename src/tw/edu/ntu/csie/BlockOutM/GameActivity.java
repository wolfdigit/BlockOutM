package tw.edu.ntu.csie.BlockOutM;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class GameActivity extends Activity {
	GameCore mCore;
	SensorManager sm;
	GameRenderer mGameRenderer;
	Handler txtH;
	TextView mText1;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {

    	super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // Create our Preview view and set it as the content of our
        // Activity
        setContentView(R.layout.main);
        mGLSurfaceView = (GLSurfaceView) findViewById(R.id.GLSurfaceView);
        mGameRenderer = new GameRenderer();
        mGLSurfaceView.setRenderer(mGameRenderer);
        //setContentView(mGLSurfaceView);
        mText1 = (TextView)findViewById(R.id.text1);
        txtH = new msgHandler();
        mCore = new GameCore(txtH, mGameRenderer);
		sm=(SensorManager)getSystemService(Context.SENSOR_SERVICE);
		sm.registerListener(mCore.sensorListener, sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME);
		sm.registerListener(mCore.sensorListener, sm.getDefaultSensor(Sensor.TYPE_ORIENTATION), SensorManager.SENSOR_DELAY_GAME);

//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Thread tCore = new Thread(mCore);
		tCore.start();
    }

    public boolean onTouchEvent(MotionEvent event){
    	float x=event.getX();
    	float y=event.getY();
    	switch(event.getAction()){
    	case MotionEvent.ACTION_DOWN:
    		mCore.displayMsg("x:"+x);
    	case MotionEvent.ACTION_UP:
    		mCore.displayMsg("y:"+y);
    	case MotionEvent.ACTION_MOVE:
    		mCore.displayMsg("x:"+x+"y:"+y);
    		break;
    	default:
    	}
    	return super.onTouchEvent(event);
    }
    
    class msgHandler extends Handler {
    	public void handleMessage(Message msg){
    		mText1.setText((String)msg.obj);
    		super.handleMessage(msg);
    	}
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

    public boolean onKeyUp(int keyCode, KeyEvent event) {
    	super.onKeyDown(keyCode, event);
    	if (keyCode==KeyEvent.KEYCODE_DPAD_CENTER) {
    		mCore.sensorListener.reset();
    	}
    	return true;
    }
    
    private GLSurfaceView mGLSurfaceView;
}