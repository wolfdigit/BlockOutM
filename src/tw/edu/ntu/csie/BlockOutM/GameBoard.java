/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package tw.edu.ntu.csie.BlockOutM;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * A vertex shaded cube.
 */
class GameBoard
{
	public float H, W, D;
	int h, w, d;
	private ShortBuffer mWallFaceIdxBuffer, mWallGridIdxBuffer, mCoverGridIdxBuffer, mStraightDownIdxBuffer;
    public GameBoard()
    {
        float one = 1.0f;
        h=Setting.h; w=Setting.w; d=Setting.d;
        H = h * one; W = w * one; D = d * one;
        float vertices[] = new float[(h+1)*(w+1)*(d+1)*3];
        short straightDownIdx[] = new short[(h-1)*(w-1)*2];
        short pointIdx[][][] = new short[d+1][][];
        short vertexCount = 0;
        for (int i=0; i<=d; i++) {
        	pointIdx[i] = new short[h+1][];
        	for (int j=0; j<=h; j++) {
        		pointIdx[i][j] = new short[w+1];
        		for (int k=0; k<=w; k++) {
        			vertices[vertexCount*3+0] = (w-k) * one;
        			vertices[vertexCount*3+1] = (h-j) * one;
        			vertices[vertexCount*3+2] = i * one;
        			pointIdx[i][j][k] = vertexCount;
        			vertexCount++;
        		}
        	}
        }

        short wallFaceIdx[] = {
        		pointIdx[0][0][0], pointIdx[d][0][0], pointIdx[d][0][w],
        		pointIdx[0][0][0], pointIdx[d][0][w], pointIdx[0][0][w],
        		pointIdx[0][0][0], pointIdx[0][h][0], pointIdx[d][h][0],
        		pointIdx[0][0][0], pointIdx[d][h][0], pointIdx[d][0][0],
        		pointIdx[0][0][0], pointIdx[0][0][w], pointIdx[0][h][w],
        		pointIdx[0][0][0], pointIdx[0][h][w], pointIdx[0][h][0],
        		pointIdx[0][0][w], pointIdx[d][0][w], pointIdx[d][h][w],
        		pointIdx[0][0][w], pointIdx[d][h][w], pointIdx[0][h][w],
        		pointIdx[0][h][0], pointIdx[0][h][w], pointIdx[d][h][w],
        		pointIdx[0][h][0], pointIdx[d][h][w], pointIdx[d][h][0]
        };
        
        short wallGridIdx[] = new short[((h+1)*3+(w+1)*3+(d+1)*4)*2];
        short coverGridIdx[] = new short[((h+1)+(w+1))*2];
        int wallGridIdxCount=0;
        int coverGridIdxCount=0;
        for (int i=0; i<=h; i++) {
        	wallGridIdx[wallGridIdxCount*2+0] = pointIdx[0][i][0];
        	wallGridIdx[wallGridIdxCount*2+1] = pointIdx[d][i][0];
        	wallGridIdxCount++;
        	wallGridIdx[wallGridIdxCount*2+0] = pointIdx[0][i][w];
        	wallGridIdx[wallGridIdxCount*2+1] = pointIdx[d][i][w];
        	wallGridIdxCount++;
        	wallGridIdx[wallGridIdxCount*2+0] = pointIdx[0][i][0];
        	wallGridIdx[wallGridIdxCount*2+1] = pointIdx[0][i][w];
        	wallGridIdxCount++;
        	coverGridIdx[coverGridIdxCount*2+0] = pointIdx[d][i][0];
        	coverGridIdx[coverGridIdxCount*2+1] = pointIdx[d][i][w];
        	coverGridIdxCount++;
        }
        for (int i=0; i<=w; i++) {
        	wallGridIdx[wallGridIdxCount*2+0] = pointIdx[0][0][i];
        	wallGridIdx[wallGridIdxCount*2+1] = pointIdx[d][0][i];
        	wallGridIdxCount++;
        	wallGridIdx[wallGridIdxCount*2+0] = pointIdx[0][h][i];
        	wallGridIdx[wallGridIdxCount*2+1] = pointIdx[d][h][i];
        	wallGridIdxCount++;
        	wallGridIdx[wallGridIdxCount*2+0] = pointIdx[0][0][i];
        	wallGridIdx[wallGridIdxCount*2+1] = pointIdx[0][h][i];
        	wallGridIdxCount++;
        	coverGridIdx[coverGridIdxCount*2+0] = pointIdx[d][0][i];
        	coverGridIdx[coverGridIdxCount*2+1] = pointIdx[d][h][i];
        	coverGridIdxCount++;
        }
        for (int i=0; i<=d; i++) {
        	wallGridIdx[wallGridIdxCount*2+0] = pointIdx[i][0][0];
        	wallGridIdx[wallGridIdxCount*2+1] = pointIdx[i][0][w];
        	wallGridIdxCount++;
        	wallGridIdx[wallGridIdxCount*2+0] = pointIdx[i][h][0];
        	wallGridIdx[wallGridIdxCount*2+1] = pointIdx[i][h][w];
        	wallGridIdxCount++;
        	wallGridIdx[wallGridIdxCount*2+0] = pointIdx[i][0][0];
        	wallGridIdx[wallGridIdxCount*2+1] = pointIdx[i][h][0];
        	wallGridIdxCount++;
        	wallGridIdx[wallGridIdxCount*2+0] = pointIdx[i][0][w];
        	wallGridIdx[wallGridIdxCount*2+1] = pointIdx[i][h][w];
        	wallGridIdxCount++;
        }
        
        int straightDownCount = 0;
        for (int i=1; i<h; i++) {
        	for (int j=1; j<w; j++) {
        		straightDownIdx[straightDownCount*2+0] = pointIdx[d][i][j];
        		straightDownIdx[straightDownCount*2+1] = pointIdx[0][i][j];
        		straightDownCount++;
        	}
        }
/*
        int vertices[] = {
                -one, -one, -one,
                one, -one, -one,
                one,  one, -one,
                -one,  one, -one,
                -one, -one,  one,
                one, -one,  one,
                one,  one,  one,
                -one,  one,  one,
        };

        int colors[] = {
                0,    0,    0,  one,
                one,    0,    0,  one,
                one,  one,    0,  one,
                0,  one,    0,  one,
                0,    0,  one,  one,
                one,    0,  one,  one,
                one,  one,  one,  one,
                0,  one,  one,  one
        };

        byte indices[] = {
                0, 4, 5,    0, 5, 1,
                1, 5, 6,    1, 6, 2,
                2, 6, 7,    2, 7, 3,
                3, 7, 4,    3, 4, 0,
                4, 7, 6,    4, 6, 5,
                3, 0, 1,    3, 1, 2
        };
*/

        // Buffers to be passed to gl*Pointer() functions
        // must be direct, i.e., they must be placed on the
        // native heap where the garbage collector cannot
        // move them.
        //
        // Buffers with multi-byte datatypes (e.g., short, int, float)
        // must have their byte order set to native order

        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length*4);
        vbb.order(ByteOrder.nativeOrder());
        mVertexBuffer = vbb.asFloatBuffer();
        mVertexBuffer.put(vertices);
        mVertexBuffer.position(0);
/*
        ByteBuffer cbb = ByteBuffer.allocateDirect(colors.length*4);
        cbb.order(ByteOrder.nativeOrder());
        mColorBuffer = cbb.asIntBuffer();
        mColorBuffer.put(colors);
        mColorBuffer.position(0);

        mIndexBuffer = ByteBuffer.allocateDirect(indices.length);
        mIndexBuffer.put(indices);
        mIndexBuffer.position(0);
*/
        ByteBuffer wfbb = ByteBuffer.allocateDirect(wallFaceIdx.length*2);
        wfbb.order(ByteOrder.nativeOrder());
        mWallFaceIdxBuffer = wfbb.asShortBuffer();
        mWallFaceIdxBuffer.put(wallFaceIdx);
        mWallFaceIdxBuffer.position(0);
        
