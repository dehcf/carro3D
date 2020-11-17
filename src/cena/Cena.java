package cena;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT; //primitivas 3D

/**
 *
 * @author Siabreu
 */
public class Cena implements GLEventListener {

    private float xMin, xMax, yMin, yMax, zMin, zMax;
    GLU glu;
    public float angulo = 0, tx, ty, tz;
    public int mode;

    @Override
    public void init(GLAutoDrawable drawable) {
        //dados iniciais da cena
        glu = new GLU();
        GL2 gl = drawable.getGL().getGL2();
        //Estabelece as coordenadas do SRU (Sistema de Referencia do Universo)
        //-100 a 100 e não mais de 0 a 1
        xMin = yMin = zMin = -100;
        xMax = yMax = zMax = 100;

        tx = ty = tz = 0;

        //Habilita o buffer de profundidade
        gl.glEnable(GL2.GL_DEPTH_TEST);
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        //obtem o contexto Opengl
        GL2 gl = drawable.getGL().getGL2();
        //objeto para desenho 3D
        GLUT glut = new GLUT();

        //define a cor da janela (R, G, G, alpha)
        gl.glClearColor(0, 0, 0, 1);
        //limpa a janela com a cor especificada
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity(); //ler a matriz identidade

        /*
            desenho da cena - Carrinho 3D       
        *
         */
        gl.glRotatef(angulo, 0, 1, 0);

        //desenha o corpo do carro
        gl.glPushMatrix();
        corpoCarro(gl, glut);
        gl.glPopMatrix();
        
        //desenha os farois 
        gl.glPushMatrix();
        farois(gl, glut);
        gl.glPopMatrix();

        //desenha as rodas 
        gl.glPushMatrix();
        rodas(gl, glut);
        gl.glPopMatrix();

        gl.glFlush();
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        //obtem o contexto grafico Opengl
        GL2 gl = drawable.getGL().getGL2();

        //evita a divisao por zero
        if (height == 0) {
            height = 1;
        }
        //calcula a proporcao da janela (aspect ratio) da nova janela
        //float aspect = (float) width / height;

        //ativa a matriz de projecao
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity(); //ler a matriz identidade

        //projecao ortogonal sem a correcao do aspecto
        gl.glOrtho(xMin, xMax, yMin, yMax, zMin, zMax);

        //ativa a matriz de modelagem
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity(); //ler a matriz identidade
        System.out.println("Reshape: " + width + ", " + height);
    }

    public void corpoCarro(GL2 gl, GLUT glut) {
        //parte de baixo
        gl.glPushMatrix();
        gl.glTranslatef(0, -40, 0);
        gl.glScalef(1, 0.5f, 1);
        gl.glColor3f(0, 0, 1);
        glut.glutSolidCube(100);
        gl.glPopMatrix();
        
        //parte de cima
        gl.glPushMatrix();
        gl.glScalef(1, 0.5f, 0.5f);
        gl.glTranslatef(0, 0, -50);
        gl.glColor3f(0, 0, 0.5f);
        glut.glutSolidCube(100);
        gl.glPopMatrix();
    }

    public void farois(GL2 gl, GLUT glut) {
        gl.glColor3f(1.0f, 1.0f, 0.0f); //cor dos farois frontais

        //farol frontal esquerdo
        gl.glPushMatrix();
        gl.glTranslatef(-20, -40, 50);
        glut.glutSolidSphere(5, 20, 20);
        gl.glPopMatrix();

        //farol frontal direito
        gl.glPushMatrix();
        gl.glTranslatef(20, -40, 50);
        glut.glutSolidSphere(5, 20, 20);
        gl.glPopMatrix();

        gl.glColor3f(1.0f, 0.0f, 0.0f); //cor dos farois trazeiros
        
        //farol trazeiro direito
        gl.glPushMatrix();
        gl.glTranslatef(20, -40, -50);
        glut.glutSolidSphere(5, 20, 20);
        gl.glPopMatrix();
        
        //farol trazeiro esquerdo
        gl.glPushMatrix();
        gl.glTranslatef(-20, -40, -50);
        glut.glutSolidSphere(5, 20, 20);
        gl.glPopMatrix();
    }

    public void rodas(GL2 gl, GLUT glut) {
        gl.glColor3f(1.0f, 0.0f, 0.0f);
        gl.glColor3f(0.8f, 0.8f, 0.8f);

        //frontal direita
        gl.glPushMatrix();
        gl.glTranslatef(57, -60, 40);
        gl.glRotatef(90, 0, 1, 0);
        glut.glutSolidTorus(7, 20, 50, 50);
        gl.glPopMatrix();

        //trazeira direita
        gl.glPushMatrix();
        gl.glTranslatef(57, -60, -40);
        gl.glRotatef(90, 0, 1, 0);
        glut.glutSolidTorus(7, 20, 50, 50);
        gl.glPopMatrix();

        //frontal esquerda
        gl.glPushMatrix();
        gl.glTranslatef(-57, -60, 40);
        gl.glRotatef(90, 0, 1, 0);
        glut.glutSolidTorus(7, 20, 50, 50);
        gl.glPopMatrix();

        //trazeira esquerda
        gl.glPushMatrix();
        gl.glTranslatef(-57, -60, -40);
        gl.glRotatef(90, 0, 1, 0);
        glut.glutSolidTorus(7, 20, 50, 50);
        gl.glPopMatrix();
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
    }
}
