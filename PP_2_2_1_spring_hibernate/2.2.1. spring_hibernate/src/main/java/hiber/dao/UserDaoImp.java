package hiber.dao;

import hiber.exceptions.NonUniqueResultReturnFirstException;
import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

   @Autowired
   private SessionFactory sessionFactory;

   @Override
   public void add(User user) {
      sessionFactory.getCurrentSession().save(user);
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> listUsers() {
      TypedQuery<User> query=sessionFactory.getCurrentSession().createQuery("from User");
      return query.getResultList();
   }

   @Override
   public User getUserByCarModelAndCarSeries(String model, int series) {
      TypedQuery<User> query=sessionFactory.getCurrentSession().createQuery("from User user " +
              "where user.car.model=:model and user.car.series=:series", User.class);
      query.setParameter("model", model);
      query.setParameter("series", series);
      List<User> result = query.getResultList();
      if (result.isEmpty()) {
         throw new NoResultException();
      }
      if (result.size()>1){
         throw new NonUniqueResultReturnFirstException(result.size(), result.get(0));
      }
      return result.get(0);
   }
}

