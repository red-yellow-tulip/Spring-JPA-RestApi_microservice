package main.java.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@Entity
@Table(name = "student_group" , schema = "university_schemas")
public class Group implements Serializable {

    @Getter @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Getter @Setter
    @Column (name = "group_id")
    private Long groupId;


    @Getter @Setter
    @Column (name = "name")
    private String groupName;

    @Getter @Setter
    @ManyToOne  ( cascade = {CascadeType.MERGE, CascadeType.PERSIST},fetch=FetchType.LAZY)
    @JoinColumn (name = "university_id")
    @JsonBackReference(value = "group_to_university_json")
    private University university;


    @Getter @Setter
    @OneToMany (fetch = FetchType.LAZY, mappedBy = "group", cascade = {CascadeType.ALL}, orphanRemoval = true)
    //@JsonManagedReference(value = "student_to_group_json")
    @JsonIgnore
    private Set<Student> listStudents = new HashSet<>();


    public Group() {
    }


    public Group(Long groupId, String groupName) {
        this.groupId = groupId;
        this.groupName = groupName;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Group group = (Group) o;
        return Objects.equals(groupId, group.groupId) &&
                Objects.equals(groupName, group.groupName) &&
                Objects.equals(university, group.university);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupId, groupName, university);
    }

    @Override
    public String toString() {
        return "Group{" +
                "id=" + id +
                ", groupId=" + groupId +
                ", groupName='" + groupName + '\'' +
                ", universityId=" + university +
                ", listStudents=" + listStudents +
                '}';
    }
}
