package com.vh.employee.imp;

import com.vh.employee.IEmployeeDao;
import com.vh.employee.IEmployeeServie;
import com.vh.util.PinYinTool;
import com.vh.util.StringUtil;
import com.vh.vo.employee.ABS;
import com.vh.vo.employee.Employee;

/**
 * 实现管理员业务逻辑层接口
 *
 * @author 孙为愿
 */
public class EmployeeServie implements IEmployeeServie {
	private IEmployeeDao employeeDao = new EmployeeDao();

	/**
	 * 实现登陆抽象方法
	 */
	@Override
	public boolean login(String employeeName, String password) {
		return employeeDao.login(employeeName, password);
	}

	/**
	 * 实现获取登陆次数抽象方法
	 */
	@Override
	public int loginTimmer(String employeeName, String password) {
		return employeeDao.loginTimmer(employeeName, password);
	}

	/**
	 * 实现修改登陆次数抽象方法
	 */
	@Override
	public void changeTimmer(String employeeName, boolean flag) {
		employeeDao.changeTimmer(employeeName, flag);
	}

	/**
	 * 实现添加员工抽象方法
	 */
	@Override
	public void add(Employee employee) {
		employee.setEmployeeId(StringUtil.uuid());
		String realName = employee.getRealName();
		String[] name = realName.split("");
		String employeeName = "";
		for (int i = 0; i < name.length; i++) {
			if (i == 0) {
				employeeName += PinYinTool.getPinYinAllChar(name[i], false);
			} else {
				employeeName += PinYinTool.getPinYinAllChar(name[i], false).substring(0, 1);
			}
		}
		employee.setEmployeeName(employeeName);
		employeeDao.add(employee);
	}

	/**
	 * 实现判断旧密码是否正确抽象方法
	 */
	@Override
	public boolean judgeOldPassword(String employeeName, String oldPassword) {
		return employeeDao.judgeOldPassword(employeeName, oldPassword);
	}

	/**
	 * 实现修改密码抽象方法
	 *
	 *@author 孙为愿
	 */
	public boolean changePassword(String employeeName,String surePassword) {
		return employeeDao.changePassword(employeeName, surePassword);
	}

	/**
	 * 修改用户名抽象方法
	 *
	 * @author 孙为愿
	 */
	public boolean changeEmployeeName(String employeeName, String newEmployeeName) {
		return employeeDao.chcngeEmployeeName(employeeName, newEmployeeName);
	}

	/**
	 * 实现获得用户信息抽象方法
	 *
	 *@author 孙为愿
	 */
	public Employee getInfo(String employeeName) {
		Employee employee=employeeDao.getInfo(employeeName);
		employee.setAbs(employeeDao.getABSById(employee.getCertificationId()));
		return employee;
	}

	/**
	 * 实现通过身份证号获取生日，年龄，性别抽象方法
	 *
	 * @author 孙为愿
	 */
	public ABS getABSById(String certificationId) {
		return employeeDao.getABSById(certificationId);
	}
}
