package tw.edu.ntu.csie.BlockOutM;

import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLSurfaceView;

/**
 * Render the whole game screen
 */

class GameRenderer implements GLSurfaceView.Renderer {
	//private Cube mCube;
	DepthTable mDepthTable;
    public GameRenderer() {
        mGameBoard = new GameBoard();
        mDepthTable = new DepthTable();
        //mCube = new Cube();
    }
    float near=7.0f;

    public void buildPile(boolean occupy[][][]) {
    	mGameBoard.buildPile(occupy);
    	mDepthTable.buildPile(occupy);
    }
    
    public void buildBlock(Block block) {
    	mGameBoard.buildBlock(block);
    	mDepthTable.buildBlock(block);
    }
    
    float leftMost;
    public void onDrawFrame(GL10 gl) {
    	float eps = 0.01f;
        /*
         * Usually, the first thing one might want to do is to clear
         * the screen. The most efficient way of doing this is to use
         * glClear().
         */

        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

        /*
         * Now we're ready to draw some 3D objects
         */

        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
        //gl.glPushMatrix();
        gl.glTranslatef(leftMost, -mDepthTable.D/2, -near);
        gl.glRotatef(90, -1, 0, 0);
        mDepthTable.draw(gl);
        //gl.glPopMatrix();
        gl.glLoadIdentity();
        //gl.glTranslatef(0, 0, -1.0f);
        gl.glTranslatef(-mGameBoard.W/2, -mGameBoard.H/2, -near-mGameBoard.D);
//        gl.glRotatef(mAngle,        0, 1, 0);
//        gl.glRotatef(mAngle*0.25f,  1, 0, 0);

        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);

        mGameBoard.draw(gl);
        //mCube.draw(gl);

//        gl.glRotatef(mAngle*2.0f, 0, 1, 1);
//        gl.glTranslatef(0.5f, 0.5f, 0.5f);

//        mGameBoard.draw(gl);

//        mAngle += 1.2f;
    }

    public int[] getConfigSpec() {
         // We want a depth buffer, don't care about the
         // details of the color buffer.
         int[] configSpec = {
                 EGL10.EGL_DEPTH_SIZE,   16,
                 EGL10.EGL_NONE
         };
         return configSpec;
    }

    public void onSurfaceChanged(GL10 gl, int width, int height) {
         float eps=0.01f;
         gl.glViewport(0, 0, width, height);

         /*
          * Set our projection matrix. This doesn't have to be done
          * each time we draw, but usually a new projection needs to
          * be set when the viewport is resized.
          */

         float ratio = (float) width / height;
         gl.glMatrixMode(GL10.GL_PROJECTION);
         gl.glLoadIdentity();
         //gl.glFrustumf(-ratio, ratio, -1, 1, 1, 10);
         if (mGameBoard.W/mGameBoard.H>ratio) {
             gl.glFrustumf(-mGameBoard.W/2-eps, mGameBoard.W/2+eps, -mGameBoard.W/ratio/2-eps, mGameBoard.W/ratio/2+eps, near-eps, near+mGameBoard.D+eps);
             leftMost = -mGameBoard.W/2;
         }
         else {
             gl.glFrustumf(-mGameBoard.H*ratio/2-eps, mGameBoard.H*ratio/2+eps, -mGameBoard.H/2-eps, mGameBoard.H/2+eps, near-eps, near+mGameBoard.D+eps);
             leftMost = -mGameBoard.H*ratio/2;
         }
         //gl.glFrustumf(0.0f, 10000.0f, 0.0f, 2.0f, 0, 0);
    }

    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        /*
         * By default, OpenGL enables features that improve quality
         * but reduce performance. One might want to tweak that
         * especially on software renderer.
         */
        gl.glDisable(GL10.GL_DITHER);

        /*
         * Some one-time OpenGL initialization can be made here
         * probably based on features of this particular context
         */
         gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT,
                 GL10.GL_FASTEST);

         gl.glClearColor(0,0,0,0);
         gl.glEnable(GL10.GL_CULL_FACE);
         gl.glShadeModel(GL10.GL_SMOOTH);
         gl.glEnable(GL10.GL_DEPTH_TEST);
         gl.glEnable (gl.GL_BLEND); 
         gl.glBlendFunc (gl.GL_SRC_ALPHA, gl.GL_ONE_MINUS_SRC_ALPHA);
    }
    private GameBoard mGameBoard;
//    private float mAngle;
}
