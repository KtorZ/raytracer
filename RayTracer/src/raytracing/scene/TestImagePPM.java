/** Classe de Tests de ImagePPM
 * @author Matthias Benkort, Clément Dolou
 * @version 1.1
 */
package raytracing.scene;

import raytracing.scene.Color;
import raytracing.scene.positionable.ViewPoint;
import raytracing.scene.positionable.Screen;
import raytracing.scene.geometry.*;
import java.io.*;
import org.junit.*;
import static org.junit.Assert.*;

public class TestImagePPM{
    private ImagePPM image;
    public static final double EPSILON = Math.pow(10, -9);

    @Before
	public void setUp(){
	//Instanciation d'une image de reference.
	image = new ImagePPM(new int[]{10,15}, 223);
	for(int i = 0; i < 10; i++){
	    for(int j = 0; j < 15; j++){
		image.addColor(i,j,new Color(1,0.5,0),false);
	    }
	}
    }

    //Tests relatifs a la taille
    @Test(expected = IllegalArgumentException.class)
	public void size1(){
	image.setSize(new int[]{0,0});
    }

    @Test(expected = IllegalArgumentException.class)
	public void size2(){
	image.setSize(new int[]{-10,10});
    }

    @Test(expected = IllegalArgumentException.class)
	public void size3(){
	image.setSize(new int[]{10,-10});
    }

    @Test(expected = IllegalArgumentException.class)
	public void size4(){
	image.setSize(new int[]{0,0});
    }

    @Test(expected = IllegalArgumentException.class)
	public void size5(){
	image.setSize(new int[]{10,10,10});
    }

    @Test
	public void size6(){
	image.setSize(new int[]{20,10});
	int size[] = image.getSize();
	assertEquals(size[0], 20, EPSILON);
	assertEquals(size[1], 10, EPSILON);
    }
	
    @Test
	public void size7(){
	int size[] = image.getSize();
	assertEquals(size[0], 10, EPSILON);
	assertEquals(size[1], 15, EPSILON);
    }
	
    //Tests relatifs a l'intensite maximale
    @Test(expected = IllegalArgumentException.class)
	public void intensity1(){
	image.setIntensityMaxScale(-1);
    }

    @Test(expected = IllegalArgumentException.class)
	public void intensity2(){
	image.setIntensityMaxScale(300);
    }

    @Test
	public void intensity3(){
	image.setIntensityMaxScale(241);
	assertEquals(image.getIntensityMaxScale(), 241, EPSILON);
    }
	
    @Test
	public void intensity4(){
	assertEquals(image.getIntensityMaxScale(), 223, EPSILON);
    }

    //Tests relatifs a la matrice de couleur
    @Test(expected = IllegalArgumentException.class)
	public void color1(){
	image.addColor(4,51,new Color(0.1,0.5,0),true);
    }
	
    public void color2(){
	image.addColor(51,4,new Color(0.1,0.5,0),true);
    }

    @Test(expected = IllegalArgumentException.class)
	public void color3(){
	image.addColor(4,4,null,true);
    }
	
    @Test(expected = IllegalArgumentException.class)
	public void color1bis(){
	image.addColor(4,51,new Color(0.1,0.5,0),false);
    }
	
    public void color2bis(){
	image.addColor(51,4,new Color(0.1,0.5,0),false);
    }

    @Test(expected = IllegalArgumentException.class)
	public void color3bis(){
	image.addColor(4,4,null,false);
    }	
	
    @Test
	public void testColorReplace(){
	//Rappel : 	image.addColor(i,j,new Color(1,0.5,0),false);
	image.addColor(4,4, new Color(0.7,0.7,0.7), false);
	Color colorMap[][] = image.getColorMap();
	assertEquals(colorMap[4][4].getRedComp(), 0.7, EPSILON);
	assertEquals(colorMap[4][4].getGreenComp(), 0.7, EPSILON);
	assertEquals(colorMap[4][4].getBlueComp(), 0.7, EPSILON);
    }
	
    @Test
	public void testColorReplaceWithSum(){
	//Rappel : 	image.addColor(i,j,new Color(1,0.5,0),false);
	image.addColor(4,4, new Color(0.7,0.7,0.7), true);
	Color colorMap[][] = image.getColorMap();
	assertEquals(colorMap[4][4].getRedComp(), 1, EPSILON);
	assertEquals(colorMap[4][4].getGreenComp(), 0.85, EPSILON);
	assertEquals(colorMap[4][4].getBlueComp(), 0.7, EPSILON);
    }
	

    //Test relatifs à l'exportation
    @Test(expected=IllegalArgumentException.class)
	public void testWrongParam(){
	image.exportPPM(null);
    }
	
    @Test
	public void testExport(){
	//Creation d'un ViewPoint de test
	Point pt1 = new Point(1,0,0);
	Point pt2 = new Point(0,1,0);
	Point pt3 = new Point(0,0,1);
	Screen scr = new Screen(new Point[]{pt1, pt2, pt3}, 100, 150);
		
	//Exportation d'une image
	String path = TestImagePPM.class.getResource(".").getPath();
	image.exportPPM(new ViewPoint(new Point(0,0,0), scr, path+"VueDeTest")); 
	try{
	    //Parsing de l'image cree
	    File file = new File(path+"VueDeTest.ppm");
	    FileReader fRead = new FileReader(file);
	    LineNumberReader reader = new LineNumberReader(fRead);
			
	    //En-tete
	    String format = reader.readLine();
	    String strSize[] = reader.readLine().split(" ");
	    int size[] = {Integer.parseInt(strSize[0]), Integer.parseInt(strSize[1])};
	    int intensityMaxScale = Integer.parseInt(reader.readLine());
			
	    assertTrue(format.equals("P3"));
	    int imgSize[] = image.getSize();
	    assertEquals(size[0], imgSize[1]);
	    assertEquals(size[1], imgSize[0]);		
	    assertEquals(intensityMaxScale, image.getIntensityMaxScale());
			
	    //Image
	    String buffer[][] = new String[10][45];
	    String line;
	    int k = 0;
	    while((line = reader.readLine()) != null){
		buffer[k] = line.split(" ");
		k++;
	    }
	    for(int i = 0; i < 10; i++){
		for(int j = 0; j < 45; j+=3){
		    assertEquals(Double.parseDouble(buffer[i][j]), 255, EPSILON);
		    assertEquals(Double.parseDouble(buffer[i][j+1]), 127, EPSILON);
		    assertEquals(Double.parseDouble(buffer[i][j+2]), 0, EPSILON);
		}
	    }
	    file.delete();
	    reader.close();
	    fRead.close();
	}catch(Exception e){
	    fail("Erreur lors du parsing.");
	}
    }
	
    public static void main(String args[]){
	org.junit.runner.JUnitCore.main(TestImagePPM.class.getName());
    }
}

