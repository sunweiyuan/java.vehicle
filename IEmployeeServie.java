package com.vh.employee;

import com.vh.vo.employee.ABS;
import com.vh.vo.employee.Employee;

/**
 * 管理员业务逻辑层接口
 *
 * @author 孙为愿
 */
public interface IEmployeeServie {
	
	/**
	 * 登陆抽象方法
	 *
	 *@author 孙为愿
	 * @param password 
	 * @param employeeName 
	 */
	boolean login(String employeeName, String password);
	/**
	 * 获取登陆次数抽象方法
	 *
	 *@author 孙为愿
	 */
	int loginTimmer(String employeeName,String password);

	/**
	 * 修改登陆次数抽象方法
	 *
	 * @author 孙为愿
	 */
	void changeTimmer(String employeeName, boolean flag);
	
	/**
	 * 添加员工抽象方法
	 *
	 *@author 孙为愿
	 */
	void add(Employee employee);
	
	/**
	 * 判断旧密码是否正确抽象方法
	 *
	 *@author 孙为愿
	 */
	boolean judgeOldPassword(String employeeName,String oldPassword);
	/**
	 * 修改密码抽象方法
	 *
	 *@author 孙为愿
	 */
	boolean changePassword(String employeeName,String surePassword);

	/**
	 * 修改用户名抽象方法
	 *
	 * @author 孙为愿
	 */
	boolean changeEmployeeName(String employeeName, String newEmployeeName);
	/**
	 * 获得用户信息抽象方法
	 *
	 *@author 孙为愿
	 */
	Employee getInfo(String employeeName);

	/**
	 * 通过身份证号获取生日，年龄，性别抽象方法
	 *
	 * @author 孙为愿
	 */
	ABS getABSById(String certificationId);
}
