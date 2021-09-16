package com.hero.stock.controller.services.mastersmodule;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;




















import org.springframework.web.multipart.MultipartFile;

import com.hero.forms.services.admin.masters.bank.HERO_ADM_SERVC_BANKHELPER;
import com.hero.forms.services.admin.masters.bank.HERO_ADM_SERVC_IBANKDAO;
import com.hero.forms.services.admin.masters.company.HERO_ADM_SERVC_COMPANYHELPER;
import com.hero.forms.services.admin.masters.company.HERO_ADM_SERVC_ICOMPANYDAO;
import com.hero.forms.services.stock.masters.category.HERO_STK_SERVC_CATEGORYHELPER;
import com.hero.forms.services.stock.masters.category.HERO_STK_SERVC_iCATEGORYDAO;
import com.hero.forms.services.stock.masters.supplier.HERO_STK_SERVC_ISUPPLIERDAO;
import com.hero.forms.services.stock.masters.supplier.HERO_STK_SERVC_SUPPLIERHELPER;
import com.hero.forms.services.stock.masters.uom.HERO_STK_SERVC_IUOMDAO;
import com.hero.forms.services.stock.masters.uom.HERO_STK_SERVC_UOMHELPER;
import com.hero.stock.forms.services.masters.customer.HERO_STK_SERVC_CUSTOMERHELPER;
import com.hero.stock.forms.services.masters.customer.HERO_STK_SERVC_ICUSTOMERDAO;
import com.hero.stock.forms.transactions.product.HERO_STK_SERVC_IPRODUCTDAO;
import com.hero.stock.forms.transactions.product.HERO_STK_SERVC_PRODUCTHELPER;
import com.hero.stock.lov.HERO_STK_IINVENTORYLOV;
import com.hero.stock.util.HERO_STK_INVENTORYDAO;

@Controller
public class HERO_STK_SERVC_MASTERMODULE extends HERO_STK_INVENTORYDAO{

	private static Logger log = Logger.getLogger(HERO_STK_SERVC_MASTERMODULE.class);
	

	@Autowired
	private HERO_STK_SERVC_PRODUCTHELPER productMasterHelperOBJ;
	@Autowired
	private HERO_STK_SERVC_IPRODUCTDAO productDAOobj;
	
	@Autowired
	private HERO_ADM_SERVC_BANKHELPER bankMasterHelperOBJ;
	@Autowired
	private HERO_ADM_SERVC_IBANKDAO bankMasterDAOobj;
	
	
	@Autowired
	private HERO_STK_SERVC_SUPPLIERHELPER supplierHelperOBJ;
	@Autowired
	private HERO_STK_SERVC_ISUPPLIERDAO supplierDAOobj;
	
	
	@Autowired
	private HERO_STK_SERVC_CATEGORYHELPER categoryMasterHelperOBJ;
	@Autowired
	private HERO_STK_SERVC_iCATEGORYDAO categoryDAOobj;
	
	@Autowired
	private HERO_STK_SERVC_CUSTOMERHELPER customerHelperOBJ;
	@Autowired
	private HERO_STK_SERVC_ICUSTOMERDAO customerDAOobj;
	
	@Autowired
	private HERO_ADM_SERVC_COMPANYHELPER companyHelperOBJ;
	@Autowired
	private HERO_ADM_SERVC_ICOMPANYDAO companyDAOobj;
	
	@Autowired
	private HERO_STK_SERVC_UOMHELPER uomHelperOBJ;
	@Autowired
	private HERO_STK_SERVC_IUOMDAO uomDAOobj;
	
	

	

	
	@Autowired
	private HERO_STK_IINVENTORYLOV inventoryLOVobj;
	
	
	@RequestMapping(value="/uploadCompanylogo",method=RequestMethod.POST)
	public @ResponseBody
	 String uploadCompanylogo(@RequestParam("name") String name,
			@RequestParam("file") MultipartFile file,HttpServletRequest request) {
		System.out.println("name "+name);
		String serverPath = request.getRealPath("/");
		File dir = new File(serverPath);
		dir = dir.getParentFile();
		serverPath = dir.getAbsolutePath();
		System.out.println("Directory   "+serverPath);
		serverPath = serverPath+File.separator+"customermaster";
		File makedir = new File(serverPath);  
		if(!makedir.exists()){
			makedir.mkdirs();  
		}
		try {
			byte[] bytes = file.getBytes();
			File serverFile = new File(serverPath
					+ File.separator + name);
			BufferedOutputStream stream = new BufferedOutputStream(
					new FileOutputStream(serverFile));
			stream.write(bytes);
			stream.close();
			
		} catch (IOException e) {
			return "Failed to Upload";
		}
		return "File Successfully Uploaded";
		
	
	}
	
