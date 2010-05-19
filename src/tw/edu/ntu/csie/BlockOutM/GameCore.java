package tw.edu.ntu.csie.BlockOutM;

import android.widget.TextView;

public class GameCore {
	TextView mText1;
	GameCore (TextView text1) {
		mText1 = text1;
		new AccelListener(this);
	}
	void rotate(int direction) {
        mText1.setText(Integer.toString(direction));
	}
	void rotate(int direction, double val) {
        mText1.setText(direction+":"+val);
	}
}
