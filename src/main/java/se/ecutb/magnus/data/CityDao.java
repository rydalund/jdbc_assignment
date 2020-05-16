package se.ecutb.magnus.data;

import se.ecutb.magnus.entity.City;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface CityDao {
    City findById(int id);
    List<City> findByCode(String code);
    List<City> findByName(String name);
    List<City> findAll();
    City add(City city) throws SQLException;
    City update(City city);
    int delete(City city);
}
