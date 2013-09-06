public class RBT extends Dictionary{
	
	private Node nil;  //Sentinel nil
    private Node root;
	
	private class Node {
        private int key;
        private char color;
        private Node left, right, p;

        public Node(int key, char color) {
            this.key = key;
            this.color = color;
            this.left = nil;
            this.right = nil;
            this.p = nil;
        }
        
        public boolean equals(Object other){ //Overriding equals()
        	
    	    boolean result;
    	    
    	    if((other == null) || (getClass() != other.getClass())){
    	        result = false;
    	    }
    	    else{
    	        Node otherNode = (Node)other;
    	        result = key == otherNode.key &&  color == otherNode.color;
    	    }

    	    return result;
    	}
	}
	
	public RBT(){  //initialize Root and Tail
		root = new Node(-1,'B');
    	nil = new Node(-1,'B');
    }
	
	private void leftRotate(Node x){ //Left Rotate about x
		
		Node y = x.right;
		x.right = y.left;
		
		if(!y.left.equals(nil))
			y.left.p = x; 
		y.p = x.p;
		
		if(x.p.equals(nil))
			root = y;
		else if(x.equals(x.p.left))
			x.p.left = y;
		else
			x.p.right = y;
		
		y.left = x;
		x.p = y;
	}
	
	private void rightRotate(Node y){ //Right Rotate about y
		
		Node x = y.left;
		y.left = x.right;
		
		if(!x.right.equals(nil))
			x.right.p = y; 
		x.p = y.p;
		
		if(y.p.equals(nil))
			root = x;
		else if(y.equals(y.p.left))
			y.p.left = x;
		else
			y.p.right = x;
		
		x.right = y;
		y.p = x;
	}
	
	public void insert(int val){
		
		Node y = nil;
		Node x = root;
		
		Node z = new Node(val,'R');
		
		while(!x.equals(nil)){
			y = x;
			
			if(z.key < x.key)
				x = x.left;
			else
				x = x.right;
		}
			
		z.p = y;
			
		if(y.equals(nil))   //Empty Tree
			root = z;
		else if(z.key < y.key)
			y.left = z;
		else
			y.right = z;
			
		insertFixup(z);
		
	}
	
	private void insertFixup(Node z){ //Fix the Violations if any
		while(z.p.color == 'R')
			if(z.p.equals(z.p.p.left)){  //Case 1 
				Node y = z.p.p.right;
				if(y.color == 'R'){
					z.p.color = 'B';
					y.color = 'B';
					z.p.p.color = 'R';
					z = z.p.p;
				}
				else if(z.equals(z.p.right)){ //Case 2
					z = z.p;
					leftRotate(z);
					z.p.color = 'B';
					z.p.p.color = 'R';
					rightRotate(z.p.p);
				}
				else{ ////Case 3
					z.p.color = 'B';
					z.p.p.color = 'R';
					rightRotate(z.p.p);
				}					
			}
			else{ //Symmetric to above cases
				Node y = z.p.p.left;
				if(y.color == 'R'){
					z.p.color = 'B';
					y.color = 'B';
					z.p.p.color = 'R';
					z = z.p.p;
				}
				else if(z.equals(z.p.left)){
					z = z.p;
					rightRotate(z);
					z.p.color = 'B';
					z.p.p.color = 'R';
					leftRotate(z.p.p);
				}
				else{
					z.p.color = 'B';
					z.p.p.color = 'R';
					leftRotate(z.p.p);
				}
			}
		root.color = 'B';
	}
  
	public boolean search(int k){
		
		Node x = root;
		
		while(!x.equals(nil) && k != x.key)
			if(k < x.key)
				x = x.left;
			else
				x = x.right;
		
		if(k == x.key)
			return true; //Found
		return false; //Not Found
	}
	
	private Node searchNode(int k){
		
		Node x = root;
		
		while(!x.equals(nil) && k != x.key)
			if(k < x.key)
				x = x.left;
			else
				x = x.right;
		
		return x;
	}
	
	void inorderTreeWalk(Node x){ //Inorder Traversal
		if(!x.equals(nil)){
			inorderTreeWalk(x.left);
			System.out.println(" "+x.key+" : "+x.color);
			inorderTreeWalk(x.right);
		}
	}
  
	public boolean delete(int value){
		Node z = searchNode(value);
		
		if(!z.equals(nil)){ //If node found then delete
			Node y = z;
			char y_original_color = y.color;
			Node x;
			
			if(z.left.equals(nil)){
				x = z.right;
				transplant(z,z.right);
			}
			else if(z.right.equals(nil)){
				x = z.left;
				transplant(z,z.left);
			}
			else{
				y = minimum(z.right);
				y_original_color = y.color;
				x  = y.right;
				
				if(y.p.equals(z))
					x.p = y;
				else{
					transplant(y,y.right);
					y.right = z.right;
					y.right.p = y;
				}
				
				transplant(z,y);
				y.left = z.left;
				y.left.p = y;
				y.color = z.color;
			}
			if(y_original_color == 'B')
				deleteFixup(x);
			return true;
		}
		return false; //Node Not Found
	}
	
	private Node minimum(Node x) {
		
		while(!x.left.equals(nil))
			x = x.left;
		
		return x;
	}

	private void transplant(Node u, Node v) {
		
		if(u.p.equals(nil))
			root = v;
		else if(u.equals(u.p.left))
			u.p.left = v;
		else
			u.p.right = v;
		
		v.p = u.p;
	}

	private void deleteFixup(Node x){ //Fix the Violations in Delete
		Node w;
		
		while(!x.equals(root) && x.color == 'B'){
			
			if(x.equals(x.p.left)){
				
				w = x.p.right;
				
				if(w.color == 'R'){ //Case 1
					w.color = 'B';
					x.p.color = 'R';
					leftRotate(x.p);
					w = x.p.right;
				}
				
				if(w.left.color == 'B' && w.right.color == 'B'){ //Case 2
					w.color = 'R';
					x = x.p;
				}
				else if(w.right.color == 'B'){//Case 3
					w.left.color = 'B';
					w.color = 'R';
					rightRotate(w);
					w = x.p.right;
					
					w.color = x.p.color;
					x.p.color = 'B';
					w.right.color = 'B';
					leftRotate(x.p);
					x = root;
				}
				else{ //Case 4
					w.color = x.p.color;
					x.p.color = 'B';
					w.right.color = 'B';
					leftRotate(x.p);
					x = root;
				}
			}
			else{ // Symmetric to above cases
				
				w = x.p.left;
				
				if(w.color == 'R'){
					w.color = 'B';
					x.p.color = 'R';
					rightRotate(x.p);
					w = x.p.left;
				}
				
				if(w.right.color == 'B' && w.left.color == 'B'){
					w.color = 'R';
					x = x.p;
				}
				else if(w.left.color == 'B'){
					w.right.color = 'B';
					w.color = 'R';
					leftRotate(w);
					w = x.p.left;
					
					w.color = x.p.color;
					x.p.color = 'B';
					w.left.color = 'B';
					rightRotate(x.p);
					x = root;
				}
				else{
					w.color = x.p.color;
					x.p.color = 'B';
					w.left.color = 'B';
					rightRotate(x.p);
					x = root;
				}
			
			}
		}
		x.color = 'B';
	}
  
	public void clearADT(){
		root = nil;
  	}
  
  	public void display(){
  		System.out.println("Tree Data : ");
  		inorderTreeWalk(root);
  	}
  	
  	public static void main(String[] args){
  		RBT t = new RBT();
  		t.display();
  		
  		int array[] = {5,4,3};
  		for(int i=0;i<array.length;i++){
  			t.insert(array[i]);
  		}
  		t.display();
  	}

	
}