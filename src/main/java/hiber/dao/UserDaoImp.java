package hiber.dao;

import hiber.model.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoImp implements UserDao {

   @Autowired
   private SessionFactory sessionFactory;

   @Override
   public void add(User user) {
      sessionFactory.getCurrentSession().save(user);
   }

   @Override
   public void update(User user) {
      sessionFactory.getCurrentSession().update(user);
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> listUsers() {
      TypedQuery<User> query=sessionFactory.getCurrentSession().createQuery("from User");
      return query.getResultList();
   }

   @Override
   public Optional<User> findByCar(String model, int series) {
      if (model == null || model.isEmpty() || series <= 0) {
         throw new IllegalArgumentException("Car model and series are required");
      }

      try {
         String hql = "SELECT u " +
                 "FROM User u " +
                 /*
                 JOIN FETCH сразу загрузит данные машины в одном SQL-запросе
                 (решает проблему N+1 и LazyInitializationException)
                  */
                 "JOIN FETCH u.car c " +
                 "WHERE c.model = :model " +
                 "AND c.series = :series";

         Session session = sessionFactory.getCurrentSession();
         TypedQuery<User> query = session.createQuery(hql, User.class);
         query.setParameter("model", model);
         query.setParameter("series", series);

         return Optional.ofNullable(query.getSingleResult());
      } catch (HibernateException e) {
         System.out.printf("Error getting current hibernate session: %s%n", e.getMessage());
         e.printStackTrace();

         return Optional.empty();
      } catch (Exception e) {
         System.out.printf("Error getting user by car: %s%n", e.getMessage());
         e.printStackTrace();

         return Optional.empty();
      }
   }
}
