package entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;
import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name="users_table")
@Data
@SQLDelete(sql = "UPDATE User SET enabled = false where id = ?")
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column (name = "name")
    private String name;

    @Column (name = "last_name")
    private String lastName;

    @Column (unique = true, name = "email")
    private String email;

    @Column (name = "user_name", unique = true)
    private String userName;

    @Column (name = "created_at")
    private Date createdAt;

//    @Column (name = "user_status")
    private boolean enabled;


    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "user_role" , joinColumns={@JoinColumn(name="user_id")},
            inverseJoinColumns={@JoinColumn(name="role_id")})
    private Set<Role> roles;


    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private Set<Task> tasks;

    @ManyToOne
    private Discipline discipline;

    public User(String name, String lastName, String email, String userName, Date createdAt, boolean enabled, Set<Role> roles) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.userName = userName;
        this.createdAt = createdAt;
        this.enabled = enabled;
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }
}
