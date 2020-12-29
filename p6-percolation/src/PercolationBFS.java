import java.util.LinkedList;
import java.util.Queue;

public class PercolationBFS extends PercolationDFSFast{

	public PercolationBFS(int n) {
		super(n);
	}
	@Override
	public void dfs(int row, int col) {
		int size = myGrid.length;
		
		int[] dx = {-1,1,0,0};
		int[] dy = {0,0,-1,1};
		
		Queue<Integer> q = new LinkedList<>();
		myGrid[row][col] = FULL;
		q.add(row*size + col);
		while(q.size() != 0) {
			int i = q.remove();
			for(int k = 0; k < dx.length; k++) {
				row = i/size + dx[k];
				col = i%size + dy[k];
				if(inBounds(row,col) && myGrid[row][col] == OPEN) {
					q.add(row*size+col);
					myGrid[row][col] = FULL;
				}
			}
		}
	}
}
