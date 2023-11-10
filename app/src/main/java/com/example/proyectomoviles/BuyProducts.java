package com.example.proyectomoviles;

import com.google.firebase.firestore.PropertyName;
import java.text.DecimalFormat;

public class BuyProducts {
    // Firestore fields
    private String Nombre;
    private String Description;
    private Double Precio;
    private String ImgLink;
    private Integer CompradaCantidad;
    private Integer Year;
    private String Marca;
    private Double PrecioTotal;
    private static String DocumentID;

    // No-argument constructor required for Firebase
    public BuyProducts() {
    }

    // Getters and Setters with PropertyName annotations to match Firestore document fields


    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public Double getPrecio() {
        return Precio;
    }

    public void setPrecio(Double precio) {
        Precio = precio;
    }

    public String getImgLink() {
        return ImgLink;
    }

    public void setImgLink(String imgLink) {
        ImgLink = imgLink;
    }

    public Integer getCompradaCantidad() {
        return CompradaCantidad;
    }

    public void setCompradaCantidad(Integer compradaCantidad) {
        CompradaCantidad = compradaCantidad;
    }

    public Integer getYear() {
        return Year;
    }

    public void setYear(Integer year) {
        Year = year;
    }

    public String getMarca() {
        return Marca;
    }

    public void setMarca(String marca) {
        Marca = marca;
    }

    public Double getPrecioTotal() {
        return PrecioTotal;
    }

    public void setPrecioTotal(Double precioTotal) {
        PrecioTotal = precioTotal;
    }

    public static String getDocumentID() {
        return DocumentID;
    }

    public static void setDocumentID(String documentID) {
        DocumentID = documentID;
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

    public String getCompraCantidadToStr() {
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        return CompradaCantidad != null ? decimalFormat.format(CompradaCantidad) : "0";
    }

    public String getTotalPriceToStr() {
        DecimalFormat decimalFormat = new DecimalFormat("#,###.##");
        return PrecioTotal != null ? decimalFormat.format(PrecioTotal) : "0";
    }
}
