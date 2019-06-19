package service;

import DBUtils.PetOwnerDB;
import DBUtils.PetStoreDB;
import Dao.Pet;
import Dao.PetOwner;
import Dao.PetStore;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PetStoreService {

    PetStoreDB petStoreDB;
    Scanner in = new Scanner(System.in);
    {
        try {
            petStoreDB = new PetStoreDB();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public List<PetStore> getAllStore(){
        try {

            List<PetStore> petStoreList = new ArrayList<>();
            ResultSet rs = petStoreDB.getAll();
            while (rs.next()){
                PetStore petStore = new PetStore();
                petStore.setId(rs.getInt("id"));
                petStore.setName(rs.getString("name"));
                petStoreList.add(petStore);
            }
            return petStoreList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public void registerStore(){

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
                pets.add(pet);
            }
            return pets;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Pet> printNotSellPet(Integer storeId){
        List<Pet> pets = getNotSellPets(storeId);

        for (Pet pet:pets){
            System.out.println("------------------------");
            System.out.println("    宠物ID:"+pet.getId());
            System.out.println("    宠物名："+pet.getName());
            System.out.println("    种类："+pet.getTypeName());
            System.out.println("    健康："+pet.getHealth());
            System.out.println("    生日："+pet.getBirthday());
            System.out.println("    价格："+pet.getPrice());
            System.out.println("------------------------");
        }
        return pets;
    }

    public void printStoreInfo(PetStore petStore){
        boolean flag = true;
        while (flag){
            System.out.println("");
            System.out.println("商店ID："+petStore.getId());
            System.out.println("商店名称："+petStore.getName());
            System.out.println("余额："+petStore.getBalance());
            System.out.println("");
            System.out.println("是否要修改商店名称?(y/N)");
            String op = in.next();
            if (!op.equals("y")){
                flag = false;
            }else {
                System.out.println("输入新名称：");
                String name = in.next();
                petStore.setName(name);
                try {
                    int i = petStoreDB.update(petStore);
                    if (i==0){
                        System.out.println("修改失败！");
                        return;
                    }
                    System.out.println("修改成功!");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public PetStore withDrawBalance(PetStore petStore){
        PetStore petStore1 = petStore;
        System.out.println("请输入要提现的金额：");
        double money = in.nextDouble();
        if (petStore.getBalance()-money<0){
            System.out.println("余额不足！无法提现");
            return petStore;
        }
        petStore.setBalance(petStore.getBalance()-money);
        try {
            int i = petStoreDB.update(petStore);
            if (i==0){
                System.out.println("提现失败！");
                return petStore1;
            }
            System.out.println("提现成功！");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return petStore;
    }
}
