// -*- coding: iso-8859-1; -*-

package raytracing.test;

import raytracing.scene.*;
import raytracing.scene.geometry.*;
import raytracing.scene.positionable.*;
import raytracing.scene.positionable.element.*;
import java.util.ArrayList;
public class SphereTest {
    
    public static void main(String args[]) {
	
	// La scene
	Scene scene = new Scene(new Color(0.4, 0.4, 0.4), // Lumiere ambiante
				new Color(0.2, 0.2, 0.2), // Couleur infini
				1, // Indice de refraction de la scene
				8, // Nombre d'iterations
				"Scene"
				); 

	// Les materiaux
	Material reflexionSpec = 
	    new Material(new Color(0, 0, 0), // Absorption ambiante
			 new Color(0, 0, 0), // Absorption diffuse
			 0.95, //Coefficient de reflectance
			 100, //Coefficient de brillance
			 new Color(0, 0, 0), // Coefficient de transmission
			 new Color(0.9, 0.9, 0.9), //Coefficient de reflexion
			 1);

	Material ambiant = 
	    new Material(new Color(0.4, 1, 0.4), // Absorption ambiante
			 new Color(0, 0, 0), // Absorption diffuse
			 0, //Coefficient de reflectance
			 2, //Coefficient de brillance
			 new Color(0, 0, 0), // Coefficient de transmission
			 new Color(0, 0, 0), //Coefficient de reflexion
			 1);

	Material diffuse = 
	    new Material(new Color(0, 0, 0), // Absorption ambiante
			 new Color(0, 0, 1), // Absorption diffuse
			 0, //Coefficient de reflectance
			 2, //Coefficient de brillance
			 new Color(0, 0, 0), // Coefficient de transmission
			 new Color(0, 0, 0), //Coefficient de reflexion
			 1);

	Material platreBlanc = 
	    new Material(new Color(0.9, 0.9, 0.9), // Absorption ambiante
			 new Color(0.9, 0.9, 0.9), // Absorption diffuse
			 0.5, //Coefficient de reflectance
			 2, //Coefficient de brillance
			 new Color(0, 0, 0), // Coefficient de transmission
			 new Color(0, 0, 0), //Coefficient de reflexion
			 1);

	Material sol = 
	    new Material(new Color(0.8, 0.8, 0.8), // Absorption ambiante
			 new Color(0.8, 0.8, 0.8), // Absorption diffuse
			 0, //Coefficient de reflectance
			 4, //Coefficient de brillance
			 new Color(0, 0, 0), // Coefficient de transmission
			 new Color(0, 0, 0), //Coefficient de reflexion
			 1);

	Material plexiglas = 
	    new Material(new Color(0.05, 0.2, 0.05), // Absorption ambiante
			 new Color(0.05, 0.2, 0.05), // Absorption diffuse
			 0.1, //Coefficient de reflectance
			 6, //Coefficient de brillance
			 new Color(0.8, 0.8, 0.8), // Coefficient de transmission
			 new Color(0.1, 0.1, 0.1), //Coefficient de reflexion
			 1.005);

	Material metal = 
	    new Material(new Color(0.2, 0.2, 0.05), // Absorption ambiante
			 new Color(0.3, 0.3, 0.1), // Absorption diffuse
			 0.96, //Coefficient de reflectance
			 300, //Coefficient de brillance
			 new Color(0, 0, 0), // Coefficient de transmission
			 new Color(0.8, 0.8, 0.4), //Coefficient de reflexion
			 1);

	Material refraction = 
	    new Material(new Color(0.05, 0.1, 0.2), // Absorption ambiante
			 new Color(0.05, 0.1, 0.2), // Absorption diffuse
			 0, //Coefficient de reflectance
			 6, //Coefficient de brillance
			 new Color(0.9, 0.9, 0.9), // Coefficient de transmission
			 new Color(0, 0, 0), //Coefficient de reflexion
			 1.4);

	// Les spheres
	Sphere s;
	s = new Sphere(new Point(0.2, -0.5, 0), 0.1, "sphere", ambiant);
	scene.addElement(s);

	s = new Sphere(new Point(-0.15, -0.15, 0.3), 0.1, "sphere", diffuse);
	scene.addElement(s);

	s = new Sphere(new Point(-0.1, -0.5, 0), 0.1, "sphere", refraction);
	scene.addElement(s);

	s = new Sphere(new Point(0, 0, 0), 0.2, "sphere", reflexionSpec);
	scene.addElement(s);

	Plan surface = new Plan(new Point[]{new Point(0, 0, -0.4),
					    new Point(1, 0, -0.4),
					    new Point(0, -1, -0.4)},
	    "surface",
	    sol);
	scene.addElement(surface);

	// Les sources de lumieres
	SpotLight spot = new SpotLight(new Point(0, -3, 10),
				       new Color(0.8, 0.8, 0.8), "Spotlight");
	scene.addSpotLight(spot);

	spot = new SpotLight(new Point(1, -3, 4),
			     new Color(1, 0, 0), "Spotlight");
	scene.addSpotLight(spot);

	// Les points de vue
	ViewPoint vp = new ViewPoint(new Point(0, -7.5, 2.8),
				     new Vector(0, 1, -0.4),
				     new Vector(0, 0, 1),
				     10,
				     500, //Nombre de colonnes = nb de points en horizontal
				     500, //Nombre de lignes = nb de points en vertical
				     "SphereTest");
	scene.addViewPoint(vp);	

    }
}