package service;

import DBUtils.DealDB;
import DBUtils.PetStoreDB;
import Dao.Deal;
import Dao.Goods;
import Dao.PetOwner;
import Dao.PetStore;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DealService{

    DealDB dealDB;
    GoodsService goodsService;
    PetService petService;
    PetStoreDB petStoreDB;
    Scanner in = new Scanner(System.in);

    {
        try {
            dealDB = new DealDB();
            petStoreDB = new PetStoreDB();
            goodsService = new GoodsService();
            petService = new PetService();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Deal> substantialize(ResultSet rs){
        try {
            List<Deal> dealsList = new ArrayList<>();
            while (rs.next()){
                Deal deal = new Deal();
                deal.setId(rs.getInt("id"));
                deal.setDealType(rs.getInt("deal_type"));
                deal.setPetId(rs.getInt("pet_id"));
                deal.setPrice(rs.getDouble("price"));
                deal.setGoodsId(rs.getInt("goods_id"));
                deal.setSellerId(rs.getInt("seller_id"));
                deal.setBuyerId(rs.getInt("buyer_id"));
                deal.setAddress(rs.getString("address"));
                deal.setPhone(rs.getString("phone"));
                deal.setDealTime(rs.getDate("deal_time"));
                deal.setNumber(rs.getInt("number"));
                deal.setSign(rs.getInt("sign"));
                dealsList.add(deal);
            }
            return dealsList;
        } catch (Exception e) {

        }
        return null;
    }

    public List<Deal> getDealByBuyerId(Integer id){
        try {
            ResultSet rs = dealDB.getDealByBuyerId(id);
            List<Deal> dealList = substantialize(rs);
            return dealList;
        } catch (Exception e) {

        }
        return null;
    }

    public List<Deal> getDealByStoreId(Integer id){
        try {
            ResultSet rs = dealDB.getDealByStoreId(id);
            List<Deal> dealList = substantialize(rs);
            return dealList;
        } catch (Exception e) {

        }
        return null;
    }

    public void printStoreDeal(PetStore petStore){
        try {
            printListDeal(getDealByStoreId(petStore.getId()));
        } catch (Exception e) {

        }
    }

    public void printListDeal(List<Deal> dealList) throws Exception{
        for (Deal deal:dealList){

            System.out.println("");
            System.out.println("");
            System.out.println("-----------------");
            System.out.println("订单ID："+deal.getId());
            System.out.println("订单内容：");
            if (deal.getPetId()==0){
                goodsService.printGoodInDeal(goodsService.getGoodsById(deal.getGoodsId()),deal.getNumber());
            }
            if (deal.getGoodsId()==0){
                petService.printListPets(petService.getPetById(deal.getPetId()));
            }
            System.out.println("卖家："+petStoreDB.getStoreNameById(deal.getSellerId()));
            System.out.println("价格："+deal.getPrice());
            System.out.println("收货地址："+deal.getAddress());
            System.out.println("联系电话："+deal.getPhone());
            System.out.println("订单生成时间："+deal.getDealTime().getTime());
            if (deal.getSign()==0){
                System.out.println("未签收");
            }
            else {
                System.out.println("已签收");
            }
            System.out.println("-----------------");
            System.out.println("");
            System.out.println("");
        }
    }

    public void printListDeal(PetOwner petOwner, List<Deal> dealList) throws Exception{
        printListDeal(dealList);
        System.out.println("是否需要签收订单？（y/N）");
        String s = in.next();
        if (s.equals("y")){
            signDeal(petOwner);
        }else {
            return;
        }
    }

    public Deal getDealById(Integer id) throws Exception{
        ResultSet rs = dealDB.getDealById(id);
        Deal deal = substantialize(rs).get(0);
        return deal;
    }

    public void signDeal(PetOwner petOwner) throws Exception{
        System.out.println("请输入需要签收的订单ID：");
        Integer id = in.nextInt();
        Deal deal = getDealById(id);
        if (!deal.getBuyerId().equals(petOwner.getId())){
            System.out.println("您无权签收该订单！");
            return;
        }
        if (deal.getSign()==1){
            System.out.println("该订单已签收，请勿重复签收！");
            return;
        }
        deal.setSign(1);
        dealDB.update(deal);
        System.out.println("订单签收成功！");
    }
}
