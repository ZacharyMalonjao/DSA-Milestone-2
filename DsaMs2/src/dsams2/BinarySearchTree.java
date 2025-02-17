/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dsams2;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
    import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
/**
 *
 * @author zach malonjao
 */
public class BinarySearchTree {
       private Node root;

    public BinarySearchTree() {
        this.root = null;
    }

    public void insert(int id, String date, String stockLabel, String brand, String engineNumber, String status) {
        root = insertRecursive(root, id, date, stockLabel, brand, engineNumber, status);
    }

            private Node insertRecursive(Node node, int id, String date, String stockLabel, String brand, String engineNumber, String status) {
                if (node == null) {
                    return new Node(id, date, stockLabel, brand, engineNumber, status);
                }

                if (id < node.id) {  // Sort by ID
                    node.left = insertRecursive(node.left, id, date, stockLabel, brand, engineNumber, status);
                } else {
                    node.right = insertRecursive(node.right, id, date, stockLabel, brand, engineNumber, status);
                }

                return node;
            }

    // In-order traversal to display the BST
    public void displayBST() {
        inOrderTraversalRecursive(root);
    }

        private void inOrderTraversalRecursive(Node node) {
            if (node != null) {
                inOrderTraversalRecursive(node.left);
                System.out.println(node.id + ", " + node.date + ", " + node.stockLabel + ", " + node.brand + ", " + node.engineNumber + ", " + node.status);
                inOrderTraversalRecursive(node.right);
            }
        }
        
    public int findMax() {
    if (root == null) {
        throw new IllegalStateException("The BST is empty.");
    }
    return findMaxRecursive(root);
}

