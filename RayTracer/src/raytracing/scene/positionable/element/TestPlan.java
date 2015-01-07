package raytracing.scene.positionable.element;

import raytracing.scene.Color;
import raytracing.scene.geometry.*;
import raytracing.scene.geometry.Vector;
import raytracing.scene.Ray;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;


/** Classe de test de la classe point
 *@author Maxime Herve
 *@version 1.0
 */


public class TestPlan {
    private Plan p ;
    private Material m;
    public static final double EPSILON = 1e-6 ; 
    private Point[] points , points2 ;
    private Vector r ; 

	

    @Before
	public void setUp(){
	points = new Point[3];
	points[1] = new Point(0,0,0);
	points[2] = new Point(1,0,0);
	points[3] = new Point(0,1,0);
		
	p = new Plan(points,"plan",m);

    }
	


    // Tests relatifs à l'intersection


    // Plan de front

    //Rayon n'intercectant pas le plan ( point source au dessus du plan et rayon parallèle au plan )
		
    @Test(expected = NoIntersectionException.class )
	public void testIntersection1() {
	p.getIntersection( new Ray ( new Point ( 1,1,1) , new Vector (1,1,0) ));
		
		
    }

    // Rayon n'interceptant pas le plan ( point source au dessus du plan et rayon dirigé dans la direction de la normale )

    @Test(expected = NoIntersectionException.class )
	public void testIntersection2() {
	p.getIntersection ( new Ray ( new Point(0,0,1) , new Vector(0,0,1)));
    }



    // Rayon intercectant le plan ( point source au dessu du plan et rayon dirigé orthogoanalement vers le plan )

    @Test
	public void testIntersection3(){
	Point i = p.getIntersection ( new Ray ( new Point( 0,0,1) , new Vector ( 0,0,-1)))
	    ;
	assertTrue ( i.isEqual( new Point(0,0,0)));
    }

    // Rayon intercectant le plan ( point source au dessous du plan et rayon dirigé orthogoanalement vers le plan )

    @Test
	public void testIntersection4(){
	Point i = p.getIntersection ( new Ray ( new Point( 0,0,-1) , new Vector ( 0,0,1)))
	    ;
	assertTrue ( i.isEqual( new Point(0,0,0)));
    }

    // Rayon n'intercectant pas le plan ( point source au dessous du plan et rayon non dirigé  vers le plan )

    @Test(expected = NoIntersectionException.class)
	public void testIntersection5(){
	Point i = p.getIntersection ( new Ray ( new Point( 0,0,-1) , new Vector ( 0,0,-1)))
	    ;

    }

	
    // Point source dans le plan ( par convention on choisi de ne pas avoir d'intersection )	

    @Test(expected = NoIntersectionException.class)
	public void testIntersection6(){
	Point i = p.getIntersection ( new Ray ( new Point( 0,0,0) , new Vector ( 0,0,-1)))
	    ;

    }


    @Test(expected = NoIntersectionException.class)
	public void testIntersection7(){
	Point i = p.getIntersection ( new Ray ( new Point( 0,0,0) , new Vector ( 0,0,-1)))
	    ;

    }

    //Plan de dessus

    @Test
	public void testIntersection8(){
	p.setPoints(new Point[]{new Point(0,0,0), new Point(0,0,1), new Point(1,0,0)});
	Ray ray = new Ray(new Point(0,1,0), new Vector(0,-1,0));
	Point i = p.getIntersection(ray);
	assertTrue(i.isEqual(new Point(0,0,0)));
    }

    @Test(expected=NoIntersectionException.class)
	public void testIntersection9(){
	p.setPoints(new Point[]{new Point(0,0,0), new Point(0,0,1), new Point(1,0,0)});
	Ray ray = new Ray(new Point(0,1,0), new Vector(0,1,0));
	Point inters = p.getIntersection(ray);
    }

    @Test
	public void testIntersection10(){
	p.setPoints(new Point[]{new Point(0,0,0), new Point(0,0,1), new Point(1,0,0)});
	Ray ray = new Ray(new Point(0,-1,0), new Vector(0,1,0));
	Point inters = p.getIntersection(ray);
	assertTrue(inters.isEqual(new Point(0,0,0)));
    }

