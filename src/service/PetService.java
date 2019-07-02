package service;

import DBUtils.PetDB;
import Dao.Pet;
import Dao.PetStore;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class PetService {
    PetDB petDB;
    Scanner in = new Scanner(System.in);
    {
        try {
            petDB = new PetDB();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Pet> substantialize(ResultSet rs){
        List<Pet> pets = new ArrayList<>();
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

    public boolean checkAuthorization(Integer storeId,Integer petId){
        Pet pet = getPetById(petId).get(0);
        if (pet.getStoreId()!=storeId || pet.getOwnerId()>0){
            System.out.println("您无权操作此宠物！");
            return false;
        }
        return true;
    }

    public Pet getByIdFromList(List<Pet> petList, Integer id){
        for (Pet pet:petList){
            if (pet.getId()==id){
                return pet;
            }
        }
        return null;
    }

    public List<Pet> getPetById(Integer id){
        try {
            List<Pet> pets = substantialize(petDB.getById(id));
            return pets;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void printListPets(List<Pet> pets){
        for (Pet pet:pets){
            System.out.println("------------------------");
            System.out.println("宠物名："+pet.getName());
            System.out.println("种类："+pet.getTypeName());
            System.out.println("健康："+pet.getHealth());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            System.out.println("生日："+sdf.format(pet.getBirthday()));
            System.out.println("价格："+pet.getPrice());
            System.out.println("------------------------");
        }
    }

    public void updatePet(PetStore petStore){
        System.out.println("请输入要修改的宠物ID:");
        Integer id = in.nextInt();
        if (!checkAuthorization(petStore.getId(),id)){
            return;
        }
        System.out.println("宠物名：");
        String name = in.next();
        System.out.println("种类：");
        String type = in.next();
        System.out.println("健康：");
        String health = in.next();
        System.out.println("生日：(YYYY-MM-DD)格式");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date birthday = new Date();
        try {
            birthday = sdf.parse(in.next());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println("可爱值：");
        Integer love = in.nextInt();
        System.out.println("价格：");
        Double price = in.nextDouble();
        Pet pet = new Pet();
        pet.setId(id);
        pet.setName(name);
        pet.setTypeName(type);
        pet.setHealth(health);
        pet.setBirthday(birthday);
        pet.setLove(love);
        pet.setPrice(price);
        pet.setStoreId(petStore.getId());

        try {
            int i = petDB.update(pet);
            if (i==0){
                System.out.println("更新失败");
                return;
            }
            System.out.println("更新成功");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void addPet(PetStore petStore){
        System.out.println("请输入");
        System.out.println("宠物名：");
        String name = in.next();
        System.out.println("种类：");
        String type = in.next();
        System.out.println("健康：");
        String health = in.next();

        boolean flag = true;
        Date birthday = new Date();
        while (flag){
            try {
                System.out.println("生日：(YYYY-MM-DD)格式");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                sdf.setLenient(false);
                birthday = sdf.parse(in.next());
                flag = false;
            } catch (ParseException e) {
                System.out.println("生日日期格式错误！请重新输入");
            }
        }

        System.out.println("可爱值：");
        Integer love = in.nextInt();
        System.out.println("价格：");
        Double price = in.nextDouble();
        Pet pet = new Pet();
        pet.setName(name);
        pet.setTypeName(type);
        pet.setHealth(health);
        pet.setBirthday(birthday);
        pet.setLove(love);
        pet.setPrice(price);
        pet.setStoreId(petStore.getId());
        try {
            int i = petDB.insert(pet);
            if (i==0){
                System.out.println("添加失败");
                return;
            }
            System.out.println("添加成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deletePet(PetStore petStore){
        System.out.println("请输入需要下架的宠物ID：");

        Integer id = in.nextInt();
        if (!checkAuthorization(petStore.getId(),id)){
            return;
        }

        try {
            int i = petDB.deleteById(id);
            if (i==0){
                System.out.println("删除失败!");
                return;
            }
            System.out.println("删除成功！");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

//    public Pet getById(Integer id){
//        try {
//            ResultSet rs;
//            Pet pet = new Pet();
//            rs = petDB.getById(id);
//            while (rs.next()){
//                pet.setId();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
