package com.nuc.main;

import com.nuc.controller.CompanyController;
import com.nuc.controller.UserController;
import com.nuc.controller.impl.CompanyControlllerImpl;
import com.nuc.controller.impl.UserControllerImpl;
import com.nuc.unit.Company;
import com.nuc.unit.LoginRecord;
import com.nuc.unit.User;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

/**
 * @Author：朱瑞敏
 * @Description： TODO
 **/
public class Main {
    public static void main(String[] args) throws IOException {

        Random random = new Random();
//        int index = random.nextInt(10);
        while(true){
            int index = random.nextInt(6) + 1;
            UserController userController = new UserControllerImpl();
            CompanyController companyController = new CompanyControlllerImpl();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            switch (index)
            {
                case 1:  // 创建新用户注册
                    UserController createUserController = new UserControllerImpl();
                    User user = createUserController.createUser();
                    if(user.getName().equals("create-User-Error")){
                        outData("Register-User-Error");
                        // System.out.println("Register-User-Error");
                    }else{
                        outData("Register-User-Success-" + user.toString());
                        // System.out.println("Register-User-Success-" + user.toString());
                    }
                    break;
                case 2: // 创建新企业注册
                    companyController = new CompanyControlllerImpl();
                    Company company = companyController.createCompany();
                    if(company.getName().equals("create-Company-Error")){
                        outData("Register-Company-Error");
                        // System.out.println("Register-Company-Error");
                    }else{
                        outData("Register-Company-Success-" + company.toString());
                        // System.out.println("Register-Company-Success-" + company.toString());
                    }
                    break;
                case 3: // 用户登陆功能
                    LoginRecord loginRecordUser = userController.loginUser();
                    if(loginRecordUser != null){
                        User loginUser = userController.selectUserByUserId(loginRecordUser.getId());
                        outData("Login-User-Success-" + loginUser.getUserId() + "-" + loginUser.getSurname() + loginUser.getName() + "-" + loginRecordUser.getLoginTime() + "-"
                                + loginRecordUser.getProvince().getProvince_name() + "-" + loginRecordUser.getCity().getCity_name()
                                + "-" + loginRecordUser.getCounty().getCounty_name());
                        // System.out.println("Login-User-Success-" + loginUser.getSurname() + loginUser.getName() + "-" + loginRecordUser.getLoginTime() + "-"
                        //        + loginRecordUser.getProvince().getProvince_name() + "-" + loginRecordUser.getCity().getCity_name()
                        //        + "-" + loginRecordUser.getCounty().getCounty_name());
                    }else{
                        outData("Login-User-Error");
                        // System.out.println("Login-User-Error");
                    }
                    break;
                case 4: // 用户退出登陆功能
                    LoginRecord loginRecordUserOut = userController.outUser();
                    if(loginRecordUserOut != null){
                        User outUser = userController.selectUserByUserId(loginRecordUserOut.getId());
                        outData("OutLogin-User-Success-" + outUser.getUserId() + "-" +  outUser.getSurname() + outUser.getName() + "-" + loginRecordUserOut.getLoginTime()
                                + "-" + loginRecordUserOut.getOutTime() + "-"
                                + loginRecordUserOut.getProvince().getProvince_name() + "-" + loginRecordUserOut.getCity().getCity_name()
                                + "-" + loginRecordUserOut.getCounty().getCounty_name());
                        // System.out.println("OutLogin-User-Success-" + outUser.getSurname() + outUser.getName() + "-" + loginRecordUserOut.getLoginTime()
                        //        + "-" + loginRecordUserOut.getOutTime() + "-"
                        //        + loginRecordUserOut.getProvince().getProvince_name() + "-" + loginRecordUserOut.getCity().getCity_name()
                        //        + "-" + loginRecordUserOut.getCounty().getCounty_name());
                    }else{
                        outData("OutLogin-User-Error");
                        // System.out.println("OutLogin-User-Error");
                    }
                    break;
                case 5: // 企业登陆功能
                    LoginRecord loginRecordCompany = companyController.loginCompany();
                    if(loginRecordCompany != null){
                        Company companyLogin = companyController.selectCompanyByCompanyId(loginRecordCompany.getId());
                        outData("Login-Company-Success-" + companyLogin.getCompany_id() + "-" + companyLogin.getName() + "-" + loginRecordCompany.getLoginTime() + "-"
                                + loginRecordCompany.getProvince().getProvince_name() + "-" + loginRecordCompany.getCity().getCity_name()
                                + "-" + loginRecordCompany.getCounty().getCounty_name());
                        // System.out.println("Login-Company-Success-" + companyLogin.getName() + "-" + loginRecordCompany.getLoginTime() + "-"
                        //        + loginRecordCompany.getProvince().getProvince_name() + "-" + loginRecordCompany.getCity().getCity_name()
                        //        + "-" + loginRecordCompany.getCounty().getCounty_name());
                    }else{
                        outData("Login-Company-Error");
                        // System.out.println("Login-Company-Error");
                    }
                    break;
                case 6: // 企业退出登陆功能
                    LoginRecord loginRecordCompanyOut = companyController.outCompany();
                    if(loginRecordCompanyOut != null){
                        Company outCompany = companyController.selectCompanyByCompanyId(loginRecordCompanyOut.getId());
                        outData("OutLogin-Company-Success-" + outCompany.getCompany_id() + "-" + outCompany.getName() + "-" + loginRecordCompanyOut.getLoginTime()
                                + "-" + loginRecordCompanyOut.getOutTime() + "-"
                                + loginRecordCompanyOut.getProvince().getProvince_name() + "-" + loginRecordCompanyOut.getCity().getCity_name()
                                + "-" + loginRecordCompanyOut.getCounty().getCounty_name());
                        // System.out.println("OutLogin-Company-Success-" + outUser.getSurname() + outUser.getName() + "-" + loginRecordCompanyOut.getLoginTime()
                        //        + "-" + loginRecordCompanyOut.getOutTime() + "-"
                        //        + loginRecordCompanyOut.getProvince().getProvince_name() + "-" + loginRecordCompanyOut.getCity().getCity_name()
                        //        + "-" + loginRecordCompanyOut.getCounty().getCounty_name());
                    }else{
                        outData("OutLogin-Company-Error");
                        // System.out.println("OutLogin-Company-Error");
                    }
                    break;
                default:
                    System.out.println("错误代码");
            }
        }
    }


    public static void outData(String data) throws IOException {
//        System.out.println(data);
        String path = "/opt/bs/dataCreate/operation.log";
        BufferedWriter bw = new BufferedWriter(new FileWriter(path, true));
        bw.write(data);
        bw.newLine();
        bw.flush();
    }

}