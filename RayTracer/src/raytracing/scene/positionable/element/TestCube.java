/** Classe de tests de Cube
 * @author Matthias Benkort
 * @version 1.2
 */

package raytracing.scene.positionable.element;

import raytracing.scene.Color;
import org.junit.*;
import static org.junit.Assert.*;
import raytracing.scene.geometry.*;
import raytracing.scene.geometry.Vector;
import raytracing.scene.*;
import java.util.*;

public class TestCube{
    private Cube c;
    
    @Before
	public void setUp(){
	Point vertexs[] = new Point[3];
	vertexs[0] = new Point(0,0,0);
	vertexs[1] = new Point(1,0,0);
	vertexs[2] = new Point(0,1,0);

	Material mat = new Material(
				    new Color(0,0,0),
				    new Color(0,0,0),
				    0,
				    1,
				    new Color(0,0,0),
				    new Color(0,0,0),
				    1);

	this.c = new Cube(vertexs, "monSuperCube", mat);
    }    

    //Tests relatifs aux getters et setters des sommets
    @Test(expected = IllegalArgumentException.class)
	public void testSetters1(){
	c.setVertexs(new Point[]{new Point(0,0,0), new Point(0,0,0)});
    }
	
    @Test(expected=IllegalArgumentException.class)
	public void testSetters2(){
	c.setVertexs(new Point[]{new Point(0,0,0),
				 new Point(0,0,0), 
				 new Point(0,0,0), 
				 new Point(0,0,0)});
    }


    @Test(expected=IllegalArgumentException.class)
	public void testSetters3(){
	c.setVertexs(new Point[]{null,
				 new Point(0,0,0),
				 new Point(0,0,0)});
    }

    @Test(expected=IllegalArgumentException.class)
	public void testSetters4(){
	c.setVertexs(new Point[]{new Point(0,0,0),
				 null,
				 new Point(0,0,0)});
    }

    @Test(expected=IllegalArgumentException.class)
	public void testSetters5(){
	c.setVertexs(new Point[]{new Point(0,0,0),
				 new Point(0,0,0),
				 null});
    }

    @Test(expected=IllegalArgumentException.class)
	public void testSetter6(){
	c.setVertexs(new Point[]{
		new Point(1,2,3), //Nous irons aux bois
		new Point(4,5,6), //Ceuillir des cerises
		new Point(7,8,9)}); //Dans mon panier neuf.
    }
	
    @Test
	public void testGetter(){
	c.setVertexs(new Point[]{
		new Point(1,1,0),
		new Point(4,1,0),
		new Point(1,4,0)
	    });
					
	Point vertexs[] = c.getVertexs();
	assertTrue(vertexs[0].isEqual(new Point(1,1,0)));
	assertTrue(vertexs[1].isEqual(new Point(4,1,0)));
	assertTrue(vertexs[2].isEqual(new Point(1,4,0)));
    }
	
    //Test de getIntersection

    //Intersection par la face du haut
    @Test
	public void testIntersection1(){
	Ray ray;
	Point inters;
	ray = new Ray(new Point(0.2, 10, 0.2), new Vector(0,-1,0));
	inters = c.getIntersection(ray);
	assertTrue(inters.isEqual(new Point(0.2,1,0.2)));
        
	ray = new Ray(new Point(0.8, 10, 0.8), new Vector(0,-1,0));
	inters = c.getIntersection(ray);
	assertTrue(inters.isEqual(new Point(0.8,1,0.8)));

	ray = new Ray(new Point(0.2, 0.5, 0.2), new Vector(0,1,0));
	inters = c.getIntersection(ray);
	assertTrue(inters.isEqual(new Point(0.2,1,0.2)));
        
	ray = new Ray(new Point(0.8, 0.5, 0.8), new Vector(0,1,0));
	inters = c.getIntersection(ray);
	assertTrue(inters.isEqual(new Point(0.8,1,0.8)));
    }

    //Intersection par la face du bas
    @Test
	public void testIntersection2(){
	Ray ray;
	Point inters;
	ray = new Ray(new Point(0.2, -10, 0.2), new Vector(0,1,0));
	inters = c.getIntersection(ray);
	assertTrue(inters.isEqual(new Point(0.2,0,0.2)));
        
	ray = new Ray(new Point(0.8, -10, 0.8), new Vector(0,1,0));
	inters = c.getIntersection(ray);
	assertTrue(inters.isEqual(new Point(0.8,0,0.8)));

	ray = new Ray(new Point(0.2, 0.5, 0.2), new Vector(0,-1,0));
	inters = c.getIntersection(ray);
	assertTrue(inters.isEqual(new Point(0.2,0,0.2)));
        
	ray = new Ray(new Point(0.8, 0.5, 0.8), new Vector(0,-1,0));
	inters = c.getIntersection(ray);
	assertTrue(inters.isEqual(new Point(0.8,0,0.8)));
    }
	
