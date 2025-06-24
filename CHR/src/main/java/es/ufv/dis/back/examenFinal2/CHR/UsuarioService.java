package es.ufv.dis.back.examenFinal2.CHR;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UsuarioService {
    private final Gson gson = new Gson();
    private List<Usuario> usuarios = new ArrayList<>();

    @Value("${app.usuarios-file}")
    private String usuariosFile;

    @PostConstruct
    public void init() throws Exception {
        try (FileReader reader = new FileReader(usuariosFile)) {
            Type listType = new TypeToken<List<Usuario>>(){}.getType();
            usuarios = gson.fromJson(reader, listType);
        }
    }

    public List<Usuario> findAll() { return usuarios; }

    public Optional<Usuario> findById(String id) {
        return usuarios.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst();
    }

    public Usuario save(Usuario u) throws Exception {
        u.setId(UUID.randomUUID().toString());
        usuarios.add(u);
        persist();
        return u;
    }

    public Usuario update(String id, Usuario u) throws Exception {
        Usuario old = findById(id).orElseThrow();
        usuarios.remove(old);
        u.setId(id);
        usuarios.add(u);
        persist();
        return u;
    }

    private void persist() throws Exception {
        try (FileWriter writer = new FileWriter(usuariosFile)) {
            gson.toJson(usuarios, writer);
        }
    }
}
