package main.java.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "student"  , schema = "university_schemas")
public class Student implements Serializable {

    @Getter @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Getter @Setter
    @Column (name = "student_id", insertable = false, updatable = false)
    private Long groupId;

    @Getter @Setter
    @Column (name = "name")
    private String name;

    @Getter @Setter
    @Column (name = "surname")
    private String surname;

    @Getter @Setter
    @Temporal(TemporalType.DATE)
    @Column (name = "dateBirth")
    private Date dateBirth;

    @Getter @Setter
    @ManyToOne  ( cascade = {CascadeType.MERGE, CascadeType.PERSIST},fetch=FetchType.LAZY)
    //@JsonBackReference(value = "student_to_group_json")
    @JsonIgnore
    @JoinColumn (name = "student_id")
    private Group group;


    public Student() {

    }

    public Student(String name, String surname, Date dateBirth) {
        this.name = name;
        this.surname = surname;
        this.dateBirth = dateBirth;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Student student = (Student) o;
        return Objects.equals(groupId, student.groupId) &&
                Objects.equals(name, student.name) &&
                Objects.equals(surname, student.surname) &&
                Objects.equals(dateBirth, student.dateBirth);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupId, name, surname, dateBirth);
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", groupId=" + groupId +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", dateBirth=" + dateBirth +
                '}';
    }




}
