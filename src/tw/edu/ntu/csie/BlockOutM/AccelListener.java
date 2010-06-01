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
	double midle0=0;
	double thres0=0.3;
	double midle1=0;
	double thres1=0.18;
	double midle2=0.6;
	double thres2=0.2;
	long thres4=300000000;
	long prevTime;
	int rotX(double[] ang, double[] prevAng) {
		if (midle1-thres1<prevAng[1]&&prevAng[1]<midle1+thres1 && midle2-thres2<prevAng[2]&&prevAng[2]<midle2+thres2) {
			if (smallAng(ang[0]-midle0)>thres0&&smallAng(prevAng[0]-midle0)<thres0) return 1;
			if (smallAng(ang[0]-midle0)<-thres0&&smallAng(prevAng[0]-midle0)>-thres0) return -1;
		}
		return 0;
	}
	int rotY(double[] ang, double[] prevAng) {
		if (ang[1]>midle1+thres1&&prevAng[1]<midle1+thres1) return 1;
		if (ang[1]<midle1-thres1&&prevAng[1]>midle1-thres1) return -1;
		return 0;
	}
	int rotZ(double[] ang, double[] prevAng) {
		if (ang[2]>midle2+thres2&&prevAng[2]<midle2+thres2) return 1;
		if (ang[2]<midle2-thres2&&prevAng[2]>midle2-thres2) return -1;
		return 0;
	}
	@Override
	public void onSensorChanged(SensorEvent arg0) {
		double[] ang=new double[3];
		if (arg0.sensor.getType()==Sensor.TYPE_ORIENTATION) {
			ang[0] = arg0.values[0]*Math.PI/180;
			if (prevAng[0]==Double.NaN) {
				prevAng[0] = ang[0];
				midle0 = prevAng[0];
				return;
			}
			if (arg0.timestamp-prevTime>thres4) {
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
		}
		if (arg0.sensor.getType()==Sensor.TYPE_ACCELEROMETER) {
			ang[1] = Math.atan2(arg0.values[1],arg0.values[0]);
			ang[2] = Math.atan2(arg0.values[2],arg0.values[0]);
			if (prevAng[1]==Double.NaN) {
				prevAng[1] = ang[1];
				midle1 = prevAng[1];
				prevAng[2] = ang[2];
				midle2 = prevAng[2];
				return;
			}
			if (arg0.timestamp-prevTime>thres4) {
				if (rotY(ang, prevAng)==1) {
					prevTime=arg0.timestamp;
					mCore.rotate(1);
				} else
				if (rotY(ang, prevAng)==-1) {
					prevTime=arg0.timestamp;
					mCore.rotate(4);
				} else
				if (rotZ(ang, prevAng)==1) {
					prevTime=arg0.timestamp;
					mCore.rotate(2);
				} else
				if (rotZ(ang, prevAng)==-1) {
					prevTime=arg0.timestamp;
					mCore.rotate(5);
				}
			}
			prevAng[1] = ang[1];
			prevAng[2] = ang[2];
		}
	}

	void reset() {
		midle0 = prevAng[0];
		midle1 = prevAng[1];
		midle2 = prevAng[2];
	}
}
