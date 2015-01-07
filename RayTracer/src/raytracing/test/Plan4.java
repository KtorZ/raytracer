// -*- coding: iso-8859-1; -*-

package raytracing.test;

import raytracing.scene.*;
import raytracing.scene.geometry.*;
import raytracing.scene.positionable.*;
import raytracing.scene.positionable.element.*;
import java.util.ArrayList;
public class Plan4 {
    
    public static void main(String args[]) {
	
	// La scene
	Scene scene = new Scene(new Color(0.5, 0.5, 0.5), // Lumiere ambiante
				new Color(0, 0, 0), // Couleur infini
				1, // Indice de refraction de la scene
				8, // Nombre d'iterations
				"Scene"
				); 

	// Les materiaux
	Material porcelaine = 
	    new Material(new Color(0.2, 0.2, 0.2), // Absorption ambiante
			 new Color(0, 0, 0), // Absorption diffuse
			 0, //Coefficient de reflectance
			 200, //Coefficient de brillance
			 new Color(0.6, 0.6, 0.6), // Coefficient de transmission
			 new Color(0, 0, 0), //Coefficient de reflexion
			 1.4);
		Material platre = 
	    new Material(new Color(0.4, 0.4, 0.9), // Absorption ambiante
			 new Color(0.4, 0.4, 0.9), // Absorption diffuse
			 0.5, //Coefficient de reflectance
			 2, //Coefficient de brillance
			 new Color(0, 0, 0), // Coefficient de transmission
			 new Color(0, 0, 0), //Coefficient de reflexion
			 1);


	Sphere s = new Sphere(new Point(0, 0, -2), 1, "sphere", platre);
	scene.addElement(s);

	Plan plan1 = new Plan(new Point[]{new Point(0, 0, -0.4),  
					  new Point(0, -1, -0.4),
					  new Point(1, 0, -0.4),},
	    "surface",
	    porcelaine);
	scene.addElement(plan1);

	// Les sources de lumieres
	SpotLight spot = new SpotLight(new Point(0, -5, -10),
				       new Color(1, 1, 1), "Spotlight");
	scene.addSpotLight(spot);


	// Les points de vue
	ViewPoint vp = new ViewPoint(new Point(0, -7.5, 0.5),
				     new Vector(0, 1, -0.2),
				     new Vector(0, 0, 1),
				     30,
				     300, //Nombre de colonnes = nb de points en horizontal
				     300, //Nombre de lignes = nb de points en vertical
				     "Plan4");
	scene.addViewPoint(vp);	

    }
}
