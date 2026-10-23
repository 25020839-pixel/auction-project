package com.auction.model;
import com.auction.enums.AuctionStatus;
import com.auction.enums.TransactionStatus;
import com.auction.model.Items.Item;
import com.auction.enums.ItemStatus;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Auction extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    // Giới hạn gia hạn tối đa (phút)
    public static final int MAX_TIMELIMIT = 120;

    private String title;
    private Double startingPrice;
    private Double reservePrice;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private LocalDateTime originalEndTime;
    private AuctionStatus status;
    private Long version;
    private Item item;
    private List<BidTransaction> bidHistory;

    public Auction() {
        super();
        this.bidHistory = new ArrayList<>();
        this.status = AuctionStatus.DRAFT;
        this.version = 0L;
    }

    public Auction(String id, String title, Double startingPrice, Double reservePrice,
                   LocalDateTime startTime, LocalDateTime endTime, Item item) {
        super(id);
        this.title = title;
        this.startingPrice = startingPrice;
        this.reservePrice = reservePrice;
        this.startTime = startTime;
        this.endTime = endTime;
        this.originalEndTime = endTime;
        this.item = item;
        this.status = AuctionStatus.DRAFT;
        this.version = 0L;
        this.bidHistory = new ArrayList<>();
    }

    // Bắt đầu phiên đấu giá
    public synchronized void startAuction() {
        if (this.status == AuctionStatus.DRAFT) {
            this.status = AuctionStatus.ACTIVE;
            if (item != null) {
                item.updateStatus(ItemStatus.ACTIVE);
            }
        }
    }

    // Kết thúc phiên đấu giá
    public synchronized void endAuction() {
        if (this.status == AuctionStatus.CLOSED || this.status == AuctionStatus.CANCELLED) {
            return;
        }

        this.status = AuctionStatus.CLOSED;
        BidTransaction winBid = getHighestBid();

        if (winBid != null) {
            // Kiểm tra có đạt giá sàn không
            if (reservePrice == null || winBid.getBidAmount() >= reservePrice) {
                winBid.markAsWinning();
                if (item != null) {
                    item.updateStatus(ItemStatus.SOLD);
                }
            } else {
                if (item != null) {
                    item.updateStatus(ItemStatus.UNSOLD);
                }
            }
        }
    }

    // Hủy phiên đấu giá
    public synchronized void cancelAuction(String reason) {
        this.status = AuctionStatus.CANCELLED;
        if (item != null) {
            item.updateStatus(ItemStatus.CANCELLED);
        }
        System.out.println("Hủy đấu giá: " + reason);
    }

    // Kiểm tra giá đặt có hợp lệ không
    public boolean validateBid(Double amount) {
        if (amount == null) return false;

        // Phải ở trạng thái ACTIVE và trong thời gian diễn ra
        LocalDateTime now = LocalDateTime.now();
        if (status != AuctionStatus.ACTIVE || now.isBefore(startTime) || now.isAfter(endTime)) {
            return false;
        }

        BidTransaction highest = getHighestBid();
        if (highest == null) {
            return amount >= startingPrice;
        }
        return amount > highest.getBidAmount();
    }

    // Đặt giá
    public synchronized BidTransaction placeBid(String bidderId, Double amount, String ipAddress) {
        if (!validateBid(amount)) {
            return null; // trả về null nếu giá không hợp lệ
        }

        // Tạo giao dịch mới
        BidTransaction newBid = new BidTransaction(this.id, bidderId, amount, LocalDateTime.now(), ipAddress);
        boolean ok = newBid.processTransaction();
        if (!ok) {
            return null;
        }

        // Đổi trạng thái bid cao nhất trước đó thành OUTBID
        BidTransaction prevHighest = getHighestBid();
        if (prevHighest != null) {
            prevHighest.markAsOutbid();
        }

        bidHistory.add(newBid);
        this.version++;

        // Anti-sniping: nếu bid trong 2 phút cuối thì cộng thêm 3 phút
        LocalDateTime now = LocalDateTime.now();
        if (now.isAfter(endTime.minusMinutes(2))) {
            extendTime(3);
        }

        return newBid;
    }

    // Gia hạn phiên đấu giá
    public synchronized void extendTime(int extraMinutes) {
        LocalDateTime newEnd = this.endTime.plusMinutes(extraMinutes);
        LocalDateTime maxEnd = this.originalEndTime.plusMinutes(MAX_TIMELIMIT);

        if (newEnd.isAfter(maxEnd)) {
            this.endTime = maxEnd;
        } else {
            this.endTime = newEnd;
        }
    }

    // Tìm bid cao nhất hiện tại
    public BidTransaction getHighestBid() {
        for (int i = bidHistory.size() - 1; i >= 0; i--) {
            BidTransaction b = bidHistory.get(i);
            if (b.getStatus() == TransactionStatus.ACCEPTED || b.getStatus() == TransactionStatus.WINNING) {
                return b;
            }
        }
        return null;
    }

    public List<BidTransaction> getBidHistory() {
        return bidHistory;
    }

    @Override
    public String getInfo() {
        BidTransaction highest = getHighestBid();
        return "Auction ID: " + getID() + ", Title: " + title + ", Status: " + status +
                ", Highest Bid: " + (highest != null ? highest.getBidAmount() : "None");
    }

    // Getters & Setters thông thường
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public Double getStartingPrice() { return startingPrice; }
    public void setStartingPrice(Double startingPrice) { this.startingPrice = startingPrice; }

    public Double getReservePrice() { return reservePrice; }
    public void setReservePrice(Double reservePrice) { this.reservePrice = reservePrice; }

    public LocalDateTime getStartTime() { return startTime; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }

    public LocalDateTime getEndTime() { return endTime; }
    public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }

    public AuctionStatus getStatus() { return status; }
    public void setStatus(AuctionStatus status) { this.status = status; }

    public Long getVersion() { return version; }

    public Item getItem() { return item; }
    public void setItem(Item item) { this.item = item; }
}