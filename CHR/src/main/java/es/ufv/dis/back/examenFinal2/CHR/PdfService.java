package es.ufv.dis.back.examenFinal2.CHR;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class PdfService {

    public ByteArrayOutputStream generatePdf(List<Usuario> usuarios) {
        try {
            Document doc = new Document(PageSize.A4, 50, 50, 100, 72);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfWriter.getInstance(doc, baos);
            doc.open();
            for (Usuario u : usuarios) {
                Paragraph p = new Paragraph(
                        "ID: " + u.getId() +
                                ", Nombre: " + u.getNombre() +
                                ", Apellidos: " + u.getApellidos() +
                                ", NIF: " + u.getNif() +
                                ", Email: " + u.getEmail()
                );
                doc.add(p);
            }
            doc.close();
            return baos;
        } catch (Exception e) {
            throw new RuntimeException("Error generando PDF", e);
        }
    }
}
