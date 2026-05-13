package com.project.organix.controller;

import com.project.organix.model.*;
import com.project.organix.repository.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class WebController {

    @Autowired private UserRepository userRepository;
    @Autowired private TransaksiRepository transaksiRepository;
    @Autowired private PointHasilRepository pointHasilRepository;
    @Autowired private RewardItemRepository rewardItemRepository;
    @Autowired private ComplaintRepository complaintRepository;
    @Autowired private KategoriSampahRepository kategoriSampahRepository;

    @ModelAttribute
    public void addCurrentUri(HttpServletRequest request, Model model) {
        model.addAttribute("currentUri", request.getRequestURI());
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("pageTitle", "Dashboard");
        model.addAttribute("totalUsers", userRepository.count());
        model.addAttribute("totalTransactions", transaksiRepository.count());
        model.addAttribute("totalPoints", userRepository.findAll().stream().mapToInt(User::getPoints).sum());
        model.addAttribute("totalRewards", rewardItemRepository.count());
        model.addAttribute("totalComplaints", complaintRepository.count());
        
        List<Transaksi> recentTransactions = transaksiRepository.findAll();
        recentTransactions.sort((a, b) -> b.getCreatedAt().compareTo(a.getCreatedAt()));
        if (recentTransactions.size() > 5) {
            recentTransactions = recentTransactions.subList(0, 5);
        }
        model.addAttribute("recentTransactions", recentTransactions);
        
        return "index";
    }

    @GetMapping("/users")
    public String users(Model model) {
        model.addAttribute("pageTitle", "Users");
        model.addAttribute("users", userRepository.findAll());
        return "users/list";
    }

    @GetMapping("/users/new")
    public String newUser(Model model) {
        model.addAttribute("pageTitle", "New User");
        model.addAttribute("user", new User());
        model.addAttribute("isEdit", false);
        return "users/form";
    }

    @GetMapping("/users/edit/{id}")
    public String editUser(@PathVariable Long id, Model model) {
        model.addAttribute("pageTitle", "Edit User");
        userRepository.findById(id).ifPresent(u -> model.addAttribute("user", u));
        model.addAttribute("isEdit", true);
        return "users/form";
    }

    @PostMapping("/users/save")
    public String saveUser(@ModelAttribute User user) {
        userRepository.save(user);
        return "redirect:/users?success";
    }

    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
        return "redirect:/users?deleted";
    }

    @GetMapping("/transactions")
    public String transactions(Model model) {
        model.addAttribute("pageTitle", "Transactions");
        model.addAttribute("transactions", transaksiRepository.findAll());
        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("kategori", kategoriSampahRepository.findAll());
        return "transactions/list";
    }

    @GetMapping("/transactions/new")
    public String newTransaction(Model model) {
        model.addAttribute("pageTitle", "New Transaction");
        model.addAttribute("transaction", new Transaksi());
        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("kategori", kategoriSampahRepository.findAll());
        return "transactions/form";
    }

    @PostMapping("/transactions/save")
    public String saveTransaction(@ModelAttribute Transaksi transaction) {
        if (transaction.getTotalPoints() == null) {
            transaction.setTotalPoints(BigDecimal.ZERO);
        }
        Transaksi saved = transaksiRepository.save(transaction);
        
        User user = userRepository.findById(transaction.getUserId()).orElse(null);
        if (user != null) {
            user.setPoints(user.getPoints() + transaction.getTotalPoints().intValue());
            userRepository.save(user);
            
            PointHasil ph = new PointHasil();
            ph.setUserId(user.getId());
            ph.setPoints(transaction.getTotalPoints());
            ph.setType("ADD");
            ph.setDescription("Transaction #" + saved.getId());
            pointHasilRepository.save(ph);
        }
        
        return "redirect:/transactions?success";
    }

    @GetMapping("/transactions/delete/{id}")
    public String deleteTransaction(@PathVariable Long id) {
        transaksiRepository.deleteById(id);
        return "redirect:/transactions?deleted";
    }

    @GetMapping("/points")
    public String points(Model model) {
        model.addAttribute("pageTitle", "Points Management");
        model.addAttribute("users", userRepository.findAll());
        return "points/history";
    }

    @GetMapping("/points/user/{userId}")
    public String userPoints(@PathVariable Long userId, Model model) {
        model.addAttribute("pageTitle", "Points History");
        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("userPoints", pointHasilRepository.findByUserIdOrderByCreatedAtDesc(userId));
        model.addAttribute("selectedUser", userRepository.findById(userId).orElse(null));
        return "points/history";
    }

    @PostMapping("/points/redeem")
    public String redeemPoints(@RequestParam Long userId, @RequestParam Long points, Model model) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null && user.getPoints() >= points.intValue()) {
            user.setPoints(user.getPoints() - points.intValue());
            userRepository.save(user);
            
            PointHasil ph = new PointHasil();
            ph.setUserId(userId);
            ph.setPoints(new BigDecimal(-points));
            ph.setType("DEDUCT");
            ph.setDescription("Manual point redemption");
            pointHasilRepository.save(ph);
            
            return "redirect:/points/user/" + userId + "?success";
        }
        return "redirect:/points?error";
    }

    @GetMapping("/rewards")
    public String rewards(Model model) {
        model.addAttribute("pageTitle", "Rewards");
        model.addAttribute("rewards", rewardItemRepository.findAll());
        model.addAttribute("users", userRepository.findAll());
        return "rewards/list";
    }

    @GetMapping("/rewards/new")
    public String newReward(Model model) {
        model.addAttribute("pageTitle", "New Reward");
        model.addAttribute("reward", new RewardItem());
        model.addAttribute("isEdit", false);
        return "rewards/form";
    }

    @GetMapping("/rewards/edit/{id}")
    public String editReward(@PathVariable Long id, Model model) {
        model.addAttribute("pageTitle", "Edit Reward");
        rewardItemRepository.findById(id).ifPresent(r -> model.addAttribute("reward", r));
        model.addAttribute("isEdit", true);
        return "rewards/form";
    }

    @PostMapping("/rewards/save")
    public String saveReward(@ModelAttribute("reward") RewardItem reward, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("pageTitle", reward.getId() != null ? "Edit Reward" : "New Reward");
            model.addAttribute("isEdit", reward.getId() != null);
            return "rewards/form";
        }
        rewardItemRepository.save(reward);
        return "redirect:/rewards?success";
    }

    @GetMapping("/rewards/delete/{id}")
    public String deleteReward(@PathVariable Long id) {
        rewardItemRepository.deleteById(id);
        return "redirect:/rewards?deleted";
    }

    @PostMapping("/rewards/redeem")
    public String redeemReward(@RequestParam Long userId, @RequestParam Long rewardId, @RequestParam(defaultValue = "1") int quantity) {
        User user = userRepository.findById(userId).orElse(null);
        RewardItem reward = rewardItemRepository.findById(rewardId).orElse(null);
        
        if (user != null && reward != null) {
            BigDecimal totalPoints = reward.getPriceInPoints().multiply(BigDecimal.valueOf(quantity));
            if (user.getPoints() >= totalPoints.intValue() && reward.getStock() >= quantity) {
                user.setPoints(user.getPoints() - totalPoints.intValue());
                userRepository.save(user);
                
                reward.setStock(reward.getStock() - quantity);
                rewardItemRepository.save(reward);
                
                PointHasil ph = new PointHasil();
                ph.setUserId(userId);
                ph.setPoints(totalPoints.negate());
                ph.setType("REDEEM");
                ph.setDescription("Redeem: " + reward.getName() + " x" + quantity);
                pointHasilRepository.save(ph);
                
                return "redirect:/rewards?success";
            }
        }
        return "redirect:/rewards?error";
    }

    @GetMapping("/complaints")
    public String complaints(Model model) {
        model.addAttribute("pageTitle", "Complaints");
        model.addAttribute("complaints", complaintRepository.findAll());
        return "complaints/list";
    }

    @PostMapping("/complaints/update/{id}")
    public String updateComplaint(@PathVariable Long id, @RequestParam String status) {
        complaintRepository.findById(id).ifPresent(c -> {
            c.setStatus(status);
            complaintRepository.save(c);
        });
        return "redirect:/complaints?updated";
    }

    @GetMapping("/complaints/delete/{id}")
    public String deleteComplaint(@PathVariable Long id) {
        complaintRepository.deleteById(id);
        return "redirect:/complaints?deleted";
    }

    @GetMapping("/kategori")
    public String kategori(Model model) {
        model.addAttribute("pageTitle", "Categories");
        model.addAttribute("kategori", kategoriSampahRepository.findAll());
        return "kategori/list";
    }

    @GetMapping("/kategori/new")
    public String newKategori(Model model) {
        model.addAttribute("pageTitle", "New Category");
        model.addAttribute("kategori", new KategoriSampah());
        model.addAttribute("isEdit", false);
        return "kategori/form";
    }

    @GetMapping("/kategori/edit/{id}")
    public String editKategori(@PathVariable Long id, Model model) {
        model.addAttribute("pageTitle", "Edit Category");
        kategoriSampahRepository.findById(id).ifPresent(k -> model.addAttribute("kategori", k));
        model.addAttribute("isEdit", true);
        return "kategori/form";
    }

    @PostMapping("/kategori/save")
    public String saveKategori(@ModelAttribute KategoriSampah kategori) {
        kategoriSampahRepository.save(kategori);
        return "redirect:/kategori?success";
    }

    @GetMapping("/kategori/delete/{id}")
    public String deleteKategori(@PathVariable Long id) {
        kategoriSampahRepository.deleteById(id);
        return "redirect:/kategori?deleted";
    }
}