package es.daw.productoapirest.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
@Getter
@AllArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    // Relación bidireccional. Es el lado inverso (mapped)
    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

    public Role() {
        users = new HashSet<User>();
    }

    public void addUser(User user) {
        users.add(user);
        //user.addRole(this); // llamadas infinitas!!! cuidadín
        //user.getRoles().add(this); // no hay llamadas cíclicas!!! // porque estoy actualizando directamente la colección
    }
    public void removeUser(User user) {
        users.remove(user);
    }


    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

}


