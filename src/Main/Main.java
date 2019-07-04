package Main;

import Dao.Hospital;
import Dao.Pet;
import Dao.PetOwner;
import Dao.PetStore;
import service.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    PetOwnerService petOwnerService = new PetOwnerService();
    PetStoreService petStoreService = new PetStoreService();
    GoodsService goodsService = new GoodsService();
    PetService petService = new PetService();
    DealService dealService = new DealService();
    HospitalService hospitalService = new HospitalService();
    PetOwner loginPetOwner;
    PetStore loginPetStore;
    Hospital loginHospital;
    Scanner in = new Scanner(System.in);

    public void sellPet(){
        List<Pet> petList = petOwnerService.getPetByOwnerId(loginPetOwner.getId());
        petOwnerService.printListPets(petList);
    }

    public void buyingPet(){

        List<PetStore> petStoreList = petStoreService.getAllStore();
        List<Pet> petList = new ArrayList<>();

        for (PetStore petStore:petStoreList){
            System.out.println("");
            System.out.println("-------------");
            System.out.println("商店名："+petStore.getName());

            petList.addAll(petStoreService.printNotSellPet(petStore.getId()));
            System.out.println("-------------");
            System.out.println("");
        }

        while (true){
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
        System.out.println("购买商品数量:");
        Integer num = in.nextInt();
        goodsService.buyingGoodsById(loginPetOwner,op,num);

    }

    public void loginOwnerMenu(){
        boolean flag = true;
        while (flag){
            System.out.println("-----------------");
            System.out.println("1.查看自身信息");
            System.out.println("2.购买宠物");
            System.out.println("3.购买宠物用品");
            System.out.println("4.查看订单");
            System.out.println("5.余额充值");
            System.out.println("6.修改收货地址");
            System.out.println("7.修改联系电话");
            System.out.println("8.宠物就医");
            System.out.println("9.查看诊断单");
            System.out.println("10.退出");
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
                    try {
                        dealService.printListDeal(loginPetOwner, dealService.getDealByBuyerId(loginPetOwner.getId()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 5:
                    petOwnerService.changeBalance(loginPetOwner);
                    break;
                case 6:
                    loginPetOwner = petOwnerService.setAddress(loginPetOwner);
                    break;
                case 7:
                    loginPetOwner = petOwnerService.setPhone(loginPetOwner);
                    break;
                case 8:
                    petOwnerService.seekMedicalAttention(loginPetOwner);
                    break;
                case 9:
                    petOwnerService.printMedicalCertificate(loginPetOwner);
                    break;
                case 10:
                    flag=false;
                    break;
            }
        }
    }

    public void storeGoodsInfoMneu(){
        boolean flag = true;
        while (flag){
            System.out.println("1.上架商品");
            System.out.println("2.修改商品信息");
            System.out.println("3.删除商品");
            System.out.println("4.返回");
            int op = in.nextInt();
            switch (op){
                case 1:
                    goodsService.addGood(loginPetStore);
                    break;
                case 2:
                    goodsService.updateGoods(loginPetStore);
                    break;
                case 3:
                    goodsService.deleteGood(loginPetStore);
                    break;
                case 4:
                    flag = false;
                    break;
            }
        }
    }

    public void storePetInfoMenu(){

        boolean flag = true;
        while (flag){
            System.out.println("1.上架宠物");
            System.out.println("2.修改宠物信息");
            System.out.println("3.删除宠物");
            System.out.println("4.返回");
            int op = in.nextInt();
            switch (op){
                case 1:
                    petService.addPet(loginPetStore);break;
                case 2:
                    petService.updatePet(loginPetStore);
                    break;
                case 3:
                    petService.deletePet(loginPetStore);
                    break;
                case 4:
                    flag = false;
                    break;
            }
        }

    }




    public void loginStoreMenu(){
        boolean flag = true;
        while (flag) {
            System.out.println("-----------------");
            System.out.println("1.商店信息");
            System.out.println("2.宠物信息");
            System.out.println("3.宠物用品");
            System.out.println("4.查看订单");
            System.out.println("5.余额提现");
            System.out.println("6.退出");
            System.out.println("-----------------");

            int op = in.nextInt();
            switch (op){
                case 1:
                    petStoreService.printStoreInfo(loginPetStore);
                    break;
                case 2:
                    petStoreService.printNotSellPet(loginPetStore.getId());
                    storePetInfoMenu();
                    break;
                case 3:
                    goodsService.printGoodsByStoreId(loginPetStore);
                    storeGoodsInfoMneu();
                    break;
                case 4:
                    dealService.printStoreDeal(loginPetStore);
                    break;
                case 5:
                    loginPetStore = petStoreService.withDrawBalance(loginPetStore);
                    break;
                case 6:
                    flag = false;
                    break;
            }
        }
    }

    public void loginHospitalMenu(){
        boolean flag = true;
        while (flag){
            System.out.println("-----------------");
            System.out.println("1.医院信息");
            System.out.println("2.待诊断宠物");
            System.out.println("3.所有诊断单");

            System.out.println("4.退出");
            System.out.println("-----------------");

            int op = in.nextInt();
            switch (op){
                case 1:
                    hospitalService.printHospitalInfo(loginHospital);
                    break;
                case 2:
                    hospitalService.waitDiagnosis(loginHospital);
                    break;
                case 3:
                    hospitalService.allDiagnosis(loginHospital);
                    break;

                case 4:
                    flag = false;
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
            System.out.println("3.医院登陆");
            System.out.println("4.返回");
            System.out.println("-----------------");

            int op = in.nextInt();
            switch (op){
                case 1:

                    loginPetOwner = petOwnerService.loginByOwner();
                    if (loginPetOwner==null){
                        break;
                    }
                    loginOwnerMenu();
                    break;
                case 2:
                    loginPetStore = petStoreService.loginStore();
                    if (loginPetStore==null){
                        break;
                    }
                    loginStoreMenu();
                    break;
                case 3:
                    loginHospital = hospitalService.loginByHospital();
                    if (loginHospital==null){
                        break;
                    }
                    loginHospitalMenu();
                    break;
                case 4:
                    flag=false;
                    break;
            }
        }

    }

    public void registerOwnerOrStore(){
        boolean flag = true;
        while (flag){
            System.out.println("-----------------");
            System.out.println("1.用户注册");
            System.out.println("2.商店注册");
            System.out.println("3.医院注册");
            System.out.println("4.返回");
            System.out.println("-----------------");

            int op = in.nextInt();
            switch (op){
                case 1:
                    petOwnerService.registerOwner();
                    break;
                case 2:
                    petStoreService.registerStore();
                    break;
                case 3:
                    hospitalService.registerHospital();
                    break;
                case 4:
                    flag=false;
                    break;
            }
        }
    }

    public void loginSwitch(){

        boolean flag = true;
        while (flag){
            System.out.println("欢迎来到宠物商店系统");
            System.out.println("选择操作");
            System.out.println("1.登录");
            System.out.println("2.注册");
            System.out.println("3.退出");
            System.out.println("请输入操作数：");
            int op = in.nextInt();
            switch (op){
                case 1:
                    loginOwnerOrStore();
                    break;
                case 2:
                    registerOwnerOrStore();
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
