package org.vaadin.example;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Route("")
public class MainView extends VerticalLayout {

    private static final String API_URL = "http://localhost:8083/api/usuarios";

    private final Gson gson = new Gson();
    private final Grid<Usuario> grid = new Grid<>(Usuario.class, false);

    public MainView() {
        setSizeFull();
        configureGrid();

        // Barra de botones: Añadir + Generar PDF
        Button addBtn = new Button("Añadir Usuario", e -> openAddDialog());
        addBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        Button pdfBtn = new Button("Generar PDF", e -> downloadPdf());
        pdfBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        HorizontalLayout toolbar = new HorizontalLayout(addBtn, pdfBtn);
        toolbar.setPadding(true);
        add(grid, toolbar);

        loadUsuarios();
    }

    private void configureGrid() {
        grid.addColumn(Usuario::getNombre).setHeader("Nombre").setAutoWidth(true);
        grid.addColumn(Usuario::getApellidos).setHeader("Apellidos").setAutoWidth(true);
        grid.addColumn(Usuario::getNif).setHeader("NIF").setAutoWidth(true);
        grid.addColumn(Usuario::getEmail).setHeader("Email").setAutoWidth(true);
        grid.setSizeFull();
    }

    private void loadUsuarios() {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL))
                    .GET()
                    .build();

            HttpResponse<InputStream> response = client.send(
                    request, HttpResponse.BodyHandlers.ofInputStream()
            );

            InputStreamReader reader = new InputStreamReader(response.body());
            Type listType = new TypeToken<List<Usuario>>() {}.getType();
            List<Usuario> usuarios = gson.fromJson(reader, listType);
            grid.setItems(usuarios);

        } catch (Exception ex) {
            Notification.show("Error cargando usuarios: " + ex.getMessage(), 3000, Notification.Position.MIDDLE);
            ex.printStackTrace();
        }
    }

    private void downloadPdf() {
        // Abre la URL del PDF en una nueva pestaña. El navegador lo mostrará o descargará.
        UI.getCurrent().getPage().open(API_URL + "/pdf", "_blank");
    }

    private void openAddDialog() {
        // tu implementación de añadir usuario…
    }
}
