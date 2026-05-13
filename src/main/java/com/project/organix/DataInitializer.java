package com.project.organix;

import com.project.organix.model.KategoriSampah;
import com.project.organix.model.RewardItem;
import com.project.organix.repository.KategoriSampahRepository;
import com.project.organix.repository.RewardItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private KategoriSampahRepository kategoriSampahRepository;

    @Autowired
    private RewardItemRepository rewardItemRepository;

    @Override
    public void run(String... args) {
        // Initialize Kategori Sampah if empty
        if (kategoriSampahRepository.count() == 0) {
            KategoriSampah plastic = new KategoriSampah();
            plastic.setName("Plastic");
            plastic.setDescription("Plastic bottles, containers, and packaging");
            plastic.setPointsPerKg(150);
            kategoriSampahRepository.save(plastic);

            KategoriSampah paper = new KategoriSampah();
            paper.setName("Paper");
            paper.setDescription("Newspapers, cardboard, paper boxes");
            paper.setPointsPerKg(100);
            kategoriSampahRepository.save(paper);

            KategoriSampah glass = new KategoriSampah();
            glass.setName("Glass");
            glass.setDescription("Glass bottles and jars");
            glass.setPointsPerKg(200);
            kategoriSampahRepository.save(glass);

            KategoriSampah metal = new KategoriSampah();
            metal.setName("Metal");
            metal.setDescription("Aluminum cans, metal containers");
            metal.setPointsPerKg(250);
            kategoriSampahRepository.save(metal);

            KategoriSampah organic = new KategoriSampah();
            organic.setName("Organic");
            organic.setDescription("Food waste, garden waste");
            organic.setPointsPerKg(50);
            kategoriSampahRepository.save(organic);

            System.out.println("Default Kategori Sampah created!");
        }

        // Initialize Reward Items if empty
        if (rewardItemRepository.count() == 0) {
            RewardItem toteBag = new RewardItem();
            toteBag.setName("Eco Tote Bag");
            toteBag.setDescription("Reusable canvas tote bag");
            toteBag.setPriceInPoints(BigDecimal.valueOf(500));
            toteBag.setStock(20);
            rewardItemRepository.save(toteBag);

            RewardItem bottle = new RewardItem();
            bottle.setName("Water Bottle");
            bottle.setDescription("Stainless steel water bottle");
            bottle.setPriceInPoints(BigDecimal.valueOf(1500));
            bottle.setStock(15);
            rewardItemRepository.save(bottle);

            RewardItem badge = new RewardItem();
            badge.setName("Eco Badge");
            badge.setDescription("Recycling ambassador badge");
            badge.setPriceInPoints(BigDecimal.valueOf(200));
            badge.setStock(50);
            rewardItemRepository.save(badge);

            RewardItem umbrella = new RewardItem();
            umbrella.setName("Eco Umbrella");
            umbrella.setDescription("Eco-friendly umbrella");
            umbrella.setPriceInPoints(BigDecimal.valueOf(3000));
            umbrella.setStock(10);
            rewardItemRepository.save(umbrella);

            System.out.println("Default Reward Items created!");
        }
    }
}