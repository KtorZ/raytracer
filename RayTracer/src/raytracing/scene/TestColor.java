/** Classe de test pour la classe Color
 * @author Nicolas Gaborit
 * @version 1.0
 */
package raytracing.scene;

import raytracing.scene.Color;
import org.junit.*;
import static org.junit.Assert.*;

public class TestColor {

    public static final double EPSILON = 1e-6;
    // précision pour la comparaison entre réels.

    private Color c1;
    private Color c2;

    @Before
	public void setUp() {
	// Initialisation des elements nécessaires aux tests
	c1 = new Color(1.0, 0.5, 0);
	c2 = new Color(1.0, 0.2, 0.1);
    }

    @Test
	public void testInitialisation() {
	//Test de l'initialisation des couleurs
	assertNotNull(c1);
	assertNotNull(c2);

	assertEquals(1.0, c1.getRedComp(), EPSILON);
	assertEquals(0.5, c1.getGreenComp(), EPSILON);
	assertEquals(0, c1.getBlueComp(), EPSILON);

	assertEquals(1.0, c2.getRedComp(), EPSILON);
	assertEquals(0.2, c2.getGreenComp(), EPSILON);
	assertEquals(0.1, c2.getBlueComp(), EPSILON);
    }


    @Test
	public void testGetPixel() {
	// Test de la fonction getPixel()
	int[] pixels = new int[]{255, 127, 0};
	assertArrayEquals(pixels, c1.getPixel());
    }



    @Test
	public void testLogAdd() {
	// Test de la fonction logAdd entre c1 et c2
	c1.logAdd(c2);
	assertEquals(1.0, c1.getRedComp(), EPSILON);
	assertEquals(0.6, c1.getGreenComp(), EPSILON);
	assertEquals(0.1, c1.getBlueComp(), EPSILON);
    }


    @Test
	public void testRatio() {
	// Test de la fonction ratio avec r dans [0, 1]
	c1 = c1.ratio(0.2);
	assertEquals(0.2, c1.getRedComp(), EPSILON);
	assertEquals(0.1, c1.getGreenComp(), EPSILON);
	assertEquals(0, c1.getBlueComp(), EPSILON);
	// Test de la fonction ratio avec r hors de [0, 1]
	try {
	    c1 = c1.ratio(3);
	} catch (IllegalArgumentException e) {
	    assertTrue(e.getMessage().equals("Ratio invalide"));
	}
	// Test de la fonction ratio avec r hors de [0, 1]
	try {
	    c1 = c1.ratio(-3);
	} catch (IllegalArgumentException e) {
	    assertTrue(e.getMessage().equals("Ratio invalide"));
	}
    }

    @Test
	public void testMultiply() {
	// Test de la fonction multiply entre c1 et c2
	c1 = c1.multiply(c2);
	assertEquals(1.0, c1.getRedComp(), EPSILON);
	assertEquals(0.1, c1.getGreenComp(), EPSILON);
	assertEquals(0, c1.getBlueComp(), EPSILON);
    }

    @Test
	public void testSetRedComp() {
	// Test de la fonction setRedComp() pour des valeurs comprises
	// entre 0 et 1
	c1.setRedComp(1.0);
	assertEquals(1.0, c1.getRedComp(), EPSILON);
	c1.setRedComp(0);
	assertEquals(0, c1.getRedComp(), EPSILON);
	c1.setRedComp(0.5);
	assertEquals(0.5, c1.getRedComp(), EPSILON);
	// Test de la fonction setRedComp() pour une valeur plus grande que
	// 1 et plus petite que 0.
	try {
	    c1.setRedComp(2.0);
	    fail("IllegalArgumentException était attendue.");
	} catch (IllegalArgumentException e) {
	    assertTrue(e.getMessage().equals("Composante rouge invalide"));
	}
	try {
	    c1.setRedComp(-1.0);
	    fail("IllegalArgumentException était attendue.");
	} catch (IllegalArgumentException e) {
	    assertTrue(e.getMessage().equals("Composante rouge invalide"));
	}
    }

    @Test
	public void testSetGreenComp() {
	// Test de la fonction setGreenComp() pour des valeurs comprises
	// entre 0 et 1
	c1.setGreenComp(1.0);
	assertEquals(1.0, c1.getGreenComp(), EPSILON);
	c1.setGreenComp(0);
	assertEquals(0, c1.getGreenComp(), EPSILON);
	c1.setGreenComp(0.5);
	assertEquals(0.5, c1.getGreenComp(), EPSILON);
	// Test de la fonction setGreenComp() pour une valeur plus grande que
	// 1 et plus petite que 0.
	try {
	    c1.setGreenComp(2.0);
	    fail("IllegalArgumentException était attendue.");
	} catch (IllegalArgumentException e) {
	    assertTrue(e.getMessage().equals("Composante verte invalide"));
	}
	try {
	    c1.setGreenComp(-1.0);
	    fail("IllegalArgumentException était attendue.");
	} catch (IllegalArgumentException e) {
	    assertTrue(e.getMessage().equals("Composante verte invalide"));
	}
    }
    @Test
	public void testSetBlueComp() {
	// Test de la fonction setBlueComp() pour des valeurs comprises
	// entre 0 et 1
	c1.setBlueComp(1.0);
	assertEquals(1.0, c1.getBlueComp(), EPSILON);
	c1.setBlueComp(0);
	assertEquals(0, c1.getBlueComp(), EPSILON);
	c1.setBlueComp(0.5);
	assertEquals(0.5, c1.getBlueComp(), EPSILON);
	// Test de la fonction setBlueComp() pour une valeur plus grande que
	// 1 et plus petite que 0.
	try {
	    c1.setBlueComp(2.0);
	    fail("IllegalArgumentException était attendue.");
	} catch (IllegalArgumentException e) {
	    assertTrue(e.getMessage().equals("Composante bleue invalide"));
	}
	try {
	    c1.setBlueComp(-1.0);
	    fail("IllegalArgumentException était attendue.");
	} catch (IllegalArgumentException e) {
	    assertTrue(e.getMessage().equals("Composante bleue invalide"));
	}
    }
	    
    public static void main(String[] args) {
	org.junit.runner.JUnitCore.main(TestColor.class.getName());
    }


		
}