    @Test(expected=NoIntersectionException.class)
	public void testIntersection11(){
	p.setPoints(new Point[]{new Point(0,0,0), new Point(0,0,1), new Point(1,0,0)});
	Ray ray = new Ray(new Point(0,-1,0), new Vector(0,1,0));
	Point inters = p.getIntersection(ray);

    }

    @Test(expected=NoIntersectionException.class)
	public void testIntersection12(){
	p.setPoints(new Point[]{new Point(0,0,0), new Point(0,0,1), new Point(1,0,0)});
	Ray ray = new Ray(new Point(0,0,0), new Vector(0,1,0));
	Point inters = p.getIntersection(ray);

    }	


    @Test(expected=NoIntersectionException.class)
	public void testIntersection13(){
	p.setPoints(new Point[]{new Point(0,0,0), new Point(0,0,1), new Point(1,0,0)});
	Ray ray = new Ray(new Point(0,0,0), new Vector(0,-1,0));
	Point inters = p.getIntersection(ray);

    }	


    //Plan de coté
    @Test
	public void testIntersection14(){
	p.setPoints(new Point[]{new Point(0,0,0), new Point(0,1,0), new Point(0,0,1)});
	Ray ray = new Ray(new Point(1,0,0), new Vector(-1,0,0));
	Point inters = p.getIntersection(ray);
	assertTrue(inters.isEqual(new Point(0,0,0)));
    }
	
    @Test(expected = NoIntersectionException.class)
	public void testIntersection15(){
	p.setPoints(new Point[]{new Point(0,0,0), new Point(0,1,0), new Point(0,0,1)});
	Ray ray = new Ray(new Point(1,0,0), new Vector(1,0,0));
	p.getIntersection(ray);
    }	

    @Test
	public void testIntersection16(){
	p.setPoints(new Point[]{new Point(0,0,0), new Point(0,1,0), new Point(0,0,1)});
	Ray ray = new Ray(new Point(-1,0,0), new Vector(1,0,0));
	Point inters = p.getIntersection(ray);
	assertTrue(inters.isEqual(new Point(0,0,0)));
    }
	
    @Test(expected = NoIntersectionException.class)
	public void testIntersection17(){
	p.setPoints(new Point[]{new Point(0,0,0), new Point(0,1,0), new Point(0,0,1)});
	Ray ray = new Ray(new Point(-1,0,0), new Vector(-1,0,0));
	p.getIntersection(ray);
    }		
	
    @Test(expected = NoIntersectionException.class)
	public void testIntersection18(){
	p.setPoints(new Point[]{new Point(0,0,0), new Point(0,1,0), new Point(0,0,1)});
	Ray ray = new Ray(new Point(0,0,0), new Vector(1,0,0));
	Point inters = p.getIntersection(ray);
    }
	
    @Test(expected = NoIntersectionException.class)
	public void testIntersection19(){
	p.setPoints(new Point[]{new Point(0,0,0), new Point(0,1,0), new Point(0,0,1)});
	Ray ray = new Ray(new Point(0,0,0), new Vector(-1,0,0));
	p.getIntersection(ray);
    }	


    @Test
	public void testSetPoints() {
	points2[1]= new Point( 1.0 , 2.0 , 3.0 ) ;
	points2[2]= new Point( 1.0 , 3.0 , 5.0 ) ;
	points2[3]= new Point(-2.0 ,-1.0 , 2.0 ) ;


		
	assertEquals(1.0 , points[1].getX() , EPSILON ) ;
	assertEquals(2.0 , points[1].getY() , EPSILON ) ;
	assertEquals(3.0 , points[1].getZ() , EPSILON ) ;

	assertEquals(1.0 , points[1].getX() , EPSILON ) ;
	assertEquals(3.0 , points[1].getY() , EPSILON ) ;
	assertEquals(5.0 , points[1].getZ() , EPSILON ) ;

	assertEquals(-2.0 , points[1].getX() , EPSILON ) ;
	assertEquals(-1.0 , points[1].getY() , EPSILON ) ;
	assertEquals(2.0 , points[1].getZ() , EPSILON ) ;

    }

		
		


    public static void main(String args[]) {
	org.junit.runner.JUnitCore.main(TestSphere.class.getName());
    }

}
	

	
	
	
