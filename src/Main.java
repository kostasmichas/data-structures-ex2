import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.Random;

public class Main {
	public static final int N=(int) Math.pow(2,16);
	public static void main(String[] args) {
		System.out.println("-----------------START--------------");
		int[] Marray= {200, 500, 1000, 10000, 30000, 50000, 70000, 100000};
		for(int i =0; i<Marray.length; i++) {
			System.out.println("For M =" + Marray[i] + "--------------------");
			int[][] data = createData(Marray[i]);
			float[] averageDepths;
			KDnode root = null;
			KDtree kd= new KDtree();
			root =insertDataKD(data, root);
			averageDepths=searchDataKD(root, data);
			System.out.println("===============Average depths of k-d tree============");
			System.out.println("Successful: " + averageDepths[0]);
			System.out.println("Not successful: " + averageDepths[1]);
			System.out.println("=====================================================");
			float[][] floatData = intToFloat(data);
			PRQuadtree qt= new PRQuadtree(0,0,N,N);
			qt =insertDataQuad(floatData);
			averageDepths=searchQuad(qt, floatData);
			System.out.println("===============Average depths of Quad tree============");
			System.out.println("Successful: " + averageDepths[0]);
			System.out.println("Not successful: " + averageDepths[1]);
			System.out.println("=====================================================");
			
			
		}
		
		System.out.println("-----------------END--------------");
		

	}

	public static int[][] createData(int M) {
		int[][] result = new int[M][2];
		Random rd = new Random();
		for (int i=0; i<M; i++) {
			result[i][0]=rd.nextInt(N);
			result[i][1]=rd.nextInt(N);
		}
		return result;
	}
	
	public static float[][] intToFloat(int[][] data) {
		float[][] result = new float[data.length][2];
		for (int i=0;i<data.length;i++) {
			result[i][0]=data[i][0];
			result[i][1]=data[i][1];
		}
		return result;
	}
	
	public static KDnode insertDataKD(int[][] data, KDnode root) {
		KDtree kd = new KDtree();
		for(int i=0; i<data.length; i++) {
			root =kd.insert(root, data[i]);
		}
		return root;
	}
	
	public static float[] searchDataKD(KDnode root, int[][] data){
		float[] averageDepths= {0,0}; //0 [0] for successful search, [1] for not
		int[] searches = {0 ,0}; //0 [0] for successful search, [1] for not
		KDtree kd= new KDtree();
		while(searches[0]<100 || searches[1]<100) {
			SearchResult sr=null;
			Random rd= new Random();
			if(searches[0]<100) {
				sr = new SearchResult(kd.search(root, data[rd.nextInt(data.length)]));
			}
			else {
				sr = new SearchResult(kd.search(root, createData(1)[0]));
			}
			if(sr.result && searches[0]<100) {
				//System.out.println("Found with depth " + sr.depth);
				searches[0]++;
				averageDepths[0]+=sr.depth;
			}
			else if ((!sr.result) && searches[1]<100) {
				//System.out.println("Not found with depth " + sr.depth);
				searches[1]++;
				averageDepths[1]+=sr.depth;
			}
		}
		averageDepths[0]=averageDepths[0]/100;
		averageDepths[1]=averageDepths[1]/100;
		return averageDepths;
	}
	
	public static PRQuadtree insertDataQuad(float[][] points) {
		
		PRQuadtree PRQuadtree = new PRQuadtree(0,0,N,N);


		for (int i = 0; i < points.length; i++) {
		    Point2D.Double point = new Double(points[i][0], points[i][1]);
		        PRQuadtree.insert(point);
		}
		
		return PRQuadtree;
		
	}
	
	public static float[] searchQuad(PRQuadtree qt, float[][] data) {
		float[] averageDepths= {0,0}; //0 [0] for successful search, [1] for not
		int[] searches = {0 ,0}; //0 [0] for successful search, [1] for not
		while(searches[0]<100 || searches[1]<100) {
			Random rd = new Random();
			SearchResult sr = null;
			if(searches[0]<100) {
				int randomint=rd.nextInt(data.length);
				sr = new SearchResult(qt.search(new Double(data[randomint][0], data[randomint][1])));
			}
			else
				sr=new SearchResult(qt.search(new Double(createData(1)[0][0], createData(1)[0][1])));
			if(sr.result && searches[0]<100) {
				//System.out.println("Found with depth " + sr.depth);
				searches[0]++;
				averageDepths[0]+=sr.depth;
			}
			else if ((!sr.result) && searches[1]<100) {
				//System.out.println("Not found with depth " + sr.depth);
				searches[1]++;
				averageDepths[1]+=sr.depth;
			}
		}
		averageDepths[0]=averageDepths[0]/100;
		averageDepths[1]=averageDepths[1]/100;
		return averageDepths;
	}
	
}
