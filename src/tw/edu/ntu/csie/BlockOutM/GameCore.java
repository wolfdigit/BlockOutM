package tw.edu.ntu.csie.BlockOutM;

import android.widget.TextView;

public class GameCore implements Runnable {
	TextView mText1;
	AccelListener sensorListener;
	int[] count = new int[6];
	GameCore (TextView text1) {
		mText1 = text1;
		sensorListener = new AccelListener(this);
	}
	void rotate(int direction) {
		count[direction]++;
        mText1.setText(count[0]+"\n"+count[1]+"\n"+count[2]+"\n"+count[3]+"\n"+count[4]+"\n"+count[5]);
	}
	void rotate(int direction, double val) {
        mText1.setText(direction+":"+val);
	}
	void debugTxt(String str) {
		mText1.setText(str);
	}
	public void run() {
		while (true) {
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for (int i=0; i<6; i++) {
				count[i] = 0;
			}
		}
	}
}
