package org.vaadin.example;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Route("")   // Será accesible en http://localhost:8084/
public class MainView extends VerticalLayout {

    private final String apiUrl = getElement()
            .getAttribute("api.url", "http://localhost:8083/api/usuarios");
    private final Gson gson = new Gson();
    private final Grid<Usuario> grid = new Grid<>(Usuario.class, false);

    public MainView() {
        setSizeFull();
        configureGrid();
        add(grid, createToolbar());
        loadUsuarios();
    }

    private void configureGrid() {
        grid.addColumn(Usuario::getNombre).setHeader("Nombre").setAutoWidth(true);
        grid.addColumn(Usuario::getApellidos).setHeader("Apellidos").setAutoWidth(true);
        grid.addColumn(Usuario::getEmail).setHeader("Email").setAutoWidth(true);
        grid.setSizeFull();
    }

    private VerticalLayout createToolbar() {
        Button recargar = new Button("Recargar datos", e -> loadUsuarios());
        return new VerticalLayout(recargar);
    }

    private void loadUsuarios() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl))
                    .GET()
                    .build();

            HttpResponse<InputStreamReader> response = HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofInputStream())
                    .map(is -> new InputStreamReader(is));

            Type listType = new TypeToken<List<Usuario>>() {}.getType();
            List<Usuario> usuarios = gson.fromJson(response.body(), listType);
            grid.setItems(usuarios);

        } catch (Exception ex) {
            Notification.show("Error cargando usuarios: " + ex.getMessage(), 3000, Notification.Position.MIDDLE);
            ex.printStackTrace();
        }
    }
}
