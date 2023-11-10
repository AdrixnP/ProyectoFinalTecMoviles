package com.example.proyectomoviles;

import com.google.firebase.firestore.PropertyName;

import java.text.DecimalFormat;

public class Cart {
    private String NombreProducto;
    private String ImgProducto;
    private double PrecioProducto;
    private String MarcaProducto;
    private Integer YearProducto;
    private int CantidadProducto;
    private Long IDProducto;
    private static double PrecioTotalCarrito;
    private String subdocumentId;
    private String maindocumentId;
    private static String savesubid;
    private static String savemainid;

    public Cart() {
    }

    public Cart(String NombreProducto, String ImgProducto, double PrecioProducto, int CantidadProducto, Long IDProducto, double PrecioTotalCarrito, String subdocumentId, String MarcaProducto, Integer YearProducto, String maindocumentId) {
        this.NombreProducto = NombreProducto;
        this.ImgProducto = ImgProducto;
        this.PrecioProducto = PrecioProducto;
        this.CantidadProducto = CantidadProducto;
        this.IDProducto = IDProducto;
        this.PrecioTotalCarrito = PrecioTotalCarrito;
        this.subdocumentId = subdocumentId;
        this.MarcaProducto = MarcaProducto;
        this.YearProducto = YearProducto;
        this.maindocumentId = maindocumentId;
    }

    public static void setSavesubid(String subdocumentId) {
    }

    public static void setSavemainid(String billId) {
    }

    @PropertyName("NombreProducto")
    public String getProductnamecart() {
        return NombreProducto;
    }

    @PropertyName("NombreProducto")
    public void setProductnamecart(String NombreProducto) {
        this.NombreProducto = NombreProducto;
    }

    @PropertyName("ImgProducto")
    public String getProductimgcart() {
        return ImgProducto;
    }

    @PropertyName("ImgProducto")
    public void setProductimgcart(String ImgProducto) {
        this.ImgProducto = ImgProducto;
    }

    @PropertyName("PrecioProducto")
    public double getProductpricecart() {
        return PrecioProducto;
    }

    @PropertyName("PrecioProducto")
    public void setProductpricecart(double PrecioProducto) {
        this.PrecioProducto = PrecioProducto;
    }

    @PropertyName("CantidadProducto")
    public int getQuantitycart() {
        return CantidadProducto;
    }

    @PropertyName("CantidadProducto")
    public void setQuantitycart(int CantidadProducto) {
        this.CantidadProducto = CantidadProducto;
    }

    @PropertyName("IDProducto")
    public Long getProductidcart() {
        return IDProducto;
    }

    @PropertyName("IDProducto")
    public void setProductidcart(Long IDProducto) {
        this.IDProducto = IDProducto;
    }

    @PropertyName("PrecioTotalCarrito")
    public static double getTotalamountproductoncart() {
        return PrecioTotalCarrito;
    }

    @PropertyName("PrecioTotalCarrito")
    public void setTotalamountproductoncart(double PrecioTotalCarrito) {
        this.PrecioTotalCarrito = PrecioTotalCarrito;
    }

    public String getSubdocumentId() {
        return subdocumentId;
    }

    public static void setSubdocumentId(String subdocumentId) {
        subdocumentId = subdocumentId;
    }

    @PropertyName("MarcaProducto")
    public String getProductbrandcart() {
        return MarcaProducto;
    }

    @PropertyName("MarcaProducto")
    public void setProductbrandcart(String MarcaProducto) {
        this.MarcaProducto = MarcaProducto;
    }

    @PropertyName("YearProducto")
    public Integer getProductYearcart() {
        return YearProducto;
    }

    @PropertyName("YearProducto")
    public void setProductYearcart(Integer YearProducto) {
        this.YearProducto = YearProducto;
    }

    public String getMaindocumentId() {
        return maindocumentId;
    }

    public static void setMaindocumentId(String maindocumentId) {
        maindocumentId = maindocumentId;
    }

    public String getYearCartProductToStr() {
        DecimalFormat decimalFormat = new DecimalFormat("####");
        return YearProducto != null ? decimalFormat.format(YearProducto) : "N/A";
    }
    public String getCantidadProductoCarritoToStr() {
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        return CantidadProducto != 0 ? decimalFormat.format(CantidadProducto) : "0";
    }
    public String getFixPriceCart() {
        DecimalFormat decimalFormat = new DecimalFormat("#,###.##");
        return decimalFormat.format(PrecioProducto);
    }

}
