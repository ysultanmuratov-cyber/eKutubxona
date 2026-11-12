package backend.model;

public abstract class DigitalAsset implements DRMProtected {
    private String title;
    private String author;
    private int readCount; // Inkapsulyatsiya qilingan o'qishlar soni

    public DigitalAsset(String title, String author, int readCount) {
        this.title = title;
        this.author = author;
        this.readCount = readCount;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getReadCount() {
        return readCount;
    }

    public void incrementReadCount() {
        this.readCount++;
    }

    public abstract void displayInfo();
}