package gift.entity;

import gift.exception.BusinessException;
import gift.exception.ErrorCode;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private ProductName name;

    @Min(0)
    @Column(nullable = false)
    private int price;

    @NotBlank(message = "이미지 URL은 빈 칸일 수 없습니다.")
    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    protected Product() {
    }

    public Product(Long id, ProductName name, int price, String imageUrl) {
        validatePrice(price);
        validateImageUrl(imageUrl);
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public Product(ProductName name, int price, String imageUrl) {
        this(null, name, price, imageUrl);
    }

    public void update(ProductName name, int price, String imageUrl) {
        validatePrice(price);
        validateImageUrl(imageUrl);
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    private void validatePrice(int price) {
        if (price < 0) {
            throw new BusinessException(ErrorCode.INVALID_PRICE);
        }
    }

    private void validateImageUrl(String imageUrl) {
        if (imageUrl == null || imageUrl.trim().isEmpty()) {
            throw new BusinessException(ErrorCode.INVALID_IMAGE_URL);
        }
    }

    public Long getId() {
        return id;
    }

    public ProductName getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
