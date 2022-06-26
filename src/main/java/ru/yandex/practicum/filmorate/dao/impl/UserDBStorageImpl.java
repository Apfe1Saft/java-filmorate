package ru.yandex.practicum.filmorate.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.UserDBStorage;
import ru.yandex.practicum.filmorate.model.User;

import javax.xml.bind.ValidationException;
import java.util.*;

@Component
public class UserDBStorageImpl implements UserDBStorage {
    private final Logger log = LoggerFactory.getLogger(UserDBStorageImpl.class);

    private final JdbcTemplate jdbcTemplate;

    public UserDBStorageImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int getMaxId() {
        List<User> users = getAllUsers();
        int maxId = 0;
        for (User user : users) {
            if (maxId < user.getId()) maxId = user.getId();
        }
        return maxId + 1;
    }

    @Override
    public User addUser(User user) {
        user.setId(getMaxId());
        int userRows = jdbcTemplate.update("insert into users(id,name,login,email,birthday) values(?,?,?,?,?)",
                user.getId(), user.getName(), user.getLogin(), user.getEmail(), user.getBirthday());
        if (user.getFriends().size() > 0) {
            for (int id : user.getFriends()) {
                User friend = findUserById(id);
                if (friend.getFriends().contains(user.getId())) {
                    int friendsRows = jdbcTemplate.update("insert into friends(user_id,friend_id,status) values(?,?,?)", getMaxId(), id, true);
                } else {
                    int friendsRows = jdbcTemplate.update("insert into friends(user_id,friend_id,status) values(?,?,?)", getMaxId(), id, false);
                }
            }
        }
        return user;
    }

    @Override
    public void updateUser(User user) {
        removeUser(user.getId());
        addUser(user);
    }

    @Override
    public void removeUser(int id) {
        int usersRows = jdbcTemplate.update("delete from users where id = ?", id);
        int friendsRows = jdbcTemplate.update("delete from friends where user_id = ?", id);
        friendsRows = jdbcTemplate.update("delete from friends where friend_id = ?", id);
    }

    @Override
    public void addFriend(int userId, int friendId) {
            SqlRowSet friendsRowsOne = jdbcTemplate.queryForRowSet("select * from friends where user_id = ? and friend_id = ?",
                    userId, friendId);
            if (!friendsRowsOne.next()) {
                User friend = findUserById(friendId);
                if (friend.getFriends().contains(userId)) {
                    int friendsRows = jdbcTemplate.update("insert into friends(user_id,friend_id,status) values(?,?,?)",
                            userId, friendId, true);
                } else {
                    int friendsRows = jdbcTemplate.update("insert into friends(user_id,friend_id,status) values(?,?,?)",
                            userId, friendId, false);
                }
            }
    }

    @Override
    public void removeFriend(int userId, int friendId) {
            User friend = findUserById(friendId);
            if (friend.getFriends().contains(userId)) {
                SqlRowSet friendsRows = jdbcTemplate.queryForRowSet("delete from friends where user_id = ? and friend_id = ?", userId, friendId);
                int friendsInt = jdbcTemplate.update("update friends set status = ? where friend_id = ? and user_id = ?", false, userId, userId);
            } else {
                int friendsRows = jdbcTemplate.update("delete from friends where user_id = ? and friend_id = ?", userId, friendId);
            }
    }

    @Override
    public User findUserById(int id) {
        SqlRowSet userRows = jdbcTemplate.queryForRowSet("select * from users where id = ?", id);
        if (userRows.next()) {
            User user = new User(
                    id,
                    userRows.getString("name"),
                    userRows.getString("login"),
                    userRows.getString("email"),
                    userRows.getString("birthday")
            );

            List<User> frnds = getAllFriends(id);
            for (User u : frnds) {
                user.addFriend(u.getId());
            }
            return user;
        } else {
            return null;
        }
    }

    @Override
    public List<User> getAllUsers() {
        SqlRowSet userRows = jdbcTemplate.queryForRowSet("select id from users");
        List<User> users = new ArrayList<>();
        while (userRows.next()) {
            users.add(findUserById(userRows.getInt("id")));
        }
        return users;
    }

    @Override
    public List<User> getAllFriends(int id) {
        SqlRowSet userRows = jdbcTemplate.queryForRowSet("select friend_id from friends where user_id = ?", id);
        List<User> users = new ArrayList<>();
        while (userRows.next()) {
            users.add(findUserById(userRows.getInt("friend_id")));
        }
        return users;
    }

    @Override
    public List<User> getCommonFriends(int userOne, int userTwo) {
        List<Integer> friendsOfUserOneIds = new ArrayList<>();
        for (User user : getAllFriends(userOne)) {
            friendsOfUserOneIds.add(user.getId());
        }
        List<Integer> friendsOfUserTwoIds = new ArrayList<>();
        for (User user : getAllFriends(userTwo)) {
            friendsOfUserTwoIds.add(user.getId());
        }
        Set<Integer> commonIds = new HashSet<>();
        for (int id : friendsOfUserOneIds) {
            if (friendsOfUserTwoIds.contains(id))
                commonIds.add(id);
        }
        List<User> commonFriends = new ArrayList<>();
        for (int id : friendsOfUserTwoIds) {
            if (friendsOfUserOneIds.contains(id))
                commonIds.add(id);
        }
        for (int id : commonIds) {
            commonFriends.add(findUserById(id));
        }
        return commonFriends;
    }
}
