package tw.edu.ntu.csie.BlockOutM;

import android.widget.TextView;

public class GameCore implements Runnable {
	TextView mText1;
	GameRenderer mRenderer;
	AccelListener sensorListener;
	int[] count = new int[6];
	boolean occupy[][][];
	Block block;
	GameCore (TextView text1, GameRenderer renderer) {
		occupy = new boolean[Setting.d][][];
		for (int i=0; i<Setting.d; i++) {
			occupy[i] = new boolean[Setting.h][];
			for (int j=0; j<Setting.h; j++) {
				occupy[i][j] = new boolean[Setting.w];
				for (int k=0; k<Setting.w; k++) {
					occupy[i][j][k] = false;
				}
			}
		}
		mText1 = text1;
		mRenderer = renderer;
		sensorListener = new AccelListener(this);
	}
	void gameOver() {
		//
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
	private boolean newBlock() {
		int miny, maxy, minz, maxz;
		int minx, maxx;
		do {
			block = new Block(Setting.blockSet);
			miny=Setting.h; maxy=0; minz=Setting.w; maxz=0;
			minx=Setting.d; maxx=0;
			for (int i=0; i<block.block.length; i++) {
				if (block.y(i)<miny) {
					miny = block.y(i);
				}
				if (block.y(i)>maxy) {
					maxy = block.y(i);
				}
				if (block.z(i)<minz) {
					minz = block.z(i);
				}	
				if (block.z(i)>maxz) {
					maxz = block.z(i);
				}
				if (block.x(i)<minx) {
					minx = block.x(i);
				}
				if (block.x(i)>maxx) {
					maxx = block.x(i);
				}
			}
		} while (maxy-miny>Setting.h||maxz-minz>Setting.w||maxx-minx>Setting.d);
		if (miny<0) block.position[1] -= miny;
		if (maxy>Setting.h-1) block.position[1] -= maxy-(Setting.h-1);
		if (minz<0) block.position[2] -= minz;
		if (maxz>Setting.w-1) block.position[2] -= maxz-(Setting.w-1);
		if (maxx>Setting.d-1) block.position[0] -= maxx-(Setting.d-1);
		for (int i=0; i<block.block.length; i++) {
			if (occupy[block.x(i)][block.y(i)][block.z(i)]) {
				return false;
			}
		}
		mRenderer.buildBlock(block);
		return true;
	}
	private void concrete() {
		for (int i=0; i<block.block.length; i++) {
			occupy[block.x(i)][block.y(i)][block.z(i)] = true;
		}
	}
	public boolean slip() {
		for (int i=0; i<block.block.length; i++) {
			if (block.x(i)-1<0||occupy[block.x(i)-1][block.y(i)][block.z(i)]) {
				concrete();
				return false;
			}
		}
		block.position[0]--;
		mRenderer.buildBlock(block);
		return true;
	}
	public void run() {
		if (!newBlock()) {
			gameOver();
		}
		while (true) {
			try {
				Thread.sleep(Setting.slipTime);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (!this.slip()) {
				if (!newBlock()) {
					gameOver();
					break;
				}
			}
			for (int i=0; i<6; i++) {
				count[i] = 0;
			}
		}
	}
}
