package com.hero.stock.forms.transactions.bills;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.hero.stock.forms.transactions.purchaseorder.HERO_STK_ADDPURCHASEORDERITEMS;
import com.hero.stock.response.HERO_STK_RESPONSEINFO;
import com.hero.stock.util.HEROHOSURINVENTORYUTIL;
import com.hero.stock.util.HERO_STK_INVENTORYDAO;

public class HERO_STK_BILLSDAOIMPL extends HERO_STK_INVENTORYDAO implements HERO_STK_IBILLSDAO {
	private static Logger log = Logger.getLogger(HERO_STK_BILLSDAOIMPL.class);
	private static Logger error_log = Logger.getLogger("requestAppender");
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Override
	public HERO_STK_RESPONSEINFO billslist(String supplierid) {
		// TODO Auto-generated method stub
		StringBuilder query;
		if(supplierid.equals("0")){
			query = new StringBuilder("SELECT DISTINCT(`pur_req_id`)PURCHASECODE,`purchase_id`,`prhdr_bill_no`,`prhdr_grand_total_amt`,"
					+ "COALESCE(`prhdr_paid_amount`,0)prhdr_paid_amount,`opttype`,`paymode`,`reqdays`,e.`supplier_id`,COALESCE(`prhdr_paid_status`,0)prhdr_paid_status,COALESCE(`ps_status_name`,'Pending')"
					+ "ps_status_name, DATEDIFF(NOW(),`prhdr_modified_date`) modified_days,a.`prhdr_credited_days` AS credited_days,"
					+ " DATE_FORMAT(`prhdr_created_date`,'%c/%e/%y')prhdr_created_date FROM `hero_stock_purchase_receive_hdr` a "
					+ "JOIN `hero_stock_purchase_received_dtl` b ON a.`prhdr_id` = b.`prec_hdr_id` JOIN `hero_stock_purchase` C ON C.`purchase_code` = a.`pur_req_id` "
					+ "LEFT JOIN `hero_stock_purchase_status` d ON a.`prhdr_paid_status` = d.`ps_id` JOIN `hero_stock_supplier` e ON e.`supplier_id` = C.`supplier_id`"
					+ " WHERE (ISNULL(`prhdr_paid_status`) OR `prhdr_paid_status` = 3 "
					+ "OR `prhdr_paid_status` = 4 OR `prhdr_paid_status` = 7)");
		}else{
			query = new StringBuilder("SELECT DISTINCT(`pur_req_id`)PURCHASECODE,`purchase_id`,`prhdr_bill_no`,`prhdr_grand_total_amt`,"
					+ "COALESCE(`prhdr_paid_amount`,0)prhdr_paid_amount,`opttype`,`paymode`,`reqdays`,e.`supplier_id`,COALESCE(`prhdr_paid_status`,0)prhdr_paid_status,COALESCE(`ps_status_name`,'Pending')ps_status_name,"
					+ " DATEDIFF(NOW(),`prhdr_modified_date`) modified_days,a.`prhdr_credited_days` AS credited_days, "
					+ " DATE_FORMAT(`prhdr_created_date`,'%c/%e/%y')prhdr_created_date FROM `hero_stock_purchase_receive_hdr` a "
					+ "JOIN `hero_stock_purchase_received_dtl` b ON a.`prhdr_id` = b.`prec_hdr_id` JOIN `hero_stock_purchase` C ON C.`purchase_code` = a.`pur_req_id` "
					+ "LEFT JOIN `hero_stock_purchase_status` d ON a.`prhdr_paid_status` = d.`ps_id` JOIN `hero_stock_supplier` e ON e.`supplier_id` = C.`supplier_id`"
					+ " WHERE (ISNULL(`prhdr_paid_status`) OR `prhdr_paid_status` = 3 "
					+ "OR `prhdr_paid_status` = 4 OR `prhdr_paid_status` = 7)  AND C.`supplier_id` = "+supplierid);
		}
		
		query.append(" AND `prhdr_grand_total_amt` >  COALESCE(`prhdr_paid_amount`,0)");
		query.append(" AND (`credit_mode` = 0 OR `credit_mode` = 1 AND `credit_mode` < COALESCE(`prhdr_paid_status`,0) )");
		query.append(" ORDER BY `purchase_id` ASC");
		
		log.info("query       "+query);
		log.info("supplierid       "+supplierid);
		
		@SuppressWarnings("unchecked")
		List<Object> billsList = jdbcTemplate.query(query.toString(), new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int index) throws SQLException {
				// TODO Auto-generated method stub
				Map<String, Object> map = new HashMap<String, Object>();
				
				map.put("purchaseid", rs.getString("purchase_id"));
				map.put("purchasecode", rs.getString("PURCHASECODE"));
				map.put("billno", rs.getString("prhdr_bill_no"));
				map.put("grandtotalamt", rs.getString("prhdr_grand_total_amt"));
				map.put("paidamount", rs.getString("prhdr_paid_amount"));
				
				
				
				if(rs.getInt("prhdr_paid_status") == 7){
					log.info("prhdr_paid_status"+rs.getInt("prhdr_paid_status"));
					int CreditedDaysCount=0;
					if(rs.getInt("credited_days")>0 ||rs.getInt("modified_days")>0){
						CreditedDaysCount = rs.getInt("credited_days") - rs.getInt("modified_days");
						if(CreditedDaysCount>0){
						map.put("statusid", "Credit - "+CreditedDaysCount+" days1");
						
						}else{
							map.put("statusid", "Credit - "+rs.getInt("credited_days")+" days2");	
							
						}
					}
					else{
						map.put("statusid", "Credit - 0 days3");	
						
					}
				}
				else{
					map.put("statusid", rs.getString("prhdr_paid_status"));
				}
				
				
				//map.put("statusid", rs.getString("prhdr_paid_status"));
				map.put("status", rs.getString("ps_status_name"));
				map.put("billdate", rs.getString("prhdr_created_date"));
				/*map.put("action", "<input type='checkbox' id='billselect"+index+"' value='"+rs.getString("prhdr_bill_no")+"' checked='checked' >"
						+ "<input type='hidden' value="+(rs.getDouble("prhdr_grand_total_amt") - rs.getDouble("prhdr_paid_amount"))+" id='amounthidden"+index+"'>");*/
				map.put("paymode", rs.getString("paymode"));
				map.put("crediteddays", rs.getString("reqdays"));
				map.put("opttype", rs.getString("opttype"));
				
				
				map.put("action", "<input type='checkbox' id='billselect"+index+"' value='"+rs.getString("prhdr_bill_no")+"' checked='checked' >"
				+ "<input type='hidden' value="+(rs.getDouble("prhdr_grand_total_amt") - rs.getDouble("prhdr_paid_amount"))+" id='amounthidden"+index+"'>"
				+ "<input type='hidden' value="+(rs.getInt("prhdr_paid_status"))+" id='paidstatushidden"+index+"'>");
				
				index++;
				
				return map;
			}
		});
		log.info(billsList);
		
		inventoryResponseOBJ.setResponseType("S");
		inventoryResponseOBJ.setResponseObj(billsList);
		
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		
		return inventoryResponseInfoOBJ;
	}

	@Override
	public HERO_STK_RESPONSEINFO savebulkbill(String billsData) {
		// TODO Auto-generated method stub
		
		try {
			log.info("billsData       "+billsData);
			List<HERO_STK_BILLSREQUEST> billList = HEROHOSURINVENTORYUTIL.convertJSONArraytoList(billsData,"com.hero.stock.forms.transactions.bills.HERO_STK_BILLSREQUEST");
			
			savebulkbill(billList);
			
			inventoryResponseOBJ.setResponseType("S");
			inventoryResponseOBJ.setResponseObj("Processed Successfully");
			
		} catch (ClassNotFoundException | JSONException e) {
			// TODO Auto-generated catch block
			error_log.info(e);
		}
		catch (Exception e) {
			// TODO: handle exception
			error_log.info(e);
			inventoryResponseOBJ.setResponseType("S");
			inventoryResponseOBJ.setResponseObj("System Error Occured. Please contact System Administrator");
		}
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		
		return inventoryResponseInfoOBJ;
	}

	@Transactional
	public void savebulkbill( final List<HERO_STK_BILLSREQUEST> list) 
	{
		try
		{
			
			jdbcTemplate.batchUpdate("{call P_HERO_STOCK_PURCHASE_BULKBILL_PROCESS( ?, ?, ?, ?,?, ?,? )}",
				new BatchPreparedStatementSetter() {					
				public int getBatchSize()
				{
					return list.size();
				}
	
			public void setValues(PreparedStatement ps, int i) throws SQLException 
			{
				HERO_STK_BILLSREQUEST bill = list.get(i);
				log.info("Item Values       "+bill.getAmount()+"    "+bill.getPopaidstatus()+"  "+bill.getBillno());
				ps.setString(1, bill.getBillno());
				try {
					ps.setString(2, (HEROHOSURINVENTORYUTIL.convertToSQLDate(bill.getPaiddate())).toString());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					error_log.info(e);
				}
				ps.setString(3, bill.getPaymenttype());
				ps.setString(4, bill.getBankid());
				ps.setString(5, bill.getChequeno());
				ps.setString(6, bill.getUserid());
				ps.setString(7, bill.getPopaidstatus());
				
			}
		});
		}
		catch( Exception e )
		{
			error_log.info(e);
			throw e;
		}
	}

	@Override
	public HERO_STK_RESPONSEINFO pohistoryDetails(String pid) {
		// TODO Auto-generated method stub
		StringBuilder query;

			query = new StringBuilder("SELECT `product_name`, `prec_recving_quantity`, `prec_pur_price`,`prec_batchno`,"
					+ " (SELECT `unit`FROM `hero_admin_unit_type` G  WHERE G.`unit_type_id` = `prec_full_uom`) `receivingfulluom`, `prec_full_qty`,"
					+ " (SELECT `unit`FROM `hero_admin_unit_type` G  WHERE G.`unit_type_id` = `prec_loose_uom`) `receivinglooseuom`, `prec_loose_qty`,  "
					+ " FROM `hero_stock_purchase_received_dtl` a JOIN `hero_stock_product` b ON"
					+ "a.`prec_product_id` = b.`product_id` WHERE `prec_hdr_id` = '"+pid+"'");
		
		log.info("pohistoryDetails query       "+query);
		@SuppressWarnings("unchecked")
		List<Object> pohistoryDetailsList = jdbcTemplate.query(query.toString(), new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int index) throws SQLException {
				// TODO Auto-generated method stub
				Map<String, Object> map = new HashMap<String, Object>();
				
				map.put("productname", rs.getString("product_name"));
				map.put("recvingquantity", rs.getString("prec_recving_quantity"));
				map.put("price", rs.getString("prec_pur_price"));
				map.put("batchno", rs.getString("prec_batchno"));
				map.put("receivingfulluom", rs.getString("receivingfulluom"));
				map.put("fullqty", rs.getString("prec_full_qty"));
				map.put("receivinglooseuom", rs.getString("receivinglooseuom"));
				map.put("looseqty", rs.getString("prec_loose_qty"));
				
				index++;
				
				return map;
			}
		});
		log.info(pohistoryDetailsList);
		
		inventoryResponseOBJ.setResponseType("S");
		inventoryResponseOBJ.setResponseObj(pohistoryDetailsList);
		
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		
		return inventoryResponseInfoOBJ;
	}
}
