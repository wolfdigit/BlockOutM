package tw.edu.ntu.csie.BlockOutM;

class Setting {
	static int h=5, w=5, d=10;
	static boolean drawWallFace=true;
	static boolean drawStraightDown=true;
	static boolean drawCoverGrid=false;
	static float wallFaceColor[]={0.3f, 0.3f, 0.3f, 1.0f};
	static float wallGridColor[]={1.0f, 1.0f, 1.0f, 1.0f};
	static float coverGridColor[]={0.0f, 0.0f, 1.0f, 0.3f};
	static float blockColor[]={1.0f, 0.5f, 0.5f, 1.0f};
	static float straightDownColor[]=coverGridColor;
	static int blockSet = 0;
	static int slipTime = 500;
}
