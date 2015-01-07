// -*- coding: iso-8859-1; -*-

package raytracing.test;

import raytracing.scene.*;
import raytracing.scene.geometry.*;
import raytracing.scene.positionable.*;
import raytracing.scene.positionable.element.*;
import java.util.ArrayList;
public class Pyramide {

    public static void main(String args[]) {
	
	Sphere sphere;
	SpotLight spot;
	ViewPoint vp;

	Point coinPyramide = new Point(0, 0, 0);
	Point centre;
	int taille = 3;
	double rayon = 1;
	double h = rayon*Math.sqrt(3);
	// La scène
	Scene scene = new Scene(new Color(0.1, 0.2, 0.4), // Lumiere ambiante
				new Color(0.45, 0.7, 0.9), // Couleur infini
				1.4, // Indice de réfraction de la scène
				3, // Nombre d'itérations
				"Scene"
				); 
	
	// Les matériaux
	Material sphereMat = 
	    new Material(new Color(0.2, 0.2, 0.2), // Absorption ambiante
			 new Color(0.2, 0.2, 0.2), // Absorption diffuse
			 0.9, //Coefficient de reflectance
			 250, //Coefficient de brillance
			 new Color(0.85, 0.85, 0.85), // Coefficient de transmission
			 new Color(0.5, 0.5, 0.5), //Coefficient de reflexion
			 1);
	Material solBlancMat = 
	    new  Material(new Color(1, 1, 1), // Absorption ambiante
			  new Color(1, 1, 1), // Absorption diffuse
			  0, //Coefficient de reflectance
			  250, //Coefficient de brillance
			  new Color(0, 0, 0), // Coefficient de transmission
			  new Color(0, 0, 0), //Coefficient de reflexion
			  1);
	Material solNoirMat = 
	    new  Material(new Color(0.2, 0.2, 0.2), // Absorption ambiante
			  new Color(0, 0, 0), // Absorption diffuse
			  0, //Coefficient de reflectance
			  250, //Coefficient de brillance
			  new Color(0, 0, 0), // Coefficient de transmission
			  new Color(0, 0, 0), //Coefficient de reflexion
			  1);

	Material solTransMat = 
	    new  Material(new Color(0, 0, 0), // Absorption ambiante
			  new Color(0, 0, 0), // Absorption diffuse
			  0, //Coefficient de reflectance
			  250, //Coefficient de brillance
			  new Color(1.0, 1.0, 1.0), // Coefficient de transmission
			  new Color(0, 0, 0), //Coefficient de reflexion
			  1.4);
	
	Plan abysse = new Plan(new Point[]{new Point(0, 0, -100*rayon*taille),
					   new Point(1, 0, -100*rayon*taille),
					   new Point(0, 1, -100*rayon*taille)},
	    "abysse",
	    solNoirMat);
	
	Plan surface = new Plan(new Point[]{new Point(0, 0, 100*rayon*taille),
					    new Point(1, 0, 100*rayon*taille),
					    new Point(0, -1, 100*rayon*taille)},
	    "surface",
	    solBlancMat);
	
	Plan lightStop = new Plan(new Point[]{new Point(0, 0, 3*rayon*taille - 1),
					    new Point(1, 0, 3*rayon*taille - 1),
					    new Point(0, -1, 3*rayon*taille - 1)},
	    "surface",
	    solBlancMat);
	
	scene.addElement(abysse);
	scene.addElement(surface);
	scene.addElement(lightStop);

	// Création de la pyramide de sphères
	for (int k = 0; k <= (taille-1); k++) {
	    // Placer l'étage k
	    for (int i = 0; i <= (taille - k - 1); i++) {
		// Placer la ligne i
		for (int j = 0; j <= (taille - i - k - 1); j++) {
		    centre = Geometry.translate(coinPyramide,
						new Vector(rayon*(k + i + 2*j),
							   h*(i + (double)k/3),
							   k*2*Math.sqrt(rayon*rayon - h*h/9)));
		    sphere = new Sphere(centre, rayon, "petite sphere", sphereMat);
		    scene.addElement(sphere);
		}
	    }
	}
	
	// Les sources de lumières
	spot = new SpotLight(new Point(0, 0, 3*rayon*taille),
			     new Color(1, 1, 1), "Spotlight");
	scene.addSpotLight(spot);
	
	//spot = new SpotLight(new Point(taille*rayon/3 + 1, 0, 2*taille*rayon + 1),
	//		     new Color(1, 1, 1), "Spotlight");
	// scene.addSpotLight(spot);


	// Les points de vue
	
	Point pointDeVue = new Point(4*rayon*taille/3 + 1, 0, 4*rayon*taille/3 + 1);
	vp = new ViewPoint(new Point(-taille*rayon*2 - 1.5, -taille*rayon - 1, taille*rayon*2),
			   new Vector(2, 1, -1),
			   new Vector(0, 0, 1),
			   30,
			   100, //Nombre de colonnes = nb de points en horizontal
			   100, //Nombre de lignes = nb de points en vertical
			   "Pyramide1");

	/* Les points de vue
	   Screen scr = new Screen(new Point[]{new Point(-5, -6, 6), new Point(-4,-5, 6), new Point(-5, -4, 5)}, //Premier point comme origine
	   200, //Nombre de colonnes = nb de points en horizontal
	   200); //Nombre de lignes = nb de points en vertical
	   
	   vp = new ViewPoint(new Point(-4, -4, 0.5), scr, "Vp1"); //Source, ecran, nom
	*/	
	scene.addViewPoint(vp);	
    }
}