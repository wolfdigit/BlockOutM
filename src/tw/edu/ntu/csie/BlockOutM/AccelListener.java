package tw.edu.ntu.csie.BlockOutM;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

public class AccelListener implements SensorEventListener {
	GameCore mCore;
	double[] prevAng;
	AccelListener(GameCore core) {
		mCore = core;
	}
	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSensorChanged(SensorEvent arg0) {
		double ang1 = Math.atan2(arg0.values[1],arg0.values[0]);
		double ang2 = Math.atan2(arg0.values[2],arg0.values[0]);
		if (prevAng!=null) {
			if (ang1>0&&Math.abs(ang1-prevAng[1])>0.1) {
				mCore.rotate(1, ang1);
			}
			if (ang1<0&&Math.abs(ang1-prevAng[1])>0.1) {
				mCore.rotate(4, ang1);
			}
			if (ang2>0.7&&Math.abs(ang2-prevAng[2])>0.2) {
				mCore.rotate(2, ang2);
			}
			if (ang2<0.7&&Math.abs(ang2-prevAng[2])>0.2) {
				mCore.rotate(5, ang2);
			}
		}
		else {
			prevAng = new double[3];
		}
		prevAng[1] = ang1;
		prevAng[2] = ang2;
	}

}
