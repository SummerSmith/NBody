
public class Planet {

	double myXPos;
	double myYPos;
	double myXVel;
	double myYVel;
	double myMass;
	String myFileName;

	// constructor that takes 6 parameters
	public Planet(double xP, double yP, double xV, double yV, double m, String img) {
		myXPos = xP;
		myYPos = yP;
		myXVel = xV;
		myYVel = yV;
		myMass = m;
		myFileName = img;
	}

	// constructor that takes a planet as a parameter
	public Planet(Planet P) {
		this.myXPos = P.myXPos;
		this.myYPos = P.myYPos;
		this.myXVel = P.myXVel;
		this.myYVel = P.myYVel;
		this.myMass = P.myMass;
		this.myFileName = P.myFileName;
	}

	// calculating the distance between 2 planets
	public double calcDistance(Planet otherPlanet) {
		return Math.sqrt((this.myXPos - otherPlanet.myXPos) * (this.myXPos - otherPlanet.myXPos)
				+ (this.myYPos - otherPlanet.myYPos) * (this.myYPos - otherPlanet.myYPos));
	}

	// calculated the force exerted on one planet by another
	public double calcForceExertedBy(Planet P) {
		double G = 6.67 * Math.pow(10, -11);
		double rSquared = calcDistance(P) * calcDistance(P);
		if (rSquared != 0) {
			return ((G * this.myMass * P.myMass) / rSquared);
		}
		else{
			return 0;
		}
	}

	// calculating the force exerted by the x factor of one planet on another
	public double calcForceExertedByX(Planet P) {
		return (calcForceExertedBy(P) * (P.myXPos - this.myXPos) / calcDistance(P));
	}

	// calculating the force exerted by the y factor of one planet on another
	public double calcForceExertedByY(Planet P) {
		return (calcForceExertedBy(P) * (P.myYPos - this.myYPos) / calcDistance(P));
	}

	public double calcNetForceExertedByX(Planet[] allPlanets) {
		double sum = 0;
		for (int i = 0; i < allPlanets.length; i++) {
			if (allPlanets[i].equals(this)) {
				continue;
			}
			sum += calcForceExertedByX(allPlanets[i]);
		}
		return sum;
	}

	public double calcNetForceExertedByY(Planet[] allPlanets) {
		double sum = 0;
		for (int i = 0; i < allPlanets.length; i++) {
			if (allPlanets[i].equals(this)) {
				continue;
			}
			sum += calcForceExertedByY(allPlanets[i]);
		}

		return sum;
	}

	// updates the class variables given new information
	public void update(double seconds, double xforce, double yforce) {
		double xAcceleration = xforce / myMass;
		double yAcceleration = yforce / myMass;
		myXVel = myXVel + (seconds * xAcceleration);
		myYVel = myYVel + (seconds * yAcceleration);
		myXPos = myXPos + (seconds * myXVel);
		myYPos = myYPos + (seconds * myYVel);
	}

	public void draw(double myXPos2, double myYPos2, String myFileName) {
		StdDraw.picture(myXPos2, myYPos2, myFileName);
	}

}