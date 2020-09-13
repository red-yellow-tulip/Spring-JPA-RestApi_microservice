package main.java.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;


import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "university"  , schema = "university_schemas")
public class University implements Serializable {

    @Getter @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Getter @Setter
    @Column (name = "university_id")
    private Long universityId;

    @Getter @Setter
    @Column (name = "name")
    private String universityName;

    @Getter @Setter
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "university", cascade = {CascadeType.ALL}, orphanRemoval = true)
    @JsonManagedReference(value = "group_to_university_json")
    private Set<Group> listGroup = new HashSet<>();


    public University() {

    }


    public University(Long i, String university) {
        this.universityId = i;
        this.universityName = university;
    }
}
