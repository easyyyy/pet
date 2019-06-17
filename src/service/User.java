package service;

import DBUtils.PetOwnerDB;
import Dao.PetOwner;

import java.util.Scanner;

public class User {
    PetOwnerDB petOwnerDB;

    {
        try {
            petOwnerDB = new PetOwnerDB();
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
                System.out.println("密码错误！");
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

}
