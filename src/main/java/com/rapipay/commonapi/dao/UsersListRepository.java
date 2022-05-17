package com.rapipay.commonapi.dao;

import com.portal.common.models.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class UsersListRepository {

    @Autowired
    EntityManagerFactory entityManagerFactory;

    public void getUsersByMobileNo(String mobileNo, Response response) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        List<Object[]> results = em.createQuery("SELECT P.userId FROM UsersList P JOIN TerminalUser N ON P.id = N.userLoginId").getResultList();
        System.out.println(results);
        em.getTransaction().commit();
        em.close();

    }
}
