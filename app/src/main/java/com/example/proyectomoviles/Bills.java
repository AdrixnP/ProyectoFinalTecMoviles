package com.example.proyectomoviles;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.PropertyName;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

public class Bills {
    private String Cliente;
    private int Telefono;
    private String Vendedor;
    private String Fecha;
    private int NumProductos;
    private double PrecioTotal;
    private static String DocumentID;

    public Bills() {
        this.DocumentID = null;
    }
    public Bills(String Cliente, int Telefono, String Vendedor, int NumProductos, double PrecioTotal, String Fecha, String DocumentID) {
        this.Cliente = Cliente;
        this.Telefono = Telefono;
        this.Vendedor = Vendedor;
        this.NumProductos = NumProductos;
        this.PrecioTotal = PrecioTotal;
        this.Fecha = Fecha;
        this.DocumentID = DocumentID;
    }

    @PropertyName("Cliente")
    public String getCustomerName() {
        return Cliente;
    }

    @PropertyName("Cliente")
    public void setCustomerName(String Cliente) {
        this.Cliente = Cliente;
    }

    @PropertyName("Telefono")
    public int getCustomerPhoneNumber() {
        return Telefono;
    }

    @PropertyName("Telefono")
    public void setCustomerPhoneNumber(int Telefono) {
        this.Telefono = Telefono;
    }

    @PropertyName("Vendedor")
    public String getUserName() {
        return Vendedor;
    }

    @PropertyName("Vendedor")
    public void setUserName(String Vendedor) {
        this.Vendedor = Vendedor;
    }

    @PropertyName("NumProductos")
    public int getProductCount() {
        return NumProductos;
    }

    @PropertyName("NumProductos")
    public void setProductCount(int NumProductos) {
        this.NumProductos = NumProductos;
    }

    @PropertyName("PrecioTotal")
    public double getTotalAmount() {
        return PrecioTotal;
    }

    @PropertyName("PrecioTotal")
    public void setTotalAmount(double PrecioTotal) {
        this.PrecioTotal = PrecioTotal;
    }

    @PropertyName("Fecha")
    public String getCreationDate() {
        return Fecha;
    }

    @PropertyName("Fecha")
    public void setCreationDate(String Fecha) {
        this.Fecha = Fecha;
    }

    public String getFixAmount() {
        DecimalFormat decimalFormat = new DecimalFormat("#,###.##");
        return decimalFormat.format(PrecioTotal);
    }

    public String getFixPhone() {
        DecimalFormat decimalFormat = new DecimalFormat("##########");
        return decimalFormat.format(Telefono);
    }

    public String getFixCount() {
        return String.valueOf(NumProductos);
    }

    public static String getDocumentID() {
        return DocumentID;
    }

    public static void setDocumentID(String documentID) {
        DocumentID = documentID;
    }
}