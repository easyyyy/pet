package service;

import DBUtils.PetDB;
import Dao.Pet;

import java.sql.ResultSet;
import java.util.List;

public class PetService {
    PetDB petDB;

    {
        try {
            petDB = new PetDB();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Pet getByIdFromList(List<Pet> petList, Integer id){
        for (Pet pet:petList){
            if (pet.getId()==id){
                return pet;
            }
        }
        return null;
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
