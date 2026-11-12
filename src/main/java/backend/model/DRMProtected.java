package backend.model;

public interface DRMProtected {
    boolean validateLicense(); // Litsenziya haqiqiyligini tekshirish
    void enforceDRM();         // DRM himoya qulfini ishga tushirish
    void expireAccess();       // Obuna muddati tugaganda kirishni yopish
}