package tw.edu.ntu.csie.BlockOutM;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class AccelListener implements SensorEventListener {
	GameCore mCore;
	double[] prevAng = {Double.NaN, Double.NaN, Double.NaN};;
	AccelListener(GameCore core) {
		mCore = core;
	}
	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	double smallAng(double ang) {
		if (ang>Math.PI) return smallAng(ang-Math.PI);
		if (ang<-Math.PI) return smallAng(ang+Math.PI);
		return ang;
	}
	double thres0=0.1;
	double thres1=0.23;
	double thres2=0.38;
	long thres4=500000000;
	long prevTime;
	int rotX(double[] ang, double[] prevAng) {
		if (smallAng(ang[0]-prevAng[0])>thres0) return 1;
		if (smallAng(ang[0]-prevAng[0])<-thres0) return -1;
		return 0;
	}
	int rotY(double[] ang, double[] prevAng) {
		if (smallAng(ang[1]-prevAng[1])>thres1) return 1;
		if (smallAng(ang[1]-prevAng[1])<-thres1) return -1;
		return 0;
	}
	int rotZ(double[] ang, double[] prevAng) {
		if (smallAng(ang[2]-prevAng[2])>thres2) return 1;
		if (smallAng(ang[2]-prevAng[2])<-thres2) return -1;
		return 0;
	}
	@Override
	public void onSensorChanged(SensorEvent arg0) {
		double[] ang=new double[3];
		switch (arg0.sensor.getType()) {
		case Sensor.TYPE_ORIENTATION:
			ang[0] = arg0.values[0]*Math.PI/180;
			if (prevAng[0]!=Double.NaN&&arg0.timestamp-prevTime>thres4) {
				if (rotX(ang, prevAng)==1) {
					prevTime=arg0.timestamp;
					mCore.rotate(0);
				}
				if (rotX(ang, prevAng)==-1) {
					prevTime=arg0.timestamp;
					mCore.rotate(3);
				}
			}
			prevAng[0] = ang[0];
			break;
		case Sensor.TYPE_ACCELEROMETER:
			ang[1] = Math.atan2(arg0.values[1],arg0.values[0]);
			ang[2] = Math.atan2(arg0.values[2],arg0.values[0]);
			if (prevAng[1]!=Double.NaN&&arg0.timestamp-prevTime>thres4) {
				if (rotY(ang, prevAng)==1) {
					prevTime=arg0.timestamp;
					mCore.rotate(1);
				}
				if (rotY(ang, prevAng)==-1) {
					prevTime=arg0.timestamp;
					mCore.rotate(4);
				}
				if (rotZ(ang, prevAng)==1) {
					prevTime=arg0.timestamp;
					mCore.rotate(2);
				}
				if (rotZ(ang, prevAng)==-1) {
					prevTime=arg0.timestamp;
					mCore.rotate(5);
				}
			}
			prevAng[1] = ang[1];
			prevAng[2] = ang[2];
			break;
		}
	}

}
