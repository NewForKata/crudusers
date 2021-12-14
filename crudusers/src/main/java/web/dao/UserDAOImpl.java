package web.dao;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import web.model.User;

import javax.persistence.*;
import java.util.List;

@Component
public class UserDAOImpl implements UserDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> allUsers() {
        return entityManager.createQuery("from User", User.class
        ).getResultList();
    }

    @Override
    public void save(User user) {
        User managed = entityManager.merge(user);
        entityManager.persist(managed);
    }

    @Override
    public void delete(User user) {
        User managed = entityManager.merge(user);
        entityManager.remove(managed);
    }

    @Override
    public void edit(User updatedUser) {
        entityManager.merge(updatedUser);
    }

    @Override
    public User getById(int id) {
        TypedQuery<User> q = entityManager.createQuery(
                "select user from User user where user.id = :id", User.class
        );
        q.setParameter("id", id);
        return q.getResultList().stream().findAny().orElse(null);
    }

}