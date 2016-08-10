package srt.data.repository.management;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import srt.data.domain.management.User;

@Repository
public class SimpleUserRepositoryImpl implements IUserRepository {
	
	private JdbcTemplate jdbc;
	private KeyHolder keyHolder;
	
	private static final UserRowMapper ROWMAPPER = new UserRowMapper();
	
	@Autowired
	public SimpleUserRepositoryImpl(JdbcTemplate jdbc, KeyHolder keyHolder) {
		this.jdbc = jdbc;
		this.keyHolder = keyHolder;
	}

	@Override
	public User getUserByUserId(Long userId) {
		List<User> userList = jdbc.query(new StringBuilder(UserRowMapper.SELECT_WITH_NO_CRITERIA).append(UserRowMapper.SELECT_CRITERIA_BY_USERID).toString(), ROWMAPPER, userId);
		if (userList == null || userList.size() == 0) {
			return null;
		}
		return userList.get(0);
	}

	@Override
	public User getUsersByUserName(String userName) {
		List<User> users=jdbc.query(new StringBuilder(UserRowMapper.SELECT_WITH_NO_CRITERIA).append(UserRowMapper.SELECT_CRITERIA_BY_USERNAME).toString(), ROWMAPPER, userName);
		if(users!=null && users.size()>0){
			return users.get(0);
		}
		return null;
	}

	@Override
	public int addUser(User user) {
		int rows = jdbc.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				String sql="INSERT INTO Users (userName, userDescription, password) VALUES (?,?,?)";
		        PreparedStatement ps=con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		        ps.setString(1,user.getUserName());
		        ps.setString(2, user.getUserDescription());
		        ps.setString(3, user.getPassword());
		        return ps;
			}
		}, keyHolder);
		Long userId=keyHolder.getKey().longValue();
		user.setUserId(userId);
		return rows;
	}

	@Override
	public int updateUser(User user) {
		Long userId = user.getUserId();
		if (userId == null || userId < 0) {
			return -1;
		}
		return jdbc.update("UPDATE Users set userName=?, userDescription=? WHERE userId=?", user.getUserName(), user.getUserDescription(), userId);
	}

	@Override
	public int removeUserByUserId(Long userId) {
		if(userId==null || userId<0){
			return -1;
		}
		return jdbc.update("DELETE FROM Users WHERE userId=?", userId);
	}
	
	public static class UserRowMapper implements RowMapper<User> {

		public static final StringBuilder SELECT_WITH_NO_CRITERIA = new StringBuilder(
				"SELECT userId, userName, userDescription, password FROM Users");
		public static final StringBuilder SELECT_CRITERIA_BY_USERID = new StringBuilder(" WHERE userId=?");
		public static final StringBuilder SELECT_CRITERIA_BY_USERNAME = new StringBuilder(" WHERE userName=?");
		
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			User user = new User();
			user.setUserId(rs.getLong(1));
			user.setUserName(rs.getString(2));
			user.setUserDescription(rs.getString(3));
			user.setPassword(rs.getString(4));
			return user;
		}

	}

}
