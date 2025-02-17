/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dsams2;

/**
 *
 * @author zach malonjao
 */
public class Node {
    int id;
    String date;
    String stockLabel;
    String brand;
    String engineNumber;
    String status;
    Node left;
    Node right;
    
    public Node(int id, String date, String stockLabel, String brand, String engineNumber, String status){
        this.id = id;
        this.date = date;
        this.stockLabel= stockLabel;
        this.brand=brand;
        this. engineNumber=engineNumber;
        this.status=status;
    }
    
    
    
}
