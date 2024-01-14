package si.ape.orchestration.lib.requests.parcels;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

public class CreateParcelRequest {

    @Schema(description = "Weight of the parcel.", example = "6.9", required = true)
    private Double weight;

    @Schema(description = "Width of the parcel.", example = "10", required = true)
    private Integer width;

    @Schema(description = "Height of the parcel.", example = "10", required = true)
    private Integer height;

    @Schema(description = "Depth of the parcel.", example = "10", required = true)
    private Integer depth;

    @Schema(description = "Sender ID of the parcel The Customer (not User) ID.", example = "1", required = true)
    private Integer senderId;

    @Schema(description = "Recipient ID of the parcel. The Customer (not User) ID.", example = "2", required = true)
    private Integer recipientId;

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getDepth() {
        return depth;
    }

    public void setDepth(Integer depth) {
        this.depth = depth;
    }

    public Integer getSenderId() {
        return senderId;
    }

    public void setSenderId(Integer senderId) {
        this.senderId = senderId;
    }

    public Integer getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(Integer recipientId) {
        this.recipientId = recipientId;
    }

}