    //Intersection par la face de gauche
    @Test
	public void testIntersection3(){
	Ray ray;
	Point inters;
	ray = new Ray(new Point(-10, 0.2, 0.2), new Vector(1,0,0));
	inters = c.getIntersection(ray);
	assertTrue(inters.isEqual(new Point(0,0.2,0.2)));

	ray = new Ray(new Point(-10, 0.8, 0.8), new Vector(1,0,0));
	inters = c.getIntersection(ray);
	assertTrue(inters.isEqual(new Point(0,0.8,0.8)));

	ray = new Ray(new Point(0.5, 0.2, 0.2), new Vector(-1,0,0));
	inters = c.getIntersection(ray);
	assertTrue(inters.isEqual(new Point(0,0.2,0.2)));

	ray = new Ray(new Point(0.5, 0.8, 0.8), new Vector(-1,0,0));
	inters = c.getIntersection(ray);
	assertTrue(inters.isEqual(new Point(0,0.8,0.8)));
    }

    //Intersection par la face de droite
    @Test
	public void testIntersection4(){
	Ray ray;
	Point inters;
	ray = new Ray(new Point(10, 0.2, 0.2), new Vector(-1,0,0));
	inters = c.getIntersection(ray);
	assertTrue(inters.isEqual(new Point(1,0.2,0.2)));

	ray = new Ray(new Point(10, 0.8, 0.8), new Vector(-1,0,0));
	inters = c.getIntersection(ray);
	assertTrue(inters.isEqual(new Point(1,0.8,0.8)));

	ray = new Ray(new Point(0.5, 0.2, 0.2), new Vector(1,0,0));
	inters = c.getIntersection(ray);
	assertTrue(inters.isEqual(new Point(1,0.2,0.2)));

	ray = new Ray(new Point(0.5, 0.8, 0.8), new Vector(1,0,0));
	inters = c.getIntersection(ray);
	assertTrue(inters.isEqual(new Point(1,0.8,0.8)));
    }

    //Intersection par la face du fond
    @Test
	public void testIntersection5(){
	Ray ray;
	Point inters;
	ray = new Ray(new Point(0.2, 0.2, -10), new Vector(0,0,1));
	inters = c.getIntersection(ray);
	assertTrue(inters.isEqual(new Point(0.2,0.2,0)));

	ray = new Ray(new Point(0.8, 0.8, -10), new Vector(0,0,1));
	inters = c.getIntersection(ray);
	assertTrue(inters.isEqual(new Point(0.8,0.8,0)));

	ray = new Ray(new Point(0.2, 0.2, 0.5), new Vector(0,0,-1));
	inters = c.getIntersection(ray);
	assertTrue(inters.isEqual(new Point(0.2,0.2,0)));

	ray = new Ray(new Point(0.8, 0.8, 0.5), new Vector(0,0,-1));
	inters = c.getIntersection(ray);
	assertTrue(inters.isEqual(new Point(0.8,0.8,0)));
    }

    //Intersection par la face frontale
    @Test
	public void testIntersection6(){
	Ray ray;
	Point inters;
	ray = new Ray(new Point(0.2, 0.2, 10), new Vector(0,0,-1));
	inters = c.getIntersection(ray);
	assertTrue(inters.isEqual(new Point(0.2,0.2,1)));

	ray = new Ray(new Point(0.8, 0.8, 10), new Vector(0,0,-1));
	inters = c.getIntersection(ray);
	assertTrue(inters.isEqual(new Point(0.8,0.8,1)));

	ray = new Ray(new Point(0.2, 0.2, 0.5), new Vector(0,0,1));
	inters = c.getIntersection(ray);
	assertTrue(inters.isEqual(new Point(0.2,0.2,1)));

	ray = new Ray(new Point(0.8, 0.8, 0.5), new Vector(0,0,1));
	inters = c.getIntersection(ray);
	assertTrue(inters.isEqual(new Point(0.8,0.8,1)));
    }

    //Face du fond
    @Test(expected=NoIntersectionException.class)
	public void testNoIntersection1(){
	Ray ray = new Ray(new Point(1.1,0.5,0), new Vector(0,0,1));
	c.getIntersection(ray);
    }
    @Test(expected=NoIntersectionException.class)
	public void testNoIntersection2(){
	Ray ray = new Ray(new Point(1.1,0.5,0), new Vector(0,0,-1));
	c.getIntersection(ray);
    }
    @Test(expected=NoIntersectionException.class)
	public void testNoIntersection3(){
	Ray ray = new Ray(new Point(-0.1,0.5,0), new Vector(0,0,1));
	c.getIntersection(ray);
    }
    @Test(expected=NoIntersectionException.class)
	public void testNoIntersection4(){
	Ray ray = new Ray(new Point(-0.1,0.5,0), new Vector(0,0,-1));
	c.getIntersection(ray);
    }

