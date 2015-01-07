/** Classe de test pour la classe Ray
 * @author Nicolas Gaborit
 * @version 1.0
 */
package raytracing.scene;

import raytracing.scene.Ray;
import raytracing.scene.geometry.*;
import org.junit.*;
import static org.junit.Assert.*;

import raytracing.scene.geometry.*;

public class TestRay {

    public static final double EPSILON = 1e-9;
    // précision pour la comparaison entre réels.

    private Ray r1;

    @Before
	public void setUp() {
	// Initialisation des elements nécessaires aux tests
	r1 = new Ray(new Point(1.0, 0.0, -1.0), 
		     new Vector(1.0, 2.0, 3.0));
    }

    @Test
	public void testInitialisation() {
	//Test de l'initialisation du rayon
	assertNotNull(r1);

	assertEquals(1.0, r1.getSource().getX(), EPSILON);
	assertEquals(0.0, r1.getSource().getY(), EPSILON);
	assertEquals(-1.0, r1.getSource().getZ(), EPSILON);
	assertEquals(1.0/Math.sqrt(14), r1.getSupport().getX(), EPSILON);
	assertEquals(2.0/Math.sqrt(14), r1.getSupport().getY(), EPSILON);
	assertEquals(3.0/Math.sqrt(14), r1.getSupport().getZ(), EPSILON);
    }


    @Test
	public void testSetSource() {
	// Test de la fonction setSource() pour un point null
	Point p = null;
	try {
	    r1.setSource(p);
	    fail("IllegalArgumentException était attendue.");
	} catch (IllegalArgumentException e) {
	    assertTrue(e.getMessage().equals("Point source invalide"));
	}

	// Test de la fonction setSource() dans un cas valide.
	p = new Point(1.0, 0.0, 0.0);
	r1.setSource(p);
	assertEquals(1.0, r1.getSource().getX(), EPSILON);
	assertEquals(0.0, r1.getSource().getY(), EPSILON);
	assertEquals(0.0, r1.getSource().getZ(), EPSILON);
    }

    @Test
	public void testSetSupport() {
	// Test de la fonction setSupport() pour un vecteur de norme
	// nulle (trop petite)
	Vector v = new Vector(0.0, 0.0, 1.0E-15);
	try {
	    r1.setSupport(v);
	    fail("IllegalArgumentException était attendue.");
	} catch (IllegalArgumentException e) {
	    assertTrue(e.getMessage().equals("Vecteur support invalide"));
	}

	// Test de la fonction setSupport() pour un vecteur null
	try {
	    r1.setSupport(null);
	    fail("IllegalArgumentException était attendue.");
	} catch (IllegalArgumentException e) {
	    assertTrue(e.getMessage().equals("Vecteur support invalide"));
	}

	// Test de la fonction setSupport() dans un cas valide.
	v = new Vector(2.0, 0.0, 0.0);
	r1.setSupport(v);
	assertEquals(1.0, r1.getSupport().getX(), EPSILON);
	assertEquals(0.0, r1.getSupport().getY(), EPSILON);
	assertEquals(0.0, r1.getSupport().getZ(), EPSILON);
    }

    public static void main(String[] args) {
	org.junit.runner.JUnitCore.main(TestRay.class.getName());
    }


		
}
