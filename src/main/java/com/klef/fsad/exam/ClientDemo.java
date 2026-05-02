package com.klef.fsad.exam;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.Date;

public class ClientDemo {

    private static SessionFactory factory;

    public static void main(String[] args) {
        try {
            factory = new Configuration().configure().buildSessionFactory();
            
            System.out.println("=== Inserting new CustomerAccount record ===");
            int customerId = insertCustomerAccount();
            
            System.out.println("\n=== Updating CustomerAccount record ===");
            updateCustomerAccount(customerId);
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (factory != null) {
                factory.close();
            }
        }
    }

    public static int insertCustomerAccount() {
        Session session = factory.openSession();
        Transaction transaction = null;
        int customerId = 0;
        
        try {
            transaction = session.beginTransaction();
            
            CustomerAccount account = new CustomerAccount();
            account.setName("John Doe");
            account.setDescription("Premium Customer Account");
            account.setDate(new Date());
            account.setStatus("Active");
            account.setEmail("john.doe@example.com");
            account.setPhoneNumber("9876543210");
            
            customerId = (int) session.save(account);
            transaction.commit();
            
            System.out.println("CustomerAccount inserted successfully with ID: " + customerId);
            System.out.println("Inserted record: " + account.toString());
            
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        
        return customerId;
    }

    public static void updateCustomerAccount(int id) {
        Session session = factory.openSession();
        Transaction transaction = null;
        
        try {
            transaction = session.beginTransaction();
            
            CustomerAccount account = session.get(CustomerAccount.class, id);
            
            if (account != null) {
                System.out.println("Before update: " + account.toString());
                
                account.setName("John Doe Updated");
                account.setStatus("Premium");
                
                session.update(account);
                transaction.commit();
                
                System.out.println("CustomerAccount updated successfully!");
                System.out.println("After update: " + account.toString());
            } else {
                System.out.println("CustomerAccount with ID " + id + " not found!");
            }
            
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
