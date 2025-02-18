/*
 * $Id$
 * $URL$
 *
 * ====================================================================
 * Ikasan Enterprise Integration Platform
 *
 * Distributed under the Modified BSD License.
 * Copyright notice: The copyright for this software and a full listing
 * of individual contributors are as shown in the packaged copyright.txt
 * file.
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  - Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  - Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 *  - Neither the name of the ORGANIZATION nor the names of its contributors may
 *    be used to endorse or promote products derived from this software without
 *    specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE
 * USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * ====================================================================
 */
package org.ikasan.security.service;

import org.ikasan.security.dao.UserDao;
import org.ikasan.security.model.*;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

/**
 * Default implementation of the <code>UserService</code>
 *
 * @author Ikasan Development Team
 */
public class UserServiceImpl implements UserService
{

    /**
     * Environment property used to enable local authentication. When set to false, allow local authentication.
     */
    private static final String DASHBOARD_EXTRACT_ENABLED_PROPERTY = "ikasan.dashboard.extract.enabled";

    /**
     * Data access object for <code>User</code>
     */
    private UserDao userDao;

    /**
     * Data access object for <code>SecurityService</code>s
     */
    private SecurityService securityService;

    /**
     * <code>PasswordEncoder</code> for encoding user passwords
     */
    private PasswordEncoder passwordEncoder;

    /**
     * <code>Environment</code> for environment properties
     */
    private boolean preventLocalAuthentication;

    /**
     * Constructor
     *
     * @param userDao
     * @param securityService
     * @param passwordEncoder
     */
    public UserServiceImpl(UserDao userDao, SecurityService securityService, PasswordEncoder passwordEncoder, boolean preventLocalAuthentication)
    {
        super();
        this.userDao = userDao;
        this.securityService = securityService;
        this.passwordEncoder = passwordEncoder;
        this.preventLocalAuthentication = preventLocalAuthentication;
    }

    @Override
    public List<UserLite> getUsersWithRole(String roleName, UserFilter userFilter, int limit, int offset) {
        return this.userDao.getUsersWithRole(roleName, userFilter, limit, offset);
    }

    @Override
    public int getUsersWithRoleCount(String roleName, UserFilter userFilter) {
        return this.userDao.getUsersWithRoleCount(roleName, userFilter);
    }

    @Override
    public List<UserLite> getUsersWithoutRole(String roleName, UserFilter userFilter, int limit, int offset) {
        return this.userDao.getUsersWithoutRole(roleName, userFilter, limit, offset);
    }

    @Override
    public int getUsersWithoutRoleCount(String roleName, UserFilter userFilter) {
        return this.userDao.getUsersWithoutRoleCount(roleName, userFilter);
    }

