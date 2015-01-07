package raytracing.test;

import raytracing.scene.*;
import raytracing.scene.geometry.*;
import raytracing.scene.positionable.*;
import raytracing.scene.positionable.element.*;
import java.util.ArrayList;
public class Test3{
    public static void main(String args[]){
	Material transMat, opaqMat, blancMat, rougeMat, bleuMat;
	ViewPoint vp;
	Sphere s;
	Plan p;
	Screen scr;
	SpotLight sp;

	// // La scène
	Scene scene = new Scene(new Color(0.2, 0.2, 0.2), new Color(0.20,0.20,0.20), 1, 6, "scene"); //Lumiere ambiante1
			
	// Les matériaux		
	transMat = new Material(new Color(0.05, 0.05, 0.05), //Coefficient d'absorption ambiante
				new Color(0.05, 0.05, 0.05), //Coefficient d'absorption diffuse
				0.5, //Coefficient de reflectance
				250, //Coefficient de brillance
				new Color(0.95,0.95,0.95),// Coefficient de transmission
				new Color(0,0,0),//Coefficient de reflexion
				1.01);
			
			
	rougeMat = new Material(new Color(0.7, 0.2, 0.2), //Coefficient d'absorption ambiante
				new Color(0.7, 0.2, 0.2), //Coefficient d'absorption diffuse
				0.2, //Coefficient de reflectance
				250, //Coefficient de brillance
				new Color(0,0,0),// Coefficient de transmission
				new Color(0,0,0),//Coefficient de reflexion
				1.0);
			
	bleuMat = new Material(new Color(0.4, 0.4, 0.9), //Coefficient d'absorption ambiante
			       new Color(0.4, 0.4, 0.9), //Coefficient d'absorption diffuse
			       0.7, //Coefficient de reflectance
			       250, //Coefficient de brillance
			       new Color(0,0,0),// Coefficient de transmission
			       new Color(0.2,0.2,0.5),//Coefficient de reflexion
			       1.0);
		
	opaqMat = new Material(new Color(0.05, 0.05, 0.05), //Coefficient d'absorption ambiante
			       new Color(0.05, 0.05, 0.05), //Coefficient d'absorption diffuse
			       0.5, //Coefficient de reflectance
			       100, //Coefficient de brillance
			       new Color(0,0,0),// Coefficient de transmission
			       new Color(0.8,0.8,0.8),//Coefficient de reflexion
			       1.0);	
			
	blancMat = new Material(new Color(0.90, 0.90, 0.90), //Coefficient d'absorption ambiante
				new Color(0.90, 0.90, 0.90), //Coefficient d'absorption diffuse
				0.5, //Coefficient de reflectance
				100, //Coefficient de brillance
				new Color(0,0,0),// Coefficient de transmission
				new Color(0.1,0.1,0.1),//Coefficient de reflexion
				1.0);		
			
	// Les éléments
	s = new Sphere(new Point(-0.2,0.8,0), 0.8, "s", transMat);
	scene.addElement(s);	
		 
	s = new Sphere(new Point(0,2,4), 0.35, "s", rougeMat);
	scene.addElement(s);	
		 
	s = new Sphere(new Point(1.5,0.6,0), 0.6, "s", bleuMat);
	scene.addElement(s);
		 
	s = new Sphere(new Point(1.75,1.25,-1), 0.35, "s", rougeMat);
	scene.addElement(s);	
		 
	p = new Plan(new Point[]{
		new Point(0,0,0),
		new Point(0,0,1),
		new Point(1,0,0)},
	    "Plan",
	    blancMat);
	scene.addElement(p);
		
	p = new Plan(new Point[]{
		new Point(-1.2,0,0),
		new Point(-1.2,1,0),
		new Point(-1.2,0,1)},
	    "Plan",
	    opaqMat);
	scene.addElement(p);	
		
	Ray ray = new Ray(new Point(0,0,0), new Vector(0,1,0));
		
	// Les sources de lumières
	sp = new SpotLight(new Point(10, 10, -20), new Color(1, 1, 1), "SpotLight 1");
	scene.addSpotLight(sp);
		
	sp = new SpotLight(new Point(1, 10, 10), new Color(1, 1, 1), "SpotLight 1");
	scene.addSpotLight(sp);
		
	// Les points de vue
	scr = new Screen(new Point[]{new Point(-3, 3, 0), new Point(3,3,0), new Point(-3, -3, 0)}, //Premier point comme origine
			 600, //Nombre de colonnes = nb de points en horizontal
			 600); //Nombre de lignes = nb de points en vertical
				
	vp = new ViewPoint(new Point(0, 0.5, -5), scr, "Vp1"); //Source, ecran, nom	
	scene.addViewPoint(vp);	
    }
}
