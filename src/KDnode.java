
public class KDnode {
	int[] point;
    KDnode left, right;
 
    public KDnode(int[] arr)
    {
        this.point = arr;
        this.left = this.right = null;
    }
}

