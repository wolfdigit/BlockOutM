package tw.edu.ntu.csie.BlockOutM;
import java.security.SecureRandom;

public class Block {
	int position[] = {Setting.d-1,0,0};
	byte block[][];
	static Block[][] set = {
		{
/*		new Block(new byte[][]{{0,0,0}}),
		new Block(new byte[][]{{0,0,0}, {1,0,0}}),
		new Block(new byte[][]{{0,0,0}, {1,0,0}, {0,1,0}}),*/
		new Block(new byte[][]{{0,0,0}, {1,0,0}, {0,1,0}, {2,0,0}})
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
		this.block = set[setIdx][new SecureRandom().nextInt(set[setIdx].length)].cloneBlock();
	}
	public byte[][] cloneBlock() {
		byte[][] rtnv = new byte[this.block.length][];
		for (int i = 0; i<this.block.length; i++) {
			rtnv[i] = new byte[this.block[i].length];
			for (int j=0; j<this.block[i].length; j++) {
				rtnv[i][j] = this.block[i][j];
			}
		}
		return rtnv;
	}
	public Block clone() {
		Block rtnv = new Block(null);
		rtnv.block = this.cloneBlock();
		rtnv.position[0] = this.position[0];
		rtnv.position[1] = this.position[1];
		rtnv.position[2] = this.position[2];
		return rtnv;
	}
}
