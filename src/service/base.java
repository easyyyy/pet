package service;

import DBUtils.*;

import java.util.Scanner;

public class base {
    public static HospitalDB hospitalDB;
    public static MedicalCertificateDB medicalCertificateDB;
    public static MedicalCertificateService medicalCertificateService;

    public static PetStoreService petStoreService = new PetStoreService();
    public static GoodsService goodsService = new GoodsService();
    public static PetService petService = new PetService();
    public static DealService dealService = new DealService();
    public static HospitalService hospitalService = new HospitalService();
    public static PetOwnerDB petOwnerDB;

    public static PetDB petDB;
    public static DealDB dealDB;

    public static PetStoreDB petStoreDB;

    public static Scanner in = new Scanner(System.in);

    {
        try {
            medicalCertificateDB = new MedicalCertificateDB();
            medicalCertificateService = new MedicalCertificateService();
            petOwnerDB = new PetOwnerDB();

            hospitalDB = new HospitalDB();
            petDB = new PetDB();
            petService = new PetService();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            base base1 = new base();
            System.out.println(base1.medicalCertificateService.substantialize(medicalCertificateDB.getAllByOwnerId(3)));
        } catch (Exception e) {
            e.printStackTrace();
        };
    }
}
