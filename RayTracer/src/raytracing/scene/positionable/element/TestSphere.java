/** Classe de tests de Sphere
 * @author Matthias Benkort
 * @version 1.6
 */ 

package raytracing.scene.positionable.element;

import raytracing.scene.Color;
import raytracing.scene.geometry.*;
import raytracing.scene.geometry.Vector;
import raytracing.scene.Ray;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;

public class TestSphere{
    private Sphere s;
    public static final double EPSILON = Math.pow(10, -14);
	
    @Before
	public void setUp(){
	s = new Sphere(new Point(0,0,0),
		       10,
		       "Sphere1",
		       new Material(
				    new Color(0,0,0), 
				    new Color(0,0,0),
				    0,
				    1,
				    new Color(0,0,0), 
				    new Color(0,0,0),
				    1
				    )
		       );
    }
	
    //Test relatif au centre
    @Test(expected = IllegalArgumentException.class)
	public void center1(){
	s.setCenter(null);
    }
	
    @Test
	public void center2(){
	s.setCenter(new Point(1,2,3));
	assertEquals(s.getCenter().getX(), 1, EPSILON);
	assertEquals(s.getCenter().getY(), 2, EPSILON);
	assertEquals(s.getCenter().getZ(), 3, EPSILON);
    }
	
    //Test relatif au rayon
    @Test(expected = IllegalArgumentException.class)
	public void radius1(){
	s.setRadius(0);
    }
	
    @Test(expected = IllegalArgumentException.class)
	public void radius2(){
	s.setRadius(-10);
    }
	
    @Test
	public void radius3(){
	s.setRadius(14);
	assertEquals(s.getRadius(), 14, EPSILON);
    }
	
    //Test relatif a l'intersection
	
    //Rayon passant a cote de la sphere.
    @Test(expected = NoIntersectionException.class)
	public void testNoIntersection(){
	s.getIntersection(new Ray(new Point(10,0,15), new Vector(-1,0,0)));
    }
		
    // Rayon qui intersecte
    @Test()
	public void testIntersect(){
	Point p = s.getIntersection(new Ray(new Point(20,0,0), new Vector(-1,0,0)));
	assertTrue(p.isEqual(new Point(10, 0, 0)));
    }	
		
    //Point source tangeant a la sphere
    @Test(expected = NoIntersectionException.class)
	public void testTangeant1(){
	Point p = s.getIntersection(new Ray(new Point(0,0,10), new Vector(0,0,1)));
    }
		
    @Test()
	public void testTangeant2(){
	Point p = s.getIntersection(new Ray(new Point(0,0,10), new Vector(0,0,-1)));
	assertTrue(p.isEqual(new Point(0, 0, -10)));
    }
		
    //Rayon tangeant a la sphere
    @Test(expected = NoIntersectionException.class)
	public void testTangeant3(){
	s.getIntersection(new Ray(new Point(20, 0, 10), new Vector(-1,0,0)));
    }
		
    //Point a l'interieur de la sphere
    @Test()
	public void testInside(){
	Point p = s.getIntersection(new Ray(new Point(0,0,0), new Vector(0, 1, 0)));
	assertTrue(p.isEqual(new Point(0, 10, 0)));
    }

    @Test()
	public void testInside2(){
	Point p = s.getIntersection(new Ray(new Point(0,-5,0), new Vector(0, -1, 0)));
	assertTrue(p.isEqual(new Point(0, -10, 0)));
    }

    @Test()
	public void testInside3(){
	Point p = s.getIntersection(new Ray(new Point(0,5,0), new Vector(0, -1, 0)));
	assertTrue(p.isEqual(new Point(0, -10, 0)));
    }



		
    @Test(expected = NoIntersectionException.class)
	public void testTwoSphere(){
	Sphere s1 = new Sphere(new Point(0,0,0),
			       1,
			       "s1",
			       new Material(
					    new Color(0,0,0), 
					    new Color(0,0,0),
					    0,
					    1,
					    new Color(0,0,0), 
					    new Color(0,0,0), 
					    1
					    )
			       );
	Sphere s2 = new Sphere(new Point(5,0,0),
			       1,
			       "s2",
			       new Material(
					    new Color(0,0,0), 
					    new Color(0,0,0),
					    0,
					    1,
					    new Color(0,0,0), 
					    new Color(0,0,0), 
					    1
					    )
			       );
	//Definition d'un point sur la sphere, et d'une source de lum.
	Point sourceLum = new Point(10,0,0);
	Point interS = new Point(6,0,0);
			
	//Depart sur la sphere, en direction de la source de lumiere..
	Ray ray = new Ray(interS, new Vector(interS, sourceLum));
			
	//Doit lever une exception...
	s1.getIntersection(ray);	
    }
		
    //Tests relatifs a la normale
    @Test()
	public void testNormal(){
	Vector normal = s.getNormal(new Point(0, 0, 10));
	assertEquals(normal.getX(), 0, EPSILON);
	assertEquals(normal.getY(), 0, EPSILON);
	assertEquals(normal.getZ(), 1, EPSILON);
    }
		
    @Test(expected = RuntimeException.class)
	public void testNormal2(){
	s.getNormal(new Point(1, 0, 10));
    }	
	
	
    public static void main(String args[]) {
	org.junit.runner.JUnitCore.main(TestSphere.class.getName());
    }
}
