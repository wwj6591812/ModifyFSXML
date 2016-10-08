package com.chinatelecom.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.chinatelecom.common.DBUtil;
import com.chinatelecom.model.Allocations;
import com.chinatelecom.model.Pool;
import com.chinatelecom.model.User;

public class FSPropertiesDao {

	public synchronized void updatePoolProperties(String poolName, String targetProperties,
			String newValue) {
		PreparedStatement ptmt = null;
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			StringBuffer sb = new StringBuffer();
			sb.append(" insert into queueProperties (name, ");
			sb.append(targetProperties);
			sb.append(" ) values (?,?) ON DUPLICATE KEY UPDATE ");
			sb.append(targetProperties);
			sb.append(" =? ");
			ptmt = conn.prepareStatement(sb.toString());

			ptmt.setString(1, poolName);
			ptmt.setString(2, newValue);
			ptmt.setString(3, newValue);
			ptmt.execute();
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				ptmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public synchronized void updateUserProperties(String userName, String targetProperties,
			String newValue) {
		PreparedStatement ptmt = null;
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			StringBuffer sb = new StringBuffer();
			sb.append(" insert into userProperties (name, ");
			sb.append(targetProperties);
			sb.append(" ) values (?,?) ON DUPLICATE KEY UPDATE ");
			sb.append(targetProperties);
			sb.append(" =? ");
			ptmt = conn.prepareStatement(sb.toString());
			ptmt.setString(1, userName);
			ptmt.setString(2, newValue);
			ptmt.setString(3, newValue);
			ptmt.execute();
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				ptmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public synchronized void updateDefaultProperties(String targetProperties, String newValue) {
		PreparedStatement ptmt = null;
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			StringBuffer sb = new StringBuffer();
			sb.append(" insert into defaultProperties (id, ");
			sb.append(targetProperties);
			sb.append(" ) values (?,?) ON DUPLICATE KEY UPDATE ");
			sb.append(targetProperties);
			sb.append(" =? ");
			ptmt = conn.prepareStatement(sb.toString());

			ptmt.setInt(1, 1);
			ptmt.setString(2, newValue);
			ptmt.setString(3, newValue);
			ptmt.execute();
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				ptmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public ArrayList<Pool> queryPoolProperties() {
		PreparedStatement ptmt = null;
		Connection conn = null;
		ArrayList<Pool> result = new ArrayList<Pool>();
		try {
			conn = DBUtil.getConnection();
			StringBuffer sb = new StringBuffer();
			sb.append(" select * from queueProperties ");
			ptmt = conn.prepareStatement(sb.toString());

			ResultSet rs = ptmt.executeQuery();
			Pool pool = null;
			while (rs.next()) {
				pool = new Pool();
				if (rs.getString("name") != null)
					pool.setDisplayName(rs.getString("name"));
				if (rs.getString("minResources") != null)
					pool.setMinResources(rs.getString("minResources"));
				if (rs.getString("maxResources") != null)
					pool.setMaxResources(rs.getString("maxResources"));
				if (rs.getString("maxRunningApps") != null)
					pool.setMaxRunningApps(rs.getString("maxRunningApps"));
				if (rs.getString("maxAMShare") != null)
					pool.setMaxAMShare(rs.getString("maxAMShare"));
				if (rs.getString("weight") != null)
					pool.setWeight(rs.getString("weight"));
				if (rs.getString("minSharePreemptionTimeout") != null)
					pool.setMinSharePreemptionTimeout(rs
							.getString("minSharePreemptionTimeout"));
				if (rs.getString("fairSharePreemptionTimeout") != null)
					pool.setFairSharePreemptionTimeout(rs
							.getString("fairSharePreemptionTimeout"));
				if (rs.getString("fairSharePreemptionThreshold") != null)
					pool.setFairSharePreemptionThreshold(rs
							.getString("fairSharePreemptionThreshold"));
				if (rs.getString("schedulingMode") != null)
					pool.setSchedulingMode(rs.getString("schedulingMode"));
				if (rs.getString("aclSubmitApps") != null)
					pool.setAclSubmitApps(rs.getString("aclSubmitApps"));
				if (rs.getString("aclAdministerApps") != null)
					pool.setAclAdministerApps(rs.getString("aclAdministerApps"));

				result.add(pool);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				ptmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public ArrayList<User> queryUserProperties() {
		PreparedStatement ptmt = null;
		Connection conn = null;
		ArrayList<User> result = new ArrayList<User>();
		try {
			conn = DBUtil.getConnection();
			StringBuffer sb = new StringBuffer();
			sb.append(" select * from userProperties ");
			ptmt = conn.prepareStatement(sb.toString());

			ResultSet rs = ptmt.executeQuery();
			User user = null;
			while (rs.next()) {
				user = new User();
				if (rs.getString("name") != null)
					user.setName(rs.getString("name"));
				if (rs.getString("maxRunningJobs") != null)
					user.setMaxRunningJobs(rs.getString("maxRunningJobs"));
				result.add(user);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				ptmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public ArrayList<Allocations> queryDefaultProperties() {
		PreparedStatement ptmt = null;
		Connection conn = null;
		ArrayList<Allocations> result = new ArrayList<Allocations>();

		try {
			conn = DBUtil.getConnection();
			StringBuffer sb = new StringBuffer();
			sb.append(" select * from defaultProperties ");
			ptmt = conn.prepareStatement(sb.toString());
			ResultSet rs = ptmt.executeQuery();
			Allocations allocations = null;
			while (rs.next()) {
				allocations = new Allocations();

				if (rs.getString("queueMaxAppsDefault") != null)
					allocations.setQueueMaxAppsDefault(rs
							.getString("queueMaxAppsDefault"));
				if (rs.getString("defaultMinSharePreemptionTimeout") != null)
					allocations.setDefaultMinSharePreemptionTimeout(rs
							.getString("defaultMinSharePreemptionTimeout"));
				if (rs.getString("defaultQueueSchedulingMode") != null)
					allocations.setDefaultQueueSchedulingMode(rs
							.getString("defaultQueueSchedulingMode"));
				if (rs.getString("userMaxAppsDefault") != null)
					allocations.setUserMaxAppsDefault(rs
							.getString("userMaxAppsDefault"));
				if (rs.getString("defaultFairSharePreemptionTimeout") != null)
					allocations.setDefaultFairSharePreemptionTimeout(rs
							.getString("defaultFairSharePreemptionTimeout"));
				if (rs.getString("defaultFairSharePreemptionThreshold") != null)
					allocations.setDefaultFairSharePreemptionThreshold(rs
							.getString("defaultFairSharePreemptionThreshold"));
				if (rs.getString("queueMaxAMShareDefault") != null)
					allocations.setQueueMaxAMShareDefault(rs
							.getString("queueMaxAMShareDefault"));

				result.add(allocations);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				ptmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
}
