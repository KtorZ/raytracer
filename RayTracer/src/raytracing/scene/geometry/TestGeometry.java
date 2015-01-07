package raytracing.scene.geometry;

import org.junit.*;
import static org.junit.Assert.*;

/** Classe de test pour la classe Geometry 
 * @author Groupe23
 * @version 1.0
 */

public class TestGeometry {

    public static final double EPSILON =  1e-6 ;
    // precision pour la comparaison entre réels

    private Vector v1, v2, v3, v4, v5, v6, v7 , v8;
    private Point p1, p2, p3, p4;


    @Before
	public void SetUp() {
	//Initialisation des différents éléments necessaires aux tests
	v1 = new Vector(1.0, 2.0, 3.0);
	v2 = new Vector(0.0, 0.0, 0.0);
	v3 = new Vector(2.0, 4.0, 6.0);
	v4 = new Vector(1.0, 1.0, -1.0);
	v8 = new Vector(1.0, 0.0 , 0.0);
	p1 = new Point(1.0, 2.0, 1.0);
	p2 = new Point(-2.0, 5.0, 3.0);

    }

	
    @Test
	public void testDistance() {
	//Test pour un vecteur "quelconque"
	assertEquals(Math.sqrt(22.0), Geometry.distance(p1, p2), EPSILON);
	//Test pour un vecteur de norme nulle
	assertEquals(0, Geometry.distance(p1, p1), EPSILON);
    }

	
    @Test
	public void testScalarProduct() {
	//Test pour un vecteur quelconque et un vecteur nul
	assertEquals(0, Geometry.scalarProduct(v1,v2), EPSILON);
	//Test pour deux vecteurs orthogonaux
	assertEquals(0, Geometry.scalarProduct(v1,v4), EPSILON);
	//Test pour deux vecteurs quelconques
	assertEquals(28, Geometry.scalarProduct(v1,v3), EPSILON);
    }


    @Test
	public void testTranslate() {
	//Calcul des images des points ("translations") par un vecteur quelconque pour le 1er et un vecteur nul pour le 		  2nd
	p3 = Geometry.translate(p1, v1);
	p4 = Geometry.translate(p2, v2);

	// Test pour la "translation" de vecteur quelconque
	assertEquals(2.0, p3.getX(), EPSILON);
	assertEquals(4.0, p3.getY(), EPSILON);
	assertEquals(4.0, p3.getZ(), EPSILON);

	// Test pour la "translation" de vecteur nul
	assertEquals(-2.0, p4.getX(), EPSILON);
	assertEquals(5.0, p4.getY(), EPSILON);
	assertEquals(3.0, p4.getZ(), EPSILON);

    }



    @Test
	public void testVectorialProduct() {
	//Calcul des produits vectoriels
	v5 = Geometry.vectorialProduct(v1, v2);
	v6 = Geometry.vectorialProduct(v1, v4);
	v7 = Geometry.vectorialProduct(v1, v3);

	// Test pour un produit vectoriel avec un vecteur quelconque et un vecteur nul
	assertEquals(0.0, v5.getX(), EPSILON);
	assertEquals(0.0, v5.getY(), EPSILON);
	assertEquals(0.0, v5.getZ(), EPSILON);

	// Test pour deux vecteurs quelconques
	assertEquals(-5.0, v6.getX(), EPSILON);
	assertEquals(4.0, v6.getY(), EPSILON);
	assertEquals(-1.0, v6.getZ(), EPSILON);
	
	// Test pour deux vecteurs colinéaires
	assertEquals(0.0, v7.getX(), EPSILON);
	assertEquals(0.0, v7.getY(), EPSILON);
	assertEquals(0.0, v7.getZ(), EPSILON);

    }

	
    @Test
	public void testRealProduct(){
	// Calcul des produits réels
	v5 = Geometry.realProduct(v1, 3);
	v6 = Geometry.realProduct(v2, 5);
	v7 = Geometry.realProduct(v1, 0);

	// Test pour un produit réel avec un vecteur et un réel quelconques
	assertEquals(3.0, v5.getX(), EPSILON);
	assertEquals(6.0, v5.getY(), EPSILON);
	assertEquals(9.0, v5.getZ(), EPSILON);

	// Test pour un vecteur nul et un réel quelconque
	assertEquals(0.0, v6.getX(), EPSILON);
	assertEquals(0.0, v6.getY(), EPSILON);
	assertEquals(0.0, v6.getZ(), EPSILON);
	
	// Test pour un vecteur quelconque et le réel nul
	assertEquals(0.0, v7.getX(), EPSILON);
	assertEquals(0.0, v7.getY(), EPSILON);
	assertEquals(0.0, v7.getZ(), EPSILON);

    }


    @Test
	public void testSum(){
	//Calcul de la somme de plusieurs vecteurs
	v5 = Geometry.sum(v1, v2);
	v6 = Geometry.sum(v3, v4);

	// Test pour une somme de deux vecteurs dont un nul
	assertEquals(1.0, v5.getX(), EPSILON);
	assertEquals(2.0, v5.getY(), EPSILON);
	assertEquals(3.0, v5.getZ(), EPSILON);

	// Test pour deux vecteurs quelconques
	assertEquals(3.0, v6.getX(), EPSILON);
	assertEquals(5.0, v6.getY(), EPSILON);
	assertEquals(5.0, v6.getZ(), EPSILON);
	
    }

    @Test
	public void testNormalize(){

	v5 = Geometry.normalize(v1) ;

	// Test pour un vecteur quelconque
	assertEquals(1.0/Math.sqrt(14) , v5.getX() , EPSILON ) ;
	assertEquals(2.0/Math.sqrt(14) , v5.getY() , EPSILON ) ;
	assertEquals(3.0/Math.sqrt(14) , v5.getZ() , EPSILON ) ;

	//Test pour un vecteur normé
	assertEquals(1.0 , v8.getX() , EPSILON ) ;
	assertEquals(0.0 , v8.getY() , EPSILON ) ;
	assertEquals(0.0 , v8.getZ() , EPSILON ) ;
	
    }


    public static void main(String[] args) {
	org.junit.runner.JUnitCore.main(TestGeometry.class.getName());
    }

}




	
		
