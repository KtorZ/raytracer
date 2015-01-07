package raytracing.scene.positionable;

import org.junit.*;
import static org.junit.Assert.*;
import raytracing.scene.geometry.*;

/** Classe de test pour la classe ViewPoint
 * @author Nicolas Gaborit, all rights reserved...
 * @version 1.0
 */

public class TestViewPoint {

    public static final double EPSILON = 1e-6;
    // précision pour la comparaison entre réels.

    private ViewPoint view1, view2;
    private Point source;
    private Screen screen;

    @Before
	public void setUp() {
	// Initialisation des elements nécessaires aux tests
	source = new Point(0, 0, 0);
	screen = new Screen(new Point[]{new Point(3, 0, 1), 
					new Point(3, 2, 1), 
					new Point(1, 0, 1)},
	    800, 700);
	view1 = new ViewPoint(source, screen, "view1");
	view2 = new ViewPoint(source,
			      new Vector(1, 0, 0),
			      new Vector(1, 1, 0),
			      90, 200, 100, "view2");
    }
    
    @Test
	public void testInitialisation() {
	//Test de l'initialisation des ViewPoint
	assertNotNull(view1);
	assertNotNull(view2);
	
	assertEquals(screen.getResolutionI(), view1.getScreen().getResolutionI());
	assertEquals(screen.getResolutionJ(), view1.getScreen().getResolutionJ());
	assertEquals(100, view2.getScreen().getResolutionI());
	assertEquals(200, view2.getScreen().getResolutionJ());

	assertTrue(new Point(0, 0, 0).isEqual(view1.getSource()));
	assertTrue(new Point(0, 0, 0).isEqual(view2.getSource()));

	assertTrue(new Point(3, 0, 1).isEqual(view1.getScreen().getPointAt(0,0)));

	assertTrue(new Point(1, 0.5, -1).isEqual(view2.getScreen().getPointAt(0,0)));
	assertTrue(new Point(1, 0.5, 1).isEqual(view2.getScreen().getPointAt(0, 199)));
	assertTrue(new Point(1, -0.5, -1).isEqual(view2.getScreen().getPointAt(99, 0)));
    }

    @Test // Test de la fonction setSource 
	public void testSetSource() {
	source = new Point(2, 1, 2);
	view2.setSource(source);
	assertTrue(source.isEqual(view2.getSource()));
    }

    @Test // Test de la fonction setScreen
	public void testSetScreen() {
	view2.setScreen(screen);

	assertTrue(source.isEqual(view2.getSource()));
	assertEquals(700, view2.getScreen().getResolutionI());
	assertEquals(800, view2.getScreen().getResolutionJ());
	assertTrue(new Point(3, 0, 1).isEqual(view2.getScreen().getPointAt(0,0)));
	assertTrue(new Point(3, 2, 1).isEqual(view2.getScreen().getPointAt(0, 799)));
	assertTrue(new Point(1, 0, 1).isEqual(view2.getScreen().getPointAt(699, 0)));
    }


	    
    @Test // Test des cas d'erreur du constructeur 2
	public void testErrorConstructor() {

	// Cas d'erreurs du fov
	try {view2 = new ViewPoint(new Point(0, 0, 0),
				   new Vector(0, 2, 0),
				   new Vector(1, 1, 0),
				   180, 200, 100, "view2");
	} catch (IllegalArgumentException exc) {
	    assertTrue(exc.getMessage().equals("Angle de vision en dehors de [1;179]")); }
	try {view2 = new ViewPoint(new Point(0, 0, 0),
				   new Vector(0, 2, 0),
				   new Vector(1, 1, 0),
				   0, 200, 100, "view2");
	} catch (IllegalArgumentException exc) {
	    assertTrue(exc.getMessage().equals("Angle de vision en dehors de [1;179]")); }

	// Cas d'erreur pour le vecteur vertical
	try {view2 = new ViewPoint(new Point(0, 0, 0),
				   new Vector(0, 2, 0),
				   new Vector(0, 1, 0),
				   80, 200, 100, "view2");
	} catch (IllegalArgumentException exc) {
	    assertTrue(exc.getMessage().equals("Les vecteur du regard et de la verticale sont colinéaires")); }
    }

    public static void main(String[] args) {
	org.junit.runner.JUnitCore.main(TestViewPoint.class.getName());
    }


		
}