    //Face frontale
    @Test(expected=NoIntersectionException.class)
	public void testNoIntersection5(){
	Ray ray = new Ray(new Point(1.1,0.5,1), new Vector(0,0,1));
	c.getIntersection(ray);
    }
    @Test(expected=NoIntersectionException.class)
	public void testNoIntersection6(){
	Ray ray = new Ray(new Point(1.1,0.5,1), new Vector(0,0,-1));
	c.getIntersection(ray);
    }
    @Test(expected=NoIntersectionException.class)
	public void testNoIntersection7(){
	Ray ray = new Ray(new Point(-0.1,0.5,1), new Vector(0,0,1));
	c.getIntersection(ray);
    }
    @Test(expected=NoIntersectionException.class)
	public void testNoIntersection8(){
	Ray ray = new Ray(new Point(-0.1,0.5,1), new Vector(0,0,-1));
	c.getIntersection(ray);
    }

    //Face du dessus
    @Test(expected=NoIntersectionException.class)
	public void testNoIntersection9(){
	Ray ray = new Ray(new Point(1.1,1,0.5), new Vector(0,1,0));
	c.getIntersection(ray);
    }

    @Test(expected=NoIntersectionException.class)
	public void testNoIntersection10(){
	Ray ray = new Ray(new Point(1.1,1,0.5), new Vector(0,-1,0));
	c.getIntersection(ray);
    }

    @Test(expected=NoIntersectionException.class)
	public void testNoIntersection11(){
	Ray ray = new Ray(new Point(-0.1,1,0.5), new Vector(0,1,0));
	c.getIntersection(ray);
    }

    @Test(expected=NoIntersectionException.class)
	public void testNoIntersection12(){
	Ray ray = new Ray(new Point(-0.1,1,0.5), new Vector(0,-1,0));
	c.getIntersection(ray);
    }

    //Face du dessous
    @Test(expected=NoIntersectionException.class)
	public void testNoIntersection13(){
	Ray ray = new Ray(new Point(1.1,0,0.5), new Vector(0,1,0));
	c.getIntersection(ray);
    }

    @Test(expected=NoIntersectionException.class)
	public void testNoIntersection14(){
	Ray ray = new Ray(new Point(1.1,0,0.5), new Vector(0,-1,0));
	c.getIntersection(ray);
    }

    @Test(expected=NoIntersectionException.class)
	public void testNoIntersection15(){
	Ray ray = new Ray(new Point(-0.1,0,0.5), new Vector(0,1,0));
	c.getIntersection(ray);
    }

    @Test(expected=NoIntersectionException.class)
	public void testNoIntersection16(){
	Ray ray = new Ray(new Point(-0.1,0,0.5), new Vector(0,-1,0));
	c.getIntersection(ray);
    }

    //Face de droite
    @Test(expected=NoIntersectionException.class)
	public void testNoIntersection17(){
	Ray ray = new Ray(new Point(1,1.1,0.5), new Vector(1,0,0));
	c.getIntersection(ray);
    }

    @Test(expected=NoIntersectionException.class)
	public void testNoIntersection18(){
	Ray ray = new Ray(new Point(1,1.1,0.5), new Vector(-1,0,0));
	c.getIntersection(ray);
    }

    @Test(expected=NoIntersectionException.class)
	public void testNoIntersection19(){
	Ray ray = new Ray(new Point(1,-0.1,0.5), new Vector(1,0,0));
	c.getIntersection(ray);
    }

    @Test(expected=NoIntersectionException.class)
	public void testNoIntersection20(){
	Ray ray = new Ray(new Point(1,-0.1,0.5), new Vector(-1,0,0));
	c.getIntersection(ray);
    }

    //Face de gauche
    @Test(expected=NoIntersectionException.class)
	public void testNoIntersection21(){
	Ray ray = new Ray(new Point(0,1.1,0.5), new Vector(1,0,0));
	c.getIntersection(ray);
    }

    @Test(expected=NoIntersectionException.class)
	public void testNoIntersection22(){
	Ray ray = new Ray(new Point(0,1.1,0.5), new Vector(-1,0,0));
	c.getIntersection(ray);
    }

    @Test(expected=NoIntersectionException.class)
	public void testNoIntersection23(){
	Ray ray = new Ray(new Point(0,-0.1,0.5), new Vector(1,0,0));
	c.getIntersection(ray);
    }

    @Test(expected=NoIntersectionException.class)
	public void testNoIntersection24(){
	Ray ray = new Ray(new Point(0,-0.1,0.5), new Vector(-1,0,0));
	c.getIntersection(ray);
    }

