package main.java.service;

import main.java.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface GroupRepository extends JpaRepository<Group, Long> {

    Optional<Group> findByGroupId(long groupId);

    Optional<Group> findById(long groupId);

    List<Group> findAllByGroupNameLike(String s);

    Optional<Group> findByGroupName(String groupName);
}


