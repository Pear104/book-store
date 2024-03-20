package models.image;

import java.sql.Timestamp;
import java.util.Date;

import org.jetbrains.annotations.NotNull;

public class ImageDTO {
	private final int imageId;
	private final int productId;
	private final String url;
	private final Date createdAt;

	public ImageDTO(int imageId, int productId, @NotNull String url) {
		this.imageId = imageId;
		this.productId = productId;
		this.url = url;
		this.createdAt = new java.sql.Date(new Timestamp(new Date().getTime()).getTime());
	}

	public int getImageId() {
		return imageId;
	}

	public int getProductId() {
		return productId;
	}

	public @NotNull String getUrl() {
		return url;
	}

	public @NotNull Date getCreatedAt() {
		return createdAt;
	}
	
	public String toString() {
		return url;
	}
}