        ByteBuffer wgbb = ByteBuffer.allocateDirect(wallGridIdx.length*2);
        wgbb.order(ByteOrder.nativeOrder());
        mWallGridIdxBuffer = wgbb.asShortBuffer();
        mWallGridIdxBuffer.put(wallGridIdx);
        mWallGridIdxBuffer.position(0);

        ByteBuffer cgbb = ByteBuffer.allocateDirect(coverGridIdx.length*2);
        cgbb.order(ByteOrder.nativeOrder());
        mCoverGridIdxBuffer = cgbb.asShortBuffer();
        mCoverGridIdxBuffer.put(coverGridIdx);
        mCoverGridIdxBuffer.position(0);

        ByteBuffer apbb = ByteBuffer.allocateDirect(straightDownIdx.length*2);
        apbb.order(ByteOrder.nativeOrder());
        mStraightDownIdxBuffer = apbb.asShortBuffer();
        mStraightDownIdxBuffer.put(straightDownIdx);
        mStraightDownIdxBuffer.position(0);
    }

    public void draw(GL10 gl)
    {
//        gl.glFrontFace(gl.GL_CW);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mVertexBuffer);
        //gl.glColorPointer(4, GL10.GL_FIXED, 0, mColorBuffer);
        //gl.glDrawElements(gl.GL_TRIANGLES, 36, gl.GL_UNSIGNED_BYTE, mIndexBuffer);
        gl.glDisable(GL10.GL_DEPTH_TEST);
        if (Setting.drawWallFace) {
            gl.glColor4f(Setting.wallFaceColor[0], Setting.wallFaceColor[1], Setting.wallFaceColor[2], Setting.wallFaceColor[3]);
            gl.glDrawElements(GL10.GL_TRIANGLES, 36, GL10.GL_UNSIGNED_SHORT, mWallFaceIdxBuffer);
        }
        gl.glColor4f(Setting.wallGridColor[0], Setting.wallGridColor[1], Setting.wallGridColor[2], Setting.wallGridColor[3]);
        gl.glDrawElements(GL10.GL_LINES, ((h+1)*3+(w+1)*3+(d+1)*4)*2, GL10.GL_UNSIGNED_SHORT, mWallGridIdxBuffer);
        gl.glEnable(GL10.GL_DEPTH_TEST);
        if (Setting.drawCoverGrid) {
            gl.glColor4f(Setting.coverGridColor[0], Setting.coverGridColor[1], Setting.coverGridColor[2], Setting.coverGridColor[3]);
            gl.glDrawElements(GL10.GL_LINES, ((h+1)+(w+1))*2, GL10.GL_UNSIGNED_SHORT, mCoverGridIdxBuffer);
        }
        if (Setting.drawCoverGrid) {
            gl.glColor4f(Setting.straightDownColor[0], Setting.straightDownColor[1], Setting.straightDownColor[2], Setting.straightDownColor[3]);
            gl.glDrawElements(GL10.GL_LINES, ((h-1)*(w-1))*2, GL10.GL_UNSIGNED_SHORT, mStraightDownIdxBuffer);
        }
    }

    private FloatBuffer   mVertexBuffer;
    //private IntBuffer   mColorBuffer;
    //private ByteBuffer  mIndexBuffer;
}
