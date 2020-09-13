package main.java.service;

import main.java.entity.Group;
import main.java.entity.Student;
import main.java.entity.University;
import org.hibernate.Hibernate;
import org.springframework.orm.hibernate5.HibernateObjectRetrievalFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ServiceDatabase {

    @Resource  // таблица - student
    private StudentRepository studentRepository;

    @Resource  // таблица - group
    private GroupRepository groupRepository;

    @Resource  // таблица - university
    private UniversityRepository universityRepository;



    private long universityId = 100;

    @Transactional
    public University saveUniversity(University un) {
        return universityRepository.save(un);
    }


    @Transactional
    public void clearTable() {
        universityRepository.deleteAll();
        groupRepository.deleteAll();
        universityRepository.deleteAll();

    }
    @Transactional (readOnly = true)
    public University findUniversityById(long l) {
        return universityRepository.findByUniversityId(l).orElse(null);
    }

    @Transactional (readOnly = true)
    public List<Group> findListGroupByUniversityId(long id) {
        Optional<University> u = universityRepository.findByUniversityId(id);
        return new ArrayList<>(u.get().getListGroup());
    }

    @Transactional (readOnly = true)
    public ArrayList<Student> findStudentByGroupId(long groupId) {
        Optional<Group> g = groupRepository.findByGroupId(groupId);
        Group g0 = g.orElse(null);
        return g0 == null ? new ArrayList<>(): new ArrayList<>(g0.getListStudents());
    }
    @Transactional
    public Student addStudentByGroupId(Student s, long groupId) {
        Group g = groupRepository.findByGroupId(groupId).orElse(null);
        s.setGroupId(g.getGroupId());
        s.setGroup(g);

        g.getListStudents().add(s);
        groupRepository.save(g);

        Optional<Student> op = studentRepository.findByNameAndSurname(s.getName(),s.getSurname());
        return  op.orElse(null);

    }

    @Transactional (readOnly = true)
    public Student getStudentByNameAndSurname(String n,String sn) {
        Optional<Student> op = studentRepository.findByNameAndSurname(n,sn);
        return  op.orElse(null);
    }

    @Transactional (readOnly = true)
    public boolean isExistsStudent(String n,String sn) {
        Optional<Student> op = studentRepository.findByNameAndSurname(n,sn);
        return  op.isPresent();
    }


    @Transactional (readOnly = true)
    public List<Student> findAllStudent() {
        return studentRepository.findAll();
    }

    @Transactional (readOnly = true)
    public List<Student> findAllStudentByNameLikeOrSurnameLike(String name, String sname) {

        //return studentRepository.findAllByNameLikeOrSurnameLike(name,sname);
        return studentRepository.findAllByNameLikeAndSurnameLike("%"+name+"%","%"+sname+"%");

    }
    @Transactional (readOnly = true)
    public boolean isExistsGroupById(long groupId) {
        Optional<Group> gr = groupRepository.findByGroupId(groupId);
        return gr.isPresent();
    }

    @Transactional
    public void deleteStudent(String name, String sname) {

        Student s = studentRepository.findByNameAndSurname(name,sname).get();

        Group g = groupRepository.findById(s.getGroupId()).get();
        if (g.getListStudents().contains(s)){
            g.getListStudents().remove(s);
        }
        groupRepository.save(g);
        studentRepository.delete(s);

    }

    @Transactional (readOnly = true)
    public  List<Group>  findAllGroup() {
        List<Group>  l = new ArrayList<>(groupRepository.findAll());

        return l;
    }

    @Transactional (readOnly = true)
    public List<Group> findAllGroupByName(String groupName) {
        return groupRepository.findAllByGroupNameLike("%"+groupName+"%");
    }

    @Transactional (readOnly = true)
    public Group findGroupById(long groupId) {
        Optional<Group> g = groupRepository.findByGroupId(groupId);
        return g.orElse(null);
    }

    @Transactional
    public Group addNewGroup(Group gr) {
        University un = universityRepository.findByUniversityId(universityId).get();
        gr.setUniversity(un);
        un.getListGroup().add(gr);
        universityRepository.save(un);

        Optional<Group> n = groupRepository.findByGroupName(gr.getGroupName());
        return n.orElse(null);

    }

    @Transactional
    public void deleteGroup(long groupId) {
        Group g = groupRepository.findByGroupId(groupId).get();
        University un = universityRepository.findByUniversityId(universityId).get();

        if (un.getListGroup().contains(g))
            un.getListGroup().remove(g);

        universityRepository.save(un);
        groupRepository.delete(g);

    }

    @Transactional (readOnly = true)
    public int getCountStudentByGroupId(long groupId) {
        List<Student> l = findStudentByGroupId(groupId);
        return l.size();
    }
}