package service;

import DBUtils.GoodsDB;
import DBUtils.PetOwnerDB;
import DBUtils.PetStoreDB;
import Dao.Goods;
import Dao.PetOwner;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class GoodsService {
    GoodsDB goodsDB;
    PetStoreDB petStoreDB;
    PetOwnerDB petOwnerDB;

    {
        try {
            goodsDB = new GoodsDB();
            petStoreDB = new PetStoreDB();
            petOwnerDB = new PetOwnerDB();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Goods> substantialize(ResultSet rs){
        try {
            List<Goods> goodsList = new ArrayList<>();
            while (rs.next()){
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

    public PetOwner buyingGoodsById(PetOwner petOwner,Integer id){
        try {
            List<Goods> goodsList = substantialize(goodsDB.getById(id));
            Goods goods = goodsList.get(0);
            goods.setNumber(goods.getNumber()-1);
            petOwner.setBalance(petOwner.getBalance()-goods.getPrice());
            goodsDB.update(goods);
            petOwnerDB.update(petOwner);
            return petOwner;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return petOwner;
    }
}
