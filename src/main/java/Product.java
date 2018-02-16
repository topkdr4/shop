/**
 * Ветошкин А.В. РИС-16бзу
 * */




import java.util.Collections;
import java.util.List;





/**
 * Сущность продукта
 * */
public class Product {

    private String code;
    private String title;
    private String description;
    private String previewLink;
    private double price  = 0.0;
    private double rating = 0.0;
    private List<String> actions = Collections.emptyList();
    private List<String> fullSizeImages = Collections.emptyList();


    public String getCode() {
        return code;
    }


    public void setCode(String code) {
        this.code = code;
    }


    public String getTitle() {
        return title;
    }


    public void setTitle(String title) {
        this.title = title;
    }


    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }


    public String getPreviewLink() {
        return previewLink;
    }


    public void setPreviewLink(String previewLink) {
        this.previewLink = previewLink;
    }


    public double getPrice() {
        return price;
    }


    public void setPrice(double price) {
        this.price = price;
    }


    public double getRating() {
        return rating;
    }


    public void setRating(double rating) {
        this.rating = rating;
    }


    public List<String> getActions() {
        return actions;
    }


    public void setActions(List<String> actions) {
        this.actions = actions;
    }


    public List<String> getFullSizeImages() {
        return fullSizeImages;
    }


    public void setFullSizeImages(List<String> fullSizeImages) {
        this.fullSizeImages = fullSizeImages;
    }
}
