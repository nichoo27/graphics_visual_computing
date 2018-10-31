package com.mycompany.myapp;

import android.content.*;
import android.opengl.GLSurfaceView.*;
import com.mycompany.myapp.util.*;
import java.nio.*;
import javax.microedition.khronos.egl.*;
import javax.microedition.khronos.opengles.*;

import static android.opengl.GLES20.*;

public class AirHockeyRenderer implements Renderer {
	private static final int POSITION_COMPONENT_COUNT=2;
	private static final int BYTES_PER_FLOAT=4;
	private static FloatBuffer vertexData;
    private final Context context;
	private int program;
	private static final String U_COLOR = "u_Color";
	private int uColorLocation;
	private static final String A_POSITION = "a_Position";
	private int aPositionLocation;
	
	public AirHockeyRenderer(Context context){
		
		this.context = context;
		
		float[] tableVerticesWithTriangle={

			0.9f, 0.9f, 
			-0.9f, 0.9f, 
			0.9f, -0.9f,

			-0.9f, -0.9f, 
			-0.9f, 0.9f, 
			0.9f, -0.9f,

			0.8f, 0.8f, 
			-0.8f, 0.8f, 
			0.8f, -0.8f,

			-0.8f, -0.8f, 
			-0.8f, 0.8f, 
			0.8f, -0.8f,

			0.7f, 0.7f, 
			-0.7f, 0.7f, 
			0.7f, -0.7f,

			-0.7f, -0.7f, 
			-0.7f, 0.7f, 
			0.7f, -0.7f,

			0.6f, 0.6f, 
			-0.6f, 0.6f, 
			0.6f, -0.6f,

			-0.6f, -0.6f, 
			-0.6f, 0.6f, 
			0.6f, -0.6f,

			0.5f, 0.5f, 
			-0.5f, 0.5f, 
			0.5f, -0.5f,

			-0.5f, -0.5f, 
			-0.5f, 0.5f, 
			0.5f, -0.5f,
			
			0.4f, 0.4f, 
			-0.4f, 0.4f, 
			0.4f, -0.4f,

			-0.4f, -0.4f, 
			-0.4f, 0.4f, 
			0.4f, -0.4f,

			0.3f, 0.3f, 
			-0.3f, 0.3f, 
			0.3f, -0.3f,

			-0.3f, -0.3f, 
			-0.3f, 0.3f, 
			0.3f, -0.3f,

			0.2f, 0.2f, 
			-0.2f, 0.2f, 
			0.2f, -0.2f,

			-0.2f, -0.2f, 
			-0.2f, 0.2f, 
			0.2f, -0.2f,

			0.14f, 0.14f, 
			-0.14f, 0.14f, 
			0.14f, -0.14f,

			-0.14f, -0.14f, 
			-0.14f, 0.14f, 
			0.14f, -0.14f,

			0.04f, 0.04f, 
			-0.04f, 0.04f, 
			0.04f, -0.04f,

			-0.04f, -0.04f, 
			-0.04f, 0.04f, 
			0.04f, -0.04f,
			
			};
			
	vertexData=ByteBuffer
	.allocateDirect(tableVerticesWithTriangle.length * BYTES_PER_FLOAT)
	.order(ByteOrder.nativeOrder())
	.asFloatBuffer();
	vertexData.put(tableVerticesWithTriangle);
	
	}
	@Override

    public void onSurfaceCreated(GL10 glUnused, EGLConfig config) {
        // Set the background clear color to red. The first component is
        // red, the second is green, the third is blue, and the last
        // component is alpha, which we don'ta use in this lesson.
        glClearColor(0.0f, 1.0f, 0.0f, 0.0f);
		
		String vertexShaderSource = TextResourceReader
			.readTextFileFromResource(context,R.raw.simple_vertex_shader);
		String fragmentShaderSource = TextResourceReader
			.readTextFileFromResource(context,R.raw.simple_fragment_shader);	

		int vertexShader = ShaderHelper.compileVertexShader(vertexShaderSource);
		int fragmentShader = ShaderHelper.compileFragmentShader(fragmentShaderSource);
		program = ShaderHelper.linkProgram(vertexShader,fragmentShader);
		if(LoggerConfig.ON){
			ShaderHelper.validateProgram(program);
}
		glUseProgram(program);
		uColorLocation = glGetUniformLocation(program,U_COLOR);
		aPositionLocation = glGetAttribLocation(program,A_POSITION);
		
		vertexData.position(0);
		glVertexAttribPointer(aPositionLocation,POSITION_COMPONENT_COUNT,
		GL_FLOAT,false,0,vertexData);
		glEnableVertexAttribArray(aPositionLocation);
	}
		
    /**
     * onSurfaceChanged is called whenever the surface has changed. This is
     * called at least once when the surface is initialized. Keep in mind that
     * Android normally restarts an Activity on rotation, and in that case, the
     * renderer will be destroyed and a new one created.
     * 
     * @param width
     *            The new width, in pixels.
     * @param height
     *            The new height, in pixels.
     */
    @Override
    public void onSurfaceChanged(GL10 glUnused, int width, int height) {
        // Set the OpenGL viewport to fill the entire surface.
        glViewport(0, 0, width, height);
	}
    /**
     * OnDrawFrame is called whenever a new frame needs to be drawn. Normally,
     * this is done at the refresh rate of the screen.
     */
    @Override
    public void onDrawFrame(GL10 glUnused) {
        // Clear the rendering surface.
        glClear(GL_COLOR_BUFFER_BIT);
		

        glUniform4f(uColorLocation, 1.f, 0.0f, 0.0f, 1.0f);
        glDrawArrays(GL_TRIANGLES, 0, 6);

        glUniform4f(uColorLocation, 0.5f, 0.2f, 0.3f, 1.0f);
        glDrawArrays(GL_TRIANGLES, 6, 6);

        glUniform4f(uColorLocation, 0.0f, 1.0f, 0.3f, 1.0f);
        glDrawArrays(GL_TRIANGLES, 12, 6);

        glUniform4f(uColorLocation, 1.0f, 0.0f, 0.0f, 1.0f);
        glDrawArrays(GL_TRIANGLES, 18, 6);

        glUniform4f(uColorLocation, 0.0f, 1.0f, 1.0f, 1.0f);
        glDrawArrays(GL_TRIANGLES, 24, 6);

        glUniform4f(uColorLocation, 0.0f, 1.0f, 0.0f, 1.0f);
        glDrawArrays(GL_TRIANGLES, 30, 6);

        glUniform4f(uColorLocation, 0.0f, 0.0f, 1.0f, 1.0f);
        glDrawArrays(GL_TRIANGLES, 36, 6);

        glUniform4f(uColorLocation, 0.0f, 0.7f, 0.0f, 1.0f);
        glDrawArrays(GL_TRIANGLES, 42, 6);

        glUniform4f(uColorLocation, 0.2f, 0.5f, 0.0f, 0.0f);
        glDrawArrays(GL_TRIANGLES, 48, 6);

        glUniform4f(uColorLocation, 0.0f, 0.0f, 0.0f, 1.0f);
        glDrawArrays(GL_TRIANGLES, 54, 6);
    }
}
