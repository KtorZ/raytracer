package raytracing.scene.geometry;

import org.junit.*;
import static org.junit.Assert.*;

/** Classe de test pour la classe Vector
 * @author Groupe23
 * @version 1.0
 */

public class TestVector {

    public static final double EPSILON = 1e-6;
    // précision pour la comparaison entre réels.

    private Point p1;
    private Point p2;
    private Vector v1;
    private Vector v2;

    @Before
	public void setUp() {
	// Initialistaion des elements nécessaires aux tests
	p1 = new Point(1.0, 2.0, 5.0);
	p2 = new Point(4.0, -2.0, 3.0);
	v1 = new Vector(p1, p2);
	v2 = new Vector(4.0, -2.0, 5.0);

    }

    @Test
	public void testInitialisation() {
	//Test de l'initialisation des vecteurs avec les deux différents constructeurs

	assertNotNull(v1);
	assertNotNull(v2);

	assertEquals(3.0 , v1.getX(),EPSILON);
	assertEquals(-4.0, v1.getY(),EPSILON);
	assertEquals(-2.0, v1.getZ(),EPSILON);

	assertEquals(4.0, v2.getX(),EPSILON);
	assertEquals(-2.0, v2.getY(),EPSILON);
	assertEquals(5.0, v2.getZ(),EPSILON);
    }


    @Test
	public void testSetX() {
	// Test de la fonction setX() pour une valeur positive
	v1.setX(10);
	assertEquals(10.0, v1.getX(), EPSILON);
	// Test de la fonction setX() pour une valeur negative
	v1.setX(-5);
	assertEquals(-5.0, v1.getX(), EPSILON);
    }

    @Test
	public void testSetY() {
	// Test de la fonction setY() pour une valeur positive
	v1.setY(10);
	assertEquals(10.0, v1.getY(), EPSILON);
	// Test de la fonction setY() pour une valeur negative
	v1.setY(-5);
	assertEquals(-5.0, v1.getY(), EPSILON);
    }


    @Test
	public void testSetZ() {
	// Test de la fonction setZ() pour une valeur positive
	v1.setZ(10);
	assertEquals(10.0, v1.getZ(), EPSILON);
	// Test de la fonction setZ() pour une valeur negative
	v1.setZ(-5);
	assertEquals(-5.0, v1.getZ(), EPSILON);
    }

    @Test
	public void testGetNorm() {
	// Test de getNorm pour un vecteur quelconque
	assertEquals(Math.sqrt(16 + 4 +25), v2.getNorm(), EPSILON);
	// Test de getNorm pour un vecteur nul	
	// assertEquals(0, v3.getNorm(), EPSILON) ;

    }
		    		
	  	

    public static void main(String[] args) {
	org.junit.runner.JUnitCore.main(TestVector.class.getName());
    }


		
}
