package labor01.num2;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Tools {
	
	// TODO: get resources from class on correctedFilename ( "ClassName".class.getResourceAsStream(...) + InputStreamReader )
	
	public static void main (String[] args) {
		try {
			correctQuadrants("/labor01/punkte.dat", "src/main/resources/labor01/punkte_korr.dat");
			List<Point> points = readPointsFromQuadrant("src/main/resources/labor01/punkte_korr.dat", 1);
			
			for (Point p : points) {
				System.out.println(p);
			}
			
			for (int i = 1; i <= 4; i++) {
				points = readPointsFromQuadrant("src/main/resources/labor01/punkte_korr.dat", i);
				System.out.println("Quadrant " + i + ": " + points.size());
			}
		} catch (IOException e) {
			System.err.println(e);
		}
	}
	
	static void correctQuadrants (String filename, String correctedFilename) throws IOException {
		try (DataInputStream dis = new DataInputStream(Tools.class.getResourceAsStream(filename));
			 DataOutputStream dos = new DataOutputStream(new FileOutputStream(correctedFilename))) {
			while (dis.available()>= 20) {
				dis.readInt();
				double x = dis.readDouble();
				double y = dis.readDouble();
				int quadrant;
				
				if (x >= 0.0 && y >= 0.0)
					quadrant = 1;
				else if (x < 0.0 && y >= 0.0)
					quadrant = 2;
				else if (x < 0.0 && y < 0.0)
					quadrant = 3;
				else
					quadrant = 4;
				
				dos.writeInt(quadrant);
				dos.writeDouble(x);
				dos.writeDouble(y);
			}
		}
	}
	
	static List<Point> readPointsFromQuadrant (String filename, int quadrant) throws IOException {
		try (DataInputStream dis = new DataInputStream(new FileInputStream(filename))) {
			List<Point> points = new ArrayList<>();
			while (dis.available()>= 20) {
				int actualQuadrant = dis.readInt();
				double x = dis.readDouble();
				double y = dis.readDouble();
				
				if (actualQuadrant == quadrant)
					points.add(new Point(x, y));
			}
			return points;
		}
	}
}

class Point {
	private double x;
	private double y;
	
	public Point (double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public String toString () {
		return "(" + x + "; " + y + ")";
	}
	
	public double getX () {
		return x;
	}
	
	public double getY () {
		return y;
	}
}