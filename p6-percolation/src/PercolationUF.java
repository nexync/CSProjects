
public class PercolationUF implements IPercolate{
	private IUnionFind myFinder;
	private boolean[][] myGrid;
	private final int VTOP;
	private final int VBOTTOM;
	private int myOpenCount;
	
	PercolationUF(IUnionFind finder, int size) {
		this.VTOP = size*size;
		this.VBOTTOM = size*size +1;
		
		myFinder = finder;
		myFinder.initialize(size*size+2);
		
		myGrid = new boolean[size][size];
		myOpenCount = 0;
	}
	@Override
	public void open(int row, int col) {
		if(!inBounds(row,col))	throw new IndexOutOfBoundsException ();
		if(!isOpen(row,col)) {
			myGrid[row][col] = true;
			myOpenCount++;
			int[] dx = {-1,1,0,0};
			int[] dy = {0,0,-1,1};
			if(row == 0) {
				myFinder.union(row*myGrid.length+col, VTOP);
			}
			if(row == myGrid.length-1) {
				myFinder.union(row*myGrid.length+col, VBOTTOM);
			}
			for(int k = 0; k<4; k++) {
				int newrow = row + dx[k];
				int newcol = col + dy[k];
				if(inBounds(newrow, newcol) && myGrid[newrow][newcol]== true) {
					myFinder.union(newrow*myGrid.length+newcol, 
							row*myGrid.length + col);
				}
			}
		}
	}

	@Override
	public boolean isOpen(int row, int col) {
		if(!inBounds(row,col))	throw new IndexOutOfBoundsException ();
		else	return myGrid[row][col];
	}

	@Override
	public boolean isFull(int row, int col) {
		if(!inBounds(row,col))	throw new IndexOutOfBoundsException ();
		return myFinder.connected(row*myGrid.length+col, VTOP);
	}

	@Override
	public boolean percolates() {
		return myFinder.connected(VTOP, VBOTTOM);
	}

	@Override
	public int numberOfOpenSites() {
		return myOpenCount;
	}
	private boolean inBounds(int row, int col) {
		if (row < 0 || row >= myGrid.length) return false;
		if (col < 0 || col >= myGrid[0].length) return false;
		return true;
	}
}
