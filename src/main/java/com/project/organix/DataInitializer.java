package com.project.organix;

import com.project.organix.model.KategoriSampah;
import com.project.organix.model.RewardItem;
import com.project.organix.model.TempatPembuangan;
import com.project.organix.model.User;
import com.project.organix.repository.KategoriSampahRepository;
import com.project.organix.repository.RewardItemRepository;
import com.project.organix.repository.TempatPembuanganRepository;
import com.project.organix.repository.UserRepository;
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

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TempatPembuanganRepository tempatPembuanganRepository;

    @Override
    public void run(String... args) {
        // Initialize Admin and User if empty
        if (userRepository.count() == 0) {
            User admin = new User();
            admin.setName("Admin Organix");
            admin.setEmail("admin@organix.com");
            admin.setPassword("admin123");
            admin.setRole("ADMIN");
            admin.setPoints(0);
            userRepository.save(admin);

            User user = new User();
            user.setName("User Organix");
            user.setEmail("user@organix.com");
            user.setPassword("user123");
            user.setRole("USER");
            user.setPoints(0);
            userRepository.save(user);

            System.out.println("Default Users created!");
        }

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

        // Initialize Tempat Pembuangan Sampah if empty
        if (tempatPembuanganRepository.count() == 0) {
            String[][] tpsData = {
                    { "TPS Pasar Minggu", "Jl. Raya Pasar Minggu No.1", "Jakarta Selatan" },
                    { "TPS Mampang Prapatan", "Jl. Mampang Prapatan Raya No.50", "Jakarta Selatan" },
                    { "TPS Tebet", "Jl. Tebet Raya No.10", "Jakarta Selatan" },
                    { "TPS Serpong", "Jl. Raya Serpong No.5", "Tangerang Selatan" },
                    { "TPS BSD City", "Jl. BSD Raya No.100", "Tangerang Selatan" },
                    { "TPS Bekasi Timur", "Jl. Ahmad Yani No.20", "Bekasi" },
                    { "TPS Depok Tengah", "Jl. Margonda Raya No.30", "Depok" },
                    { "TPS Bogor Kota", "Jl. Pajajaran No.15", "Bogor" },
                    { "TPS Bandung Pusat", "Jl. Asia Afrika No.10", "Jawa Barat" },
                    { "TPS Cirebon", "Jl. Siliwangi No.12", "Jawa Barat" },
                    { "TPS Tasikmalaya", "Jl. HZ Mustofa No.5", "Jawa Barat" }
            };
            for (String[] data : tpsData) {
                TempatPembuangan tps = new TempatPembuangan();
                tps.setNama(data[0]);
                tps.setAlamat(data[1]);
                tps.setKota(data[2]);
                tps.setStatus("AKTIF");
                tempatPembuanganRepository.save(tps);
            }
            System.out.println("Default Tempat Pembuangan created!");
        } else {
            boolean hasJabar = false;
            for (TempatPembuangan tps : tempatPembuanganRepository.findAll()) {
                if (tps.getNama().equals("TPS Bandung Pusat")) {
                    hasJabar = true;
                    break;
                }
            }
            if (!hasJabar) {
                String[][] jbData = {
                        { "TPS Bandung Pusat", "Jl. Asia Afrika No.10", "Jawa Barat" },
                        { "TPS Cirebon", "Jl. Siliwangi No.12", "Jawa Barat" },
                        { "TPS Tasikmalaya", "Jl. HZ Mustofa No.5", "Jawa Barat" }
                };
                for (String[] data : jbData) {
                    TempatPembuangan tps = new TempatPembuangan();
                    tps.setNama(data[0]);
                    tps.setAlamat(data[1]);
                    tps.setKota(data[2]);
                    tps.setStatus("AKTIF");
                    tempatPembuanganRepository.save(tps);
                }
                System.out.println("Jawa Barat TPS created!");
            }
        }
    }
}