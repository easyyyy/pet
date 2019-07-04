package service;

import DBUtils.HospitalDB;
import DBUtils.MedicalCertificateDB;
import DBUtils.PetDB;
import DBUtils.PetOwnerDB;
import Dao.Hospital;
import Dao.MedicalCertificate;
import Dao.Pet;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HospitalService{

    HospitalDB hospitalDB;
    MedicalCertificateDB medicalCertificateDB;
    MedicalCertificateService medicalCertificateService;
    PetOwnerDB petOwnerDB;
    PetOwnerService petOwnerService;
    PetDB petDB;
    PetService petService;
    Scanner in = new Scanner(System.in);

    {
        try {
            medicalCertificateDB = new MedicalCertificateDB();
            medicalCertificateService = new MedicalCertificateService();
            petOwnerDB = new PetOwnerDB();
            petOwnerService = new PetOwnerService();
            hospitalDB = new HospitalDB();
            petDB = new PetDB();
            petService = new PetService();
        } catch (Exception e) {

        }
    }

    public List<Hospital> substantialize(ResultSet rs){
        List<Hospital> hospitalList = new ArrayList<>();
        try {
            while (rs.next()){
                Hospital hospital = new Hospital();
                hospital.setId(rs.getInt("id"));
                hospital.setName(rs.getString("name"));
                hospital.setPassword(rs.getString("password"));
                hospital.setBalance(rs.getDouble("balance"));
                hospitalList.add(hospital);

            }
        } catch (SQLException e) {

        }
        return hospitalList;
    }

    public void printHospitalInfo(Hospital hospital){
        boolean flag = true;
        while (flag){
            System.out.println("");
            System.out.println("医院ID："+hospital.getId());
            System.out.println("医院名称："+hospital.getName());
            System.out.println("余额："+hospital.getBalance());
            System.out.println("");
            System.out.println("是否要修改医院名称?(y/N)");
            String op = in.next();
            if (!op.equals("y")){
                flag = false;
            }else {
                System.out.println("输入新名称：");
                String name = in.next();
                hospital.setName(name);
                try {
                    int i = hospitalDB.update(hospital);
                    if (i==0){
                        System.out.println("修改失败！");
                        return;
                    }
                    System.out.println("修改成功!");
                } catch (Exception e) {

                }
            }
        }
    }

    public void printAllHospitl(){

        try {
            List<Hospital> hospitals = new ArrayList<>();
            hospitals = substantialize(hospitalDB.getAll());
            for (Hospital hospital:hospitals){
                System.out.println("-------------------");
                System.out.println("医院ID："+hospital.getId());
                System.out.println("医院名称："+hospital.getName());
                System.out.println("-------------------");
            }

        } catch (Exception e) {

        }
    }

    public Hospital loginByHospital(){
        Scanner in = new Scanner(System.in);
        System.out.println("输入医院名：");
        String name = in.next();
        System.out.println("输入密码：");
        String password = in.next();
        try {
            Hospital hospital = substantialize(hospitalDB.getByName(name)).get(0);
            if (hospital == null){
                System.out.println("医院名或密码错误！");
                return null;
            }
            if (password.equals(hospital.getPassword())){
                System.out.println("登陆成功！");
                return hospital;
            }
        }catch (Exception e){

            System.out.println("医院名或密码错误！");
        }
        return null;
    }

    public void registerHospital(){
        Scanner in = new Scanner(System.in);
        System.out.println("输入医院名：");
        String name = in.next();
        System.out.println("输入密码：");
        String password = in.next();
        System.out.println("再次输入密码");
        String password2 = in.next();

        if (!password.equals(password2)){
            System.out.println("第二次输入密码不正确！");
            return;
        }

        Hospital hospital = new Hospital();
        hospital.setName(name);
        hospital.setPassword(password);
        hospital.setBalance(0.0);
        int i = 0;
        try {
            i = hospitalDB.insert(hospital);
        } catch (Exception e) {
            System.out.println("该医院名已注册，请更换");
        }
        if (i==1){
            System.out.println("注册成功！");
        }else{
            System.out.println("注册失败");
        }

    }

    public void waitDiagnosis(Hospital hospital){
        try {
            List<MedicalCertificate> medicalCertificateList = medicalCertificateService.substantialize(medicalCertificateDB.getWaitDiagnosisByHospitalId(hospital.getId()));
            if (medicalCertificateList.size()==0){
                System.out.println("没有需要诊断的宠物");
                return;
            }

            for (MedicalCertificate medicalCertificate:medicalCertificateList){
                ResultSet rs = petOwnerDB.getById(medicalCertificate.getPet_owner_id());
                Pet pet = petService.substantialize(petDB.getById(medicalCertificate.getPetId())).get(0);
                String petOwnerName = null;
                String phone = null;
                String address = null;
                while (rs.next()){
                    petOwnerName = rs.getString("name");
                    phone = rs.getString("phone");
                    address = rs.getString("address");
                }
                System.out.println("-----------------");
                System.out.println("诊断书ID："+medicalCertificate.getId());
                System.out.println("宠物名字："+pet.getName());
                System.out.println("宠物主人姓名："+petOwnerName);
                System.out.println("联系电话："+phone);
                System.out.println("地址："+address);
                System.out.println("诊断结果："+medicalCertificate.getDetail());
                System.out.println("日期："+medicalCertificate.getDate());
                System.out.println("-----------------");
            }

            System.out.println("是否进行诊断？（y/N）");
            String op = in.next();
            if (op.equals("y")){
                System.out.println("输入需要诊断的诊断单ID：");
                int id = in.nextInt();
                MedicalCertificate medicalCertificateNew = new MedicalCertificate();
                for (MedicalCertificate medicalCertificate:medicalCertificateList){
                    if(medicalCertificate.getId()==id){
                        medicalCertificateNew = medicalCertificate;
                        System.out.println("输入诊断结果：");
                        String detail = in.next();
                        System.out.println("输入诊金：");
                        Double money = in.nextDouble();
                        medicalCertificateNew.setDiagnosis(1);
                        medicalCertificateNew.setDetail(detail);
                        medicalCertificateNew.setMoney(money);
                        medicalCertificateDB.update(medicalCertificate);
                        System.out.println("待宠物主人付款");
                        break;
                    }
                }

                if (medicalCertificateNew.getId()==null){
                    System.out.println("越权！");
                }
            }
            else {
                return;
            }
        } catch (Exception e) {

        }
    }

    public void allDiagnosis(Hospital hospital){
        try {
            List<MedicalCertificate> medicalCertificateList = medicalCertificateService.substantialize(medicalCertificateDB.getAllDiagnosisByHospitalId(hospital.getId()));
            if (medicalCertificateList.size()==0){
                System.out.println("没有已诊断的宠物");
                return;
            }

            for (MedicalCertificate medicalCertificate:medicalCertificateList){
                ResultSet rs = petOwnerDB.getById(medicalCertificate.getPet_owner_id());
                Pet pet = petService.substantialize(petDB.getById(medicalCertificate.getPetId())).get(0);
                String petOwnerName = null;
                String phone = null;
                String address = null;
                while (rs.next()){
                    petOwnerName = rs.getString("name");
                    phone = rs.getString("phone");
                    address = rs.getString("address");
                }
                System.out.println("-----------------");
                System.out.println("诊断书ID："+medicalCertificate.getId());
                System.out.println("宠物名字："+pet.getName());
                System.out.println("宠物主人姓名："+petOwnerName);
                System.out.println("联系电话："+phone);
                System.out.println("地址："+address);
                System.out.println("诊断结果："+medicalCertificate.getDetail());
                System.out.println("诊金："+medicalCertificate.getMoney());
                if (medicalCertificate.getPaid()==0){
                    System.out.println("状态：未支付");
                }else {
                    System.out.println("状态：已支付");
                }
                System.out.println("日期："+medicalCertificate.getDate());
                System.out.println("-----------------");
            }

        } catch (Exception e) {

        }
    }


}
