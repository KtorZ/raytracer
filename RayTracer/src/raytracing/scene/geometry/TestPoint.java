package raytracing.scene.geometry;

import org.junit.*;
import static org.junit.Assert.*;

/** Classe de test pour la classe Point 
 * @author Groupe23
 * @version 1.0
 */

public class TestPoint {

    public static final double EPSILON = 1e-6;
    // précision pour la comparaison entre réels.

    private Point p1;
    private Point p2;

    @Before
	public void setUp() {
	p1 = new Point(1.0, 2.0, 3.0);
	p2 = new Point(4.0, -2.0, 5.0);
    }


    @Test
	public void testInitialisation() {
	//Test de l'initialisation des points servant aux tests
	assertNotNull(p1);
	assertNotNull(p2);
	assertEquals(1.0, p1.getX(), EPSILON);
	assertEquals(2.0, p1.getY(), EPSILON);
	assertEquals(3.0, p1.getZ(), EPSILON);
	assertEquals(4.0, p2.getX(), EPSILON);
	assertEquals(-2.0, p2.getY(), EPSILON);
	assertEquals(5.0, p2.getZ(), EPSILON);
    }
	

    @Test
	public void testSetX() {
	// Test de la fonction setX() pour une valeur positive
	p1.setX(10);
	assertEquals(10.0, p1.getX(), EPSILON);
	// Test de la fonction setX() pour une valeur negative
	p1.setX(-5);
	assertEquals(-5.0, p1.getX(), EPSILON);
    }

    @Test
	public void testSetY() {
	// Test de la fonction setY() pour une valeur positive
	p1.setY(10);
	assertEquals(10.0, p1.getY(), EPSILON);
	// Test de la fonction setY() pour une valeur negative
	p1.setY(-5);
	assertEquals(-5.0, p1.getY(), EPSILON);
    }

    @Test
	public void testSetZ() {
	// Test de la fonction setZ() pour une valeur positive
	p1.setZ(10);
	assertEquals(10.0, p1.getZ(), EPSILON);
	// Test de la fonction setZ() pour une valeur negative
	p1.setZ(-5);
	assertEquals(-5.0, p1.getZ(), EPSILON);
    }



    public static void main(String[] args) {
	org.junit.runner.JUnitCore.main(TestPoint.class.getName());
    }

		
}
