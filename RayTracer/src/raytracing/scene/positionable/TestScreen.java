/** Classe de test pour la classe Screen
 * @author Nicolas Gaborit
 * @version 2.0
 */
package raytracing.scene.positionable;

import org.junit.*;
import static org.junit.Assert.*;
import raytracing.scene.geometry.*;

public class TestScreen {

    public static final double EPSILON = 1e-6;
    // précision pour la comparaison entre réels.

    private Screen s1;

    @Before
	public void setUp() {
	// Initialisation des elements nécessaires aux tests
	s1 = new Screen(new Point[]{new Point(2, 0, 0), 
				    new Point(2, 2, 0), 
				    new Point(0, 0, 0)},
	    801, 601);
    }
    
    @Test
	public void testInitialisation() {
	//Test de l'initialisation du rayon
	assertNotNull(s1);
	
	assertEquals(601, s1.getResolutionI());
	assertEquals(801, s1.getResolutionJ());
    }


    @Test // Test de la fonction getPointAt dans un cas normal
	public void testGetPointAt() {
	Point p1 = s1.getPointAt(600, 800);
	Point p2 = s1.getPointAt(300, 400);
	assertEquals(0, p1.getX(), EPSILON);
	assertEquals(2.0, p1.getY(), EPSILON);
	assertEquals(0, p1.getZ(), EPSILON);
	assertEquals(1, p2.getX(), EPSILON);
	assertEquals(1, p2.getY(), EPSILON);
	assertEquals(0, p2.getZ(), EPSILON);
	// Test de la fonction getPointAt dans les cas anormaux où les
	// coordonnées sont hors limites
	String erreur = "Selection d'un point en dehors de l'ecran";
	try { Point p = s1.getPointAt(1000, 400);
	} catch (IllegalArgumentException exc) {
	    assertTrue(exc.getMessage().equals(erreur)); }

	try { Point p = s1.getPointAt(300, 1000);
	} catch (IllegalArgumentException exc) {
	    assertTrue(exc.getMessage().equals(erreur)); }

	try { Point p = s1.getPointAt(-1000, 400);
	} catch (IllegalArgumentException exc) {
	    assertTrue(exc.getMessage().equals(erreur)); }

	try { Point p = s1.getPointAt(300, -1000);
	} catch (IllegalArgumentException exc) {
	    assertTrue(exc.getMessage().equals(erreur)); }
    }

    @Test // Test de la fonction setCorner
	public void testSetCorner() {
	// Cas normal

	// Cas d'un point non initialisé
	try { s1.setCorner(2, null);
	} catch (IllegalArgumentException exc) {
	    assertTrue(exc.getMessage().equals("Point non initialise")); }
    }

    @Test // Test de la fonction setCorners
	public void testSetCorners() {
	// Cas normal

	// Cas où trop peu de points sont donnés
	try { s1.setCorners(new Point[] {new Point(1, 1 ,1),
					 new Point(1, 2, -1)});
	} catch (IllegalArgumentException exc) {
	    assertTrue(exc.getMessage().equals("Nombre de coins invalide")); }
	
	// Cas de trois points alignés
	try { s1.setCorners(new Point[] {new Point(1, 1, 1),
					 new Point(2, 2, 2),
					 new Point(3, 3, 3)});
	} catch (IllegalArgumentException exc) {
	    assertTrue(exc.getMessage().equals("3 points alignes" +
					       " ne forment pas un plan")); }
    }

    @Test // Test de la fonction setResolutionI
	public void testSetResolutionI() {
	s1.setResolutionI(200);
	assertEquals(200, s1.getResolutionI());

	// Cas d'une résolution négative
	try {
	    s1.setResolutionI(-1);
	} catch (IllegalArgumentException exc) {
	    assertTrue(exc.getMessage().equals("Resolution verticale invalide")); }
    }

    @Test // Test de la fonction setResolutionJ
	public void testSetResolutionJ() {
	s1.setResolutionJ(200);
	assertEquals(200, s1.getResolutionJ());
       
	// Cas d'une résolution négative
	try {
	    s1.setResolutionJ(-1);
	} catch (IllegalArgumentException exc) {
	    assertTrue(exc.getMessage().equals("Resolution horizontale invalide")); }
    }
    
    public static void main(String[] args) {
	org.junit.runner.JUnitCore.main(TestScreen.class.getName());
    }


		
}
