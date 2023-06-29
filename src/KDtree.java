//https://www.geeksforgeeks.org/search-and-insertion-in-k-dimensional-tree/
public class KDtree {

	int k = 2; // 2 dimensional

	public KDnode newNode(int[] arr) {
		return new KDnode(arr);
	}

	public KDnode insertRec(KDnode root, int[] point, int depth) {
		if (root == null) {
			return newNode(point);
		}

		int cd = depth % k;
		if (point[cd] < root.point[cd]) {
			root.left = insertRec(root.left, point, depth + 1);
		} else {
			root.right = insertRec(root.right, point, depth + 1);
		}

		return root;
	}

	public KDnode insert(KDnode root, int[] point) {
		return insertRec(root, point, 0);
	}

	public boolean arePointsSame(int[] point1, int[] point2) {
		for (int i = 0; i < k; ++i) {
			if (point1[i] != point2[i]) {
				return false;
			}
		}
		return true;
	}

	public SearchResult searchRec(KDnode root, int[] point, int depth) {
		if (root == null) {

			return new SearchResult(false, depth);
		}
		if (arePointsSame(root.point, point)) {
			return new SearchResult(true, depth);
		}

		int cd = depth % k;
		if (point[cd] < root.point[cd]) {
			return searchRec(root.left, point, depth + 1);
		}
		return searchRec(root.right, point, depth + 1);
	}

	public SearchResult search(KDnode root, int[] point) {
		return new SearchResult(searchRec(root, point, 0));
	}

}
