package com.project.organix.controller;

import com.project.organix.model.Complaint;
import com.project.organix.model.User;
import com.project.organix.repository.ComplaintRepository;
import com.project.organix.repository.PointHasilRepository;
import com.project.organix.repository.RewardItemRepository;
import com.project.organix.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserWebController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PointHasilRepository pointHasilRepository;

    @Autowired
    private RewardItemRepository rewardItemRepository;

    @Autowired
    private ComplaintRepository complaintRepository;

    @ModelAttribute
    public void addCurrentUri(HttpServletRequest request, Model model) {
        model.addAttribute("currentUri", request.getRequestURI());
    }

    private Long getUserId(HttpSession session) {
        return (Long) session.getAttribute("userId");
    }

    private User getCurrentUser(HttpSession session) {
        Long userId = getUserId(session);
        if (userId == null) {
            return null;
        }
        return userRepository.findById(userId).orElse(null);
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        User user = getCurrentUser(session);
        if (user == null) return "redirect:/login";
        Long userId = user.getId();

        // Stats for the dashboard
        long complaintCount = complaintRepository.findByUserId(userId).size();
        long openComplaintCount = complaintRepository.findByUserId(userId).stream()
                .filter(c -> "OPEN".equalsIgnoreCase(c.getStatus())).count();

        // Recent 5 point transactions
        java.util.List<com.project.organix.model.PointHasil> recentPoints =
                pointHasilRepository.findByUserIdOrderByCreatedAtDesc(userId);
        if (recentPoints.size() > 5) {
            recentPoints = recentPoints.subList(0, 5);
        }

        long availableRewards = rewardItemRepository.findAll().stream()
                .filter(r -> r.getStock() > 0).count();

        model.addAttribute("pageTitle", "My Dashboard");
        model.addAttribute("user", user);
        model.addAttribute("complaintCount", complaintCount);
        model.addAttribute("openComplaintCount", openComplaintCount);
        model.addAttribute("recentPoints", recentPoints);
        model.addAttribute("availableRewards", availableRewards);
        return "user/dashboard";
    }

    @GetMapping("/points")
    public String myPoints(HttpSession session, Model model) {
        User user = getCurrentUser(session);
        if (user == null) return "redirect:/login";

        model.addAttribute("pageTitle", "My Points History");
        model.addAttribute("userPoints", pointHasilRepository.findByUserIdOrderByCreatedAtDesc(user.getId()));
        model.addAttribute("totalPoints", user.getPoints());
        return "user/points";
    }

    @GetMapping("/rewards")
    public String rewards(HttpSession session, Model model) {
        User user = getCurrentUser(session);
        if (user == null) return "redirect:/login";

        model.addAttribute("pageTitle", "Redeem Rewards");
        model.addAttribute("rewards", rewardItemRepository.findAll());
        model.addAttribute("totalPoints", user.getPoints());
        return "user/rewards";
    }

    @PostMapping("/rewards/redeem")
    public String redeemReward(@RequestParam Long rewardId, @RequestParam(defaultValue = "1") int quantity, HttpSession session) {
        Long userId = getUserId(session);
        User user = userRepository.findById(userId).orElse(null);
        var rewardOpt = rewardItemRepository.findById(rewardId);
        
        if (user != null && rewardOpt.isPresent()) {
            var reward = rewardOpt.get();
            java.math.BigDecimal totalPoints = reward.getPriceInPoints().multiply(java.math.BigDecimal.valueOf(quantity));
            if (user.getPoints() >= totalPoints.intValue() && reward.getStock() >= quantity) {
                user.setPoints(user.getPoints() - totalPoints.intValue());
                userRepository.save(user);
                
                reward.setStock(reward.getStock() - quantity);
                rewardItemRepository.save(reward);
                
                com.project.organix.model.PointHasil ph = new com.project.organix.model.PointHasil();
                ph.setUserId(userId);
                ph.setPoints(totalPoints.negate());
                ph.setType("REDEEM");
                ph.setDescription("Redeem: " + reward.getName() + " x" + quantity);
                pointHasilRepository.save(ph);
                
                return "redirect:/user/rewards?success";
            }
        }
        return "redirect:/user/rewards?error";
    }

    @GetMapping("/complaints")
    public String complaints(HttpSession session, Model model) {
        Long userId = getUserId(session);
        if (userId == null) return "redirect:/login";

        model.addAttribute("pageTitle", "My Complaints");
        model.addAttribute("complaints", complaintRepository.findByUserId(userId));
        return "user/complaints";
    }

    @GetMapping("/complaints/new")
    public String newComplaint(Model model) {
        model.addAttribute("pageTitle", "New Complaint");
        model.addAttribute("complaint", new Complaint());
        return "user/complaint_form";
    }

    @PostMapping("/complaints/save")
    public String saveComplaint(@ModelAttribute Complaint complaint, HttpSession session) {
        Long userId = getUserId(session);
        if (userId == null) return "redirect:/login";

        complaint.setUserId(userId);
        if (complaint.getCategory() == null || complaint.getCategory().isBlank()) {
            complaint.setCategory("GENERAL");
        }
        if (complaint.getPriority() == null || complaint.getPriority().isBlank()) {
            complaint.setPriority("MEDIUM");
        }
        complaint.setStatus("OPEN");
        complaintRepository.save(complaint);
        return "redirect:/user/complaints?success";
    }
}
