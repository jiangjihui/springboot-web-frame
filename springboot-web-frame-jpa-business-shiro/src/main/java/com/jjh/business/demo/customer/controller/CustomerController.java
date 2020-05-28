package com.jjh.business.demo.customer.controller;


import com.jjh.business.demo.customer.domain.Customer;
import com.jjh.business.demo.customer.service.CustomerService;
import com.jjh.common.web.controller.BaseController;
import com.jjh.common.web.form.PageRequestForm;
import com.jjh.common.web.form.PageResponseForm;
import com.jjh.common.web.form.SimpleForm;
import com.jjh.common.web.form.SimpleResponseForm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
*   客户管理
 * @author jjh
 * @date 2019/11/18
*/
@Api(tags = "客户管理")
@RestController
@RequestMapping("/target/customer")
public class CustomerController extends BaseController {

    @Autowired
    private CustomerService customerService;


    /**
     * 客户列表
     */
    @ApiOperation("客户列表")
    @PostMapping("/list")
    public SimpleResponseForm<PageResponseForm<Customer>> list(@RequestBody PageRequestForm<Customer> form) {
        List<Customer> list = customerService.list(form);
        return page(form, list);
    }

    /**
     * 新增客户
     */
    @ApiOperation("新增客户")
    @PostMapping("/add")
    public SimpleResponseForm<Customer> add(HttpServletRequest request, @RequestBody Customer entity) {
        Customer result = customerService.add(entity);
        return success(result);
    }

    /**
     * 更新客户
     */
    @ApiOperation("更新客户")
    @PostMapping("/update")
    public SimpleResponseForm<Customer> update(HttpServletRequest request, @RequestBody Customer entity) {
        Customer result = customerService.update(entity);
        return success(result);
    }

    /**
     * 删除客户
     */
    @ApiOperation("删除客户")
    @GetMapping("/delete")
    public SimpleResponseForm<String> delete(String ids) {
        customerService.del(ids);
        return success();
    }

}
