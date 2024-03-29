package project.oobat.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import project.oobat.Model.AppUser;
import project.oobat.Service.AppUserService;

// import project.oobat.Repository.AppUserRepository;

@Controller
@RequestMapping("/")
public class AuthController {
    
    @Autowired
    private AppUserService appUserService;

    @GetMapping("/login")
    public String login() {
        return "misc/login";
    }

    // @PostMapping("/login")
    // public String loginSubmit() {
    //     System.out.println("login thru post");
    //     return "login";
    // }

    @GetMapping("/register")
    public String register(Model model) {
        AppUser appUser = new AppUser();
        model.addAttribute("user", appUser);
        return "user/register";
    }

    @GetMapping("/register-admin")
    public String registerAdmin(Model model) {
        AppUser appUser = new AppUser();
        model.addAttribute("user", appUser);
        return "admin/register";
    }

    @PostMapping("/register")
    public String registerSubmit(AppUser appUser, Model model, BindingResult bindingResult) {
        
        if (bindingResult.hasErrors()) {
            model.addAttribute("user", appUser);
            model.addAttribute("successMessage", "User registered successfully!");
            model.addAttribute("bindingResult", bindingResult);
            return "user/register";
        }
        List<Object> userExist = appUserService.isUserExist(appUser.getUsername());
        if ((boolean) userExist.get(0)) {
            model.addAttribute("user", appUser);
            model.addAttribute("userExist", userExist.get(1));
            return "user/register";
        }
        //to register customer
        appUserService.registerCustomer(appUser);

        //to register pharmacist
        // appUserService.registerPharmacist(appUser);
        model.addAttribute("user", appUser);

        model.addAttribute("successMessage", "User registered successfully!");
        return "user/register";
    }

    @PostMapping("/register-admin")
    public String registerAdminSubmit(AppUser appUser, Model model, BindingResult bindingResult) {
        
        if (bindingResult.hasErrors()) {
            model.addAttribute("user", appUser);
            model.addAttribute("successMessage", "User registered successfully!");
            model.addAttribute("bindingResult", bindingResult);
            return "admin/register";
        }
        List<Object> userExist = appUserService.isUserExist(appUser.getUsername());
        if ((boolean) userExist.get(0)) {
            model.addAttribute("user", appUser);
            model.addAttribute("userExist", userExist.get(1));
            return "admin/register";
        }
        //to register customer
        // appUserService.registerCustomer(appUser);

        //to register pharmacist
        appUserService.registerPharmacist(appUser);
        model.addAttribute("user", appUser);

        model.addAttribute("successMessage", "User registered successfully!");
        return "admin/register";
    }
    

}
