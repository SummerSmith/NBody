import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class NBody {
		

		public static void main(String[] args){
			double T = 157788000.0;
			double dt = 25000.0;
			double time = 0;
			String pfile = "data/planets.txt";
			if (args.length > 2) {
				T = Double.parseDouble(args[0]);
				dt = Double.parseDouble(args[1]);
				pfile = args[2];
			}	
			Planet[] planets = null;
			double radius = 0.0;
			
			//assigns radius and array of planets using methods created below the main method
			radius = readRadius("data/planets.txt");
			planets = readPlanets("data/planets.txt");
			
			//set the scale of the drawing window to radius
			StdDraw.setScale(-radius, radius);
			//open star field as the background
			StdDraw.picture(0, 0, "images/starfield.jpg");
			//go through all the planets in array planets and draw them 
			for (int i=0; i<planets.length; i++) {
				planets[i].draw();
			}
			//play sound track in the background
			StdAudio.play("audio/2001.mid");
			
			//creating a loop to iterate through time, updating the planets as time goes on 
			do  {
				//creating two arrays to hold the net force values
				double[] xForces = new double[5];
				double[] yForces = new double[5];
				//going through the array of planets to calculate the net forces and add them to the array 
				for (int k = 0; k<5; k++) {
					xForces[k] = planets[k].calcNetForceExertedByX(planets);
					yForces[k] = planets[k].calcNetForceExertedByY(planets);
				}
				//updating the forces and time of each planet
				for (int j = 0; j<5; j++) {
					planets[j].update(dt, xForces[j], yForces[j]);
				}
				//redrawing the pictures/planets
				StdDraw.picture(0, 0, "images/starfield.jpg");
				for (int s=0; s<planets.length; s++) {
					planets[s].draw();
				}
				StdDraw.show(10);
				//incrementing the time variable
				time += dt;
			} while (time < T);
		
			System.out.printf("%d\n", planets.length);
			System.out.printf("%.2e\n", radius);
			for (int i = 0; i < planets.length; i++) {
			    System.out.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
			   		              planets[i].myXPos, planets[i].myYPos, 
			                      planets[i].myXVel, planets[i].myYVel, 
			                      planets[i].myMass, planets[i].myFileName);	
			}
			
	}

//reads the radius from the inputed file name 
	public static double readRadius(String fileName) {
		Scanner scan = null;
		try {
			scan = new Scanner(new File(fileName));
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		scan.nextDouble();
		double radius = scan.nextDouble();
		return radius;
	}

//creates an array of planets from the inputed file name
	public static Planet[] readPlanets(String fileName) {
		Scanner scan = null;
		try {
			scan = new Scanner(new File(fileName));
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		int rows = scan.nextInt();
		Planet [] planets = new Planet[rows];
		scan.nextDouble();
		for (int i = 0; i<rows; i++) {
			double x= scan.nextDouble();
			double y= scan.nextDouble();
			double xVel = scan.nextDouble();
			double yVel = scan.nextDouble();
			double mass = scan.nextDouble();
			String file= scan.next();
			planets[i] = new Planet(x, y, xVel, yVel, mass, file);
		}
		return planets; 
	}
	
	
}	