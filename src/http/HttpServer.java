package http;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 *
 * @author benjamin
 */
public class HttpServer {

    private int puerto = 8090;
    private String rutaArchivos;
    private final String htmlUploadArchivo 
            = "<!DOCTYPE html>\n"
                    + "<html>\n"
                    + "    <head>\n"
                    + "        <title>TODO supply a title</title>\n"
                    + "        <meta charset=\"UTF-8\">\n"
                    + "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                    + "    </head>\n"
                    + "    <body>\n"
                    + "        <div>spark server</div>\n"
                    + "        <form method='post' action=\"/api/upload\" enctype='multipart/form-data'>\n"
                    + "            <input type='file' name='uploaded_file'>\n"
                    + "            <button>Upload picture</button>"
                    + "        </form>\n"
                    + "    </body>\n"
                    + "</html>"; 

    public void start() {
        Spark.port(puerto);
        Spark.get("/home", (req, res) -> {
            return htmlUploadArchivo;
        });
        Spark.path("/api", () -> {
            Spark.post("/upload", "multipart/form-data", (request, response) -> this.uploadFile(request, response), new Gson()::toJson);
            Spark.get("/download/:filename", (request, response) -> this.downloadFile(request, response));
        });
    }

    public AdjuntoResponse uploadFile(Request request, Response response)
            throws IOException, ServletException {
        response.type("application/json");
        request.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));
        Part part = request.raw().getPart("uploaded_file");
        String fileName = part.getSubmittedFileName();
        File file = new File(rutaArchivos + File.separator + fileName);
        try (InputStream is = part.getInputStream()) {
            Files.copy(is, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }
        return new AdjuntoResponse(file.getName(), file.length());
    }

    public HttpServletResponse downloadFile(Request request, Response response) throws IOException {
        String fileName = request.params("filename");
        File file = new File(rutaArchivos + File.separator + fileName);
        byte[] bytes = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
        response.raw().setContentType("application/octet-stream");
        response.raw().setHeader("Content-Disposition", "attachment; filename=" + file.getName());
        response.raw().getOutputStream().write(bytes);
        response.raw().getOutputStream().flush();
        response.raw().getOutputStream().close();
        return response.raw();
    }

    public void stop() {
        Spark.stop();
    }

    public int getPuerto() {
        return puerto;
    }

    public void setPuerto(int puerto) {
        this.puerto = puerto;
    }

    public String getRutaArchivos() {
        return rutaArchivos;
    }

    public void setRutaArchivos(String rutaArchivos) {
        this.rutaArchivos = rutaArchivos;
    }

}
