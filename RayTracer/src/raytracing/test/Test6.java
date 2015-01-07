package raytracing.test;

import raytracing.scene.*;
import raytracing.scene.geometry.*;
import raytracing.scene.positionable.*;
import raytracing.scene.positionable.element.*;
import java.util.ArrayList;
public class Test6{
    public static void main(String args[]){
	Material transMat, opaqMat, blancMat, rougeRefMat, bleuMat;
	ViewPoint vp;
	Sphere s;
	Plan p;
	Screen scr;
	SpotLight sp;
	Cube c;
	double stwo;
	double hsol;

	// // La scène
	Scene scene = new Scene(new Color(0.2, 0.2, 0.2), // Couleur infinie
	 			new Color(0.20,0.20,0.20),
				1,
				4,
				"scene"); //Lumiere ambiante1
			
	// Les matériaux		
	transMat = new Material(new Color(0.05, 0.05, 0.05), //Coefficient d'absorption ambiante
				new Color(0.05, 0.05, 0.05), //Coefficient d'absorption diffuse
				0.5, //Coefficient de reflectance
				250, //Coefficient de brillance
				new Color(0.95,0.95,0.95),// Coefficient de transmission
				new Color(0,0,0),//Coefficient de reflexion
				1.01);
			
			
	rougeRefMat = new Material(new Color(0.7, 0.2, 0.2), //Coefficient d'absorption ambiante
				new Color(0.7, 0.2, 0.2), //Coefficient d'absorption diffuse
				0.2, //Coefficient de reflectance
				250, //Coefficient de brillance
				new Color(0,0,0),// Coefficient de transmission
				new Color(0.4,0.4,0.4),//Coefficient de reflexion
				1.2);
			
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
			
	blancMat = new Material(new Color(1, 1, 1), //Coefficient d'absorption ambiante
				new Color(1, 1, 1), //Coefficient d'absorption diffuse
				0.5, //Coefficient de reflectance
				100, //Coefficient de brillance
				new Color(0,0,0),// Coefficient de transmission
				new Color(0, 0,0),//Coefficient de reflexion
				1.0);		
			
	// Les éléments
	stwo = Math.sqrt(2);
	hsol = 1.2+0.5 +stwo/4;
	 
	p = new Plan(new Point[]{
		new Point(0,0,0),
		new Point(0,0,1),
		new Point(1,0,0)},
	    "Plan",
	    blancMat);
	scene.addElement(p);

	/*
	c = new Cube(new Point[]{
		new Point(0.5-stwo/4,-0.5-stwo/4 + hsol,0),
		new Point(-stwo/4,-stwo/4 + hsol ,stwo/2),
		new Point(0.5+stwo/4,-0.5+stwo/4 + hsol ,0)},
	    "cube",
	    rougeRefMat);
	scene.addElement(c);		
	*/	
		
		
	// Les sources de lumières
	sp = new SpotLight(new Point(10, 10, -20), new Color(1, 1, 1), "SpotLight 1");
	scene.addSpotLight(sp);
	
	// Les points de vue

	vp = new ViewPoint(new Point(0.5-stwo/4 + 3, 1, 4),
			   new Vector(0, 0, -1),
			   new Vector(0, 1, 0),
			   50,
			   100,
			   100,
			   "CubeSurPointe");
	scene.addViewPoint(vp);	

    }
}

