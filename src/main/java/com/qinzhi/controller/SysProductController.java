package com.qinzhi.controller;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.qinzhi.bean.Constants;
import com.qinzhi.domain.SysProduct;
import com.qinzhi.security.ShiroPrincipal;
import com.qinzhi.security.ShiroUtils;
import com.qinzhi.service.IProductService;
import com.qinzhi.utils.DateTimeUtils;
import com.qinzhi.utils.JsonUtils;
import com.qinzhi.utils.Pageable;
import com.qinzhi.utils.RenderUtil;

@Controller
public class SysProductController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SysProductController.class);
	
	@Autowired
	private IProductService productService;
	
	@RequestMapping("/findProductList.json")
    public void findProductList(
    		@RequestParam(value = "offset", defaultValue = Constants.PAGE_NO_DEFAULT_VALUE) int offset,
            @RequestParam(value = "limit", defaultValue = Constants.DEFAULT_ROWS) int limit,
            HttpServletResponse response){
        Pageable<SysProduct> pageable = null;
        try {
            pageable =
                    this.productService.findProductList(offset,limit);
        } catch (Exception e) {
            LOGGER.error("Class ： SysProductController -> Method: findProductList -> Exception: {}", e);
        }
        RenderUtil.renderJson(JsonUtils.toJson(pageable), response);
    }
	
	@RequestMapping("/addProduct.json")
	public String addProduct(HttpServletResponse response,
			SysProduct product){
		try {
			ShiroPrincipal shiroPrincipal = ShiroUtils.getPrincipal();
			product.setCreateUser(shiroPrincipal.getUser().getOperatorLoginName());
			product.setCreateDate(DateTimeUtils.currentTime());
			productService.saveProduct(product);
		} catch (Exception e) {
			LOGGER.error("Class ： SysProductController -> Method: addProduct -> Exception: {}", e);
		}
		return "system/my_production";
	}

}
