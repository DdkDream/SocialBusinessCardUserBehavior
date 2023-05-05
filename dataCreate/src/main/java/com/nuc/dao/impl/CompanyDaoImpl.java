package com.nuc.dao.impl;

import com.nuc.dao.AreaDao;
import com.nuc.dao.CompanyDao;
import com.nuc.unit.City;
import com.nuc.unit.Company;
import com.nuc.unit.County;
import com.nuc.unit.Province;

import javax.lang.model.element.VariableElement;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @Author：朱瑞敏
 * @Description： TODO 企业dao层实体类
 **/
public class CompanyDaoImpl implements CompanyDao {

    ResourceBundle jdbc = ResourceBundle.getBundle("JDBC");
    String driver = jdbc.getString("driver");
    String url = jdbc.getString("url");
    String username = jdbc.getString("user");
    String password = jdbc.getString("password");

    /**
     * 向数据库中插入企业
     * @param company 要向数据库中插入的用户
     * @return 插入的结果
     */
    @Override
    public int insertCompany(Company company) {
        int result = -1;
        try {
            Class.forName(driver);
            Connection connection = DriverManager.getConnection(url, username, password);
            String sql = "replace into company values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, company.getCompany_id());
            preparedStatement.setString(2, company.getName());
            preparedStatement.setString(3, company.getPhone());
            preparedStatement.setString(4, company.getEmail());
            preparedStatement.setString(5, company.getProvince().getProvince_id());
            preparedStatement.setString(6, company.getCity().getCity_id());
            preparedStatement.setString(7, company.getCounty().getCounty_id());
            preparedStatement.setString(8, company.getMainBusiness());
            preparedStatement.setString(9, company.getSlogan());
            preparedStatement.setString(10, company.getCreateTime());
            result = preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * 查找所有已经注册的企业
     * @return 查找的所有已经注册的企业
     */
    @Override
    public List<Company> selectAllCompany() {
        List<Company> companyList = new ArrayList<>();
        try {
            Class.forName(driver);
            Connection connection = DriverManager.getConnection(url, username, password);
            String sql = "select * from company";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()){
                Company company = new Company();
                company.setCompany_id(resultSet.getString(1));
                company.setName(resultSet.getString(2));
                company.setPhone(resultSet.getString(3));
                company.setEmail(resultSet.getString(4));
                AreaDao areaDao = new AreaDaoImpl();
                Province province = areaDao.getProvinceByProvinceId(resultSet.getString(5));
                company.setProvince(province);
                City city = areaDao.getCityByCityId(resultSet.getString(6));
                company.setCity(city);
                County county = areaDao.getCountyByCountyId(resultSet.getString(7));
                company.setCounty(county);
                company.setMainBusiness(resultSet.getString(8));
                company.setSlogan(resultSet.getString(9));
                company.setCreateTime(resultSet.getString(10));
                companyList.add(company);
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return companyList;
    }

    /**
     * 通过企业id查找企业
     * @param companyId 企业的id
     * @return 查找到的企业
     */
    @Override
    public Company selectCompanyByCompanyId(String companyId) {
        Company company = new Company();
        try {
            Class.forName(driver);
            Connection connection = DriverManager.getConnection(url, username, password);
            String sql = "select * from company where company_id = '" + companyId + "'";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                company.setCompany_id(resultSet.getString(1));
                company.setName(resultSet.getString(2));
                company.setPhone(resultSet.getString(3));
                company.setEmail(resultSet.getString(4));
                AreaDao areaDao = new AreaDaoImpl();
                company.setProvince(areaDao.getProvinceByProvinceId(resultSet.getString(5)));
                company.setCity(areaDao.getCityByCityId(resultSet.getString(6)));
                company.setCounty(areaDao.getCountyByCountyId(resultSet.getString(7)));
                company.setMainBusiness(resultSet.getString(8));
                company.setSlogan(resultSet.getString(9));
                company.setCreateTime(resultSet.getString(10));
            }
            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return company;
    }
}
