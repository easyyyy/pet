package Main;

import Dao.Pet;
import Dao.PetOwner;
import Dao.PetStore;
import service.GoodsService;
import service.PetOwnerService;
import service.PetService;
import service.PetStoreService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    PetOwnerService petOwnerService = new PetOwnerService();
    PetStoreService petStoreService = new PetStoreService();
    GoodsService goodsService = new GoodsService();
    PetService petService = new PetService();
    PetOwner loginPetOwner;
    Scanner in = new Scanner(System.in);

    public void sellPet(){
        List<Pet> petList = petOwnerService.getPetByOwnerId(loginPetOwner.getId());
        petOwnerService.printListPets(petList);
    }

    public void buyingPet(){

        List<PetStore> petStoreList = petStoreService.getAllStore();
        List<Pet> petList = new ArrayList<>();
        while (true){
            for (PetStore petStore:petStoreList){
                System.out.println("-------------");
                System.out.println("商店名："+petStore.getName());

                petList.addAll(petStoreService.printNotSellPet(petStore.getId()));
                System.out.println("-------------");
            }
            System.out.println("请输入要购买的宠物ID或输入0退出");
            int op = in.nextInt();
            if (op==0){
                break;
            }
            petOwnerService.buyingPet(loginPetOwner,petService.getByIdFromList(petList,op));
        }
    }

    public void buyingGoods(){
        goodsService.printAllGoods();
        System.out.println("是否要购买商品，需要购买输入商品id，不需要输入0退出");
        int op = in.nextInt();
        if (op==0){
            return;
        }
        goodsService.buyingGoodsById(loginPetOwner,op);
    }

    public void loginOwnerMenu(){
        boolean flag = true;
        while (flag){
            System.out.println("-----------------");
            System.out.println("1.查看自身信息");
            System.out.println("2.购买宠物");
            System.out.println("3.购买宠物用品");
            System.out.println("4.出售宠物");
            System.out.println("5.余额充值");
            System.out.println("6.退出");
            System.out.println("-----------------");

            int op = in.nextInt();
            switch (op){
                case 1:
                    petOwnerService.getInfoById(loginPetOwner.getId());
                    break;
                case 2:
                    buyingPet();
                    break;
                case 3:
                    buyingGoods();
                    break;
                case 4:
                    sellPet();
                    break;
                case 5:
                    petOwnerService.changeBalance(loginPetOwner);
                    break;
                case 6:
                    flag=false;
                    break;
            }
        }
    }

    public void loginOwnerOrStore(){
        boolean flag = true;
        while (flag){
            System.out.println("-----------------");
            System.out.println("1.用户登录");
            System.out.println("2.商店登录");
            System.out.println("3.返回");
            System.out.println("-----------------");

            int op = in.nextInt();
            switch (op){
                case 1:
                    loginPetOwner = petOwnerService.loginByOwner();
                    loginOwnerMenu();
                    break;
                case 2:
                    break;
                case 3:
                    flag=false;
                    break;
            }
        }

    }

    public void loginSwitch(){

        boolean flag = true;
        while (flag){
            System.out.println("欢迎来到宠物商店系统");
            System.out.println("请先登录");
            System.out.println("1.登录");
            System.out.println("2.注册");
            System.out.println("3.退出");

            int op = in.nextInt();
            switch (op){
                case 1:
                    loginOwnerOrStore();
                    break;
                case 2:
                    break;
                case 3:
                    flag = false;
                    break;
            }

        }

    }

    public static void main(String[] args) {
        Main main = new Main();
        main.loginSwitch();
    }
}
