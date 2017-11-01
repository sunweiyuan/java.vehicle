package com.vh.employee.imp;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.vh.employee.IEmployeeDao;
import com.vh.util.db.DBLinkUtil;
import com.vh.util.db.IProcedureMapper;
import com.vh.util.db.ISelectMapper;
import com.vh.vo.employee.ABS;
import com.vh.vo.employee.Employee;

/**
 * 实现管理员数据访问层接口
 *
 * @author 孙为愿
 */
public class EmployeeDao implements IEmployeeDao {

	/**
	 * 实现登陆抽象方法
	 */
	@Override
	public boolean login(String employeeName, String password) {
		String sql = "select * from employee where employee_name = ? and password = ?";
		class SelectMapper implements ISelectMapper<Boolean> {

			@Override
			public Boolean callback(ResultSet resultSet) {
				try {
					if (resultSet.next()) {
						return true;
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return false;
			}

		}
		return new DBLinkUtil().select(sql, new SelectMapper(), employeeName, password);
	}

	/**
	 * 实现获取登陆次数抽象方法
	 */
	@Override
	public int loginTimmer(String employeeName, String password) {
		String sql = "select timmer from employee where employee_name=? and password=?";
		class SelectMapper implements ISelectMapper<Integer> {

			@Override
			public Integer callback(ResultSet resultSet) {
				try {
					resultSet.next();
					return resultSet.getInt("timmer");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return 0;
			}

		}
		return new DBLinkUtil().select(sql, new SelectMapper(), employeeName, password);
	}

	/**
	 * 实现修改登陆次数抽象方法
	 */
	@Override
	public void changeTimmer(String employeeName, boolean flag) {
		if (flag) {
			String sql = "update employee set timmer=0 where employee_name=?";
			new DBLinkUtil().update(sql, new Object[] { employeeName });
		} else {
			String sql = "update employee set timmer=timmer+1 where employee_name=?";
			new DBLinkUtil().update(sql, new Object[] { employeeName });
		}
	}

	/**
	 * 实现添加员工抽象方法
	 */
	@Override
	public void add(Employee employee) {
		String sql = "insert into employee(employee_id,employee_name,"
				+ "real_name,certification_id,bank_id,mobile,address) values(?,?,?,?,?,?,?)";
		new DBLinkUtil().update(sql,
				new Object[] { employee.getEmployeeId(), employee.getEmployeeName(), employee.getRealName(),
						employee.getCertificationId(), employee.getBankId(), employee.getMobile(),
						employee.getAddress() });
	}

	/**
	 * 实现判断旧密码是否正确抽象方法
	 */
	@Override
	public boolean judgeOldPassword(String employeeName, String oldPassword) {
		String sql = "select real_name from employee where employee_name=? and password=?";
		class SelectMapper implements ISelectMapper<Boolean> {

			@Override
			public Boolean callback(ResultSet resultSet) {
				try {
					if (resultSet.next())
						return true;
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return false;
			}

		}
		return new DBLinkUtil().select(sql, new SelectMapper(), employeeName, oldPassword);
	}

	/**
	 * 实现修改密码抽象方法
	 *
	 * @author 孙为愿
	 */
	public boolean changePassword(String employeeName, String surePassword) {
		String sql = "update employee set password = ? where employee_name = ?";
		return new DBLinkUtil().update(sql, new Object[] { surePassword, employeeName });
	}

	/**
	 * 修改用户名抽象方法
	 *
	 * @author 孙为愿
	 */
	public boolean chcngeEmployeeName(String employeeName, String newEmployeeName) {
		String sql = "update employee set employee_name=? where employee_name=?";
		return new DBLinkUtil().update(sql, new Object[] { newEmployeeName, employeeName });
	}

	/**
	 * 实现获得用户信息抽象方法
	 *
	 * @author 孙为愿
	 */
	public Employee getInfo(String employeeName) {
		String sql = "select * from employee where employee_name=?";
		class SelectMapper implements ISelectMapper<Employee> {

			@Override
			public Employee callback(ResultSet resultSet) {
				Employee employee = new Employee();
				try {
					resultSet.next();
					employee.setEmployeeName(resultSet.getString("employee_name"));
					employee.setRealName(resultSet.getString("real_name"));
					employee.setCertificationId(resultSet.getString("certification_id"));
					employee.setBankId(resultSet.getString("bank_id"));
					employee.setAddress(resultSet.getString("address"));
					employee.setMobile(resultSet.getString("mobile"));
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return employee;
			}

		}
		return new DBLinkUtil().select(sql, new SelectMapper(), employeeName);
	}

	/**
	 * 实现通过身份证号获取生日，年龄，性别抽象方法
	 *
	 * @author 孙为愿
	 */
	public ABS getABSById(String certificationId) {
		String procedureName = "get_info(?,?,?,?)";
		class ProcedureMapper implements IProcedureMapper<ABS> {
			@Override
			public ABS procedureMapper(CallableStatement callableStatement) {
				ABS abs = new ABS();
				try {
					abs.setBrithday(callableStatement.getString(2));
					abs.setSex(callableStatement.getString(3));
					abs.setAge(callableStatement.getInt(4));
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return abs;
			}

		}
		return new DBLinkUtil().procedure(procedureName, new Object[] { certificationId }, new int[] {
				oracle.jdbc.OracleTypes.CHAR, oracle.jdbc.OracleTypes.CHAR, oracle.jdbc.OracleTypes.NUMBER },
				new ProcedureMapper());
	}
}
