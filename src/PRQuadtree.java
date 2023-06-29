import java.awt.geom.Point2D;


public class PRQuadtree {
	private double xMin;
	private double yMin;
	private double xMax;
	private double yMax;
	private Point2D.Double data;
	private PRQuadtree nwChild;
	private PRQuadtree neChild;
	private PRQuadtree swChild;
	private PRQuadtree seChild;

	public PRQuadtree(double xMin, double yMin, double xMax, double yMax) {
		this.xMin = xMin;
		this.yMin = yMin;
		this.xMax = xMax;
		this.yMax = yMax;
		this.data = null;
		this.nwChild = null;
		this.neChild = null;
		this.swChild = null;
		this.seChild = null;
	}

	public SearchResult search(Point2D.Double point) {
		SearchResult result = new SearchResult(false, 0);
		double x = point.x;
		double y = point.y;

		if (x < xMin || x > xMax || y < yMin || y > yMax) {
			return result;
		}

		if (data != null && data.equals(point)) {
			result.result = true;
			return result;
		}

		if (nwChild != null) {
			double xMid = (xMin + xMax) / 2;
			double yMid = (yMin + yMax) / 2;
			if (x < xMid) {
				if (y < yMid) {
					result = nwChild.search(point);
				} else {
					result = swChild.search(point);
				}
			} else {
				if (y < yMid) {
					result = neChild.search(point);
				} else {
					result = seChild.search(point);
				}
			}
		}

		result.depth++;
		return result;
	}

	public void insert(Point2D.Double point) {
		double x = point.x;
		double y = point.y;

		if (x < xMin || x > xMax || y < yMin || y > yMax) {
			return;
		}

		if (data == null) {
			data = point;
			return;
		}

		if (nwChild == null) {
			double xMid = (xMin + xMax) / 2;
			double yMid = (yMin + yMax) / 2;
			nwChild = new PRQuadtree(xMin, yMin, xMid, yMid);
			neChild = new PRQuadtree(xMid, yMin, xMax, yMid);
			swChild = new PRQuadtree(xMin, yMid, xMid, yMax);
			seChild = new PRQuadtree(xMid, yMid, xMax, yMax);
		}

		double xMid = (xMin + xMax) / 2;
		double yMid = (yMin + yMax) / 2;
		if (x < xMid) {
			if (y < yMid) {
				nwChild.insert(point);
			} else {
				swChild.insert(point);
			}
		} else {
			if (y < yMid) {
				neChild.insert(point);
			} else {
				seChild.insert(point);
			}
		}
	}

	
}