	@RequestMapping(value="/saveproduct",method=RequestMethod.POST)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> saveProduct(@RequestBody String productData)
	{
		log.info("productData        "+productData);
		inventoryResponseInfoOBJ = productMasterHelperOBJ.saveProduct(productData, productDAOobj);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ,new HttpHeaders(),HttpStatus.OK);
	}
	
	@RequestMapping(value="/productlist",method=RequestMethod.GET)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> getProductList()
	{
		inventoryResponseInfoOBJ = productMasterHelperOBJ.productlist(productDAOobj);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ, new HttpHeaders(), HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/productinfo/{productid}",method=RequestMethod.GET)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> getproductinfo(@PathVariable("productid")String productid)
	{
		inventoryResponseInfoOBJ = productMasterHelperOBJ.getproductinfo(productDAOobj,productid);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ, new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/productviewinfo/{productid}/{userid}",method=RequestMethod.GET)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> getproductviewinfo(@PathVariable("productid")String productid,@PathVariable("userid")String userid)
	{
		inventoryResponseInfoOBJ = productMasterHelperOBJ.getproductviewinfo(productDAOobj,productid,userid);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ, new HttpHeaders(), HttpStatus.OK);
	}
	
	
	
	@RequestMapping(value="/importproducttostock",method=RequestMethod.POST)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> importProducttoStock(@RequestBody String importdata,HttpServletRequest httpRequest)
	{
		log.info("importproducttostock master module in controller");
		inventoryResponseInfoOBJ = productMasterHelperOBJ.importProducttoStock(productDAOobj,importdata,httpRequest);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ, new HttpHeaders(), HttpStatus.OK);
	}
	
	

	
	
	@RequestMapping(value="/savebank/{bankid}/{bankname}/{userid}/{oprn}",method=RequestMethod.POST)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> saveBank(
			@PathVariable("bankid")String bankId,
			@PathVariable("bankname")String bankName,
			@PathVariable("userid")String userid,
			@PathVariable("oprn")String oprn)
	{
		log.info("Values        "+bankId+"   "+bankName+"   "+oprn);
		inventoryResponseInfoOBJ = bankMasterHelperOBJ.savebank(bankId,bankName,userid, oprn, bankMasterDAOobj);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ,new HttpHeaders(), HttpStatus.OK);
	}
	

	@RequestMapping(value="/banklist",method = RequestMethod.GET)
	public ResponseEntity<Object> loadBank()
	{
		inventoryResponseInfoOBJ = bankMasterHelperOBJ.loadbanks(bankMasterDAOobj);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ, new HttpHeaders(),HttpStatus.OK);
	}
	
	@RequestMapping(value="/uomlist")
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> loaduoms()
	{
		inventoryResponseInfoOBJ = uomHelperOBJ.loaduoms(uomDAOobj);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ,new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/saveuom")
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> saveuom(@RequestBody String uomData)
	{
		inventoryResponseInfoOBJ = uomHelperOBJ.saveuom(uomData, uomDAOobj);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ, new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/saveuomsetting")
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> saveuomsetting(@RequestBody String uomsettingData,HttpServletRequest httpRequest)
	{
		inventoryResponseInfoOBJ = uomHelperOBJ.saveuomsetting(uomsettingData, uomDAOobj,httpRequest);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ, new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/uomsettinglist")
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> loaduomsetting()
	{
		inventoryResponseInfoOBJ = uomHelperOBJ.loaduomsetting(uomDAOobj);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ,new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/uomitemslist/{uomsettingsid}")
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> loaduomitemslist(@PathVariable(value="uomsettingsid")String uomsettingsid)
	{
		inventoryResponseInfoOBJ = uomHelperOBJ.loaduomitemslist(uomDAOobj,uomsettingsid);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ,new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/saveuomconfig")
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> saveuomconfig(@RequestBody String uomsettingData,HttpServletRequest httpRequest)
	{
		inventoryResponseInfoOBJ = uomHelperOBJ.saveuomconfig(uomsettingData, uomDAOobj,httpRequest);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ, new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/savecategory/{categoryid}/{categoryname}/{userid}/{oprn}",method=RequestMethod.POST)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> saveCategory(
			@PathVariable("categoryid")String categoryId,
			@PathVariable("categoryname")String categoryName,
			@PathVariable("userid")String userid,
			@PathVariable("oprn")String oprn)
	{
		log.info("Values        "+categoryId+"   "+categoryName+"   "+oprn);
		inventoryResponseInfoOBJ = categoryMasterHelperOBJ.savecategory(categoryId,categoryName,userid, oprn, categoryDAOobj);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ,new HttpHeaders(), HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/categorylist",method = RequestMethod.GET)
	public ResponseEntity<Object> loadCategory()
	{
		inventoryResponseInfoOBJ = categoryMasterHelperOBJ.loadcategories(categoryDAOobj);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ, new HttpHeaders(),HttpStatus.OK);
	}

	@RequestMapping(value="/productsuggestions",method=RequestMethod.GET)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> getproductsuggestions()
	{
		log.info("welcome to product suggestion");
		inventoryResponseInfoOBJ = productMasterHelperOBJ.getproductsuggestions(productDAOobj);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ, new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/savecompany",method=RequestMethod.POST)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> savecompany(@RequestBody String companyData)
	{
		inventoryResponseInfoOBJ = companyHelperOBJ.savecompany(companyData, companyDAOobj); 
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ, new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/companylist")
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> getCompanyList()
	{
		inventoryResponseInfoOBJ = companyHelperOBJ.getCompanyList(companyDAOobj);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ, new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/savecustomer")
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> savecustomer(@RequestBody String customerData)
	{
		inventoryResponseInfoOBJ = customerHelperOBJ.savecustomer(customerData, customerDAOobj);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ, new HttpHeaders(), HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/savesupplier")
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> savesupplier(@RequestBody String supplierData)
	{
		inventoryResponseInfoOBJ = supplierHelperOBJ.saveSupplier(supplierData, supplierDAOobj);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ, new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/savesuppliercontact")
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> savesuppliercontact(@RequestBody String supplierContactData)
	{
		inventoryResponseInfoOBJ = supplierHelperOBJ.saveSupplierContact(supplierContactData, supplierDAOobj);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ, new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/supplierlist")
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> loadsupplier()
	{
		inventoryResponseInfoOBJ = supplierHelperOBJ.supplierlist(supplierDAOobj);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ,new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/supplierinfo/{supplierid}")
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> getsupplierinfo(@PathVariable("supplierid")String supplierid)
	{
		inventoryResponseInfoOBJ = supplierHelperOBJ.getsupplierinfo(supplierDAOobj, supplierid);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ,new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/supplierviewinfo/{supplierid}/{supplierstatusid}")
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> getsupplierviewinfo(@PathVariable("supplierid")String supplierid,@PathVariable("supplierstatusid")String supplierstatusid)
	{
		inventoryResponseInfoOBJ = supplierHelperOBJ.getsupplierviewinfo(supplierDAOobj, supplierid,supplierstatusid);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ,new HttpHeaders(), HttpStatus.OK);
	}
	@RequestMapping(value="/loadcustomers")
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> loadcustomers(HttpServletRequest httpRequest)
	{
		inventoryResponseInfoOBJ = customerHelperOBJ.loadcustomers(customerDAOobj,httpRequest);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ,new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/loadaddcustomer/{customerid}")
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> loadaddcustomer(@PathVariable("customerid") String customerid)
	{
		inventoryResponseInfoOBJ = customerHelperOBJ.loadaddcustomer(customerDAOobj,customerid);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ,new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/loadcustomerviewpagewise/{pageno}/{status}")
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> loadcustomerviewpagewise(@PathVariable("pageno") String pageno,@PathVariable("status") String status,HttpServletRequest httpRequest)
	{
		inventoryResponseInfoOBJ = customerHelperOBJ.loadcustomerviewpagewise(customerDAOobj,pageno,status,httpRequest);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ,new HttpHeaders(), HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/loadcustomerviewstatuswise/{status}")
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> loadcustomerviewstatuswise(@PathVariable("status") String status,HttpServletRequest httpRequest)
	{
		inventoryResponseInfoOBJ = customerHelperOBJ.loadcustomerviewstatuswise(customerDAOobj,status,httpRequest);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ,new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/customerviewdetails/{customerid}")
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> customeroverviewDetails(@PathVariable("customerid") String customerid)
	{
		inventoryResponseInfoOBJ = customerHelperOBJ.customeroverviewDetails(customerDAOobj,customerid);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ,new HttpHeaders(), HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/country",method = RequestMethod.GET)
	public ResponseEntity<Object> getCountry()
	{
		inventoryResponseInfoOBJ = inventoryLOVobj.getCountry();
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ, new HttpHeaders(),HttpStatus.OK);
	}
	
	@RequestMapping(value="/states/{countryId}")
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> getStates(@PathVariable int countryId)
	{
		log.info("countryid   "+countryId);
		inventoryResponseInfoOBJ = inventoryLOVobj.getStates(countryId);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ,new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/cities/{stateId}")
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> getCities(@PathVariable int stateId)
	{
		log.info("stateId   "+stateId);
		inventoryResponseInfoOBJ = inventoryLOVobj.getCities(stateId);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ,new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/loadcustomergroup")
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> loadcustomergroup()
	{
		inventoryResponseInfoOBJ = customerHelperOBJ.loadcustomergroup(customerDAOobj);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ,new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/savecustomergroup")
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> savecustomergroup(@RequestBody String customergroupdata)
	{
		inventoryResponseInfoOBJ = customerHelperOBJ.savecustomergroup(customergroupdata,customerDAOobj);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ,new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="downloadsampleformat/{columnsheet}")
    public void downloadSampleFormat(@PathVariable("columnsheet") String columnsheet, HttpServletRequest httpRequest,
     HttpServletResponse httpResponse )
    {
		String fileName = "sampleformat.xlsx";
		log.info("fileName    "+columnsheet);
		productMasterHelperOBJ.downloadSampleFormat(fileName,columnsheet, productDAOobj, httpRequest, httpResponse);
		
    }
	
	
	@RequestMapping(value="/dishtypelist",method=RequestMethod.GET)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> dishtypelist()
	{
		inventoryResponseInfoOBJ = productMasterHelperOBJ.dishtypelist(productDAOobj);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ, new HttpHeaders(), HttpStatus.OK);
	}

	
	@RequestMapping(value="/savedishtype",method=RequestMethod.POST)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> savedishtype(@RequestBody String productData)
	{
		log.info("productData        "+productData);
		inventoryResponseInfoOBJ = productMasterHelperOBJ.savedishtype(productData, productDAOobj);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ,new HttpHeaders(),HttpStatus.OK);
	}
	
	@RequestMapping(value="/dishlist",method=RequestMethod.GET)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> dishlist()
	{
		inventoryResponseInfoOBJ = productMasterHelperOBJ.dishlist(productDAOobj);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ, new HttpHeaders(), HttpStatus.OK);
	}

	
	@RequestMapping(value="/savedish",method=RequestMethod.POST)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> savedish(@RequestBody String productData)
	{
		log.info("productData        "+productData);
		inventoryResponseInfoOBJ = productMasterHelperOBJ.savedish(productData, productDAOobj);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ,new HttpHeaders(),HttpStatus.OK);
	}
	
	@RequestMapping(value="/saveterms",method=RequestMethod.POST)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> saveterms(@RequestBody String productData)
	{
		log.info("productData        "+productData);
		inventoryResponseInfoOBJ = productMasterHelperOBJ.saveterms(productData, productDAOobj);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ,new HttpHeaders(),HttpStatus.OK);
	}
	
	@RequestMapping(value="/termslist",method=RequestMethod.GET)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> termslist()
	{
		inventoryResponseInfoOBJ = productMasterHelperOBJ.termslist(productDAOobj);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ, new HttpHeaders(), HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/savecompanymaster",method=RequestMethod.POST)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> savecompanymaster(@RequestBody String productData,HttpServletRequest request)
	{
		log.info("productData        "+productData);
		inventoryResponseInfoOBJ = productMasterHelperOBJ.savecompanymaster(productData, productDAOobj,request);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ,new HttpHeaders(),HttpStatus.OK);
	}
	
	@RequestMapping(value="/companymasterlist",method=RequestMethod.GET)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> companymasterlist()
	{
		inventoryResponseInfoOBJ = productMasterHelperOBJ.companymasterlist(productDAOobj);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ, new HttpHeaders(), HttpStatus.OK);
	}

}
