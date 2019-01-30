package com.rebs.db.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rebs.db.dao.EmployeeMapper;
import com.rebs.db.domain.Employee;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;
    
    
    public List<Employee> getEmployeeByWorkNo(String workNo){
        return employeeMapper.getEmployeeByWorkNo(workNo);
    }
    
    /**
     * 新增员工的openId
     * @param employee
     * @return
     */
    public boolean insertWxEmployee(Employee employee) {
        // TODO Auto-generated method stub
        return employeeMapper.insertWxEmployee(employee) == 1;
    }
    
    
    /**
     * 查询员工的openId是否存在
     * @param employeeId
     * @return
     */
    public Employee selectWxEmployeeByEmployeeId(Integer employeeId){
        return employeeMapper.selectWxEmployeeByEmployeeId(employeeId);
    }
    
    /**
     * 更新员工的openId
     * @param record
     * @return
     */
    public boolean updateWxEmployee(Employee employee) {
        // TODO Auto-generated method stub
        return employeeMapper.updateWxEmployee(employee) == 1;
    }
}
