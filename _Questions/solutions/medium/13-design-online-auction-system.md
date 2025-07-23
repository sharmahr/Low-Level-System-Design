# Designing an Online Auction System

## Requirements
1. The online auction system should allow users to register and log in to their accounts.
2. Users should be able to create new auction listings with details such as item name, description, starting price, and auction duration.
3. Users should be able to browse and search for auction listings based on various criteria (e.g., item name, category, price range).
4. Users should be able to place bids on active auction listings.
5. The system should automatically update the current highest bid and notify the bidders accordingly.
6. The auction should end when the specified duration is reached, and the highest bidder should be declared the winner.
7. The system should handle concurrent access to auction listings and ensure data consistency.
8. The system should be extensible to accommodate future enhancements and new features.


```java
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class User {
    private String userId;
    private String name;
    private String email;

    // Constructor, getters, and setters
}

class AuctionListing {
    private String listingId;
    private String itemName;
    private String description;
    private double startingPrice;
    private double currentHighestBid;
    private String currentHighestBidder;
    private long endTime;

    // Constructor, getters, and setters
}

class Bid {
    private String bidId;
    private String listingId;
    private String bidderId;
    private double bidAmount;
    private long bidTime;

    // Constructor, getters, and setters
}

interface AuctionSearch {
    List<AuctionListing> searchListings(String keyword, String category, double minPrice, double maxPrice);
}

class SimpleAuctionSearch implements AuctionSearch {
    private List<AuctionListing> auctionListings;

    public SimpleAuctionSearch(List<AuctionListing> auctionListings) {
        this.auctionListings = auctionListings;
    }

    @Override
    public List<AuctionListing> searchListings(String keyword, String category, double minPrice, double maxPrice) {
        List<AuctionListing> results = new ArrayList<>();
        for (AuctionListing listing : auctionListings) {
            if (listing.getItemName().contains(keyword) &&
                    listing.getStartingPrice() >= minPrice &&
                    listing.getStartingPrice() <= maxPrice) {
                results.add(listing);
            }
        }
        return results;
    }
}

class AuctionManager {
    private List<AuctionListing> auctionListings;
    private List<Bid> bids;
    private Lock lock;

    public AuctionManager() {
        this.auctionListings = new ArrayList<>();
        this.bids = new ArrayList<>();
        this.lock = new ReentrantLock();
    }

    public void createAuctionListing(AuctionListing listing) {
        lock.lock();
        try {
            auctionListings.add(listing);
        } finally {
            lock.unlock();
        }
    }

    public List<AuctionListing> searchListings(AuctionSearch searchStrategy, String keyword, String category, double minPrice, double maxPrice) {
        return searchStrategy.searchListings(keyword, category, minPrice, maxPrice);
    }

    public void placeBid(Bid bid) {
        lock.lock();
        try {
            AuctionListing listing = findListingById(bid.getListingId());
            if (listing != null && bid.getBidAmount() > listing.getCurrentHighestBid()) {
                listing.setCurrentHighestBid(bid.getBidAmount());
                listing.setCurrentHighestBidder(bid.getBidderId());
                bids.add(bid);
                notifyBidders(listing);
            }
        } finally {
            lock.unlock();
        }
    }

    private AuctionListing findListingById(String listingId) {
        for (AuctionListing listing : auctionListings) {
            if (listing.getListingId().equals(listingId)) {
                return listing;
            }
        }
        return null;
    }

    private void notifyBidders(AuctionListing listing) {
        // Notify bidders about the updated highest bid
    }

    public void endAuction(String listingId) {
        lock.lock();
        try {
            AuctionListing listing = findListingById(listingId);
            if (listing != null && System.currentTimeMillis() >= listing.getEndTime()) {
                // Declare the highest bidder as the winner
                // Notify the winner and other bidders
                auctionListings.remove(listing);
            }
        } finally {
            lock.unlock();
        }
    }
}

public class OnlineAuctionSystem {
    public static void main(String[] args) {
        AuctionManager auctionManager = new AuctionManager();

        // Create auction listings
        AuctionListing listing1 = new AuctionListing("L001", "Item 1", "Description 1", 100.0, 0.0, "", System.currentTimeMillis() + 3600000);
        AuctionListing listing2 = new AuctionListing("L002", "Item 2", "Description 2", 200.0, 0.0, "", System.currentTimeMillis() + 7200000);
        auctionManager.createAuctionListing(listing1);
        auctionManager.createAuctionListing(listing2);

        // Search for auction listings
        AuctionSearch searchStrategy = new SimpleAuctionSearch(auctionManager.getAuctionListings());
        List<AuctionListing> searchResults = auctionManager.searchListings(searchStrategy, "Item", "", 50.0, 150.0);
        // Display search results

        // Place bids
        Bid bid1 = new Bid("B001", "L001", "U001", 110.0, System.currentTimeMillis());
        Bid bid2 = new Bid("B002", "L001", "U002", 120.0, System.currentTimeMillis());
        auctionManager.placeBid(bid1);
        auctionManager.placeBid(bid2);

        // End auction
        auctionManager.endAuction("L001");
    }
}
```

Explanation:
1. The code defines classes for different entities in the online auction system, such as `User`, `AuctionListing`, and `Bid`.

2. The `AuctionSearch` interface and its implementation `SimpleAuctionSearch` represent a strategy for searching auction listings based on various criteria. This allows for flexibility in implementing different search algorithms.

3. The `AuctionManager` class manages the core functionality of the auction system. It maintains lists of auction listings and bids, and provides methods for creating auction listings, searching listings, placing bids, and ending auctions. It uses a `ReentrantLock` to ensure thread safety when modifying the auction data.

4. The `placeBid` method in `AuctionManager` checks if the placed bid is higher than the current highest bid and updates the highest bid and bidder accordingly. It also notifies the bidders about the updated highest bid.

5. The `endAuction` method in `AuctionManager` checks if the specified auction duration has reached and declares the highest bidder as the winner. It also removes the ended auction listing from the list.

6. The `OnlineAuctionSystem` class contains the main method and demonstrates the usage of the auction system by creating auction listings, searching for listings, placing bids, and ending an auction.

Design Patterns Used:
- Strategy Pattern: The `AuctionSearch` interface and its implementation `SimpleAuctionSearch` follow the Strategy pattern. They encapsulate different search algorithms and allow for easy extensibility and interchangeability of search strategies.

- Singleton Pattern: The `AuctionManager` class can be implemented as a singleton to ensure that only one instance of the auction manager exists throughout the system. This allows for centralized management of auction data and ensures data consistency.

Thread Safety:
- The `AuctionManager` class uses `ReentrantLock` to ensure thread safety when accessing and modifying shared data (auction listings and bids). The lock is acquired before accessing or modifying the data and released after the operation is completed.

Extensibility:
- The use of interfaces and loose coupling between classes allows for easy extensibility of the system. For example, new search strategies can be added by implementing the `AuctionSearch` interface without modifying the existing code.

- The system can be further enhanced to include additional features such as user registration and authentication, auction categories, bidding history, and payment processing.