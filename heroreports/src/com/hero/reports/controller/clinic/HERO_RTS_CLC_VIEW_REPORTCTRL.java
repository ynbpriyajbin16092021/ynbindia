package com.hero.reports.controller.clinic;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.hero.heroutils.HERO_UTILS;
import com.hero.reports.util.HERO_RTS_INVENTORYDAO;




@Controller
@RequestMapping("/report/")
public class HERO_RTS_CLC_VIEW_REPORTCTRL extends HERO_RTS_INVENTORYDAO {
	private static final Logger logger = LoggerFactory.getLogger(HERO_RTS_CLC_VIEW_REPORTCTRL.class);
	private String filePath;

    @RequestMapping(method = RequestMethod.GET , value = "prescriptionschedule/{prescriptionid}")
    public ModelAndView generatePurchaseScheduleReport(ModelAndView modelAndView,
    		@PathVariable("prescriptionid") String prescriptionid,HttpServletRequest httpRequest){

    	logger.info("--------------generate PDF report for prescription id----------"+prescriptionid);

        try
        {
        
        Map<String,Object> parameterMap = new HashMap<String,Object>();
        /*String lImageURL = (String.format("%s://%s:%d"+httpRequest.getContextPath()+"/",httpRequest.getScheme(),  httpRequest.getServerName(), httpRequest.getServerPort())).concat("forms/viewimages?viewtype=1");
        String rImageURL = (String.format("%s://%s:%d"+httpRequest.getContextPath()+"/",httpRequest.getScheme(),  httpRequest.getServerName(), httpRequest.getServerPort())).concat("forms/viewimages?viewtype=2");
       */ 

       /* String lImageURL =HERO_UTILS.getApplicationSontext(httpRequest)+"/heroclinic/forms/imageview?type=D";*/

        /*String lImageURL = HERO_UTILS.getApplicationSontext(httpRequest)+"/heroclinic/forms/imageview?type=P";*/
       
        String lImageURL = "",rImageURL = "";
        /*String imageURL = (String.format("%s://%s:%d"+"/pharmacy"+"/",httpRequest.getScheme(),  httpRequest.getServerName(), httpRequest.getServerPort())).concat("forms/viewimages");*/
       /* String imageURL = ("http://localhost:8080/heroadmin/forms/viewimages");*/
        String imageURL = HERO_UTILS.getApplicationSontext(httpRequest)+"/heroadmin/forms/HeroImageView?index=0";
        
        
        
        String selectQuery = "SELECT `hp_imagepath` patient_image ,`bd_imgpath_file` doctor_image FROM "
        					+" `hero_clinic_patient_prescriptions` A JOIN `hero_admin_patient` B ON A.`hp_id` = B.`hp_seq_id` JOIN "
        					+" `hero_admin_physician_profile` C ON A.`bd_id` = C.`bd_user_id` "
        					+" WHERE `hpp_id` = '"+prescriptionid+"'";
		@SuppressWarnings("unchecked")
		List<Object> pImgList = jdbcTemplate.query(selectQuery, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				// TODO Auto-generated method stub
				/*List<HashMap<String, String>> detail = new ArrayList<HashMap<String,String>>();*/
				
				HashMap<String, String> map = new HashMap<String, String>();

				map.put("patientImage", rs.getString("patient_image"));
				map.put("doctorImage", rs.getString("doctor_image"));

				return map;
			}
		});
		File file = null;
		if(pImgList.size() > 0){
			Map<String, Object> pImgMap = (Map<String, Object>) pImgList.get(0);
			filePath = httpRequest.getServletContext().getRealPath("");
			File dir = new File(filePath);
			dir = dir.getParentFile();
			
			filePath = dir.getAbsolutePath()+File.separator+"clinic"+File.separator+"patientprofile"+File.separator+pImgMap.get("patientImage")+".jpg";
			lImageURL = filePath;
			filePath = dir.getAbsolutePath()+File.separator+"clinic"+File.separator+"physicanprofile"+File.separator+pImgMap.get("doctorImage")+".jpg";
			rImageURL = filePath;

		}
		
		file = new File(lImageURL);
		if (!file.exists()) {
        	filePath = HERO_UTILS.getApplicationSontext(httpRequest)+"/heroadmin/resources/images/logos/defaultpatient.png";
        	lImageURL = filePath;	
        }
		
		file = new File(rImageURL);
		if (!file.exists()) {
			filePath = HERO_UTILS.getApplicationSontext(httpRequest)+"/heroadmin/resources/images/logos/defaultphysician.png";
        	rImageURL = filePath;	
        }
		
        parameterMap.put("datasource", jdbcTemplate.getDataSource());
        parameterMap.put("P_PRESCRIPTION_ID", prescriptionid);
        parameterMap.put("logo", imageURL);
        parameterMap.put("leftimage", lImageURL);
        parameterMap.put("rightimage", rImageURL);
        parameterMap.put("P_LOGINED_USER", httpRequest.getSession().getAttribute("uid"));
        
        modelAndView = new ModelAndView("prescriptionscheduleReport", parameterMap);
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        }
        return modelAndView;

    }
}
