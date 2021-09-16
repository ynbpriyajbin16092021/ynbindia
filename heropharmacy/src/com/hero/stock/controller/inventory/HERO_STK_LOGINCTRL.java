package  com.hero.stock.controller.inventory;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.hero.stock.forms.login.HERO_STK_ILOGINDAO;
import  com.hero.stock.lov.HERO_STK_IINVENTORYLOV;
import  com.hero.stock.util.HEROHOSURINVENTORYUTIL;

@Controller
@SessionAttributes( {"uid"})
public class HERO_STK_LOGINCTRL {

	private static Logger log = Logger.getLogger(HERO_STK_LOGINCTRL.class);
	
	@Autowired
	private HERO_STK_ILOGINDAO loginDAO;
	@Autowired
	private HERO_STK_IINVENTORYLOV inventoryLOVobj;

	private Object ordercount;

	@RequestMapping("/login")
	public ModelAndView loginPremia() {
		return new ModelAndView("/forms/login/login", "message", "");
	}

	@RequestMapping(value = "/registration")
	public ModelAndView registerPremia() {
		/*
		 * List<LoginRequest> userList = loginDAO.fetchUsers(loginDAO);
		 * ModelAndView model = new ModelAndView();
		 * model.addObject("userList",userList);
		 * model.setViewName("/forms/login/registration"); return model;
		 */
		return new ModelAndView("/forms/login/registration", "message",
				"Success");

	}
	
	@RequestMapping(value="/dashboard")
	public ModelAndView gotoHomepage(@RequestParam(value = "uid", required = false) String uid,HttpServletRequest httpRequest)
	{
		ModelAndView model = new ModelAndView();
		
		model.setViewName("/forms/template/dashboard");
		  
		Map<String, Object> map = (Map<String, Object>) inventoryLOVobj.getDashboardDetails(httpRequest);
		log.info("map      "+map);
		
		String productCount = (String)HEROHOSURINVENTORYUTIL.getValueFromList(map, "productCount");
		String transfersCount = (String)HEROHOSURINVENTORYUTIL.getValueFromList(map, "transfersCount");
		String supplierCount = (String)HEROHOSURINVENTORYUTIL.getValueFromList(map, "supplierCount");
		String storeCount = (String)HEROHOSURINVENTORYUTIL.getValueFromList(map, "storeCount");
		String usersCount = (String)HEROHOSURINVENTORYUTIL.getValueFromList(map, "userCount");
		String customerCount = (String)HEROHOSURINVENTORYUTIL.getValueFromList(map, "customerCount");
		String purchaseAmountStr = (String)HEROHOSURINVENTORYUTIL.getValueFromList(map, "purchaseAmount");
		String salesAmountStr = (String)HEROHOSURINVENTORYUTIL.getValueFromList(map, "salesAmount");
		String customerDueStr = (String)HEROHOSURINVENTORYUTIL.getValueFromList(map, "customerDue");
		String purchaseCount = (String)HEROHOSURINVENTORYUTIL.getValueFromList(map, "purchaseCount");
		//String posCount = (String)HEROHOSURINVENTORYUTIL.getValueFromList(map, "posCount");
		String ordercount = (String)HEROHOSURINVENTORYUTIL.getValueFromList(map, "ordercount");
		String receivestockcount = (String)HEROHOSURINVENTORYUTIL.getValueFromList(map, "receivestockcount");
		
		
		
		Double salesAmount = 0.0,purchaseAmount = 0.0,customerDue = 0.0;
		
		if(purchaseAmountStr != null)
		{
			purchaseAmount = Double.parseDouble(purchaseAmountStr);	
		}
		
		if(customerDueStr != null)
		{
			customerDue = Double.parseDouble(customerDueStr);	
		}
		/*Double salesAmount = Double.parseDouble(salesAmountStr);*/
		
		if(salesAmountStr != null)
		{
			salesAmount = Double.parseDouble(salesAmountStr);
		}
		Double profitAmount = salesAmount - purchaseAmount;
		if(profitAmount < 0)
		{
			profitAmount = 0.0;
		}

		model.addObject("productCount",productCount);
		//model.addObject("posCount",posCount);
		model.addObject("transfersCount",transfersCount);
		model.addObject("purchaseCount",purchaseCount);
		model.addObject("supplierCount",supplierCount);
		model.addObject("storeCount",storeCount);
		model.addObject("usersCount",usersCount);
		model.addObject("customerCount",customerCount);
		model.addObject("ordercount",ordercount);
		model.addObject("receivestockcount",receivestockcount);

		
		
		
		BigDecimal bd = new BigDecimal(purchaseAmount);
		String formattedPurchaseAmount = HEROHOSURINVENTORYUTIL.IndianFormat(bd); 
		model.addObject("purchaseAmount",formattedPurchaseAmount);
		
		BigDecimal bd1 = new BigDecimal(salesAmount);
		String formattedSalesAmount = HEROHOSURINVENTORYUTIL.IndianFormat(bd1); 
		
		model.addObject("salesAmount",formattedSalesAmount);
		
		BigDecimal bd3 = new BigDecimal(customerDue);
		String formattedcustomerDue = HEROHOSURINVENTORYUTIL.IndianFormat(bd3); 
		model.addObject("customerDue",formattedcustomerDue);
		
		model.addObject("recentBillsList",map.get("recentBills"));
		
		model.addObject("recentTransferList",map.get("recentTransfers"));
		model.addObject("ordersList",map.get("ordersList"));
		model.addObject("purchaserecentList",map.get("purchaserecent"));
		model.addObject("topSellingProductList",map.get("topSellingProductList"));
		model.addObject("transferList",map.get("transfer"));
		model.addObject("productRecentList",map.get("productRecent"));
		model.addObject("lowstockList",map.get("lowstock"));
		model.addObject("orderList",map.get("order"));
		model.addObject("receivestock",map.get("receivestock"));
		
		if(profitAmount == 0){
			model.addObject("profitAmount",0.00);
		}
		else{
			BigDecimal bd2 = new BigDecimal(profitAmount);
			String formattedProfitAmount = HEROHOSURINVENTORYUTIL.IndianFormat(bd2); 
			model.addObject("profitAmount",formattedProfitAmount);
		}
		return model;
		
		/*return new ModelAndView("/forms/template/dashboard","message","success");*/
	}
}
