package com.hero.reports.controller.stock;
 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.hero.heroutils.HERO_UTILS;
import com.hero.reports.util.HERO_RTS_INVENTORYDAO;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/report/")
public class HERO_RTS_STK_REPORTCTRL extends HERO_RTS_INVENTORYDAO {

    private static final Logger log = LoggerFactory.getLogger(HERO_RTS_STK_REPORTCTRL.class);
 

    @RequestMapping(method = RequestMethod.GET , value = "purchaseorderschedule/{purchaseid}/{billno}")
    public ModelAndView generatePurchaseScheduleReport(ModelAndView modelAndView,
    		@PathVariable("purchaseid") String purchaseid,
    		@PathVariable("billno") String billno,HttpServletRequest httpRequest){

        log.info("--------------generate PDF report for purchaseid----------"+purchaseid);

        try
        {
        
        Map<String,Object> parameterMap = new HashMap<String,Object>();
       // String imageURL = ("http://localhost:8080/heroadmin/").concat("forms/viewimages");
        String imageURL = HERO_UTILS.getApplicationSontext(httpRequest)+"/heroadmin/".concat("forms/HeroImageView?index=0");
        /*String imageURL = (String.format("%s://%s:%d"+httpRequest.getContextPath()+"/",httpRequest.getScheme(),  httpRequest.getServerName(), httpRequest.getServerPort())).concat("forms/viewimages");*/
        parameterMap.put("datasource", jdbcTemplate.getDataSource());
        parameterMap.put("P_PURCHASE_ID", purchaseid);
        parameterMap.put("P_BILL_NO", billno);
        parameterMap.put("logo", imageURL);
        parameterMap.put("P_LOGINED_USER", httpRequest.getSession().getAttribute("uid"));
        /*parameterMap.put("P_LOGINED_USER",'1');*/
        log.info("parameterMap   print  "+parameterMap);
        modelAndView = new ModelAndView("purchasescheduleReport", parameterMap);
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        }
        return modelAndView;

    }
    
    @RequestMapping(method = RequestMethod.GET , value = "stocktransferschedule/{transferid}")
    public ModelAndView generateStockTransferScheduleReport(ModelAndView modelAndView,
    		@PathVariable("transferid") String transferid,HttpServletRequest httpRequest){

        log.info("--------------generate PDF report for transferid----------"+transferid);

        try
        {
        
        /*String imageURL = (String.format("%s://%s:%d"+httpRequest.getContextPath()+"/",httpRequest.getScheme(),  httpRequest.getServerName(), httpRequest.getServerPort())).concat("forms/viewimages");*/	
       /* String imageURL = ("http://localhost:8080/heroadmin/").concat("forms/viewimages");	*/
        	String imageURL = HERO_UTILS.getApplicationSontext(httpRequest)+"/heroadmin/".concat("forms/HeroImageView?index=0");
        Map<String,Object> parameterMap = new HashMap<String,Object>();
        
        parameterMap.put("datasource", jdbcTemplate.getDataSource());
        //parameterMap.put("P_TXR_ID", transferid);
        parameterMap.put("P_PURCHASE_ID", transferid);
        parameterMap.put("P_BILL_NO", transferid);
        parameterMap.put("logo", imageURL);
        parameterMap.put("P_LOGINED_USER", httpRequest.getSession().getAttribute("uid"));

        modelAndView = new ModelAndView("stocktransferscheduleReport", parameterMap);
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        }
        return modelAndView;

    }
    
    @RequestMapping(method = RequestMethod.GET , value = "invoiceschedule/{posid}")
    public ModelAndView generateInvoiceScheduleReport(ModelAndView modelAndView,
    		@PathVariable("posid") String posid,HttpServletRequest httpRequest){

        log.info("--------------generate PDF report for posid----------"+posid);

        try
        {
        	
        
        /*String imageURL = (String.format("%s://%s:%d"+httpRequest.getContextPath()+"/",httpRequest.getScheme(),  httpRequest.getServerName(), httpRequest.getServerPort())).concat("forms/viewimages");*/	
       /* String imageURL = ("http://localhost:8080/heroadmin/").concat("forms/viewimages");*/
        	String imageURL = HERO_UTILS.getApplicationSontext(httpRequest)+"/heroadmin/".concat("forms/HeroImageView?index=0");
        Map<String,Object> parameterMap = new HashMap<String,Object>();
        
        parameterMap.put("datasource", jdbcTemplate.getDataSource());
        parameterMap.put("P_INVOICE_ID", posid);
        parameterMap.put("logo", imageURL);
        parameterMap.put("P_LOGINED_USER", httpRequest.getSession().getAttribute("uid"));

        modelAndView = new ModelAndView("invoicescheduleReport", parameterMap);
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        }
        return modelAndView;

    }
 

}//ReportController
