package ar.com.thiagoianuzzi.model.table;

import ar.com.thiagoianuzzi.DiscordBot;
import ar.com.thiagoianuzzi.model.entity.LoggedMessage;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

import java.util.List;

public class LoggedMessageRepository {
    private final EntityManager entityManager;

    public LoggedMessageRepository() {
        this.entityManager = DiscordBot.getInstance().getEntityManager();
    }

    public List<LoggedMessage> getAll() {
        return entityManager.createQuery("SELECT u FROM LoggedMessage u", LoggedMessage.class).getResultList();
    }

    public LoggedMessage getByMessageId(String messageId) {
        try {
            return this.entityManager
                    .createQuery("SELECT p FROM LoggedMessage p WHERE p.messageId = :id", LoggedMessage.class)
                    .setParameter("id", messageId)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public void save(LoggedMessage loggedMessage) {
        entityManager.getTransaction().begin();
        entityManager.persist(loggedMessage);
        entityManager.getTransaction().commit();
    }

    public void update(LoggedMessage loggedMessage) {
        entityManager.getTransaction().begin();
        entityManager.merge(loggedMessage);
        entityManager.getTransaction().commit();
    }

    public void delete(LoggedMessage loggedMessage) {
        entityManager.getTransaction().begin();
        entityManager.remove(loggedMessage);
        entityManager.getTransaction().commit();
    }
}
