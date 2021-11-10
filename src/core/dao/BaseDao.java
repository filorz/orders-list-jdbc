package core.dao;

import core.models.Ordering;

public interface BaseDao {

    int create(String userName);

    boolean update(Ordering ordering);

    Ordering get(String id);

    String delete();

    void deleteAll();

}
