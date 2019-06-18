package service;

import DBUtils.PetOwnerDB;
import DBUtils.PetStoreDB;
import Dao.Pet;
import Dao.PetOwner;
import Dao.PetStore;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PetStoreService {

    PetStoreDB petStoreDB;
    {
        try {
            petStoreDB = new PetStoreDB();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void registerStore(){
        Scanner in = new Scanner(System.in);
        PetStore petStore = new PetStore();
        System.out.println("请输入商店名：");
        petStore.setName(in.next());
        System.out.println("请输入密码：");
        String p1 = in.next();
        System.out.println("请再次输入密码：");
        String p2 = in.next();
        if (!p1.equals(p2)){
            System.out.println("两次输入密码不正确！");
            return;
        }
        petStore.setPassword(p1);
        try {

            int i = petStoreDB.insert(petStore);
            if (i==1){
                System.out.println("注册成功！");
                return;
            }
            System.out.println("注册失败！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public PetStore loginStore(){
        Scanner in = new Scanner(System.in);
        PetStore petStore = new PetStore();
        System.out.println("请输入商店名：");
        petStore.setName(in.next());
        System.out.println("请输入密码：");
        String p1 = in.next();
        try {
            petStore = petStoreDB.getByName(petStore.getName());
            if (petStore!=null){
                System.out.println("登陆成功！");
                return petStore;
            }
            System.out.println("登陆失败！");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Pet> getNotSellPets(Integer storeId){
        List<Pet> pets = new ArrayList<>();
        ResultSet rs = petStoreDB.getNotSellPet(storeId);
        try {

            while (rs.next()){

                Pet pet = new Pet();
                pet.setId(rs.getInt("id"));
                pet.setName(rs.getString("name"));
                pet.setTypeName(rs.getString("type_name"));
                pet.setPrice(rs.getDouble("price"));
                pet.setHealth(rs.getString("health"));
                System.out.println(pet);
                pets.add(pet);
            }
            System.out.println(pets);
            return pets;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void printNotSellPet(Integer storeId){
        List<Pet> pets = getNotSellPets(storeId);

        for (Pet pet:pets){
            System.out.println("------------------------");
            System.out.println("宠物ID:"+pet.getId());
            System.out.println("宠物名："+pet.getName());
            System.out.println("种类："+pet.getTypeName());
            System.out.println("健康："+pet.getHealth());
            System.out.println("生日："+pet.getBirthday());
            System.out.println("价格："+pet.getPrice());
            System.out.println("------------------------");
        }
    }
}