
public class PercolationDFSFast extends PercolationDFS{

	public PercolationDFSFast(int n) {
		super(n);
	}
	
	@Override
	public void updateOnOpen(int row, int col) {
		boolean mark = false;
		if (! inBounds(row,col)) return;
		if(row == 0) {
			mark = true;
		}
		if(row != 0) {
    		if(myGrid[row-1][col] == FULL)	mark = true;
    	}
    	if(row != myGrid.length-1) {
    		if(myGrid[row+1][col] == FULL)	mark = true;
    	}
    	if(col != 0) {
    		if(myGrid[row][col-1] == FULL)	mark = true;
    	}
    	if(col != myGrid.length-1) {
    		if(myGrid[row][col+1] == FULL)	mark = true;
    	}
    	if (mark)	dfs(row, col);
	}
}
