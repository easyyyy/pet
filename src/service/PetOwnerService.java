package service;

import DBUtils.*;
import Dao.*;

import javax.xml.bind.SchemaOutputResolver;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;




public class PetOwnerService {
    PetOwnerDB petOwnerDB;
    PetDB petDB;
    DealDB dealDB;

    base base1 = new base();
    Scanner in = base.in;
    HospitalService hospitalService = base1.hospitalService;
    MedicalCertificateDB medicalCertificateDB;

    {
        try {
            petOwnerDB = new PetOwnerDB();
            petDB = new PetDB();
            dealDB = new DealDB();
//            dealDB = base1.dealDB;
//            hospitalService = new HospitalService();
            medicalCertificateDB = new MedicalCertificateDB();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

//
//    public List<PetOwner> substantialize(ResultSet rs){
//        try {
//            List<PetOwner> petOwnerList = new ArrayList<>();
//
//            while (rs.next()){
//
//                PetOwner petOwner = new PetOwner();
////                good.setId(rs.getInt("id"));
////                good.setName(rs.getString("name"));
////                good.setNumber(rs.getInt("number"));
////                good.setPrice(rs.getDouble("price"));
////                good.setStoreId(rs.getInt("store_id"));
////                goodsList.add(good);
//                petOwner.set
//
//            }
//            return goodsList;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    public PetOwner setAddress(PetOwner petOwner){
        System.out.println("请输入新地址：");
        String address = in.next();
        petOwner.setAddress(address);
        try {

            petOwnerDB.update(petOwner);
            System.out.println("修改成功！");
            return petOwner;
        } catch (Exception e) {

        }
        return null;
    }

    public PetOwner setPhone(PetOwner petOwner){
        System.out.println("请输入新联系电话：");
        String phone = in.next();
        petOwner.setPhone(phone);
        try {
            petOwnerDB.update(petOwner);
            System.out.println("修改成功！");
            return petOwner;
        } catch (Exception e) {

        }
        return null;
    }

    public PetOwner loginByOwner(){
        Scanner in = new Scanner(System.in);
        System.out.println("输入用户名：");
        String name = in.next();
        System.out.println("输入密码：");
        String password = in.next();
        try {
            PetOwner s = petOwnerDB.getByName(name);
            if (s == null){
                System.out.println("用户名或密码错误！");
                return null;
            }
            if (password.equals(s.getPassword())){
                System.out.println("登陆成功！");
                return s;
            }
        }catch (Exception e){

            System.out.println("用户名或密码错误！");
        }
        return null;
    }

    public void registerOwner(){
        Scanner in = new Scanner(System.in);
        System.out.println("输入用户名：");
        String name = in.next();
        System.out.println("输入密码：");
        String password = in.next();
        System.out.println("再次输入密码");
        String password2 = in.next();

        if (!password.equals(password2)){
            System.out.println("第二次输入密码不正确！");
            return;
        }
        PetOwner petOwner = new PetOwner();
        petOwner.setName(name);
        petOwner.setPassword(password);
        int i = petOwnerDB.insert(petOwner);
        if (i==1){
            System.out.println("注册成功！");
        }else{
            System.out.println("注册失败");
        }

    }

    public PetOwner changeBalance(PetOwner petOwner){
        Scanner in = new Scanner(System.in);
        System.out.println("输入充值金额：");
        Double money = in.nextDouble();

        try {
            petOwner.setBalance(petOwner.getBalance()+money);
            int i = petOwnerDB.update(petOwner);
            if (i==1){
                System.out.println("充值成功！");
                return petOwner;
            }
            petOwner.setBalance(petOwner.getBalance()+money);
            return petOwner;
        } catch (Exception e) {

        }
        return petOwner;
    }

    public void buyingPet(PetOwner petOwner, Pet pet){
        Deal deal = new Deal();
        deal.setDealType(1);
        deal.setPetId(pet.getId());
        deal.setSellerId(pet.getStoreId());
        deal.setBuyerId(petOwner.getId());
        deal.setPrice(pet.getPrice());
        deal.setAddress(petOwner.getAddress());
        deal.setPhone(petOwner.getPhone());


        pet.setOwnerId(petOwner.getId());
        if ((petOwner.getBalance()-pet.getPrice())<0){
            System.out.println("余额不足请充值");
            return;
        }
        petOwner.setBalance(petOwner.getBalance()-pet.getPrice());
        try {

            petDB.update(pet);

            dealDB.insert(deal);
            int i = petOwnerDB.update(petOwner);
            if (i!=1){
                System.out.println("购买失败");
            }
            System.out.println("购买成功");
        } catch (Exception e) {

        }
    }

    public void sellPet(PetOwner petOwner, Pet pet, PetStore petStore){
        Deal deal = new Deal();
        deal.setDealType(2);
        deal.setPetId(pet.getId());
        deal.setSellerId(petOwner.getId());
        deal.setBuyerId(petStore.getId());
        deal.setPrice(pet.getPrice());


        pet.setOwnerId(null);
        if ((petStore.getBalance()-pet.getPrice())<0){
            System.out.println("余额不足请充值");
            return;
        }
        petStore.setBalance(petStore.getBalance()-pet.getPrice());
        try {
            petDB.update(pet);
            dealDB.insert(deal);
            int i = petOwnerDB.update(petOwner);
            if (i!=1){
                System.out.println("购买失败");
            }
            System.out.println("购买成功");
        } catch (Exception e) {

        }
    }

    public List<Pet> getPetByOwnerId(Integer id){
        List<Pet> pets = new ArrayList<>();
        ResultSet rs = petOwnerDB.getPetByOwnerId(id);

        try {
            while (rs.next()){
                Pet pet = new Pet();
                pet.setId(rs.getInt("id"));
                pet.setName(rs.getString("name"));
                pet.setTypeName(rs.getString("type_name"));
                pet.setHealth(rs.getString("health"));
                pet.setLove(rs.getInt("love"));
                pet.setBirthday(rs.getDate("birthday"));
                pet.setOwnerId(rs.getInt("owner_id"));
                pet.setStoreId(rs.getInt("store_id"));
                pet.setPrice(rs.getDouble("price"));
                pets.add(pet);
            }
        } catch (SQLException e) {

        }
        return pets;
    }

    public void printListPets(List<Pet> pets){
        for (Pet pet:pets){
            System.out.println("-----------------");
            System.out.println("宠物ID："+pet.getId());
            System.out.println("宠物名："+pet.getName());
            System.out.println("种类："+pet.getTypeName());
            System.out.println("健康："+pet.getHealth());
            System.out.println("生日："+pet.getBirthday());
            System.out.println("-----------------");
        }
    }

    public void getInfoById(Integer id){
        ResultSet rs = petOwnerDB.getById(id);
        PetOwner petOwner = new PetOwner();
        try {
            while (rs.next()){
                petOwner.setId(rs.getInt("id"));
                petOwner.setName(rs.getString("name"));
                petOwner.setBalance(rs.getDouble("balance"));
                petOwner.setPhone(rs.getString("phone"));
                petOwner.setAddress(rs.getString("address"));
            }
            System.out.println("昵称："+petOwner.getName());

            System.out.println("余额："+petOwner.getBalance());

            System.out.println("收货地址："+petOwner.getAddress());

            System.out.println("联系电话："+petOwner.getPhone());

            System.out.println("宠物信息：");
            printListPets(getPetByOwnerId(petOwner.getId()));


        } catch (SQLException e) {

        }


    }

    public void seekMedicalAttention(PetOwner petOwner){
        System.out.println("-----------------");
        System.out.println("选择医院：");
        hospitalService.printAllHospitl();
        int op = in.nextInt();
        printListPets(getPetByOwnerId(petOwner.getId()));
        System.out.println("选择需要就医的宠物ID");
        int petId = in.nextInt();

        MedicalCertificate medicalCertificate = new MedicalCertificate();
        medicalCertificate.setPetId(petId);
        medicalCertificate.setHospitalId(op);
        medicalCertificate.setPet_owner_id(petOwner.getId());
        medicalCertificate.setDiagnosis(0);
        try {
            medicalCertificateDB.insert(medicalCertificate);
            System.out.println("宠物已送往医院就医，等待就医");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("-----------------");
    }

    public void printMedicalCertificate(PetOwner petOwner){


        try {

            List<MedicalCertificate> medicalCertificateList = base1.medicalCertificateService.substantialize(medicalCertificateDB.getAllByOwnerId(petOwner.getId()));

            for (MedicalCertificate medicalCertificate:medicalCertificateList){
                ResultSet rs = petOwnerDB.getById(medicalCertificate.getPet_owner_id());
                Pet pet = base.petService.substantialize(petDB.getById(medicalCertificate.getPetId())).get(0);
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
                System.out.println("诊金："+medicalCertificate.getMoney());
                if (medicalCertificate.getPaid()==0){
                    System.out.println("状态：未支付");
                }else {
                    System.out.println("状态：已支付");
                }

                System.out.println("-----------------");
            }

            System.out.println("是否支付诊金？（y/N）");
            String op = in.next();
            if (op.equals("y")){
                System.out.println("选择需要支付的诊断书ID：");
                int id = in.nextInt();
                MedicalCertificate medicalCertificate1 = null;
                for (MedicalCertificate medicalCertificate:medicalCertificateList){
                    if (medicalCertificate.getId()==id){
                        petOwner.setBalance(petOwner.getBalance()-medicalCertificate.getMoney());
                        Hospital hospital = hospitalService.substantialize(base1.hospitalDB.getByid(medicalCertificate.getHospitalId())).get(0);
                        hospital.setBalance(hospital.getBalance()+medicalCertificate.getMoney());

                        petOwnerDB.update(petOwner);
                        base1.hospitalDB.update(hospital);
                        System.out.println("支付成功!");
                        medicalCertificate1 = medicalCertificate;
                        medicalCertificate1.setPaid(1);
                        medicalCertificateDB.update(medicalCertificate1);
                    }
                }
                if (medicalCertificate1==null){
                    System.out.println("支付失败！");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
