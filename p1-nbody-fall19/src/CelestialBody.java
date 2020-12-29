

/**
 * Celestial Body class for NBody
 * @author ola
 *
 */
public class CelestialBody {

	private double myXPos;
	private double myYPos;
	private double myXVel;
        private double myYVel;
	private double myMass;
	private String myFileName;

	/**
	 * Create a Body from parameters	
	 * @param xp initial x position
	 * @param yp initial y position
	 * @param xv initial x velocity
	 * @param yv initial y velocity
	 * @param mass of object
	 * @param filename of image for object animation
	 */
	
	
	public CelestialBody(double xp, double yp, double xv,
			             double yv, double mass, String filename){
		myXPos = xp;
		myYPos = yp;
		myXVel = xv;
		myYVel = yv;
		myMass = mass;
		myFileName = filename;
	}

	/**
	 * Copy constructor: copy instance variables from one
	 * body to this body
	 * @param b used to initialize this body
	 */
	public CelestialBody(CelestialBody b){
		myXPos = b.getX();
		myYPos = b.getY();
		myXVel = b.getXVel();
		myYVel = b.getYVel();
		myMass = b.getMass();
		myFileName = b.getName();
	}
	/**
	 * Returns x position of this body
	 * @return value of x position
	 */
	public double getX() {
		return myXPos;
	}
	/**
	 * Returns y position of this Body
	 * @return value of y position
	 */
	public double getY() {
		return myYPos;
	}
	/**
	 * Returns x velocity of this Body
	 * @return value of x velocity
	 */
	public double getXVel() {
		return myXVel;
	}
	/**
	 * Return y-velocity of this Body.
	 * @return value of y-velocity.
	 */
	public double getYVel() {
		return myYVel;
	}
	/**
	 * Returns mass of this Body
	 * @return value of mass
	 */
	public double getMass() {
		return myMass;
	}
	/**
	 * Returns file name of this Body
	 * @return value of filename
	 */
	public String getName() {
		return myFileName;
	}

	/**
	 * Return the distance between this body and another
	 * @param b the other body to which distance is calculated
	 * @return distance between this body and b
	 */
	public double calcDistance(CelestialBody b) {
		return Math.sqrt(Math.pow((myXPos - b.getX()),2) + Math.pow(myYPos-b.getY(), 2));
	}
	
	/**
	 * Return the force exerted upon this body by another
	 * @param b the other body that exerts a force on this body
	 * @return force exerted by b on this body
	 */
	public double calcForceExertedBy(CelestialBody b) {
		return ((6.67*1e-11)*myMass*b.getMass())/Math.pow(this.calcDistance(b), 2);
	}

	/**
	 * Return the force exerted upon this body by another in the x-direction
	 * @param b the other body that exerts a force on this body
	 * @return the force exerted by b in the x-direction on this body
	 */
	public double calcForceExertedByX(CelestialBody b) {
		return (this.calcForceExertedBy(b)*(b.getX()-myXPos))/this.calcDistance(b);
	}
	/**
	 * Return the force exerted upon this body by another in the y-direction
	 * @param b the other body that exerts a force on this body
	 * @return the force exerted by b in the y-direction on this body
	 */	
	public double calcForceExertedByY(CelestialBody b) {
		return (this.calcForceExertedBy(b)*(b.getY()-myYPos))/this.calcDistance(b);

	}
	
	/**
	 * Return the net force exerted upon this body by all other bodies in the x-direction
	 * @param bodies the rest of the bodies not including this body that exert a gravitational force upon it
	 * @return the net force exerted by all other bodies on this body in the x-direction
	 */
	public double calcNetForceExertedByX(CelestialBody[] bodies) {
		double ret = 0;
		for (CelestialBody b : bodies) {
			if (!b.equals(this)) {
				ret += this.calcForceExertedByX(b);
			}
		}
		return ret;
	}
	/**
	 * Return the net force exerted upon this body by all other bodies in the y-direction
	 * @param bodies the rest of the bodies not including this body that exert a gravitational force upon it
	 * @return the net force exerted by all other bodies on this body in the y-direction
	 */
	public double calcNetForceExertedByY(CelestialBody[] bodies) {
		double ret = 0;
		for (CelestialBody b : bodies) {
			if (!b.equals(this)) {
				ret += this.calcForceExertedByY(b);
			}
		}
		return ret;
	}
	
	/**
	 * Changes the values of x and y positions as well as x and y velocities based upon given forces in the x and y direction
	 * @param deltaT the time-step after which the new set of position and velocity variables are calculated
	 * @param xforce the force exerted upon this body in the x-direction
	 * @param yforce the force exerted upon this body in the y-direction
	 */
	public void update(double deltaT, 
			           double xforce, double yforce) {
		double ax = xforce/myMass;
		double ay = yforce/myMass;
		double nxv = myXVel + deltaT*ax;
		double nyv = myYVel + deltaT*ay;
		double nx = myXPos + deltaT*nxv;
		double ny = myYPos + deltaT*nyv;
		myXPos = nx;
		myYPos = ny;
		myXVel = nxv;
		myYVel = nyv;
	}
	/**
	 * Draws the Body in the GUI
	 */
	public void draw() {
		StdDraw.picture(myXPos, myYPos, "images/"+myFileName);
	}
}
