/** Classe de tests de Triangle
 * @author Matthias Benkort
 * @version 1.1
 */ 
package raytracing.scene.positionable.element;

import raytracing.scene.Color;
import raytracing.scene.geometry.*;
import raytracing.scene.geometry.Vector;
import raytracing.scene.Ray;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;

public class TestTriangle{
    private Triangle t;
	
    @Before
	public void setUp(){
	t = new Triangle(new Point[]{
		new Point(0,0,0),
		new Point(1,0,0),
		new Point(0,1,0)},
	    "Triangle",
	    new Material(new Color(0,0,0), new Color(0,0,0), 0,1, new Color(0,0,0), new Color(0,0,0), 1)
	    );
    }
	
    //Facette de front
    @Test
	public void testIntersection1(){
	Ray ray = new Ray(new Point(0.3,0.3,5), new Vector(0,0,-1));
	Point inters = t.getIntersection(ray);
	assertTrue(inters.isEqual(new Point(0.3,0.3,0)));
    }
	
    @Test(expected = NoIntersectionException.class)
	public void testIntersection2(){
	Ray ray = new Ray(new Point(0.3,0.3,5), new Vector(0,0,1));
	t.getIntersection(ray);
    }	
	
    @Test
	public void testIntersection3(){
	Ray ray = new Ray(new Point(0.3,0.3,-5), new Vector(0,0,1));
	Point inters = t.getIntersection(ray);
	assertTrue(inters.isEqual(new Point(0.3,0.3,0)));
    }
	
    @Test(expected = NoIntersectionException.class)
	public void testIntersection4(){
	Ray ray = new Ray(new Point(0.3,0.3,-5), new Vector(0,0,-1));
	t.getIntersection(ray);
    }	
	
    @Test(expected = NoIntersectionException.class)
	public void testIntersection5(){
	Ray ray = new Ray(new Point(1,1,5), new Vector(0,0,-1));
	t.getIntersection(ray);
    }	
	
    @Test(expected = NoIntersectionException.class)
	public void testIntersection4bis(){
	Ray ray = new Ray(new Point(0.3,0.3,0), new Vector(0,0,-1));
	t.getIntersection(ray);
    }	
	
    @Test(expected = NoIntersectionException.class)
	public void testIntersection5bis(){
	Ray ray = new Ray(new Point(0.3,0.3,0), new Vector(0,0,1));
	t.getIntersection(ray);
    }	
    //Facette de dessus
    @Test
	public void testIntersection6(){
	t.setPoints(new Point[]{new Point(0,0,0), new Point(0,0,1), new Point(1,0,0)});
	Ray ray = new Ray(new Point(0.3,5,0.3), new Vector(0,-1,0));
	Point inters = t.getIntersection(ray);
	assertTrue(inters.isEqual(new Point(0.3,0,0.3)));
    }
	
    @Test(expected = NoIntersectionException.class)
	public void testIntersection7(){
	t.setPoints(new Point[]{new Point(0,0,0), new Point(0,0,1), new Point(1,0,0)});
	Ray ray = new Ray(new Point(0.3,5,0.3), new Vector(0,1,0));
	t.getIntersection(ray);
    }	
	
    @Test
	public void testIntersection8(){
	t.setPoints(new Point[]{new Point(0,0,0), new Point(0,0,1), new Point(1,0,0)});
	Ray ray = new Ray(new Point(0.3,-5,0.3), new Vector(0,1,0));
	Point inters = t.getIntersection(ray);
	assertTrue(inters.isEqual(new Point(0.3,0,0.3)));
    }
	
    @Test(expected = NoIntersectionException.class)
	public void testIntersection9(){
	t.setPoints(new Point[]{new Point(0,0,0), new Point(0,0,1), new Point(1,0,0)});
	Ray ray = new Ray(new Point(0.3,-5,0.3), new Vector(0,-1,0));
	t.getIntersection(ray);
    }	
	
    @Test(expected = NoIntersectionException.class)
	public void testIntersection10(){
	t.setPoints(new Point[]{new Point(0,0,0), new Point(0,0,1), new Point(1,0,0)});
	Ray ray = new Ray(new Point(0.3, 0,0.3), new Vector(0,1,0));
	t.getIntersection(ray);
    }	
	
    @Test(expected = NoIntersectionException.class)
	public void testIntersection11(){
	t.setPoints(new Point[]{new Point(0,0,0), new Point(0,0,1), new Point(1,0,0)});
	Ray ray = new Ray(new Point(0.3, 0,0.3), new Vector(0,-1,0));
	t.getIntersection(ray);
    }	
	
    //Facette de coté, si l'on puit dire.
    @Test
	public void testIntersection12(){
	t.setPoints(new Point[]{new Point(0,0,0), new Point(0,1,0), new Point(0,0,1)});
	Ray ray = new Ray(new Point(5,0.3,0.3), new Vector(-1,0,0));
	Point inters = t.getIntersection(ray);
	assertTrue(inters.isEqual(new Point(0,0.3,0.3)));
    }
	
    @Test(expected = NoIntersectionException.class)
	public void testIntersection13(){
	t.setPoints(new Point[]{new Point(0,0,0), new Point(0,1,0), new Point(0,0,1)});
	Ray ray = new Ray(new Point(5,0.3,0.3), new Vector(1,0,0));
	t.getIntersection(ray);
    }	

    @Test
	public void testIntersection14(){
	t.setPoints(new Point[]{new Point(0,0,0), new Point(0,1,0), new Point(0,0,1)});
	Ray ray = new Ray(new Point(-5,0.3,0.3), new Vector(1,0,0));
	Point inters = t.getIntersection(ray);
	assertTrue(inters.isEqual(new Point(0,0.3,0.3)));
    }
	
    @Test(expected = NoIntersectionException.class)
	public void testIntersection15(){
	t.setPoints(new Point[]{new Point(0,0,0), new Point(0,1,0), new Point(0,0,1)});
	Ray ray = new Ray(new Point(-5,0.3,0.3), new Vector(-1,0,0));
	t.getIntersection(ray);
    }		
	
    @Test(expected = NoIntersectionException.class)
	public void testIntersection16(){
	t.setPoints(new Point[]{new Point(0,0,0), new Point(0,1,0), new Point(0,0,1)});
	Ray ray = new Ray(new Point(0,0.3,0.3), new Vector(1,0,0));
	Point inters = t.getIntersection(ray);
	assertTrue(inters.isEqual(new Point(0,0.3,0.3)));
    }
	
    @Test(expected = NoIntersectionException.class)
	public void testIntersection17(){
	t.setPoints(new Point[]{new Point(0,0,0), new Point(0,1,0), new Point(0,0,1)});
	Ray ray = new Ray(new Point(0,0.3,0.3), new Vector(-1,0,0));
	t.getIntersection(ray);
    }	
	
    public static void main(String args[]) {
	org.junit.runner.JUnitCore.main(TestTriangle.class.getName());
    }
}
