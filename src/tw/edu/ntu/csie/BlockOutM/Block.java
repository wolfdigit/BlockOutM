package tw.edu.ntu.csie.BlockOutM;
import java.security.SecureRandom;

public class Block {
	static class blockShape {
		byte cube[][];
		byte nCube;
		blockShape(){}
		blockShape(byte[][] array) {
			nCube = (byte)array.length;
			cube = new byte[nCube][];
			for (int i=0; i<nCube; i++) {
				cube[i] = array[i];
			}
		}
	}
	static blockShape setA[] = {
		new blockShape(new byte[][]{{0,0,0}}),
		new blockShape(new byte[][]{{0,0,0}, {1,0,0}}),
		new blockShape(new byte[][]{{0,0,0}, {1,0,0}, {0,1,0}})
		};
	blockShape shape;
	Block() {
		this((byte)(new SecureRandom().nextInt()%Setting.blockSet.length));
	}
	Block(byte idx) {
		shape = Setting.blockSet[idx];
	}
}
