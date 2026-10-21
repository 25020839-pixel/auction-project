package com.auction.model;
import java.time.LocalDateTime;
import com.auction.enums.TransactionStatus;
import com.auction.model.Items.Item;

public class BidTransaction extends BaseEntity{
    private Auction auction;
    private Bidder bidder;
    private int bidAmount;
    private LocalDateTime timestamp;
    private TransactionStatus status;
    private String iPAdress;



    public boolean processTranscation(){
        Item itemProcess = this.auction.getItem();
        if (bidAmount > this.auction.getHighestBid()){
            this.markAsWinning();
            this.auction.setHighestBid(this);
        } else {
            markAsRejected();
        }
    }

    public void markAsOutbid(){
        this.status = TransactionStatus.OUTBID;
    }

    public void markAsWinning(){
        this.status = TransactionStatus.WINNING;
    }

    public void markAsRejected(){
        this.status = TransactionStatus.REJECTED;
    }
}