    @Override
    public int getUserCount(UserFilter userFilter) {
        return this.userDao.getUserCount(userFilter);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.ikasan.framework.security.service.UserService#getUsers()
     */
    public List<User> getUsers()
    {
        return userDao.getUsers();
    }

    /*
     * (non-Javadoc)
     *
     * @see org.ikasan.framework.security.service.UserService#getUserLites()
     */
    public List<UserLite> getUserLites()
    {
        return userDao.getUserLites();
    }

    @Override
    public List<User> getUsers(UserFilter userFilter, int limit, int offset) {
        return this.userDao.getUsers(userFilter, limit, offset);
    }

    @Override
    public List<UserLite> getUserLites(int limit, int offset) {
        return this.userDao.getUserLites(limit, offset);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.springframework.security.userdetails.UserDetailsManager#changePassword(java.lang.String,
     * java.lang.String)
     */
    public void changePassword(String oldPassword, String newPassword)
    {
        throw new UnsupportedOperationException(
            "As administrators can change passwords for other users we have our own userChangePasssword method.");
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.springframework.security.userdetails.UserDetailsManager#createUser(org.springframework.security.userdetails
     * .UserDetails)
     */
    public void createUser(UserDetails userDetails)
    {
        String username = userDetails.getUsername();
        String password = userDetails.getPassword();

        String email = "";
        String firstName = "";
        String surname = "";
        String department = "";
        boolean requiredPasswordChange = false;

        if ( userDetails instanceof User tempUser )
        {
            email = tempUser.getEmail();
            firstName = tempUser.getFirstName();
            surname = tempUser.getSurname();
            department = tempUser.getDepartment();
            requiredPasswordChange = tempUser.isRequiresPasswordChange();
        }
        if ( username == null || "".equals(username) )
        {
            throw new IllegalArgumentException("userDetails must contain a non empty username");
        }
        if ( password == null || "".equals(password) )
        {
            throw new IllegalArgumentException("userDetails must contain a non empty password");
        }
        if ( email == null || "".equals(email) )
        {
            throw new IllegalArgumentException("user must contain a non empty email address");
        }

        if ( userExists(username) )
        {
            throw new IllegalArgumentException("userDetails must contain a unique username");
        }

        IkasanPrincipal principal = new IkasanPrincipal();
        principal.setName(username);
        principal.setType("user");
        principal.setDescription(username + " user principal.");
        this.securityService.savePrincipal(principal);

        String encodedPassword = passwordEncoder.encode(password);
        User userToCreate = new User(username, encodedPassword, email, true);
        userToCreate.setFirstName(firstName);
        userToCreate.setSurname(surname);
        userToCreate.setDepartment(department);
        userToCreate.addPrincipal(principal);
        userToCreate.setRequiresPasswordChange(requiredPasswordChange);
        userDao.save(userToCreate);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.springframework.security.userdetails.UserDetailsManager#deleteUser(java.lang.String)
     */
    public void deleteUser(String username)
    {
        userDao.delete(getUserForOperation(username));
    }

    /*
     * (non-Javadoc)
     *
     * @see org.ikasan.framework.security.service.UserService#disableUser(java.lang.String)
     */
    public void disableUser(String username)
    {
        User user = getUserForOperation(username);
        user.setEnabled(false);
        userDao.save(user);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.ikasan.framework.security.service.UserService#enableUser(java.lang.String)
     */
    public void enableUser(String username)
    {
        User user = getUserForOperation(username);
        user.setEnabled(true);
        userDao.save(user);
    }

    /**
     * Looks up a user, but throws an exception if it doesn't exist
     *
     * @param username
     * @return User if exists
     * @throws IllegalArgumentException if user is not found
     */
    private User getUserForOperation(String username) throws IllegalArgumentException
    {
        User user = userDao.getUser(username);
        if ( user == null )
        {
            throw new IllegalArgumentException("user does not exist with username [" + username + "]");
        }
        return user;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.springframework.security.userdetails.UserDetailsManager#updateUser(org.springframework.security.userdetails
     * .UserDetails)
     */
    public void updateUser(UserDetails userDetails)
    {
        userDao.save((User) userDetails);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.springframework.security.userdetails.UserDetailsManager#userExists(java.lang.String)
     */
    public boolean userExists(String username)
    {
        return (userDao.getUser(username) != null);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.ikasan.framework.security.service.UserService#loadUserByUsername(java.lang.String)
     */
    public User loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException
    {
        if (this.preventLocalAuthentication) {
            throw new UsernameNotFoundException(DASHBOARD_EXTRACT_ENABLED_PROPERTY + "=true. Do not try to authenticate locally. Username : " + username);
        }
        User user = userDao.getUser(username);
        if ( user == null )
        {
            throw new UsernameNotFoundException("Unknown username : " + username);
        }
        if ( user.isEnabled() )
        {
            return user;
        }
        else
        {
            throw new UsernameNotFoundException("Disabled username : " + username + " contact administrator.");
        }

    }

    /*
     * (non-Javadoc)
     *
     * @see org.ikasan.framework.security.service.UserService#getAuthorities()
     */
    public List<Policy> getAuthorities()
    {
        List<Policy> policies = securityService.getAllPolicies();

        return policies;

    }

    /*
     * (non-Javadoc)
     *
     * @see org.ikasan.framework.security.service.UserService#grantAuthority(java.lang.String, java.lang.String)
     */
    public void grantAuthority(String username, String authority)
    {
        User user = loadUserByUsername(username);
        Policy nongrantedPolicy = securityService.findPolicyByName(authority);
        IkasanPrincipal userPrincipal = securityService.findPrincipalByName(user.getUsername());
        Role userRole = securityService.findRoleByName("User");

        user.addPrincipal(userPrincipal);
        userPrincipal.addRole(userRole);
        userRole.addPolicy(nongrantedPolicy);
        userDao.save(user);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.ikasan.framework.security.service.UserService#revokeAuthority(java.lang.String, java.lang.String)
     */
    public void revokeAuthority(String username, String authority)
    {
        User user = loadUserByUsername(username);
        Policy nongrantedPolicy = securityService.findPolicyByName(authority);
        user.revokePolicy(nongrantedPolicy);
        userDao.save(user);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.ikasan.framework.security.service.UserService#changeUsersPassword(java.lang.String, java.lang.String)
     */
    public void changeUsersPassword(String username, String newPassword, String confirmNewPassword)
        throws IllegalArgumentException
    {
        if ( !newPassword.equals(confirmNewPassword) )
        {
            throw new IllegalArgumentException("Passwords do not match, please try again.");
        }
        String encodedPassword = passwordEncoder.encode(newPassword);
        User user = loadUserByUsername(username);
        user.setPassword(encodedPassword);
        user.setEnabled(true);
        userDao.save(user);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.ikasan.framework.security.service.UserService#changeUsersEmail(java.lang.String, java.lang.String)
     */
    public void changeUsersEmail(String username, String newEmail) throws IllegalArgumentException
    {
        User user = null;
        try
        {
            user = loadUserByUsername(username);
        }
        catch (UsernameNotFoundException e)
        {
            throw new IllegalArgumentException("Username could not be found", e);
        }
        catch (DataAccessException e)
        {
            throw new IllegalArgumentException("Username could not be found due to a DataAccessException", e);
        }
        user.setEmail(newEmail);
        userDao.save(user);
    }

    /* (non-Javadoc)
     * @see org.ikasan.security.service.UserService#getUserByUsernameLike(java.lang.String)
     */
    @Override
    public List<User> getUserByUsernameLike(String username)
    {
        return this.userDao.getUserByUsernameLike(username);
    }

    /* (non-Javadoc)
     * @see org.ikasan.security.service.UserService#getUserByFirstnameLike(java.lang.String)
     */
    @Override
    public List<User> getUserByFirstnameLike(String firstname)
    {
        return this.userDao.getUserByFirstnameLike(firstname);
    }

    /* (non-Javadoc)
     * @see org.ikasan.security.service.UserService#getUserBySurnameLike(java.lang.String)
     */
    @Override
    public List<User> getUserBySurnameLike(String surname)
    {
        return this.userDao.getUserBySurnameLike(surname);
    }
}