    // Autre test d'intersection suite aux bugs du tracer
    @Test(expected=NoIntersectionException.class)
	public void testNoIntersectionBis1(){
	c.setVertexs(new Point[]{
		new Point(-1.5,-1.5,0),
		new Point(1.5,-1.5,0),
		new Point(-1.5,1.5,0)
	    });
	Ray ray = new Ray(new Point(0,0,10), new Vector(-0.4427748087558073,0.4427748087558073,-0.7796800224851965));
	Point p = c.getIntersection(ray);

	System.out.println(p);
    }

    /*
      Point vertexs[] = new Point[3];
      vertexs[0] = new Point(0,0,0);
      vertexs[1] = new Point(1,0,0);
      vertexs[2] = new Point(0,1,0);
    */

    //Tests relatifs à la normale

    //Normal de la face du fond.
    @Test
	public void testNormal1(){
	Vector normal = c.getNormal(new Point(0.35,0.35,0));
	assertEquals(normal.getX(), 0, Geometry.EPSILON);
	assertEquals(normal.getY(), 0, Geometry.EPSILON);
	assertEquals(normal.getZ(), -1, Geometry.EPSILON);

	normal = c.getNormal(new Point(0.85,0.85,0));
	assertEquals(normal.getX(), 0, Geometry.EPSILON);
	assertEquals(normal.getY(), 0, Geometry.EPSILON);
	assertEquals(normal.getZ(), -1, Geometry.EPSILON);		
    }

    //Normal de la face frontale.
    @Test
	public void testNormal2(){
	Vector normal = c.getNormal(new Point(0.35,0.35,1));
	assertEquals(normal.getX(), 0, Geometry.EPSILON);
	assertEquals(normal.getY(), 0, Geometry.EPSILON);
	assertEquals(normal.getZ(), 1, Geometry.EPSILON);

	normal = c.getNormal(new Point(0.85,0.85,1));
	assertEquals(normal.getX(), 0, Geometry.EPSILON);
	assertEquals(normal.getY(), 0, Geometry.EPSILON);
	assertEquals(normal.getZ(), 1, Geometry.EPSILON);		
    }

    //Normal de la face du dessus.
    @Test
	public void testNormal3(){
	Vector normal = c.getNormal(new Point(0.35,1,0.35));
	assertEquals(normal.getX(), 0, Geometry.EPSILON);
	assertEquals(normal.getY(), 1, Geometry.EPSILON);
	assertEquals(normal.getZ(), 0, Geometry.EPSILON);

	normal = c.getNormal(new Point(0.85,1,0.85));
	assertEquals(normal.getX(), 0, Geometry.EPSILON);
	assertEquals(normal.getY(), 1, Geometry.EPSILON);
	assertEquals(normal.getZ(), 0, Geometry.EPSILON);		
    }	

    //Normal de la face du dessous.
    @Test
	public void testNormal4(){
	Vector normal = c.getNormal(new Point(0.35,0,0.35));
	assertEquals(normal.getX(), 0, Geometry.EPSILON);
	assertEquals(normal.getY(), -1, Geometry.EPSILON);
	assertEquals(normal.getZ(), 0, Geometry.EPSILON);

	normal = c.getNormal(new Point(0.85,0,0.85));
	assertEquals(normal.getX(), 0, Geometry.EPSILON);
	assertEquals(normal.getY(), -1, Geometry.EPSILON);
	assertEquals(normal.getZ(), 0, Geometry.EPSILON);		
    }

    //Normal de la face de droite.
    @Test
	public void testNormal5(){
	Vector normal = c.getNormal(new Point(1,0.35,0.35));
	assertEquals(normal.getX(), 1, Geometry.EPSILON);
	assertEquals(normal.getY(), 0, Geometry.EPSILON);
	assertEquals(normal.getZ(), 0, Geometry.EPSILON);

	normal = c.getNormal(new Point(1,0.85,0.85));
	assertEquals(normal.getX(), 1, Geometry.EPSILON);
	assertEquals(normal.getY(), 0, Geometry.EPSILON);
	assertEquals(normal.getZ(), 0, Geometry.EPSILON);		
    }

    //Normal de la face de gauche.
    @Test
	public void testNormal6(){
	Vector normal = c.getNormal(new Point(0,0.35,0.35));
	assertEquals(normal.getX(), -1, Geometry.EPSILON);
	assertEquals(normal.getY(), 0, Geometry.EPSILON);
	assertEquals(normal.getZ(), 0, Geometry.EPSILON);

	normal = c.getNormal(new Point(0,0.85,0.85));
	assertEquals(normal.getX(), -1, Geometry.EPSILON);
	assertEquals(normal.getY(), 0, Geometry.EPSILON);
	assertEquals(normal.getZ(), 0, Geometry.EPSILON);		
    }

    public static void main(String args[]) {
	org.junit.runner.JUnitCore.main(TestCube.class.getName());
    }
}
