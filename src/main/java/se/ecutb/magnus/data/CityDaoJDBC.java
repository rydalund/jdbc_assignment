package se.ecutb.magnus.data;

import se.ecutb.magnus.entity.City;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static se.ecutb.magnus.data.Database.getConnection;

public class CityDaoJDBC implements CityDao{

    private static final String FIND_BY_ID = "SELECT*FROM city WHERE city.ID = ?";
    private static final String FIND_BY_COUNTRY_CODE = "SELECT*FROM city WHERE city.CountryCode = ?";
    private static final String FIND_BY_NAME = "SELECT*FROM city WHERE city.Name = ?";
    private static final String FIND_ALL = "SELECT*FROM city";
    private static final String ADD_CITY = "INSERT INTO city (Name,CountryCode,District,Population) VALUES (?,?,?,?)";
    private static final String UPDATE_CITY = "UPDATE city SET Name=?,CountryCode=?,District=?,Population=? WHERE city.ID=?";
    private static final String DELETE_CITY = "DELETE FROM city WHERE city.ID = ?";

    private static PreparedStatement create_findById (Connection connection, int id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(FIND_BY_ID);
        statement.setInt(1, id);
        return statement;
    }

    private static PreparedStatement create_findByCode (Connection connection, String code) throws SQLException {

        PreparedStatement statement = connection.prepareStatement(FIND_BY_COUNTRY_CODE);
        statement.setString(1, code);
        return statement;
    }

    private static PreparedStatement create_findByName (Connection connection, String name) throws SQLException {

        PreparedStatement statement = connection.prepareStatement(FIND_BY_NAME);
        statement.setString(1, name);
        return statement;
    }

    private static PreparedStatement create_findAll (Connection connection) throws SQLException {

        return connection.prepareStatement(FIND_ALL);
    }

    private City createCityFromResultSet(ResultSet resultSet) throws SQLException {
        return new City(
            resultSet.getInt("ID"),
            resultSet.getString("Name"),
            resultSet.getString("CountryCode"),
            resultSet.getString("District"),
            resultSet.getInt("Population")
        );
    }

    @Override
    public City findById(int id) {
        City city = null;

        try{
            Connection connection = getConnection();
            PreparedStatement statement = create_findById(connection,id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                city = createCityFromResultSet(resultSet);
            }
        }catch (SQLException e){
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
                citiesByCode.add(createCityFromResultSet(resultSet));
            }
        }catch (SQLException e){
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
                citiesByName.add(createCityFromResultSet(resultSet));
            }
        }catch (SQLException e){
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
                allCities.add(createCityFromResultSet(resultSet));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return allCities;
    }

    @Override
    public City add(City city){
        ResultSet resultSet = null;
        PreparedStatement statement = null;
        Connection connection = null;

        try{
            connection = getConnection();
            statement = connection.prepareStatement(ADD_CITY, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, city.getName());
            statement.setString(2, city.getCountryCode());
            statement.setString(3, city.getDistrict());
            statement.setInt(4, city.getPopulation());
            statement.execute();
            resultSet = statement.getGeneratedKeys();

            while (resultSet.next()){
                city = new City(resultSet.getInt(1),city.getName(),city.getCountryCode(),city.getDistrict(), city.getPopulation());
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try {
                if (connection != null) {
                    connection.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return city;
    }

    @Override
    public City update(City city) {
        ResultSet resultSet = null;

        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE_CITY)
            ){
                statement.setString(1, city.getName());
                statement.setString(2, city.getCountryCode());
                statement.setString(3, city.getDistrict());
                statement.setInt(4, city.getPopulation());
                statement.setInt(5, city.getId());
                statement.execute();
            }catch (SQLException e){
            e.printStackTrace();
        }
        return city;
    }


    @Override
    public int delete(City city) {
        int foo = 0;

        try (Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(DELETE_CITY)
            ){
                statement.setInt(1,city.getId());
            foo = statement.executeUpdate();


        }catch (SQLException e){
            e.printStackTrace();
        }
        return foo;
    }
}
