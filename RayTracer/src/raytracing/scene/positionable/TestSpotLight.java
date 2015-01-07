package raytracing.scene.positionable;

import org.junit.*;
import static org.junit.Assert.*;
import raytracing.scene.geometry.*;
import raytracing.scene.Color;

/** Classe de test de SpotLight
 *@author Maxime Hervé
 *@version 1.0
 */


public class TestSpotLight{
	
    private SpotLight spotlight ;
    private Color color , color2 ;
    private Point position , position2 ;
    public static final double EPSILON=1e-9; 


    @Before
	public void setUp() {
	position = new Point (0,0,0);
	color = new Color( 0.5 , 0.5 , 0.5);
	spotlight = new SpotLight( position , color , "spotlight" );
    }


    @Test
	public void testGetColor() {
	assertEquals( color.getRedComp() , spotlight.getColor().getRedComp() , EPSILON ) ;
	assertEquals( color.getBlueComp() , spotlight.getColor().getBlueComp() , EPSILON ) ;
	assertEquals( color.getGreenComp() , spotlight.getColor().getGreenComp() , EPSILON ) ;
	
    }

    @Test
	public void testGetPosition(){
	assertEquals( position.getX() , spotlight.getPosition().getX() , EPSILON ) ;
	assertEquals( position.getY() , spotlight.getPosition().getY() , EPSILON ) ;
	assertEquals( position.getZ() , spotlight.getPosition().getZ() , EPSILON ) ;
    }


    @Test	
	public void testSetColor(){
	color2 = new Color(0.7 , 0.7 , 0.7 );
	spotlight.setColor(color2);
	assertEquals( color2.getRedComp() , spotlight.getColor().getRedComp() , EPSILON ) ;
	assertEquals( color2.getBlueComp() , spotlight.getColor().getBlueComp() , EPSILON ) ;
	assertEquals( color2.getGreenComp() , spotlight.getColor().getGreenComp() , EPSILON ) ;
    }

    @Test
	public void testSetPosition(){
	position2 = new Point(1,1,1);
	spotlight.setPosition(position2);
	Point p = spotlight.getPosition();
	assertTrue ( p.isEqual(position2));

    }



    public static void main(String args[]) {
        org.junit.runner.JUnitCore.main(TestSpotLight.class.getName());
    }
		

}

	 

