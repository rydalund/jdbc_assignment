package se.ecutb.magnus.data;

import se.ecutb.magnus.entity.City;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static se.ecutb.magnus.data.Database.getConnection;

public class CityDaoJDBC implements CityDao{

    private static PreparedStatement create_findById (Connection connection, int id) throws SQLException {
        final String FIND_BY_ID = "SELECT*FROM city WHERE city.ID = ?";
        PreparedStatement statement = connection.prepareStatement(FIND_BY_ID);
        statement.setInt(1, id);
        return statement;
    }

    private static PreparedStatement create_findByCode (Connection connection, String code) throws SQLException {
        final String FIND_BY_COUNTRY_CODE = "SELECT*FROM city WHERE city.CountryCode = ?";
        PreparedStatement statement = connection.prepareStatement(FIND_BY_COUNTRY_CODE);
        statement.setString(1, code);
        return statement;
    }

    private static PreparedStatement create_findByName (Connection connection, String name) throws SQLException {
        final String FIND_BY_NAME = "SELECT*FROM city WHERE city.Name = ?";
        PreparedStatement statement = connection.prepareStatement(FIND_BY_NAME);
        statement.setString(1, name);
        return statement;
    }

    private static PreparedStatement create_findAll (Connection connection) throws SQLException {
        final String FIND_ALL = "SELECT*FROM city";
        return connection.prepareStatement(FIND_ALL);
    }

    private static PreparedStatement create_add (Connection connection, City city) throws SQLException {
        final String ADD_CITY = "INSERT INTO city (Name,CountryCode,District,Population) VALUES (?,?,?,?)";
        PreparedStatement statement = connection.prepareStatement(ADD_CITY, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, city.getName());
        statement.setString(2, city.getCountryCode());
        statement.setString(3, city.getDistrict());
        statement.setInt(4, city.getPopulation());
        return statement;
    }

    private static PreparedStatement create_update (Connection connection, City city) throws SQLException {
        final String UPDATE_CITY = "UPDATE city SET Name=?,CountryCode=?,District=?,Population=? WHERE city.ID=?";
        PreparedStatement statement = connection.prepareStatement(UPDATE_CITY);
        statement.setString(1, city.getName());
        statement.setString(2, city.getCountryCode());
        statement.setString(3, city.getDistrict());
        statement.setInt(4, city.getPopulation());
        statement.setInt(5, city.getId());
        return statement;
    }

    private static PreparedStatement create_delete (Connection connection, City city) throws SQLException {
        final String DELETE_CITY = "DELETE FROM city WHERE city.ID = ?";
        PreparedStatement statement = connection.prepareStatement(DELETE_CITY);
        statement.setInt(1,city.getId());
        return statement;
    }

    @Override
    public City findById(int id){
        City city = null;

        try{
            Connection connection = getConnection();
            PreparedStatement statement = create_findById(connection,id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                city = new City(
                        resultSet.getInt("ID"),
                        resultSet.getString("Name"),
                        resultSet.getString("CountryCode"),
                        resultSet.getString("District"),
                        resultSet.getInt("Population")
                );
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return city;
    }

    @Override
    public List<City> findByCode(String code) {
        List<City> citiesByCode = new ArrayList<>();

        try{
            Connection connection = getConnection();
            PreparedStatement statement = create_findByCode(connection,code);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                citiesByCode.add(
                        new City(
                                resultSet.getInt("ID"),
                                resultSet.getString("Name"),
                                resultSet.getString("CountryCode"),
                                resultSet.getString("District"),
                                resultSet.getInt("Population")
                        )
                );
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return citiesByCode;
    }

    @Override
    public List<City> findByName(String name) {
        List<City> citiesByName = new ArrayList<>();

        try{
            Connection connection = getConnection();
            PreparedStatement statement = create_findByName(connection,name);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                citiesByName.add(
                        new City(
                                resultSet.getInt("ID"),
                                resultSet.getString("Name"),
                                resultSet.getString("CountryCode"),
                                resultSet.getString("District"),
                                resultSet.getInt("Population")
                        )
                );
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return citiesByName;
    }

    @Override
    public List<City> findAll() {
        List<City> allCities = new ArrayList<>();

        try{
            Connection connection = getConnection();
            PreparedStatement statement = create_findAll(connection);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                allCities.add(
                        new City(
                                resultSet.getInt("ID"),
                                resultSet.getString("Name"),
                                resultSet.getString("CountryCode"),
                                resultSet.getString("District"),
                                resultSet.getInt("Population")
                        )
                );
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return allCities;
    }

    @Override
    public City add(City city) {

        try{
            Connection connection = getConnection();
            PreparedStatement statement = create_add(connection, city);
            statement.execute();
            ResultSet resultSet = statement.getGeneratedKeys();
            while (resultSet.next()){
                city = new City(
                        resultSet.getInt(1),
                        city.getName(),
                        city.getCountryCode(),
                        city.getDistrict(),
                        city.getPopulation()
                );
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return city;
    }

    @Override
    public City update(City city) {

        try{
            Connection connection = getConnection();
            PreparedStatement statement = create_update(connection, city);
            statement.execute();

        }catch (Exception e){
            e.printStackTrace();
        }
        return city;
    }

    @Override
    public int delete(City city) {
        int foo = 0;

        try{
            Connection connection = getConnection();
            PreparedStatement statement = create_delete(connection, city);
            foo = statement.executeUpdate();

        }catch (Exception e){
            e.printStackTrace();
        }
        return foo;
    }
}