        private int findMaxRecursive(Node node) {
            // Keep going to the right until we reach the last node
            while (node.right != null) {
                node = node.right;
            }
            return node.id; // Return the id of the maximum node
        }    
        
        
         public void writeBSTtoCSV(String csvFile) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile))) {
            writeBSTtoCSVRecursive(root, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeBSTtoCSVRecursive(Node node, BufferedWriter writer) throws IOException {
        if (node != null) {
            // Traverse the left subtree
            writeBSTtoCSVRecursive(node.left, writer);
            // Write the current node's data to the CSV
            writer.write(node.id + "," + node.date + "," + node.stockLabel + "," + node.brand + "," + node.engineNumber + "," + node.status);
            writer.newLine(); // Move to the next line
            // Traverse the right subtree
            writeBSTtoCSVRecursive(node.right, writer);
        }   
    }
    
    public boolean isEngineNumberFound(String engineNumber) {
        return isEngineNumberFoundRecursive(root, engineNumber);
    }

    private boolean isEngineNumberFoundRecursive(Node node, String engineNumber) {
        // Base case: if the node is null, the engine number is not found
        if (node == null) {
            return false;
        }

        // Check if the current node's engine number matches
        if (node.engineNumber.equals(engineNumber)) {
            return true;
        }

        // Recursively search in the left and right subtrees
        return isEngineNumberFoundRecursive(node.left, engineNumber) || 
               isEngineNumberFoundRecursive(node.right, engineNumber);
    }
    
    public Node searchEngineNumber(String engineNumber) {
        return searchEngineNumberRecursive(root, engineNumber); // Assuming root is the starting point
    }

    private Node searchEngineNumberRecursive(Node node, String engineNumber) {
        if (node == null) {
            return null; // Base case: not found
        }

        // Check if the current node's engine number matches
        if (node.engineNumber.equalsIgnoreCase(engineNumber)) {
            return node; // Found the node
        }

        // Search both left and right subtrees (since tree is not ordered by engineNumber)
        Node leftSearch = searchEngineNumberRecursive(node.left, engineNumber);
        if (leftSearch != null) {
            return leftSearch; // If found in the left subtree, return it
        }

        // If not found in the left subtree, search in the right subtree
        return searchEngineNumberRecursive(node.right, engineNumber);
    }
    
    


    public void displayBSTSortedByBrand() {
        // Step 1: Collect all nodes in a list
        List<Node> nodeList = new ArrayList<>();
        collectNodes(root, nodeList);

        // Step 2: Sort the list by brand
         mergeSort(nodeList, 0, nodeList.size() - 1);
        //Collections.sort(nodeList, Comparator.comparing(node -> node.brand));

        // Step 3: Print the sorted nodes
        for (Node node : nodeList) {
            System.out.println(node.id + ", " + node.date + ", " + node.stockLabel + ", " +
                               node.brand + ", " + node.engineNumber + ", " + node.status);
        }
        
        
    }

    private void mergeSort(List<Node> nodeList, int left, int right){
        if (left<right){
            int middle = (left+right)/2;
            mergeSort(nodeList, left, middle); //sort left
            mergeSort(nodeList, middle+1, right);//sort right
             merge(nodeList, left, middle, right);  // Merge the sorted halves
        }
    }
    
    
    // Helper method to collect all nodes in the tree
    private void collectNodes(Node node, List<Node> nodeList) {
        if (node != null) {
            collectNodes(node.left, nodeList);  // Collect nodes from the left subtree
            nodeList.add(node);                 // Add the current node to the list
            collectNodes(node.right, nodeList); // Collect nodes from the right subtree
        }
    }
    
       private void merge(List<Node> nodeList, int left, int middle, int right) {
        // Create temporary lists to hold the left and right halves
        List<Node> leftList = new ArrayList<>(nodeList.subList(left, middle + 1));
        List<Node> rightList = new ArrayList<>(nodeList.subList(middle + 1, right + 1));

        int i = 0, j = 0, k = left;

        // Merge the two halves back into the original list
        while (i < leftList.size() && j < rightList.size()) {
            if (leftList.get(i).brand.compareTo(rightList.get(j).brand) <= 0) {
                nodeList.set(k, leftList.get(i));  // Take from the left list
                i++;
            } else {
                nodeList.set(k, rightList.get(j));  // Take from the right list
                j++;
            }
            k++;
        }

        // Copy any remaining elements from the left list
        while (i < leftList.size()) {
            nodeList.set(k, leftList.get(i));
            i++;
            k++;
        }

        // Copy any remaining elements from the right list
        while (j < rightList.size()) {
            nodeList.set(k, rightList.get(j));
            j++;
            k++;
        }
    }

    
    //Remove
    
    public void remove(int id){
             if(search(id)){
                 removeHelper(root, id);
             }else{
                 System.out.println(id+ " can't be found");
             }

         }

         private Node removeHelper(Node root, int id) {
        if (root == null) {
            return root; // Base case: Node not found
        } else if (id < root.id) {
            root.left = removeHelper(root.left, id); // Search in the left subtree
        } else if (id > root.id) {
            root.right = removeHelper(root.right, id); // Search in the right subtree
        } else { // Node to be deleted found
            if (root.left == null && root.right == null) {
                return null; // Remove leaf node
            } else if (root.right != null) {
                // Replace with successor
                Node successorNode = getSuccessor(root);
                root.id = successorNode.id;
                root.date = successorNode.date;
                root.stockLabel = successorNode.stockLabel;
                root.brand = successorNode.brand;
                root.engineNumber = successorNode.engineNumber;
                root.status = successorNode.status;
                root.right = removeHelper(root.right, successorNode.id);
            } else { // Replace with predecessor
                Node predecessorNode = getPredecessor(root);
                root.id = predecessorNode.id;
                root.date = predecessorNode.date;
                root.stockLabel = predecessorNode.stockLabel;
                root.brand = predecessorNode.brand;
                root.engineNumber = predecessorNode.engineNumber;
                root.status = predecessorNode.status;
                root.left = removeHelper(root.left, predecessorNode.id);
            }
        }
        return root;
    }

    private Node getSuccessor(Node root) {
        root = root.right;
        while (root.left != null) {
            root = root.left;
        }
        return root;
    }

    private Node getPredecessor(Node root) {
        root = root.left;
        while (root.right != null) {
            root = root.right;
        }
        return root;
    }
    
    
    public boolean search(int id){
        return searchHelper(root,id);
        
    }
    
        private boolean searchHelper(Node root, int id){
            if(root == null){
                return false;
            }else if (root.id == id){
                return true;
            }else if (root.id> id){
                return searchHelper(root.left, id);
            }else{
                return searchHelper(root.right, id);
            }
        }


}
