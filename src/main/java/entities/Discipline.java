package entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="discipline")
@Data
@NoArgsConstructor

public class Discipline {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "discipline_name")
    private String name;

    @OneToMany (cascade = CascadeType.ALL)
    @JoinColumn(name = "discipline_id")
    private Set<User> members;

    @OneToOne(cascade = CascadeType.ALL)
    private User headOfDiscipline;

    public Discipline(String name,Set<User> members, User headOfDiscipline) {
        this.name = name;
        this.headOfDiscipline = headOfDiscipline;
        this.members = members;
    }
}
