/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author sendi
 */
public class Contact {
    private int id;
    private String name;
    private String phone;
    private String category;

    // Konstruktor
    public Contact(int id, String name, String phone, String category) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.category = category;
    }

    // Getter dan Setter
    public int getId() { return id; }
    public String getName() { return name; }
    public String getPhone() { return phone; }
    public String getCategory() { return category; }
}
