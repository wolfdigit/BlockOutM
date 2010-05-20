package tw.edu.ntu.csie.BlockOutM;

import android.widget.TextView;

public class GameCore {
	TextView mText1;
	int[] count = new int[6];
	GameCore (TextView text1) {
		mText1 = text1;
		new AccelListener(this);
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
}
