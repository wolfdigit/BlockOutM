package tw.edu.ntu.csie.BlockOutM;
import java.security.SecureRandom;

public class Block {
	int position[] = {Setting.d-1,0,0};
	byte block[][];
	static Block[][] set = {
		{
		new Block(new byte[][]{{0,0,0}}),
		new Block(new byte[][]{{0,0,0}, {1,0,0}}),
		new Block(new byte[][]{{0,0,0}, {1,0,0}, {0,1,0}})
		}
	};
	int x(int idx) {
		return position[0]+block[idx][0];
	}
	int y(int idx) {
		return position[1]+block[idx][1];
	}
	int z(int idx) {
		return position[2]+block[idx][2];
	}
	Block(byte[][] blocks) {
		this.block = blocks;
	}
	Block(int setIdx) {
		byte[][] origBlock = set[setIdx][new SecureRandom().nextInt(set[setIdx].length)].block;
		this.block = new byte[origBlock.length][];
		for (int i = 0; i<origBlock.length; i++) {
			this.block[i] = new byte[origBlock[i].length];
			for (int j=0; j<origBlock[i].length; j++) {
				this.block[i][j] = origBlock[i][j];
			}
		}
	}
}
