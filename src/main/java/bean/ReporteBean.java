package com.skatelife.bean;

import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.line.LineChartDataSet;
import org.primefaces.model.charts.line.LineChartModel;
import org.primefaces.model.charts.line.LineChartOptions;
import org.primefaces.model.charts.pie.PieChartDataSet;
import org.primefaces.model.charts.pie.PieChartModel;
import org.primefaces.model.charts.bar.BarChartDataSet;
import org.primefaces.model.charts.bar.BarChartModel;
import org.primefaces.model.charts.bar.BarChartOptions;
import org.primefaces.model.charts.optionconfig.title.Title;
import org.primefaces.model.charts.axes.cartesian.CartesianScales;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearAxes;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearTicks;

import java.io.Serializable;
import java.util.*;

@Named
@ViewScoped
public class ReporteBean implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    // Modelos de gráficos
    private LineChartModel lineModel;
    private PieChartModel pieModel;
    private BarChartModel barModel;
    
    // Datos de estadísticas
    private Double ventasTotales;
    private Integer productosVendidos;
    private Integer nuevosClientes;
    private Double ticketPromedio;
    
    // Filtros
    private Date fechaInicio;
    private Date fechaFin;
    private String categoriaSeleccionada;
    
    // Lista de productos para la tabla
    private List<ReporteProducto> reporteProductos;
    
    @PostConstruct
    public void init() {
        // Inicializar estadísticas
        ventasTotales = 525000.0;
        productosVendidos = 1248;
        nuevosClientes = 89;
        ticketPromedio = 15.3;
        
        // Crear gráficos
        createLineModel();
        createPieModel();
        createBarModel();
        
        // Cargar datos de productos
        cargarReporteProductos();
    }
    
    private void createLineModel() {
        lineModel = new LineChartModel();
        ChartData data = new ChartData();
        
        LineChartDataSet dataSet2023 = new LineChartDataSet();
        List<Object> values2023 = new ArrayList<>();
        values2023.add(35000);
        values2023.add(42000);
        values2023.add(38000);
        values2023.add(51000);
        values2023.add(48000);
        values2023.add(55000);
        values2023.add(62000);
        values2023.add(58000);
        values2023.add(67000);
        values2023.add(71000);
        values2023.add(69000);
        values2023.add(85000);
        
        dataSet2023.setData(values2023);
        dataSet2023.setFill(false);
        dataSet2023.setLabel("2023");
        dataSet2023.setBorderColor("rgb(75, 192, 192)");
        dataSet2023.setTension(0.1);
        
        LineChartDataSet dataSet2024 = new LineChartDataSet();
        List<Object> values2024 = new ArrayList<>();
        values2024.add(45000);
        values2024.add(52000);
        values2024.add(48000);
        values2024.add(61000);
        values2024.add(58000);
        values2024.add(65000);
        values2024.add(72000);
        values2024.add(68000);
        values2024.add(77000);
        values2024.add(81000);
        values2024.add(79000);
        values2024.add(95000);
        
        dataSet2024.setData(values2024);
        dataSet2024.setFill(false);
        dataSet2024.setLabel("2024");
        dataSet2024.setBorderColor("rgb(255, 99, 132)");
        dataSet2024.setTension(0.1);
        
        data.addChartDataSet(dataSet2023);
        data.addChartDataSet(dataSet2024);
        
        List<String> labels = Arrays.asList("Ene", "Feb", "Mar", "Abr", "May", "Jun", 
                                           "Jul", "Ago", "Sep", "Oct", "Nov", "Dic");
        data.setLabels(labels);
        
        lineModel.setData(data);
        
        LineChartOptions options = new LineChartOptions();
        Title title = new Title();
        title.setDisplay(true);
        title.setText("Comparación de Ventas Anuales");
        options.setTitle(title);
        
        lineModel.setOptions(options);
    }
    
    private void createPieModel() {
        pieModel = new PieChartModel();
        ChartData data = new ChartData();
        
        PieChartDataSet dataSet = new PieChartDataSet();
        List<Number> values = new ArrayList<>();
        values.add(185000);
        values.add(95000);
        values.add(78000);
        values.add(112000);
        values.add(55000);
        
        dataSet.setData(values);
        
        List<String> bgColors = new ArrayList<>();
        bgColors.add("rgb(255, 99, 132)");
        bgColors.add("rgb(54, 162, 235)");
        bgColors.add("rgb(255, 205, 86)");
        bgColors.add("rgb(75, 192, 192)");
        bgColors.add("rgb(153, 102, 255)");
        dataSet.setBackgroundColor(bgColors);
        
        data.addChartDataSet(dataSet);
        
        List<String> labels = Arrays.asList("Tablas", "Ruedas", "Trucks", "Accesorios", "Ropa");
        data.setLabels(labels);
        
        pieModel.setData(data);
    }
    
    private void createBarModel() {
        barModel = new BarChartModel();
        ChartData data = new ChartData();
        
        BarChartDataSet barDataSet = new BarChartDataSet();
        barDataSet.setLabel("Unidades Vendidas");
        
        List<Number> values = new ArrayList<>();
        values.add(145);
        values.add(132);
        values.add(128);
        values.add(156);
        values.add(118);
        values.add(142);
        values.add(167);
        values.add(189);
        values.add(134);
        values.add(98);
        
        barDataSet.setData(values);
        
        List<String> bgColor = new ArrayList<>();
        bgColor.add("rgba(44, 95, 124, 0.7)");
        barDataSet.setBackgroundColor(bgColor);
        
        List<String> borderColor = new ArrayList<>();
        borderColor.add("rgb(44, 95, 124)");
        barDataSet.setBorderColor(borderColor);
        barDataSet.setBorderWidth(1);
        
        data.addChartDataSet(barDataSet);
        
        List<String> labels = Arrays.asList(
            "Santa Cruz Classic",
            "Element Complete",
            "Baker Board Pro",
            "Spitfire Wheels",
            "Independent Trucks",
            "Vans Old Skool",
            "Thrasher T-Shirt",
            "Grip Tape Mob",
            "Bones Bearings",
            "Nike SB Dunk"
        );
        data.setLabels(labels);
        barModel.setData(data);
        
        BarChartOptions options = new BarChartOptions();
        CartesianScales cScales = new CartesianScales();
        CartesianLinearAxes linearAxes = new CartesianLinearAxes();
        CartesianLinearTicks ticks = new CartesianLinearTicks();
        linearAxes.setTicks(ticks);
        cScales.addYAxesData(linearAxes);
        options.setScales(cScales);
        
        Title title = new Title();
        title.setDisplay(true);
        title.setText("Top 10 Productos Más Vendidos");
        options.setTitle(title);
        
        barModel.setOptions(options);
    }
    
    private void cargarReporteProductos() {
        reporteProductos = new ArrayList<>();
        
        reporteProductos.add(new ReporteProducto(
            "Santa Cruz Classic Dot", "Tablas", 145, 85000.0, 12325000.0, 15, 12.5
        ));
        reporteProductos.add(new ReporteProducto(
            "Element Skate Complete", "Tablas", 132, 95000.0, 12540000.0, 8, 8.3
        ));
        reporteProductos.add(new ReporteProducto(
            "Baker Board Pro Model", "Tablas", 128, 92000.0, 11776000.0, 22, 15.2
        ));
        reporteProductos.add(new ReporteProducto(
            "Spitfire Wheels 52mm", "Ruedas", 156, 35000.0, 5460000.0, 45, 18.9
        ));
        reporteProductos.add(new ReporteProducto(
            "Independent Trucks Stage 11", "Trucks", 118, 65000.0, 7670000.0, 18, 5.8
        ));
        reporteProductos.add(new ReporteProducto(
            "Vans Old Skool Black", "Ropa", 142, 89000.0, 12638000.0, 32, 22.1
        ));
        reporteProductos.add(new ReporteProducto(
            "Thrasher Magazine T-Shirt", "Ropa", 167, 35000.0, 5845000.0, 67, 31.5
        ));
        reporteProductos.add(new ReporteProducto(
            "Grip Tape Mob Perforated", "Accesorios", 189, 12000.0, 2268000.0, 125, 45.2
        ));
        reporteProductos.add(new ReporteProducto(
            "Bones Bearings Reds", "Accesorios", 134, 28000.0, 3752000.0, 89, 12.8
        ));
        reporteProductos.add(new ReporteProducto(
            "Nike SB Dunk Low", "Ropa", 98, 125000.0, 12250000.0, 5, -5.2
        ));
        reporteProductos.add(new ReporteProducto(
            "Powell Peralta Ripper", "Tablas", 87, 88000.0, 7656000.0, 12, 3.4
        ));
        reporteProductos.add(new ReporteProducto(
            "Thunder Trucks Polished", "Trucks", 102, 62000.0, 6324000.0, 28, 8.9
        ));
    }
    
    public void generarReporte() {
        createLineModel();
        createPieModel();
        createBarModel();
        cargarReporteProductos();
    }
    
    // Getters y Setters
    public LineChartModel getLineModel() {
        return lineModel;
    }
    
    public PieChartModel getPieModel() {
        return pieModel;
    }
    
    public BarChartModel getBarModel() {
        return barModel;
    }
    
    public Double getVentasTotales() {
        return ventasTotales;
    }
    
    public Integer getProductosVendidos() {
        return productosVendidos;
    }
    
    public Integer getNuevosClientes() {
        return nuevosClientes;
    }
    
    public Double getTicketPromedio() {
        return ticketPromedio;
    }
    
    public Date getFechaInicio() {
        return fechaInicio;
    }
    
    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }
    
    public Date getFechaFin() {
        return fechaFin;
    }
    
    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }
    
    public String getCategoriaSeleccionada() {
        return categoriaSeleccionada;
    }
    
    public void setCategoriaSeleccionada(String categoriaSeleccionada) {
        this.categoriaSeleccionada = categoriaSeleccionada;
    }
    
    public List<ReporteProducto> getReporteProductos() {
        return reporteProductos;
    }
    
    public static class ReporteProducto implements Serializable {
        private static final long serialVersionUID = 1L;
        
        private String nombre;
        private String categoria;
        private Integer unidadesVendidas;
        private Double precioUnitario;
        private Double totalVentas;
        private Integer stockActual;
        private Double crecimiento;
        
        public ReporteProducto() {}
        
        public ReporteProducto(String nombre, String categoria, Integer unidadesVendidas,
                             Double precioUnitario, Double totalVentas, 
                             Integer stockActual, Double crecimiento) {
            this.nombre = nombre;
            this.categoria = categoria;
            this.unidadesVendidas = unidadesVendidas;
            this.precioUnitario = precioUnitario;
            this.totalVentas = totalVentas;
            this.stockActual = stockActual;
            this.crecimiento = crecimiento;
        }
        
        public String getNombre() { return nombre; }
        public void setNombre(String nombre) { this.nombre = nombre; }
        
        public String getCategoria() { return categoria; }
        public void setCategoria(String categoria) { this.categoria = categoria; }
        
        public Integer getUnidadesVendidas() { return unidadesVendidas; }
        public void setUnidadesVendidas(Integer unidadesVendidas) { 
            this.unidadesVendidas = unidadesVendidas; 
        }
        
        public Double getPrecioUnitario() { return precioUnitario; }
        public void setPrecioUnitario(Double precioUnitario) { 
            this.precioUnitario = precioUnitario; 
        }
        
        public Double getTotalVentas() { return totalVentas; }
        public void setTotalVentas(Double totalVentas) { 
            this.totalVentas = totalVentas; 
        }
        
        public Integer getStockActual() { return stockActual; }
        public void setStockActual(Integer stockActual) { 
            this.stockActual = stockActual; 
        }
        
        public Double getCrecimiento() { return crecimiento; }
        public void setCrecimiento(Double crecimiento) { 
            this.crecimiento = crecimiento; 
        }
    }
}