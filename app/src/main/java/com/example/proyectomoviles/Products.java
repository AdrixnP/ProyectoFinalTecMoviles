package com.example.proyectomoviles;

import com.google.firebase.firestore.PropertyName;
import java.text.DecimalFormat;

public class Products {
    // Firestore fields
    private String Nombre;
    private String Description;
    private Double Precio;
    private String ImgLink;
    private Integer Cantidad;
    private Integer Year;
    private String Marca;
    private Long ID;
    private static String DocumentID;

    // No-argument constructor required for Firebase
    public Products() {
    }

    // Getters and Setters with PropertyName annotations to match Firestore document fields
    @PropertyName("Nombre")
    public String getNombre() {
        return Nombre;
    }

    @PropertyName("Nombre")
    public void setNombre(String nombre) {
        this.Nombre = nombre;
    }

    @PropertyName("Description")
    public String getDescription() {
        return Description;
    }

    @PropertyName("Description")
    public void setDescription(String description) {
        this.Description = description;
    }

    @PropertyName("Precio")
    public Double getPrecio() {
        return Precio;
    }

    @PropertyName("Precio")
    public void setPrecio(Double precio) {
        this.Precio = precio;
    }

    @PropertyName("ImgLink")
    public String getImgLink() {
        return ImgLink;
    }

    @PropertyName("ImgLink")
    public void setImgLink(String imgLink) {
        this.ImgLink = imgLink;
    }

    @PropertyName("Cantidad")
    public Integer getCantidad() {
        return Cantidad;
    }

    @PropertyName("Cantidad")
    public void setCantidad(Integer cantidad) {
        this.Cantidad = cantidad;
    }

    @PropertyName("Year")
    public Integer getYear() {
        return Year;
    }

    @PropertyName("Year")
    public void setYear(Integer year) {
        this.Year = year;
    }

    @PropertyName("Marca")
    public String getMarca() {
        return Marca;
    }

    @PropertyName("Marca")
    public void setMarca(String marca) {
        this.Marca = marca;
    }

    @PropertyName("ID")
    public Long getId() {
        return ID;
    }

    @PropertyName("ID")
    public void setId(Long id) {
        this.ID = id;
    }

    public String getDocumentID() {
        return DocumentID;
    }

    public static void setDocumentID(String documentID) {
        DocumentID = documentID;
    }

    // toString method to output the product information
    @Override
    public String toString() {
        return "Products{" +
                "Nombre='" + Nombre + '\'' +
                ", Description='" + Description + '\'' +
                ", Precio=" + Precio +
                ", ImgLink='" + ImgLink + '\'' +
                ", Cantidad=" + Cantidad +
                ", Year=" + Year +
                ", Marca='" + Marca + '\'' +
                ", ID=" + ID +
                '}';
    }

    // Formatting methods for price, year, and quantity
    public String getPriceToStr() {
        DecimalFormat decimalFormat = new DecimalFormat("#,###.##");
        return Precio != null ? decimalFormat.format(Precio) : "0";
    }

    public String getYearToStr() {
        DecimalFormat decimalFormat = new DecimalFormat("####");
        return Year != null ? decimalFormat.format(Year) : "N/A";
    }

    public String getCantidadToStr() {
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        return Cantidad != null ? decimalFormat.format(Cantidad) : "0";
    }
}
