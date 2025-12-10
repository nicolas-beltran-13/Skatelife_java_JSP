package com.skatelife.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@WebServlet("/img/*")
public class ImageServlet extends HttpServlet {
    
    // IMPORTANTE: Cambia esta ruta según tu servidor
    // Opción 1: Ruta absoluta en Windows
    private static final String IMAGE_DIRECTORY = "C:/skatelife/imagenes/";
    
    // Opción 2: Ruta en Linux/Mac
    // private static final String IMAGE_DIRECTORY = "/var/skatelife/imagenes/";
    
    // Opción 3: Ruta relativa al home del usuario
    // private static final String IMAGE_DIRECTORY = 
    //     System.getProperty("user.home") + "/skatelife/imagenes/";
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Obtener el nombre del archivo de la URL
        String filename = request.getPathInfo();
        if (filename == null || filename.equals("/")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Nombre de archivo no especificado");
            return;
        }
        
        // Remover el "/" inicial
        filename = filename.substring(1);
        
        // Prevenir ataques de path traversal
        if (filename.contains("..") || filename.contains("/") || filename.contains("\\")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Nombre de archivo inválido");
            return;
        }
        
        File file = new File(IMAGE_DIRECTORY, filename);
        
        if (!file.exists() || !file.isFile()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Imagen no encontrada");
            return;
        }
        
        // Determinar el tipo MIME
        String contentType = getServletContext().getMimeType(file.getName());
        if (contentType == null) {
            contentType = "application/octet-stream";
        }
        
        // Configurar la respuesta
        response.setContentType(contentType);
        response.setContentLengthLong(file.length());
        response.setHeader("Content-Disposition", "inline; filename=\"" + file.getName() + "\"");
        
        // Cache por 1 hora
        response.setHeader("Cache-Control", "max-age=3600");
        
        // Enviar el archivo
        Files.copy(file.toPath(), response.getOutputStream());
    }
}