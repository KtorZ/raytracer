// -*- coding: iso-8859-1; -*-

package raytracing.test;

import raytracing.scene.*;
import raytracing.scene.geometry.*;
import raytracing.scene.positionable.*;
import raytracing.scene.positionable.element.*;
import java.util.ArrayList;
public class SceneOP {
    
    public static void main(String args[]) {
	
	// La scene
	Scene scene = new Scene(new Color(0.2, 0.2, 0.2), // Lumiere ambiante
				new Color(0, 0, 0), // Couleur infini
				1, // Indice de refraction de la scene
				5, // Nombre d'iterations
				"Scene"
				); 

	// Les materiaux
	Material porcelaine = 
	    new Material(new Color(0.95, 0.95, 0.85), // Absorption ambiante
			 new Color(0.95, 0.95, 0.85), // Absorption diffuse
			 0.95, //Coefficient de reflectance
			 250, //Coefficient de brillance
			 new Color(0, 0, 0), // Coefficient de transmission
			 new Color(0.4, 0.4, 0.4), //Coefficient de reflexion
			 1);

	Material platre = 
	    new Material(new Color(0.4, 0.4, 0.9), // Absorption ambiante
			 new Color(0.4, 0.4, 0.9), // Absorption diffuse
			 0.5, //Coefficient de reflectance
			 2, //Coefficient de brillance
			 new Color(0, 0, 0), // Coefficient de transmission
			 new Color(0, 0, 0), //Coefficient de reflexion
			 1);

	Material platreVert = 
	    new Material(new Color(0.4, 0.9, 0.4), // Absorption ambiante
			 new Color(0.4, 0.9, 0.4), // Absorption diffuse
			 0.5, //Coefficient de reflectance
			 2, //Coefficient de brillance
			 new Color(0, 0, 0), // Coefficient de transmission
			 new Color(0, 0, 0), //Coefficient de reflexion
			 1);


	Material platreRouge = 
	    new Material(new Color(0.9, 0, 0), // Absorption ambiante
			 new Color(0.9, 0, 0), // Absorption diffuse
			 0.5, //Coefficient de reflectance
			 2, //Coefficient de brillance
			 new Color(0, 0, 0), // Coefficient de transmission
			 new Color(0, 0, 0), //Coefficient de reflexion
			 1);

	Material sol = 
	    new Material(new Color(0.8, 0.8, 0.8), // Absorption ambiante
			 new Color(0.8, 0.8, 0.8), // Absorption diffuse
			 0.3, //Coefficient de reflectance
			 40, //Coefficient de brillance
			 new Color(0, 0, 0), // Coefficient de transmission
			 new Color(0.3, 0.3, 0.3), //Coefficient de reflexion
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
	    new Material(new Color(0.05, 0.1, 0.05), // Absorption ambiante
			 new Color(0.1, 0.2, 0.1), // Absorption diffuse
			 0.96, //Coefficient de reflectance
			 650, //Coefficient de brillance
			 new Color(0, 0, 0), // Coefficient de transmission
			 new Color(0.4, 0.7, 0.4), //Coefficient de reflexion
			 1);

	Material miroir = 
	    new Material(new Color(0.3, 0.3, 0.3), // Absorption ambiante
			 new Color(0.3, 0.3, 0.3), // Absorption diffuse
			 0.4, //Coefficient de reflectance
			 200, //Coefficient de brillance
			 new Color(0, 0, 0), // Coefficient de transmission
			 new Color(0.7, 0.7, 0.7), //Coefficient de reflexion
			 1);

	Material verre = 
	    new Material(new Color(0.05, 0.1, 0.2), // Absorption ambiante
			 new Color(0.05, 0.1, 0.2), // Absorption diffuse
			 0.1, //Coefficient de reflectance
			 6, //Coefficient de brillance
			 new Color(0.9, 0.9, 0.9), // Coefficient de transmission
			 new Color(0.4, 0.4, 0.4), //Coefficient de reflexion
			 1.04);

	// Les spheres
	Sphere s = new Sphere(new Point(0, 0, 2), 2, "Transparente", verre);
	scene.addElement(s);

	s = new Sphere(new Point(4, -4, 3), 0.5, "Porcelaine", porcelaine);
	scene.addElement(s);

	s = new Sphere(new Point(1, 1, 3.5), 1, "Bleu", platre);
	scene.addElement(s);

	s = new Sphere(new Point(-0.5, -3, -2), 3, "Coupee", sol);
	scene.addElement(s);

	// Le cube
	Cube c = new Cube(new Point[] {new Point(3.5, -0, 0.4),
				       new Point(2.5, -1.5, 0.4),
				       new Point(5, -1, 0.4)},
	    "Cube", 
			  platreVert);
	scene.addElement(c);

	Plan surface = new Plan(new Point[]{new Point(0, 0, 0),
					    new Point(1, 0, 0),
					    new Point(0, -1, 0)},
	    "sol",
	    sol);
	scene.addElement(surface);

	surface = new Plan(new Point[]{new Point(-3, 0, 0),
				       new Point(-3, 1, 0),
				       new Point(-3, 0, 1)},
	    "miroir",
	    miroir);
	scene.addElement(surface);

	// Les sources de lumieres
	SpotLight spot = new SpotLight(new Point(4, -15, 7),
				       new Color(1, 1, 1), "Devant");
	scene.addSpotLight(spot);

	spot = new SpotLight(new Point(1, 5, 7),
			     new Color(1, 1, 1), "Derriere");
	scene.addSpotLight(spot);


	// Les points de vue
	ViewPoint vp = new ViewPoint(new Point(2, -10, 2),
				     new Vector(-0.4, 2, 0),
				     new Vector(0, 0, 1),
				     70,
				     1200, //Nombre de colonnes = nb de points en horizontal
				     1200, //Nombre de lignes = nb de points en vertical
				     "SceneOP");
	scene.addViewPoint(vp);	
	    
    }
}