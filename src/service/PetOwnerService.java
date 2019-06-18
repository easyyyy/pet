package service;

import DBUtils.DealDB;
import DBUtils.PetDB;
import DBUtils.PetOwnerDB;
import DBUtils.PetStoreDB;
import Dao.Deal;
import Dao.Pet;
import Dao.PetOwner;
import Dao.PetStore;

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


    {
        try {
            petOwnerDB = new PetOwnerDB();
            petDB = new PetDB();
            dealDB = new DealDB();
        } catch (Exception e) {
            e.printStackTrace();
        }

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
            e.printStackTrace();
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
            e.printStackTrace();
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
            e.printStackTrace();
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
            e.printStackTrace();
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
            e.printStackTrace();
        }
        return pets;
    }

    public void printListPets(List<Pet> pets){
        for (Pet pet:pets){
            System.out.println("------------------------");
            System.out.println("宠物名："+pet.getName());
            System.out.println("种类："+pet.getTypeName());
            System.out.println("健康："+pet.getHealth());
            System.out.println("生日："+pet.getBirthday());
            System.out.println("------------------------");
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
            }
            System.out.println("昵称："+petOwner.getName());

            System.out.println("余额："+petOwner.getBalance());

            System.out.println("宠物信息：");
            printListPets(getPetByOwnerId(petOwner.getId()));

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

}
