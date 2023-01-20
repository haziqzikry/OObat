package project.oobat.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import project.oobat.Service.StockService;

@Controller
@RequestMapping("/admin/stock")
public class AdminStockController {

    @Autowired
    private StockService stockService;
    
    @GetMapping("/manage")
    public String manageStock() {
        return "managestock";
    }
}