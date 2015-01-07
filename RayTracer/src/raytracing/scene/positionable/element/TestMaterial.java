/** Test de la classe Material
 * @author Matthias Benkort
 * @version 2.0
 */
package raytracing.scene.positionable.element;

import raytracing.scene.Color;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;

public class TestMaterial{
    private Material m;
	
    public static final double EPSILON = Math.pow(10,-14);
	
    @Before
	public void setUp(){
	m = new Material(
			 new Color(1,1,1), //Couleur ambiante
			 new Color(1,1,1), //Couleur diffuse
			 0, //Reflectance du materiau
			 1, //Brillance
			 new Color(0,0,0), //Ktransmission
			 new Color(0,0,0), //KReflexion
			 1.33); //IRefraction
    }
	
    //Getter et Setter de la couleur ambiante
    @Test(expected=IllegalArgumentException.class)
  	public void ambient1(){
	m.setCAmbient(null);
    }
	
    @Test
	public void ambient2(){
	m.setCAmbient(new Color(0.7,0.3,0.5));
	assertEquals(m.getCAmbient().getRedComp(), 0.7, EPSILON);
	assertEquals(m.getCAmbient().getGreenComp(), 0.3, EPSILON);
	assertEquals(m.getCAmbient().getBlueComp(), 0.5, EPSILON);
    }
	
    //Getter et Setter de la couleur diffuse
    @Test(expected=IllegalArgumentException.class)
    	public void diffuse1(){
	m.setCAmbient(null);
    }
	
    @Test
	public void diffuse2(){
	m.setCDiffuse(new Color(0.7,0.3,0.5));
	assertEquals(m.getCDiffuse().getRedComp(), 0.7, EPSILON);
	assertEquals(m.getCDiffuse().getGreenComp(), 0.3, EPSILON);
	assertEquals(m.getCDiffuse().getBlueComp(), 0.5, EPSILON);
    }	
	
    //Getter et Setter du coefficient de reflectance
    @Test(expected=IllegalArgumentException.class)
	public void ks1(){
	m.setKSpecular(-14);
    }
	
    @Test(expected=IllegalArgumentException.class)
	public void ks2(){
	m.setKSpecular(14);
    }	
	
    @Test
	public void ks3(){
	m.setKSpecular(0.14);
	assertEquals(m.getKSpecular(), 0.14, EPSILON);
    }	
	
	
    //Getter et Setter de la brillance
    @Test(expected=IllegalArgumentException.class)
	public void kn1(){
	m.setBrightness(0);
    }
	
    @Test
	public void kn2(){
	m.setBrightness(1000);
	assertEquals(m.getBrightness(), 1000, EPSILON);
    }

    //Getter et Setter du coefficient de transmission
    @Test(expected=IllegalArgumentException.class)
	public void ktWrongCompVal(){
	m.setCTransmission(null);
    }
    @Test
	public void getKt(){
	m.setCTransmission(new Color(0.14,0.18,0.99));
	Color cTransmission = m.getCTransmission();
	assertEquals(cTransmission.getRedComp(), 0.14, EPSILON);
	assertEquals(cTransmission.getGreenComp(), 0.18, EPSILON);
	assertEquals(cTransmission.getBlueComp(), 0.99, EPSILON);
    }		
    //Getter et Setter du coefficient de reflection
    @Test(expected=IllegalArgumentException.class)
	public void krWrongCompVal(){
	m.setCReflection(null);
    }
    @Test
	public void getKr(){
	m.setCReflection(new Color(0.14,0.18,0.99));
	Color cReflection = m.getCReflection();
	assertEquals(cReflection.getRedComp(), 0.14, EPSILON);
	assertEquals(cReflection.getGreenComp(), 0.18, EPSILON);
	assertEquals(cReflection.getBlueComp(), 0.99, EPSILON);
    }	

    //Getter et Setter de l'indice de refraction
    @Test(expected=IllegalArgumentException.class)
	public void iRef1(){
	m.setIRefraction(0);
    }	
    @Test
	public void iRef2(){
	m.setIRefraction(2.5);
	assertEquals(m.getIRefraction(), 2.5, EPSILON);
    }

    public static void main(String args[]) {
	org.junit.runner.JUnitCore.main(TestMaterial.class.getName());
    }
}
