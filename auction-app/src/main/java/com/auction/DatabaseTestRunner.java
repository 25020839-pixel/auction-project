package com.auction;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.auction.enums.ItemStatus;
import com.auction.model.Items.Art;
import com.auction.model.Items.Electronics;
import com.auction.model.Items.Item;
import com.auction.model.Items.Vehicle;
import com.auction.model.Users.Seller;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class DatabaseTestRunner {
    public static void main(String[] args) {
        Map<String, Object> connectionProperties = Map.of(
                "jakarta.persistence.jdbc.url", requiredEnvironmentVariable("AUCTION_DB_URL"),
                "jakarta.persistence.jdbc.user", requiredEnvironmentVariable("AUCTION_DB_USER"),
                "jakarta.persistence.jdbc.password", requiredEnvironmentVariable("AUCTION_DB_PASSWORD"));

        EntityManagerFactory entityManagerFactory =
                Persistence.createEntityManagerFactory("auctionPU", connectionProperties);
        try {
            saveAndReadSampleItems(entityManagerFactory);
        } finally {
            entityManagerFactory.close();
        }
    }

    private static void saveAndReadSampleItems(EntityManagerFactory entityManagerFactory) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        String sellerId = UUID.randomUUID().toString();

        try {
            transaction.begin();

            Seller seller = new Seller(
                    sellerId,
                    "database-test-seller",
                    "test-only-password",
                    "database-test@example.com",
                    "Database Test Seller",
                    "000-000-0000",
                    5.0);
            entityManager.persist(seller);

            entityManager.persist(new Art(
                    UUID.randomUUID().toString(),
                    "Database Test Painting",
                    "Sample artwork created by DatabaseTestRunner",
                    ItemStatus.APPROVED,
                    seller,
                    "Test Artist"));
            entityManager.persist(new Electronics(
                    UUID.randomUUID().toString(),
                    "Database Test Laptop",
                    "Sample electronics item created by DatabaseTestRunner",
                    ItemStatus.APPROVED,
                    seller,
                    "Example Brand",
                    12));
            entityManager.persist(new Vehicle(
                    UUID.randomUUID().toString(),
                    "Database Test Car",
                    "Sample vehicle created by DatabaseTestRunner",
                    ItemStatus.APPROVED,
                    seller,
                    "Example Motors",
                    "Test Model",
                    2025));

            transaction.commit();
            entityManager.clear();

            List<Item> savedItems = entityManager.createQuery(
                            "select i from Item i where i.seller.id = :sellerId order by i.name",
                            Item.class)
                    .setParameter("sellerId", sellerId)
                    .getResultList();

            System.out.println("Saved seller:");
            System.out.println(seller.getInfo());
            System.out.println("\nItems read back from the database:");
            for (Item item : savedItems) {
                System.out.println(item.getClass().getSimpleName() + ": " + item.getName());
            }
            System.out.println("\nUse seller id " + sellerId
                    + " to inspect these records in your database client.");
        } catch (RuntimeException exception) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw exception;
        } finally {
            entityManager.close();
        }
    }

    private static String requiredEnvironmentVariable(String name) {
        String value = System.getenv(name);
        if (value == null || value.isBlank()) {
            throw new IllegalStateException(
                    "Set the " + name + " environment variable before running DatabaseTestRunner.");
        }
        return value;
    }
}
