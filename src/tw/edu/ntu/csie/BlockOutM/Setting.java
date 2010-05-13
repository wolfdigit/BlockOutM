package tw.edu.ntu.csie.BlockOutM;

class Setting {
	int h=5, w=5, d=10;
	boolean drawWallFace=true;
	boolean drawStraightDown=true;
	boolean drawCoverGrid=true;
	float wallFaceColor[]={0.3f, 0.3f, 0.3f, 1.0f};
	float wallGridColor[]={1.0f, 1.0f, 1.0f, 1.0f};
	float coverGridColor[]={0.0f, 0.0f, 1.0f, 0.3f};
	float straightDownColor[]=coverGridColor;
}
