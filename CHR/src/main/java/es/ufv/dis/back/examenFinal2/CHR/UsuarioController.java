package es.ufv.dis.back.examenFinal2.CHR;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService service;
    private final PdfService pdfService;

    public UsuarioController(UsuarioService service,
                             PdfService pdfService) {
        this.service    = service;
        this.pdfService = pdfService;
    }

    @GetMapping
    public List<Usuario> listAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getById(@PathVariable String id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Usuario create(@RequestBody Usuario u) throws Exception {
        return service.save(u);
    }

    @PutMapping("/{id}")
    public Usuario update(@PathVariable String id,
                          @RequestBody Usuario u) throws Exception {
        return service.update(id, u);
    }

    // ─── Nuevo endpoint PDF ──────────────────────────────────────

    @GetMapping("/pdf")
    public ResponseEntity<byte[]> downloadPdf() {
        // 1. Generar el PDF en memoria
        ByteArrayOutputStream baos = pdfService.generatePdf(service.findAll());

        // 2. Cabeceras HTTP: tipo PDF y forzar descarga
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(
                ContentDisposition.builder("attachment")
                        .filename("usuarios.pdf")
                        .build()
        );

        // 3. Devolver array de bytes
        return new ResponseEntity<>(
                baos.toByteArray(),
                headers,
                HttpStatus.OK
        );
    }
}
