package service;

import DBUtils.DealDB;
import DBUtils.GoodsDB;
import DBUtils.PetOwnerDB;
import DBUtils.PetStoreDB;
import Dao.*;
import sun.util.resources.cldr.es.CalendarData_es_PY;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GoodsService {
    GoodsDB goodsDB;
    PetStoreDB petStoreDB;
    PetOwnerDB petOwnerDB;
    DealDB dealDB;
    Scanner in = new Scanner(System.in);

    {
        try {
            goodsDB = new GoodsDB();
            petStoreDB = new PetStoreDB();
            petOwnerDB = new PetOwnerDB();
            dealDB = new DealDB();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Goods> substantialize(ResultSet rs){
        try {
            List<Goods> goodsList = new ArrayList<>();

            while (rs.next()){
                System.out.println();
                Goods good = new Goods();
                good.setId(rs.getInt("id"));
                good.setName(rs.getString("name"));
                good.setNumber(rs.getInt("number"));
                good.setPrice(rs.getDouble("price"));
                good.setStoreId(rs.getInt("store_id"));
                goodsList.add(good);

            }
            return goodsList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Goods getGoodsById(Integer id){
        try {
            List<Goods> goods = substantialize(goodsDB.getById(id));

            Goods good = goods.get(0);
            return good;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void printGoodInDeal(Goods goods,Integer num){
        System.out.println("    商品ID："+goods.getId());
        System.out.println("    商品名称："+goods.getName());
        System.out.println("    商品价格："+goods.getPrice());
        System.out.println("    商品数量："+num);
        System.out.println("    销售商家："+petStoreDB.getStoreNameById(goods.getStoreId()));
    }

    public void addGood(PetStore petStore){
        System.out.println("商品名称：");
        String name = in.next();
        System.out.println("商品价格：");
        Double price = in.nextDouble();
        System.out.println("商品数量：");
        Integer number = in.nextInt();

        Goods good = new Goods();
        good.setName(name);
        good.setPrice(price);
        good.setNumber(number);
        good.setStoreId(petStore.getId());

        try {
            goodsDB.insert(good);
            System.out.println("新增商品成功!");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void addGoodNumberById(Integer id){
        Goods good = getGoodsById(id);
        System.out.println("添加库存数量：");
        Integer num = in.nextInt();
        good.setNumber(good.getNumber()+num);
        try {
            int i = goodsDB.update(good);
            if (i==1){
                System.out.println("添加成功！");
            }else {
                System.out.println("添加失败!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean checkAuthorization(Integer storeId,Integer goodId){
        Goods good = getGoodsById(goodId);
        if (!good.getStoreId().equals(storeId)){
            System.out.println("您无权操作此商品！");
            return false;
        }
        return true;
    }

    public void updateGoods(PetStore petStore){
        System.out.println("请输入要修改的商品ID：");
        Integer id = in.nextInt();

        if (!checkAuthorization(petStore.getId(), id)){
            return;
        }

        System.out.println("商品名称：");
        String name = in.next();
        System.out.println("商品价格：");
        Double price = in.nextDouble();
        System.out.println("商品数量：");
        Integer number = in.nextInt();

        Goods good = new Goods();
        good.setId(id);
        good.setName(name);
        good.setPrice(price);
        good.setNumber(number);
        good.setStoreId(petStore.getId());

        try {
            int i = goodsDB.update(good);
            if (i==1){
                System.out.println("修改成功！");
            }else {
                System.out.println("修改失败!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void deleteGood(PetStore petStore){

        System.out.println("请输入要删除的商品ID：");
        Integer id = in.nextInt();

        if (!checkAuthorization(petStore.getId(), id)){
            return;
        }
        try {
            goodsDB.deleteById(id);
            System.out.println("商品下架成功！");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public List<Goods> getAllGoods(){
        try {
            ResultSet rs = goodsDB.getAll();
            return substantialize(rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Goods> getGoodsByStoreId(Integer id){
        try {
            ResultSet rs = goodsDB.getGoodsByStoreId(id);
            return substantialize(rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void printGoodsByStoreId(PetStore petStore){
        for (Goods goods:getGoodsByStoreId(petStore.getId())){
            System.out.println("------------");
            System.out.println("    商品ID："+goods.getId());
            System.out.println("    商品名称："+goods.getName());
            System.out.println("    商品价格："+goods.getPrice());
            System.out.println("    商品库存："+goods.getNumber());
            System.out.println("------------");
        }
    }

    public void printAllGoods(){
        for (Goods goods:getAllGoods()){
            System.out.println("------------");
            System.out.println("    商品ID："+goods.getId());
            System.out.println("    商品名称："+goods.getName());
            System.out.println("    商品价格："+goods.getPrice());
            System.out.println("    商品库存："+goods.getNumber());
            System.out.println("    销售商家："+petStoreDB.getStoreNameById(goods.getId()));
            System.out.println("------------");
        }
    }

    public PetOwner buyingGoodsById(PetOwner petOwner,Integer id,Integer num){
        try {

            List<Goods> goodsList = substantialize(goodsDB.getById(id));
            Goods goods = goodsList.get(0);

            if (goods.getNumber()<1){
                System.out.println("商品库存不足，请等待商家添加库存！");
                return petOwner;
            }
            goods.setNumber(goods.getNumber()-num);
            if (petOwner.getBalance()-goods.getPrice()*num<0){
                System.out.println("余额不足！请充值");
                return petOwner;
            }
            petOwner.setBalance(petOwner.getBalance()-goods.getPrice()*num);
            PetStore petStore = petStoreDB.getById(goods.getStoreId());
            petStore.setBalance(petStore.getBalance()+goods.getPrice()*num);
            petStoreDB.update(petStore);
            goodsDB.update(goods);

            petOwnerDB.update(petOwner);
            Deal deal = new Deal();
            deal.setDealType(1);
            deal.setBuyerId(petOwner.getId());
            deal.setSellerId(goods.getStoreId());
            deal.setGoodsId(goods.getId());
            deal.setPrice(goods.getPrice()*num);
            deal.setAddress(petOwner.getAddress());
            deal.setPhone(petOwner.getPhone());
            deal.setNumber(num);
            dealDB.insert(deal);
            System.out.println("购买成功！");
            return petOwner;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return petOwner;
    }
}
