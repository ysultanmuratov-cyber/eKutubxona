package backend.model;

public class EBook extends DigitalAsset {
    private int fileSizeMB;

    public EBook(String title, String author, int readCount, int fileSizeMB) {
        super(title, author, readCount);
        this.fileSizeMB = fileSizeMB;
    }

    @Override
    public boolean validateLicense() {
        return true;
    }

    @Override
    public void enforceDRM() {
        System.out.println("DRM himoyasi yoqildi: " + getTitle());
    }

    @Override
    public void expireAccess() {
        System.out.println("Muddat tugadi: " + getTitle());
    }

    @Override
    public void displayInfo() {
        System.out.println("E-Kitob: " + getTitle() + " - " + getAuthor());
    }
}